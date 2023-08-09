package com.example.refactoringtool;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import com.example.refactoringtool.menu.AboutMenuItemListener;
import com.example.refactoringtool.menu.CheckSignatureMenuItemListener;
import com.example.refactoringtool.menu.ExitMenuItemListener;
import com.example.refactoringtool.menu.ExtractMethodMenuItemListener;
import com.example.refactoringtool.menu.RefactorButtonListener;
import com.example.refactoringtool.menu.SaveMenuItemListener;

public class SWTRefactoringToolPluginUI {
    private Shell shell;
    private Text inputText;
    private Text outputText;

    public SWTRefactoringToolPluginUI() {
        createUI();
    }

    private void createUI() {
        shell = new Shell();
        shell.setText("Refactoring Tool");

        GridLayout layout = new GridLayout(2, false);
        shell.setLayout(layout);

        // Input field
        Label inputLabel = new Label(shell, SWT.NONE);
        inputLabel.setText("Input:");

        inputText = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        GridData inputTextLayoutData = new GridData(GridData.FILL_BOTH);
        inputTextLayoutData.verticalSpan = 3;
        inputText.setLayoutData(inputTextLayoutData);

        // Output field
        Label outputLabel = new Label(shell, SWT.NONE);
        outputLabel.setText("Output:");

        outputText = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
        GridData outputTextLayoutData = new GridData(GridData.FILL_BOTH);
        outputTextLayoutData.verticalSpan = 3;
        outputText.setLayoutData(outputTextLayoutData);

        // Button to trigger refactoring
        Button refactorButton = new Button(shell, SWT.PUSH);
        refactorButton.setText("Refactor");
        refactorButton.addSelectionListener(new RefactorButtonListener(new RefactoringTool()));

        // Menu bar
        Menu menuBar = new Menu(shell, SWT.BAR);
        shell.setMenuBar(menuBar);

        // File menu
        MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        fileMenuHeader.setText("File");

        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        fileMenuHeader.setMenu(fileMenu);

        // Save menu item
        MenuItem saveMenuItem = new MenuItem(fileMenu, SWT.PUSH);
        saveMenuItem.setText("Save");
        saveMenuItem.addSelectionListener(new SaveMenuItemListener());

        // Exit menu item
        MenuItem exitMenuItem = new MenuItem(fileMenu, SWT.PUSH);
        exitMenuItem.setText("Exit");
        exitMenuItem.addSelectionListener(new ExitMenuItemListener(shell));

        // Refactoring menu
        MenuItem refactorMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        refactorMenuHeader.setText("Refactor");

        Menu refactorMenu = new Menu(shell, SWT.DROP_DOWN);
        refactorMenuHeader.setMenu(refactorMenu);

        // Extract Method menu item
        MenuItem extractMethodMenuItem = new MenuItem(refactorMenu, SWT.PUSH);
        extractMethodMenuItem.setText("Extract Method");
        extractMethodMenuItem.addSelectionListener(new ExtractMethodMenuItemListener());

        // Check Method Signature menu item
        MenuItem checkSignatureMenuItem = new MenuItem(refactorMenu, SWT.PUSH);
        checkSignatureMenuItem.setText("Check Method Signature");
        checkSignatureMenuItem.addSelectionListener(new CheckSignatureMenuItemListener());

        // Help menu
        MenuItem helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        helpMenuHeader.setText("Help");

        Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
        helpMenuHeader.setMenu(helpMenu);

        // About menu item
        MenuItem aboutMenuItem = new MenuItem(helpMenu, SWT.PUSH);
        aboutMenuItem.setText("About");
        aboutMenuItem.addSelectionListener(new AboutMenuItemListener(shell));

        shell.pack();
        shell.open();

        Display display = shell.getDisplay();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    public static void main(String[] args) {
        new SWTRefactoringToolPluginUI();
    }
}
