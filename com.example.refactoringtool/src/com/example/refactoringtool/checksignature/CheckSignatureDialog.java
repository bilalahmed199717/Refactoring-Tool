package com.example.refactoringtool.checksignature;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.texteditor.ITextEditor;

public class CheckSignatureDialog extends Dialog {

    private Text signatureText; // Add this line to declare the signatureText variable

    public CheckSignatureDialog(Shell parentShell, ITextEditor editor, int startOffset, int endOffset) {
        super(parentShell);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        container.setLayout(new GridLayout(2, false));

        createSignatureControls(container);

        return container;
    }

    private void createSignatureControls(Composite container) {
        Label signatureLabel = new Label(container, SWT.NONE);
        signatureLabel.setText("Method Signature:");

        signatureText = new Text(container, SWT.BORDER);
        signatureText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Check Method Signature");
    }

    @Override
    protected void okPressed() {
        String methodSignature = signatureText.getText().trim();
        if (methodSignature.isEmpty()) {
            showErrorDialog("Error", "Please enter a method signature to check.");
            return;
        }

        try {
            boolean isMethodSignature = checkMethodSignature(methodSignature);
            if (isMethodSignature) {
                showInformationDialog("Valid Signature", "The entered signature is a valid method signature.");
            } else {
                showInformationDialog("Invalid Signature", "The entered signature is not a valid method signature.");
            }
        } catch (BadLocationException e) {
            showErrorDialog("Error", "An error occurred while checking the method signature: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        super.okPressed();
    }

    private boolean checkMethodSignature(String methodSignature) throws BadLocationException {
        // Remove unused variable selectedText
        // It is not used in this method
        // String selectedText = document.get(startOffset, endOffset - startOffset);

        // TODO: Implement the logic to check if the entered signature matches the selected text
        // You can use regular expressions or any other parsing techniques to identify method signatures.
        // Return true if it's a valid method signature, otherwise, return false.
        return false;
    }

    private void showErrorDialog(String title, String message) {
        MessageDialog.openError(getShell(), title, message);
    }

    private void showInformationDialog(String title, String message) {
        MessageDialog.openInformation(getShell(), title, message);
    }
}
