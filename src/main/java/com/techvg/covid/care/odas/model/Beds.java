package com.techvg.covid.care.odas.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class Beds implements Serializable {
	private static final long serialVersionUID = 1L;
	private String no_gen_beds;
	private String no_hdu_beds;
	private String no_icu_beds;
	private String no_o2_concentrators;
	private String no_vent_beds;
	
	public String getNo_gen_beds() {
		return no_gen_beds;
	}
	public void setNo_gen_beds(String no_gen_beds) {
		this.no_gen_beds = no_gen_beds;
	}
	public String getNo_hdu_beds() {
		return no_hdu_beds;
	}
	public void setNo_hdu_beds(String no_hdu_beds) {
		this.no_hdu_beds = no_hdu_beds;
	}
	public String getNo_icu_beds() {
		return no_icu_beds;
	}
	public void setNo_icu_beds(String no_icu_beds) {
		this.no_icu_beds = no_icu_beds;
	}
	public String getNo_o2_concentrators() {
		return no_o2_concentrators;
	}
	public void setNo_o2_concentrators(String no_o2_concentrators) {
		this.no_o2_concentrators = no_o2_concentrators;
	}
	public String getNo_vent_beds() {
		return no_vent_beds;
	}
	public void setNo_vent_beds(String no_vent_beds) {
		this.no_vent_beds = no_vent_beds;
	}
	
}
