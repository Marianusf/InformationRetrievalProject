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
        // Path ke folder dokumen (sesuaikan dengan lokasi Anda)
        String documentsPath = "C:\\Users\\MSI - PC\\InformationRetrievalProject\\Koleksi";
        SimpleInformationRetrievalSystem system = new SimpleInformationRetrievalSystem(documentsPath);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Cari kata satu kata kunci");
            System.out.println("2. Cari lebih dari satu kata kunci");
            System.out.println("3. Lihat struktur inverted index");
            System.out.println("4. Keluar");
            System.out.print("Pilih Menu: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Masukkan kata kunci: ");
                    String keyword = scanner.nextLine();
                    ArrayList<String> results = system.searchSingleKeyword(keyword);
                    System.out.println("Dokumen yang memiliki kata'" + keyword + "': " + results);
                    break;
                case 2:
                    System.out.print("Masukkan kata kunci (pisahkan dengan spasi): ");
                    String[] keywords = scanner.nextLine().split(" ");
                    ArrayList<String> multiResults = system.searchMultipleKeywords(keywords);
                    System.out.println("Dokumen yang memiliki semua kata: " + multiResults);
                    break;
                case 3:
                    system.printInvertedIndex();
                    break;
                case 4:
                    System.out.println("Keluar program...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid!");
            }
        }
    }
}
