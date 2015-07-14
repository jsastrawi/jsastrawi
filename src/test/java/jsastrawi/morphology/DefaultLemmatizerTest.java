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
        String rootWords[] = {"nilai", "hancur", "benar", "apa", "siapa", "jubah", 
            "baju", "celana", "hantu", "beli", "jual", "buku", "milik", "kulit",
            "beri", "sakit", "kasih", "buang", "sakit", "suap", "adu", "rambut",
            "suara", "daerah", "ajar", "kerja", "ternak", "asing", "raup", "gerak",
            "puruk", "terbang", "lipat", "ringkas", "warna", "yakin", "bangun",
            "fitnah", "vonis", "baru", "ajar"
        };
        
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
    
    public void testKuMuNya() {
        assertLemma("jubahku", "jubah");
        assertLemma("bajumu", "baju");
        assertLemma("celananya", "celana");
    }
    
    public void testIKanAn() {
        assertLemma("hantui", "hantu");
        assertLemma("belikan", "beli");
        assertLemma("jualan", "jual");
    }
    
    public void testSuffixCombination() {
        assertLemma("bukumukah", "buku");
        assertLemma("miliknyalah", "milik");
        assertLemma("kulitkupun", "kulit");
        assertLemma("berikanku", "beri");
        assertLemma("sakitimu", "sakit");
        assertLemma("beriannya", "beri");
        assertLemma("kasihilah", "kasih");
    }
    
    public void testPlainPrefix() {
        assertLemma("dibuang", "buang");
        assertLemma("kesakitan", "sakit");
        assertLemma("sesuap", "suap");
    }
    
    public void testPrefixDisambiguation() {
        // rule 1a : berV -> ber-V
        assertLemma("beradu", "adu");
        
        // rule 1b : berV -> be-rV
        assertLemma("berambut", "rambut");
        
        // rule 2 : berCAP -> ber-CAP
        assertLemma("bersuara", "suara");
        
        // rule 3 : berCAerV -> ber-CAerV where C != 'r'
        assertLemma("berdaerah", "daerah");
        
        // rule 4 : belajar -> bel-ajar
        assertLemma("belajar", "ajar");
        
        // rule 5 : beC1erC2 -> be-C1erC2 where C1 != {'r'|'l'}
        assertLemma("bekerja", "kerja");
        assertLemma("beternak", "ternak");

        // rule 6a : terV -> ter-V
        assertLemma("terasing", "asing");
        
        // rule 6b : terV -> te-rV
        assertLemma("teraup", "raup");
        
        // rule 7 : terCerV -> ter-CerV where C != 'r'
        assertLemma("tergerak", "gerak");
        
        // rule 8 : terCP -> ter-CP where C != 'r' and P != 'er'
        assertLemma("terpuruk", "puruk");
        
        // rule 9 : teC1erC2 -> te-C1erC2 where C1 != 'r'
        assertLemma("teterbang", "terbang");
        
        // rule 10 : me{l|r|w|y}V -> me-{l|r|w|y}V
        assertLemma("melipat", "lipat");
        assertLemma("meringkas", "ringkas");
        assertLemma("mewarnai", "warna");
        assertLemma("meyakinkan", "yakin");
        
        // rule 11 : mem{b|f|v} -> mem-{b|f|v}
        assertLemma("membangun", "bangun");
        assertLemma("memfitnah", "fitnah");
        assertLemma("memvonis", "vonis");
        
        // rule 12 : mempe{r|l} -> mem-pe
        //assertLemma("memperbaru", "baru");
        //assertLemma("mempelajar", "ajar");
    }
}
