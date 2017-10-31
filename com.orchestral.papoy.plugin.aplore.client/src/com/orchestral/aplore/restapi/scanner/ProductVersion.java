package com.orchestral.aplore.restapi.scanner;

public class ProductVersion {
	private String majorVersion;
	private String minorVersion;
	private String servicePackVersion;
	private String milestoneVersion;

	public ProductVersion() {
	}

	public ProductVersion(final String majorVersion, final String minorVersion, final String servicePackVersion,
			final String milestoneVersion) {
		this.majorVersion = majorVersion;
		this.minorVersion = minorVersion;
		this.servicePackVersion = servicePackVersion;
		this.milestoneVersion = milestoneVersion;
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

	public String getMilestoneVersion() {
		return this.milestoneVersion;
	}

	public void setMilestoneVersion(final String milestoneVersion) {
		this.milestoneVersion = milestoneVersion;
	}
}
