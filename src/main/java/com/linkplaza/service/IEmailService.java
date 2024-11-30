package com.linkplaza.service;

public interface IEmailService {
    void send(String to, String subject, String content);

    String buildAccountVerificationMail(String verificationCode);

    String buildDeleteAccountVerificationMail(String verificationCode);

}
