package com.sromku.bugsnag.actions;

import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import com.sromku.bugsnag.dialog.ClassSelectionDialog;
import com.sromku.bugsnag.model.Error;
import com.sromku.bugsnag.utils.WorkspaceUtils;

public class OpenEditorAction extends Action {

	private final TableViewer viewer;

	public OpenEditorAction(TableViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void run() {
		ISelection selection = viewer.getSelection();
		Object obj = ((IStructuredSelection) selection).getFirstElement();
		Error error = (Error) obj;
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
		List<IJavaElement> javaElements = WorkspaceUtils.search(clz);
		if (javaElements.size() > 0) {
			if (javaElements.size() == 1) {
				WorkspaceUtils.openInEditor(javaElements.get(0), line);
			} else {
				ClassSelectionDialog classSelectionDialog= new ClassSelectionDialog(viewer.getTable().getShell(), javaElements, clz, line);
				classSelectionDialog.open();
			}
		}

	}

}
