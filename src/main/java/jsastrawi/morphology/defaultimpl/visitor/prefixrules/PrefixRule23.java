package jsastrawi.morphology.defaultimpl.visitor.prefixrules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jsastrawi.morphology.defaultimpl.visitor.Disambiguator;

public class PrefixRule23 implements Disambiguator {

    @Override
    public String disambiguate(String word) {        
        Matcher matcher = Pattern.compile("^per([bcdfghjklmnpqrstvwxyz])([a-z])(.*)$").matcher(word);
        if (matcher.find()) {
            if (matcher.group(3).matches("^er(.*)$")) {
                return word;
            }
            return matcher.group(1) + matcher.group(2) + matcher.group(3);
        }
        
        return word;
    }
}
