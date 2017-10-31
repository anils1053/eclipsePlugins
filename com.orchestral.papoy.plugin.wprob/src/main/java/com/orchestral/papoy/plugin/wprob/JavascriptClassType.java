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

public enum JavascriptClassType {
	APP("app", "Y.App"),
	VIEW("view", "Y.View"),
	WIDGET("widget", "Y.Widget"),
	MODEL("model", "Y.Model"),
	MODEL_LIST("model-list", "Y.ModelList");

	private String yuiModuleName;
	private String yuiClassToExtends;

	private JavascriptClassType(final String yuiModuleName, final String yuiClassToExtends) {
		this.yuiModuleName = yuiModuleName;
		this.yuiClassToExtends = yuiClassToExtends;
	}

	/**
	 * @return the yuiModuleName
	 */
	public String getYuiModuleName() {
		return this.yuiModuleName;
	}

	/**
	 * @return the yuiClassToExtends
	 */
	public String getYuiClassToExtends() {
		return this.yuiClassToExtends;
	}

	public static String[] getNames() {
		final JavascriptClassType[] types = JavascriptClassType.values();
		final String[] names = new String[types.length];
		for (int i = 0; i < types.length; i++) {
			names[i] = types[i].name();
		}
		return names;
	}
}
