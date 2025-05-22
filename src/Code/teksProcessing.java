/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Code;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.id.IndonesianAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author maria
 */
public class teksProcessing {
    private final Analyzer analyzer;

    public teksProcessing() {
        this.analyzer = new IndonesianAnalyzer();
    }

    public List<String> proses(String text) {
        List<String> hasil = new ArrayList<>();
        try (TokenStream tokenStream = analyzer.tokenStream(null, new StringReader(text))) {
            CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                hasil.add(attr.toString());
            }
            tokenStream.end();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasil;
    }
}
