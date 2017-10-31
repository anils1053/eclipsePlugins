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
package com.orchestral.aplore.restapi.scanner;

public class RestAPIProperties {

	private String aploreRepository;
	private String productName;
	private String javaDocPrefix;
	private ProductVersion productVersion = new ProductVersion();

	public RestAPIProperties() {
	}

	public RestAPIProperties(final String productName, final String javaDocPrefix, final String aploreRepository,
			final ProductVersion productVersion) {
		this.aploreRepository = aploreRepository;
		this.javaDocPrefix = javaDocPrefix;
		this.productName = productName;
		this.productVersion = productVersion;
	}

	public String getJavaDocPrefix() {
		return this.javaDocPrefix;
	}

	public void setJavaDocPrefix(final String javaDocPrefix) {
		this.javaDocPrefix = javaDocPrefix;
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

	public ProductVersion getProductVersion() {
		return this.productVersion;
	}

	public void setProductVersion(final ProductVersion productVersion) {
		this.productVersion = productVersion;
	}

	public String getAploreRepository() {
		return this.aploreRepository;
	}

	public void setAploreRepository(final String aploreRepository) {
		this.aploreRepository = aploreRepository;
	}
}
