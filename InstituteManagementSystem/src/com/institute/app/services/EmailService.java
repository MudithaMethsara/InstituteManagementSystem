package com.institute.app.services;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Handles sending emails, such as notifications and receipts.
 * Reads configuration from `EmailConfig.properties`.
 */
public class EmailService {

    private final Properties emailProps;
    private final Authenticator authenticator;
    private final String fromEmail;

    /**
     * Initializes the EmailService by loading configuration from the properties file.
     */
    public EmailService() {
        try {
            // Load email configuration from the classpath.
            emailProps = new Properties();
            InputStream inputStream = EmailService.class.getClassLoader().getResourceAsStream("com/institute/app/config/EmailConfig.properties");
            if (inputStream == null) {
                throw new RuntimeException("EmailConfig.properties not found in the classpath.");
            }
            emailProps.load(inputStream);

            // Store the "From" address and create an authenticator for the SMTP server.
            fromEmail = emailProps.getProperty("mail.from");
            final String username = emailProps.getProperty("mail.username");
            final String password = emailProps.getProperty("mail.password");

            authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            };

        } catch (IOException e) {
            e.printStackTrace();
            // A more robust application might disable email features or show an error dialog.
            throw new RuntimeException("Failed to load email configuration.", e);
        }
    }

    /**
     * Sends an email with HTML content.
     *
     * @param to       The recipient's email address.
     * @param subject  The subject of the email.
     * @param htmlBody The HTML content of the email body.
     * @return `true` if the email was sent successfully, `false` otherwise.
     */
    public boolean sendEmail(String to, String subject, String htmlBody) {
        // Create a mail session with the loaded properties and authenticator.
        Session session = Session.getInstance(emailProps, authenticator);

        try {
            // Create a new MimeMessage object.
            Message message = new MimeMessage(session);
            // Set the sender's address.
            message.setFrom(new InternetAddress(fromEmail));
            // Set the recipient's address.
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            // Set the email subject.
            message.setSubject(subject);
            // Set the email content to be HTML.
            message.setContent(htmlBody, "text/html; charset=utf-8");

            // Send the message.
            Transport.send(message);

            System.out.println("Email sent successfully to " + to);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Failed to send email: " + e.getMessage());
            return false;
        }
    }

    /**
     * Loads an email template from the resources folder.
     *
     * @param templateName The name of the template file (e.g., "welcome_email.html").
     * @return The content of the template as a String.
     * @throws IOException If the template file cannot be read.
     */
    public String loadEmailTemplate(String templateName) throws IOException {
        String resourcePath = "/email-templates/" + templateName; // Note the leading slash for resource loading
        try (InputStream inputStream = EmailService.class.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IOException("Template file not found: " + resourcePath);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }
}
