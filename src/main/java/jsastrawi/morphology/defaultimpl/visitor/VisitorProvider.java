package jsastrawi.morphology.defaultimpl.visitor;

import java.util.LinkedList;
import java.util.List;

public class VisitorProvider {
    private final List<ContextVisitor> visitors;
    private final List<ContextVisitor> suffixVisitors;
    private final List<ContextVisitor> prefixVisitors;
    
    public VisitorProvider() {
        visitors = new LinkedList<>();
        prefixVisitors = new LinkedList<>();
        
        suffixVisitors = new LinkedList<>();
        suffixVisitors.add(new RemoveInflectionalParticle()); // lah|kah|tah|pun
        suffixVisitors.add(new RemoveInflectionalPossessivePronoun()); // ku|mu|nya
        suffixVisitors.add(new RemoveDerivationalSuffix()); // i|kan|an
    }
    
    public List<ContextVisitor> getSuffixVisitors() {
        return suffixVisitors;
    }

    public List<ContextVisitor> getVisitors() {
        return visitors;
    }

    public List<ContextVisitor> getPrefixVisitors() {
        return prefixVisitors;
    }
}
