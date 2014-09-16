package com.sromku.bugsnag.actions;

import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

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
		String cls = error.where.split("\\.")[0];
		Integer line = 0;
		if (error.where.contains(":")) {
			line = Integer.valueOf(error.where.split(":")[1]);
		}
		List<IJavaElement> javaElement = WorkspaceUtils.search(cls);
		if (javaElement.size() > 0) {
			// TODO - open first, but need to be selected from dialog
			WorkspaceUtils.openInEditor(javaElement.get(0), line);
		}

	}

}
