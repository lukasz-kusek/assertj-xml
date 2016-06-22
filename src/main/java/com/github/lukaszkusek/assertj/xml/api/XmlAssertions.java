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

import org.apache.commons.lang.NotImplementedException;

import javax.xml.transform.TransformerException;
import java.io.IOException;

public class XmlAssertions {

    private String actualXml;

    public XmlAssertions(String actualXml) {
        this.actualXml = actualXml;
    }

    public static XmlAssertions assertThat(String actualXml) {
        return new XmlAssertions(actualXml);
    }

    public XPathMode xpath(String xPath) {
        throw new NotImplementedException();
//        return new XPathMode(actualXml, xPath);
    }

    public XMLMode usingValueExtractors(ValueExtractor... valueExtractors) {
        XMLMode xmlMode = new XMLMode(actualXml);
        xmlMode.usingValueExtractors(valueExtractors);
        return xmlMode;
    }

    public XMLMode notIgnoringNamespaces() {
        XMLMode xmlMode = new XMLMode(actualXml);
        xmlMode.notIgnoringNamespaces();
        return xmlMode;
    }

    public XMLMode ignoringNamespaces() {
        XMLMode xmlMode = new XMLMode(actualXml);
        xmlMode.ignoringNamespaces();
        return xmlMode;
    }

    public XMLMode ignoringXPaths(String... xpathsToIgnore) {
        XMLMode xmlMode = new XMLMode(actualXml);
        xmlMode.ignoringXPaths(xpathsToIgnore);
        return xmlMode;
    }

    public void isEqualTo(String xml) throws TransformerException, IOException {
        XMLMode xmlMode = new XMLMode(actualXml);
        xmlMode.isEqualTo(xml);
    }

    public XMLResultMode comparingTo(String xml) throws TransformerException, IOException {
        XMLMode xmlMode = new XMLMode(actualXml);
        return xmlMode.comparingTo(xml);
    }
}
