package com.example.refactoringtool.extractmethod;

import java.io.ByteArrayInputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ExtractMethod extends Dialog {

    private IDocument document;
    private ITextSelection selection;
    private Text methodNameText;
    private Combo accessModifierCombo;
    private Text parameterText;
    private Text parameterTypeText;
    private Text parameterNameText;
    private Text methodSignaturePreviewText;

    public ExtractMethod(Shell parentShell, IDocument document, ITextSelection selection) {
        super(parentShell);
        this.document = document;
        this.selection = selection;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        container.setLayout(new GridLayout(2, false));

        createMethodNameControls(container);
        createAccessModifierControls(container);
        createParameterControls(container);
        createParameterTypeControls(container);
        createParameterNameControls(container);
        createMethodSignaturePreview(container);

        return container;
    }

    private void createMethodNameControls(Composite container) {
        Label methodNameLabel = new Label(container, SWT.NONE);
        methodNameLabel.setText("Method Name:");

        methodNameText = new Text(container, SWT.BORDER);
        methodNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        methodNameText.addModifyListener(e -> updateMethodSignaturePreview());
    }

    private void createAccessModifierControls(Composite container) {
        Label accessModifierLabel = new Label(container, SWT.NONE);
        accessModifierLabel.setText("Access Modifier:");

        accessModifierCombo = new Combo(container, SWT.READ_ONLY);
        accessModifierCombo.setItems(new String[] { "public", "protected", "package", "private" });
        accessModifierCombo.select(0);
        accessModifierCombo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                updateMethodSignaturePreview();
            }
        });
    }

    private void createParameterControls(Composite container) {
        Label parameterLabel = new Label(container, SWT.NONE);
        parameterLabel.setText("Parameters:");

        parameterText = new Text(container, SWT.BORDER);
        parameterText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        parameterText.addModifyListener(e -> updateMethodSignaturePreview());
    }

    private void createParameterTypeControls(Composite container) {
        Label parameterTypeLabel = new Label(container, SWT.NONE);
        parameterTypeLabel.setText("Parameter Type:");

        parameterTypeText = new Text(container, SWT.BORDER);
        parameterTypeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        parameterTypeText.addModifyListener(e -> updateMethodSignaturePreview());
    }

    private void createParameterNameControls(Composite container) {
        Label parameterNameLabel = new Label(container, SWT.NONE);
        parameterNameLabel.setText("Parameter Name:");

        parameterNameText = new Text(container, SWT.BORDER);
        parameterNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        parameterNameText.addModifyListener(e -> updateMethodSignaturePreview());
    }

    private void createMethodSignaturePreview(Composite container) {
        Label methodSignaturePreviewLabel = new Label(container, SWT.NONE);
        methodSignaturePreviewLabel.setText("Method Signature Preview:");

        methodSignaturePreviewText = new Text(container, SWT.BORDER | SWT.READ_ONLY);
        methodSignaturePreviewText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    }

    private void updateMethodSignaturePreview() {
        String methodName = methodNameText.getText().trim();
        String accessModifier = accessModifierCombo.getText();
        String parameterType = parameterTypeText.getText().trim();
        String parameterName = parameterNameText.getText().trim();
        String parameters = parameterType + " " + parameterName;

        String methodSignature = accessModifier + " void " + methodName + "(" + parameters + ")";
        methodSignaturePreviewText.setText(methodSignature);
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Extract Method");
    }

    @Override
    protected void okPressed() {
        // Extract the method
        String methodName = methodNameText.getText().trim();
        if (methodName.isEmpty()) {
            showErrorDialog("Error", "Please enter a method name.");
            return;
        }

        String accessModifier = accessModifierCombo.getText();
        String parameterType = parameterTypeText.getText().trim();
        String parameterName = parameterNameText.getText().trim();
        String parameters = parameterType + " " + parameterName;

        if (parameterType.isEmpty() || parameterName.isEmpty()) {
            showErrorDialog("Error", "Please provide both parameter type and name.");
            return;
        }

        try {
            String lineDelimiter = getLineDelimiter();
            String selectedText = document.get(startOffset, endOffset - startOffset);
            String newMethodDeclaration = createMethodDeclaration(methodName, accessModifier, parameters, selectedText,
                    lineDelimiter);
            String newMethodCall = createMethodCall(methodName, parameterName, lineDelimiter);

            // Find the position of the last closing curly brace '}' in the document
            int lastClosingBraceOffset = document.get().lastIndexOf('}');

            // Insert the new method declaration before the last closing curly brace
            document.replace(lastClosingBraceOffset, 0, lineDelimiter + newMethodDeclaration);

            // Replace the selected text with the method call
            document.replace(startOffset, endOffset - startOffset, newMethodCall);

            // Create an IFile instance from the selectedText
            IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path("Temp.java"));
            file.create(new ByteArrayInputStream(selectedText.getBytes()), true, null);

            ICompilationUnit cu = JavaCore.createCompilationUnitFrom(file);

            @SuppressWarnings("deprecation")
			ASTParser parser = ASTParser.newParser(AST.JLS17); // Use the correct AST version here
            parser.setKind(ASTParser.K_COMPILATION_UNIT);
            parser.setSource(cu);

            @SuppressWarnings("unused") // To suppress the unused variable warning
            ASTNode astRoot = parser.createAST(null);

            showInformationDialog("Extraction Successful", "Method extracted successfully!");
        } catch (BadLocationException e) {
            showErrorDialog("Error", "An error occurred while extracting the method: " + e.getMessage());
            e.printStackTrace();
            return;
        } catch (CoreException e) {
            showErrorDialog("Error", "An error occurred while creating the IFile: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        super.okPressed();
    }

    private String getLineDelimiter() {
        try {
            String lineDelimiter = document.getLineDelimiter(document.getLineOfOffset(startOffset));
            return (lineDelimiter != null) ? lineDelimiter : "\n";
        } catch (BadLocationException e) {
            e.printStackTrace();
            return "\n";
        }
    }

    private void showErrorDialog(String title, String message) {
        MessageDialog.openError(getShell(), title, message);
    }

    private void showInformationDialog(String title, String message) {
        MessageDialog.openInformation(getShell(), title, message);
    }

    private String createMethodDeclaration(String methodName, String accessModifier, String parameters,
            String selectedText, String lineDelimiter) {
        StringBuilder methodDeclaration = new StringBuilder();

        methodDeclaration.append(accessModifier).append(" void ").append(methodName).append("(");
        methodDeclaration.append(parameters).append(") {").append(lineDelimiter);
        methodDeclaration.append(selectedText).append(lineDelimiter);
        methodDeclaration.append("}");

        return methodDeclaration.toString();
    }

    private String createMethodCall(String methodName, String parameterName, String lineDelimiter) {
        return methodName + "(" + parameterName + ");" + lineDelimiter;
    }

    public boolean canExtractMethod() throws BadLocationException {
        String selectedText = document.get(startOffset, endOffset - startOffset);

        // Use ASTParser to parse the selected code
        ASTParser parser = ASTParser.newParser(AST.JLS17); // Use the latest AST version here
        parser.setKind(ASTParser.K_CLASS_BODY_DECLARATIONS);
        parser.setSource(selectedText.toCharArray());

        ASTNode astRoot = parser.createAST(null);

        // Check if the parsed AST represents a valid method extraction
        if (astRoot instanceof MethodDeclaration) {
            return true;
        }

        return false;
    }

    public String getSuggestedParameter() throws BadLocationException {
        // Existing code
        return null; // Add a default return value, modify this as needed based on your implementation
    }

    public String getMethodName() {
        return methodNameText.getText().trim();
    }

    public String getAccessModifier() {
        return accessModifierCombo.getText();
    }

    public String getParameters() {
        return parameterText.getText();
    }

    public void setMethodSignaturePreview(String signature) {
        Display.getDefault().asyncExec(() -> {
            if (methodNameText != null && !methodNameText.isDisposed()) {
                methodNameText.setText(signature);
            }
        });
    }
}
