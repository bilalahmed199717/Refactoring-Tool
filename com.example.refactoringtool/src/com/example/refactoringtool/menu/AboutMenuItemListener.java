package com.example.refactoringtool.menu;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class AboutMenuItemListener implements SelectionListener {

    private final Shell shell;

    public AboutMenuItemListener(Shell shell) {
        this.shell = shell;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        showMessageDialog("Refactoring Tool", "This is a refactoring tool application.");
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent e) {
        showMessageDialog("Refactoring Tool", "This is a refactoring tool application.");
    }

    private void showMessageDialog(String title, String message) {
        MessageBox dialog = new MessageBox(shell);
        dialog.setText(title);
        dialog.setMessage(message);
        dialog.open();
    }
}