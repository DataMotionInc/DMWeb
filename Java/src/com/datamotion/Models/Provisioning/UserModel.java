package com.datamotion.Models.Provisioning;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserModel {
	@JsonProperty("FirstName")
	private String firstName;
	@JsonProperty("LastName")
	private String lastName;
	@JsonProperty("Email")
	private String email;
	@JsonProperty("PhoneNumber")
	private String phoneNumber;
	
	public String getFirstName() { return this.firstName; }
	
	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	public String getLastName() { return this.lastName; }
	
	public void setLastName(String lastName) { this.lastName = lastName; }
	
	public String getEmail() { return this.email; }
	
	public void setEmail(String email) { this.email = email; }
	
	public String getPhoneNumber() { return this.phoneNumber; }
	
	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
