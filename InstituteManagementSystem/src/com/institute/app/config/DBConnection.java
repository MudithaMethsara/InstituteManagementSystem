package com.institute.app.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties props = new Properties();
                // The properties file should be in the classpath.
                // For a Maven/Gradle structure, it would be in `src/main/resources`.
                // In this NetBeans-style structure, we'll place it alongside the config classes
                // and ensure the `resources` folder is on the classpath during the build.
                try (InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream("com/institute/app/config/AppSettings.properties")) {
                    if (inputStream == null) {
                        // A more robust solution might try to load from a file path as a fallback.
                        throw new RuntimeException("Sorry, unable to find AppSettings.properties in the classpath.");
                    }
                    props.load(inputStream);
                }

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                // Load the MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Attempt to establish the connection
                connection = DriverManager.getConnection(url, user, password);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("MySQL JDBC Driver not found. Ensure `mysql-connector-j-*.jar` is in the `lib` directory and on the classpath.");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to connect to the database. Please check your DB settings in `AppSettings.properties` and ensure the database is running.", e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to read `AppSettings.properties`.", e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null; // Set to null to allow for reconnection if needed
            } catch (SQLException e) {
                // Log this, but don't rethrow as the application might be shutting down.
                e.printStackTrace();
            }
        }
    }
}
