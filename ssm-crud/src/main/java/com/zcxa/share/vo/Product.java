package com.zcxa.share.vo;

import java.io.Serializable;

public class Product implements Serializable{
	/**
	 * 产品实体
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private String name;
    private String description;
    private Double unitPrice;
    private String imageUrl;
    private int isNew;
    private int categoryId;
    private Category category;
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public int getIsNew() {
		return isNew;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public Category getCategory() {
		return category;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", unitPrice=" + unitPrice
				+ ", imageUrl=" + imageUrl + ", isNew=" + isNew + ", categoryId=" + categoryId + ", category="
				+ category + ", getId()=" + getId() + ", getName()=" + getName() + ", getDescription()="
				+ getDescription() + ", getUnitPrice()=" + getUnitPrice() + ", getImageUrl()=" + getImageUrl()
				+ ", getIsNew()=" + getIsNew() + ", getCategoryId()=" + getCategoryId() + ", getCategory()="
				+ getCategory() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
    
	
}
