package com.example.refactoringtool.menu;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.example.refactoringtool.RefactoringTool;

public class RefactorButtonListener implements SelectionListener {

    private final RefactoringTool refactoringTool;

    public RefactorButtonListener(RefactoringTool refactoringTool) {
        this.refactoringTool = refactoringTool;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        refactoringTool.performRefactoring();
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent e) {
        // Do nothing
    }
}
