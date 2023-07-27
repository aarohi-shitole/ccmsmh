package com.techvg.covid.care.odas.model;

public class UpdateFacilityInfoResponse {
	private long hospitalId;
	private String odasfacilityid;
	private String referencenumber;
	private String status;
	
	public long getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(int hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getOdasfacilityid() {
		return odasfacilityid;
	}
	public void setOdasfacilityid(String odasfacilityid) {
		this.odasfacilityid = odasfacilityid;
	}
	public String getReferencenumber() {
		return referencenumber;
	}
	public void setReferencenumber(String referencenumber) {
		this.referencenumber = referencenumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
