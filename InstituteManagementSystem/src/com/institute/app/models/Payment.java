package com.institute.app.models;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents a financial payment made by a student.
 */
public class Payment {
    private int paymentId;
    private int studentId;
    private Integer courseId; // Optional: link to a specific course
    private BigDecimal amount;
    private Date paymentDate;
    private Integer paymentMethodId;
    private String description;
    private String invoiceNumber;

    // Default constructor
    public Payment() {
    }

    // Constructor with all fields
    public Payment(int paymentId, int studentId, Integer courseId, BigDecimal amount, Date paymentDate, Integer paymentMethodId, String description, String invoiceNumber) {
        this.paymentId = paymentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethodId = paymentMethodId;
        this.description = description;
        this.invoiceNumber = invoiceNumber;
    }

    // --- Getters and Setters ---

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @Override
    public String toString() {
        return "Payment #" + invoiceNumber + " [Amount: " + amount + ", Date: " + paymentDate + "]";
    }
}
