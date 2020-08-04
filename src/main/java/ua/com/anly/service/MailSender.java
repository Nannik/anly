package ua.com.anly.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSender {
    private final JavaMailSender mailSender;

    public MailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.username}")
    private String username;

    public void send(String emailTo, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        mimeMessage.setContent(message, "text/html");

        helper.setFrom(username);
        helper.setTo(emailTo);
        helper.setSubject(subject);

        mailSender.send(mimeMessage);
    }
}
