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
package com.orchestral.aplore.client.property;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AploreClientConfig {

	public static String productName;
	public static String majorVersion;
	public static String minorVersion;
	public static String servicePackVersion;
	public static String milestone;

	public static String rootPackage;
	public static String javaDocPrefix;
	public static String aploreRepository;

	public static void setPropertyValues() {
		try {
			// final Properties properties = PropertyFileReader.readPropertyFile();
			// productName = properties.getProperty("product.productName");
			// majorVersion = properties.getProperty("product.majorVersion");
			// minorVersion = properties.getProperty("product.minorVersion");
			// servicePackVersion = properties.getProperty("product.servicePackVersion");
			// milestone = properties.getProperty("product.milestone");
			//
			// rootPackage = properties.getProperty("product.rootPackageToScan");
			// javaDocPrefix = properties.getProperty("product.buildResult.javadocUrl");
			// aploreRepository = properties.getProperty("aplore.aploreServerHostName");

			productName = "Test";
			majorVersion = "1";
			minorVersion = "2";
			servicePackVersion = "0";
			milestone = "beta";

			rootPackage = "com.orchestral";
			javaDocPrefix = "";
			aploreRepository = "http://yizhangy-pc:8080";

			// System.out.println(productName);
			// System.out.println(majorVersion);
			// System.out.println(minorVersion);
			// System.out.println(servicePackVersion);
			// System.out.println(milestone);
			// System.out.println(rootPackage);
			// System.out.println(javaDocPrefix);
			// System.out.println(aploreRepository);

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	protected static Properties readPropertyFile() throws IOException {
		final Properties myProps = new Properties();
		InputStream is = null;
		try {
			is = AploreClientConfig.class.getClassLoader().getResourceAsStream("aplore.properties");
			myProps.load(is);
			System.out.println(myProps.get("aploreRepositoryUrl"));
		} finally {
			is.close();
		}
		return myProps;
	}
}
