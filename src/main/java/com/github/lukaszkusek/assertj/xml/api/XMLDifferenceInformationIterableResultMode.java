/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Lukasz Kusek
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.lukaszkusek.assertj.xml.api;

import com.github.lukaszkusek.xml.comparator.diff.DifferenceInformation;
import com.github.lukaszkusek.xml.comparator.diff.XMLDiff;
import org.assertj.core.api.AbstractIterableAssert;

public class XMLDifferenceInformationIterableResultMode
        extends AbstractIterableAssert<XMLDifferenceInformationIterableResultMode, Iterable<DifferenceInformation>, DifferenceInformation> {

    private XMLDiff xmlDiff;

    protected XMLDifferenceInformationIterableResultMode(XMLDiff xmlDiff, Iterable<DifferenceInformation> actual) {
        super(actual, XMLDifferenceInformationIterableResultMode.class);
        this.xmlDiff = xmlDiff;
    }

    public XMLStringIterableResultMode simpleXPaths() {
        return new XMLStringIterableResultMode(xmlDiff, xmlDiff.getSimpleXPaths());
    }

    public XMLStringIterableResultMode xPaths() {
        return new XMLStringIterableResultMode(xmlDiff, xmlDiff.getXPaths());
    }

    public XMLStringIterableResultMode messages() {
        return new XMLStringIterableResultMode(xmlDiff, xmlDiff.getMessages());
    }

    public XMLDifferenceInformationIterableResultMode differenceInformations() {
        return new XMLDifferenceInformationIterableResultMode(xmlDiff, xmlDiff.getDifferenceInformationSet());
    }
}
