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

import java.util.HashSet;
import java.util.Set;

public final class Hyphen implements Analyzer {

    private final static Set<Character> alphabetChars;

    static {
        String tmp = "abcdefghijklmnopqrstuvwxyz";

        alphabetChars = new HashSet();
        for (char c : tmp.toCharArray()) {
            alphabetChars.add(c);
        }
        for (char c : tmp.toUpperCase().toCharArray()) {
            alphabetChars.add(c);
        }
    }

    static boolean isAlphabet(char c) {
        return alphabetChars.contains((Character) c);
    }

    @Override
    public Analysis analyze(final Model m) {
        if (m.getCurrentChar() == '-') {
            // don't split dash
            if (m.hasNextChar() && m.getNextChar() == '-') {
                return Analysis.SHOULD_SPLIT;
            } else if (m.hasPrevChar() && isAlphabet(m.getPrevChar())
                    && m.hasNextChar() && isAlphabet(m.getNextChar())) {
                return Analysis.SHOULD_NOT_SPLIT;
            }
        }

        return Analysis.SKIP;
    }

}
