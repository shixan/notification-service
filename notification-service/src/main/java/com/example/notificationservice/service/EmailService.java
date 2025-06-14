package com.example.notificationservice.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender sender;

    public EmailService(JavaMailSender sender) {
        this.sender = sender;
    }
    public void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, false);
            sender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

