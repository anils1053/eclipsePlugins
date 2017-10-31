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

public class WebModuleMetadata {
	private final String namespace;
	private final String moduleName;
	private final String className;
	private final JavascriptClassType classType;
	private boolean skinnable;

	public WebModuleMetadata(final String namespace, final String moduleName, final String className,
			final JavascriptClassType classType) {
		super();
		this.namespace = namespace;
		this.moduleName = moduleName;
		this.className = className;
		this.classType = classType;
	}

	/**
	 * @return the namespace
	 */
	public String getNamespace() {
		return namespace;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public String getClassName() {
		return this.className;
	}

	public void setSkinnable(final boolean skinnable) {
		this.skinnable = skinnable;
	}

	/**
	 * @return the skinnable
	 */
	public boolean isSkinnable() {
		return this.skinnable;
	}

	/**
	 * @return the classType
	 */
	public JavascriptClassType getClassType() {
		return this.classType;
	}
}
