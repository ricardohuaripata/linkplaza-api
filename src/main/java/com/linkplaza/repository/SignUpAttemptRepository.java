package com.linkplaza.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linkplaza.entity.SignUpAttempt;

@Repository
public interface SignUpAttemptRepository extends JpaRepository<SignUpAttempt, Long> {

    Optional<SignUpAttempt> findFirstByEmailAndVerificationCodeOrderByDateExpirationDesc(String email,
            String verificationCode);

    void deleteByEmail(String email);

}
