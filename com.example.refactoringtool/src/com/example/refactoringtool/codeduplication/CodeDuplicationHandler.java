package com.example.refactoringtool.codeduplication;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

public class CodeDuplicationHandler implements IHandler {

    @Override
    public void addHandlerListener(IHandlerListener handlerListener) {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
        if (activeEditor instanceof ITextEditor) {
            ITextEditor textEditor = (ITextEditor) activeEditor;
            IDocumentProvider docProvider = textEditor.getDocumentProvider();
            IDocument doc = docProvider.getDocument(textEditor.getEditorInput());

            ISelection selection = textEditor.getSelectionProvider().getSelection();
            if (selection instanceof ITextSelection) {
                ITextSelection textSelection = (ITextSelection) selection;
                String selectedText = textSelection.getText();

                // Check for code duplication
                boolean hasCodeDuplication = CodeDuplication.checkForCodeDuplication(doc, selectedText);

                // Show result in a dialog
                Shell shell = activeEditor.getSite().getShell();
                String message;
                if (hasCodeDuplication) {
                    message = "Selected code contains duplication.";
                } else {
                    message = "No duplication found in the selected code.";
                }
                MessageDialog.openInformation(shell, "Code Duplication Check", message);
            }
        }

        return null;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isHandled() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void removeHandlerListener(IHandlerListener handlerListener) {
        // TODO Auto-generated method stub

    }
}
