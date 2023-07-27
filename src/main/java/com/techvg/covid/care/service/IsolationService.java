package com.techvg.covid.care.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.techvg.covid.care.odas.model.IsolationAccessToken;
import com.techvg.covid.care.odas.model.IsolationCount;
import com.techvg.covid.care.odas.model.IsolationRequest;
import com.techvg.covid.care.odas.model.ResponseMessage;
import com.techvg.covid.care.service.dto.DistrictCriteria;
import com.techvg.covid.care.service.dto.DistrictDTO;
import com.techvg.covid.care.service.dto.HospitalCriteria;
import com.techvg.covid.care.service.dto.IsolationCriteria;
import com.techvg.covid.care.service.dto.IsolationDTO;
import com.techvg.covid.care.service.totp.SMSConstants;
import com.techvg.covid.care.service.totp.UserOTPService;

import io.github.jhipster.service.filter.StringFilter;

/**
 * Service Implementation for managing {@link Isolation}.
 */
@Service
@Transactional
public class IsolationService {

	private final Logger log = LoggerFactory.getLogger(IsolationService.class);

	@Value("${isolation.username}")
	private String isolationUsername;

	@Value("${isolation.password}")
	private String isolationPassword;

	@Autowired
	private IsolationRequest isolationRequest;

	@Value("${isolation.authenticate}")
	private String authenaticateUrl;

	@Value("${isolation.count}")
	private String isolationBackenUrlCount;

	@Value("${isolation.list}")
	private String isolationBackenUrl;

	@Autowired
	private IsolationAccessToken isolationAccessToken;

	@Autowired
	private DistrictQueryService districtQueryService;

	@Autowired
	private RestTemplate restTemplate;

	public void getAccessToken() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// Setting header
		log.info("isolationUsername:::::::::::::::::::" + this.isolationUsername);
		log.info("isolationPassword:::::::::::::::::::" + this.isolationPassword);
		this.isolationRequest.setUsername(this.isolationUsername);
		this.isolationRequest.setPassword(this.isolationPassword);
		HttpEntity<IsolationRequest> request = new HttpEntity<>(this.isolationRequest, headers);

