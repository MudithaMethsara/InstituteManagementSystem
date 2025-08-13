package com.institute.app.gui.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;

/**
 * A placeholder panel for Teacher Management.
 * The full implementation will contain UI for managing teacher records.
 */
public class TeacherPanel extends JPanel {
    public TeacherPanel() {
        setLayout(new BorderLayout());
        JLabel placeholderLabel = new JLabel("Teacher & Staff Management Module");
        placeholderLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(placeholderLabel, BorderLayout.CENTER);
    }
}
