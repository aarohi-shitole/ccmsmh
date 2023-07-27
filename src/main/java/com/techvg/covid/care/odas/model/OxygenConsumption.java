package com.techvg.covid.care.odas.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class OxygenConsumption implements Serializable{
	private static final long serialVersionUID = 1L;
	private CunsumptionInfo consumptionInfo;
	private String facilityid;
	private String requestId;
	private String timestamp;
	
	public CunsumptionInfo getConsumptionInfo() {
		return consumptionInfo;
	}
	public void setConsumptionInfo(CunsumptionInfo consumptionInfo) {
		this.consumptionInfo = consumptionInfo;
	}
	public String getFacilityid() {
		return facilityid;
	}
	public void setFacilityid(String facilityid) {
		this.facilityid = facilityid;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
