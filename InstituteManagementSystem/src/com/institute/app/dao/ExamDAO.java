package com.institute.app.dao;

import com.institute.app.config.DBConnection;
import com.institute.app.models.Exam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all database operations for the Exam model.
 */
public class ExamDAO {

    /**
     * Adds a new exam to the database.
     *
     * @param exam The Exam object to add.
     * @return The exam object with the new exam_id, or null on failure.
     */
    public Exam addExam(Exam exam) {
        String sql = "INSERT INTO exams (exam_name, exam_date, course_id, max_marks) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, exam.getExamName());
            // The `exam_date` column in the schema is DATETIME, so Timestamp is appropriate.
            pstmt.setTimestamp(2, new java.sql.Timestamp(exam.getExamDate().getTime()));
            pstmt.setObject(3, exam.getCourseId());
            pstmt.setInt(4, exam.getMaxMarks());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        exam.setExamId(generatedKeys.getInt(1));
                        return exam;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves an exam by its ID.
     *
     * @param examId The ID of the exam to retrieve.
     * @return An Exam object if found, otherwise null.
     */
    public Exam getExamById(int examId) {
        String sql = "SELECT * FROM exams WHERE exam_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, examId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToExam(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a list of all exams.
     *
     * @return A List of Exam objects.
     */
    public List<Exam> getAllExams() {
        List<Exam> exams = new ArrayList<>();
        String sql = "SELECT * FROM exams ORDER BY exam_date DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                exams.add(mapResultSetToExam(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exams;
    }

    /**
     * Updates an existing exam's record.
     *
     * @param exam The Exam object with updated information.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateExam(Exam exam) {
        String sql = "UPDATE exams SET exam_name = ?, exam_date = ?, course_id = ?, max_marks = ? WHERE exam_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, exam.getExamName());
            pstmt.setTimestamp(2, new java.sql.Timestamp(exam.getExamDate().getTime()));
            pstmt.setObject(3, exam.getCourseId());
            pstmt.setInt(4, exam.getMaxMarks());
            pstmt.setInt(5, exam.getExamId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes an exam from the database.
     *
     * @param examId The ID of the exam to delete.
     * @return true if the deletion was successful, false otherwise.
     */
    public boolean deleteExam(int examId) {
        String sql = "DELETE FROM exams WHERE exam_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, examId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Helper method to map a ResultSet row to an Exam object.
     */
    private Exam mapResultSetToExam(ResultSet rs) throws SQLException {
        return new Exam(
                rs.getInt("exam_id"),
                rs.getString("exam_name"),
                rs.getTimestamp("exam_date"), // Use getTimestamp for DATETIME columns
                (Integer) rs.getObject("course_id"),
                rs.getInt("max_marks")
        );
    }
}
