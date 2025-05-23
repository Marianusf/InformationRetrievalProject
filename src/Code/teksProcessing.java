/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Code;

import org.apache.lucene.analysis.Analyzer;//digunakan untuk semua analyzer Lucene
import org.apache.lucene.analysis.TokenStream;//token untuk hasil proses analisis teks
import org.apache.lucene.analysis.id.IndonesianAnalyzer;//analyzer bawaan lucene versi bahasa Indonesia untuk steaming dan stopword
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;//akses untuk token dalam bentuk teks
import java.io.StringReader;//membaca file dengan asumsi seperti file
import java.util.ArrayList;//untuk mengembalikan dan menyimpan daftar kata hasil tokenisasi
import java.util.List;
/**
 *
 * @author maria
 */
public class teksProcessing {
    private final Analyzer analyzer;//analyzer adalah variabel instan dari tipe Analyzer untuk analisis teks

    public teksProcessing() {
        this.analyzer = new IndonesianAnalyzer();//agar menggunakan analyzer bahasa Indonesia
    }

    public List<String> proses(String text) {
        List<String> hasil = new ArrayList<>();//buat arraylist kosong untuk hasil tokenisasi
        
        
        try (TokenStream tokenStream = analyzer.tokenStream(null, new StringReader(text))) {//Menganalisis teks dan StringReader agar analyzer bisa membaca string sebagai input.
            CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);//untuk menyimpan kata pada attr CharTermAtrribute
            tokenStream.reset();//reset untuk digunakan membaca kata/token
            
            while (tokenStream.incrementToken()) {//selama ada token berikutnya, maka ambil dan tambahkan ke list hasil
                hasil.add(attr.toString());
            }
            tokenStream.end();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasil;
    }
}
