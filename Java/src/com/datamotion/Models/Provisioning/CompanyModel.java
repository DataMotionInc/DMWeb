package com.datamotion.Models.Provisioning;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyModel {
	@JsonProperty("ReferenceId")
	private String referenceId;
	@JsonProperty("CompanyName")
	private String companyName;
	@JsonProperty("Admin")
	private UserModel admin;
	@JsonProperty("BillingInterval")
	private BillingIntervalModel billingInterval;
	@JsonProperty("ExternalIp")
	private String externalIp;
	@JsonProperty("Status")
	private Status status;
	@JsonProperty("UserTypes")
	private List<UserTypeModel> userTypes;
	@JsonProperty("Cobrand")
	private CobrandModel cobrand;
	@JsonProperty("AutoSenderDomains")
	private List<String> autoSenderDomains;
	@JsonProperty("ExpirationDate")
	private String expirationDate;
	
	public String getReferenceId() { return this.referenceId; }
	
	public void setReferenceId(String referenceId) { this.referenceId = referenceId; }
	
	public String getCompanyName() { return this.companyName; }
	
	public void setCompanyName(String companyName) { this.companyName = companyName; }
	
	public UserModel getAdmin() { return this.admin; }
	
	public void setAdmin(UserModel admin) { this.admin = admin; }
	
	public BillingIntervalModel getBillingInterval() { return this.billingInterval; }
	
	public void setBillingInterval(BillingIntervalModel billingInterval) { this.billingInterval = billingInterval; }
	
	public String getExternalIp() { return this.externalIp; }
	
	public void setExternalIp(String externalIp) { this.externalIp = externalIp; }
	
	public Status getStatus() { return this.status; }
	
	public void setStatus(Status status) { this.status = status; }
	
	public List<UserTypeModel> getUserTypes() { return this.userTypes; }
	
	public void setUserTypes(List<UserTypeModel> userTypes) { this.userTypes = userTypes; }
	
	public CobrandModel getCobrand() { return this.cobrand; }
	
	public void setCobrand(CobrandModel cobrand) { this.cobrand = cobrand; }
	
	public List<String> getAutoSenderDomains() { return this.autoSenderDomains; }
	
	public void setAutoSenderDomains(List<String> autoSenderDomains) { this.autoSenderDomains = autoSenderDomains; }
	
	public String getExpirationDate() { return this.expirationDate; }
	
	public void setExpirationDate(String expirationDate) { this.expirationDate = expirationDate; }
	
	public enum Status
    {
        Active(0),
        Trial(1),
        Cancel(2),
        NoUpdate(3);
        
        private int numVal;
        
        Status(int numVal) {
        	this.numVal = numVal;
        }
        
        public int getNumVal() {
        	return this.numVal;
        }
    }
}
