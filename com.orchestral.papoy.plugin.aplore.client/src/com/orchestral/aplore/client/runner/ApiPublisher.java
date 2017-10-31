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
package com.orchestral.aplore.client.runner;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.orchestral.aplore.clinicalevent.scanner.ClinicalEventEndPoint;
import com.orchestral.aplore.dto.AploreDto;
import com.orchestral.aplore.restapi.scanner.EndPoint;
import com.orchestral.aplore.restapi.scanner.ProductDetails;
import com.orchestral.aplore.restapi.scanner.ProductVersion;
import com.orchestral.aplore.restapi.scanner.RestAPIProperties;
import com.orchestral.aplore.restapi.scanner.RestScanner;
import com.orchestral.aplore.restapi.uploader.ReportUploader;
import com.orchestral.aplore.soap.scanner.SoapEndPoint;
import com.orchestral.aplore.util.ProgressBar;

public class ApiPublisher {

	public void publisher(final Class clazz, final Class pathClass, final ClassLoader cl, final RestAPIProperties restApiProperties,
			final String bundleName) {
		try {
			System.out.println("\nStarted scanning your project for REST Apis...");
			ProgressBar.startProgressBar();
			final ProductVersion productVersion = restApiProperties.getProductVersion();
			final ProductDetails productDetails = new ProductDetails(restApiProperties.getProductName(),
					productVersion.getMajorVersion(), productVersion.getMinorVersion(),
					productVersion.getServicePackVersion(), productVersion.getMilestoneVersion(),
					restApiProperties.getJavaDocPrefix());
			// final List<EndPoint> endpoints = new
			// RestScanner().findRESTEndpoints(AploreClientConfig.rootPackage,
			// productDetails.getJavadocBaseUrl());
			final List<EndPoint> endpoints = new RestScanner().findRESTEndpointsMagic(clazz,
					productDetails.getJavadocBaseUrl(), pathClass, cl, bundleName);
			final List<SoapEndPoint> soapEndpoints = new ArrayList<>();
			// new
			// SoapScanner().findSoapEndpoints(AploreClientConfig.rootPackage,
			// productDetails.getJavadocBaseUrl());
			final List<ClinicalEventEndPoint> clinicalEventEndpoints = new ArrayList<>();
			// new ClinicalEventScanner()
			// .findClinicalEventEndPoints(AploreClientConfig.rootPackage,
			// productDetails.getJavadocBaseUrl());

			final AploreDto apisDto = new AploreDto(productDetails, endpoints, soapEndpoints, clinicalEventEndpoints);

			final Gson gson = new Gson();
			final String restApiReportJson = gson.toJson(apisDto);
			System.out.println("\n\nFollowing report is being published to Aplore: " + restApiReportJson + "\n");
			ReportUploader.publishRestApiToRepository(restApiProperties.getAploreRepository(), restApiReportJson);

			System.out.println("\nScan complete.");
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
