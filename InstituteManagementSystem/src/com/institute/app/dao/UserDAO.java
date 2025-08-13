package com.institute.app.dao;

import com.institute.app.config.DBConnection;
import com.institute.app.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles user authentication.
 *
 * IMPORTANT SECURITY WARNING:
 * The current implementation of the `authenticate` method is for demonstration
 * purposes only and is highly insecure. It compares a plain text password
 * against the database, which is a critical security vulnerability.
 * A real-world application MUST use a strong, salted password hashing algorithm
 * like BCrypt or Argon2. The developer using this code is responsible for
 * implementing proper password security.
 */
public class UserDAO {

    /**
     * Authenticates a user by checking the provided username and plain text password
     * against the database.
     *
     * @param username The user's username.
     * @param password The user's plain text password.
     * @return A `User` object if the credentials are correct; `null` otherwise.
     */
    public User authenticate(String username, String password) {
        // SQL query to find a user with a matching username and (insecure) plain text password.
        String sql = "SELECT * FROM users WHERE username = ? AND password_hash = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password); // This is the insecure part.

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // If a user is found, map the ResultSet to a User object.
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // In a real app, this should be logged to a file or monitoring service.
        }
        // Return null if no matching user was found or if an error occurred.
        return null;
    }

    /**
     * Helper method to map a ResultSet row to a User object.
     * @param rs The ResultSet to map.
     * @return A new User object.
     * @throws SQLException If a database access error occurs.
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("user_id"),
                rs.getString("username"),
                rs.getString("password_hash"),
                rs.getString("email"),
                rs.getInt("role_id"),
                rs.getTimestamp("created_at")
        );
    }
}
