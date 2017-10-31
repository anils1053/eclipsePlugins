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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import freemarker.cache.TemplateLoader;

public class ProcessorTemplateLoader implements TemplateLoader {

	@Override
	public void closeTemplateSource(final Object source) throws IOException {
		((InputStream) source).close();
	}

	@Override
	public Object findTemplateSource(final String name) throws IOException {
		return getClass().getResourceAsStream("/" + name.substring(0, name.indexOf("_")) + ".ftl");
	}

	@Override
	public long getLastModified(final Object source) {
		return 0;
	}

	@Override
	public Reader getReader(final Object source, final String encoding) throws IOException {
		return new InputStreamReader((InputStream) source, encoding);
	}
}
