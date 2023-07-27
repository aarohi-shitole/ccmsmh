package com.techvg.covid.care.web.rest;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * REST controller for managing {@link com.techvg.covid.care.domain.Hospital}.
 */
@RestController
@RequestMapping("/api/reports")
public class ReportResource {

	private final Logger log = LoggerFactory.getLogger(ReportResource.class);

	@Value("${custom.jasper.url}")
	private String reportServerUrl;

	@Value("${custom.jasper.username}")
	private String reportServerUseranme;

	@Value("${custom.jasper.password}")
	private String reportServerPassword;

	private final RestTemplate restTemplate;

	public ReportResource(RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
		((RestTemplate) this.restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				if (response.getRawStatusCode() != 400) {
					super.handleError(response);
				}
			}
		});
	}

	/**
	 * {@code GET  /hospitals/:id} : get the "id" hospital.
	 *
	 * @param id the id of the hospitalDTO to retrieve.
	 * @return
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the hospitalDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/downloadByName")
	public ResponseEntity<byte[]> getByName(@RequestParam(name = "format", required = true) String format,
			@RequestParam(name = "name", required = true) String name,
			@RequestParam(name = "state_id", required = false) String stateId,
			@RequestParam(name = "division_ids", required = false) String divisionIds,
			@RequestParam(name = "district_ids", required = false) String districtIds,
			@RequestParam(name = "municipal_corp_ids", required = false) String municipalCorpIds,
			@RequestParam(name = "taluka_ids", required = false) String talukaIds) {
		log.debug("REST request to get repot : {}" + name);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(this.reportServerUrl + "/CCMS/Reports/" + name + "." + format);
		if (StringUtils.isNotEmpty(stateId))
			builder.queryParam("state_id", stateId);
		if (StringUtils.isNotEmpty(divisionIds))
			builder.queryParam("division_ids", divisionIds);
		if (StringUtils.isNotEmpty(districtIds))
			builder.queryParam("district_ids", districtIds);
		if (StringUtils.isNotEmpty(municipalCorpIds))
			builder.queryParam("municipal_corp_ids", municipalCorpIds);
		if (StringUtils.isNotEmpty(talukaIds))
			builder.queryParam("taluka_ids", talukaIds);

		String auth = this.reportServerUseranme + ":" + this.reportServerPassword;
		String base64Creds = Base64.getEncoder().encodeToString(auth.getBytes());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBasicAuth(base64Creds);

		HttpEntity request = new HttpEntity(httpHeaders);

		return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, byte[].class);
	}

}
