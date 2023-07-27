package com.techvg.covid.care.domain.enumeration;

/**
 * The HealthCondition enumeration.
 */
public enum HealthCondition {
    STABLE("Stable"),
    CRITICAL("Critical"),
    RECOVERED("Recovered"),
    DEATH("Death");

    private final String value;


    HealthCondition(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
