package com.datamotion.Models.Provisioning;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CobrandModel {
	@JsonProperty("Url")
	private String url;
	@JsonProperty("Color")
	private String color;
	@JsonProperty("Logo")
	private byte[] logo;
	@JsonProperty("LogoFileName")
	private String logoFileName;
	@JsonProperty("SecureContacts")
	private SecureContactModel secureContacts;
	
	public String getUrl() { return this.url; }
	
	public void setUrl(String url) { this.url = url; }
	
	public String getColor() { return this.color; }
	
	public void setColor(String color) { this.color = color; }
	
	public byte[] getLogo() { return this.logo; }
	
	public void setLogo(byte[] logo) { this.logo = logo; }
	
	public String getLogoFileName() { return this.logoFileName; }
	
	public void setLogoFileName(String logoFileName) { this.logoFileName = logoFileName; }
	
	public SecureContactModel getSecureContacts() { return this.secureContacts; }
	
	public void setSecureContacts(SecureContactModel secureContacts) { this.secureContacts = secureContacts; }
}
