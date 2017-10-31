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
package com.orchestral.aplore.restapi.uploader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.orchestral.aplore.util.ProgressBar;

public class ReportUploader {

	public static void publishRestApiToRepository(final String hostname, final String reportJson) {

		try {

			final String contextPath = "/aplore/restws/publish";
			final URL targetUrl = new URL(hostname + contextPath);
			System.out.println("\nAplore server Url is:" + targetUrl);
			System.out.println("\nPublishing to Aplore central repository..");
			ProgressBar.startProgressBar();

			final HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");

			final OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(reportJson.getBytes());
			outputStream.flush();

			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ httpConnection.getResponseCode());
			}

			final BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
					(httpConnection.getInputStream())));

			String output;
			System.out.println("\nOutput from Server:\n");
			while ((output = responseBuffer.readLine()) != null) {
				System.out.println("Report uploaded. Id of the report is:" + output);
			}

			httpConnection.disconnect();

		} catch (final MalformedURLException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}
}
