package com.zcxa.share.vo;

import java.util.List;

public class Category {
	private Long id;
	
	private String categoryName;
	
	private String remark;
	
	List<Product> productList;
	
	public Long getId() {
		return id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getRemark() {
		return remark;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", categoryName=" + categoryName + ", remark=" + remark + ", productList="
				+ productList + "]";
	}

	
	
	
}
