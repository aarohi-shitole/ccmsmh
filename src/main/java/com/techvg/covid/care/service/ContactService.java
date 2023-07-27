package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.Contact;
import com.techvg.covid.care.odas.model.FacilityInfo;
import com.techvg.covid.care.repository.ContactRepository;
import com.techvg.covid.care.service.dto.ContactDTO;
import com.techvg.covid.care.service.dto.HospitalDTO;
import com.techvg.covid.care.service.mapper.ContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Contact}.
 */
@Service
@Transactional
public class ContactService {

    private final Logger log = LoggerFactory.getLogger(ContactService.class);

    private final ContactRepository contactRepository;

    private final ContactMapper contactMapper;
    
    @Lazy
    @Autowired
	private HospitalService hospitalService;

    public ContactService(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    /**
     * Save a contact.
     *
     * @param contactDTO the entity to save.
     * @return the persisted entity.
     */
    public ContactDTO save(ContactDTO contactDTO) {
        log.debug("Request to save Contact : {}", contactDTO);
        Contact contact = contactMapper.toEntity(contactDTO);
        contact = contactRepository.save(contact);
        ContactDTO result = contactMapper.toDto(contact);
        
        
     // -----------------------------------------------------------------------------------------
     		// Send Hospital Data to ODAS System
     		if (contactDTO.getHospitalId() != null) {

     			Optional<HospitalDTO> hospital = hospitalService.findOne(contactDTO.getHospitalId());
     			if (hospital.isPresent()) {
     				HospitalDTO hospitalDTO = hospital.get();
     				FacilityInfo facilityInfo = hospitalService.sendFacilityInfoToODAS(hospitalDTO);
     			}

     		}
     		// -----------------------------------------------------------------------------------------
     		
     		return result;
    }

    /**
     * Get all the contacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contacts");
        return contactRepository.findAll(pageable)
            .map(contactMapper::toDto);
    }


    /**
     * Get one contact by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContactDTO> findOne(Long id) {
        log.debug("Request to get Contact : {}", id);
        return contactRepository.findById(id)
            .map(contactMapper::toDto);
    }

    /**
     * Delete the contact by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Contact : {}", id);
        contactRepository.deleteById(id);
    }
}
