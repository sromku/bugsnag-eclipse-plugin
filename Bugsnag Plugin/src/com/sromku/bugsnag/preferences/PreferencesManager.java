package com.sromku.bugsnag.preferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;

import com.sromku.bugsnag.Activator;
import com.sromku.bugsnag.model.Account;
import com.sromku.bugsnag.model.Column;
import com.sromku.bugsnag.model.ColumnInfo;
import com.sromku.bugsnag.model.Project;
import com.sromku.bugsnag.utils.SerializationUtils;

public class PreferencesManager {

	private static IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
	private static List<OnChangeListener> observers = new ArrayList<OnChangeListener>();

	public final static String BUGSNAG_ACCOUNTS = "BUGSNAG_ACCOUNTS";
	public final static String SELECTED_PROJECT = "SELECTED_PROJECT";

	public final static String DEFAULT_COLUMNS = "DEFAULT_COLUMNS";
	public final static String SELECTED_COLUMNS = "SELECTED_COLUMNS";

	public static void addOnChangeListener(OnChangeListener onChangeListener) {
		observers.add(onChangeListener);
	}

	public static void removeOnChangeListener(OnChangeListener onChangeListener) {
		observers.remove(onChangeListener);
	}

	/**
	 * Add new account
	 */
	public static void updateAccount(Account account) {
		ArrayList<Account> accounts = getAccounts();
		if (!accounts.contains(account)) {
			accounts.add(account);
		} else {
			Collections.replaceAll(accounts, account, account);
		}
		String serializedAccounts = SerializationUtils.serialize(accounts);
		preferenceStore.putValue(BUGSNAG_ACCOUNTS, serializedAccounts);
	}

	/**
	 * Set or update the columns user want to see.
	 */
	public static void updateSelectedColumns(ArrayList<ColumnInfo> columns) {
		String serialized = SerializationUtils.serialize(columns);
		preferenceStore.putValue(SELECTED_COLUMNS, serialized);
		notifyObservers();
	}

	/**
	 * Update one column
	 */
	public static void updateSelectedColumn(ColumnInfo updatedColumnInfo) {
		ArrayList<ColumnInfo> selectedColumns = getSelectedColumns();
		for (ColumnInfo columnInfo : selectedColumns) {
			if (columnInfo.name.equals(updatedColumnInfo.name)) {
				columnInfo.selected = updatedColumnInfo.selected;
				columnInfo.width = updatedColumnInfo.width;
			}
		}
		PreferencesManager.updateSelectedColumns(selectedColumns);
	}

	/**
	 * Delete account
	 */
	public static void deleteAccount(Account account) {
		ArrayList<Account> accounts = getAccounts();
		accounts.remove(account);
		String serializedAccounts = SerializationUtils.serialize(accounts);
		preferenceStore.putValue(BUGSNAG_ACCOUNTS, serializedAccounts);
	}

	/**
	 * Get all accounts
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Account> getAccounts() {
		String serializedAccounts = preferenceStore.getString(BUGSNAG_ACCOUNTS);
		ArrayList<Account> accounts = (ArrayList<Account>) SerializationUtils.deserialize(serializedAccounts, ArrayList.class);
		if (accounts == null) {
			accounts = new ArrayList<Account>();
		}
		return accounts;
	}

	/**
	 * Set default project
	 */
	public static void setDefaultProject(Project project) {
		String serializedProject = SerializationUtils.serialize(project);
		preferenceStore.setDefault(SELECTED_PROJECT, serializedProject);
	}

	/**
	 * Set the default columns
	 */
	public static void setDefaultColumns(IPreferenceStore preferenceStore) {
		String defaultString = preferenceStore.getDefaultString(DEFAULT_COLUMNS);
		if (defaultString == null || defaultString.length() == 0) {
			ArrayList<ColumnInfo> defaultColumns = new ArrayList<ColumnInfo>();
			defaultColumns.add(ColumnInfo.create(Column.EXCEPTION.getName(), 300, true));
			defaultColumns.add(ColumnInfo.create(Column.CLASS.getName(), 150, true));
			defaultColumns.add(ColumnInfo.create(Column.LOCATION.getName(), 70, true));
			defaultColumns.add(ColumnInfo.create(Column.OCCURRENCES.getName(), 80, true));
			defaultColumns.add(ColumnInfo.create(Column.APP_VERSION.getName(), 150, true));
			defaultColumns.add(ColumnInfo.create(Column.LAST_DATE.getName(), 150, true));
			defaultColumns.add(ColumnInfo.create(Column.MESSAGE.getName(), 200, false));
			defaultColumns.add(ColumnInfo.create(Column.AFFECTED_USERS.getName(), 80, false));
			defaultColumns.add(ColumnInfo.create(Column.COMMENTS.getName(), 70, false));
			defaultColumns.add(ColumnInfo.create(Column.CREATED_DATE.getName(), 150, false));
			defaultColumns.add(ColumnInfo.create(Column.IS_RESOLVED.getName(), 70, false));
			defaultColumns.add(ColumnInfo.create(Column.RELEASE_STAGES.getName(), 150, false));
			defaultColumns.add(ColumnInfo.create(Column.SEVERITY.getName(), 80, false));
			String serialized = SerializationUtils.serialize(defaultColumns);
			preferenceStore.setDefault(DEFAULT_COLUMNS, serialized);
		}
	}

	/**
	 * Get default project
	 */
	public static Project getDefaultProject() {
		String serializedDefaultProject = preferenceStore.getDefaultString(SELECTED_PROJECT);
		if (serializedDefaultProject == null || serializedDefaultProject.length() == 0) {
			return null;
		}
		return SerializationUtils.deserialize(serializedDefaultProject, Project.class);
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<ColumnInfo> getSelectedColumns() {
		String selectedColumns = preferenceStore.getString(SELECTED_COLUMNS);
		if (selectedColumns != null && selectedColumns.length() > 0) {
			return SerializationUtils.deserialize(selectedColumns, ArrayList.class);
		} else {
			String defaultColumns = preferenceStore.getDefaultString(DEFAULT_COLUMNS);
			return SerializationUtils.deserialize(defaultColumns, ArrayList.class);
		}
	}

	private static void notifyObservers() {
		for (OnChangeListener onChangeListener : observers) {
			onChangeListener.onChanged();
		}
	}

	public interface OnChangeListener {
		void onChanged();
	}

}
