package com.institute.app.dao;

import com.institute.app.config.DBConnection;
import com.institute.app.models.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all database operations for the Teacher model.
 */
public class TeacherDAO {

    /**
     * Adds a new teacher to the database.
     *
     * @param teacher The Teacher object to add.
     * @return The teacher object with the new teacher_id, or null on failure.
     */
    public Teacher addTeacher(Teacher teacher) {
        String sql = "INSERT INTO teachers (first_name, last_name, email, phone, subject_specialization, hire_date, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, teacher.getFirstName());
            pstmt.setString(2, teacher.getLastName());
            pstmt.setString(3, teacher.getEmail());
            pstmt.setString(4, teacher.getPhone());
            pstmt.setString(5, teacher.getSubjectSpecialization());
            pstmt.setDate(6, new java.sql.Date(teacher.getHireDate().getTime()));
            // Handle nullable user_id
            if (teacher.getUserId() != null) {
                pstmt.setInt(7, teacher.getUserId());
            } else {
                pstmt.setNull(7, Types.INTEGER);
            }

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        teacher.setTeacherId(generatedKeys.getInt(1));
                        return teacher;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a teacher by their ID.
     *
     * @param teacherId The ID of the teacher to retrieve.
     * @return A Teacher object if found, otherwise null.
     */
    public Teacher getTeacherById(int teacherId) {
        String sql = "SELECT * FROM teachers WHERE teacher_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, teacherId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTeacher(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a list of all teachers.
     *
     * @return A List of Teacher objects.
     */
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teachers ORDER BY last_name, first_name";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                teachers.add(mapResultSetToTeacher(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    /**
     * Updates an existing teacher's record.
     *
     * @param teacher The Teacher object with updated information.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateTeacher(Teacher teacher) {
        String sql = "UPDATE teachers SET first_name = ?, last_name = ?, email = ?, phone = ?, subject_specialization = ?, hire_date = ?, user_id = ? WHERE teacher_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, teacher.getFirstName());
            pstmt.setString(2, teacher.getLastName());
            pstmt.setString(3, teacher.getEmail());
            pstmt.setString(4, teacher.getPhone());
            pstmt.setString(5, teacher.getSubjectSpecialization());
            pstmt.setDate(6, new java.sql.Date(teacher.getHireDate().getTime()));
            if (teacher.getUserId() != null) {
                pstmt.setInt(7, teacher.getUserId());
            } else {
                pstmt.setNull(7, Types.INTEGER);
            }
            pstmt.setInt(8, teacher.getTeacherId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a teacher from the database.
     *
     * @param teacherId The ID of the teacher to delete.
     * @return true if the deletion was successful, false otherwise.
     */
    public boolean deleteTeacher(int teacherId) {
        String sql = "DELETE FROM teachers WHERE teacher_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, teacherId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Helper method to map a ResultSet row to a Teacher object.
     */
    private Teacher mapResultSetToTeacher(ResultSet rs) throws SQLException {
        // Use getObject for nullable columns to avoid returning 0 for NULL.
        Integer userId = (Integer) rs.getObject("user_id");

        return new Teacher(
                rs.getInt("teacher_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("subject_specialization"),
                rs.getDate("hire_date"),
                userId
        );
    }
}
