package com.example.refactoringtool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RefactoringToolPluginUI {

    private JFrame frame;
    private JTextField inputTextField;
    private JTextArea outputTextArea;

    public RefactoringToolPluginUI() {
        initUI();
    }

    public void show() {
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
        });
    }

    private void initUI() {
        frame = new JFrame("Refactoring Tool Plugin");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel inputLabel = new JLabel("Input:");
        inputTextField = new JTextField(20);
        inputPanel.add(inputLabel);
        inputPanel.add(inputTextField);

        JPanel outputPanel = new JPanel(new BorderLayout());
        JLabel outputLabel = new JLabel("Output:");
        outputTextArea = new JTextArea(10, 20);
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        outputPanel.add(outputLabel, BorderLayout.NORTH);
        outputPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton processButton = new JButton("Process");
        processButton.addActionListener(new ProcessButtonListener());
        buttonPanel.add(processButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(800, 600));
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(outputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    private class ProcessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Call your refactoring tool method here with inputTextField.getText() as input
            // Store the output in a String variable called output
            String output = "Refactoring output";

            // Set the output text in the outputTextArea
            outputTextArea.setText(output);
        }
    }
}
