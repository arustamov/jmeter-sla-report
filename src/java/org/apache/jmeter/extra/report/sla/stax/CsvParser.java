/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.apache.jmeter.extra.report.sla.stax;

import org.apache.jmeter.extra.report.sla.JMeterReportModel;
import org.apache.jmeter.extra.report.sla.parser.CSVSampleParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class CsvParser {

    private static final String JTL_HAS_HEADER_PROPERTY = "jmeter.jtl.has_header";

    private final CSVSampleParser parser;
    private boolean hasHeader;

    public CsvParser(JMeterReportModel model) {
    	parser = new CSVSampleParser(model);
	}
    public void parseCsv(Reader fis) throws IOException {
        BufferedReader in = new BufferedReader(fis);
        hasHeader = Boolean.valueOf(System.getProperty(JTL_HAS_HEADER_PROPERTY));
        try {
            parseLines(in);
        } finally {
            in.close();
        }
    }

    private void parseLines(BufferedReader in) throws IOException {
        String line = null;
        if (hasHeader) {
            line = in.readLine();
            parser.parseHeader(line);
        }
        while ((line = in.readLine()) != null) {
            parser.parse(line);
        }
    }

}