package es.pln.textsimplification.lexparser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Raúl Moya Reyes <raulmoya.es>
 */
public class TextSimplification {

    private String simplifiedText;
    private final StanfordParse lexicalAnalyzer;
    private final Map<String, Integer> wordCount;

    public TextSimplification() {
        simplifiedText = "";
        lexicalAnalyzer = new StanfordParse();
        wordCount = new HashMap();
    }

    public void simplify(String text, List<String> relations) {
        // Dividir el texto en frases
        String[] sentences = text.split("\\.");
        for (String sentence : sentences) {
            // Analisis sintáctico de la frase
            Map<Integer, Word> analyzedSentence = lexicalAnalyzer.analyze(sentence);
            Map<Integer, String> aux = new TreeMap();
            for (Word w : analyzedSentence.values()) {
                // Aquí se filtran las palabras.
                if (!relations.contains(w.getRelation())) {
                    aux.put(w.getIdDepWord(), w.getDependentWord());
                    // Contamos las palabras para obtener las más representativas
                    if (wordCount.get(w.getDependentWord()) == null) {
                        wordCount.put(w.getDependentWord(), 1);
                    } else {
                        wordCount.put(w.getDependentWord(), wordCount.get(w.getDependentWord()) + 1);
                    }
                }

            }
            for (Map.Entry e : aux.entrySet()) {
                simplifiedText += e.getValue() + " ";
            }
            simplifiedText += ". ";
        }
    }

    public String getSimplifiedText() {
        return simplifiedText;
    }

    public String getWordCount() {
        int i1 = 0;

        String w1 = wordCount.keySet().iterator().next();
        String w2 = w1;
        String w3 = w2;

        for (Map.Entry e : wordCount.entrySet()) {
            if (i1 <= (int) e.getValue()) {
                w3 = w2;
                w2 = w1;
                w1 = (String) e.getKey();
                i1 = (int) e.getValue();
            }
        }

        return w1 + ", " + w2 + ", " + w3;
    }

}
