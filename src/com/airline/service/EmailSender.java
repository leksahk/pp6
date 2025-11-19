package com.airline.service;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    private final Session session;
    private final String from;

    public EmailSender(String from, String appPassword) {
        this.from = from;

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        this.session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, appPassword);
            }
        });
    }

    public void send(String to, String subject, String body) throws MessagingException {
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        msg.setSubject(subject);
        msg.setText(body);

        Transport.send(msg);
        System.out.println("Лист надіслано: " + subject);
    }

    public void sendError(String to, String errorTitle, Throwable exception, String errorClass) throws MessagingException {
        StringBuilder body = new StringBuilder();

        String timestamp = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        body.append("--- ЗВІТ ПРО ПОМИЛКУ ---\n\n");
        body.append("Клас: ").append(errorClass).append("\n");
        body.append("Час: ").append(timestamp).append("\n\n"); // ← тут правильний час
        body.append("Повідомлення:\n").append(exception.getMessage()).append("\n\n");
        body.append("Стек трасування:\n");

        for (StackTraceElement el : exception.getStackTrace()) {
            body.append("    at ").append(el.toString()).append("\n");
        }

        send(to, "Критична помилка: " + errorTitle, body.toString());
    }

}


