package jsastrawi.morphology.defaultimpl.visitor.prefixrules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jsastrawi.morphology.defaultimpl.visitor.Disambiguator;

public class PrefixRule34 implements Disambiguator {

    @Override
    public String disambiguate(String word) {
        Matcher matcher = Pattern.compile("^pe([bcdfghjklmnpqrstvwxyz])(.*)$").matcher(word);
        if (matcher.find()) {
            if (matcher.group(2).matches("^er(.*)$")) {
                return word;
            }
            return matcher.group(1) + matcher.group(2);
        }
        
        return word;
    }
}
