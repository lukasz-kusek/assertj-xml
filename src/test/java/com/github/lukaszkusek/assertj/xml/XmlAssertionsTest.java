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

package com.github.lukaszkusek.assertj.xml;

import org.junit.Test;

import javax.xml.transform.TransformerException;
import java.io.IOException;

import static com.github.lukaszkusek.assertj.xml.api.ValueExtractor.valueExtractor;
import static com.github.lukaszkusek.assertj.xml.api.XmlAssertions.assertThat;

public class XmlAssertionsTest {

    @Test
    public void shouldSucceedComparingTheSameXml() throws TransformerException, IOException {
        // given
        String xml = "<a></a>";

        // when
        assertThat(xml).isEqualTo(xml);
    }

    @Test
    public void shouldFailComparingFileWithAndWithoutNamespace() throws TransformerException, IOException {
        // given
        String xml1 = ResourceReader.getFileContent("withNamespaces1.xml");
        String xml2 = ResourceReader.getFileContent("withoutNamespaces.xml");

        // when
        assertThat(xml1)
                .notIgnoringNamespaces()
                .comparingTo(xml2)
                .messages()
                .containsOnly("First root node: /sch:Preference is different from second root node: /Preference");
    }

    @Test
    public void shouldCompareFileWithAndWithoutNamespaces() throws TransformerException, IOException {
        // given
        String xml1 = ResourceReader.getFileContent("withNamespaces1.xml");
        String xml2 = ResourceReader.getFileContent("withoutNamespaces.xml");

        // when
        assertThat(xml1)
                .comparingTo(xml2)
                .xPaths()
                .containsOnly(
                        "/Preference/Airport[3]/@Level",
                        "/Preference/Cabin[1]/Info/@Vendor",
                        "/Preference/Airline[1]",
                        "/Preference/Airport[1]/@Exclude",
                        "/Preference/Aggregator[2]",
                        "/Preference/Meal/Info/@MealService");
    }

    @Test
    public void shouldCompareBothFilesWithNamespacesIgnoringNamespaces() throws TransformerException, IOException {
        // given
        String xml1 = ResourceReader.getFileContent("withNamespaces1.xml");
        String xml2 = ResourceReader.getFileContent("withNamespaces2.xml");

        // when
        assertThat(xml1)
                .comparingTo(xml2)
                .xPaths()
                .containsOnly(
                        "/Preference/Aggregator[2]",
                        "/Preference/Airport[3]/@Level",
                        "/Preference/Airport[1]/@Exclude"
                );
    }

    @Test
    public void shouldCompareBothFilesWithNamespacesNotIgnoringNamespaces() throws TransformerException, IOException {
        // given
        String xml1 = ResourceReader.getFileContent("withNamespaces1.xml");
        String xml2 = ResourceReader.getFileContent("withNamespaces2.xml");

        // when
        assertThat(xml1)
                .notIgnoringNamespaces()
                .comparingTo(xml2)
                .xPaths()
                .containsOnly(
                        "/sch:Preference/sch:Aggregator[2]",
                        "/sch:Preference/sch:Airport[3]/@Level",
                        "/sch:Preference/sch:Airport[1]/@Exclude"
                );
    }

    @Test
    public void shouldExtractValueUsingExtractorPatternAndResultInIdentical() throws TransformerException, IOException {
        // given
        String xml1 = ResourceReader.getFileContent("withTimestamp1.xml");
        String xml2 = ResourceReader.getFileContent("withTimestamp2.xml");

        // when
        assertThat(xml1)
                .usingValueExtractors(valueExtractor("/Response/Error", "[^:]*:(.*)"))
                .isEqualTo(xml2);
    }

    @Test
    public void shouldCompareXMLs() throws TransformerException, IOException {
        // given
        String xml1 = ResourceReader.getFileContent("toCompare1.xml");
        String xml2 = ResourceReader.getFileContent("toCompare2.xml");

        // when
        assertThat(xml1)
                .comparingTo(xml2)
                .xPaths()
                .containsOnly(
                        "/Response/Profiles/ProfileInfo/Profile/Customer/PersonName/GivenName/text()",
                        "/Response/Profiles/ProfileInfo/Profile/Customer/PersonName/Surname/text()",
                        "/Response/Profiles/ProfileInfo/Profile/Customer/Telephone[1]",
                        "/Response/Profiles/ProfileInfo/Profile/Customer/Telephone[2]/@PhoneType",
                        "/Response/Profiles/ProfileInfo/Profile/Customer/EmployeeInfo/@CompanyProfileId",
                        "/Response/Profiles/ProfileInfo/Profile/Customer/EmployeeInfo/@EmployeeTitle",
                        "/Response/Profiles/ProfileInfo/Profile/Customer/EmployeeInfo/@CompanyName",
                        "/Response/Profiles/ProfileInfo/Profile/TPA_ProfileExtensions/CustLoyaltyTotals/@AccountType",
                        "/Response/Profiles/ProfileInfo/Profile/TPA_ProfileExtensions/SpecialDate/@Description",
                        "/Response/Profiles/ProfileInfo/Profile/TPA_ProfileExtensions/CustDefinedData",
                        "/Response/Profiles/ProfileInfo/Profile/TPA_ProfileExtensions/VITCustomer"
                );
    }

}
