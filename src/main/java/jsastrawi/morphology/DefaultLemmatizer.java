package jsastrawi.morphology;

import java.util.Set;
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
        if (dictionary.contains(word)) {
            return word;
        }
        
        if (word.length() <= 3) {
            return word;
        }
        
        Context context = new Context(word, dictionary, visitorProvider);
        context.execute();
        
        return context.getResult();
    }
    
}
