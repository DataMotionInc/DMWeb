package com.datamotion.Models.Admin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTypeModel {
	@JsonProperty("TypeId")
	private int typeId;
	@JsonProperty("Description")
	private String description;
	@JsonProperty("MinimumPasswordLength")
	private String minimumPasswordLength;
	@JsonProperty("RequiredPasswordCategories")
	private String requiredPasswordCategories;
	@JsonProperty("Categories")
	private String[] categories;
	
	public int getTypeId() { return this.typeId; }
	
	public void setTypeId(int typeId) { this.typeId = typeId; }
	
	public String getDescription() { return this.description; }
	
	public void setDescription(String description) { this.description = description; }
	
	public String getMinimumPasswordLength() { return this.minimumPasswordLength; }
	
	public void setMinimumPasswordLength(String minimumPasswordLength) { this.minimumPasswordLength = minimumPasswordLength; }
	
	public String getRequiredPasswordCategories() { return this.requiredPasswordCategories; }
	
	public void setRequiredPasswordCategories(String requiredPasswordCategories) { this.requiredPasswordCategories = requiredPasswordCategories; }
	
	public String[] getCategories() { return this.categories; }
	
	public void setCategories(String[] categories) { this.categories = categories; }
}