		log.info("isolationAuthenaticateUrl:::::::::::::::::::" + this.authenaticateUrl);
		this.isolationAccessToken = restTemplate
				.postForEntity(this.authenaticateUrl, request, IsolationAccessToken.class).getBody();
	}

	public IsolationDTO save(IsolationDTO isolationDTO) {

		log.debug("Request to save Isolation : {}", isolationDTO);
		int count = 0;

		try {

			isolationDTO = this.postIsolation(isolationDTO);

		} catch (org.springframework.web.client.HttpClientErrorException ce) {
			log.info("::::" + ce.getStatusCode().toString());
			if ("401 UNAUTHORIZED".equalsIgnoreCase(ce.getStatusCode().toString()) && count < 1) {
				count++;
				this.getAccessToken();
				isolationDTO = this.postIsolation(isolationDTO);
			}
			log.error("While save Isolation", ce);
		} catch (Exception e) {

		}

		return isolationDTO;
	}

	public List<JSONObject> save(MultipartFile file) throws URISyntaxException {
		try {
			InputStream fis = file.getInputStream();
			List<IsolationDTO> isolationList = excelToDatabase(fis);
			for (IsolationDTO isolationDTO : isolationList) {
				save(isolationDTO);
			}
			return null;
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
	}

	private List<IsolationDTO> excelToDatabase(InputStream fis) {

		try {
			List<DistrictDTO> listDistricts = getDistrictList();

			Workbook workbook = new XSSFWorkbook(fis);

			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();
			int rowCount = sheet.getPhysicalNumberOfRows();
			List<IsolationDTO> userInformation = new ArrayList<IsolationDTO>();
			System.out.println("rowCount " + rowCount);

			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				System.out.println("rowNumber " + rowNumber);

				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				} else {
					rowNumber++;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();

				IsolationDTO isolationDTO = new IsolationDTO();
				isolationDTO.setPasswordHash("welcome1");
				// isolationDTO.setLastModified(new Date().toInstant());
				isolationDTO.setLastModifiedBy("CCMSBackend");
				isolationDTO.setStateId(1L);

				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					
					switch (cellIdx) {					
					case 1:
						isolationDTO.setFirstName(getCellData(currentCell));
						break;

					case 2:
						isolationDTO.setAge(getCellData(currentCell));
						break;

					case 3:
						isolationDTO.setGender(getCellData(currentCell));
						break;

					case 4:
						String districtName = getCellData(currentCell);
						Long districtId = null;
						for (DistrictDTO districtDTO : listDistricts) {
							String localDistrictName = districtDTO.getName();
							if (localDistrictName != null && localDistrictName.equalsIgnoreCase(districtName)) {
								districtId = districtDTO.getId();
								break;
							}
						}
						if(districtId!=null)
							isolationDTO.setDistrictId(districtId);
						break;

					case 5:
						isolationDTO.setAddress(getCellData(currentCell));
						break;

					case 6:
						String mobNo = getCellData(currentCell);
						if(mobNo != null && (!mobNo.isEmpty() || !mobNo.equalsIgnoreCase("null"))) {
							isolationDTO.setMobileNo(mobNo);
						}else {
							continue;
						}
						break;

					case 7:
						isolationDTO.setRtpcrId(getCellData(currentCell));
						break;

					case 8:
						isolationDTO.setRatId(getCellData(currentCell));	
						break;

					case 9:
						isolationDTO.setIcmrId(getCellData(currentCell));
						break;

					case 10:
						isolationDTO.setIsolationStartDate(getCellData(currentCell));
						break;

					case 11:
						isolationDTO.setIsolationEndDate(getCellData(currentCell));
						break;

					case 12:
						isolationDTO.setAadharCardNo(getCellData(currentCell));
						break;

					default:
						break;
					}

					cellIdx++;
				}
				if(isolationDTO!=null && isolationDTO.getMobileNo() != null && isolationDTO.getMobileNo().length() == 10 && (!isolationDTO.getMobileNo().isEmpty() || !isolationDTO.getMobileNo().equalsIgnoreCase("null"))) {
					userInformation.add(isolationDTO);
				}else if(isolationDTO!=null && isolationDTO.getMobileNo() != null && isolationDTO.getMobileNo().length() != 10){
					
				}
			}

			workbook.close();

			return userInformation;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}

	private String getCellData(Cell currentCell) {
		try {
		if(currentCell.getCellType() == CellType.ERROR || currentCell.getCellType() == CellType.BLANK) {
			return "";
		}
		if(currentCell.getCellType() == CellType.STRING) {
			return currentCell.getStringCellValue();
		} else {
			return NumberToTextConverter.toText(currentCell.getNumericCellValue());	
		} }catch (Exception e) {
			return "";
		}
	}

	private List<DistrictDTO> getDistrictList() {
		DistrictCriteria criteria = new DistrictCriteria();
		Pageable pageable = PageRequest.of(0, 40);
		Page<DistrictDTO> page = districtQueryService.findByCriteria(criteria, pageable);
		List<DistrictDTO> listDistricts = page.getContent();
		return listDistricts;
	}

	public ResponseEntity<?> save(List<IsolationDTO> isolationDTOList) {

		log.debug("Request to save Isolation : {}", isolationDTOList);
		int count = 0;
		try {

			return this.posMultitIsolation(isolationDTOList);

		} catch (org.springframework.web.client.HttpClientErrorException ce) {
			log.info("::::" + ce.getStatusCode().toString());
			if ("401 UNAUTHORIZED".equalsIgnoreCase(ce.getStatusCode().toString()) && count < 1) {
				count++;
				this.getAccessToken();
				return this.posMultitIsolation(isolationDTOList);
			}
			log.error("While save Isolation", ce);
		} catch (Exception e) {

		}

		String message = "Please upload an excel file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}

	public IsolationDTO postIsolation(IsolationDTO isolationDTO) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if (this.isolationAccessToken != null && this.isolationAccessToken.getId_token() != null) {
			log.info("IsolationToken::Token:::" + isolationAccessToken.getId_token());
			headers.add("Authorization", "Bearer " + isolationAccessToken.getId_token());
		}

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		isolationDTO.setLastModified(null);

		String jsonData = gson.toJson(isolationDTO);

		HttpEntity<String> request = new HttpEntity<>(jsonData, headers);

		return this.restTemplate.postForEntity(this.isolationBackenUrl, request, IsolationDTO.class).getBody();
	}

	public ResponseEntity<?> posMultitIsolation(List<IsolationDTO> isolationDTOList) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if (this.isolationAccessToken != null && this.isolationAccessToken.getId_token() != null) {
			log.info("IsolationToken::Token:::" + isolationAccessToken.getId_token());
			headers.add("Authorization", "Bearer " + isolationAccessToken.getId_token());
		}

		HttpEntity<?> request = new HttpEntity<>(isolationDTOList, headers);

		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(this.isolationBackenUrl);
		urlBuilder.path("/list");

		String url = urlBuilder.toString();
		log.debug("::::::::::::::::::::::::::::" + url);

		return this.restTemplate.postForEntity(url, request, ResponseEntity.class).getBody();
	}

	@Transactional(readOnly = true)
	public List<IsolationDTO> findByCriteria(IsolationCriteria criteria) {
		log.debug("find by criteria : {}", criteria);

		List<IsolationDTO> isolationDTOList = null;
		int count = 0;

		try {

			isolationDTOList = this.postByCriteria(criteria);

		} catch (org.springframework.web.client.HttpClientErrorException ce) {
			log.info("::::" + ce.getStatusCode().toString());
			if ("401 UNAUTHORIZED".equalsIgnoreCase(ce.getStatusCode().toString()) && count < 1) {
				count++;
				this.getAccessToken();
				isolationDTOList = this.postByCriteria(criteria);
			}
			log.error("While save Isolation", ce);
		} catch (Exception e) {

		}

		return isolationDTOList;
	}

	/**
	 * Return a {@link Page} of {@link IsolationDTO} which matches the criteria from
	 * the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @param page     The page, which should be returned.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public ResponseEntity<List<IsolationDTO>> findByCriteria(IsolationCriteria criteria, Pageable page) {
		log.debug("find by criteria : {}, page: {}", criteria, page);
		ResponseEntity<List<IsolationDTO>> isolationDTOList = null;
		int count = 0;

		try {

			isolationDTOList = this.getByCriteria(criteria, page);

		} catch (org.springframework.web.client.HttpClientErrorException ce) {
			log.info("::::" + ce.getStatusCode().toString());
			if ("401 UNAUTHORIZED".equalsIgnoreCase(ce.getStatusCode().toString()) && count < 1) {
				count++;
				this.getAccessToken();
				isolationDTOList = this.getByCriteria(criteria, page);
			}
			log.error("While save Isolation", ce);
		} catch (Exception e) {

		}

		return isolationDTOList;
	}

	/**
	 * Return the number of matching entities in the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the number of matching entities.
	 */
	@Transactional(readOnly = true)
	public Object countByCriteria(boolean isDistrict, IsolationCriteria criteria) {

		int count = 0;
		Object listCount = null;

		try {

			listCount = this.getForCountCriteria(isDistrict, criteria);

		} catch (org.springframework.web.client.HttpClientErrorException ce) {

			log.info("::::" + ce.getStatusCode().toString());

			if ("401 UNAUTHORIZED".equalsIgnoreCase(ce.getStatusCode().toString()) && count < 1) {
				count++;
				this.getAccessToken();
				listCount = this.getForCountCriteria(isDistrict, criteria);
			}

			log.error("While save Isolation", ce);
		}

		return listCount;
	}

	public Object getForCountCriteria(boolean isDistrict, IsolationCriteria criteria) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if (this.isolationAccessToken != null && this.isolationAccessToken.getId_token() != null) {
			log.info("IsolationToken::Token:::" + isolationAccessToken.getId_token());
			headers.add("Authorization", "Bearer " + isolationAccessToken.getId_token());
		}
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		GsonBuilder builder = new GsonBuilder();

		Gson gson = builder.create();

		String jsonData = gson.toJson(criteria);

		log.debug("json========================" + jsonData);

		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(this.isolationBackenUrlCount);

		if (isDistrict) {
			urlBuilder.path("/district");
		}

		urlBuilder = getBuilderWithQuaryParam(urlBuilder, criteria);

		String url = urlBuilder.toUriString();
		log.info("url ::::::::::::::::" + url);

		return this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class).getBody();

	}

	public Optional<IsolationDTO> getIsolationById(long id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if (this.isolationAccessToken != null && this.isolationAccessToken.getId_token() != null) {
			log.info("IsolationToken::Token:::" + isolationAccessToken.getId_token());
			headers.add("Authorization", "Bearer " + isolationAccessToken.getId_token());
		}
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(this.isolationBackenUrl);

		urlBuilder.path("/" + id);

		String url = urlBuilder.toUriString();
		log.info("url ::::::::::::::::" + url);

		IsolationDTO isolationDTO = this.restTemplate.exchange(url, HttpMethod.GET, entity, IsolationDTO.class)
				.getBody();

		return Optional.ofNullable(isolationDTO);

	}

	private UriComponentsBuilder getBuilderWithQuaryParam(UriComponentsBuilder urlBuilder, IsolationCriteria criteria) {

		if (criteria != null) {
			if (criteria.getHealthCondition() != null) {
				urlBuilder.queryParam("healthCondition.equals", criteria.getHealthCondition().getEquals());
			}

			if (criteria.getStatus() != null) {
				urlBuilder.queryParam("status.equals", criteria.getStatus().getEquals());
			}

			if (criteria.getDistrictId() != null) {
				urlBuilder.queryParam("districtId.equals", criteria.getDistrictId().getEquals());
			}

			if (criteria.getSymptomatic() != null) {
				urlBuilder.queryParam("symptomatic.equals", criteria.getSymptomatic().getEquals());
			}

			if (criteria.getFirstName() != null) {
				urlBuilder.queryParam("firstName.contains", criteria.getFirstName().getEquals());
			}

			if (criteria.getLastName() != null) {
				urlBuilder.queryParam("lastName.equals", criteria.getLastName().getEquals());
			}

			if (criteria.getAadharCardNo() != null) {
				urlBuilder.queryParam("aadharCardNo.contains", criteria.getAadharCardNo().getEquals());
			}

			if (criteria.getTalukaId() != null) {
				urlBuilder.queryParam("talukaId.equals", criteria.getTalukaId().getEquals());
			}

			if (criteria.getCityId() != null) {
				urlBuilder.queryParam("cityId.equals", criteria.getCityId().getEquals());
			}

			if (criteria.getSelfRegistered() != null) {
				urlBuilder.queryParam("selfExaminated.equals", criteria.getSelfRegistered().getEquals());
			}

		}

		return urlBuilder;
	}

	public List<IsolationDTO> postByCriteria(IsolationCriteria criteria) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if (this.isolationAccessToken != null && this.isolationAccessToken.getId_token() != null) {
			log.info("IsolationToken::Token:::" + isolationAccessToken.getId_token());
			headers.add("Authorization", "Bearer " + isolationAccessToken.getId_token());
		}
		HttpEntity<IsolationCriteria> request = new HttpEntity<>(criteria, headers);

		ResponseEntity<IsolationDTO[]> responses = this.restTemplate.postForEntity(this.isolationBackenUrlCount,
				request, IsolationDTO[].class);

		List<IsolationDTO> list = Arrays.asList(responses.getBody());

		return list;
	}

	public ResponseEntity<List<IsolationDTO>> getByCriteria(IsolationCriteria criteria, Pageable page) {
		//		Pageable pageable = PageRequest.of(0, 30);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if (this.isolationAccessToken != null && this.isolationAccessToken.getId_token() != null) {
			log.info("IsolationToken::Token:::" + isolationAccessToken.getId_token());
			headers.add("Authorization", "Bearer " + isolationAccessToken.getId_token());
		}
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		GsonBuilder builder = new GsonBuilder();

		Gson gson = builder.create();

		String jsonData = gson.toJson(criteria);

		log.debug("json========================" + jsonData);

		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(this.isolationBackenUrl);

		urlBuilder = getBuilderWithQuaryParam(urlBuilder, criteria);
		urlBuilder.queryParam("page", page.getPageNumber());
		urlBuilder.queryParam("size", page.getPageSize());
		urlBuilder.queryParam("sort", "lastModified,desc");
		String url = urlBuilder.toUriString();

		ResponseEntity<IsolationDTO[]> responses = this.restTemplate.exchange(url, HttpMethod.GET, entity,
				IsolationDTO[].class);

		List<IsolationDTO> list = Arrays.asList(responses.getBody());
		headers = responses.getHeaders();
		return ResponseEntity.ok().headers(headers).body(list);
	}

	//    /**
	//     * Get all the isolations.
	//     *
	//     * @param pageable the pagination information.
	//     * @return the list of entities.
	//     */
	//    @Transactional(readOnly = true)
	//    public Page<IsolationDTO> findAll(Pageable pageable) {
	//        log.debug("Request to get all Isolations");
	//        return isolationRepository.findAll(pageable)
	//            .map(isolationMapper::toDto);
	//    }

	//    /**
	//     * Get one isolation by id.
	//     *
	//     * @param id the id of the entity.
	//     * @return the entity.
	//     */
	@Transactional(readOnly = true)
	public Optional<IsolationDTO> findOne(Long id) {
		int count = 0;
		Optional<IsolationDTO> isoDto = null;

		try {

			isoDto = this.getIsolationById(id);

		} catch (org.springframework.web.client.HttpClientErrorException ce) {

			log.info("::::" + ce.getStatusCode().toString());

			if ("401 UNAUTHORIZED".equalsIgnoreCase(ce.getStatusCode().toString()) && count < 1) {
				count++;
				this.getAccessToken();
				isoDto = this.getIsolationById(id);
			}

			log.error("While save Isolation", ce);
		}

		return isoDto;
	}
	//
	//    /**
	//     * Delete the isolation by id.
	//     *
	//     * @param id the id of the entity.
	//     */
	//    public void delete(Long id) {
	//        log.debug("Request to delete Isolation : {}", id);
	//        isolationRepository.deleteById(id);
	//    }
}
