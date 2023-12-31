package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.Inventory;
import com.techvg.covid.care.domain.Transactions;
import com.techvg.covid.care.domain.enumeration.TransactionStatus;
import com.techvg.covid.care.repository.TransactionsRepository;
import com.techvg.covid.care.service.dto.TransactionsDTO;
import com.techvg.covid.care.service.mapper.TransactionsMapper;
import com.techvg.covid.care.web.rest.errors.AlreadyExitsException;
import com.techvg.covid.care.web.rest.errors.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Transactions}.
 */
@Service
@Transactional
public class TransactionsService {

    private final Logger log = LoggerFactory.getLogger(TransactionsService.class);

    private final TransactionsRepository transactionsRepository;

    private final TransactionsMapper transactionsMapper;

    public TransactionsService(TransactionsRepository transactionsRepository, TransactionsMapper transactionsMapper) {
        this.transactionsRepository = transactionsRepository;
        this.transactionsMapper = transactionsMapper;
    }

    /**
     * Save a transactions.
     *
     * @param transactionsDTO the entity to save.
     * @return the persisted entity.
     */
    public TransactionsDTO save(TransactionsDTO transactionsDTO) {
        log.debug("Request to save Transactions : {}", transactionsDTO);
        List<Transactions> transacation = transactionsRepository.findByInventoryIdAndStatus(transactionsDTO.getInventoryId(),TransactionStatus.OPEN);
		if (transactionsDTO.getId()==null && !transacation.isEmpty()) {
			throw new AlreadyExitsException("Transactions is already exixt in open status for "+transactionsDTO.getInventoryMasterName());
		} else {
        Transactions transactions = transactionsMapper.toEntity(transactionsDTO);
        transactions = transactionsRepository.save(transactions);
        return transactionsMapper.toDto(transactions);
		}
    }

    /**
     * Get all the transactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TransactionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Transactions");
        return transactionsRepository.findAll(pageable)
            .map(transactionsMapper::toDto);
    }


    /**
     * Get one transactions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TransactionsDTO> findOne(Long id) {
        log.debug("Request to get Transactions : {}", id);
        return transactionsRepository.findById(id)
            .map(transactionsMapper::toDto);
    }

    /**
     * Delete the transactions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Transactions : {}", id);
        transactionsRepository.deleteById(id);
    }
}
