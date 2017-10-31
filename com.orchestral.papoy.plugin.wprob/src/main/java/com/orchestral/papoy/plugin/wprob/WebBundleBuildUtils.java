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
package com.orchestral.papoy.plugin.wprob;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Image;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class WebBundleBuildUtils {
	private final static Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

	static {
		cfg.setTemplateLoader(new ProcessorTemplateLoader());
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	}

	public static Image loadImage(final String path) {
		try {
			return new Image(null, WebBundleBuildUtils.class.getClassLoader().getResourceAsStream(path));
		} catch (final SWTException ex) {
			throw new IllegalArgumentException("cannot find image in " + path);
		}
	}

	public static InputStream getJavascriptClassInputStream(final WebModuleMetadata metaData) {
		final Map<String, String> data = new HashMap<>();
		data.put("className", metaData.getClassName());
		data.put("yuiClassToExtends", metaData.getClassType().getYuiClassToExtends());
		data.put("namespace", metaData.getNamespace());

		return process(data, "templates/class.ftl");
	}

	private static InputStream process(final Map<String, String> data, final String templatePath) {
		/* Get the template (uses cache internally) */
		Template temp;
		try {
			temp = cfg.getTemplate(templatePath);
			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			final Writer out = new OutputStreamWriter(outputStream);
			temp.process(data, out);
			return new ByteArrayInputStream(outputStream.toByteArray());
		} catch (final IOException | TemplateException e) {
			throw new IllegalStateException("cannot process template: ", e);
		}
	}

	public static InputStream getMetaInputStream(final WebModuleMetadata metaData) {
		final Map<String, String> data = new HashMap<>();
		data.put("moduleName", metaData.getModuleName());
		data.put("requiredYuiModuleName", metaData.getClassType().getYuiModuleName());
		data.put("skinnable", Boolean.toString(metaData.isSkinnable()));

		return process(data, "templates/meta.ftl");
	}

	public static InputStream getBuildInputStream(final WebModuleMetadata metaData) {
		final Map<String, String> data = new HashMap<>();
		data.put("moduleName", metaData.getModuleName());

		return process(data, "templates/build.ftl");
	}

	public static InputStream getImportCssInputStream() {
		return process(new HashMap<String, String>(), "templates/importCss.ftl");
	}

	public static InputStream getHiveInputStream() {
		return process(new HashMap<String, String>(), "templates/hive.ftl");
	}
}
