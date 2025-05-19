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
        String documentsPath = "..\\Koleksi";
        SimpleInformationRetrievalSystem system = new SimpleInformationRetrievalSystem(documentsPath);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Search with single keyword");
            System.out.println("2. Search with multiple keywords");
            System.out.println("3. Show inverted index structure");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter keyword: ");
                    String keyword = scanner.nextLine();
                    ArrayList<String> results = system.searchSingleKeyword(keyword);
                    System.out.println("Documents containing '" + keyword + "': " + results);
                    break;
                case 2:
                    System.out.print("Enter keywords (separated by space): ");
                    String[] keywords = scanner.nextLine().split(" ");
                    ArrayList<String> multiResults = system.searchMultipleKeywords(keywords);
                    System.out.println("Documents containing all keywords: " + multiResults);
                    break;
                case 3:
                    system.printInvertedIndex();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}
