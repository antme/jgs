package com.zcyservice.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = ArchiveFile.TABLE_NAME)
public class ArchiveFile extends BaseEntity {
	public static final String ARCHIVE_ID = "archiveId";

	public static final String TABLE_NAME = "ArchiveFile";

	@Column(name = ARCHIVE_ID)
	@Expose
	public String archiveId;

	@Column(name = "archiveFileName")
	@Expose
	public String archiveFileName;
	
	@Column(name = "archiveFilePath")
	@Expose
	public String archiveFilePath;

	@Column(name = "archiveFileType")
	@Expose
	public String archiveFileType;

	@Column(name = "archiveType")
	@Expose
	public ArchiveType archiveType;

	@Column(name = "archiveFileLastModifyDate")
	@Expose
	public Date archiveFileLastModifyDate;
	
	
	@Expose
	public String archiveUploadKey;
	

	public String getArchiveId() {
		return archiveId;
	}

	public void setArchiveId(String archiveId) {
		this.archiveId = archiveId;
	}

	public String getArchiveFileName() {
		return archiveFileName;
	}

	public void setArchiveFileName(String archiveFileName) {
		this.archiveFileName = archiveFileName;
	}

	public Date getArchiveFileLastModifyDate() {
		return archiveFileLastModifyDate;
	}

	public void setArchiveFileLastModifyDate(Date archiveFileLastModifyDate) {
		this.archiveFileLastModifyDate = archiveFileLastModifyDate;
	}

	public String getArchiveFileType() {
		return archiveFileType;
	}

	public void setArchiveFileType(String archiveFileType) {
		this.archiveFileType = archiveFileType;
	}

	public ArchiveType getArchiveType() {
		return archiveType;
	}

	public void setArchiveType(ArchiveType archiveType) {
		this.archiveType = archiveType;
	}
	
	

	public String getArchiveFilePath() {
		return archiveFilePath;
	}

	public void setArchiveFilePath(String archiveFilePath) {
		this.archiveFilePath = archiveFilePath;
	}

	
	


	public String getArchiveUploadKey() {
		return archiveUploadKey;
	}

	public void setArchiveUploadKey(String archiveUploadKey) {
		this.archiveUploadKey = archiveUploadKey;
	}





	public enum ArchiveType {
		FIRST, SECOND, FIRST_ATTACH, SECOND_ATTACH
	}

}
