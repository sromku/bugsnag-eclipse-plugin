package com.sromku.bugsnag.actions;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.sromku.bugsnag.Activator;
import com.sromku.bugsnag.api.Api;
import com.sromku.bugsnag.model.Error;
import com.sromku.bugsnag.views.BugsnagView;

public class RefreshAction implements IViewActionDelegate {

	private BugsnagView view;

	@Override
	public void run(IAction action) {
		getBugReports();
	}

	private void getBugReports() {
		Job job = new Job("Syncing bug reports from bugsnag") {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				monitor.beginTask("Syncing bug reports from bugsnag", 100);
				Api network = Api.getInstance();
				// TODO - 
				final List<Error> errors = network.getErrors("", "");
				Activator.runOnUIThread(new Runnable() {
					@Override
					public void run() {
						view.setData(errors);
					}
				});
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {

	}

	@Override
	public void init(IViewPart view) {
		this.view = (BugsnagView) view;
	}

}
