package com.sromku.bugsnag.providers;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class ClassesLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public void dispose() {

	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		IJavaElement iJavaElement = (IJavaElement) element;
		switch (columnIndex) {
		case 0:
			return iJavaElement.getPath().toString();
		case 1:
			return iJavaElement.getElementName();
		default:
			break;
		}
		return "";
	}

}
