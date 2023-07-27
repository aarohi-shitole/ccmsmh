package com.techvg.covid.care.odas.model;


import java.io.Serializable;

public class Facility implements Serializable{
	private static final long serialVersionUID = 1L;
    private Address address;
    private String facilitysubtype;
    private String facilitytype;
    private String id;
    private String langitude;
    private String latitude;
    private String name;
    
    public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getFacilitysubtype() {
		return facilitysubtype;
	}
	public void setFacilitysubtype(String facilitysubtype) {
		this.facilitysubtype = facilitysubtype;
	}
	public String getFacilitytype() {
		return facilitytype;
	}
	public void setFacilitytype(String facilitytype) {
		this.facilitytype = facilitytype;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLangitude() {
		return langitude;
	}
	public void setLangitude(String langitude) {
		this.langitude = langitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwnershipsubtype() {
		return ownershipsubtype;
	}
	public void setOwnershipsubtype(String ownershipsubtype) {
		this.ownershipsubtype = ownershipsubtype;
	}
	public String getOwnershiptype() {
		return ownershiptype;
	}
	public void setOwnershiptype(String ownershiptype) {
		this.ownershiptype = ownershiptype;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String ownershipsubtype;
    private String ownershiptype;
}