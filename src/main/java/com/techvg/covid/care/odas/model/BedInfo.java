package com.techvg.covid.care.odas.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;


@Component
public class BedInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Beds beds;
	private String facilityid;
	private String requestId;
	private String timestamp;
	
	public Beds getBeds() {
		return beds;
	}
	public void setBeds(Beds beds) {
		this.beds = beds;
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
