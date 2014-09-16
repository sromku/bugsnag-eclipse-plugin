package com.sromku.bugsnag.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.part.ViewPart;

import com.sromku.bugsnag.Activator;
import com.sromku.bugsnag.actions.OpenEditorAction;
import com.sromku.bugsnag.dialog.ConfigureColumnsDialog;
import com.sromku.bugsnag.model.Account;
import com.sromku.bugsnag.model.ColumnInfo;
import com.sromku.bugsnag.model.Project;
import com.sromku.bugsnag.preferences.PreferencesManager;
import com.sromku.bugsnag.preferences.PreferencesManager.OnChangeListener;
import com.sromku.bugsnag.providers.BugReportsContentProvider;
import com.sromku.bugsnag.providers.BugReportsLabelProvider;
import com.sromku.bugsnag.utils.SelectionListenersStore;

/**
 * @author sromku
 */
public class BugsnagView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.sromku.bugsnag.views.BugsnagView";

	private TableViewer viewer;
	private Action doubleClickAction;

	private Object data;

	private BugReportsLabelProvider bugReportsLabelProvider;

	/**
	 * The constructor.
	 */
	public BugsnagView() {
		PreferencesManager.addOnChangeListener(new OnChangeListener() {
			@Override
			public void onChanged() {
				createColumns();
				viewer.setInput(data);
			}
		});
	}

	/**
	 * Create the view itself
	 */
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		// create columns
		createColumns();

		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new BugReportsContentProvider());
		bugReportsLabelProvider = new BugReportsLabelProvider();
		viewer.setLabelProvider(bugReportsLabelProvider);
		// viewer.setSorter(new NameSorter());

		makeActions();
		contributeToActionBars();
	}

	public void setData(Object object) {
		this.data = object;
		viewer.setInput(object);
	}

	/**
	 * Create and set actions
	 */
	private void makeActions() {
		doubleClickAction = new OpenEditorAction(viewer);
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		IMenuManager menuManager = bars.getMenuManager();

		// Submenu of all projects
		final MenuManager menuProjects = new MenuManager("Projects");
		menuProjects.addMenuListener(new IMenuListener() {

			@Override
			public void menuAboutToShow(IMenuManager manager) {
				manager.removeAll();
				Project selectedProjectApi = PreferencesManager.getDefaultProject();
				List<Project> values = new ArrayList<Project>();
				ArrayList<Account> accounts = PreferencesManager.getAccounts();
				for (Account account : accounts) {
					values.addAll(account.projects);
				}
				if (values.size() > 0) {
					for (Project project : values) {
						menuProjects.add(new ToggleAction(project, project.equals(selectedProjectApi)));
					}
				} else {
					menuProjects.add(new AddNewProject());
				}
			}
		});

		// hack: have to add this shit, because this will make the real menu to
		// be shown.
		menuProjects.add(new Action("temp") {
		});

		// add submenu
		menuManager.add(menuProjects);

		// add separtor
		menuManager.add(new Separator());

		// add configure columns
		menuManager.add(new SelectColumnsAction());
	}

	public class ToggleAction extends Action {

		private Project project;

		public ToggleAction(Project projectModel, boolean selected) {
			super(projectModel.name);
			this.project = projectModel;
			setChecked(selected);
		}

		@Override
		public void run() {
			PreferencesManager.setDefaultProject(project);
		}

	}

	public class SelectColumnsAction extends Action {

		public SelectColumnsAction() {
			super("Configure columns...");
		}

		@Override
		public void run() {
			Activator.runOnUIThread(new Runnable() {
				@Override
				public void run() {
					ConfigureColumnsDialog columnsDialog = new ConfigureColumnsDialog(getViewSite().getShell());
					columnsDialog.open();
				}
			});
		}
	}

	public class AddNewProject extends Action {

		public AddNewProject() {
			super("Add new...");
		}

		@Override
		public void run() {
			String preferencePageId = "com.sromku.bugsnag.preferences.BugsnagPreferencePage";
			PreferenceDialog pref = PreferencesUtil.createPreferenceDialogOn(getSite().getWorkbenchWindow().getShell(), preferencePageId, null, null);
			pref.open();
		}

	}

	/**
	 * Create columns
	 */
	private void createColumns() {
		// clean all columns
		if (viewer != null) {
			TableColumn[] columns = viewer.getTable().getColumns();
			for (int i = 0; i < columns.length; i++) {
				columns[i].dispose();
			}

			for (ColumnInfo columnInfo : PreferencesManager.getSelectedColumns()) {
				if (columnInfo.selected) {
					TableViewerColumn colStatus = new TableViewerColumn(viewer, SWT.NONE);
					colStatus.getColumn().setWidth(columnInfo.width);
					colStatus.getColumn().setText(columnInfo.name);
					colStatus.setLabelProvider(new CellLabelProvider() {
						@Override
						public void update(ViewerCell cell) {
							String data = bugReportsLabelProvider.getColumnText(cell.getElement(), cell.getColumnIndex());
							cell.setText(data);
						}
					});
				}
			}
		}
	}

	public TableViewer getViewer() {
		return viewer;
	}

	public void refresh() {
		getViewer().refresh();
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	@Override
	public void dispose() {
		// Clean all registered ISelectionLisntener
		List<ISelectionListener> iSelectionListeners = SelectionListenersStore.getISelectionListeners();
		for (ISelectionListener iSelectionListener : iSelectionListeners) {
			getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(iSelectionListener);
		}
		SelectionListenersStore.clear();
		super.dispose();
	}
}