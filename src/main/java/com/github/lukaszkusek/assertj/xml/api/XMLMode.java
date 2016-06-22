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

import com.github.lukaszkusek.xml.comparator.XMLComparator;
import com.github.lukaszkusek.xml.comparator.XMLComparatorBuilder;
import com.github.lukaszkusek.xml.comparator.diff.XMLDiff;
import org.assertj.core.api.Assertions;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class XMLMode {

    private final XMLComparatorBuilder builder;
    private String actualXml;

    public XMLMode(String actualXml) {
        this.actualXml = actualXml;
        builder = XMLComparator.builder().first(actualXml);
    }

    public XMLMode usingValueExtractors(ValueExtractor... valueExtractors) {
        Map<String, String> valueExtractorsAsMap =
                Arrays.asList(valueExtractors).stream()
                        .collect(
                                Collectors.toMap(
                                        ValueExtractor::xPath,
                                        ValueExtractor::pattern));
        builder.valueExtractors(valueExtractorsAsMap);
        return this;
    }

    public XMLMode notIgnoringNamespaces() {
        builder.ignoreNamespaces(false);
        return this;
    }

    public XMLMode ignoringNamespaces() {
        builder.ignoreNamespaces(true);
        return this;
    }

    public XMLMode ignoringXPaths(String... xpathsToIgnore) {
        Set<String> xpathsToIgnoreAsSet = Arrays.asList(xpathsToIgnore).stream().collect(Collectors.toSet());
        builder.xPathsToOmit(xpathsToIgnoreAsSet);
        return this;
    }

    public void isEqualTo(String expectedXml) throws TransformerException, IOException {
        builder.second(expectedXml);
        XMLDiff xmlDiff = builder.compare();
        if (xmlDiff.getDifferencesCount() != 0) {
            String message = xmlDiff.getDifferenceInformationSet().stream()
                    .map(differenceInformation -> " - [XPath: " + differenceInformation.getXPath()
                            + ", Simple XPath: " + differenceInformation.getSimpleXPath()
                            + ", Message: " + differenceInformation.getMessage()
                            + "]")
                    .collect(Collectors.joining("," + System.lineSeparator()));
            Assertions.fail("Expected '" + actualXml + "' to be equal to '" + expectedXml + "' but discrepancies were found: " + System.lineSeparator() + message);
        }
    }

    public XMLResultMode comparingTo(String expectedXml) throws TransformerException, IOException {
        builder.second(expectedXml);
        return new XMLResultMode(builder.compare());
    }
}
