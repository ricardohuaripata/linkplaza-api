package com.linkplaza.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linkplaza.entity.Page;
import com.linkplaza.entity.User;

import java.util.List;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {
    Optional<Page> findByUrl(String url);

    boolean existsByUrl(String url);

    List<Page> findByUser(User user);

}
