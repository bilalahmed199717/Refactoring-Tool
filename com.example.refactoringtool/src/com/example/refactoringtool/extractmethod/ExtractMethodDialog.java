package com.example.refactoringtool.extractmethod;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class ExtractMethodDialog extends Dialog {

    public ExtractMethodDialog(Shell parentShell, IDocument document, int startOffset, int endOffset) {
        super(parentShell);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        container.setLayout(new GridLayout(2, false));

        createMethodNameControls();
        createAccessModifierControls();
        createParameterControls();

        return container;
    }

    private void createMethodNameControls() {
        // ... (unchanged)
    }

    private void createAccessModifierControls() {
        // ... (unchanged)
    }

    private void createParameterControls() {
        // ... (unchanged)
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Extract Method");
    }

    @Override
    protected void okPressed() {
        // ... (unchanged)
    }

}
