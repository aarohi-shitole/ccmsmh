package com.techvg.covid.care.domain.enumeration;

public enum MessageType {

	FACILITY("Facility"),
	BED_INFO("Bed Info"),
	BED_OCCUPANCY("Bed Occupancy"),
	OXYGEN_INFRA("Bed Infra"),
	OXYGEN_CONSUMPTIONS("Oxygen Consumptions");
	
	private final String value;


	MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
