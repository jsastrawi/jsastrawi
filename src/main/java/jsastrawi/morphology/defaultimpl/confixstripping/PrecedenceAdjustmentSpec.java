package jsastrawi.morphology.defaultimpl.confixstripping;

import jsastrawi.specification.Specification;

public class PrecedenceAdjustmentSpec implements Specification<String> {

    private String[] regexRules = {
        "^be(.*)lah$",
        "^be(.*)an$",
        "^me(.*)i$",
        "^di(.*)i$",
        "^pe(.*)i$",
        "^ter(.*)i$"
    };
            
    @Override
    public boolean isSatisfiedBy(String word) {
        for (String rule : regexRules) {
            if (word.matches(rule)) {
                return true;
            }
        }
        
        return false;
    }
    
}
