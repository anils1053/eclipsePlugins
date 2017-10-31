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
package com.orchestral.aplore.clinicalevent.scanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.orchestral.aplore.client.property.AploreClientConfig;
import com.orchestral.aplore.util.ClassFinder;
import com.orchestral.aplore.util.JavaDocUrlBuilder;
import com.orchestral.i12e.clinical.event.api_7_7.ClinicalEvent;

public class ClinicalEventScanner {

	public List<ClinicalEventEndPoint> findClinicalEventEndPoints(final String basepackage, final String javadocBaseUrl)
			throws Exception {
		final List<ClinicalEventEndPoint> endpoints = new ArrayList<>();

		final ClassFinder classFinder = new ClassFinder();
		final List<Class> classes = classFinder.getClasses(basepackage);

		for (final Class<?> clazz : classes) {
			if (ClinicalEvent.class.isAssignableFrom(clazz)) {
				endpoints.add(createClinicalEventEndPoint(clazz, classFinder.getAbsolutePaths(), javadocBaseUrl));
			}
		}
		return endpoints;
	}

	private ClinicalEventEndPoint createClinicalEventEndPoint(final Class<?> clazz,
			final Map<Class, String> absolutePath, final String javadocBaseUrl) {
		final ClinicalEventEndPoint newEndpoint = new ClinicalEventEndPoint();
		newEndpoint.setJavaClass(clazz.getName());
		newEndpoint.setPackageName(clazz.getPackage().getName());

		final String javadocUrl = JavaDocUrlBuilder
				.buildJavaDocUrl(AploreClientConfig.rootPackage, javadocBaseUrl, absolutePath.get(clazz));

		newEndpoint.setJavadocLink(javadocUrl);

		return newEndpoint;
	}

}
