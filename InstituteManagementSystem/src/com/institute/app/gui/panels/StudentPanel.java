package com.institute.app.gui.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;

/**
 * A placeholder panel for Student Management.
 * The full implementation will include a table to list students,
 * buttons for add/edit/delete, and forms for data entry.
 */
public class StudentPanel extends JPanel {
    public StudentPanel() {
        // Use a layout manager for the panel
        setLayout(new BorderLayout());

        // Create a label to indicate what this panel is for
        JLabel placeholderLabel = new JLabel("Student Management Module");
        placeholderLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add the label to the center of the panel
        add(placeholderLabel, BorderLayout.CENTER);
    }
}
