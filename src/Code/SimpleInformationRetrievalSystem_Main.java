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

public class SimpleInformationRetrievalSystem_Main {

    public static void main(String[] args) {
        // Path ke folder dokumen (pastikan disesuaikan dengan lokasi folder dokumen Anda)
        String pathDokumen = "..\\Koleksi";
        SimpleInformationRetrievalSystem sistem = new SimpleInformationRetrievalSystem(pathDokumen);

        Scanner scanner = new Scanner(System.in);

                while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Cari dengan satu kata kunci");
            System.out.println("2. Cari dengan beberapa kata kunci");
            System.out.println("3. Tampilkan struktur indeks ");
            System.out.println("4. Keluar");

            int pilihan = -1;

            // Validasi input angka
            while (true) {
                System.out.print("Pilih opsi (1-4): ");
                String input = scanner.nextLine();

                try {
                    pilihan = Integer.parseInt(input);
                    if (pilihan < 1 || pilihan > 4) {
                        System.out.println("Masukkan angka antara 1 sampai 4.");
                    } else {
                        break; // input valid
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input tidak valid! Masukkan angka (bukan huruf atau simbol).");
                }
            }

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan kata kunci: ");
                    String kataKunci = scanner.nextLine();
                    ArrayList<String> hasil = sistem.cariSatuKataKunci(kataKunci);
                    System.out.println("Dokumen yang mengandung '" + kataKunci + "': " + hasil);
                    break;

                case 2:
                    System.out.print("Masukkan beberapa kata kunci (pisahkan dengan spasi): ");
                    String[] kataKunciBanyak = scanner.nextLine().split(" ");
                    ArrayList<String> hasilBanyak = sistem.cariBanyakKataKunci(kataKunciBanyak);
                    System.out.println("Dokumen yang mengandung semua kata kunci: " + hasilBanyak);
                    break;

                case 3:
                    sistem.tampilkanIndeksTerbalik();
                    break;

                case 4:
                    System.out.println("Keluar dari program...");
                    scanner.close();
                    System.exit(0);
            }
        }

    }
}
