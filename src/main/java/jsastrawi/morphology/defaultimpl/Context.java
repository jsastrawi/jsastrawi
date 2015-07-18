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
package jsastrawi.morphology.defaultimpl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import jsastrawi.morphology.defaultimpl.confixstripping.PrecedenceAdjustmentSpec;
import jsastrawi.morphology.defaultimpl.visitor.ContextVisitor;
import jsastrawi.morphology.defaultimpl.visitor.VisitorProvider;

/**
 * Encapsulated context in lemmatizing a word
 */
public class Context {

    private final String originalWord;
    private String currentWord;
    private final Set<String> dictionary;
    private final VisitorProvider visitorProvider;
    private List<Removal> removals;
    private String result;
    private final List<ContextVisitor> visitors;
    private final List<ContextVisitor> suffixVisitors;
    private final List<ContextVisitor> prefixVisitors;
    private boolean processIsStopped;

    /**
     * Constructor
     *
     * @param originalWord original word
     * @param dictionary dictionary of root words
     * @param visitorProvider visitor provider
     */
    public Context(String originalWord, Set<String> dictionary, VisitorProvider visitorProvider) {
        this.originalWord = originalWord;
        this.currentWord = this.originalWord;
        this.dictionary = dictionary;
        this.visitorProvider = visitorProvider;
        this.removals = new LinkedList<>();

        this.visitors = visitorProvider.getVisitors();
        this.suffixVisitors = visitorProvider.getSuffixVisitors();
        this.prefixVisitors = visitorProvider.getPrefixVisitors();
    }

    /**
     * Get original word of which to find the lemma
     *
     * @return original word
     */
    public String getOriginalWord() {
        return originalWord;
    }

    /**
     * Set the current state of the word in the lemmatization process
     *
     * @param currentWord current word
     */
    public void setCurrentWord(String currentWord) {
        this.currentWord = currentWord;
    }

    /**
     * Get current word in the middle of lemmatization process
     *
     * @return current word
     */
    public String getCurrentWord() {
        return currentWord;
    }

    /**
     * Add removal to the collection for later use
     *
     * @param r removal
     */
    public void addRemoval(Removal r) {
        removals.add(r);
    }

    /**
     * Get removals
     *
     * @return removals
     */
    public List<Removal> getRemovals() {
        return removals;
    }

    /**
     * Get the lemma as a result of the lemmatization process
     *
     * @return result
     */
    public String getResult() {
        return result;
    }

    /**
     * Execute lemmatization process
     */
    public void execute() {
        // step 1 - 5
        startStemmingProcess();

        // step 6
        if (dictionary.contains(currentWord)) {
            result = currentWord;
        } else {
            result = originalWord;
        }
    }

    private void startStemmingProcess() {

        // step 1
        if (dictionary.contains(currentWord)) {
            return;
        }

        if (currentWord.length() <= 3) {
            return;
        }

        acceptVisitors(visitors);

        if (dictionary.contains(currentWord)) {
            return;
        }

        PrecedenceAdjustmentSpec spec = new PrecedenceAdjustmentSpec();

        /*
         * Confix Stripping
         * Try to remove prefix before suffix if the specification is met
         */
        if (spec.isSatisfiedBy(originalWord)) {
            // step 4, 5
            removePrefixes();
            if (dictionary.contains(currentWord)) {
                return;
            }

            // step 2, 3
            removeSuffixes();
            if (dictionary.contains(currentWord)) {
                return;
            } else {
                // if the trial is failed, restore the original word
                // and continue to normal rule precedence (suffix first, prefix afterwards)
                setCurrentWord(originalWord);
                removals.clear();
            }
        }

        // step 2, 3
        removeSuffixes();
        if (dictionary.contains(currentWord)) {
            return;
        }

        // step 4, 5
        removePrefixes();
        if (dictionary.contains(currentWord)) {
            return;
        }

        loopPengembalianAkhiran();
    }

    private String acceptVisitors(List<ContextVisitor> visitors) {
        for (ContextVisitor visitor : visitors) {
            accept(visitor);

            if (dictionary.contains(currentWord)) {
                return currentWord;
            }

            if (processIsStopped) {
                return currentWord;
            }
        }

        return currentWord;
    }

    private void accept(ContextVisitor visitor) {
        visitor.visit(this);
    }

    private void removeSuffixes() {
        acceptVisitors(suffixVisitors);
    }

    private void removePrefixes() {
        for (int i = 0; i < 3; i++) {
            acceptPrefixVisitors(prefixVisitors);
            if (dictionary.contains(currentWord)) {
                return;
            }
        }
    }

    private void acceptPrefixVisitors(List<ContextVisitor> prefixVisitors) {
        int removalCount = removals.size();

        for (ContextVisitor visitor : prefixVisitors) {
            accept(visitor);

            if (dictionary.contains(currentWord)) {
                return;
            }

            if (processIsStopped) {
                return;
            }

            if (removals.size() > removalCount) {
                return;
            }
        }
    }

    /**
     * Get dictionary
     *
     * @return dictionary
     */
    public Set<String> getDictionary() {
        return dictionary;
    }

    private void loopPengembalianAkhiran() {
        // restore prefix to form [DP+[DP+[DP]]] + Root word
        restorePrefix();

        List<Removal> originalRemovals = removals;
        LinkedList<Removal> reversedRemovals = new LinkedList<>(removals);
        Collections.reverse(reversedRemovals);
        String originalCurrentWord = currentWord;

        for (Removal removal : reversedRemovals) {
            if (!isSuffixRemoval(removal)) {
                continue;
            }

            if (removal.getRemovedPart().equals("kan")) {
                setCurrentWord(removal.getResult() + "k");

                // step 4, 5
                removePrefixes();
                if (dictionary.contains(currentWord)) {
                    return;
                }

                setCurrentWord(removal.getResult() + "kan");
            } else {
                setCurrentWord(removal.getSubject());
            }

            // step 4, 5
            removePrefixes();
            if (dictionary.contains(currentWord)) {
                return;
            }

            this.removals = originalRemovals;
            setCurrentWord(originalCurrentWord);
        }
    }

    private void restorePrefix() {
        for (Removal removal : removals) {
            if (removal.getAffixType().equals("DP")) {
                setCurrentWord(removal.getSubject());
                break;
            }
        }

        ListIterator<Removal> iter = removals.listIterator();
        while (iter.hasNext()) {
            if (iter.next().getAffixType().equals("DP")) {
                iter.remove();
            }
        }
    }

    private boolean isSuffixRemoval(Removal removal) {
        return removal.getAffixType().equals("DS")
                || removal.getAffixType().equals("PP")
                || removal.getAffixType().equals("P");
    }
}
