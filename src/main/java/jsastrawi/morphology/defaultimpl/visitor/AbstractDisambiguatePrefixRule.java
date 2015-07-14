package jsastrawi.morphology.defaultimpl.visitor;

import java.util.Collection;
import java.util.LinkedList;
import jsastrawi.morphology.defaultimpl.Context;
import jsastrawi.morphology.defaultimpl.Removal;
import jsastrawi.morphology.defaultimpl.RemovalImpl;

public abstract class AbstractDisambiguatePrefixRule implements ContextVisitor {

    protected Collection<Disambiguator> disambiguators = new LinkedList<Disambiguator>();
    
    @Override
    public void visit(Context context) {
        String result = null;
        
        for (Disambiguator disambiguator : disambiguators) {
            result = disambiguator.disambiguate(context.getCurrentWord());
            
            if (context.getDictionary().contains(result)) {
                break;
            }
        }
        
        if (null == result || result.equals(context.getCurrentWord())) {
            return;
        }
        
        String removedPart = context.getCurrentWord().replace(result, "");
        Removal removal = new RemovalImpl(this, context.getCurrentWord(), result, removedPart, "DP");
        context.addRemoval(removal);
        context.setCurrentWord(result);
    }
    
    public void addDisambiguator(Disambiguator disambiguator) {
        disambiguators.add(disambiguator);
    }
    
    public void addDisambiguators(Collection<Disambiguator> disambiguators) {
        for (Disambiguator disambiguator : disambiguators) {
            this.disambiguators.add(disambiguator);
        }
    }
}
