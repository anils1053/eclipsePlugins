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
package com.orchestral.papoy.plugin.aplore.ui;

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

public class PublishRestDialog extends Dialog {

	private PublishRestMetaData metaData;

	private Text txtProductName;
	private Text txtVersion;
	private Text txtJavaDocUrl;

	public PublishRestDialog(final Shell parent) {
		super(parent);
	}

	@Override
	public Control createDialogArea(final Composite parent) {
		final Composite area = (Composite) super.createDialogArea(parent);
		final Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		final GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		createProudctNameField(container);
		createVersionInput(container);
		createJavaDocUrlInput(container);
		return container;
	}

	@Override
	public void create() {
		super.create();
		final Button buttonOK = getButton(IDialogConstants.OK_ID);
		buttonOK.setEnabled(false);

		final Listener listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				final String productName = PublishRestDialog.this.txtProductName.getText();
				final String versionName = PublishRestDialog.this.txtVersion.getText();
				final String namespace = PublishRestDialog.this.txtJavaDocUrl.getText();
				if (validateName(productName) && validateName(versionName) && validateName(namespace)) {
					buttonOK.setEnabled(true);
				} else {
					buttonOK.setEnabled(false);
				}
			}
		};
		this.txtProductName.addListener(SWT.Modify, listener);

		this.txtVersion.addListener(SWT.Modify, listener);

		this.txtJavaDocUrl.addListener(SWT.Modify, listener);
	}

	private void createProudctNameField(final Composite container) {
		final Label lb = new Label(container, SWT.NONE);
		lb.setText("Product Name");

		final GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;

		this.txtProductName = new Text(container, SWT.BORDER);
		this.txtProductName.setLayoutData(data);
	}

	private void createVersionInput(final Composite container) {
		final Label lbe = new Label(container, SWT.NONE);
		lbe.setText("Version");

		final GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		this.txtVersion = new Text(container, SWT.BORDER);
		this.txtVersion.setLayoutData(data);
	}

	private void createJavaDocUrlInput(final Composite container) {
		final Label lb = new Label(container, SWT.NONE);
		lb.setText("Java doc url");

		final GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		this.txtJavaDocUrl = new Text(container, SWT.BORDER);
		this.txtJavaDocUrl.setLayoutData(data);
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Create Javascript Module");
	}

	private void saveInput() {
		this.metaData = new PublishRestMetaData(this.txtJavaDocUrl.getText(), this.txtProductName.getText(), this.txtVersion.getText());
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

	public PublishRestMetaData getMetaData() {
		return this.metaData;
	}

	private boolean validateName(final String name) {
		 return StringUtils.isNotBlank(name) ? !StringUtils.containsWhitespace(name.trim()) : false;
	}

}
