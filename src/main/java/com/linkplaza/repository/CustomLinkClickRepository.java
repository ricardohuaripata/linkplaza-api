package com.linkplaza.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.linkplaza.entity.CustomLink;
import com.linkplaza.entity.CustomLinkClick;
import com.linkplaza.entity.Page;

@Repository
public interface CustomLinkClickRepository extends JpaRepository<CustomLinkClick, Long> {

    List<CustomLinkClick> findByCustomLink_PageAndDateCreatedBetween(Page page, Date startDate, Date endDate);

    Long countByCustomLink(CustomLink customLink);

    @Query("SELECT COUNT(DISTINCT clc.ipAddress) FROM CustomLinkClick clc WHERE clc.customLink = :customLink")
    Long countDistinctByIpAddressAndCustomLink(@Param("customLink") CustomLink customLink);

    Long countByCustomLink_Page(Page page);

    @Query("SELECT COUNT(DISTINCT clc.ipAddress) FROM CustomLinkClick clc WHERE clc.customLink.page = :page")
    Long countDistinctByIpAddressAndCustomLinkPage(@Param("page") Page page);
}
