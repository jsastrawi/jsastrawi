package jsastrawi.morphology;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import junit.framework.TestCase;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import org.junit.Before;

public class DefaultLemmatizerTest extends TestCase {
    private DefaultLemmatizer lemmatizer;
    private Set<String> dictionary;
    
    @Before
    @Override
    public void setUp() {
        String rootWords[] = {"nilai", "hancur", "benar", "apa", "siapa"};
        
        dictionary = new LinkedHashSet<>(Arrays.asList(rootWords));
        lemmatizer = new DefaultLemmatizer(dictionary);
    }
    
    public void testImplementsLemmatizerInterface() {
        assertThat(lemmatizer, instanceOf(Lemmatizer.class));
    }
    
    private void assertLemma(String word, String lemma) {
        assertEquals(lemma, lemmatizer.lemmatize(word));
    }
    
    public void testDontStemWordFoundInDictionary() {
        assertLemma("nilai", "nilai");
    }

    public void testDontStemShortWords() {
        assertLemma("mei", "mei");
        assertLemma("bui", "bui");
    }
    
    public void testLahKahTahPun() {
        assertLemma("hancurlah", "hancur");
        assertLemma("benarkah", "benar");
        assertLemma("apatah", "apa");
        assertLemma("siapapun", "siapa");
    }
}
