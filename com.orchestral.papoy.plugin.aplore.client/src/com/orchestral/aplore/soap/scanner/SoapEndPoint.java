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


public class SoapEndPoint {

	private String javaClass;
	private String javaMethodName;
	private String javaDocUrl;

	public String getJavaClass() {
		return this.javaClass;
	}

	public void setJavaClass(final String javaClass) {
		this.javaClass = javaClass;
	}

	public String getJavaMethodName() {
		return this.javaMethodName;
	}

	public void setJavaMethodName(final String javaMethodName) {
		this.javaMethodName = javaMethodName;
	}

	public String getJavaDocUrl() {
		return this.javaDocUrl;
	}

	public void setJavaDocUrl(final String javaDocUrl) {
		this.javaDocUrl = javaDocUrl;
	}

}
