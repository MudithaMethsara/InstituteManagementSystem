package com.institute.app.dao;

import com.institute.app.config.DBConnection;
import com.institute.app.models.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all database operations for the Payment model.
 */
public class PaymentDAO {

    /**
     * Adds a new payment to the database.
     *
     * @param payment The Payment object to add.
     * @return The payment object with the new payment_id, or null on failure.
     */
    public Payment addPayment(Payment payment) {
        String sql = "INSERT INTO payments (student_id, course_id, amount, payment_date, payment_method_id, description, invoice_number) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, payment.getStudentId());
            pstmt.setObject(2, payment.getCourseId());
            pstmt.setBigDecimal(3, payment.getAmount());
            pstmt.setDate(4, new java.sql.Date(payment.getPaymentDate().getTime()));
            pstmt.setObject(5, payment.getPaymentMethodId());
            pstmt.setString(6, payment.getDescription());
            pstmt.setString(7, payment.getInvoiceNumber());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        payment.setPaymentId(generatedKeys.getInt(1));
                        return payment;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a payment by its ID.
     *
     * @param paymentId The ID of the payment to retrieve.
     * @return A Payment object if found, otherwise null.
     */
    public Payment getPaymentById(int paymentId) {
        String sql = "SELECT * FROM payments WHERE payment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, paymentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPayment(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a list of all payments.
     *
     * @return A List of Payment objects.
     */
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments ORDER BY payment_date DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                payments.add(mapResultSetToPayment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    /**
     * Updates an existing payment's record.
     *
     * @param payment The Payment object with updated information.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updatePayment(Payment payment) {
        String sql = "UPDATE payments SET student_id = ?, course_id = ?, amount = ?, payment_date = ?, payment_method_id = ?, description = ?, invoice_number = ? WHERE payment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, payment.getStudentId());
            pstmt.setObject(2, payment.getCourseId());
            pstmt.setBigDecimal(3, payment.getAmount());
            pstmt.setDate(4, new java.sql.Date(payment.getPaymentDate().getTime()));
            pstmt.setObject(5, payment.getPaymentMethodId());
            pstmt.setString(6, payment.getDescription());
            pstmt.setString(7, payment.getInvoiceNumber());
            pstmt.setInt(8, payment.getPaymentId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a payment from the database.
     *
     * @param paymentId The ID of the payment to delete.
     * @return true if the deletion was successful, false otherwise.
     */
    public boolean deletePayment(int paymentId) {
        String sql = "DELETE FROM payments WHERE payment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, paymentId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Helper method to map a ResultSet row to a Payment object.
     */
    private Payment mapResultSetToPayment(ResultSet rs) throws SQLException {
        return new Payment(
                rs.getInt("payment_id"),
                rs.getInt("student_id"),
                (Integer) rs.getObject("course_id"),
                rs.getBigDecimal("amount"),
                rs.getDate("payment_date"),
                (Integer) rs.getObject("payment_method_id"),
                rs.getString("description"),
                rs.getString("invoice_number")
        );
    }
}
