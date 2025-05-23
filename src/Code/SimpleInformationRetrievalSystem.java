/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Code;

/**
 *
 * @author maria
 */
import java.io.*;
import java.util.*;
import java.nio.file.*;

// Sistem Pencarian Informasi Sederhana
public class SimpleInformationRetrievalSystem {

    private HashMap<String, ArrayList<String>> indeksTerbalik;//Peta (Map) dari kata ke daftar nama file yang mengandung kata itu. Ini adalah inti dari sistem pencarian.
    private String pathFolderDokumen;
    private ArrayList<String> daftarNamaDokumen;

    //CONSTRUKTOR
    public SimpleInformationRetrievalSystem(String path) {
        indeksTerbalik = new HashMap<>();
        pathFolderDokumen = path;
        daftarNamaDokumen = new ArrayList<>();
        bangunIndeks();//setiap kelas ini membuat objek maka otomatis function bangunIndeks() dipanggil
    }

    // Membangun struktur indeks terbalik dari dokumen-dokumen
    private void bangunIndeks() {
        try {
            File folder = new File(pathFolderDokumen);
            File[] fileList = urutkanFileBerdasarkanAngka(folder.listFiles());

            if (fileList != null) {//cek apakah array tidak kosong
                for (File file : fileList) {
                    if (file.isFile()) {//memastikan yang diproses adalah file
                        String namaFile = file.getName();
                        daftarNamaDokumen.add(namaFile);
                        String isi = new String(Files.readAllBytes(file.toPath()));//baca seluruh isi file menjadi string
                        
                        teksProcessing preprocessor = new teksProcessing();//untuk stopword dan steaming dari kelas teksProcessing
                        
                        //diubah ke huruf kecil, kemudian dipanggil function process dari kelas teksProcessing
                        List<String> kataKata = preprocessor.proses(isi.toLowerCase());

                        for (String kata : kataKata) {//iterasi dari hasil processing
                            if (!indeksTerbalik.containsKey(kata)) {//cek apakah kata sudah muncul sebelumnya pada indeks
                                indeksTerbalik.put(kata, new ArrayList<>());//jika belum buat daftar baru
                            }
                            if (!indeksTerbalik.get(kata).contains(namaFile)) {//cek juga apakah dokumen saat ini belum dicatat di daftar kata tersebut
                                indeksTerbalik.get(kata).add(namaFile);//tambahkan nama dokumen ke daftar jika belum ada
                            }
                        }

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Pencarian berdasarkan satu kata kunci
    public ArrayList<String> cariSatuKataKunci(String kataKunci) {
        kataKunci = kataKunci.toLowerCase();
        if (indeksTerbalik.containsKey(kataKunci)) {
            return indeksTerbalik.get(kataKunci);
        }
        return new ArrayList<>();
    }

    // Pencarian berdasarkan banyak kata kunci (hasil harus mengandung semuanya)
    public ArrayList<String> cariBanyakKataKunci(String[] kataKunci) {
        if (kataKunci.length == 0) {
            return new ArrayList<>();
        }

        ArrayList<String> hasil = new ArrayList<>(cariSatuKataKunci(kataKunci[0]));

        for (int i = 1; i < kataKunci.length; i++) {
            ArrayList<String> hasilSaatIni = cariSatuKataKunci(kataKunci[i]);
            hasil.retainAll(hasilSaatIni); // hanya ambil yang ada di semua
        }

        return hasil;
    }

    
    // Menampilkan seluruh struktur indeks terbalik
    //fungsi ini untuk menampilkan semua kata yang ada pada dokumen
    public void tampilkanIndeksTerbalik() {
        System.out.println("Struktur Indeks Terbalik:");
        for (Map.Entry<String, ArrayList<String>> entry : indeksTerbalik.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            for (String dokumen : entry.getValue()) {
                System.out.print(dokumen + " ");
            }
            System.out.println();
        }
    }

    
    private int ambilAngkaDariNama(String namaFile) {
        String angka = "";
        for (int i = 0; i < namaFile.length(); i++) {
            if (Character.isDigit(namaFile.charAt(i))) {
                angka += namaFile.charAt(i);
            }
        }

        if (angka.isEmpty()) {
            return 0;
        }

        return Integer.parseInt(angka);
    }

    
    // Mengurutkan file berdasarkan angka yang terkandung dalam nama file, ini sejenis dengan sorting pakai bubble sort
    private File[] urutkanFileBerdasarkanAngka(File[] fileList) {
        for (int i = 0; i < fileList.length - 1; i++) {
            for (int j = i + 1; j < fileList.length; j++) {
                int angkaI = ambilAngkaDariNama(fileList[i].getName());
                int angkaJ = ambilAngkaDariNama(fileList[j].getName());

                if (angkaI > angkaJ) {//membandingan jika angkaI lebih besar dari angkaJ maka,
                    // tukar posisi file
                    File temp = fileList[i];
                    fileList[i] = fileList[j];
                    fileList[j] = temp;
                }
            }
        }
        return fileList;
    }
}
