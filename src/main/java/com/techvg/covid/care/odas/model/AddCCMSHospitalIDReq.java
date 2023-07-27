package com.techvg.covid.care.odas.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class AddCCMSHospitalIDReq implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long ccmsHospitalId;
    private String odasFacilityId;
    private String referencenumber;
    
	public String getReferencenumber() {
		return referencenumber;
	}
	public void setReferencenumber(String referencenumber) {
		this.referencenumber = referencenumber;
	}
	public Long getCcmsHospitalId() {
		return ccmsHospitalId;
	}
	public void setCcmsHospitalId(Long ccmsHospitalId) {
		this.ccmsHospitalId = ccmsHospitalId;
	}
	public String getOdasFacilityId() {
		return odasFacilityId;
	}
	public void setOdasFacilityId(String odasFacilityId) {
		this.odasFacilityId = odasFacilityId;
	}
}
