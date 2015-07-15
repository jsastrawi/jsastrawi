package jsastrawi.morphology.defaultimpl.visitor.prefixrules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jsastrawi.morphology.defaultimpl.visitor.Disambiguator;

public class PrefixRule37a implements Disambiguator {

    @Override
    public String disambiguate(String word) {
        Matcher matcher = Pattern.compile("^([bcdfghjklmnpqrstvwxyz])(er[aiueo])(.*)$").matcher(word);
        if (matcher.find()) {
            return matcher.group(1) + matcher.group(2) + matcher.group(3);
        }
        
        return word;
    }
}
