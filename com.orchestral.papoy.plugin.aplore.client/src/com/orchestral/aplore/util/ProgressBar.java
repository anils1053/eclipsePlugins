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

public class ProgressBar {
	public static void startProgressBar() {
		try {
			System.out.print("[");
			for (double progressPercentage = 0.0; progressPercentage < 1.0; progressPercentage += 0.01) {
				System.out.print(".");
				Thread.sleep(5);
			}
			System.out.print("]");
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}
}
