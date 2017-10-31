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
package com.orchestral.aplore.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader {

	public static Properties readPropertyFile() throws IOException {
		final Properties myProps = new Properties();
		InputStream is = null;
		try {
			is = PropertyFileReader.class.getClassLoader().getResourceAsStream("aplore.properties");
			myProps.load(is);
		} finally {
			is.close();
		}
		return myProps;
	}
}
