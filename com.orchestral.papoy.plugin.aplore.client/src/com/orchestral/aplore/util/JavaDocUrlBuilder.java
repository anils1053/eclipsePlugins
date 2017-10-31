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


public class JavaDocUrlBuilder {

	public static String buildJavaDocUrl(final String rootPackage, final String javadocBaseUrl, final String absolutePathOfClass) {
		try {
			String url = absolutePathOfClass.replace("\\build\\main\\classes\\", "\\");
			final int startIndex = url.indexOf(rootPackage);
			url = url.substring(startIndex);
			final int endIndex = url.indexOf(".class");
			url = url.substring(0, endIndex);
			url = javadocBaseUrl + url + ".html";
			url = url.replaceAll("\\\\", "/");
			return url;
		} catch (final Exception e) {
			return null;
		}
	}
}
