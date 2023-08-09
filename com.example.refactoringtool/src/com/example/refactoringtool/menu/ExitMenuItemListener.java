package com.example.refactoringtool.menu;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ExitMenuItemListener implements SelectionListener {

    private final Shell shell;

    public ExitMenuItemListener(Shell shell) {
        this.shell = shell;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        exitApplication();
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent e) {
        exitApplication();
    }

    private void exitApplication() {
        shell.dispose(); // Dispose the shell
        Display.getDefault().dispose(); // Dispose the display
        System.exit(0); // Terminate the application
    }
}