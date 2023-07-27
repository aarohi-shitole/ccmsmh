package com.techvg.covid.care.odas;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techvg.covid.care.domain.PatientInfo;
import com.techvg.covid.care.odas.model.AddCCMSHospitalIDReq;
import com.techvg.covid.care.service.BedInventoryQueryService;
import com.techvg.covid.care.service.BedInventoryService;
import com.techvg.covid.care.service.HospitalService;
import com.techvg.covid.care.service.InventoryQueryService;
import com.techvg.covid.care.service.InventoryService;
import com.techvg.covid.care.service.dto.BedInventoryCriteria;
import com.techvg.covid.care.service.dto.BedInventoryDTO;
import com.techvg.covid.care.service.dto.HospitalDTO;
import com.techvg.covid.care.service.dto.InventoryCriteria;
import com.techvg.covid.care.service.dto.InventoryDTO;

import io.github.jhipster.service.filter.LongFilter;

@Component
public class HospitalInfoConsumer {

	Logger logger = LoggerFactory.getLogger(HospitalInfoConsumer.class);

	@Autowired
	private BedInventoryQueryService bedInventoryQueryService;

	@Autowired
	private BedInventoryService bedInventoryService;

	@Lazy
	@Autowired
	private HospitalService hospitalService;

	@Lazy
	@Autowired
	private InventoryQueryService inventoryQueryService;

	@Lazy
	@Autowired
	private InventoryService inventoryService;

	@RabbitListener(queues = "${ccms.hospital.queue.info}")
	public void recievedFacilityInfo(String odas_facility_response) {
		logger.info(
				"Recieved Message From RabbitMQ Queue ${ccms.hospital.queue.info}::::::::: " + odas_facility_response);

		try {
			
			JSONObject facilityJson = new JSONObject(odas_facility_response);
			AddCCMSHospitalIDReq addCCMSHospitalIDReq = new AddCCMSHospitalIDReq();

			addCCMSHospitalIDReq.setCcmsHospitalId(facilityJson.getLong("ccmsHospitalId"));
			addCCMSHospitalIDReq.setOdasFacilityId(facilityJson.getString("odasFacilityId"));
			addCCMSHospitalIDReq.setReferencenumber(facilityJson.getString("referencenumber"));
			
//			AddCCMSHospitalIDReq facilityInfo = new ObjectMapper().readValue(odas_facility_response,
//					AddCCMSHospitalIDReq.class);
			this.processHospitalInfo(addCCMSHospitalIDReq);
		} catch (Exception e) {
			logger.error("recievedFacilityInfo::::::::::error", e);
		}
	}

	private void processHospitalInfo(AddCCMSHospitalIDReq falInfoResponse) {

		logger.info("Received information from ODAS::::::" + falInfoResponse);

		long localHospitaId = falInfoResponse.getCcmsHospitalId();
		Optional<HospitalDTO> hospital = hospitalService.findOne(localHospitaId);

		HospitalDTO hospitalDTO = null;

		if (hospital.isPresent()) {
			hospitalDTO = hospital.get();
			hospitalDTO.setOdasFacilityId(falInfoResponse.getOdasFacilityId());
			hospitalDTO.setReferenceNumber(falInfoResponse.getReferencenumber());
		}

		HospitalDTO result = hospitalService.save(hospitalDTO);

		// --------------------------------------------------------------------------------------
		// After update the send odas facility id in our database
		// update the bes information to odas

		Pageable pageable = PageRequest.of(0, 30);
		BedInventoryCriteria criteria = new BedInventoryCriteria();
		LongFilter longFilter = new LongFilter();
		longFilter.setEquals(result.getId());
		criteria.setHospitalId(longFilter);

		Page<BedInventoryDTO> page = bedInventoryQueryService.findByCriteria(criteria, pageable);

		List<BedInventoryDTO> list = page.getContent();
		if (list != null && list.size() > 0) {

			bedInventoryService.sendFacilityBedsInfoToODAS(result.getId(), list, false);

		}
		// -----------------------------------------------------------------------------------------------

		// -----------------------------------------------------------------------------------------------

		// After update the send odas facility id in our database
		// update the Inventory information to odas

		Pageable pageableOdas = PageRequest.of(0, 30);

		LongFilter longFilterODAS = new LongFilter();
		InventoryCriteria criteriaODAS = new InventoryCriteria();

		if (result.getHospitalId() != null) {

			longFilterODAS.setEquals(result.getHospitalId());
			criteriaODAS.setHospitalId(longFilter);

			Page<InventoryDTO> pageInventory = inventoryQueryService.findByCriteria(criteriaODAS, pageableOdas);
			List<InventoryDTO> listInventory = pageInventory.getContent();

			if (result.getHospitalId() != null) {

				inventoryService.sendFacilityOxygenInfoToODAS(result.getHospitalId(), listInventory, false);
			}

		}
		// --------------------------------------------------------------------------------------------------------------

	}

}
