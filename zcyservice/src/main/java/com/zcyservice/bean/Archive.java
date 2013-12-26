package com.zcyservice.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = Archive.TABLE_NAME)
public class Archive extends BaseEntity {

	public static final String TABLE_NAME = "Archive";

	@Column(name = "archiveCode")
	@Expose
	public String archiveCode;

	@Column(name = "archiveName")
	@Expose
	public String archiveName;

	@Column(name = "archiveDescription")
	@Expose
	public String archiveDescription;

	@Column(name = "archiveFilePath")
	@Expose
	public String archiveFilePath;

	@Column(name = "archiveStatus")
	@Expose
	public String archiveStatus;

	public String getArchiveCode() {
		return archiveCode;
	}

	public void setArchiveCode(String archiveCode) {
		this.archiveCode = archiveCode;
	}

	public String getArchiveName() {
		return archiveName;
	}

	public void setArchiveName(String archiveName) {
		this.archiveName = archiveName;
	}

	public String getArchiveDescription() {
		return archiveDescription;
	}

	public void setArchiveDescription(String archiveDescription) {
		this.archiveDescription = archiveDescription;
	}

	public String getArchiveFilePath() {
		return archiveFilePath;
	}

	public void setArchiveFilePath(String archiveFilePath) {
		this.archiveFilePath = archiveFilePath;
	}

	public String getArchiveStatus() {
		return archiveStatus;
	}

	public void setArchiveStatus(String archiveStatus) {
		this.archiveStatus = archiveStatus;
	}

}
