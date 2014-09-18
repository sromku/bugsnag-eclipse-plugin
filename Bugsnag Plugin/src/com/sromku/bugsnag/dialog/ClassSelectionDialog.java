package com.sromku.bugsnag.dialog;

import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.sromku.bugsnag.providers.ClassesLabelProvider;
import com.sromku.bugsnag.providers.TableContentProvider;
import com.sromku.bugsnag.utils.WorkspaceUtils;

public class ClassSelectionDialog extends TitleAreaDialog {

	private List<IJavaElement> javaElements;
	private TableViewer tableViewer;
	private String className;
	private int line;

	public ClassSelectionDialog(Shell parentShell, List<IJavaElement> javaElements, String className, int line) {
		super(parentShell);
		this.javaElements = javaElements;
		this.className = className;
		this.line = line;
	}

	@Override
	public void create() {
		super.create();
		setTitle("Pick a class (" + className + ":" + line + ")");
		setMessage("Multiple classes with the same name are found, please pick one (double click)", IMessageProvider.NONE);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// set the internal layout of the container
		container.setLayout(new FormLayout());

		// create table showing the classes
		createTable(container);

		return area;
	}

	private void createTable(Composite parent) {
		FormData formData = new FormData();
		formData.bottom = new FormAttachment(100, -5);
		formData.top = new FormAttachment(0, 5);
		formData.right = new FormAttachment(100, -5);
		formData.left = new FormAttachment(0, 5);

		tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		tableViewer.getTable().setLayoutData(formData);

		// columns
		TableViewerColumn colPath = new TableViewerColumn(tableViewer, SWT.NONE);
		colPath.getColumn().setWidth(400);
		colPath.getColumn().setText("Path");

		TableViewerColumn colName = new TableViewerColumn(tableViewer, SWT.NONE);
		colName.getColumn().setWidth(90);
		colName.getColumn().setText("Class name");
		
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.getTable().setLinesVisible(true);

		// data
		tableViewer.setContentProvider(new TableContentProvider());
		tableViewer.setLabelProvider(new ClassesLabelProvider());
		tableViewer.setInput(javaElements);
		
		// double click
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				StructuredSelection selection = (StructuredSelection) tableViewer.getSelection();
				WorkspaceUtils.openInEditor((IJavaElement)selection.getFirstElement(), line);
				close();
			}
		});
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "Close", true);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(500, 300);
	}

	@Override
	protected void okPressed() {
		super.okPressed();
	}

}
