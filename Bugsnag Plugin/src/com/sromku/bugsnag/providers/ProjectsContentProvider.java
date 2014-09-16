package com.sromku.bugsnag.providers;

import java.util.Collection;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.sromku.bugsnag.model.Account;
import com.sromku.bugsnag.model.Project;

public class ProjectsContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Object[]) {
			return (Object[]) inputElement;
		}
		if (inputElement instanceof Collection) {
			return ((Collection) inputElement).toArray();
		}
		return new Object[0];
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Account) {
			Account account = (Account) parentElement;
			return account.projects.toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof Project) {
			return ((Project) element).account;
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof Account) {
			Account account = (Account) element;
			return account.projects != null && account.projects.size() > 0;
		}
		return false;
	}

}
