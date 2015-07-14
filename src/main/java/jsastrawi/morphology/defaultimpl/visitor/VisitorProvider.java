package jsastrawi.morphology.defaultimpl.visitor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import jsastrawi.morphology.defaultimpl.visitor.prefixrules.*;

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
        
        prefixVisitors.add(new RemovePlainPrefix()); // di|ke|se
        prefixVisitors.add(new PrefixDisambiguator(Arrays.asList(new Disambiguator[]{
                new PrefixRule1a(),
                new PrefixRule1b(),
        })));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule2()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule3()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule4()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule5()));
        prefixVisitors.add(new PrefixDisambiguator(Arrays.asList(new Disambiguator[]{
                new PrefixRule6a(),
                new PrefixRule6b()
        })));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule7()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule8()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule9()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule10()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule11()));
        //prefixVisitors.add(new PrefixDisambiguator(new PrefixRule12()));
        
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
