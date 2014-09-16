package com.sromku.bugsnag.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.ISelectionListener;

/**
 * This class is used for storing the selection listeners that we create and
 * register to the workbench. <br>
 * Once we dispose the main view, we should remove all listeners from workbench
 * and clean this list.
 * 
 * @author sromku
 */
public class SelectionListenersStore {

	private static List<ISelectionListener> selectionListners = new ArrayList<ISelectionListener>();

	public static void addISelectionListener(ISelectionListener iSelectionListener) {
		selectionListners.add(iSelectionListener);
	}

	public static List<ISelectionListener> getISelectionListeners() {
		return selectionListners;
	}

	public static void clear() {
		selectionListners.clear();
	}
}
