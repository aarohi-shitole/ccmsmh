package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.PatientInfo;
import com.techvg.covid.care.service.dto.PatientInfoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PatientInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientInfoRepository extends JpaRepository<PatientInfo, Long>, JpaSpecificationExecutor<PatientInfo> {

	@Query(value = "SELECT * FROM patient_info ORDER BY ccms_pull_date DESC LIMIT 1", nativeQuery = true)
	PatientInfo findByOrderByCcmsPullDateByDesc();
}
