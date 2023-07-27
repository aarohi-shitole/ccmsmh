package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.Hospital;
import com.techvg.covid.care.service.dto.HospitalDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Hospital entity.
 */
@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long>, JpaSpecificationExecutor<Hospital> {

    @Query(value = "select distinct hospital from Hospital hospital left join fetch hospital.suppliers",
        countQuery = "select count(distinct hospital) from Hospital hospital")
    Page<Hospital> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct hospital from Hospital hospital left join fetch hospital.suppliers")
    List<Hospital> findAllWithEagerRelationships();

    @Query("select hospital from Hospital hospital left join fetch hospital.suppliers where hospital.id =:id")
    Optional<Hospital> findOneWithEagerRelationships(@Param("id") Long id);
    
//    @Query("select hospital from Hospital hospital where hospital.registration_no =:registration_no")
//    Optional<Hospital> findByRegistration_No(@Param("registration_no")String registration_no);
}
