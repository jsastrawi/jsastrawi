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

import jsastrawi.morphology.defaultimpl.visitor.ContextVisitor;

/**
 * Standard implementation of Removal
 */
public class RemovalImpl implements Removal {

    private final ContextVisitor visitor;

    private final String subject;

    private final String result;

    private final String removedPart;

    private final String affixType;

    /**
     * Constructor
     *
     * @param visitor visitor
     * @param subject subject
     * @param result result
     * @param removedPart removed part
     * @param affixType affix type
     */
    public RemovalImpl(ContextVisitor visitor, String subject, String result, String removedPart, String affixType) {
        this.visitor = visitor;
        this.subject = subject;
        this.result = result;
        this.removedPart = removedPart;
        this.affixType = affixType;
    }

    @Override
    public ContextVisitor getVisitor() {
        return visitor;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public String getRemovedPart() {
        return removedPart;
    }

    @Override
    public String getAffixType() {
        return affixType;
    }

}
