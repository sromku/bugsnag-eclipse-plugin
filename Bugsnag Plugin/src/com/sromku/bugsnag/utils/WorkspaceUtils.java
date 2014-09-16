package com.sromku.bugsnag.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.eclipse.jdt.core.search.TypeDeclarationMatch;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

public class WorkspaceUtils {

	/**
	 * Find all java files that match the class name
	 * 
	 * @param cls
	 *            The class name
	 * @return List of java files
	 */
	public static List<IJavaElement> search(String cls) {

		final List<IJavaElement> javaElements = new ArrayList<IJavaElement>();

		IJavaSearchScope scope = SearchEngine.createWorkspaceScope();
		SearchEngine engine = new SearchEngine();
		SearchPattern pattern = SearchPattern.createPattern(cls, IJavaSearchConstants.TYPE, IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);

		SearchRequestor requestor = new SearchRequestor() {
			public void acceptSearchMatch(final SearchMatch match) throws CoreException {
				TypeDeclarationMatch typeMatch = (TypeDeclarationMatch) match;
				IJavaElement type = (IJavaElement) typeMatch.getElement();
				javaElements.add(type);
			}
		};
		try {
			engine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, requestor, new NullProgressMonitor());
		}
		catch (final CoreException e) {
			e.printStackTrace();
		}

		return javaElements;
	}

	/**
	 * Open editor in specific line number
	 * 
	 * @param javaElement
	 *            The java file to be opened
	 * @param lineNumber
	 *            The line number
	 */
	public static void openInEditor(IJavaElement javaElement, int lineNumber) {
		try {
			IEditorPart editorPart = JavaUI.openInEditor(javaElement, true, true);
			if (!(editorPart instanceof ITextEditor) || lineNumber <= 0) {
				return;
			}

			ITextEditor editor = (ITextEditor) editorPart;
			IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
			if (document != null) {
				IRegion lineInfo = null;
				lineInfo = document.getLineInformation(lineNumber - 1);
				if (lineInfo != null) {
					editor.selectAndReveal(lineInfo.getOffset(), lineInfo.getLength());
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
