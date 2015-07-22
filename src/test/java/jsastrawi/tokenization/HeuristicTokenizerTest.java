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
package jsastrawi.tokenization;

import junit.framework.TestCase;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.Before;
import static org.junit.Assert.*;

public class HeuristicTokenizerTest extends TestCase {

    private HeuristicTokenizer tokenizer;

    @Before
    @Override
    public void setUp() {
        tokenizer = new HeuristicTokenizer();
    }

    public void testImplementsTokenizerInterface() {
        assertThat(tokenizer, instanceOf(Tokenizer.class));
    }

    public void testSimpleWhitespace() {
        assertArrayEquals(
                new String[]{"Ini", "kalimat", "pertama", ".",
                    "Ini", "kalimat", "kedua", "."},
                tokenizer.tokenize("Ini kalimat pertama. Ini kalimat kedua.")
        );
    }

    public void testMultipleWhitespace() {
        assertArrayEquals(
                new String[]{"Budi", "belajar", "NLP", ".",
                    "Budi", "melakukan", "tokenization", "."},
                tokenizer.tokenize("Budi    belajar   NLP. Budi   melakukan   tokenization.")
        );
    }

    public void testMultipleWhitespaceWithNumeric() {
        assertArrayEquals(
                new String[]{"Budi", "membeli", "2", "buku",
                    "tentang", "Natural", "Language", "Processing", "Bahasa", "Indonesia", "."},
                tokenizer.tokenize("Budi membeli 2 buku tentang Natural Language Processing Bahasa Indonesia.")
        );
    }

    public void testPunctuation() {
        assertArrayEquals(
                new String[]{"Budi", "pergi", "ke", "pasar", ",", "membeli", "banyak",
                    "hal", ":", "sapu", ",", "payung", ",", "keramik", "."},
                tokenizer.tokenize("Budi pergi ke pasar, membeli banyak hal: sapu, payung, keramik.")
        );
    }

    public void testRepetitivePunctuation() {
        assertArrayEquals(
                new String[]{"Budi", "belajar", "NLP", "...", "Benarkah", "Budi",
                    "melakukan", "tokenization", "???"},
                tokenizer.tokenize("Budi   belajar   NLP... Benarkah Budi melakukan   tokenization???")
        );
    }

    public void testParentheses() {
        assertArrayEquals(
                new String[]{"(", "Natural", "Language", "Processing", ")", "NLP", "(",
                    "Natural", "Language", "Processing", ")", "."},
                tokenizer.tokenize("(Natural Language Processing) NLP (Natural Language Processing).")
        );
    }

    public void testQuote() {
        assertArrayEquals(
                new String[]{"\"", "(", "Saya", ")", "Aku", "akan", "belajar",
                    "NLP", ",", "\"", "kata", "Budi", "."},
                tokenizer.tokenize("\"(Saya) Aku akan belajar NLP,\" kata Budi.")
        );
    }

    public void testDash() {
        assertArrayEquals(
                new String[]{"Gadis", "itu", "--", "yang", "tadi", "tidak", "peduli",
                    "--", "kini", "menjadi", "peduli", "."},
                tokenizer.tokenize("Gadis itu--yang tadi tidak peduli--kini menjadi peduli.")
        );
    }
}
