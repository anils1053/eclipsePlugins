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

public class ClinicalEventEndPoint {

	private String javaClass;

	private String packageName;

	private String javadocLink;

	public String getJavaClass() {
		return this.javaClass;
	}

	public void setJavaClass(final String javaClass) {
		this.javaClass = javaClass;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public void setPackageName(final String packageName) {
		this.packageName = packageName;
	}

	public String getJavadocLink() {
		return this.javadocLink;
	}

	public void setJavadocLink(final String javadocLink) {
		this.javadocLink = javadocLink;
	}

}
