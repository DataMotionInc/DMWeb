package com.datamotion.Models.Admin;

import java.util.Dictionary;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountModel {
	@JsonProperty("UniqueId")
	private int uniqueId;
	@JsonProperty("Email")
	private String email;
	@JsonProperty("UserId")
	private String userId;
	@JsonProperty("SingleSignOnId")
	private String singleSignOnId;
	@JsonProperty("Password")
	private String password;
	@JsonProperty("FirstName")
	private String firstName;
	@JsonProperty("LastName")
	private String lastName;
	@JsonProperty("Phone")
	private String phone;
	@JsonProperty("CompanyId")
	private int companyId;
	@JsonProperty("EmployeeId")
	private String employeeId;
	@JsonProperty("Miscellaneous")
	private String miscellaneous;
	@JsonProperty("Disabled")
	private boolean disabled;
	@JsonProperty("UserTypeId")
	private int userTypeId;
	@JsonProperty("ButtonUser")
	private boolean buttonUser;
	@JsonProperty("ReceiveOffers")
	private boolean receiveOffers;
	@JsonProperty("Errors")
	private Dictionary<String, List<String>> errors;
	
	public int getUniqueId() { return this.uniqueId; }
	
	public void setUniqueId(int uniqueId) { this.uniqueId = uniqueId; }
	
	public String getEmail() { return this.email; }
	
	public void setEmail(String email) { this.email = email; }
	
	public String getUserId() { return this.userId; }
	
	public void setUserId(String userId) { this.userId = userId; }
	
	public String getSingleSignOnId() { return this.singleSignOnId; }
	
	public void setSingleSignOnId(String singleSignOn) { this.singleSignOnId = singleSignOn; }
	
	public String getPassword() { return this.password; }
	
	public void setPassword(String password) { this.password = password; }
	
	public String getFirstName() { return this.firstName; }
	
	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	public String getLastName() { return this.lastName; }
	
	public void setLastName(String lastName) { this.lastName = lastName; }
	
	public String getPhone() { return this.phone; }
	
	public void setPhone(String phone) { this.phone = phone; }
	
	public int getCompanyId() { return this.companyId; }
	
	public void setCompanyId(int companyId) { this.companyId = companyId; }
	
	public String getEmployeeId() { return this.employeeId; }
	
	public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
	
	public String getMiscellaneous() { return this.miscellaneous; }
	
	public void setMiscellaneous(String miscellaneous) { this.miscellaneous = miscellaneous; }
	
	public boolean getDisabled() { return this.disabled; }
	
	public void setDisabled(boolean disabled) { this.disabled = disabled; }
	
	public int getUserTypeId() { return this.userTypeId; }
	
	public void setUserTypeId(int userTypeId) { this.userTypeId = userTypeId; }
	
	public boolean getButtonUser() { return this.buttonUser; }
	
	public void setButtonUser(boolean buttonUser) { this.buttonUser = buttonUser; }
	
	public boolean getReceiveOffers() { return this.receiveOffers; }
	
	public void setReceiveOffers(boolean receiveOffers) { this.receiveOffers = receiveOffers; }
	
	public Dictionary<String, List<String>> getErrors() { return this.errors; }
	
	public void setErrors(Dictionary<String, List<String>> errors) { this.errors = errors; }
}
