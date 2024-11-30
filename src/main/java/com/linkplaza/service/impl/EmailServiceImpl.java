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
        return "<p style=\"margin-bottom: 32px;\">Use the code below to verify your account. It will expire in 15 minutes.</p>"
                + "<h1>" + verificationCode + "</h1>";
    }

    @Override
    public String buildDeleteAccountVerificationMail(String verificationCode) {
        return "<p style=\"margin-bottom: 32px;\">Use the code below to confirm that you want to delete your account. It will expire in 15 minutes.</p>"
                + "<h1>" + verificationCode + "</h1>";
    }

}
