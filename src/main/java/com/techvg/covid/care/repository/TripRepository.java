package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.Trip;
import com.techvg.covid.care.domain.enumeration.TransactionStatus;
import com.techvg.covid.care.service.dto.TripDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data  repository for the Trip entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TripRepository extends JpaRepository<Trip, Long>, JpaSpecificationExecutor<Trip> {
}
