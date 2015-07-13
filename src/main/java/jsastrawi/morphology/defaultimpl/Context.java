package jsastrawi.morphology.defaultimpl;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import jsastrawi.morphology.defaultimpl.visitor.ContextVisitor;
import jsastrawi.morphology.defaultimpl.visitor.VisitorProvider;

public class Context {
    
    private final String originalWord;
    private String currentWord;
    private final Set<String> dictionary;
    private final VisitorProvider visitorProvider;
    private final List<Removal> removals;
    private String result;
    private final List<ContextVisitor> visitors;
    private final List<ContextVisitor> suffixVisitors;
    private final List<ContextVisitor> prefixVisitors;
    private boolean processIsStopped;
    
    public Context(String originalWord, Set<String> dictionary, VisitorProvider visitorProvider) {
        this.originalWord = originalWord;
        this.currentWord  = originalWord;
        this.dictionary   = dictionary;
        this.visitorProvider = visitorProvider;
        this.removals = new LinkedList<>();
        
        this.visitors = visitorProvider.getVisitors();
        this.suffixVisitors = visitorProvider.getSuffixVisitors();
        this.prefixVisitors = visitorProvider.getPrefixVisitors();
    }
    
    public String getOriginalWord() {
        return originalWord;
    }
    
    public void setCurrentWord(String currentWord) {
        this.currentWord = currentWord;
    }

    public String getCurrentWord() {
        return currentWord;
    }

    public void addRemoval(Removal r) {
        removals.add(r);
    }
    
    public List<Removal> getRemovals() {
        return removals;
    }

    public String getResult() {
        return result;
    }
    
    public void execute() {
        // step 1 - 5
        startStemmingProcess();
        
        // step 6
        if (dictionary.contains(currentWord)) {
            result = currentWord;
        } else {
            result = originalWord;
        }
    }

    private void startStemmingProcess() {
        // step 1
        if (dictionary.contains(currentWord)) {
            return;
        }
        
        acceptVisitors(visitors);
        
        if (dictionary.contains(currentWord)) {
            return;
        }
        
        // step 2, 3
        removeSuffixes();
        if (dictionary.contains(currentWord)) {
            return;
        }
    }

    private String acceptVisitors(List<ContextVisitor> visitors) {
        for (ContextVisitor visitor : visitors) {
            accept(visitor);
            
            if (dictionary.contains(currentWord)) {
                return currentWord;
            }
            
            if (processIsStopped) {
                return currentWord;
            }
        }
        
        return currentWord;
    }

    private void accept(ContextVisitor visitor) {
        visitor.visit(this);
    }

    private void removeSuffixes() {
        acceptVisitors(suffixVisitors);
    }
}
