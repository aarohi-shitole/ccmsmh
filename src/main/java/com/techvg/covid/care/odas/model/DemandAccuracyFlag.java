package com.techvg.covid.care.odas.model;

public enum DemandAccuracyFlag {

	ACCURATE("Accurate"),
    OVER("Over"),
    UNDER("Under");
   
    private final String value;

    private DemandAccuracyFlag(String value) {
		this.value=value;
	}
    
    public String getValue() {
        return value;
    }
}
