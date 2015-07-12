package jsastrawi.morphology;

import java.util.Set;

public class DefaultLemmatizer implements Lemmatizer {
    private final Set<String> dictionary;

    public DefaultLemmatizer(Set<String> dictionary) {
        this.dictionary = dictionary;
    }
    
    @Override
    public String lemmatize(String word) {
        if (dictionary.contains(word)) {
            return word;
        }
        
        if (word.length() <= 3) {
            return word;
        }
        
        return "";
    }
    
}
