package com.institute.app.models;

import java.util.Date;

/**
 * Represents an exam for a particular course.
 */
public class Exam {
    private int examId;
    private String examName;
    private Date examDate;
    private Integer courseId;
    private int maxMarks;

    // Default constructor
    public Exam() {
    }

    // Constructor with all fields
    public Exam(int examId, String examName, Date examDate, Integer courseId, int maxMarks) {
        this.examId = examId;
        this.examName = examName;
        this.examDate = examDate;
        this.courseId = courseId;
        this.maxMarks = maxMarks;
    }

    // --- Getters and Setters ---

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(int maxMarks) {
        this.maxMarks = maxMarks;
    }

    @Override
    public String toString() {
        return examName + " (Date: " + examDate + ")";
    }
}
