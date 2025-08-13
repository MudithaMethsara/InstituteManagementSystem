package com.institute.app.gui;

import com.institute.app.gui.panels.CoursePanel;
import com.institute.app.gui.panels.ExamPanel;
import com.institute.app.gui.panels.PaymentPanel;
import com.institute.app.gui.panels.ReportsPanel;
import com.institute.app.gui.panels.StudentPanel;
import com.institute.app.gui.panels.TeacherPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

/**
 * The main window of the application, displayed after successful login.
 * It uses a JTabbedPane to organize the different management panels.
 */
public class Dashboard extends JFrame {

    private JTabbedPane tabbedPane;

    public Dashboard() {
        initComponents();
    }

    private void initComponents() {
        // --- Frame Setup ---
        setTitle("Institute Management System - Dashboard");
        // Set a larger default size for the main application window.
        setSize(1200, 800);
        // Exit the application when this frame is closed.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Center the frame on the screen.
        setLocationRelativeTo(null);
        // Use BorderLayout for the main frame.
        setLayout(new BorderLayout());

        // --- Header ---
        JLabel headerLabel = new JLabel("Welcome to the Institute Management System", SwingConstants.CENTER);
        headerLabel.setFont(new java.awt.Font("Segoe UI", 1, 24));
        // Add some padding to the header.
        headerLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(headerLabel, BorderLayout.NORTH);

        // --- Tabbed Pane for Main Content ---
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new java.awt.Font("Segoe UI", 0, 14));

        // Add placeholder panels for each module.
        // In a real application, these would be instances of the actual panel classes.
        tabbedPane.addTab("Students", new StudentPanel());
        tabbedPane.addTab("Teachers", new TeacherPanel());
        tabbedPane.addTab("Courses", new CoursePanel());
        tabbedPane.addTab("Exams", new ExamPanel());
        tabbedPane.addTab("Payments", new PaymentPanel());
        tabbedPane.addTab("Reports", new ReportsPanel());

        // Add a placeholder for Timetable as well, as it's in the file structure
        JPanel timetablePanel = new JPanel();
        timetablePanel.add(new JLabel("Timetable Management Coming Soon!"));
        tabbedPane.addTab("Timetable", timetablePanel);


        // Add the tabbed pane to the center of the frame.
        add(tabbedPane, BorderLayout.CENTER);

        // --- Footer ---
        JLabel footerLabel = new JLabel("Status: Connected | User: Admin", SwingConstants.LEFT);
        footerLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(footerLabel, BorderLayout.SOUTH);
    }

    // A simple placeholder panel to be used until the real panels are built.
    private JPanel createPlaceholderPanel(String name) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(name + " management will be here.");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
}
