/*
 * Copyright (c) Orches
tral Developments Ltd and the Orion Health group of companies (2001 - 2015).
 *
 * This document is copyright. Except for the purpose of fair reviewing, no part
 * of this publication may be reproduced or transmitted in any form or by any
 * means, electronic or mechanical, including photocopying, recording, or any
 * information storage and retrieval system, without permission in writing from
 * the publisher. Infringers of copyright render themselves liable for
 * prosecution.
 */
package com.orchestral.aplore.dto;

import java.util.List;

import com.orchestral.aplore.clinicalevent.scanner.ClinicalEventEndPoint;
import com.orchestral.aplore.restapi.scanner.EndPoint;
import com.orchestral.aplore.restapi.scanner.ProductDetails;
import com.orchestral.aplore.soap.scanner.SoapEndPoint;

public class AploreDto {

	private ProductDetails productDetails;
	private List<EndPoint> restEndPoints;
	private List<SoapEndPoint> soapEndPoints;
	private List<ClinicalEventEndPoint> clinicalEventEndPoints;

	public AploreDto(final ProductDetails productDetails, final List<EndPoint> endPoints, final List<SoapEndPoint> soapEndPoints,
			final List<ClinicalEventEndPoint> clinicalEventEndPoints) {
		this.productDetails = productDetails;
		this.restEndPoints = endPoints;
		this.soapEndPoints = soapEndPoints;
		this.clinicalEventEndPoints = clinicalEventEndPoints;
	}

	public ProductDetails getProductDetails() {
		return this.productDetails;
	}

	public void setProductDetails(final ProductDetails productDetails) {
		this.productDetails = productDetails;
	}

	public List<EndPoint> getRestEndPoints() {
		return this.restEndPoints;
	}

	public void setRestEndPoints(final List<EndPoint> restEndpoints) {
		this.restEndPoints = restEndpoints;
	}

	public ProductDetails getProductVersion() {
		return this.productDetails;
	}

	public void setProductVersion(final ProductDetails productVersion) {
		this.productDetails = productVersion;
	}

	public List<SoapEndPoint> getSoapEndPoints() {
		return this.soapEndPoints;
	}

	public void setSoapEndPoints(final List<SoapEndPoint> soapEndPoints) {
		this.soapEndPoints = soapEndPoints;
	}

	public List<ClinicalEventEndPoint> getClinicalEventEndPoints() {
		return this.clinicalEventEndPoints;
	}

	public void setClinicalEventEndPoints(final List<ClinicalEventEndPoint> clinicalEventEndPoints) {
		this.clinicalEventEndPoints = clinicalEventEndPoints;
	}
	
	@Override
	public String toString(){
		return restEndPoints.get(0).getJavaClass()+restEndPoints.get(0).getJavaMethodName();
	}

}
