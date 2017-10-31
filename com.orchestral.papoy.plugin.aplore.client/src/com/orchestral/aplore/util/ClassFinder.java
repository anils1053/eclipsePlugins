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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class ClassFinder {

	private final Map<Class, String> absolutePaths;

	public ClassFinder() {
		this.absolutePaths = new HashMap<Class, String>();
	}

	public Map<Class, String> getAbsolutePaths() {
		return this.absolutePaths;
	}

	/**
	 * Returns all of the classes in the specified package (including sub-packages).
	 */
	public List<Class> getClasses(final String pkg) throws IOException, ClassNotFoundException {
		final ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		// turn package into the folder equivalent
		final String path = pkg.replace('.', '/');
		final Enumeration<URL> resources = classloader.getResources(path);
		final List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			final URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		final ArrayList<Class> classes = new ArrayList<Class>();
		for (final File directory : dirs) {
			classes.addAll(getClasses(directory, pkg));
		}
		return classes;
	}

	/**
	 * Returns a list of all the classes from the package in the specified directory. Calls itself recursively until no more directories are
	 * found.
	 */
	private List<Class> getClasses(final File dir, final String pkg) throws ClassNotFoundException {
		final List<Class> classes = new ArrayList<Class>();
		if (!dir.exists()) {
			return classes;
		}
		final File[] files = dir.listFiles();
		for (final File file : files) {
			if (file.isDirectory()) {
				classes.addAll(getClasses(file, pkg + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				String ClassName = pkg + '.' + file.getName().substring(0, file.getName().length() - 6);
				
				//
				
				final Class<?> clazz = Class.forName(ClassName);
				classes.add(clazz);
				this.absolutePaths.put(clazz, file.getAbsolutePath());
			}
		}
		return classes;
	}
}
