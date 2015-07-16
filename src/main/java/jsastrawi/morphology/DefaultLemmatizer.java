package jsastrawi.morphology;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jsastrawi.morphology.defaultimpl.Context;
import jsastrawi.morphology.defaultimpl.visitor.VisitorProvider;

public class DefaultLemmatizer implements Lemmatizer {
    private final Set<String> dictionary;
    private final VisitorProvider visitorProvider;

    public DefaultLemmatizer(Set<String> dictionary) {
        this.dictionary = dictionary;
        this.visitorProvider = new VisitorProvider();
    }
    
    @Override
    public String lemmatize(String word) {
        word = word.toLowerCase();
        
        if (isPlural(word)) {
            return lemmatizePluralWord(word);
        } else {
            return lemmatizeSingularWord(word);
        }
    }
    
    public Set<String> getDictionary() {
        return dictionary;
    }

    private boolean isPlural(String word) {
        // -ku|-mu|-nya
        // nikmat-Ku, etc
        Matcher matcher = Pattern.compile("^(.*)-(ku|mu|nya)$").matcher(word);
        if (matcher.find()) {
            return matcher.group(1).contains("-");
        }
        
        return word.contains("-");
    }

    private String lemmatizePluralWord(String word) {
        Matcher matcher = Pattern.compile("^(.*)-(.*)$").matcher(word);
        
        if (!matcher.find()) {
            return word;
        }
        
        String word1 = matcher.group(1);
        String word2 = matcher.group(2);
        
        if (word1.isEmpty() || word2.isEmpty()) {
            return word;
        }

        // malaikat-malaikat-nya -> malaikat malaikat-nya
        String suffix = word2;
        Matcher matcher2 = Pattern.compile("^(.*)-(.*)$").matcher(word1);
        if (suffix.matches("^ku|mu|nya$") && matcher2.find()) {
            word1 = matcher2.group(1);
            word2 = matcher2.group(2) + "-" + suffix;
        }
        
        // berbalas-balasan -> balas
        String lemma1 = lemmatizeSingularWord(word1);
        String lemma2 = lemmatizeSingularWord(word2);
        
        // meniru-nirukan -> tiru
        if (!dictionary.contains(word2) && lemma2.equals(word2)) {
            lemma2 = lemmatizeSingularWord("me" + word2);
        }
        
        if (lemma1.equals(lemma2)) {
            return lemma1;
        } else {
            return word;
        }
    }
    
    private String lemmatizeSingularWord(String word) {
        Context context = new Context(word, dictionary, visitorProvider);
        context.execute();
        
        return context.getResult();
    }
    
}
