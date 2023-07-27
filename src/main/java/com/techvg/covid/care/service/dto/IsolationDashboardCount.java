package com.techvg.covid.care.service.dto;

public class IsolationDashboardCount {

	long stableCount;
	long criticalCount;
	long deathCount;
	long recoveredCount;
	long symptomaticCount;
	long totalCount;
	
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public long getStableCount() {
		return stableCount;
	}
	public void setStableCount(long stableCount) {
		this.stableCount = stableCount;
	}
	public long getCriticalCount() {
		return criticalCount;
	}
	public void setCriticalCount(long criticalCount) {
		this.criticalCount = criticalCount;
	}
	public long getDeathCount() {
		return deathCount;
	}
	public void setDeathCount(long deathCount) {
		this.deathCount = deathCount;
	}
	public long getRecoveredCount() {
		return recoveredCount;
	}
	public void setRecoveredCount(long recoveredCount) {
		this.recoveredCount = recoveredCount;
	}
	public long getSymptomaticCount() {
		return symptomaticCount;
	}
	public void setSymptomaticCount(long symptomaticCount) {
		this.symptomaticCount = symptomaticCount;
	}
	
}
