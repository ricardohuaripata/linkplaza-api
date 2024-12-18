package com.linkplaza.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.linkplaza.entity.Page;
import com.linkplaza.entity.SocialLink;
import com.linkplaza.entity.SocialLinkClick;

@Repository
public interface SocialLinkClickRepository extends JpaRepository<SocialLinkClick, Long> {
    List<SocialLinkClick> findBySocialLink_PageAndDateCreatedBetween(Page page, Date startDate, Date endDate);

    Long countBySocialLink(SocialLink socialLink);

    @Query("SELECT COUNT(DISTINCT slc.ipAddress) FROM SocialLinkClick slc WHERE slc.socialLink = :socialLink")
    Long countDistinctByIpAddressAndSocialLink(@Param("socialLink") SocialLink socialLink);

    Long countBySocialLink_Page(Page page);

    @Query("SELECT COUNT(DISTINCT slc.ipAddress) FROM SocialLinkClick slc WHERE slc.socialLink.page = :page")
    Long countDistinctByIpAddressAndSocialLinkPage(@Param("page") Page page);
}
