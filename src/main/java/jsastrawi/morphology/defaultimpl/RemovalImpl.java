package jsastrawi.morphology.defaultimpl;

import jsastrawi.morphology.defaultimpl.visitor.ContextVisitor;

public class RemovalImpl implements Removal {

    private final ContextVisitor visitor;

    private final String subject;

    private final String result;

    private final String removedPart;

    private final String affixType;

    public RemovalImpl(ContextVisitor visitor, String subject, String result, String removedPart, String affixType) {
        this.visitor = visitor;
        this.subject = subject;
        this.result  = result;
        this.removedPart = removedPart;
        this.affixType = affixType;
    }

    @Override
    public ContextVisitor getVisitor() {
        return visitor;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public String getRemovedPart() {
        return removedPart;
    }

    @Override
    public String getAffixType() {
        return affixType;
    }

}
