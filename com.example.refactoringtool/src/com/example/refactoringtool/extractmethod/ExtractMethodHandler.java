package com.example.refactoringtool.extractmethod;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

public class ExtractMethodHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
        if (editor instanceof ITextEditor) {
            ITextEditor textEditor = (ITextEditor) editor;
            IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
            ITextSelection selection = (ITextSelection) textEditor.getSelectionProvider().getSelection();

            int startOffset = selection.getOffset();
            int endOffset = startOffset + selection.getLength();

            // Check if the selection can be extracted as a method
            ExtractMethod extractor = new ExtractMethod(textEditor.getSite().getShell(), document, startOffset, endOffset);
            try {
                if (extractor.canExtractMethod()) {
                    extractor.open();
                } else {
                    // Show an error message if the selection is not a valid method extraction
                    MessageDialog.openError(textEditor.getSite().getShell(), "Error",
                            "Selected code cannot be extracted as a method.");
                }
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
