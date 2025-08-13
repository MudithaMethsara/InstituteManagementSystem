package com.institute.app.gui.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;

/**
 * A placeholder panel for Reports.
 * The full implementation will contain UI for generating and viewing various reports.
 */
public class ReportsPanel extends JPanel {
    public ReportsPanel() {
        setLayout(new BorderLayout());
        JLabel placeholderLabel = new JLabel("Reports & Analytics Module");
        placeholderLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(placeholderLabel, BorderLayout.CENTER);
    }
}
