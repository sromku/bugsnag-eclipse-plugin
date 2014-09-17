package com.sromku.bugsnag.providers;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.sromku.bugsnag.model.Column;
import com.sromku.bugsnag.model.ColumnInfo;
import com.sromku.bugsnag.model.Error;
import com.sromku.bugsnag.preferences.PreferencesManager;
import com.sromku.bugsnag.utils.Utils;

public class ErrorsLabelProvider implements ITableLabelProvider {

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
		ColumnInfo columnInfo = PreferencesManager.getSelectedColumns().get(columnIndex);
		return getColumnText(element, columnInfo);
	}

	public String getColumnText(Object element, ColumnInfo columnInfo) {
		Error error = (Error) element;
		Column column = columnInfo.getColumn();
		String[] classLocation = error.where.split(":");
		String clz = "";
		Integer line = -1;
		if (classLocation.length > 1) {
			clz = classLocation[0];
			try {
				line = Integer.valueOf(classLocation[1]);
			}
			catch (Exception e) {
			}
		} else {
			clz = classLocation[0];
		}

		switch (column) {
		case AFFECTED_USERS:
			return String.valueOf(error.affectedUsers);
		case APP_VERSION:
			return String.valueOf(error.appVersions);
		case CLASS:
			return clz;
		case COMMENTS:
			return String.valueOf(error.comments);
		case CREATED_DATE:
			return Utils.toDate(error.firstReceived);
		case EXCEPTION:
			return String.valueOf(error.exception);
		case IS_RESOLVED:
			return String.valueOf(error.isResolved);
		case LAST_DATE:
			return Utils.toDate(error.lastReceived);
		case LOCATION:
			return "line: " + line;
		case OCCURRENCES:
			return String.valueOf(error.occurrences);
		case RELEASE_STAGES:
			return String.valueOf(error.releaseStages);
		case SEVERITY:
			return error.severity;
		case MESSAGE:
			return error.message;
		default:
			break;
		}
		return "";
	}
}
