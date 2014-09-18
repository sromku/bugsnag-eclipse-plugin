package com.sromku.bugsnag.dialog;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.sromku.bugsnag.model.Column;
import com.sromku.bugsnag.model.ColumnInfo;
import com.sromku.bugsnag.preferences.PreferencesManager;
import com.sromku.bugsnag.providers.TableContentProvider;
import com.sromku.bugsnag.providers.ColumnsLabelProvider;

public class ConfigureColumnsDialog extends TitleAreaDialog {

	private CheckboxTableViewer table;
	private Button upButton;
	private Button downButton;
	private Text widthText;

	public ConfigureColumnsDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		setTitle("Configure columns...");
		setMessage("Select and arrange the columns you want to see in the view");
	}

	public void setInput() {
		ArrayList<ColumnInfo> values = PreferencesManager.getSelectedColumns();
		table.setInput(values.toArray());
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// set internal layout
		container.setLayout(new FormLayout());

		// set the table to the left
		setTable(container);

		// set all buttons to the right
		setButtons(container);

		// set column width
		setWidth(container);

		// set data
		table.setContentProvider(new TableContentProvider());
		table.setLabelProvider(new ColumnsLabelProvider());

		// set checked state
		table.setCheckStateProvider(new ICheckStateProvider() {

			@Override
			public boolean isGrayed(Object element) {
				return false;
			}

			@Override
			public boolean isChecked(Object element) {
				ColumnInfo columnInfo = (ColumnInfo) element;
				return columnInfo.selected;
			}
		});

		// listen to changes
		table.addCheckStateListener(new ICheckStateListener() {

			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				ColumnInfo columnInfo = (ColumnInfo) event.getElement();
				columnInfo.selected = event.getChecked();
				PreferencesManager.updateSelectedColumn(columnInfo);
				setInput();
			}
		});

		// add selection listener
		table.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection = (StructuredSelection) event.getSelection();
				if (!selection.isEmpty()) {
					ColumnInfo columnInfo = (ColumnInfo) selection.getFirstElement();
					int index = PreferencesManager.getSelectedColumns().indexOf(columnInfo);
					if (index == 0) {
						setButtonEnabled(upButton, false);
						setButtonEnabled(downButton, true);
					} else if (index == Column.values().length - 1) {
						setButtonEnabled(upButton, true);
						setButtonEnabled(downButton, false);
					} else {
						setButtonEnabled(upButton, true);
						setButtonEnabled(downButton, true);
					}
					widthText.setEnabled(true);
					widthText.setText(String.valueOf(columnInfo.width));
				} else {
					setButtonEnabled(upButton, false);
					setButtonEnabled(downButton, false);
					widthText.setEnabled(false);
					widthText.setText("");
				}
			}
		});

		setInput();
		return area;
	}

	private void setButtonEnabled(Button button, boolean isEnabled) {
		button.setEnabled(isEnabled);
	}

	private void setTable(Composite parent) {
		table = CheckboxTableViewer.newCheckList(parent, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);
		FormData formData = new FormData();
		formData.bottom = new FormAttachment(100, -35);
		formData.top = new FormAttachment(0, 10);
		formData.right = new FormAttachment(65, 0);
		formData.left = new FormAttachment(0, 10);
		table.getTable().setLayoutData(formData);
	}

	private Composite setButtons(Composite parent) {
		Composite buttons = new Composite(parent, SWT.NONE);
		FormData buttonsFormData = new FormData();
		buttonsFormData.top = new FormAttachment(0, 10);
		buttonsFormData.right = new FormAttachment(100, -10);
		buttonsFormData.left = new FormAttachment(table.getTable(), 10);
		buttons.setLayoutData(buttonsFormData);

		// set composite layout
		buttons.setLayout(new FormLayout());

		// set up button
		upButton = new Button(buttons, SWT.NONE);
		upButton.setText("Up");
		upButton.setEnabled(false);
		setButtonLayoutData(upButton, null);

		// add selection listener
		upButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StructuredSelection structuredSelection = (StructuredSelection) table.getSelection();
				if (structuredSelection != null && !structuredSelection.isEmpty()) {
					ColumnInfo columnInfo = (ColumnInfo) structuredSelection.getFirstElement();
					ArrayList<ColumnInfo> selectedColumns = PreferencesManager.getSelectedColumns();
					int index = selectedColumns.indexOf(columnInfo);
					if (index - 1 >= 0) {
						Collections.swap(selectedColumns, index, index - 1);
						PreferencesManager.updateSelectedColumns(selectedColumns);
						setInput();
					}
				}
			}
		});

		// set down button
		downButton = new Button(buttons, SWT.NONE);
		downButton.setText("Down");
		downButton.setEnabled(false);

		// add selection listener
		downButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StructuredSelection structuredSelection = (StructuredSelection) table.getSelection();
				if (structuredSelection != null && !structuredSelection.isEmpty()) {
					ColumnInfo columnInfo = (ColumnInfo) structuredSelection.getFirstElement();
					ArrayList<ColumnInfo> selectedColumns = PreferencesManager.getSelectedColumns();
					int index = selectedColumns.indexOf(columnInfo);
					if (index + 1 < selectedColumns.size()) {
						Collections.swap(selectedColumns, index, index + 1);
						PreferencesManager.updateSelectedColumns(selectedColumns);
						setInput();
					}
				}
			}
		});

		setButtonLayoutData(downButton, upButton);
		return buttons;
	}

	private void setWidth(Composite container) {
		Composite composite = new Composite(container, SWT.NONE);
		FormData formData = new FormData();
		formData.top = new FormAttachment(table.getTable(), 10);
		formData.right = new FormAttachment(65, 0);
		formData.left = new FormAttachment(0, 10);
		formData.bottom = new FormAttachment(100, 0);
		composite.setLayoutData(formData);

		// set layout
		composite.setLayout(new FormLayout());

		// set label
		Label label = new Label(composite, SWT.NONE);
		label.setText("Column width:");
		FormData labelFormData = new FormData();
		labelFormData.top = new FormAttachment(0, 0);
		labelFormData.left = new FormAttachment(0, 0);
		label.setLayoutData(labelFormData);

		// set input
		widthText = new Text(composite, SWT.BORDER);
		FormData widthFormData = new FormData();
		widthFormData.width = 50;
		widthFormData.top = new FormAttachment(0, 0);
		widthFormData.right = new FormAttachment(100, 0);
		widthText.setLayoutData(widthFormData);
		widthText.setEnabled(false);

		// verify numbers only
		widthText.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				if (e.text == null || e.text.length() == 0) {
					return;
				}
				
				try {
					Integer.parseInt(e.text);
				}
				catch (NumberFormatException ex) {
					e.doit = false;
					return;
				}
			}
		});

		// modify listener
		widthText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				String string = widthText.getText();
				if (string != null && string.length() > 0) {
					try {
						int width = Integer.parseInt(string);
						StructuredSelection selection = (StructuredSelection) table.getSelection();
						if (selection != null && !selection.isEmpty()) {
							ColumnInfo columnInfo = (ColumnInfo) selection.getFirstElement();
							columnInfo.width = width;
							PreferencesManager.updateSelectedColumn(columnInfo);
						}
					}
					catch (NumberFormatException ex) {
						return;
					}
				}
			}
		});

	}

	private void setButtonLayoutData(Button button, Button relative) {
		FormData formData = new FormData();
		if (relative == null) {
			formData.top = new FormAttachment(0, 10);
		} else {
			formData.top = new FormAttachment(relative, 5);
		}
		formData.right = new FormAttachment(100, 0);
		formData.left = new FormAttachment(0, 0);
		button.setLayoutData(formData);
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
		return new Point(300, 400);
	}

	@Override
	protected void okPressed() {
		super.okPressed();
	}

}
