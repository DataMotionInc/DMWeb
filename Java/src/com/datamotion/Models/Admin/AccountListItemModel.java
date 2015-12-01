package com.datamotion.Models.Admin;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountListItemModel {
	@JsonProperty("UniqueId")
	private int uniqueId;
	@JsonProperty("Aid")
	private int aid;
	@JsonProperty("CompanyId")
	private int companyId;
	@JsonProperty("Created")
	private DateTime created;
	@JsonProperty("LastNotice")
	private DateTime lastNotice;
	@JsonProperty("DiskSpaceUsed")
	private String diskSpaceUsed;
	@JsonProperty("EmailId")
	private int emailId;
	@JsonProperty("Email")
	private String email;
	@JsonProperty("EmployeeId")
	private String employeeId;
	@JsonProperty("FirstName")
	private String firstName;
	@JsonProperty("LastName")
	private String lastName;
	@JsonProperty("LastLogin")
	private DateTime lastLogin;
	@JsonProperty("MessagesReceived")
	private int messagesReceived;
	@JsonProperty("MessagesSent")
	private int messagesSent;
	@JsonProperty("UserId")
	private String userId;
	@JsonProperty("UserTypeId")
	private int userTypeId;
	
	public int getUniqueId() { return this.uniqueId; }
	
	public void setUniqueId(int uniqueId) { this.uniqueId = uniqueId; }
	
	public int getAid() { return this.aid; }
	
	public void setAid(int aid) { this.aid = aid; }
	
	public int getCompanyId() { return this.companyId; }
	
	public void setCompanyId(int companyId) { this.companyId = companyId; }
	
	public DateTime getCreated() { return this.created; }
	
	public void setCreated(DateTime created) { this.created = created; }
	
	public DateTime getLastNotice() { return this.lastNotice; }
	
	public void setLastNotice(DateTime lastNotice) { this.lastNotice = lastNotice; }
	
	public String getDiskSpaceUsed() { return this.diskSpaceUsed; }
	
	public void setDiskSpaceUsed(String diskSpaceUsed) { this.diskSpaceUsed = diskSpaceUsed; }
	
	public int getEmailId() { return this.emailId; }
	
	public void setEmailId(int emailId) { this.emailId = emailId; }
	
	public String getEmail() { return this.email; }
	
	public void setEmail(String email) { this.email = email; }
	
	public String getEmployeeId() { return this.employeeId; }
	
	public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
	
	public String getFirstName() { return this.firstName; }
	
	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	public String getLastName() { return this.lastName; }
	
	public void setLastName(String lastName) { this.lastName = lastName; }
	
	public DateTime getLastLogin() { return this.lastLogin; }
	
	public void setLastLogin(DateTime lastLogin) { this.lastLogin = lastLogin; }
	
	public int getMessagesReceived() { return this.messagesReceived; }
	
	public void setMessagesReceived(int messagesReceived) { this.messagesReceived = messagesReceived; }
	
	public int getMessagesSent() { return this.messagesSent; }
	
	public void setMessagesSent(int messagesSent) { this.messagesSent = messagesSent; }
	
	public String getUserId() { return this.userId; }
	
	public void setUserId(String userId) { this.userId = userId; }
	
	public int getUserTypeId() { return this.userTypeId; }
	
	public void setUserTypeId(int userTypeId) { this.userTypeId = userTypeId; }
}
