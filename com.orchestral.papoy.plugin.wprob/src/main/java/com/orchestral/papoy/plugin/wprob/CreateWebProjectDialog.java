package com.orchestral.papoy.plugin.wprob;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class CreateWebProjectDialog extends Dialog {
	private WebModuleMetadata metaData;

	private Text txtModuleName;
	private Text txtClassName;
	private Text txtNamespace;
	private Combo classTypes;
	private Button skinnable;

	public CreateWebProjectDialog(final Shell parent) {
		super(parent);
	}

	@Override
	public Control createDialogArea(final Composite parent) {
		final Composite area = (Composite) super.createDialogArea(parent);
		final Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		final GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		createModuleName(container);
		createClassTypes(container);
		createClassName(container);
		createNamespace(container);
		createSkinnable(container);
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
				final String moduleName = CreateWebProjectDialog.this.txtModuleName.getText();
				final String className = CreateWebProjectDialog.this.txtClassName.getText();
				final String namespace = CreateWebProjectDialog.this.txtNamespace.getText();
				if (validateName(moduleName) && validateName(className) && validateName(namespace)) {
					buttonOK.setEnabled(true);
				} else {
					buttonOK.setEnabled(false);
				}
			}
		};
		this.txtModuleName.addListener(SWT.Modify, listener);

		this.txtClassName.addListener(SWT.Modify, listener);

		this.txtNamespace.addListener(SWT.Modify, listener);
	}

	private void createModuleName(final Composite container) {
		final Label lbModuleName = new Label(container, SWT.NONE);
		lbModuleName.setText("YUI Module Name");

		final GridData dataModuleName = new GridData();
		dataModuleName.grabExcessHorizontalSpace = true;
		dataModuleName.horizontalAlignment = GridData.FILL;

		this.txtModuleName = new Text(container, SWT.BORDER);
		this.txtModuleName.setLayoutData(dataModuleName);
	}

	private void createClassName(final Composite container) {
		final Label lbClassName = new Label(container, SWT.NONE);
		lbClassName.setText("YUI JS Class Name");

		final GridData dataClassName = new GridData();
		dataClassName.grabExcessHorizontalSpace = true;
		dataClassName.horizontalAlignment = GridData.FILL;
		this.txtClassName = new Text(container, SWT.BORDER);
		this.txtClassName.setLayoutData(dataClassName);
	}

	private void createNamespace(final Composite container) {
		final Label lbClassName = new Label(container, SWT.NONE);
		lbClassName.setText("YUI Namespace");

		final GridData dataClassName = new GridData();
		dataClassName.grabExcessHorizontalSpace = true;
		dataClassName.horizontalAlignment = GridData.FILL;
		this.txtNamespace = new Text(container, SWT.BORDER);
		this.txtNamespace.setLayoutData(dataClassName);
	}

	private void createSkinnable(final Composite container) {
		final Label lbClassName = new Label(container, SWT.NONE);
		lbClassName.setText("Skinnable (Add Skin Css)");

		final GridData dataSkinnable = new GridData();
		dataSkinnable.grabExcessHorizontalSpace = true;
		dataSkinnable.horizontalAlignment = GridData.FILL;
		this.skinnable = new Button(container, SWT.CHECK);
		this.skinnable.setLayoutData(dataSkinnable);
	}

	private void createClassTypes(final Composite container) {
		final Label lbClassName = new Label(container, SWT.NONE);
		lbClassName.setText("YUI Class Type to Extend");

		final GridData dataSkinnable = new GridData();
		dataSkinnable.grabExcessHorizontalSpace = true;
		dataSkinnable.horizontalAlignment = GridData.FILL;
		this.classTypes = new Combo(container, SWT.DROP_DOWN);

		this.classTypes.setItems(JavascriptClassType.getNames());
		this.classTypes.setLayoutData(dataSkinnable);
		this.classTypes.select(0);
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Create Javascript Module");
	}

	private void saveInput() {
		this.metaData = new WebModuleMetadata(this.txtNamespace.getText(), this.txtModuleName.getText(), this.txtClassName.getText(),
				getClassType());
		this.metaData.setSkinnable(this.skinnable.getSelection());
	}

	private JavascriptClassType getClassType() {
		final String className = this.classTypes.getText();
		return JavascriptClassType.valueOf(className);
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

	public WebModuleMetadata getMetaData() {
		return this.metaData;
	}

	private boolean validateName(final String name) {
		return StringUtils.isNotBlank(name) ? !StringUtils.containsWhitespace(name.trim()) : false;
	}

}
