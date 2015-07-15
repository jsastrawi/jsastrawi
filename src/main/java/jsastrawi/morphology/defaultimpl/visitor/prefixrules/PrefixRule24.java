package jsastrawi.morphology.defaultimpl.visitor.prefixrules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jsastrawi.morphology.defaultimpl.visitor.Disambiguator;

public class PrefixRule24 implements Disambiguator {

    @Override
    public String disambiguate(String word) {        
        Matcher matcher = Pattern.compile("^per([bcdfghjklmnpqrstvwxyz])([a-z])er([aiueo])(.*)$").matcher(word);
        if (matcher.find()) {
            if (matcher.group(1).equals("r")) {
                return word;
            }
            return matcher.group(1) + matcher.group(2) + "er" + matcher.group(3) + matcher.group(4);
        }
        
        return word;
    }
}
