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

import java.util.LinkedList;
import java.util.List;
import jsastrawi.tokenization.analyzer.Alphanumeric;
import jsastrawi.tokenization.analyzer.Analysis;
import jsastrawi.tokenization.analyzer.Analyzer;
import jsastrawi.tokenization.analyzer.Hyphen;
import jsastrawi.tokenization.analyzer.Model;
import jsastrawi.tokenization.analyzer.Punctuation;
import jsastrawi.tokenization.analyzer.Whitespace;

public final class HeuristicTokenizer implements Tokenizer {

    private final List<Analyzer> analyzers;

    //private EntityFinder entityFinder;
    public HeuristicTokenizer() {

        analyzers = new LinkedList<>();
        analyzers.add(new Alphanumeric());
        analyzers.add(new Whitespace());
        analyzers.add(new Punctuation());
        analyzers.add(new Hyphen());
    }

    public List<Analyzer> getAnalyzers() {
        return analyzers;
    }

    public void addAnalyzer(Analyzer a) {
        analyzers.add(a);
    }

    @Override
    public final String[] tokenize(String text) {
        List<String> tokens = new LinkedList<>();
        StringBuilder tokenBuffer = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            Model m = new Model(text, i);

            int analysisSkip = 0;
            int analysisShoulSplit = 0;
            int analysisShouldNotSplit = 0;

            for (Analyzer analyzer : analyzers) {
                Analysis analysis = analyzer.analyze(m);

                if (analysis == Analysis.SKIP) {
                    analysisSkip++;
                } else if (analysis == Analysis.SHOULD_SPLIT) {
                    analysisShoulSplit++;
                } else if (analysis == Analysis.SHOULD_NOT_SPLIT) {
                    analysisShouldNotSplit++;
                }
            }

            if (analysisShoulSplit > 0 && analysisShoulSplit >= analysisShouldNotSplit) {
                if (m.getCurrentChar() != ' ') {
                    if (tokenBuffer.length() > 0) {
                        tokens.add(tokenBuffer.toString());
                        tokenBuffer = new StringBuilder();
                    }

                    tokenBuffer.append(m.getCurrentChar());
                } else {
                    if (tokenBuffer.length() > 0) {
                        tokens.add(tokenBuffer.toString());
                        tokenBuffer = new StringBuilder();
                    }
                }
            } else {
                tokenBuffer.append(m.getCurrentChar());
            }
        }

        if (tokenBuffer.length() > 0) {
            tokens.add(tokenBuffer.toString());
        }

        return tokens.toArray(new String[]{});
    }

}
