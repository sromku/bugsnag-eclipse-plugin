package com.sromku.bugsnag.providers;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.sromku.bugsnag.model.Account;
import com.sromku.bugsnag.model.Project;

public class ProjectsLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public void dispose() {

	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		switch (columnIndex) {
		case 0:
			if (element instanceof Account) {
				Account account = (Account) element;
				return account.name;
			} else if (element instanceof Project) {
				Project project = (Project) element;
				return project.name;
			}
			break;
		case 1:
			if (element instanceof Project) {
				Project project = (Project) element;
				return project.apiKey;
			} else {
				return "";
			}
		default:
			break;
		}
		return null;
	}

}
