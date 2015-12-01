package com.datamotion.Models.Admin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginModel {
	@JsonProperty("Identity")
	private IdentityModel identity;
	
	@JsonProperty("TimeStamp")
	private String currentTimestamp;
	
	@JsonProperty("IpAddress")
	private String ipAddress;
	
	public IdentityModel getIdentity()
	{
		return identity;
	}
	
	public void setIdentity(IdentityModel identity)
	{
		this.identity = identity;
	}
	
	public String getCurrentTimestamp()
	{
		return currentTimestamp;
	}
	
	public void setCurrentTimestamp(String currentTimestamp)
	{
		this.currentTimestamp = currentTimestamp;
	}
	
	public String getIpAddress()
	{
		return ipAddress;
	}
	
	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}
}

