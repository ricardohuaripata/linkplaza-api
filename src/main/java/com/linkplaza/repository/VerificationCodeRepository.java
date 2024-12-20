package com.linkplaza.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linkplaza.entity.User;
import com.linkplaza.entity.VerificationCode;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode> findFirstByUserAndCodeAndTypeAndUsedFalseOrderByDateExpirationDesc(User user,
            String code, String type);

    void deleteByDateExpirationBefore(Date date);

    void deleteByUser(User user);

}