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

public class SimpleInformationRetrievalSystem {

    private HashMap<String, ArrayList<String>> invertedIndex;
    private String documentsPath;
    private ArrayList<String> documentNames;

    public SimpleInformationRetrievalSystem(String path) {
        invertedIndex = new HashMap<>();
        documentsPath = path;
        documentNames = new ArrayList<>();
        buildIndex();
    }

    private void buildIndex() {
        try {
            File folder = new File(documentsPath);
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String fileName = file.getName();
                        documentNames.add(fileName);

                        String content = new String(Files.readAllBytes(file.toPath()));
                        String[] words = content.toLowerCase().split("\\W+");

                        for (String word : words) {
                            if (word.length() > 0) {
                                if (!invertedIndex.containsKey(word)) {
                                    invertedIndex.put(word, new ArrayList<>());
                                }
                                if (!invertedIndex.get(word).contains(fileName)) {
                                    invertedIndex.get(word).add(fileName);
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

    public ArrayList<String> searchSingleKeyword(String keyword) {
        keyword = keyword.toLowerCase();
        if (invertedIndex.containsKey(keyword)) {
            return invertedIndex.get(keyword);
        }
        return new ArrayList<>();
    }

    public ArrayList<String> searchMultipleKeywords(String[] keywords) {
        if (keywords.length == 0) {
            return new ArrayList<>();
        }

        // Start with the first keyword's results
        ArrayList<String> result = new ArrayList<>(searchSingleKeyword(keywords[0]));

        // Intersect with other keywords' results
        for (int i = 1; i < keywords.length; i++) {
            ArrayList<String> currentResults = searchSingleKeyword(keywords[i]);
            result.retainAll(currentResults);
        }

        return result;
    }

    public void printInvertedIndex() {
        System.out.println("Inverted Index Structure:");
        for (Map.Entry<String, ArrayList<String>> entry : invertedIndex.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            for (String doc : entry.getValue()) {
                System.out.print(doc + " ");
            }
            System.out.println();
        }
    }

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
