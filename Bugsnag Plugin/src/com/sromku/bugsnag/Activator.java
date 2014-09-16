package com.sromku.bugsnag;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.sromku.bugsnag.preferences.PreferencesManager;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "com.sromku.bugsnag"; //$NON-NLS-1$
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}
	
	@Override
	public IPreferenceStore getPreferenceStore() {
		IPreferenceStore preferenceStore = super.getPreferenceStore();
		PreferencesManager.setDefaultColumns(preferenceStore);
		return preferenceStore;
	}

	public static void runOnUIThread(Runnable runnable) {
		Display.getDefault().asyncExec(runnable);
	}

}
