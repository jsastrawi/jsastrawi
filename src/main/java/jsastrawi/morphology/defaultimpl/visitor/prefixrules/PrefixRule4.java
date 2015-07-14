package jsastrawi.morphology.defaultimpl.visitor.prefixrules;

import jsastrawi.morphology.defaultimpl.visitor.Disambiguator;

public class PrefixRule4 implements Disambiguator {

    @Override
    public String disambiguate(String word) {        
        if (word.equals("belajar")) {
            return "ajar";
        }
        
        return word;
    }
}
