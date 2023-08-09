package com.example.refactoringtool.menu;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditor;

import com.example.refactoringtool.extractmethod.ExtractMethodDialog;

public class ExtractMethodMenuItemListener implements IEditorActionDelegate, SelectionListener {

    private IEditorPart editor;

    @Override
    public void run(IAction action) {
        if (editor instanceof ITextEditor) {
            ITextEditor textEditor = (ITextEditor) editor;
            IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
            ITextSelection selection = (ITextSelection) textEditor.getSelectionProvider().getSelection();

            int startOffset = selection.getOffset();
            int endOffset = startOffset + selection.getLength();

            ExtractMethodDialog dialog = new ExtractMethodDialog(textEditor.getSite().getShell(), document, startOffset, endOffset);
            dialog.open();
        }
    }

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        // not used
    }

    @Override
    public void setActiveEditor(IAction action, IEditorPart targetEditor) {
        editor = targetEditor;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        run(null); // Call the run method when the widget is selected
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent e) {
        // not used
    }

    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        // This method is not used in this implementation
    }
}
