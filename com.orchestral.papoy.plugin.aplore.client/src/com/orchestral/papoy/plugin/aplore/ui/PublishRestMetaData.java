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
package com.orchestral.papoy.plugin.aplore.ui;

public class PublishRestMetaData {
	private String productName;
	private String version;
	private String javaDocUrl;

	public PublishRestMetaData(final String productName, final String version, final String javaDocUrl) {
		super();
		this.productName = productName;
		this.version = version;
		this.javaDocUrl = javaDocUrl;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(final String version) {
		this.version = version;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return this.productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(final String productName) {
		this.productName = productName;
	}

	/**
	 * @return the javaDocUrl
	 */
	public String getJavaDocUrl() {
		return this.javaDocUrl;
	}

	/**
	 * @param javaDocUrl the javaDocUrl to set
	 */
	public void setJavaDocUrl(final String javaDocUrl) {
		this.javaDocUrl = javaDocUrl;
	}
}
