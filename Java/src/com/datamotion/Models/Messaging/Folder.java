package com.datamotion.Models.Messaging;

public class Folder {
	private int FID;
	public int getFID() { return this.FID; }
	public void setFID(int FID) { this.FID = FID; }
	private String Name;
	public String getName() { return this.Name; }
	public void setName(String Name) { this.Name = Name; }
	private int Type;
	public int getType() { return this.Type; }
	public void setType(int Type) { this.Type = Type; }
	private String TypeDesc;
	public String getTypeDesc() { return this.TypeDesc; }
	public void setTypeDesc(String TypeDesc) { this.TypeDesc = TypeDesc; }
	private int TotalMessages;
	public int getTotalMessages() { return this.TotalMessages; }
	public void setTotalMessage(int TotalMessages) { this.TotalMessages = TotalMessages; }
}
