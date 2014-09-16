package com.sromku.bugsnag.providers;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.sromku.bugsnag.model.Column;
import com.sromku.bugsnag.model.ColumnInfo;
import com.sromku.bugsnag.preferences.PreferencesManager;

public class BugReportsLabelProvider implements ITableLabelProvider {

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Error error = (Error) element;
		ColumnInfo columnInfo = PreferencesManager.getSelectedColumns().get(columnIndex);
		Column column = columnInfo.getColumn();
		// TODO -
		switch (column) {
		case AFFECTED_USERS:
			break;
		case APP_VERSION:
			break;
		case CLASS:
			break;
		case COMMENTS:
			break;
		case CREATED_DATE:
			break;
		case EXCEPTION:
			break;
		case IS_RESOLVED:
			break;
		case LAST_DATE:
			break;
		case LOCATION:
			break;
		case OCCURRENCES:
			break;
		case RELEASE_STAGES:
			break;
		case SEVERITY:
			break;
		default:
			break;
		}
		return "// TODO";
	}

}
