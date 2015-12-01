package com.datamotion.Models.Admin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountIndexRequestModel {
	@JsonProperty("UserTypeId")
	private int userTypeId;
	@JsonProperty("PageNumber")
	private int pageNumber;
	@JsonProperty("CompanyId")
	private int companyId;
	@JsonProperty("OrderBy")
	private String orderBy;
	@JsonProperty("Filter")
	private String filter;
	
	public int getUserTypeId() { return this.userTypeId; }
	
	public void setUserTypeId(int userTypeId) { this.userTypeId = userTypeId; }
	
	public int getPageNumber() { return this.pageNumber; }
	
	public void setPageNumber(int pageNumber) { this.pageNumber = pageNumber; }
	
	public String getOrderBy() { return this.orderBy; }
	
	public void setOrderBy(String orderBy) { this.orderBy = orderBy; }
	
	public String getFilter() { return this.filter; }
	
	public void setFilter(String filter) { this.filter = filter; }
}
