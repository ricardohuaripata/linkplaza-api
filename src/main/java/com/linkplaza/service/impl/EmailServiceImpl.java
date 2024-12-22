package com.linkplaza.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
    @Autowired
    private Environment env;

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
                "<p style=\"font-size: large; color: #374151; margin: 0;padding: 1.5rem;\">Use the code below to verify your account. It will expire in 15 minutes.</p>"
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
                "<p style=\"font-size: large; color: #374151; margin: 0;padding: 1.5rem;\">Use the code below to confirm that you want to delete your account. It will expire in 15 minutes.</p>"
                +
                "<h2 style=\"font-size: xx-large; margin: 0;padding: 1.5rem;\">" + verificationCode + "</h2>" +
                "</td></tr></table></td></tr></table>";
    }

    @Override
    public String buildResetPasswordMail(String token) {
        String frontend = env.getProperty("app.root.frontend");
        String url = frontend + "/reset-password?token=" + token;
        
        return "<table width=\"100%\" style=\"background-color: #f5f5f5;\">" +
                "<tr><td align=\"center\">" +
                "<table style=\"background-color: #ffffff;\">" +
                "<tr><td>" +
                "<h1 style=\"font-size: xx-large; margin: 0;padding: 1.5rem;\">LinkPlaza</h1>" +

                "<div style=\"padding: 1.5rem;\">" +
                "<h2 style=\"font-size:xx-large;margin:0;\">Forgot your password?</h2>" +
                "<p style=\"font-size:large;color: #374151;\">Click on the button below to reset your password. It will expire in 15 minutes.</p>"
                +
                "</div>" +
                "<div style=\"padding: 1.5rem;display: flex;\">" +
                "<a href=\"" + url + "\" style=\"text-decoration: none;color: #ffffff;\">" +
                "<div style=\"font-size: larger;margin: 0;padding: 1.5rem;background-color: #7e22ce;border-radius: 9999px;\">Reset password</div>"
                +
                "</a>" +
                "</div>" +

                "</td></tr></table></td></tr></table>";
    }

}
