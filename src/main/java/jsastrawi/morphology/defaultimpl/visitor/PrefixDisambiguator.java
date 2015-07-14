package jsastrawi.morphology.defaultimpl.visitor;

import java.util.Collection;

class PrefixDisambiguator extends AbstractDisambiguatePrefixRule implements ContextVisitor {
    
    public PrefixDisambiguator(Disambiguator disambiguator) {
        this.addDisambiguator(disambiguator);
    }
    
    public PrefixDisambiguator(Collection<Disambiguator> disambiguators) {
        this.addDisambiguators(disambiguators);
    }
    
}
