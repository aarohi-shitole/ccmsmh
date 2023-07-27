package com.techvg.covid.care.odas.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class FacilityInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String ccmshospitalid;
	private Facility facility;
	private List<Nodalcontact> nodalcontacts;
	private String requestId;
	private Date timestamp;
			
	public String getCCMSHospitalId() {
		return ccmshospitalid;
	}
	public void setCCMSHospitalId(String cCMSHospitalId) {
		ccmshospitalid = cCMSHospitalId;
	}
	public Facility getFacility() {
		return facility;
	}
	public void setFacility(Facility facility) {
		this.facility = facility;
	}
	public List<Nodalcontact> getNodalcontacts() {
		return nodalcontacts;
	}
	public void setNodalcontacts(List<Nodalcontact> nodalcontacts) {
		this.nodalcontacts = nodalcontacts;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
