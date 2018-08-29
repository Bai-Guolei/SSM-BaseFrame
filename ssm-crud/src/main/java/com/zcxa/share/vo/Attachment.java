package com.zcxa.share.vo;

import java.io.Serializable;
import java.util.Date;

public class Attachment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * Mogodb存储文件ID
	 */
	private String fileId;
	
	/**
	 * 相关表结构的ID
	 */
	private Long dataId;
	
	/**
	 * 文件名称
	 */
	private String fileName;
	
	/**
	 * 文件编码， 针对一条数据不同类型文件， 如果不传默认1(兼容以前版本),
	 */
	private String fileCode;
	
	/**
	 * 文件大小
	 */
	private Long fileSize;
	
	/**
	 * 文件来源
	 */
	private String fileSource;
	
	/**
	 * 创建人
	 */
	private Long createUerId;
	/**
	 * 创建人姓名
	 */
	private String createUserName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 删除标记
	 */
	private Integer isDel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileSource() {
		return fileSource;
	}

	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}

	public Long getCreateUerId() {
		return createUerId;
	}

	public void setCreateUerId(Long createUerId) {
		this.createUerId = createUerId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	
	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getFileCode() {
		return fileCode;
	}

	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}
	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
}
