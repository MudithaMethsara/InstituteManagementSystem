package com.institute.app.utils;

import javax.swing.JTable;
import java.awt.print.PrinterException;
import javax.swing.JOptionPane;

/**
 * A utility class for printing Swing components, such as JTables.
 */
public final class PrintUtils {

    /**
     * Private constructor to prevent instantiation.
     */
    private PrintUtils() {}

    /**
     * Opens the print dialog for a given JTable, allowing the user to print its contents.
     *
     * @param table      The JTable to be printed.
     * @param headerText A title or header text to be printed at the top of the page.
     * @param footerText A footer text, which can include page numbers using "{0}".
     */
    public static void printTable(JTable table, String headerText, String footerText) {
        try {
            // Set up the header and footer formats.
            java.text.MessageFormat header = new java.text.MessageFormat(headerText);
            java.text.MessageFormat footer = new java.text.MessageFormat(footerText);

            // Show the print dialog and print the table.
            // JTable.PrintMode.FIT_WIDTH ensures the table columns fit onto the page width.
            boolean complete = table.print(JTable.PrintMode.FIT_WIDTH, header, footer);

            if (complete) {
                // Optionally notify the user of success.
                JOptionPane.showMessageDialog(null, "Printing has been sent to the printer.", "Printing Complete", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // This block executes if the user cancels the print dialog.
                JOptionPane.showMessageDialog(null, "Printing has been cancelled.", "Printing Cancelled", JOptionPane.WARNING_MESSAGE);
            }
        } catch (PrinterException pe) {
            // This catches errors with the printing system itself.
            JOptionPane.showMessageDialog(null, "A printing error occurred: " + pe.getMessage(), "Printing Error", JOptionPane.ERROR_MESSAGE);
            pe.printStackTrace();
        } catch (SecurityException se) {
            // This may occur in restricted environments (e.g., unsigned applets).
            JOptionPane.showMessageDialog(null, "A security error occurred, printing is not allowed.", "Security Error", JOptionPane.ERROR_MESSAGE);
            se.printStackTrace();
        }
    }
}
