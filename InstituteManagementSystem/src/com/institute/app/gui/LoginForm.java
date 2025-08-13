package com.institute.app.gui;

import com.institute.app.dao.UserDAO;
import com.institute.app.models.User;
import javax.swing.*;
import java.awt.Font;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 * The login window for the application.
 * It provides fields for username and password and handles user authentication.
 */
public class LoginForm extends JFrame {

    // UI Components
    private JLabel titleLabel;
    private JLabel userLabel;
    private JLabel passLabel;
    private JTextField userTextField;
    private JPasswordField passField;
    private JButton loginButton;

    public LoginForm() {
        // This method sets up all the UI components.
        initComponents();
    }

    private void initComponents() {
        // --- Frame Setup ---
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login - Institute Management System");
        // Using AbsoluteLayout as specified by the inclusion of AbsoluteLayout.jar
        getContentPane().setLayout(new AbsoluteLayout());
        setSize(450, 320);
        setLocationRelativeTo(null); // Center the frame on the screen
        setResizable(false);

        // --- UI Component Initialization ---

        // Title Label
        titleLabel = new JLabel("Institute Management Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        getContentPane().add(titleLabel, new AbsoluteConstraints(85, 30, -1, -1));

        // Username Label and Field
        userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        getContentPane().add(userLabel, new AbsoluteConstraints(80, 90, -1, 30));
        userTextField = new JTextField(20);
        userTextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        getContentPane().add(userTextField, new AbsoluteConstraints(180, 90, 180, 30));

        // Password Label and Field
        passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        getContentPane().add(passLabel, new AbsoluteConstraints(80, 140, -1, 30));
        passField = new JPasswordField(20);
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        getContentPane().add(passField, new AbsoluteConstraints(180, 140, 180, 30));

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        getContentPane().add(loginButton, new AbsoluteConstraints(180, 200, 100, 35));

        // --- Event Handling ---

        // Add an action listener to the login button using a lambda expression.
        loginButton.addActionListener(e -> loginActionPerformed());

        // Also allow login by pressing Enter in the password field.
        passField.addActionListener(e -> loginActionPerformed());
    }

    /**
     * This method is called when the login button is clicked or Enter is pressed.
     */
    private void loginActionPerformed() {
        String username = userTextField.getText().trim();
        String password = new String(passField.getPassword());

        // Basic validation to ensure fields are not empty.
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Username and password fields cannot be empty.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Authenticate the user.
        UserDAO userDAO = new UserDAO();
        User user = userDAO.authenticate(username, password);

        if (user != null) {
            // If authentication is successful...
            JOptionPane.showMessageDialog(this,
                    "Login Successful! Welcome, " + user.getUsername() + ".",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            // Open the main application dashboard.
            Dashboard dashboard = new Dashboard();
            dashboard.setVisible(true);

            // Close the login form.
            this.dispose();
        } else {
            // If authentication fails...
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password. Please try again.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
