package com.institute.app.models;

/**
 * Represents a course offered by the institute.
 */
public class Course {
    private int courseId;
    private String courseName;
    private String courseCode;
    private String description;
    private Integer credits;
    private Integer teacherId; // Foreign key to teachers table

    // Default constructor
    public Course() {
    }

    // Constructor with all fields
    public Course(int courseId, String courseName, String courseCode, String description, Integer credits, Integer teacherId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.description = description;
        this.credits = credits;
        this.teacherId = teacherId;
    }

    // --- Getters and Setters ---

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        // Useful for display in UI components
        return courseName + " (" + courseCode + ")";
    }
}
