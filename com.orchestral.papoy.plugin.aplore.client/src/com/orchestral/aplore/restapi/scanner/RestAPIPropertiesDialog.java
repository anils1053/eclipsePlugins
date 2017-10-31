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
package com.orchestral.aplore.restapi.scanner;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class RestAPIPropertiesDialog extends Dialog {
	private Text productNameField;
	private Text javaDocPrefixField;
	private Text aploreRepositoryField;
	private Text majorVersionField;
	private Text minorVersionField;
	private Text servicePackVersionField;
	private Text milestoneVersionField;
	private final RestAPIProperties properties;

	public RestAPIPropertiesDialog(final Shell parentShell, final RestAPIProperties properties) {
		super(parentShell);
		this.properties = properties;
	}

	@Override
	public Control createDialogArea(final Composite parent) {
		final Composite area = (Composite) super.createDialogArea(parent);
		final Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		final GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);
		createProductNameFieldSection(container);
		createJavaDocPrefixFieldSection(container);
		createAploreRepositoryFieldSection(container);
		createMajorVersionFieldSection(container);
		createMinorVersionFieldSection(container);
		createServicePackVersionFieldSection(container);
		createMilestoneVersionFieldSection(container);
		return container;
	}

	@Override
	public void create() {
		super.create();
		final Button buttonOK = getButton(IDialogConstants.OK_ID);
		buttonOK.setEnabled(canPublishRestApi());

		final Listener listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				buttonOK.setEnabled(canPublishRestApi());
			}
		};
		this.productNameField.addListener(SWT.Modify, listener);
		this.aploreRepositoryField.addListener(SWT.Modify, listener);
		this.javaDocPrefixField.addListener(SWT.Modify, listener);
		this.majorVersionField.addListener(SWT.Modify, listener);
		this.minorVersionField.addListener(SWT.Modify, listener);
		this.servicePackVersionField.addListener(SWT.Modify, listener);
		this.milestoneVersionField.addListener(SWT.Modify, listener);
	}

	private boolean canPublishRestApi() {
		final String productName = this.productNameField.getText();
		final String aploreRepository = this.aploreRepositoryField.getText();
		final String javaDocPrefix = this.javaDocPrefixField.getText();
		final String majorVersion = this.majorVersionField.getText();
		final String minorVersion = this.minorVersionField.getText();
		final String servicePackVersion = this.servicePackVersionField.getText();
		final String milestoneVersion = this.milestoneVersionField.getText();
		if (StringUtils.isBlank(productName) ||
				StringUtils.isBlank(aploreRepository) ||
				StringUtils.isBlank(javaDocPrefix) ||
				StringUtils.isBlank(majorVersion) ||
				StringUtils.isBlank(minorVersion) ||
				StringUtils.isBlank(servicePackVersion) ||
				StringUtils.isBlank(milestoneVersion)) {
			return false;
		} else {
			return true;
		}
	}

	private void createProductNameFieldSection(final Composite container) {
		createLable(container, "Product Name");
		this.productNameField = new Text(container, SWT.BORDER);
		this.productNameField.setText(StringUtils.trimToEmpty(this.properties.getProductName()));
		updateTxtFieldLayout(this.productNameField);
	}

	private void createJavaDocPrefixFieldSection(final Composite container) {
		createLable(container, "JavaDoc Base URL");
		this.javaDocPrefixField = new Text(container, SWT.BORDER);
		this.javaDocPrefixField.setText(StringUtils.trimToEmpty(this.properties.getJavaDocPrefix()));
		updateTxtFieldLayout(this.javaDocPrefixField);
	}

	private void createAploreRepositoryFieldSection(final Composite container) {
		createLable(container, "Aplore Repository");
		this.aploreRepositoryField = new Text(container, SWT.BORDER);
		this.aploreRepositoryField.setText(StringUtils.trimToEmpty(this.properties.getAploreRepository()));
		updateTxtFieldLayout(this.aploreRepositoryField);
	}

	private void createMajorVersionFieldSection(final Composite container) {
		createLable(container, "Major Version");
		this.majorVersionField = new Text(container, SWT.BORDER);
		this.majorVersionField.setText(StringUtils.trimToEmpty(this.properties.getProductVersion().getMajorVersion()));
		updateTxtFieldLayout(this.majorVersionField);
	}

	private void createMinorVersionFieldSection(final Composite container) {
		createLable(container, "Minor Version");
		this.minorVersionField = new Text(container, SWT.BORDER);
		this.minorVersionField.setText(StringUtils.trimToEmpty(this.properties.getProductVersion().getMinorVersion()));
		updateTxtFieldLayout(this.minorVersionField);
	}

	private void createServicePackVersionFieldSection(final Composite container) {
		createLable(container, "Service Pack version");
		this.servicePackVersionField = new Text(container, SWT.BORDER);
		this.servicePackVersionField.setText(StringUtils.trimToEmpty(this.properties.getProductVersion().getServicePackVersion()));
		updateTxtFieldLayout(this.servicePackVersionField);
	}

	private void createMilestoneVersionFieldSection(final Composite container) {
		createLable(container, "Milestone Version");
		this.milestoneVersionField = new Text(container, SWT.BORDER);
		this.milestoneVersionField.setText(StringUtils.trimToEmpty(this.properties.getProductVersion().getMilestoneVersion()));
		updateTxtFieldLayout(this.milestoneVersionField);
	}

	private void createLable(final Composite container, final String labelTxt) {
		final Label label = new Label(container, SWT.NONE);
		label.setText(labelTxt);
	}

	private void updateTxtFieldLayout(final Text field) {
		final GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		field.setLayoutData(data);
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Configure Aplore Properties");
	}

	private void saveInput() {
		final String productName = StringUtils.trimToNull(this.productNameField.getText());
		final String javaDocPrefix = StringUtils.trimToNull(this.javaDocPrefixField.getText());
		final String aploreRepo = StringUtils.trimToNull(this.aploreRepositoryField.getText());
		final String major = StringUtils.trimToNull(this.majorVersionField.getText());
		final String minor = StringUtils.trimToNull(this.minorVersionField.getText());
		final String servicePack = StringUtils.trimToNull(this.servicePackVersionField.getText());
		final String milestone = StringUtils.trimToNull(this.milestoneVersionField.getText());
		this.properties.setProductName(productName);
		this.properties.setJavaDocPrefix(javaDocPrefix);
		this.properties.setAploreRepository(aploreRepo);
		final ProductVersion version = new ProductVersion(major, minor, servicePack, milestone);
		this.properties.setProductVersion(version);
	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	public RestAPIProperties getProperties() {
		return this.properties;
	}

}
