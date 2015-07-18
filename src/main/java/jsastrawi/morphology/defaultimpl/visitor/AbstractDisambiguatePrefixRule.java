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
package jsastrawi.morphology.defaultimpl.visitor;

import java.util.Collection;
import java.util.LinkedList;
import jsastrawi.morphology.defaultimpl.Context;
import jsastrawi.morphology.defaultimpl.Removal;
import jsastrawi.morphology.defaultimpl.RemovalImpl;

/**
 * Base class to disambiguate prefix rules
 */
public abstract class AbstractDisambiguatePrefixRule implements ContextVisitor {

    protected Collection<Disambiguator> disambiguators = new LinkedList<Disambiguator>();

    @Override
    public void visit(Context context) {
        String result = null;

        for (Disambiguator disambiguator : disambiguators) {
            result = disambiguator.disambiguate(context.getCurrentWord());

            if (context.getDictionary().contains(result)) {
                break;
            }
        }

        if (null == result || result.equals(context.getCurrentWord())) {
            return;
        }

        String removedPart = context.getCurrentWord().replace(result, "");
        Removal removal = new RemovalImpl(this, context.getCurrentWord(), result, removedPart, "DP");
        context.addRemoval(removal);
        context.setCurrentWord(result);
    }

    /**
     * Add disambiguator
     * 
     * @param disambiguator disambiguator
     */
    public void addDisambiguator(Disambiguator disambiguator) {
        disambiguators.add(disambiguator);
    }

    /**
     * Add many disambiguators at once
     * 
     * @param disambiguators a collection of disambiguators
     */
    public void addDisambiguators(Collection<Disambiguator> disambiguators) {
        for (Disambiguator disambiguator : disambiguators) {
            this.disambiguators.add(disambiguator);
        }
    }
}
