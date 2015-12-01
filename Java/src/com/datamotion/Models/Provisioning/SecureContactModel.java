package com.datamotion.Models.Provisioning;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SecureContactModel {
	@JsonProperty("UserTypeCode")
	private String userTypeCode;
	@JsonProperty("DisplayName")
	private String displayName;
	@JsonProperty("Email")
	private String email;
	
	public String getUserTypeCode() { return this.userTypeCode; }
	
	public void setUserTypeCode(String userTypeCode) { this.userTypeCode = userTypeCode; }
	
	public String getDisplayName() { return this.displayName; }
	
	public void setDisplayName(String displayName) { this.displayName = displayName; }
	
	public String getEmail() { return this.email; }
	
	public void setEmail(String email) { this.email = email; }
}
