package com.techvg.covid.care.odas;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.techvg.covid.care.domain.District;
import com.techvg.covid.care.domain.ICMRDailyCount;
import com.techvg.covid.care.domain.PatientInfo;
import com.techvg.covid.care.repository.ICMRDailyCountRepository;
import com.techvg.covid.care.repository.PatientInfoRepository;
import com.techvg.covid.care.service.DistrictQueryService;
import com.techvg.covid.care.service.IsolationService;
import com.techvg.covid.care.service.StateQueryService;
import com.techvg.covid.care.service.dto.DistrictCriteria;
import com.techvg.covid.care.service.dto.DistrictDTO;
import com.techvg.covid.care.service.mapper.DistrictMapper;
import com.techvg.covid.care.service.mapper.StateMapper;

import io.github.jhipster.service.filter.StringFilter;

@Component
public class PatientInfoConsumer {

	Logger logger = LoggerFactory.getLogger(PatientInfoConsumer.class);

	@Autowired
	private PatientInfoRepository patientInfoRepository;

	@Autowired
	private DistrictQueryService districtQueryService;

	@Autowired
	private StateQueryService stateQueryService;

	@Autowired
	private DistrictMapper districtMapper;

	@Autowired
	private StateMapper stateMapper;

	@Autowired
	private ICMRDailyCountRepository icmrDailyCountRepository;
	
	@Value("${icmr.shouldFetchData}")
	private boolean shouldFetchDatafromICMR;
	

