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

import java.util.ArrayList;
import java.util.List;

public class EndPoint {
	private String uri;
	private MethodEnum method;

	private String javaClass;
	private String javaMethodName;
	private String javaDocUrl;

	private final List<EndpointParameter> queryParameters = new ArrayList<EndpointParameter>();
	private final List<EndpointParameter> pathParameters = new ArrayList<EndpointParameter>();
	private final List<EndpointParameter> payloadParameters = new ArrayList<EndpointParameter>();

	public String getUri() {
		return this.uri;
	}

	public void setUri(final String uri) {
		this.uri = uri;
	}

	public MethodEnum getMethod() {
		return this.method;
	}

	public void setMethod(final MethodEnum method) {
		this.method = method;
	}

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

	public List<EndpointParameter> getQueryParameters() {
		return this.queryParameters;
	}

	public List<EndpointParameter> getPathParameters() {
		return this.pathParameters;
	}

	public List<EndpointParameter> getPayloadParameters() {
		return this.payloadParameters;
	}

	public String getJavaDocUrl() {
		return javaDocUrl;
	}

	public void setJavaDocUrl(String javaDocUrl) {
		this.javaDocUrl = javaDocUrl;
	}

}
