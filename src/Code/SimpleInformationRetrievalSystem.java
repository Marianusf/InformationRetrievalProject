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

    private HashMap<String, ArrayList<String>> indeksTerbalik;
    private String pathFolderDokumen;
    private ArrayList<String> daftarNamaDokumen;

    public SimpleInformationRetrievalSystem(String path) {
        indeksTerbalik = new HashMap<>();
        pathFolderDokumen = path;
        daftarNamaDokumen = new ArrayList<>();
        bangunIndeks();
    }

    // Membangun struktur indeks terbalik dari dokumen-dokumen
    private void bangunIndeks() {
        try {
            File folder = new File(pathFolderDokumen);
            File[] fileList = urutkanFileBerdasarkanAngka(folder.listFiles());

            if (fileList != null) {
                for (File file : fileList) {
                    if (file.isFile()) {
                        String namaFile = file.getName();
                        daftarNamaDokumen.add(namaFile);

                        String isi = new String(Files.readAllBytes(file.toPath()));
//                        String[] kataKata = isi.toLowerCase().split("\\W+");
//
//                        for (String kata : kataKata) {
//                            if (kata.length() > 0) {
//                                if (!indeksTerbalik.containsKey(kata)) {
//                                    indeksTerbalik.put(kata, new ArrayList<>());
//                                }
//                                if (!indeksTerbalik.get(kata).contains(namaFile)) {
//                                    indeksTerbalik.get(kata).add(namaFile);
//                                }
//                            }
//                        }
                        teksProcessing preprocessor = new teksProcessing();
                        List<String> kataKata = preprocessor.proses(isi.toLowerCase());

                        for (String kata : kataKata) {
                            if (!indeksTerbalik.containsKey(kata)) {
                                indeksTerbalik.put(kata, new ArrayList<>());
                            }
                            if (!indeksTerbalik.get(kata).contains(namaFile)) {
                                indeksTerbalik.get(kata).add(namaFile);
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

    // Mengurutkan file berdasarkan angka yang terkandung dalam nama file
    private File[] urutkanFileBerdasarkanAngka(File[] fileList) {
        for (int i = 0; i < fileList.length - 1; i++) {
            for (int j = i + 1; j < fileList.length; j++) {
                int angkaI = ambilAngkaDariNama(fileList[i].getName());
                int angkaJ = ambilAngkaDariNama(fileList[j].getName());

                if (angkaI > angkaJ) {
                    // tukar posisi file
                    File temp = fileList[i];
                    fileList[i] = fileList[j];
                    fileList[j] = temp;
                }
            }
        }
        return fileList;
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

}