	// TODO We will do it later
	// @RabbitListener(queues = "${icmr.patient.info.queue}")
	/*
	 * public void recievedPatientInfo(String patient_info) { logger.
	 * info("Recieved Patient Info From Queue ${icmr.patient.info.queue} ::::::::: "
	 * + patient_info);
	 * 
	 * try {
	 * 
	 * JSONObject patientJson = new JSONObject(patient_info);
	 * 
	 * // PatientInfo patientInfo = new ObjectMapper().readValue(patient_info, //
	 * PatientInfo.class);
	 * 
	 * PatientInfo patientInfo = new PatientInfo();
	 * 
	 * if (patientJson != null) {
	 * 
	 * Pageable pageable = PageRequest.of(0, 30);
	 * 
	 * DistrictCriteria criteria = new DistrictCriteria();
	 * 
	 * StringFilter stringFilter = new StringFilter(); String districtName =
	 * patientJson.getString("districtOfResidence");
	 * stringFilter.setEquals(districtName); criteria.setName(stringFilter);
	 * 
	 * Page<DistrictDTO> pageDistrict =
	 * districtQueryService.findByCriteria(criteria, pageable);
	 * 
	 * if (!pageDistrict.isEmpty()) { List<DistrictDTO> districtList =
	 * pageDistrict.getContent(); DistrictDTO districtDTO = districtList.get(0);
	 * patientInfo.setDistrict(districtMapper.toEntity(districtDTO)); } else {
	 * 
	 * throw new NotFoundException("District name not funds in our database " +
	 * districtName); }
	 * 
	 * // -------------------------------------------------------------------------
	 * StateCriteria criteriaState = new StateCriteria();
	 * 
	 * stringFilter = new StringFilter(); String stateName =
	 * patientJson.getString("stateOfResidence");
	 * 
	 * stringFilter.setEquals(stateName); criteriaState.setName(stringFilter);
	 * 
	 * Page<StateDTO> pageState = stateQueryService.findByCriteria(criteriaState,
	 * pageable);
	 * 
	 * if (!pageState.isEmpty()) { List<StateDTO> stateList =
	 * pageState.getContent(); StateDTO stateDTO = stateList.get(0);
	 * patientInfo.setState(stateMapper.toEntity(stateDTO)); } else {
	 * 
	 * throw new NotFoundException("State name not funds in our database " +
	 * stateName); }
	 * 
	 * }
	 * 
	 * patientInfo.setIcmrId(patientJson.has("icmrId") ?
	 * patientJson.getString("icmrId") : "");
	 * patientInfo.setSrfId(patientJson.has("srfId") ?
	 * patientJson.getString("srfId") : "");
	 * patientInfo.setLabName(patientJson.has("labName") ?
	 * patientJson.getString("labName") : "");
	 * patientInfo.setPatientID(patientJson.has("patientID") ?
	 * patientJson.getString("patientID") : "");
	 * patientInfo.setPatientName(patientJson.has("patientName") ?
	 * patientJson.getString("patientName") : "");
	 * patientInfo.setAge(patientJson.has("age") ? patientJson.getString("age") :
	 * ""); patientInfo.setAgeIn(patientJson.has("ageIn") ?
	 * patientJson.getString("ageIn") : "");
	 * patientInfo.setGender(patientJson.has("gender") ?
	 * patientJson.getString("gender") : ""); patientInfo
	 * .setContactNumber(patientJson.has("contactNumber") ?
	 * patientJson.getString("contactNumber") : "");
	 * patientInfo.setContactNumberBelongsTo(
	 * patientJson.has("contactNumberBelongsTo") ?
	 * patientJson.getString("contactNumberBelongsTo") : "");
	 * 
	 * patientInfo.setAadharCardNumber( patientJson.has("aadharCardNumber") ?
	 * patientJson.getString("aadharCardNumber") : "");
	 * patientInfo.setPassportNumber( patientJson.has("passportNumber") ?
	 * patientJson.getString("passportNumber") : "");
	 * patientInfo.setNationality(patientJson.has("nationality") ?
	 * patientJson.getString("nationality") : "");
	 * patientInfo.setAddress(patientJson.has("address") ?
	 * patientJson.getString("address") : "");
	 * patientInfo.setVillageTown(patientJson.has("villageTown") ?
	 * patientJson.getString("villageTown") : "");
	 * patientInfo.setPincode(patientJson.has("pincode") ?
	 * patientJson.getString("pincode") : ""); patientInfo.setPatientCategory(
	 * patientJson.has("patientCategory") ? patientJson.getString("patientCategory")
	 * : ""); try {
	 * 
	 * patientInfo.setDateOfSampleCollection(patientJson.has(
	 * "dateOfSampleCollection") ?
	 * getConvertedDate(patientJson.getString("dateOfSampleCollection")) : null);
	 * 
	 * } catch (Exception ex) {
	 * patientInfo.setDateOfSampleCollection(getDefaultDate());
	 * 
	 * }
	 * 
	 * //
	 * -----------------------------------------------------------------------------
	 * ---- try {
	 * 
	 * patientInfo.setDateOfSampleReceived(patientJson.has("dateOfSampleReceived") ?
	 * getConvertedDate(patientJson.getString("dateOfSampleReceived")) : null);
	 * 
	 * } catch (Exception ex) {
	 * patientInfo.setDateOfSampleReceived(getDefaultDate());
	 * 
	 * } //
	 * -----------------------------------------------------------------------------
	 * ------
	 * 
	 * patientInfo.setSampleType(patientJson.has("sampleType") ?
	 * patientJson.getString("sampleType") : "");
	 * 
	 * patientInfo.setSampleType(patientJson.has("sampleType") ?
	 * patientJson.getString("sampleType") : "");
	 * patientInfo.setSampleId(patientJson.has("sampleID") ?
	 * patientJson.getString("sampleID") : "");
	 * patientInfo.setHospitalized(patientJson.has("hospitalized") ?
	 * patientJson.getString("hospitalized") : "");
	 * patientInfo.setHospitalName(patientJson.has("hospitalName") ?
	 * patientJson.getString("hospitalName") : "");
	 * 
	 * //
	 * -----------------------------------------------------------------------------
	 * ---- try {
	 * 
	 * patientInfo.setHospitalizationDate(patientJson.has("hospitalizationDate") ?
	 * getConvertedDate(patientJson.getString("hospitalizationDate")) : null);
	 * 
	 * } catch (Exception ex) {
	 * patientInfo.setHospitalizationDate(getDefaultDate());
	 * 
	 * } //
	 * -----------------------------------------------------------------------------
	 * ------
	 * 
	 * patientInfo.setSymptomsStatus( patientJson.has("symptomsStatus") ?
	 * patientJson.getString("symptomsStatus") : ""); patientInfo.setTestingKitUsed(
	 * patientJson.has("testingKitUsed") ? patientJson.getString("testingKitUsed") :
	 * ""); patientInfo.seteGeneNGene(patientJson.has("eGeneNGene") ?
	 * patientJson.getString("eGeneNGene") : "");
	 * patientInfo.setCtValueOfEGeneNGene( patientJson.has("ctValueOfEGeneNGene") ?
	 * patientJson.getString("ctValueOfEGeneNGene") : "");
	 * 
	 * patientInfo.setCtValueOfRdRpSGene( patientJson.has("ctValueOfRdRpSGene") ?
	 * patientJson.getString("ctValueOfRdRpSGene") : "");
	 * 
	 * patientInfo.setRepeatSample(patientJson.has("repeatSample") ?
	 * patientJson.getString("repeatSample") : "");
	 * 
	 * //
	 * -----------------------------------------------------------------------------
	 * ---- try {
	 * 
	 * patientInfo.setDateOfSampleTested(patientJson.has("dateOfSampleTested") ?
	 * getConvertedDate(patientJson.getString("dateOfSampleTested")) : null);
	 * 
	 * } catch (Exception ex) { patientInfo.setDateOfSampleTested(getDefaultDate());
	 * 
	 * } //
	 * -----------------------------------------------------------------------------
	 * ------
	 * 
	 * //
	 * -----------------------------------------------------------------------------
	 * ---- try {
	 * 
	 * patientInfo.setEntryDate( patientJson.has("entryDate") ?
	 * getConvertedDate(patientJson.getString("entryDate")) : null);
	 * 
	 * } catch (Exception ex) { patientInfo.setEntryDate(getDefaultDate());
	 * 
	 * } //
	 * -----------------------------------------------------------------------------
	 * ------
	 * 
	 * //
	 * -----------------------------------------------------------------------------
	 * ---- try {
	 * 
	 * patientInfo.setEditedOn( patientJson.has("updatedDate") ?
	 * getConvertedDate(patientJson.getString("updatedDate")) : null);
	 * 
	 * } catch (Exception ex) { patientInfo.setEditedOn(getDefaultDate());
	 * 
	 * } //
	 * -----------------------------------------------------------------------------
	 * ------
	 * 
	 * patientInfo.setRemarks(patientJson.has("remarks") ?
	 * patientJson.getString("remarks") : null);
	 * 
	 * patientInfo.setLastModifiedBy("System");
	 * 
	 * //
	 * -----------------------------------------------------------------------------
	 * ---- try {
	 * 
	 * patientInfo.setLastModified(getDefaultDate());
	 * 
	 * } catch (Exception ex) { patientInfo.setCcmsPullDate(getDefaultDate());
	 * 
	 * } //
	 * -----------------------------------------------------------------------------
	 * ------
	 * 
	 * //
	 * -----------------------------------------------------------------------------
	 * ---- try {
	 * 
	 * patientInfo.setCcmsPullDate( patientJson.has("ccmsPullDate") ?
	 * getConvertedDate(patientJson.getString("ccmsPullDate")) : null);
	 * 
	 * } catch (Exception ex) { patientInfo.setCcmsPullDate(getDefaultDate());
	 * 
	 * } //
	 * -----------------------------------------------------------------------------
	 * ------
	 * 
	 * this.processPatientInfo(patientInfo); } catch (Exception e) {
	 * logger.error("recievedFacilityInfo::::::::::error", e); }
	 * 
	 * }
	 */

