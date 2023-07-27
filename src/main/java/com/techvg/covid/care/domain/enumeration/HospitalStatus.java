package com.techvg.covid.care.domain.enumeration;

/**
 * The HospitalStatus enumeration.
 */
public enum HospitalStatus {
    A("Active"),
    I("Inactive"),
    D("Deleted");

    private final String value;


    HospitalStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
