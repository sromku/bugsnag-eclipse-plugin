package com.sromku.bugsnag.dialog;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.sromku.bugsnag.api.Api;
import com.sromku.bugsnag.model.Account;

/**
 * Edit account dialog
 * 
 * @author sromku
 */
public class EditAccountDialog extends TitleAreaDialog {
	
	private Text name;
	private Text authToken;
	private Account account;

	public EditAccountDialog(Shell parentShell) {
		super(parentShell);
	}

	public Account getAccount() {
		return account;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		return init(parent);
	}

	private Control init(Composite parent) {
		setTitle("Account details");
		setMessage("Set the details of your bugsnag account.");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginLeft = 5;
		gridLayout.marginTop = 5;
		gridLayout.marginRight = 5;
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		container.setLayout(gridLayout);

		// Set Label
		name = setField("Account name", "Choose any convenient account name", container);

		// Set project id
		authToken = setField("Auth token", "", container);

		// set data
		if (account != null) {
			name.setText(account.name == null ? "" : account.name);
			authToken.setText(account.authToken == null ? "" : account.authToken);
		}

		return area;
	}

	private Text setField(String labelName, String hint, Composite container) {
		Label label = new Label(container, SWT.NONE);
		label.setText(labelName + ":");
		GridData gridData = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		label.setLayoutData(gridData);
		Text text = new Text(container, SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		text.setLayoutData(gridData);
		text.setMessage(hint);
		return text;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	protected void buttonPressed(int buttonId) {
		String authToken = this.authToken.getText();
		// do the connection testing:
		account = Api.getInstance().getAccount(authToken);
		if (account == null) {
			return;
		}
		account.authToken = this.authToken.getText();
		super.buttonPressed(buttonId);
	}

}
