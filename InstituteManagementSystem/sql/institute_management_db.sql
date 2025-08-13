-- =================================================================
-- SQL Schema for Institute Management System
-- Database: MySQL 8
-- =================================================================

-- It's often better to handle database creation and selection outside the script
-- to give the user more control. This script assumes `institute_management_db` exists and is in use.
-- Example commands to run before this script:
-- CREATE DATABASE IF NOT EXISTS institute_management_db;
-- USE institute_management_db;

-- Drop tables in reverse order of dependency to avoid foreign key errors
DROP TABLE IF EXISTS `payments`;
DROP TABLE IF EXISTS `payment_methods`;
DROP TABLE IF EXISTS `exam_results`;
DROP TABLE IF EXISTS `exams`;
DROP TABLE IF EXISTS `enrollments`;
DROP TABLE IF EXISTS `students`;
DROP TABLE IF EXISTS `courses`;
DROP TABLE IF EXISTS `teachers`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `roles`;


-- ---------------------------------
-- Table Structure for `roles`
-- ---------------------------------
CREATE TABLE `roles` (
    `role_id` INT AUTO_INCREMENT PRIMARY KEY,
    `role_name` VARCHAR(50) NOT NULL UNIQUE COMMENT 'e.g., Admin, Teacher, Accountant'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ---------------------------------
-- Table Structure for `users`
-- ---------------------------------
CREATE TABLE `users` (
    `user_id` INT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `password_hash` VARCHAR(255) NOT NULL COMMENT 'Store hashed passwords, not plain text',
    `email` VARCHAR(100) NOT NULL UNIQUE,
    `role_id` INT,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`role_id`) REFERENCES `roles`(`role_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ---------------------------------
-- Table Structure for `teachers`
-- ---------------------------------
CREATE TABLE `teachers` (
    `teacher_id` INT AUTO_INCREMENT PRIMARY KEY,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `email` VARCHAR(100) NOT NULL UNIQUE,
    `phone` VARCHAR(20),
    `subject_specialization` VARCHAR(100),
    `hire_date` DATE,
    `user_id` INT UNIQUE COMMENT 'Optional link to a user account for login',
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ---------------------------------
-- Table Structure for `courses`
-- ---------------------------------
CREATE TABLE `courses` (
    `course_id` INT AUTO_INCREMENT PRIMARY KEY,
    `course_name` VARCHAR(100) NOT NULL,
    `course_code` VARCHAR(20) UNIQUE,
    `description` TEXT,
    `credits` INT,
    `teacher_id` INT COMMENT 'Lead teacher for the course',
    FOREIGN KEY (`teacher_id`) REFERENCES `teachers`(`teacher_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ---------------------------------
-- Table Structure for `students`
-- ---------------------------------
CREATE TABLE `students` (
    `student_id` INT AUTO_INCREMENT PRIMARY KEY,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `date_of_birth` DATE,
    `email` VARCHAR(100) NOT NULL UNIQUE,
    `phone` VARCHAR(20),
    `address` TEXT,
    `enrollment_date` DATE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ---------------------------------
-- Table Structure for `enrollments` (Junction Table)
-- ---------------------------------
CREATE TABLE `enrollments` (
    `enrollment_id` INT AUTO_INCREMENT PRIMARY KEY,
    `student_id` INT NOT NULL,
    `course_id` INT NOT NULL,
    `enrollment_date` DATE NOT NULL,
    `grade` VARCHAR(5) COMMENT 'e.g., A+, B, F',
    UNIQUE KEY `uk_student_course` (`student_id`, `course_id`),
    FOREIGN KEY (`student_id`) REFERENCES `students`(`student_id`) ON DELETE CASCADE,
    FOREIGN KEY (`course_id`) REFERENCES `courses`(`course_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ---------------------------------
-- Table Structure for `exams`
-- ---------------------------------
CREATE TABLE `exams` (
    `exam_id` INT AUTO_INCREMENT PRIMARY KEY,
    `exam_name` VARCHAR(100) NOT NULL,
    `exam_date` DATETIME NOT NULL,
    `course_id` INT,
    `max_marks` INT NOT NULL DEFAULT 100,
    FOREIGN KEY (`course_id`) REFERENCES `courses`(`course_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ---------------------------------
-- Table Structure for `exam_results`
-- ---------------------------------
CREATE TABLE `exam_results` (
    `result_id` INT AUTO_INCREMENT PRIMARY KEY,
    `exam_id` INT NOT NULL,
    `student_id` INT NOT NULL,
    `marks_obtained` DECIMAL(5, 2) NOT NULL,
    `comments` TEXT,
    UNIQUE KEY `uk_exam_student` (`exam_id`, `student_id`),
    FOREIGN KEY (`exam_id`) REFERENCES `exams`(`exam_id`) ON DELETE CASCADE,
    FOREIGN KEY (`student_id`) REFERENCES `students`(`student_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ---------------------------------
-- Table Structure for `payment_methods`
-- ---------------------------------
CREATE TABLE `payment_methods` (
    `method_id` INT AUTO_INCREMENT PRIMARY KEY,
    `method_name` VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ---------------------------------
-- Table Structure for `payments`
-- ---------------------------------
CREATE TABLE `payments` (
    `payment_id` INT AUTO_INCREMENT PRIMARY KEY,
    `student_id` INT NOT NULL,
    `course_id` INT COMMENT 'Optional, if payment is for a specific course',
    `amount` DECIMAL(10, 2) NOT NULL,
    `payment_date` DATE NOT NULL,
    `payment_method_id` INT,
    `description` VARCHAR(255),
    `invoice_number` VARCHAR(50) UNIQUE,
    FOREIGN KEY (`student_id`) REFERENCES `students`(`student_id`) ON DELETE CASCADE,
    FOREIGN KEY (`course_id`) REFERENCES `courses`(`course_id`) ON DELETE SET NULL,
    FOREIGN KEY (`payment_method_id`) REFERENCES `payment_methods`(`method_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- =================================================================
-- Seed Data
-- =================================================================

-- Insert default roles
INSERT INTO `roles` (`role_name`) VALUES
('Admin'),
('Teacher'),
('Accountant'),
('Receptionist');

-- Insert default payment methods
INSERT INTO `payment_methods` (`method_name`) VALUES
('Cash'),
('Credit Card'),
('Bank Transfer'),
('Online Payment');

-- Insert a default admin user (password is 'admin_password' - should be hashed in a real app)
-- NOTE: This is a placeholder hash. A real application must generate a secure hash.
INSERT INTO `users` (`username`, `password_hash`, `email`, `role_id`) VALUES
('admin', '$2a$10$D.pG3sZJc.Mv2fE4b.t3o.xY9z.L8pQz/wX7kZ.uY5g.vX6kZ.uY5', 'admin@institute.com', (SELECT role_id FROM roles WHERE role_name = 'Admin'));

-- Insert a sample teacher
INSERT INTO `teachers` (`first_name`, `last_name`, `email`, `phone`, `subject_specialization`, `hire_date`) VALUES
('John', 'Doe', 'john.doe@institute.com', '123-456-7890', 'Computer Science', '2023-01-15');

-- Insert a sample course
INSERT INTO `courses` (`course_name`, `course_code`, `description`, `credits`, `teacher_id`) VALUES
('Introduction to Java', 'CS101', 'A beginner-friendly course on Java programming.', 3, (SELECT teacher_id FROM teachers WHERE email = 'john.doe@institute.com'));

-- Insert a sample student
INSERT INTO `students` (`first_name`, `last_name`, `date_of_birth`, `email`, `phone`, `address`, `enrollment_date`) VALUES
('Jane', 'Smith', '2005-05-20', 'jane.smith@email.com', '987-654-3210', '123 Main St, Anytown', '2024-08-01');

-- Enroll the sample student in the sample course
INSERT INTO `enrollments` (`student_id`, `course_id`, `enrollment_date`) VALUES
((SELECT student_id FROM students WHERE email = 'jane.smith@email.com'), (SELECT course_id FROM courses WHERE course_code = 'CS101'), '2024-08-01');

-- Insert a sample payment for the student
INSERT INTO `payments` (`student_id`, `course_id`, `amount`, `payment_date`, `payment_method_id`, `description`, `invoice_number`) VALUES
((SELECT student_id FROM students WHERE email = 'jane.smith@email.com'),
 (SELECT course_id FROM courses WHERE course_code = 'CS101'),
 500.00,
 CURDATE(),
 (SELECT method_id FROM payment_methods WHERE method_name = 'Online Payment'),
 'Tuition Fee for Introduction to Java',
 CONCAT('INV-', DATE_FORMAT(CURDATE(), '%Y%m%d'), '-001')
);

-- =================================================================
-- End of Script
-- =================================================================
