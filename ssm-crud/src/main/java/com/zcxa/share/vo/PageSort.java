package com.zcxa.share.vo;

public class PageSort {
	private int page;
	private int rows;
	private String sortName;
	private String sortOrder;

	
	public PageSort() {
		this.page = 1;
		this.rows = 20;
	}

	
	public int getPage() {
		return page;
	}


	public int getRows() {
		return rows;
	}


	public void setPage(int page) {
		this.page = page;
	}


	public void setRows(int rows) {
		this.rows = rows;
	}


	public String getSortName() {
		return this.sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
}
