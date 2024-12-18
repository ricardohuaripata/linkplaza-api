package com.linkplaza.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkplaza.service.IEmailService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    @Async
    public void send(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8");
            messageHelper.setText(content, true);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            mailSender.send(message);
            log.info("*** Mail sent!! :) ***");
        } catch (MessagingException e) {
            log.error("*** Exception occurred when sending mail :( ***", e);
            throw new IllegalStateException("Failed to send email");
        }
    }

    @Override
    public String buildAccountVerificationMail(String verificationCode) {
        return "<table width=\"100%\" style=\"background-color: #f5f5f5;\">" +
                "<tr><td align=\"center\">" +
                "<table style=\"background-color: #ffffff;\">" +
                "<tr><td>" +
                "<h1 style=\"font-size: xx-large; margin: 0;padding: 1.5rem;\">LinkPlaza</h1>" +
                "<p style=\"font-size: larger; color: #374151; margin: 0;padding: 1.5rem;\">Use the code below to verify your account. It will expire in 15 minutes.</p>"
                +
                "<h2 style=\"font-size: xx-large; margin: 0;padding: 1.5rem;\">" + verificationCode + "</h2>" +
                "</td></tr></table></td></tr></table>";
    }

    @Override
    public String buildDeleteAccountVerificationMail(String verificationCode) {
        return "<table width=\"100%\" style=\"background-color: #f5f5f5;\">" +
                "<tr><td align=\"center\">" +
                "<table style=\"background-color: #ffffff;\">" +
                "<tr><td>" +
                "<h1 style=\"font-size: xx-large; margin: 0;padding: 1.5rem;\">LinkPlaza</h1>" +
                "<p style=\"font-size: larger; color: #374151; margin: 0;padding: 1.5rem;\">Use the code below to confirm that you want to delete your account. It will expire in 15 minutes.</p>"
                +
                "<h2 style=\"font-size: xx-large; margin: 0;padding: 1.5rem;\">" + verificationCode + "</h2>" +
                "</td></tr></table></td></tr></table>";
    }

}
