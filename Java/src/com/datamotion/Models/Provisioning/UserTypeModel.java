package com.datamotion.Models.Provisioning;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTypeModel {
	@JsonProperty("UserTypeCode")
	private String userTypeCode;
	@JsonProperty("LicenseQuantity")
	private int licenseQuantity;
	
	public String getUserTypeCode() { return this.userTypeCode; }
	
	public void setUserTypeCode(String userTypeCode) { this.userTypeCode = userTypeCode; }
	
	public int getLicenseQuantity() { return this.licenseQuantity; }
	
	public void setLicenseQuantity(int licenseQuantity) { this.licenseQuantity = licenseQuantity; }
}
