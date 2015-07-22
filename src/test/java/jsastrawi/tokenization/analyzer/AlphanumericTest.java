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
package jsastrawi.tokenization.analyzer;

import jsastrawi.tokenization.Tokenizer;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andy
 */
public class AlphanumericTest {

    Alphanumeric analyzer;

    @Before
    public void setUp() {
        analyzer = new Alphanumeric();
    }

    /**
     * Test of isAlphanumeric method, of class Alphanumeric.
     */
    @Test
    public void testIsAlphanumeric() {
        assertTrue(Alphanumeric.isAlphanumeric('s'));
        assertFalse(Alphanumeric.isAlphanumeric('*'));
    }

    @Test
    public void testInstanceOf() {
        assertThat(analyzer, instanceOf(Analyzer.class));
    }

    @Test
    public void testAnalyze() {
        assertEquals(Analysis.SHOULD_SPLIT, analyzer.analyze(new Model("abc", 0)));
        assertEquals(Analysis.SHOULD_SPLIT, analyzer.analyze(new Model(".abc", 1)));
        assertEquals(Analysis.SKIP, analyzer.analyze(new Model("abc", 1)));
        assertEquals(Analysis.SKIP, analyzer.analyze(new Model("9a", 1)));
        assertEquals(Analysis.SHOULD_NOT_SPLIT, analyzer.analyze(new Model("bbc", 1)));
        assertEquals(Analysis.SKIP, analyzer.analyze(new Model("#$%", 1)));
    }
}
