package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.Transactions;
import com.techvg.covid.care.domain.enumeration.TransactionStatus;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Transactions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long>, JpaSpecificationExecutor<Transactions> {
	
	List<Transactions> findByInventoryIdAndStatus(Long id, TransactionStatus status);
}
