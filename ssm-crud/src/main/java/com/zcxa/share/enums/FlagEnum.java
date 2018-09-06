package com.zcxa.share.enums;

public enum FlagEnum {
	YES(1,"是"),
	NO(0, "否");
	
	private Integer id;
	
	private String value;

	private FlagEnum(Integer id, String value) {
		this.id = id;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public FlagEnum getFlagEnum(Integer id) {
		if(null == id) {
			return null;
		}
		for(FlagEnum flag : values()) {
			if(flag.getId() == id) {
				return flag;
			}
		}
		return null;
	}
	
}
