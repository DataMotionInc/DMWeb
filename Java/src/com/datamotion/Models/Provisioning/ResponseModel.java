package com.datamotion.Models.Provisioning;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseModel {
	@JsonProperty("TransactionId")
	private String transactionId;
	@JsonProperty("Errors")
	private List<ErrorCodes> errors;
	@JsonProperty("IV")
	private String iv;
	@JsonProperty("Data")
	private String data;
	@JsonProperty("Hash")
	private String hash;
	
	public String getTransactionId() { return this.transactionId; }
	
	public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
	
	public List<ErrorCodes> getErrors() { return this.errors; }
	
	public void setErrors(List<ErrorCodes> errors) { this.errors = errors; }
	
	public String getIV() { return this.iv; }
	
	public void setIV(String iv) { this.iv = iv; }
	
	public String getData() { return this.data; }
	
	public void setData(String data) { this.data = data; }
	
	public String getHash() { return this.hash; }
	
	public void setHash(String hash) { this.hash = hash; }
	
	private enum ErrorCodes
	{
	    NoError,
	    CouldNotCreateCompany,
	    CompanyNotFound,
	    CompanyTypeNotFound,
	    CompanyNameAlreadyExists,
	    DomainAlreadyRegistered,
	    UnableToRegisterDomain,
	    ReferenceIdAlreadyUsed,
	    UnknownCompanyStatus,
	    BillingPeriodInvalid,
	    PastExpirationDate,
	    CouldNotCreateAdmin,
	    CouldNotLoadUser,
	    UserNotInCompany,
	    CouldNotAddUserToUserType,
	    CouldNotUpdateUser,
	    CouldNotCreateUser,
	    InvalideUserId,
	    ErrorDeletingUser,
	    InvalidEmailAddress,
	    TemplateNotFound,
	    UserTypeNotFound,
	    LicenseQuanityLessThanNumberOfUsers,
	    CouldNotCreateUserType,
	    ErrorDeletingUserType,
	    CouldNotCreateCobrand,
	    IncompleteCobrandData,
	    CobrandExists,
	    UnableToProcessCobrandLogo,
	    MoreSecureContactsThanLicenses,
	    SecureContactProvisioningNotAllowed,
	    SecureContactRequiresCobrand,
	    NotAuthenticated,
	    BadCrypto,
	    InvalidPlatformName,
	    UnknownCommand,
	    ErrorConnectingToRemotePlatform,
	    InvalidDate,
	    MalformedJsonString,
	    UnknownError
	}
}
