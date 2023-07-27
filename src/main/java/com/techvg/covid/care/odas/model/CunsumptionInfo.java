package com.techvg.covid.care.odas.model;

import org.springframework.stereotype.Component;

@Component
public class CunsumptionInfo {
	String consumption_for_date;
    String consumption_updated_date;
    String total_oxygen_consumed;
    String total_oxygen_delivered;
    String total_oxygen_generated;
    
    
	public String getConsumption_for_date() {
		return consumption_for_date;
	}
	public void setConsumption_for_date(String consumption_for_date) {
		this.consumption_for_date = consumption_for_date;
	}
	public String getConsumption_updated_date() {
		return consumption_updated_date;
	}
	public void setConsumption_updated_date(String consumption_updated_date) {
		this.consumption_updated_date = consumption_updated_date;
	}
	public String getTotal_oxygen_consumed() {
		return total_oxygen_consumed;
	}
	public void setTotal_oxygen_consumed(String total_oxygen_consumed) {
		this.total_oxygen_consumed = total_oxygen_consumed;
	}
	public String getTotal_oxygen_delivered() {
		return total_oxygen_delivered;
	}
	public void setTotal_oxygen_delivered(String total_oxygen_delivered) {
		this.total_oxygen_delivered = total_oxygen_delivered;
	}
	public String getTotal_oxygen_generated() {
		return total_oxygen_generated;
	}
	public void setTotal_oxygen_generated(String total_oxygen_generated) {
		this.total_oxygen_generated = total_oxygen_generated;
	}
    
    


}
