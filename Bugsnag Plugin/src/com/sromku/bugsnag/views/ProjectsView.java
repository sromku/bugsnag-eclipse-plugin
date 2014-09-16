package com.sromku.bugsnag.views;

import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.sromku.bugsnag.api.Api;
import com.sromku.bugsnag.dialog.EditAccountDialog;
import com.sromku.bugsnag.model.Account;
import com.sromku.bugsnag.model.Project;
import com.sromku.bugsnag.preferences.PreferencesManager;
import com.sromku.bugsnag.providers.ProjectsContentProvider;
import com.sromku.bugsnag.providers.ProjectsLabelProvider;

/**
 * Composite view which holds table of bugsnag accounts and projects + options
 * to manage them.
 * 
 * @author sromku
 */
public class ProjectsView extends Composite {

	private TreeViewer treeViewer;
	private Button removeButton;
	private Button editButton;

	public ProjectsView(Composite parent, int style) {
		super(parent, style);
		setLayout();
		setControl();
		setInput();
	}

	private void setInput() {
		List<Account> values = PreferencesManager.getAccounts();
		treeViewer.setInput(values.toArray());
	}

	private void setLayout() {
		this.setLayout(new FormLayout());
		FormData formData = new FormData();
		formData.bottom = new FormAttachment(100, 0);
		formData.top = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		formData.left = new FormAttachment(0, 0);
		this.setLayoutData(formData);
	}

	private void setControl() {
		// Set Add button
		Button addButton = new Button(this, SWT.NONE);
		addButton.setText("New...");
		FormData formDataAddButton = new FormData();
		formDataAddButton.height = 25;
		formDataAddButton.width = 90;
		formDataAddButton.top = new FormAttachment(0, 0);
		formDataAddButton.right = new FormAttachment(100, 0);
		addButton.setLayoutData(formDataAddButton);
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				EditAccountDialog dynamicDialog = new EditAccountDialog(getShell());
				if (dynamicDialog.open() == Window.OK) {
					Account account = dynamicDialog.getAccount();
					updateAccount(account);
				}
			}
		});

		// Set Edit button
		editButton = new Button(this, SWT.NONE);
		editButton.setText("Edit...");
		FormData formDataEditButton = new FormData();
		formDataEditButton.height = 25;
		formDataEditButton.width = 90;
		formDataEditButton.top = new FormAttachment(addButton, 5);
		formDataEditButton.right = new FormAttachment(100, 0);
		editButton.setLayoutData(formDataEditButton);
		editButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection selection = treeViewer.getSelection();
				if (!selection.isEmpty()) {
					Account account = (Account) ((StructuredSelection) selection).getFirstElement();
					EditAccountDialog dynamicDialog = new EditAccountDialog(getShell());
					dynamicDialog.setAccount(account);
					int selectedOption = dynamicDialog.open();
					if (selectedOption == Window.OK) {
						account = dynamicDialog.getAccount();
						updateAccount(account);
					}
				}
			}
		});
		editButton.setEnabled(false);

		// Set Remove button
		removeButton = new Button(this, SWT.NONE);
		removeButton.setText("Remove");
		FormData formDataRemoveButton = new FormData();
		formDataRemoveButton.height = 25;
		formDataRemoveButton.width = 90;
		formDataRemoveButton.top = new FormAttachment(editButton, 5);
		formDataRemoveButton.right = new FormAttachment(100, 0);
		removeButton.setLayoutData(formDataRemoveButton);
		removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection selection = treeViewer.getSelection();
				if (!selection.isEmpty()) {
					Account account = (Account) ((StructuredSelection) selection).getFirstElement();
					PreferencesManager.deleteAccount(account);
					setInput();
				}
			}
		});
		removeButton.setEnabled(false);

		// set table
		setTreeTable(addButton);
	}

	protected void updateAccount(Account account) {
		List<Project> projects = Api.getInstance().getProjects(account.authToken, account.id);
		for (Project project : projects) {
			project.account = account;
		}
		account.projects = projects;
		PreferencesManager.updateAccount(account);
		setInput();
	}

	private void setTreeTable(Control relativeRight) {
		// set layout
		treeViewer = new TreeViewer(this, SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER);
		FormData formData = new FormData();
		formData.bottom = new FormAttachment(100, -1);
		formData.top = new FormAttachment(0, 1);
		formData.right = new FormAttachment(relativeRight, -5);
		formData.left = new FormAttachment(0, 1);
		treeViewer.getTree().setLayoutData(formData);

		// set header
		treeViewer.getTree().setHeaderVisible(true);
		treeViewer.getTree().setLinesVisible(true);

		// create columns
		createColumns(treeViewer);

		// set provider
		treeViewer.setContentProvider(new ProjectsContentProvider());
		treeViewer.setLabelProvider(new ProjectsLabelProvider());

		// set listener
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection = (StructuredSelection) event.getSelection();
				Object element = selection.getFirstElement();
				if (element instanceof Account) {
					removeButton.setEnabled(true);
					editButton.setEnabled(true);
				} else {
					removeButton.setEnabled(false);
					editButton.setEnabled(false);
				}
			}
		});
	}

	private void createColumns(TreeViewer treeViewer) {
		// project name
		TreeViewerColumn colName = new TreeViewerColumn(treeViewer, SWT.NONE);
		colName.getColumn().setWidth(150);
		colName.getColumn().setText("Name");

		// project id
		TreeViewerColumn colKey = new TreeViewerColumn(treeViewer, SWT.NONE);
		colKey.getColumn().setWidth(100);
		colKey.getColumn().setText("API key");
	}

}
