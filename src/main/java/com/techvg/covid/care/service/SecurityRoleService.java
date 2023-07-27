package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.SecurityRole;
import com.techvg.covid.care.repository.SecurityRoleRepository;
import com.techvg.covid.care.service.dto.SecurityRoleDTO;
import com.techvg.covid.care.service.mapper.SecurityRoleMapper;
import com.techvg.covid.care.web.rest.errors.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SecurityRole}.
 */
@Service
@Transactional
public class SecurityRoleService {

    private final Logger log = LoggerFactory.getLogger(SecurityRoleService.class);

    private final SecurityRoleRepository securityRoleRepository;

    private final SecurityRoleMapper securityRoleMapper;

    public SecurityRoleService(SecurityRoleRepository securityRoleRepository, SecurityRoleMapper securityRoleMapper) {
        this.securityRoleRepository = securityRoleRepository;
        this.securityRoleMapper = securityRoleMapper;
    }

    /**
     * Save a securityRole.
     *
     * @param securityRoleDTO the entity to save.
     * @return the persisted entity.
     */
    public SecurityRoleDTO save(SecurityRoleDTO securityRoleDTO) {
        log.debug("Request to save SecurityRole : {}", securityRoleDTO);
        SecurityRole securityRole = securityRoleMapper.toEntity(securityRoleDTO);
        securityRole = securityRoleRepository.save(securityRole);
        return securityRoleMapper.toDto(securityRole);
    }

    /**
     * Get all the securityRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SecurityRoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SecurityRoles");
        return securityRoleRepository.findAll(pageable)
            .map(securityRoleMapper::toDto);
    }


    /**
     * Get all the securityRoles with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SecurityRoleDTO> findAllWithEagerRelationships(Pageable pageable) {
        return securityRoleRepository.findAllWithEagerRelationships(pageable).map(securityRoleMapper::toDto);
    }

    /**
     * Get one securityRole by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SecurityRoleDTO> findOne(Long id) {
        log.debug("Request to get SecurityRole : {}", id);
        return securityRoleRepository.findOneWithEagerRelationships(id)
            .map(securityRoleMapper::toDto);
    }

    /**
     * Delete the securityRole by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SecurityRole : {}", id);
        securityRoleRepository.deleteById(id);
    }
}
