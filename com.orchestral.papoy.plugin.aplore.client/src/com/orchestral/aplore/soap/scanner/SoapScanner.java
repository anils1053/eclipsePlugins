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
package com.orchestral.aplore.soap.scanner;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.orchestral.aplore.client.property.AploreClientConfig;
import com.orchestral.aplore.util.ClassFinder;
import com.orchestral.aplore.util.JavaDocUrlBuilder;

/**
 * Explores SOAP classes in a given package, looking for annotations indicating SOAP endpoints.
 *
 */
public class SoapScanner {

	/**
	 * Returns SOAP end points defined in Java classes in the specified package.
	 */
	@SuppressWarnings("rawtypes")
	public List<SoapEndPoint> findSoapEndpoints(final String basepackage, final String javadocBaseUrl) throws IOException,
			ClassNotFoundException {
		final List<SoapEndPoint> endpoints = new ArrayList<>();

		final ClassFinder classFinder = new ClassFinder();
		final List<Class> classes = classFinder.getClasses(basepackage);

		for (final Class<?> clazz : classes) {
			final Annotation annotation = clazz.getAnnotation(WebService.class);
			if (annotation != null) {

				final Method[] methods = clazz.getMethods();
				for (final Method method : methods) {
					endpoints.add(createSoapEndpoint(method, clazz, classFinder.getAbsolutePaths(), javadocBaseUrl));
				}
			}
		}

		return endpoints;
	}

	/**
	 * Create an endpoint object to represent the SOAP endpoint defined in the specified Java method.
	 */
	@SuppressWarnings("rawtypes")
	private SoapEndPoint createSoapEndpoint(final Method javaMethod, final Class<?> clazz, final Map<Class, String> absolutePath,
			final String javadocBaseUrl) {
		final SoapEndPoint newEndpoint = new SoapEndPoint();
		newEndpoint.setJavaMethodName(javaMethod.getName());
		newEndpoint.setJavaClass(clazz.getName());

		final String javadocUrl = JavaDocUrlBuilder.buildJavaDocUrl(AploreClientConfig.rootPackage, javadocBaseUrl,
				absolutePath.get(clazz));
		newEndpoint.setJavaDocUrl(javadocUrl);

		return newEndpoint;
	}

}