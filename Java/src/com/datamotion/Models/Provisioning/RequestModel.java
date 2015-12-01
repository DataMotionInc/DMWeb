package com.datamotion.Models.Provisioning;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestModel {
	@JsonProperty("UserName")
	private String userName;
	@JsonProperty("PlatformCode")
	private String platformCode;
	@JsonProperty("Command")
	private Command command;
	@JsonProperty("IV")
	private String iv;
	@JsonProperty("Data")
	private String data;
	@JsonProperty("Hash")
	private String hash;
	
	public String getUserName() { return this.userName; }
	
	public void setUserName(String userName) { this.userName = userName; }
	
	public String getPlatformCode() { return this.platformCode; }
	
	public void setPlatformCode(String platformCode) { this.platformCode = platformCode; }
	
	public Command getCommand() { return this.command; }
	
	public void setCommand(Command command) { this.command = command; }
	
	public String getIV() { return this.iv; }
	
	public void setIV(String iv) { this.iv = iv; }
	
	public String getData() { return this.data; }
	
	public void setData(String data) { this.data = data; }
	
	public String getHash() { return this.hash; }
	
	public void setHash(String hash) { this.hash = hash; }
	
	
	public enum Command
    {
        Create(1),
        Update(2),
        Cancel(3);
        
        private int numVal;
        
        Command(int numVal) {
        	this.numVal = numVal;
        }
        
        public int getNumVal() {
        	return this.numVal;
        }
    }
}
