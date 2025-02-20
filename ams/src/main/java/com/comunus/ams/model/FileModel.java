package com.comunus.ams.model;

import java.sql.Date;

public class FileModel {

	private Long id;
	private String fileName;
	private String fileData;
	private String created_date;
	

	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileData() {
		return fileData;
	}
	public void setFileData(String fileData) {
		this.fileData = fileData;
	}
	@Override
	public String toString() {
		return "FileModel [id=" + id + ", fileName=" + fileName + ", fileData=" + fileData + "]";
	}
}
	
	
