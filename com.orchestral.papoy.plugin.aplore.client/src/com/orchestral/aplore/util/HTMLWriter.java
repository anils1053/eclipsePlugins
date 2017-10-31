/*
 * Copyright (c) Orchestral Developments Ltd and the Orion Health group of companies (2001 - 2015).
 *
 * This document is copyright. Except for the purpose of fair reviewing, no part
 * of this publication may be reproduced or transmitted in any form or by any
 * means, electronic or mechanical, including photocopying, recording, or any
 * information storage and retrieval system, without permission in writing from
 * the publisher. Infringers of copyright render themselves liable for
 * prosecution.
 */
package com.orchestral.aplore.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import com.orchestral.aplore.clinicalevent.scanner.ClinicalEventEndPoint;
import com.orchestral.aplore.dto.AploreDto;
import com.orchestral.aplore.restapi.scanner.EndPoint;
import com.orchestral.aplore.restapi.scanner.EndpointParameter;
import com.orchestral.aplore.soap.scanner.SoapEndPoint;

public class HTMLWriter {

	private static final String NEWLINE = System.getProperty("line.separator");

	/**
	 * Writes the provided REST endpoints to an HTML file.
	 */
	public static File outputEndpointsTable(final AploreDto restApisDto, final String htmlpath) throws IOException {
		final File docfile = new File(htmlpath);
		checkHtmlAssetFiles(docfile.getAbsoluteFile().getParentFile());

		final FileWriter fstream = new FileWriter(docfile);
		final BufferedWriter out = new BufferedWriter(fstream);

		out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">" + NEWLINE);
		out.write("<html>");
		out.write("<head>");
		out.write("<style type=\"text/css\">" + NEWLINE);
		out.write("@import \"demo_page.css\";" + NEWLINE);
		out.write("@import \"header.ccss\";" + NEWLINE);
		out.write("@import \"demo_table.css\";" + NEWLINE);
		out.write("</style>" + NEWLINE);
		out.write("<script type=\"text/javascript\" charset=\"utf-8\" src=\"jquery.js\"></script>" + NEWLINE);
		out.write("<script type=\"text/javascript\" charset=\"utf-8\" src=\"jquery.dataTables.js\"></script>" + NEWLINE);
		out.write("<script type=\"text/javascript\" charset=\"utf-8\" src=\"FixedColumns.js\"></script>" + NEWLINE);
		out.write("<script type=\"text/javascript\" charset=\"utf-8\" src=\"RowGroupingWithFixedColumn.js\"></script>" + NEWLINE);
		out.write("</head>" + NEWLINE);
		out.write("<body id=\"dt_example\">" + NEWLINE);

		out.write("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"display\" id=\"soapEndpoints\">" + NEWLINE);

		out.write(NEWLINE);
		out.write(NEWLINE);
		out.write("<h1>SOAP APIs</h1>");
		out.write(NEWLINE);
		out.write("<thead><tr><th>JAVA CLASS</th><th>SOAP ENDPOINT METHOD</th><th>JAVADOC LINK</th></tr></thead>"
				+ NEWLINE);
		out.write("<tbody>" + NEWLINE);
		for (final SoapEndPoint soapEndpoint : restApisDto.getSoapEndPoints()) {
			out.write("<td>" + soapEndpoint.getJavaClass() + "</td>");
			out.write("<td>" + soapEndpoint.getJavaMethodName() + "</td>");
			out.write("<td>");
			out.write("</td>");
			out.write("<td><a href='" + soapEndpoint.getJavaDocUrl() + "'/>Javadoc</td>");
			out.write("</tr>" + NEWLINE);
		}
		out.write("</tbody>");
		out.write("</table>");

