package com.institute.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * A utility class for common date and time operations.
 * This helps centralize date formatting and parsing logic.
 */
public final class DateUtils {

    // Define standard date formats to be used across the application.
    private static final String DB_DATE_FORMAT = "yyyy-MM-dd";
    private static final String UI_DATE_FORMAT = "dd-MMM-yyyy"; // e.g., 25-Aug-2024

    private static final SimpleDateFormat dbSdf = new SimpleDateFormat(DB_DATE_FORMAT);
    private static final SimpleDateFormat uiSdf = new SimpleDateFormat(UI_DATE_FORMAT);

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private DateUtils() {}

    /**
     * Formats a Date object into a string suitable for display in the UI (e.g., "25-Aug-2024").
     *
     * @param date The date to format.
     * @return A formatted date string, or an empty string if the input is null.
     */
    public static String formatToUI(Date date) {
        if (date == null) {
            return "";
        }
        return uiSdf.format(date);
    }

    /**
     * Formats a Date object into a string suitable for SQL queries (e.g., "2024-08-25").
     *
     * @param date The date to format.
     * @return A formatted date string, or null if the input is null.
     */
    public static String formatToDB(Date date) {
        if (date == null) {
            return null;
        }
        return dbSdf.format(date);
    }

    /**
     * Parses a date string from the UI format ("dd-MMM-yyyy") into a Date object.
     *
     * @param dateString The string to parse.
     * @return A Date object, or null if the string is null, empty, or invalid.
     */
    public static Date parseFromUI(String dateString) {
        if (ValidationUtils.isNullOrEmpty(dateString)) {
            return null;
        }
        try {
            return uiSdf.parse(dateString);
        } catch (ParseException e) {
            // Inform the user about the incorrect format.
            JOptionPane.showMessageDialog(null,
                    "Invalid date format. Please use " + UI_DATE_FORMAT + " (e.g., 25-Aug-2024).",
                    "Date Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Converts a java.util.Date to a java.sql.Date.
     * @param utilDate The java.util.Date to convert.
     * @return A java.sql.Date object, or null if the input is null.
     */
    public static java.sql.Date toSqlDate(java.util.Date utilDate) {
        if (utilDate == null) {
            return null;
        }
        return new java.sql.Date(utilDate.getTime());
    }
}
