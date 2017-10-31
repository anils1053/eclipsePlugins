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

public class EndpointParameter {
	private ParameterType parameterType = ParameterType.PAYLOAD;
	private String javaType;
	private String defaultValue;
	private String name;

	public ParameterType getParameterType() {
		return this.parameterType;
	}

	public void setParameterType(final ParameterType parameterType) {
		this.parameterType = parameterType;
	}

	public String getJavaType() {
		return this.javaType;
	}

	public void setJavaType(final String javaType) {
		this.javaType = javaType;
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(final String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}