		out.write(NEWLINE);
		out.write(NEWLINE);
		out.write("<h1>REST APIs</h1>");
		out.write(NEWLINE);
		out.write("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"display\" id=\"endpoints\">" + NEWLINE);
		out.write("<thead><tr><th>URI</th><th>REST</th><th>JAVA CLASS</th><th>ENDPOINT METHOD</th><th>PARAMETERS</th><th>JAVADOC LINK</th></tr></thead>"
				+ NEWLINE);
		out.write("<tbody>" + NEWLINE);
		for (final EndPoint endpoint : restApisDto.getRestEndPoints()) {
			switch (endpoint.getMethod()) {
				case GET:
					out.write("<tr class='gradeA'>");
					break;
				case PUT:
					out.write("<tr class='gradeC'>");
					break;
				case POST:
					out.write("<tr class='gradeU'>");
					break;
				case DELETE:
					out.write("<tr class='gradeX'>");
					break;
				default:
					out.write("<tr>");
			}
			out.write("<td>" + endpoint.getUri() + "</td>");
			out.write("<td>" + endpoint.getMethod() + "</td>");
			out.write("<td>" + endpoint.getJavaClass() + "</td>");
			out.write("<td>" + endpoint.getJavaMethodName() + "</td>");
			out.write("<td>");
			for (final EndpointParameter parameter : endpoint.getPathParameters()) {
				out.write("path {" + parameter.getName() + "}  (" + parameter.getJavaType() + ")<br/>");
			}
			for (final EndpointParameter parameter : endpoint.getQueryParameters()) {
				out.write("query: " + parameter.getName() + " (" + parameter.getJavaType() + ") ");
				if (parameter.getDefaultValue() != null) {
					out.write("default = \"" + parameter.getDefaultValue() + "\"");
				}
				out.write("<br/>");
			}
			for (final EndpointParameter parameter : endpoint.getPayloadParameters()) {
				out.write("payload : " + parameter.getJavaType() + "<br/>");
			}
			out.write("</td>");
			out.write("<td><a href='" + endpoint.getJavaDocUrl() + "'/>Javadoc</td>");
			out.write("</tr>" + NEWLINE);
		}

		out.write("</tbody>");
		out.write("</table>");

		out.write(NEWLINE);
		out.write(NEWLINE);
		out.write("<h1>Clinical Event APIs</h1>");
		out.write(NEWLINE);
		out.write("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"display\" id=\"endpoints\">" + NEWLINE);
		out.write("<thead><tr><th>JAVA CLASS</th><th>PACKAGE NAME</th><th>JAVADOC LINK</th></tr></thead>"
				+ NEWLINE);
		out.write("<tbody>" + NEWLINE);
		for (final ClinicalEventEndPoint clinicalEventEndpoint : restApisDto.getClinicalEventEndPoints()) {
			out.write("<td>" + clinicalEventEndpoint.getJavaClass() + "</td>");
			out.write("<td>" + clinicalEventEndpoint.getPackageName() + "</td>");
			out.write("<td>");
			out.write("</td>");
			out.write("<td><a href='" + clinicalEventEndpoint.getJavadocLink() + "'/>Javadoc</td>");
			out.write("</tr>" + NEWLINE);
		}
		out.write("</tbody>");
		out.write("</table>");

		out.write("</body></html>");

		out.close();
		fstream.close();
		return docfile;
	}

	/**
	 * Verifies that the JS and CSS files required by the HTML table are present.
	 */
	private static void checkHtmlAssetFiles(final File directory) throws FileNotFoundException {
		if (directory.exists() == false) {
			throw new FileNotFoundException(directory.getAbsolutePath());
		}
		final File rowgroupingjs = new File(directory, "RowGroupingWithFixedColumn.js");
		if (rowgroupingjs.exists() == false) {
			throw new FileNotFoundException(rowgroupingjs.getAbsolutePath());
		}
		final File fixedcolumnsplugin = new File(directory, "FixedColumns.js");
		if (fixedcolumnsplugin.exists() == false) {
			throw new FileNotFoundException(fixedcolumnsplugin.getAbsolutePath());
		}
		final File pagecss = new File(directory, "demo_page.css");
		if (pagecss.exists() == false) {
			throw new FileNotFoundException(pagecss.getAbsolutePath());
		}
		final File tablecss = new File(directory, "demo_table.css");
		if (tablecss.exists() == false) {
			throw new FileNotFoundException(tablecss.getAbsolutePath());
		}
		final File headercss = new File(directory, "header.ccss");
		if (headercss.exists() == false) {
			throw new FileNotFoundException(headercss.getAbsolutePath());
		}
		final File datatablesjs = new File(directory, "jquery.dataTables.js");
		if (datatablesjs.exists() == false) {
			throw new FileNotFoundException(datatablesjs.getAbsolutePath());
		}
		final File jqueryjs = new File(directory, "jquery.js");
		if (jqueryjs.exists() == false) {
			throw new FileNotFoundException(jqueryjs.getAbsolutePath());
		}
	}

}
