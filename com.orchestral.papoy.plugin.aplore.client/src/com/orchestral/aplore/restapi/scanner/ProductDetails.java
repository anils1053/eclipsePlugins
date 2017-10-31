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

public class ProductDetails {

	/* Uniquely identifies the API report */
	private String majorVersion;
	private String minorVersion;
	private String servicePackVersion;
	private String milestone;

	private String productName;
	private String javadocBaseUrl;

	public ProductDetails(final String productName,
			final String majorVersion,
			final String minorVersion,
			final String servicePackVersion,
			final String milestone,
			final String javadocBaseUrl) {
		this.productName = productName;
		this.majorVersion = majorVersion;
		this.minorVersion = minorVersion;
		this.servicePackVersion = servicePackVersion;
		this.milestone = milestone;
		this.javadocBaseUrl = javadocBaseUrl;
	}

	public String getMajorVersion() {
		return this.majorVersion;
	}

	public void setMajorVersion(final String majorVersion) {
		this.majorVersion = majorVersion;
	}

	public String getMinorVersion() {
		return this.minorVersion;
	}

	public void setMinorVersion(final String minorVersion) {
		this.minorVersion = minorVersion;
	}

	public String getServicePackVersion() {
		return this.servicePackVersion;
	}

	public void setServicePackVersion(final String servicePackVersion) {
		this.servicePackVersion = servicePackVersion;
	}

	public String getMilestone() {
		return this.milestone;
	}

	public void setMilestone(final String milestone) {
		this.milestone = milestone;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(final String productName) {
		this.productName = productName;
	}

	public String getJavadocBaseUrl() {
		return this.javadocBaseUrl;
	}

	public void setJavadocBaseUrl(final String javadocBaseUrl) {
		this.javadocBaseUrl = javadocBaseUrl;
	}
}