	@RabbitListener(queues = "${icmr.patient.count.queue}")
	public void recievedPatientsCountInfo(String patients_count_info) {
		logger.info(
				"Recieved Patient Count Info From Queue ${icmr.patient.count.queue} ::::::::: " + patients_count_info);

		if (shouldFetchDatafromICMR) {
		try {

			JSONObject patientsCountJson = new JSONObject(patients_count_info);

			ICMRDailyCount iCMRDailyCount = new ICMRDailyCount();

			if (patientsCountJson != null) {

				Pageable pageable = PageRequest.of(0, 30);

				DistrictCriteria criteria = new DistrictCriteria();

				StringFilter stringFilter = new StringFilter();
				
				//-------------------------------------------------------------
				iCMRDailyCount.setTotalPositive(patientsCountJson.has("totalPositiveResult") ? patientsCountJson.getString("totalPositiveResult") : "");
				iCMRDailyCount.setTotalNegative(patientsCountJson.has("totaNegativeResult") ? patientsCountJson.getString("totaNegativeResult") : "");
				iCMRDailyCount.setTotalSamplesTested(patientsCountJson.has("totalSamplesTested") ? patientsCountJson.getString("totalSamplesTested") : "");
				iCMRDailyCount.setCurrentPreviousMonthTest(patientsCountJson.has("previousMonthTested") ? patientsCountJson.getString("previousMonthTested") : "");
				iCMRDailyCount.setUpdatedDate(patientsCountJson.has("ccmsPullDate") ? patientsCountJson.getString("ccmsPullDate") : "");
				
				
				String districtName = patientsCountJson.getString("districtName");
				if (districtName != null) {
				stringFilter.setEquals(districtName);
				criteria.setName(stringFilter);

				Page<DistrictDTO> pageDistrict = districtQueryService.findByCriteria(criteria, pageable);

				if (!pageDistrict.isEmpty()) {
					List<DistrictDTO> districtList = pageDistrict.getContent();
					DistrictDTO districtDTO = districtList.get(0);
					District district = districtMapper.toEntity(districtDTO);
					iCMRDailyCount.setDistrictId(district.getId());
				} 
				}
			}

			// -----------------------------------------------------------------------------------

			this.processPatientsCountInfo(iCMRDailyCount);
		} catch (Exception e) {
			logger.error("recievedFacilityInfo::::::::::error", e);
		}
		}

	}

	private Instant getDefaultDate() {
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		long epochMilli = now.toInstant().toEpochMilli();
		Date date = new Date(epochMilli);

		return Instant.ofEpochMilli(date.getTime());
	}

	private Instant getConvertedDate(String inputDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		TemporalAccessor temporalAccessor = formatter.parse(inputDate);
		LocalDateTime localDateTime = LocalDateTime.from(temporalAccessor);
		ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
		return Instant.from(zonedDateTime);
	}

	private void processPatientInfo(PatientInfo patientInfo) {

		try {
			patientInfoRepository.save(patientInfo);
		} catch (Exception e) {
			// Add Data to DLQ if there some problem with insert the data in database.

		}
	}

	private void processPatientsCountInfo(ICMRDailyCount iCMRDailyCount) {

		try {
			icmrDailyCountRepository.save(iCMRDailyCount);
		} catch (Exception e) {
			// Add Data to DLQ if there some problem with insert the data in database.

		}
	}

}
