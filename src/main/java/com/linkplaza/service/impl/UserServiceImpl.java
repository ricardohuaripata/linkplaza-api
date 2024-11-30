package com.linkplaza.service.impl;

import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkplaza.common.AppConstants;
import com.linkplaza.dto.VerifyCodeDto;
import com.linkplaza.entity.User;
import com.linkplaza.entity.VerificationCode;
import com.linkplaza.enumeration.VerificationCodeType;
import com.linkplaza.repository.UserRepository;
import com.linkplaza.repository.VerificationCodeRepository;
import com.linkplaza.service.IEmailService;
import com.linkplaza.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationCodeRepository verificationCodeRepository;
    @Autowired
    private IEmailService emailService;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user found with id " + id));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("No user found with email '" + email + "'"));
    }

    @Override
    public User getAuthenticatedUser() {
        String authUserEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return getUserByEmail(authUserEmail);
    }

    @Override
    @Transactional
    public User verifyAccount(VerifyCodeDto verifyCodeDto) {
        User authUser = getAuthenticatedUser();

        Optional<VerificationCode> verificationCode = verificationCodeRepository
                .findFirstByUserAndCodeAndTypeAndUsedFalseOrderByDateExpirationDesc(authUser,
                        verifyCodeDto.getVerificationCode(), VerificationCodeType.ACCOUNT_VERIFICATION.name());
        // validar existencia del codigo
        if (verificationCode.isEmpty()) {
            throw new IllegalArgumentException("Wrong email or verification code.");
        }
        // validar caducidad del codigo
        if (verificationCode.get().getDateExpiration().before(new Date())) {
            throw new IllegalStateException("The verification code has expired.");
        }
        // si todo es correcto:
        verificationCode.get().setUsed(true);
        verificationCodeRepository.save(verificationCode.get());

        authUser.setEmailVerified(true);
        return userRepository.save(authUser);
    }

    @Override
    @Transactional
    public void deleteAccount(VerifyCodeDto verifyCodeDto) {
        User authUser = getAuthenticatedUser();

        Optional<VerificationCode> verificationCode = verificationCodeRepository
                .findFirstByUserAndCodeAndTypeAndUsedFalseOrderByDateExpirationDesc(authUser,
                        verifyCodeDto.getVerificationCode(), VerificationCodeType.DELETE_ACCOUNT_VERIFICATION.name());
        // validar existencia del codigo
        if (verificationCode.isEmpty()) {
            throw new IllegalArgumentException("Wrong email or verification code.");
        }
        // validar caducidad del codigo
        if (verificationCode.get().getDateExpiration().before(new Date())) {
            throw new IllegalStateException("The verification code has expired.");
        }
        // si todo es correcto:
        verificationCodeRepository.deleteByUser(authUser);
        userRepository.delete(authUser);
    }

    @Override
    @Transactional
    public void sendAccountVerificationCode() {
        User authUser = getAuthenticatedUser();

        if (authUser.isEmailVerified()) {
            throw new IllegalStateException("Your account is already verified.");
        }

        String verificationcode = generateVerificationCode(authUser, VerificationCodeType.ACCOUNT_VERIFICATION.name());

        String mailTo = authUser.getEmail();
        String mailSubject = "Your code: " + verificationcode;
        String mailContent = emailService.buildAccountVerificationMail(verificationcode);
        // emailService.send(mailTo, mailSubject, mailContent);

    }

    @Override
    @Transactional
    public void sendDeleteAccountVerificationCode() {
        User authUser = getAuthenticatedUser();

        String verificationcode = generateVerificationCode(authUser,
                VerificationCodeType.DELETE_ACCOUNT_VERIFICATION.name());

        String mailTo = authUser.getEmail();
        String mailSubject = "Your code: " + verificationcode;
        String mailContent = emailService.buildDeleteAccountVerificationMail(verificationcode);
        // emailService.send(mailTo, mailSubject, mailContent);

    }

    public String generateVerificationCode(User user, String type) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int num = (int) (Math.random() * 10);
            code.append(num);
        }
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setUser(user);
        verificationCode.setCode(code.toString());
        verificationCode.setType(type);
        verificationCode.setUsed(false);
        verificationCode
                .setDateExpiration(new Date(System.currentTimeMillis() + AppConstants.VERIFICATION_CODE_EXPIRATION));
        verificationCodeRepository.save(verificationCode);
        return code.toString();
    }

}