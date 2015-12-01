package com.datamotion.Models.Provisioning;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BillingIntervalModel {
	@JsonProperty("Monthly")
	private boolean monthly;
	@JsonProperty("Years")
	private int years;
	
	public boolean getMonthly() { return this.monthly; }
	
	public void setMonthly(boolean monthly) { this.monthly = monthly; }
	
	public int getYears() { return this.years; }
	
	public void setYears(int years) { this.years = years; }
}
