package com.example.refactoringtool;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import com.example.refactoringtool.codeduplication.CodeDuplication;
import com.example.refactoringtool.extractmethod.ExtractMethod;

import java.util.Map;

public class RefactoringToolPlugin implements IEditorActionDelegate {

    private IEditorPart editor;

    @Override
    public void run(IAction action) {
        if (editor instanceof ITextEditor) {
            ITextEditor textEditor = (ITextEditor) editor;
            IDocumentProvider docProvider = textEditor.getDocumentProvider();
            IDocument doc = docProvider.getDocument(textEditor.getEditorInput());

            ISelection selection = textEditor.getSelectionProvider().getSelection();
            if (selection instanceof ITextSelection) {
                ITextSelection textSelection = (ITextSelection) selection;
                textSelection.getStartLine();
                textSelection.getEndLine();
                int startOffset = textSelection.getOffset();
                int endOffset = startOffset + textSelection.getLength();
                String selectedText = textSelection.getText();

                // Check for code duplication before extracting the method
                boolean hasCodeDuplication = checkForCodeDuplication(doc, selectedText);
                if (hasCodeDuplication) {
                    // Handle code duplication, e.g., show a warning dialog or take appropriate action
                    // ...
                } else {
                    // Extract the selected code as a separate method
                    extractMethod(textEditor, doc, startOffset, endOffset);
                }
            }
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

    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        // This method is not used in this implementation
    }

    private void extractMethod(ITextEditor editor, IDocument document, int startOffset, int endOffset) {
        // Get the shell to create the extraction dialog
        Shell shell = editor.getSite().getShell();

        // Create an extraction dialog and open it
        ExtractMethod dialog = new ExtractMethod(shell, document, startOffset, endOffset);
        dialog.open();
    }

    private boolean checkForCodeDuplication(IDocument document, String selectedText) {
        String code = document.get();
        int duplicationThreshold = 10; // Set the threshold for minimum duplication length

        Map<String, Integer> duplicatedCodeMap = CodeDuplication.findDuplicatedCode(code, duplicationThreshold);

        return duplicatedCodeMap.containsKey(selectedText);
    }
}
