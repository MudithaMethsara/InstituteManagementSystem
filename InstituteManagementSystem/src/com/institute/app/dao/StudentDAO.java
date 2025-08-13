package com.institute.app.dao;

import com.institute.app.config.DBConnection;
import com.institute.app.models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all database operations for the Student model.
 * This includes Create, Read, Update, and Delete (CRUD) operations.
 */
public class StudentDAO {

    /**
     * Adds a new student to the database.
     *
     * @param student The Student object to add.
     * @return The student object with the new student_id assigned by the database, or null on failure.
     */
    public Student addStudent(Student student) {
        // The SQL query to insert a new student record.
        String sql = "INSERT INTO students (first_name, last_name, date_of_birth, email, phone, address, enrollment_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Using try-with-resources to ensure the connection and statement are closed automatically.
        try (Connection conn = DBConnection.getConnection();
             // The `RETURN_GENERATED_KEYS` flag tells the JDBC driver to return the auto-incremented ID.
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set the parameters for the prepared statement.
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            // Convert java.util.Date to java.sql.Date for the database.
            pstmt.setDate(3, new java.sql.Date(student.getDateOfBirth().getTime()));
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getPhone());
            pstmt.setString(6, student.getAddress());
            pstmt.setDate(7, new java.sql.Date(student.getEnrollmentDate().getTime()));

            // Execute the insert statement.
            int affectedRows = pstmt.executeUpdate();

            // Check if the insert was successful.
            if (affectedRows > 0) {
                // Retrieve the auto-generated key (the new student_id).
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // Set the ID on the original student object and return it.
                        student.setStudentId(generatedKeys.getInt(1));
                        return student;
                    }
                }
            }
        } catch (SQLException e) {
            // In a real app, use a logging framework.
            e.printStackTrace();
        }
        // Return null if the operation failed.
        return null;
    }

    /**
     * Retrieves a single student from the database by their ID.
     *
     * @param studentId The ID of the student to retrieve.
     * @return A Student object if found, otherwise null.
     */
    public Student getStudentById(int studentId) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // If a record is found, create and return a new Student object.
                    return mapResultSetToStudent(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a list of all students from the database.
     *
     * @return A List of Student objects. The list will be empty if no students are found.
     */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY last_name, first_name";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Iterate through the result set and add each student to the list.
            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    /**
     * Updates an existing student's record in the database.
     *
     * @param student The Student object with updated information.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET first_name = ?, last_name = ?, date_of_birth = ?, email = ?, phone = ?, address = ?, enrollment_date = ? WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setDate(3, new java.sql.Date(student.getDateOfBirth().getTime()));
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getPhone());
            pstmt.setString(6, student.getAddress());
            pstmt.setDate(7, new java.sql.Date(student.getEnrollmentDate().getTime()));
            pstmt.setInt(8, student.getStudentId());

            // `executeUpdate` returns the number of rows affected.
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a student from the database.
     *
     * @param studentId The ID of the student to delete.
     * @return true if the deletion was successful, false otherwise.
     */
    public boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // This might fail if there are foreign key constraints (e.g., enrollments).
            // The ON DELETE CASCADE in the SQL schema handles this.
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Helper method to map a ResultSet row to a Student object.
     *
     * @param rs The ResultSet to map.
     * @return A new Student object.
     * @throws SQLException if a database access error occurs.
     */
    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        return new Student(
                rs.getInt("student_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getDate("date_of_birth"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("address"),
                rs.getDate("enrollment_date")
        );
    }
}
