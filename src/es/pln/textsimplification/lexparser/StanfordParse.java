package es.pln.textsimplification.lexparser;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Raúl Moya Reyes <raulmoya.es>
 */
public class StanfordParse {

    private final LexicalizedParser lp;

    public StanfordParse() {
        lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
    }

    public Map<Integer, Word> analyze(String sentence) {
        // Tokenizador
        TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
        Tokenizer<CoreLabel> tok = tokenizerFactory.getTokenizer(new StringReader(sentence));
        List<CoreLabel> rawWords2 = tok.tokenize();
        Tree parse = lp.apply(rawWords2);

        // Analisis sintáctico
        TreebankLanguagePack tlp = new PennTreebankLanguagePack();
        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
        GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
        Collection<TypedDependency> tdl = gs.allTypedDependencies(); //gs.typedDependenciesCCprocessed();
        
        System.out.println("Lexical Analyzer: "+tdl);

        // Mapeo para desacoplar del StanfordParse
        Map<Integer,Word> wordList = new TreeMap();
        for(TypedDependency t : tdl){
            Word w = new Word(t.reln().toString(), t.dep().toString(), t.gov().toString());
            wordList.put(w.getIdDepWord(), w);
        }
        return wordList;
    }
}
