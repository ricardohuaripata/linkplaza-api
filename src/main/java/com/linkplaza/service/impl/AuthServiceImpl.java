package com.linkplaza.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkplaza.common.AppConstants;
import com.linkplaza.dto.AccountVerifyDto;
import com.linkplaza.dto.SignInDto;
import com.linkplaza.dto.SignUpDto;
import com.linkplaza.entity.User;
import com.linkplaza.entity.VerificationCode;
import com.linkplaza.enumeration.Role;
import com.linkplaza.enumeration.VerificationCodeType;
import com.linkplaza.repository.UserRepository;
import com.linkplaza.repository.VerificationCodeRepository;
import com.linkplaza.service.IAuthService;
import com.linkplaza.service.IEmailService;
import com.linkplaza.service.IUserService;

@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IUserService userService;
    @Autowired
    private IEmailService emailService;
    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Override
    @Transactional
    public User signUp(SignUpDto signUpDto) {

        Optional<User> existingUser = userRepository.findByEmail(signUpDto.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("The email '" + signUpDto.getEmail() + "' is already taken.");
        }

        User newUser = new User();
        Date currentDate = new Date();
        newUser.setEmail(signUpDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        newUser.setDateCreated(currentDate);
        newUser.setDateLastModified(currentDate);
        newUser.setRole(Role.ROLE_USER.name());
        newUser.setEmailVerified(false);
        User savedUser = userRepository.save(newUser);

        String verificationcode = generateVerificationCode(savedUser, VerificationCodeType.EMAIL_VERIFICATION.name());

        String mailTo = savedUser.getEmail();
        String mailSubject = "Your code: " + verificationcode;
        String mailContent = emailService.buildAccountVerifyMail(verificationcode);
        emailService.send(mailTo, mailSubject, mailContent);

        return savedUser;
    }

    @Override
    public User authenticateUser(SignInDto signInDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInDto.getEmail(), signInDto.getPassword()));
        return userService.getUserByEmail(signInDto.getEmail());
    }

    @Override
    @Transactional
    public User accountVerify(AccountVerifyDto accountVerifyDto) {
        User authUser = userService.getAuthenticatedUser();

        Optional<VerificationCode> verificationCode = verificationCodeRepository
                .findFirstByUserAndCodeAndTypeAndUsedFalseOrderByDateExpirationDesc(authUser,
                        accountVerifyDto.getVerificationCode(), VerificationCodeType.EMAIL_VERIFICATION.name());
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
