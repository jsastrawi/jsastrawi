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

public final class Punctuation implements Analyzer {

    private static final Set<Character> punctuations;

    static {
        punctuations = new HashSet();
        char[] tmp = ",.;:?!\"()\'[]+=*&^%$#@~`{}\\|><".toCharArray();

        for (char c : tmp) {
            punctuations.add(c);
        }
    }

    @Override
    public Analysis analyze(final Model m) {
        if (isPunctuation(m.getCurrentChar())
                && m.hasPrevChar() && m.getCurrentChar() == m.getPrevChar()) {
            return Analysis.SHOULD_NOT_SPLIT;
        } else if (isPunctuation(m.getCurrentChar())) {
            return Analysis.SHOULD_SPLIT;
        }

        return Analysis.SKIP;
    }

    static boolean isPunctuation(char c) {
        return punctuations.contains(c);
    }

}
