package com.sromku.bugsnag.providers;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.sromku.bugsnag.model.ColumnInfo;

public class ColumnsLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		ColumnInfo columnInfo = (ColumnInfo) element;
		return columnInfo.name;
	}

}
