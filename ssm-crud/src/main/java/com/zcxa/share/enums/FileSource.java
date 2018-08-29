package com.zcxa.share.enums;


public enum FileSource {
	
	TZGG("100", "通知公告附件", "Doc.Notice"),

    Other("10000", "其他", "Doc.CMM.Other");
	
	
	
	private String id;
	private String name;
	private String type;
	
	private FileSource(String id, String name, String type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public static FileSource findById(String id){
		for(FileSource fileSource: FileSource.values()){
			if(fileSource.getId().equals(id)){
				return fileSource;
			}
		}
		return null;
	}
}
