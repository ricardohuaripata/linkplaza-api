package com.linkplaza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linkplaza.entity.SocialPlatform;

@Repository
public interface SocialPlatformRepository extends JpaRepository<SocialPlatform, Long> {

}