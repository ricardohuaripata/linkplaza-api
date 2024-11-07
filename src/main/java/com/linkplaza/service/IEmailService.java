package com.linkplaza.service;

public interface IEmailService {
    void send(String to, String subject, String content);

    String buildAccountVerifyMail(String verificationCode);

}
