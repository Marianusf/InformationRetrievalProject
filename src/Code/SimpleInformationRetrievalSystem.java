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
            File[] fileList = folder.listFiles();

            if (fileList != null) {
                for (File file : fileList) {
                    if (file.isFile()) {
                        String namaFile = file.getName();
                        daftarNamaDokumen.add(namaFile);

                        String isi = new String(Files.readAllBytes(file.toPath()));
                        String[] kataKata = isi.toLowerCase().split("\\W+");

                        for (String kata : kataKata) {
                            if (kata.length() > 0) {
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

    public static void main(String[] args) {
        // Ganti path sesuai dengan lokasi folder dokumen Anda
        String pathDokumen = "..\\Koleksi";
        SimpleInformationRetrievalSystem sistem = new SimpleInformationRetrievalSystem(pathDokumen);

        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Cari satu kata kunci");
            System.out.println("2. Cari dengan dua kata kunci");
            System.out.println("3. Tampilkan struktur indeks terbalik");
            System.out.println("4. Keluar");
            System.out.print("Pilih Opsi : ");

            int pilihan = input.nextInt();
            input.nextLine(); // menghilangkan newline

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan Kata Kunci: ");
                    String kata = input.nextLine();
                    ArrayList<String> hasil1 = sistem.cariSatuKataKunci(kata);
                    System.out.println("Dokumen yang mengandung '" + kata + "': " + hasil1);
                    break;
                case 2:
                    System.out.print("Masukkan Kata Kunci (pisahkan dengan spasi): ");
                    String[] kataKunci = input.nextLine().split(" ");
                    ArrayList<String> hasil2 = sistem.cariBanyakKataKunci(kataKunci);
                    System.out.println("Dokumen yang mengandung semua kata kunci: " + hasil2);
                    break;
                case 3:
                    sistem.tampilkanIndeksTerbalik();
                    break;
                case 4:
                    System.out.println("Keluar...");
                    input.close();
                    System.exit(0);
                default:
                    System.out.println("Opsi tidak valid.");
            }
        }
    }
}
