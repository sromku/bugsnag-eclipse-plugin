package com.sromku.bugsnag.actions;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.sromku.bugsnag.Activator;
import com.sromku.bugsnag.api.Api;
import com.sromku.bugsnag.dialog.BugDetailsDialog;
import com.sromku.bugsnag.model.Error;
import com.sromku.bugsnag.model.Event;
import com.sromku.bugsnag.model.Project;
import com.sromku.bugsnag.preferences.PreferencesManager;
import com.sromku.bugsnag.views.BugsnagView;

public class DetailsAction implements IViewActionDelegate {

	private BugsnagView view;
	private Error error;

	@Override
	public void run(IAction action) {

		IStructuredSelection selection = (IStructuredSelection) view.getViewer().getSelection();
		if (selection != null) {
			if (selection.getFirstElement() != null) {
				error = (Error) selection.getFirstElement();
				if (error != null) {

					Job job = new Job("Fetching full error details") {

						@Override
						protected IStatus run(IProgressMonitor monitor) {
							Project project = PreferencesManager.getDefaultProject();
							Api network = Api.getInstance();
							final List<Event> events = network.getEvents(project.account.authToken, error.id);
							error.events = events;
							Activator.runOnUIThread(new Runnable() {
								@Override
								public void run() {
									BugDetailsDialog bugDetailsDialog = new BugDetailsDialog(view.getViewSite().getShell(), error);
									bugDetailsDialog.open();
								}
							});
							return Status.OK_STATUS;
						}
					};

					job.setUser(true);
					job.schedule();
				}
			}
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

	@Override
	public void init(IViewPart viewPart) {
		view = (BugsnagView) viewPart;
	}

}
