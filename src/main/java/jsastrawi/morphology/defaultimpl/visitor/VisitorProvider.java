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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import jsastrawi.morphology.defaultimpl.visitor.prefixrules.*;

/**
 * Context Visitor Provider. It provides visitors that will visit the context to
 * apply each rule.
 */
public class VisitorProvider {

    private final List<ContextVisitor> visitors;
    private final List<ContextVisitor> suffixVisitors;
    private final List<ContextVisitor> prefixVisitors;

    /**
     * Constructor
     */
    public VisitorProvider() {
        visitors = new LinkedList<>();

        suffixVisitors = new LinkedList<>();
        suffixVisitors.add(new RemoveInflectionalParticle()); // lah|kah|tah|pun
        suffixVisitors.add(new RemoveInflectionalPossessivePronoun()); // ku|mu|nya
        suffixVisitors.add(new RemoveDerivationalSuffix()); // i|kan|an

        prefixVisitors = new LinkedList<>();

        prefixVisitors.add(new RemovePlainPrefix()); // di|ke|se
        prefixVisitors.add(new PrefixDisambiguator(Arrays.asList(new Disambiguator[]{
            new PrefixRule1a(),
            new PrefixRule1b(),})));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule2()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule3()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule4()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule5()));
        prefixVisitors.add(new PrefixDisambiguator(Arrays.asList(new Disambiguator[]{
            new PrefixRule6a(),
            new PrefixRule6b()
        })));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule7()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule8()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule9()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule10()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule11()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule12()));
        prefixVisitors.add(new PrefixDisambiguator(Arrays.asList(new Disambiguator[]{
            new PrefixRule13a(),
            new PrefixRule13b()
        })));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule14()));
        prefixVisitors.add(new PrefixDisambiguator(Arrays.asList(new Disambiguator[]{
            new PrefixRule15a(),
            new PrefixRule15b()
        })));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule16()));
        prefixVisitors.add(new PrefixDisambiguator(Arrays.asList(new Disambiguator[]{
            new PrefixRule17a(),
            new PrefixRule17b(),
            new PrefixRule17c(),
            new PrefixRule17d()
        })));

        prefixVisitors.add(new PrefixDisambiguator(Arrays.asList(new Disambiguator[]{
            new PrefixRule18a(),
            new PrefixRule18b()
        })));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule19()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule20()));
        prefixVisitors.add(new PrefixDisambiguator(Arrays.asList(new Disambiguator[]{
            new PrefixRule21a(),
            new PrefixRule21b()
        })));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule23()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule24()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule25()));

        prefixVisitors.add(new PrefixDisambiguator(Arrays.asList(new Disambiguator[]{
            new PrefixRule26a(),
            new PrefixRule26b()
        })));

        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule27()));
        prefixVisitors.add(new PrefixDisambiguator(Arrays.asList(new Disambiguator[]{
            new PrefixRule28a(),
            new PrefixRule28b()
        })));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule29()));
        prefixVisitors.add(new PrefixDisambiguator(Arrays.asList(new Disambiguator[]{
            new PrefixRule30a(),
            new PrefixRule30b()
        })));
        prefixVisitors.add(new PrefixDisambiguator(Arrays.asList(new Disambiguator[]{
            new PrefixRule31a(),
            new PrefixRule31b()
        })));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule32()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule34()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule35()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule36()));
        prefixVisitors.add(new PrefixDisambiguator(Arrays.asList(new Disambiguator[]{
            new PrefixRule37a(),
            new PrefixRule37b()
        })));
        prefixVisitors.add(new PrefixDisambiguator(Arrays.asList(new Disambiguator[]{
            new PrefixRule38a(),
            new PrefixRule38b()
        })));
        prefixVisitors.add(new PrefixDisambiguator(Arrays.asList(new Disambiguator[]{
            new PrefixRule39a(),
            new PrefixRule39b()
        })));
        prefixVisitors.add(new PrefixDisambiguator(Arrays.asList(new Disambiguator[]{
            new PrefixRule40a(),
            new PrefixRule40b()
        })));

        // Sastrawi additional rules
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule41()));
        prefixVisitors.add(new PrefixDisambiguator(new PrefixRule42()));
    }

    /**
     * Get suffix visitors
     *
     * @return suffix visitors
     */
    public List<ContextVisitor> getSuffixVisitors() {
        return suffixVisitors;
    }

    /**
     * Get visitors
     *
     * @return visitors
     */
    public List<ContextVisitor> getVisitors() {
        return visitors;
    }

    /**
     * Get prefix visitors
     *
     * @return prefix visitors
     */
    public List<ContextVisitor> getPrefixVisitors() {
        return prefixVisitors;
    }
}
