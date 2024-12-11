package com.linkplaza.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.linkplaza.entity.Page;
import com.linkplaza.entity.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByPageAndDateCreatedBetween(Page page, Date startDate, Date endDate);

    Long countByPage(Page page);

    @Query("SELECT COUNT(DISTINCT v.ipAddress) FROM Visit v WHERE v.page = :page")
    Long countDistinctByIpAddressAndPage(@Param("page") Page page);

}
