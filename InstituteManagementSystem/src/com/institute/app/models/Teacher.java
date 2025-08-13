package com.institute.app.models;

import java.util.Date;

/**
 * Represents a teacher or staff member in the institute.
 */
public class Teacher {
    private int teacherId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String subjectSpecialization;
    private Date hireDate;
    private Integer userId; // Using Integer to allow for null user_id

    // Default constructor
    public Teacher() {
    }

    // Constructor with all fields
    public Teacher(int teacherId, String firstName, String lastName, String email, String phone, String subjectSpecialization, Date hireDate, Integer userId) {
        this.teacherId = teacherId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.subjectSpecialization = subjectSpecialization;
        this.hireDate = hireDate;
        this.userId = userId;
    }

    // --- Getters and Setters ---

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSubjectSpecialization() {
        return subjectSpecialization;
    }

    public void setSubjectSpecialization(String subjectSpecialization) {
        this.subjectSpecialization = subjectSpecialization;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        // This is useful for displaying teachers in a JComboBox or JList
        return firstName + " " + lastName;
    }
}
