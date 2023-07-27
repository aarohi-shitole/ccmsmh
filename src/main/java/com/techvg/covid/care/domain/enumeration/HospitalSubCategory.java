package com.techvg.covid.care.domain.enumeration;

/**
 * The HospitalSubCategory enumeration.
 */
public enum HospitalSubCategory {
    FREE("FREE"),
    MPJAY("MJPJAY/PMJAY"),
    CHARGEABLE("CHARGEABLE"),
    TRUST("CHARITABLE TRUST");

    private final String value;


    HospitalSubCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
