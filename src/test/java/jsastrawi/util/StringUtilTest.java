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
package jsastrawi.util;

import junit.framework.TestCase;

public class StringUtilTest extends TestCase {

    public void testIsWhitespace() {
        assertTrue(StringUtil.isWhitespace(' '));
        assertTrue(StringUtil.isWhitespace('\t'));
        assertTrue(StringUtil.isWhitespace('\n'));

        assertTrue(StringUtil.isWhitespace(" "));
        assertTrue(StringUtil.isWhitespace("\t"));
        assertTrue(StringUtil.isWhitespace("\n"));
    }

    public void testGetNextWhitespace() {
        assertEquals(4, StringUtil.getNextWhitespace("Saya belajar segmentasi kalimat."));
        assertEquals(12, StringUtil.getNextWhitespace("Saya belajar segmentasi kalimat.", 5));

        // exclusive current position
        assertEquals(2, StringUtil.getNextWhitespace(" S aya", 0));
    }

    public void testHasNextWhitespace() {
        assertFalse(StringUtil.hasNextWhitespace("Saya"));
        assertTrue(StringUtil.hasNextWhitespace("Saya "));
        assertTrue(StringUtil.hasNextWhitespace("Sa y a ", 1));
        assertFalse(StringUtil.hasNextWhitespace("Saya ", 4));
    }

    public void testGetPrevWhitespace() {
        assertEquals(0, StringUtil.getPrevWhitespace(" "));
        assertEquals(10, StringUtil.getPrevWhitespace("segmentasi kalimat"));
        assertEquals(12, StringUtil.getPrevWhitespace("Saya belajar segmentasi kalimat.", 15));

        // exclusive current position
        assertEquals(4, StringUtil.getPrevWhitespace("Saya belajar segmentasi kalimat.", 12));
    }

    public void testHasPrevWhitespace() {
        assertFalse(StringUtil.hasPrevWhitespace("Saya"));
        assertTrue(StringUtil.hasPrevWhitespace(" Saya"));
        assertTrue(StringUtil.hasPrevWhitespace("Sa y a ", 5));
        assertFalse(StringUtil.hasPrevWhitespace("Sa ya ", 2));
    }

    public void testGetNextNonWhitespace() {
        assertEquals(0, StringUtil.getNextNonWhitespace("Saya belajar segmentasi kalimat."));
        assertEquals(5, StringUtil.getNextNonWhitespace("Saya belajar segmentasi kalimat.", 4));

        // exclusive current position
        assertEquals(3, StringUtil.getNextNonWhitespace(" S aya", 1));
    }

    public void testHasNextNonWhitespace() {
        assertFalse(StringUtil.hasNextNonWhitespace("   "));
        assertTrue(StringUtil.hasNextNonWhitespace("  a "));
    }

    public void testGetPrevNonWhitespace() {
        assertEquals(26, StringUtil.getPrevNonWhitespace("natural language processing"));
        assertEquals(17, StringUtil.getPrevNonWhitespace("segmentasi kalimat"));
        assertEquals(11, StringUtil.getPrevNonWhitespace("Saya belajar segmentasi kalimat.", 12));

        // exclusive current position
        assertEquals(4, StringUtil.getPrevNonWhitespace("bahasa", 5));
    }

    public void testHasPrevNonWhitespace() {
        assertFalse(StringUtil.hasPrevNonWhitespace("   "));
        assertTrue(StringUtil.hasPrevNonWhitespace("  a "));
    }
}
