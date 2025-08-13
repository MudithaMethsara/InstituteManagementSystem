package com.institute.app.gui.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;

/**
 * A placeholder panel for Payment Management.
 * The full implementation will contain UI for tracking fees and payments.
 */
public class PaymentPanel extends JPanel {
    public PaymentPanel() {
        setLayout(new BorderLayout());
        JLabel placeholderLabel = new JLabel("Fee & Payment Management Module");
        placeholderLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(placeholderLabel, BorderLayout.CENTER);
    }
}
