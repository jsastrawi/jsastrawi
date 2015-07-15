package jsastrawi.morphology.defaultimpl.visitor.prefixrules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jsastrawi.morphology.defaultimpl.visitor.Disambiguator;

public class PrefixRule32 implements Disambiguator {

    @Override
    public String disambiguate(String word) {        
        if (word.equals("pelajar")) {
            return "ajar";
        }
        
        Matcher matcher = Pattern.compile("^pe(l[aiueo])(.*)$").matcher(word);
        if (matcher.find()) {
            return matcher.group(1) + matcher.group(2);
        }
        
        return word;
    }
}
