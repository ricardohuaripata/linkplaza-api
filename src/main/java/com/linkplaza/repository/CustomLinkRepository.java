package com.linkplaza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linkplaza.entity.CustomLink;

@Repository
public interface CustomLinkRepository extends JpaRepository<CustomLink, Long> {

}