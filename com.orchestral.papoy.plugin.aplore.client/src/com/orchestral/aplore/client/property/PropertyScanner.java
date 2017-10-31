/*
 * Copyright (c) Orchestral Developments Ltd and the Orion Health group of companies (2001 - 2016).
 *
 * This document is copyright. Except for the purpose of fair reviewing, no part
 * of this publication may be reproduced or transmitted in any form or by any
 * means, electronic or mechanical, including photocopying, recording, or any
 * information storage and retrieval system, without permission in writing from
 * the publisher. Infringers of copyright render themselves liable for
 * prosecution.
 */
package com.orchestral.aplore.client.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.orchestral.aplore.restapi.scanner.ProductVersion;
import com.orchestral.aplore.restapi.scanner.RestAPIProperties;

public class PropertyScanner {

	private static String APLORE_PROPERTIES_FILENAME = "aplore.properties";

	public static void createOrUpdateAploreProperties(final String aplorePropertiesFileBasePath, final RestAPIProperties apiProperties) {
		try {

			final File file = new File(aplorePropertiesFileBasePath + "\\" + APLORE_PROPERTIES_FILENAME);
				final Properties prop = new Properties();
				prop.setProperty("product.productName", apiProperties.getProductName());
				prop.setProperty("product.majorVersion", apiProperties.getProductVersion().getMajorVersion());
				prop.setProperty("product.minorVersion", apiProperties.getProductVersion().getMinorVersion());
				prop.setProperty("product.servicePackVersion", apiProperties.getProductVersion().getServicePackVersion());
				prop.setProperty("product.milestone", apiProperties.getProductVersion().getMilestoneVersion());
				prop.setProperty("product.buildResult.javadocUrl", apiProperties.getJavaDocPrefix());
				prop.setProperty("aplore.aploreServerHostName", apiProperties.getAploreRepository());
				final FileOutputStream fileOut = new FileOutputStream(file);
				prop.store(fileOut, "Properties used by Aplore Plugin");
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public static RestAPIProperties readVersionProperties(final String aplorePropertiesFileBasePath) {
		final RestAPIProperties apiProperties = new RestAPIProperties();
		final Properties prop = new Properties();
		InputStream input = null;
		final String aplorePropertiesPath = aplorePropertiesFileBasePath + "\\" + APLORE_PROPERTIES_FILENAME;
		try {
			final File file = new File(aplorePropertiesFileBasePath + "\\" + APLORE_PROPERTIES_FILENAME);
			if (file.exists()) {
				input = new FileInputStream(aplorePropertiesPath);

				// load a properties file
				prop.load(input);

				// get the property value and print it out
				apiProperties.setAploreRepository(prop.getProperty("aplore.aploreServerHostName"));
				apiProperties.setJavaDocPrefix(prop.getProperty("product.buildResult.javadocUrl"));
				apiProperties.setProductName(prop.getProperty("product.productName"));

				final String majorVersion = prop.getProperty("product.majorVersion");
				final String minorVersion = prop.getProperty("product.minorVersion");
				final String servicePackVersion = prop.getProperty("product.servicePackVersion");
				final String milestoneVersion = prop.getProperty("product.milestone");
				final ProductVersion productVersion = new ProductVersion(majorVersion, minorVersion, servicePackVersion, milestoneVersion);
				apiProperties.setProductVersion(productVersion);
			}
		} catch (final IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return apiProperties;
	}

}
