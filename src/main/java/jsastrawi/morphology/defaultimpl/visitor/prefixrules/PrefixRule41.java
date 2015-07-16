package jsastrawi.morphology.defaultimpl.visitor.prefixrules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jsastrawi.morphology.defaultimpl.visitor.Disambiguator;

public class PrefixRule41 implements Disambiguator {

    @Override
    public String disambiguate(String word) {
        Matcher matcher = Pattern.compile("^ku(.*)$").matcher(word);
        if (matcher.find()) {
            return matcher.group(1);
        }
        
        return word;
    }
}
