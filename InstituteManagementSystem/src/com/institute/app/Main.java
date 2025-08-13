package com.institute.app;

import com.formdev.flatlaf.FlatDarkLaf;
import com.institute.app.gui.LoginForm;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * The main entry point for the Institute Management System application.
 */
public class Main {

    public static void main(String[] args) {
        // It is crucial to set the look and feel before creating any UI components.
        try {
            // Set FlatLaf Dark as the global look and feel for a modern UI.
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            // This might happen on unusual systems. Log the error and continue with the default L&F.
            System.err.println("Failed to initialize the FlatLaf look and feel.");
            e.printStackTrace();
        }

        // All Swing UI operations should be performed on the Event Dispatch Thread (EDT)
        // to ensure thread safety. SwingUtilities.invokeLater is the standard way to do this.
        SwingUtilities.invokeLater(() -> {
            // Create an instance of the LoginForm and make it visible.
            // The application flow starts here.
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        });
    }
}
