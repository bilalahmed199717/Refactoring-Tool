package com.example.refactoringtool.menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class SaveMenuItemListener implements SelectionListener {

    @Override
    public void widgetSelected(SelectionEvent e) {
        // Get the shell from the selection event
        Shell shell = e.widget.getDisplay().getActiveShell();

        // Open a file dialog for saving
        FileDialog dialog = new FileDialog(shell, SWT.SAVE);
        String filePath = dialog.open();

        // Check if a file was selected
        if (filePath != null) {
            // Perform the save operation
            saveToFile(filePath);
        }
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent e) {
        // Do nothing
    }

    private void saveToFile(String filePath) {
        // TODO: Implement the save operation here
        // Save the content to the specified file path
        // You can use file I/O libraries like java.io or java.nio to perform the save operation
        // Example:
        // try (FileWriter writer = new FileWriter(filePath)) {
        //     // Write the content to the file
        //     writer.write(content);
        // } catch (IOException e) {
        //     // Handle any exceptions
        //     e.printStackTrace();
        // }
    }
}