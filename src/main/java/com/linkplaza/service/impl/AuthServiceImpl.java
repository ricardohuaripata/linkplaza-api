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
import com.linkplaza.entity.SignUpAttempt;
import com.linkplaza.entity.User;
import com.linkplaza.enumeration.Role;
import com.linkplaza.repository.SignUpAttemptRepository;
import com.linkplaza.repository.UserRepository;
import com.linkplaza.service.IAuthService;
import com.linkplaza.service.IEmailService;
import com.linkplaza.service.IUserService;

@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SignUpAttemptRepository signUpAttemptRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IUserService userService;
    @Autowired
    private IEmailService emailService;

    @Override
    @Transactional
    public void signUp(SignUpDto signUpDto) {

        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());

        if (user.isPresent()) {
            throw new IllegalArgumentException("The email '" + signUpDto.getEmail() + "' is already taken.");
        }

        SignUpAttempt signUpAttempt = new SignUpAttempt();

        signUpAttempt.setEmail(signUpDto.getEmail());
        signUpAttempt.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        signUpAttempt.setDateExpiration(
                new Date(System.currentTimeMillis() + AppConstants.VERIFICATION_CODE_EXPIRATION));

        StringBuilder verificationcode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int num = (int) (Math.random() * 10);
            verificationcode.append(num);
        }

        signUpAttempt.setVerificationCode(verificationcode.toString());
        signUpAttemptRepository.save(signUpAttempt);

        String mailTo = signUpDto.getEmail();
        String mailSubject = "Your code: " + verificationcode.toString();
        String mailContent = emailService.buildAccountVerifyMail(verificationcode.toString());

        // emailService.send(mailTo, mailSubject, mailContent);

    }

    @Override
    public User signIn(SignInDto signInDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInDto.getEmail(), signInDto.getPassword()));
        return userService.getUserByEmail(signInDto.getEmail());
    }

    @Override
    @Transactional
    public User completeSignUp(AccountVerifyDto accountVerifyDto) {
        Optional<SignUpAttempt> signUpAttempt = signUpAttemptRepository
                .findFirstByEmailAndVerificationCodeOrderByDateExpirationDesc(accountVerifyDto.getEmail(),
                        accountVerifyDto.getVerificationCode());
        // validar existencia del codigo para el email en cuestion
        if (signUpAttempt.isEmpty()) {
            throw new IllegalArgumentException("Invalid account verification.");
        }
        // validar caducidad del codigo
        if (signUpAttempt.get().getDateExpiration().before(new Date())) {
            throw new IllegalStateException("The verification code has expired.");
        }

        // si todo es correcto:
        // se eliminan todos los intentos de registro para el email en cuestion
        signUpAttemptRepository.deleteByEmail(signUpAttempt.get().getEmail());
        // se completa el registro del usuario
        User newUser = new User();
        Date currentDate = new Date();

        newUser.setEmail(signUpAttempt.get().getEmail());
        newUser.setPassword(signUpAttempt.get().getPassword());
        newUser.setDateCreated(currentDate);
        newUser.setDateLastModified(currentDate);
        newUser.setRole(Role.ROLE_USER.name());

        return userRepository.save(newUser);
    }
}
