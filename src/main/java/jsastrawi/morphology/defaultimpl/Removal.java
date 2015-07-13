package jsastrawi.morphology.defaultimpl;

import jsastrawi.morphology.defaultimpl.visitor.ContextVisitor;

public interface Removal {
    
    public ContextVisitor getVisitor();
    
    public String getSubject();
    
    public String getResult();
    
    public String getRemovedPart();
    
    public String getAffixType();
    
}