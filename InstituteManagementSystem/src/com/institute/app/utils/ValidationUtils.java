package com.institute.app.utils;

import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.util.regex.Pattern;

/**
 * A utility class for validating user input from Swing components.
 */
public final class ValidationUtils {

    // A reasonably strict regex for email validation.
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    // A flexible regex for phone numbers, allowing digits, dashes, spaces, and parentheses.
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^[\\d\\s\\-()+]{7,20}$"
    );

    /**
     * Private constructor to prevent instantiation.
     */
    private ValidationUtils() {}

    /**
     * Checks if a string is null, empty, or just whitespace.
     * @param str The string to check.
     * @return `true` if the string is effectively empty, `false` otherwise.
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Validates that a JTextField is not empty.
     * If it is, it displays an error message to the user.
     *
     * @param parent      The parent component for the dialog (e.g., the frame or panel).
     * @param field       The JTextField to validate.
     * @param fieldName   The name of the field to include in the error message (e.g., "First Name").
     * @return `true` if the field is valid (not empty), `false` otherwise.
     */
    public static boolean isNotEmpty(Component parent, JTextField field, String fieldName) {
        if (isNullOrEmpty(field.getText())) {
            showError(parent, fieldName + " cannot be empty.");
            field.requestFocusInWindow();
            return false;
        }
        return true;
    }

    /**
     * Validates an email address.
     *
     * @param parent The parent component for the dialog.
     * @param field  The JTextField containing the email.
     * @return `true` if the email format is valid, `false` otherwise.
     */
    public static boolean isValidEmail(Component parent, JTextField field) {
        if (!EMAIL_PATTERN.matcher(field.getText().trim()).matches()) {
            showError(parent, "Please enter a valid email address.");
            field.requestFocusInWindow();
            return false;
        }
        return true;
    }

    /**
     * Validates a string to ensure it can be parsed as a positive number.
     * @param parent The parent component for the dialog.
     * @param field The JTextField containing the number.
     * @param fieldName The name of the field for the error message.
     * @return `true` if the value is a valid positive number, `false` otherwise.
     */
    public static boolean isPositiveNumber(Component parent, JTextField field, String fieldName) {
        try {
            double value = Double.parseDouble(field.getText().trim());
            if (value <= 0) {
                showError(parent, fieldName + " must be a positive number.");
                field.requestFocusInWindow();
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            showError(parent, fieldName + " must be a valid number.");
            field.requestFocusInWindow();
            return false;
        }
    }

    /**
     * Helper method to show a standardized error message dialog.
     */
    private static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }
}
