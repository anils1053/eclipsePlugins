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
package com.orchestral.aplore.restapi.scanner;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.orchestral.aplore.util.ClassFinder;

/**
 * Explores the Java classes in a given package, looking for annotations indicating REST endpoints.
 *
 */
public class RestScanner {

	/**
	 * Returns REST end points defined in Java classes in the specified package.
	 *
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */

	public List<EndPoint> findRESTEndpointsMagic(final Class clazz, final String javadocBaseUrl, final Class pathClass,
			final ClassLoader cl, final String bundleName)
					throws IOException, ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException,
					IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		final List<EndPoint> endpoints = new ArrayList<EndPoint>();

		final ClassFinder classFinder = new ClassFinder();

		final String basePath = getRESTEndpointPath(clazz, pathClass);
		final Method[] methods = clazz.getMethods();
		for (final Method method : methods) {
			final Class getClazz = cl.loadClass("javax.ws.rs.GET");
			final Class putClazz = cl.loadClass("javax.ws.rs.PUT");
			final Class postClazz = cl.loadClass("javax.ws.rs.POST");
			final Class deleteClazz = cl.loadClass("javax.ws.rs.DELETE");
			if (method.isAnnotationPresent(getClazz)) {
				endpoints.add(createEndpoint(method, MethodEnum.GET, clazz, basePath,
						classFinder.getAbsolutePaths(), javadocBaseUrl, cl, bundleName));
			} else if (method.isAnnotationPresent(putClazz)) {
				endpoints.add(createEndpoint(method, MethodEnum.PUT, clazz, basePath,
						classFinder.getAbsolutePaths(), javadocBaseUrl, cl, bundleName));
			} else if (method.isAnnotationPresent(postClazz)) {
				endpoints.add(createEndpoint(method, MethodEnum.POST, clazz, basePath,
						classFinder.getAbsolutePaths(), javadocBaseUrl, cl, bundleName));
			} else if (method.isAnnotationPresent(deleteClazz)) {
				endpoints.add(createEndpoint(method, MethodEnum.DELETE, clazz, basePath,
						classFinder.getAbsolutePaths(), javadocBaseUrl, cl, bundleName));
			}
		}

		return endpoints;
	}

	/**
	 * Create an endpoint object to represent the REST endpoint defined in the specified Java method.
	 */
	@SuppressWarnings("rawtypes")
	private EndPoint createEndpoint(final Method javaMethod, final MethodEnum restMethod, final Class<?> clazz,
			final String classUri, final Map<Class, String> absolutePath, final String javadocBaseUrl, final ClassLoader cl,
			final String bundleName)
					throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
					NoSuchMethodException, SecurityException {
		final EndPoint newEndpoint = new EndPoint();
		newEndpoint.setMethod(restMethod);
		newEndpoint.setJavaMethodName(javaMethod.getName());
		newEndpoint.setJavaClass(clazz.getName());

		// final String javadocUrl = JavaDocUrlBuilder.buildJavaDocUrl("com.orchestral", javadocBaseUrl,
		// absolutePath.get(clazz));
		final String javadocUrl = javadocBaseUrl + "/" + bundleName;
		System.out.println("javadocUrl" + javadocUrl);
		newEndpoint.setJavaDocUrl(javadocUrl);

		final Class pathClass = cl.loadClass("javax.ws.rs.Path");

		if (javaMethod.getAnnotation(pathClass) != null) {

			final Object pathObject = pathClass.cast(javaMethod.getAnnotation(pathClass));
			final String path = pathClass.getMethod("value").invoke(pathObject) + "";

			newEndpoint.setUri(classUri + path);
		} else {
			newEndpoint.setUri(classUri);
		}
		newEndpoint.setUri(newEndpoint.getUri().replace("//", "/"));
		discoverParameters(javaMethod, newEndpoint, cl);
		return newEndpoint;
	}

	/**
	 * Get the parameters for the specified endpoint from the provided java method.
	 *
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("rawtypes")
	private void discoverParameters(final Method method, final EndPoint endpoint, final ClassLoader cl)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		final Annotation[][] annotations = method.getParameterAnnotations();
		final Class[] parameterTypes = method.getParameterTypes();

		for (int i = 0; i < parameterTypes.length; i++) {
			final Class parameter = parameterTypes[i];
			// ignore parameters used final to access context
			final Class requestClass = cl.loadClass("javax.ws.rs.core.Request");
			final Class httpServletResponseClass = cl.loadClass("javax.servlet.http.HttpServletResponse");
			final Class httpServletRequestClass = cl.loadClass("javax.servlet.http.HttpServletRequest");
			final Class pathParamClazz = cl.loadClass("javax.ws.rs.PathParam");
			final Class queryParamClazz = cl.loadClass("javax.ws.rs.QueryParam");
			final Class defaultParamClazz = cl.loadClass("javax.ws.rs.DefaultValue");
			if ((parameter == requestClass) || (parameter == httpServletResponseClass)
					|| (parameter == httpServletRequestClass)) {
				continue;
			}

			final EndpointParameter nextParameter = new EndpointParameter();
			nextParameter.setJavaType(parameter.getName());

			final Annotation[] parameterAnnotations = annotations[i];

			for (final Annotation annotation : parameterAnnotations) {
				final String annotationName = annotation.annotationType().getName();
				if (annotationName.equals("javax.ws.rs.PathParam")) {
					nextParameter.setParameterType(ParameterType.PATH);
					final String value = (String) pathParamClazz.getMethod("value").invoke(annotation);
					nextParameter.setName(value);
				} else if (annotationName.equals("javax.ws.rs.QueryParam")) {
					nextParameter.setParameterType(ParameterType.QUERY);
					final String value = (String) queryParamClazz.getMethod("value").invoke(annotation);
					nextParameter.setName(value);
				} else if (annotationName.equals("javax.ws.rs.DefaultValue")) {
					final String value = (String) defaultParamClazz.getMethod("value").invoke(annotation);
					nextParameter.setDefaultValue(value);
				}
			}

			switch (nextParameter.getParameterType()) {
				case PATH:
					endpoint.getPathParameters().add(nextParameter);
					break;
				case QUERY:
					endpoint.getQueryParameters().add(nextParameter);
					break;
				case PAYLOAD:
					endpoint.getPayloadParameters().add(nextParameter);
					break;
			}
		}
	}

	/**
	 * Get the REST endpoint path for the specified class.
	 *
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private String getRESTEndpointPath(final Class<?> clazz, final Class pathClass)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		String path = "";
		if (clazz.getAnnotation(pathClass) != null) {
			final Object pathObject = pathClass.cast(clazz.getAnnotation(pathClass));
			path = pathClass.getMethod("value").invoke(pathObject) + path;
		}

		if (!path.endsWith("/")) {
			path = path + "/";
		}
		return path;
	}

}