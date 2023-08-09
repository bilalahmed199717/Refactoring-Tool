package com.example.refactoringtool.menu;

import org.eclipse.jface.text.ITextSelection;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

import com.example.refactoringtool.checksignature.CheckSignatureDialog;

public class CheckSignatureMenuItemListener implements SelectionListener {

    @Override
    public void widgetSelected(SelectionEvent e) {
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        IWorkbenchPart activePart = page.getActivePart();

        if (activePart instanceof ITextEditor) {
            ITextEditor textEditor = (ITextEditor) activePart;
            ITextSelection selection = (ITextSelection) textEditor.getSelectionProvider().getSelection();

            if (selection != null && !selection.isEmpty()) {
                int startOffset = selection.getOffset();
                int endOffset = startOffset + selection.getLength();

                // Create a check signature dialog and open it
                CheckSignatureDialog dialog = new CheckSignatureDialog(textEditor.getSite().getShell(), textEditor, startOffset, endOffset);
                dialog.open();
            }
        }
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent e) {
        // Do nothing
    }
}
