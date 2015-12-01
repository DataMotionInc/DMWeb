package com.datamotion.Models.Admin;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountListModel {
	@JsonProperty("Count")
	private int count;
	@JsonProperty("CurrentPage")
	private int currentPage;
	@JsonProperty("TotalPages")
	private int totalPages;
	@JsonProperty("TotalUsers")
	private String totalUsers;
	@JsonProperty("PageSize")
	private String pageSize;
	@JsonProperty("Accounts")
	private List<AccountListItemModel> accounts;
	
	public int getCount() { return this.count; }
	
	public void setCount(int count) { this.count = count; }
	
	public int getCurrentPage() { return this.currentPage; }
	
	public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }
	
	public int getTotalPages() { return this.totalPages; }
	
	public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
	
	public String getTotalUsers() { return this.totalUsers; }
	
	public void setTotalUsers(String totalUsers) { this.totalUsers = totalUsers; }
	
	public String getPageSize() { return this.pageSize; }
	
	public void setPageSize(String pageSize) { this.pageSize = pageSize; }
	
	public List<AccountListItemModel> getAccounts() { return this.accounts; }
	
	public void setAccounts(List<AccountListItemModel> accounts) { this.accounts = accounts; }
}
