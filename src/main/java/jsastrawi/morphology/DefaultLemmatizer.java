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

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jsastrawi.morphology.defaultimpl.Context;
import jsastrawi.morphology.defaultimpl.visitor.VisitorProvider;

/**
 * The default implementation of Lemmatizer for Bahasa Indonesia.
 *
 * It implements several stemming algorithms:
 * <ul>
 * <li>Nazief and Adriani</li>
 * <li>Confix Stripping (CS)</li>
 * <li>Enhanced Confix Stripping (ECS)</li>
 * <li>Improved ECS</li>
 * </ul>
 *
 * Sastrawi also makes several improvements to the algorithms. Resources and
 * links to the original paper: <a
 * href="https://github.com/sastrawi/sastrawi/wiki/Resources">
 * https://github.com/sastrawi/sastrawi/wiki/Resources</a>.
 */
public class DefaultLemmatizer implements Lemmatizer {

    private final Set<String> dictionary;
    private final VisitorProvider visitorProvider;

    /**
     * Construct the default lemmatizer. The algorithms really depends on a
     * dictionary of root words.
     *
     * @param dictionary dictionary of root words (base form)
     */
    public DefaultLemmatizer(Set<String> dictionary) {
        this.dictionary = dictionary;
        this.visitorProvider = new VisitorProvider();
    }

    @Override
    public String lemmatize(String word) {
        word = word.toLowerCase();

        if (isPlural(word)) {
            return lemmatizePluralWord(word);
        } else {
            return lemmatizeSingularWord(word);
        }
    }

    /**
     * @return the dictionary
     */
    public Set<String> getDictionary() {
        return dictionary;
    }

    private boolean isPlural(String word) {
        // -ku|-mu|-nya
        // nikmat-Ku, etc
        Matcher matcher = Pattern.compile("^(.*)-(ku|mu|nya)$").matcher(word);
        if (matcher.find()) {
            return matcher.group(1).contains("-");
        }

        return word.contains("-");
    }

    private String lemmatizePluralWord(String word) {
        Matcher matcher = Pattern.compile("^(.*)-(.*)$").matcher(word);

        if (!matcher.find()) {
            return word;
        }

        String word1 = matcher.group(1);
        String word2 = matcher.group(2);

        if (word1.isEmpty() || word2.isEmpty()) {
            return word;
        }

        // malaikat-malaikat-nya -> malaikat malaikat-nya
        String suffix = word2;
        Matcher matcher2 = Pattern.compile("^(.*)-(.*)$").matcher(word1);
        if (suffix.matches("^ku|mu|nya$") && matcher2.find()) {
            word1 = matcher2.group(1);
            word2 = matcher2.group(2) + "-" + suffix;
        }

        // berbalas-balasan -> balas
        String lemma1 = lemmatizeSingularWord(word1);
        String lemma2 = lemmatizeSingularWord(word2);

        // meniru-nirukan -> tiru
        if (!dictionary.contains(word2) && lemma2.equals(word2)) {
            lemma2 = lemmatizeSingularWord("me" + word2);
        }

        if (lemma1.equals(lemma2)) {
            return lemma1;
        } else {
            return word;
        }
    }

    private String lemmatizeSingularWord(String word) {
        Context context = new Context(word, dictionary, visitorProvider);
        context.execute();

        return context.getResult();
    }

}
