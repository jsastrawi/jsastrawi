/**
 * JSastrawi is licensed under The MIT License (MIT)
 *
 * Copyright (c) 2015 Andy Librian
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
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
            "fitnah", "vonis", "baru", "labuh", "minum", "pukul", "cinta", "dua",
            "jauh", "ziarah", "nuklir", "tangkap", "gila", "hajar", "qasar",
            "udara", "kupas", "suara", "populer", "warna", "yoga", "adil", "rumah",
            "muka", "tarung", "percaya", "serta", "pengaruh", "kritik",
            "sekolah", "tahan", "capai", "mulai", "tani", "abai",
            "syarat", "syukur", "bom", "promosi", "proteksi", "prediksi", "kaji",
            "sembunyi", "langgan", "laku", "baik", "bisik", "terang", "iman", "puas",
            "makan", "nyala", "nyanyi", "nyata", "nyawa", "rata", "lembut", "ligas",
            "kerja", "balas", "tebar", "daya", "makmur", "untung", "sepuluh", "ekonomi",
            "peran", "medan", "ideal", "final", "taat", "lewat", "nganga", "bagai",
            "badan", "tiru", "sepak", "kuasa", "nikmat", "malaikat"
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
        assertLemma("memperbaru", "baru");
        assertLemma("mempelajar", "ajar");

        // rule 13a : mem{rV|V} -> mem{rV|V}
        assertLemma("meminum", "minum");

        // rule 13b : mem{rV|V} -> me-p{rV|V}
        assertLemma("memukul", "pukul");

        // rule 14 : men{c|d|j|z} -> men-{c|d|j|z}
        assertLemma("mencinta", "cinta");
        assertLemma("mendua", "dua");
        assertLemma("menjauh", "jauh");
        assertLemma("menziarah", "ziarah");

        // rule 15a : men{V} -> me-n{V}
        assertLemma("menuklir", "nuklir");

        // rule 15b : men{V} -> me-t{V}
        assertLemma("menangkap", "tangkap");

        // rule 16 : meng{g|h|q} -> meng-{g|h|q}
        assertLemma("menggila", "gila");
        assertLemma("menghajar", "hajar");
        assertLemma("mengqasar", "qasar");

        // rule 17a : mengV -> meng-V
        assertLemma("mengudara", "udara");

        // rule 17b : mengV -> meng-kV
        assertLemma("mengupas", "kupas");

        // rule 18 : menyV -> meny-sV
        assertLemma("menyuarakan", "suara");

        // rule 19 : mempV -> mem-pV where V != 'e'
        assertLemma("mempopulerkan", "populer");

        // rule 20 : pe{w|y}V -> pe-{w|y}V
        assertLemma("pewarna", "warna");
        assertLemma("peyoga", "yoga");

        // rule 21a : perV -> per-V
        assertLemma("peradilan", "adil");

        // rule 21b : perV -> pe-rV
        assertLemma("perumahan", "rumah");

        // rule 23 : perCAP -> per-CAP where C != 'r' and P != 'er'
        assertLemma("permuka", "muka");

        // rule 24 : perCAerV -> per-CAerV where C != 'r'
        assertLemma("perdaerah", "daerah");

        // rule 25 : pem{b|f|v} -> pem-{b|f|v}
        assertLemma("pembangun", "bangun");
        assertLemma("pemfitnah", "fitnah");
        assertLemma("pemvonis", "vonis");

        // rule 26a : pem{rV|V} -> pe-m{rV|V}
        assertLemma("peminum", "minum");

        // rule 26b : pem{rV|V} -> pe-p{rV|V}
        assertLemma("pemukul", "pukul");

        // rule 27 : men{c|d|j|z} -> men-{c|d|j|z}
        // TODO : should find more relevant examples
        assertLemma("pencinta", "cinta");
        assertLemma("pendua", "dua");
        assertLemma("penjauh", "jauh");
        assertLemma("penziarah", "ziarah");

        // rule 28a : pen{V} -> pe-n{V}
        assertLemma("penuklir", "nuklir");

        // rule 28b : pen{V} -> pe-t{V}
        assertLemma("penangkap", "tangkap");

        // rule 29 : peng{g|h|q} -> peng-{g|h|q}
        assertLemma("penggila", "gila");
        assertLemma("penghajar", "hajar");
        assertLemma("pengqasar", "qasar");

        // rule 30a : pengV -> peng-V
        assertLemma("pengudara", "udara");

        // rule 30b : pengV -> peng-kV
        assertLemma("pengupas", "kupas");

        // rule 31 : penyV -> peny-sV
        assertLemma("penyuara", "suara");

        // rule 32 : pelV -> pe-lV except pelajar -> ajar
        assertLemma("pelajar", "ajar");
        assertLemma("pelabuh", "labuh");

        // rule 33 : peCerV -> per-erV where C != {r|w|y|l|m|n}
        // TODO : find the examples
        // rule 34 : peCP -> pe-CP where C != {r|w|y|l|m|n} and P != 'er'
        assertLemma("petarung", "tarung");

        // rule 35 : terC1erC2 -> ter-C1erC2 where C1 != 'r'
        assertLemma("terpercaya", "percaya");

        // rule 36 : peC1erC2 -> pe-C1erC2 where C1 != {r|w|y|l|m|n}
        assertLemma("pekerja", "kerja");
        assertLemma("peserta", "serta");

        // CS modify rule 12
        assertLemma("mempengaruhi", "pengaruh");

        // CS modify rule 16
        assertLemma("pengkritik", "kritik");

        // CS adjusting rule precedence
        assertLemma("bersekolah", "sekolah");
        assertLemma("bertahan", "tahan");
        assertLemma("mencapai", "capai");
        assertLemma("dimulai", "mulai");
        assertLemma("petani", "tani");
        assertLemma("terabai", "abai");

        // ECS
        assertLemma("mensyaratkan", "syarat");
        assertLemma("mensyukuri", "syukur");
        assertLemma("mengebom", "bom");
        assertLemma("mempromosikan", "promosi");
        assertLemma("memproteksi", "proteksi");
        assertLemma("memprediksi", "prediksi");
        assertLemma("pengkajian", "kaji");
        assertLemma("pengebom", "bom");

        // ECS loop pengembalian akhiran
        assertLemma("bersembunyi", "sembunyi");
        assertLemma("bersembunyilah", "sembunyi");
        assertLemma("pelanggan", "langgan");
        assertLemma("pelaku", "laku");
        assertLemma("pelangganmukah", "langgan");
        assertLemma("pelakunyalah", "laku");

        assertLemma("perbaikan", "baik");
        assertLemma("kebaikannya", "baik");
        assertLemma("bisikan", "bisik");
        assertLemma("menerangi", "terang");
        assertLemma("berimanlah", "iman");

        assertLemma("memuaskan", "puas");
        assertLemma("berpelanggan", "langgan");
        assertLemma("bermakanan", "makan");

        // CC (Modified ECS)
        assertLemma("menyala", "nyala");
        assertLemma("menyanyikan", "nyanyi");
        assertLemma("menyatakannya", "nyata");

        assertLemma("penyanyi", "nyanyi");
        assertLemma("penyawaan", "nyawa");

        // CC infix
        assertLemma("rerata", "rata");
        assertLemma("lelembut", "lembut");
        assertLemma("lemigas", "ligas");
        assertLemma("kinerja", "kerja");

        // plurals
        assertLemma("buku-buku", "buku");
        assertLemma("berbalas-balasan", "balas");
        assertLemma("bolak-balik", "bolak-balik");

        // combination of prefix + suffix
        assertLemma("bertebaran", "tebar");
        assertLemma("terasingkan", "asing");
        assertLemma("membangunkan", "bangun");
        assertLemma("mencintai", "cinta");
        assertLemma("menduakan", "dua");
        assertLemma("menjauhi", "jauh");
        assertLemma("menggilai", "gila");
        assertLemma("pembangunan", "bangun");

        // return the word if not found in the dictionary
        assertLemma("marwan", "marwan");
        assertLemma("subarkah", "subarkah");

        // recursively remove prefix
        assertLemma("memberdayakan", "daya");
        assertLemma("persemakmuran", "makmur");
        assertLemma("keberuntunganmu", "untung");
        assertLemma("kesepersepuluhnya", "sepuluh");

        // issues
        assertLemma("Perekonomian", "ekonomi");
        assertLemma("menahan", "tahan");

        // failed on other algorithm but we should lemmatize successfully
        assertLemma("peranan", "peran");
        assertLemma("memberikan", "beri");
        assertLemma("medannya", "medan");

        assertLemma("sebagai", "bagai");
        assertLemma("bagian", "bagian");
        assertLemma("berbadan", "badan");
        assertLemma("abdullah", "abdullah");

        // adopted foreign suffixes
        //assertLemma("budayawan", "budaya");
        //assertLemma("karyawati", "karya");
        assertLemma("idealis", "ideal");
        assertLemma("idealisme", "ideal");
        assertLemma("finalisasi", "final");

        // sastrawi additional rules
        assertLemma("mentaati", "taat");
        assertLemma("meniru-nirukan", "tiru");
        assertLemma("menyepak-nyepak", "sepak");

        assertLemma("melewati", "lewat");
        assertLemma("menganga", "nganga");

        assertLemma("kupukul", "pukul");
        assertLemma("kauhajar", "hajar");

        assertLemma("kuasa-Mu", "kuasa");
        assertLemma("nikmat-Ku", "nikmat");
        assertLemma("malaikat-malaikat-Nya", "malaikat");
    }
}
