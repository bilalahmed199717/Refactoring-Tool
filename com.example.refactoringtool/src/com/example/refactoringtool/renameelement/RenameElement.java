package com.example.refactoringtool.renameelement;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

public class RenameElement {
    // Remove unused field editor
    // It is not used in this class
    // private IEditorPart editor;

    private IDocument document;
    private int startOffset;
    private int endOffset;

    public RenameElement(IDocument document, int startOffset, int endOffset) {
        // Remove unused field editor from the constructor
        // this.editor = editor;
        this.document = document;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }

    public void renameElement(String newName) {
        try {
            // Remove unused variable selectedText
            // It is not used in this method
            // String selectedText = document.get(startOffset, endOffset - startOffset);
            document.replace(startOffset, endOffset - startOffset, newName);
        } catch (BadLocationException e) {
            // Handle the exception if needed
            e.printStackTrace();
        }
    }
}
