package com.hotel_management.infrastructure.worker;

import javax.mail.*;
import javax.mail.internet.*;
import java.text.Normalizer;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

public class Mailer {
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final int SMTP_PORT = 587; // TLS
    private static final String FROM_EMAIL = "thuannd.dev@gmail.com";
    private static final String FROM_PASSWORD = "eqfs fblr dtvc iqwk";


    // Send email HTML (UTF-8)
    public static void sendHtmlEmail(String toEmail, String subject, String htmlBody) throws MessagingException {
        Session session = createSession();
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM_EMAIL));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

        // Set subject with proper encoding
        msg.setSubject(subject);
        msg.setSentDate(new Date());

        // Set HTML content directly without using MimeBodyPart to avoid activation framework issues
        msg.setContent(htmlBody, "text/html; charset=UTF-8");

        Transport.send(msg);
    }

    /**
     * Send welcome email to newly registered guest (async)
     * @param toEmail Guest email address
     * @param guestName Guest full name
     * @param username Guest username
     */
    public static void sendWelcomeEmail(String toEmail, String guestName, String username) {
        CompletableFuture.runAsync(() -> {
            try {
                String subject = "Welcome to Hotel Management System";
                String htmlBody = buildWelcomeEmailTemplate(toEmail, guestName, username);
                sendHtmlEmail(toEmail, subject, htmlBody);
                System.out.println("Welcome email sent successfully to: " + toEmail);
            } catch (MessagingException e) {
                System.err.println("Failed to send welcome email to: " + toEmail);
                e.printStackTrace();
            }
        });
    }

    /**
     * Send email asynchronously (non-blocking)
     * @param toEmail Recipient email
     * @param subject Email subject
     * @param htmlBody HTML content
     */
    public static void sendEmailAsync(String toEmail, String subject, String htmlBody) {
        CompletableFuture.runAsync(() -> {
            try {
                sendHtmlEmail(toEmail, subject, htmlBody);
                System.out.println("Email sent successfully to: " + toEmail);
            } catch (MessagingException e) {
                System.err.println("Failed to send email to: " + toEmail);
                e.printStackTrace();
            }
        });
    }

    /**
     * Build HTML template for welcome email
     */
    private static String buildWelcomeEmailTemplate(String toEmail, String guestName, String username) {
        // Remove Vietnamese accents to avoid encoding issues
        String normalizedGuestName = removeVietnameseAccents(guestName);

        return "<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head>" +
                "    <meta charset='UTF-8'>" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "    <title>Welcome</title>" +
                "</head>" +
                "<body style='margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;'>" +
                "    <table width='100%' cellpadding='0' cellspacing='0' style='background-color: #f4f4f4; padding: 20px;'>" +
                "        <tr>" +
                "            <td align='center'>" +
                "                <table width='600' cellpadding='0' cellspacing='0' style='background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);'>" +
                "                    <!-- Header -->" +
                "                    <tr>" +
                "                        <td style='background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 40px 30px; text-align: center; border-radius: 8px 8px 0 0;'>" +
                "                            <h1 style='color: #ffffff; margin: 0; font-size: 28px;'>&#127976; Hotel Management System</h1>" +
                "                        </td>" +
                "                    </tr>" +
                "                    <!-- Content -->" +
                "                    <tr>" +
                "                        <td style='padding: 40px 30px;'>" +
                "                            <h2 style='color: #333333; margin-top: 0;'>Hello " + normalizedGuestName + "!</h2>" +
                "                            <p style='color: #666666; font-size: 16px; line-height: 1.6;'>" +
                "                                Thank you for registering an account with our <strong>Hotel Management System</strong>." +
                "                            </p>" +
                "                            <p style='color: #666666; font-size: 16px; line-height: 1.6;'>" +
                "                                Your account has been successfully created with the following information:" +
                "                            </p>" +
                "                            <div style='background-color: #f8f9fa; border-left: 4px solid #667eea; padding: 20px; margin: 20px 0;'>" +
                "                                <p style='margin: 0; color: #333333;'><strong>Username:</strong> " + username + "</p>" +
                "                                <p style='margin: 10px 0 0 0; color: #333333;'><strong>Email:</strong> " + toEmail + "</p>" +
                "                            </div>" +
                "                            <p style='color: #666666; font-size: 16px; line-height: 1.6;'>" +
                "                                You can now log in to the system and start using our services." +
                "                            </p>" +
                "                            <div style='text-align: center; margin: 30px 0;'>" +
                "                                <a href='https://hotel-management.up.railway.app/login' style='display: inline-block; padding: 12px 30px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: #ffffff; text-decoration: none; border-radius: 5px; font-weight: bold;'>Login Now</a>" +
                "                            </div>" +
                "                            <p style='color: #999999; font-size: 14px; line-height: 1.6;'>" +
                "                                If you did not register for this account, please ignore this email." +
                "                            </p>" +
                "                        </td>" +
                "                    </tr>" +
                "                    <!-- Footer -->" +
                "                    <tr>" +
                "                        <td style='background-color: #f8f9fa; padding: 20px 30px; text-align: center; border-radius: 0 0 8px 8px;'>" +
                "                            <p style='color: #999999; font-size: 12px; margin: 0;'>" +
                "                                &copy; 2025 Hotel Management System. All rights reserved." +
                "                            </p>" +
                "                            <p style='color: #999999; font-size: 12px; margin: 10px 0 0 0;'>" +
                "                                This is an automated email, please do not reply to this message." +
                "                            </p>" +
                "                        </td>" +
                "                    </tr>" +
                "                </table>" +
                "            </td>" +
                "        </tr>" +
                "    </table>" +
                "</body>" +
                "</html>";
    }

    /**
     * Remove Vietnamese accents from text
     * Example: "Nguyễn Dương Thuận" -> "Nguyen Duong Thuan"
     * @param text Input text with Vietnamese accents
     * @return Text without accents
     */
    private static String removeVietnameseAccents(String text) {
        if (text == null) {
            return "";
        }

        // Normalize Unicode (decompose accented characters)
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);

        // Remove combining diacritical marks
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String result = pattern.matcher(normalized).replaceAll("");

        // Replace special Vietnamese characters that don't decompose properly
        result = result.replace("\u0111", "d"); // đ
        result = result.replace("\u0110", "D"); // Đ

        return result;
    }

    private static Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", String.valueOf(SMTP_PORT));
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, FROM_PASSWORD);
            }
        };
        return Session.getInstance(props, auth);
    }
}

