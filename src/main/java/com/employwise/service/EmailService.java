package com.employwise.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Slf4j
@Service
public class EmailService {
    @Value("${mail.email}")
    private String email;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.smtp}")
    private String smtp;

    @Value("${mail.port}")
    private String port;

    @Async
    public void sendEmail(String subject, String message, String to){
        String from = this.email;
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", this.smtp);
        properties.put("mail.smtp.port", this.port);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
        MimeMessage m  =new MimeMessage(session);

        try {
            m.setFrom(new InternetAddress(from));
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            m.setSubject(subject);
            m.setContent(message, "text/html");
            Transport.send(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
