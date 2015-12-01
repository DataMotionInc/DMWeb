package com.datamotion.Models.Admin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentityModel {
	@JsonProperty("UID")
	private Integer uid;
	
	@JsonProperty("UserId")
	private String userId;
	
	@JsonProperty("SingleSignOnId")
	private String singleSignOnId;
	
	@JsonProperty("Email")
	private String email;
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public Integer getUid()
    {
        return uid;
    }

    public void setUid(Integer uid)
    {
        this.uid = uid;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getSingleSignOnId()
    {
        return singleSignOnId;
    }

    public void setSingleSignOnId(String singleSignOnId)
    {
        this.singleSignOnId = singleSignOnId;
    }
}