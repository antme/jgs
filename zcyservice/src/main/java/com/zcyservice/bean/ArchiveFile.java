package com.zcyservice.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = ArchiveFile.TABLE_NAME)
public class ArchiveFile extends BaseEntity {
	public static final String ARCHIVE_FILE_NAME = "archiveFileName";

	public static final String ARCHIVE_FILE_PATH = "archiveFilePath";

	public static final String ARCHIVE_FILE_PROPERTY = "archiveFileProperty";

	public static final String ARCHIVE_TEXT_DATA = "archiveTextData";

	public static final String ARCHIVE_ID = "archiveId";

	public static final String TABLE_NAME = "ArchiveFile";

	@Column(name = ARCHIVE_ID)
	@Expose
	public String archiveId;

	@Column(name = ARCHIVE_FILE_NAME)
	@Expose
	public String archiveFileName;

	@Column(name = ARCHIVE_FILE_PATH)
	@Expose
	public String archiveFilePath;

	@Column(name = "archiveFileType")
	@Expose
	public String archiveFileType;

	@Column(name = ARCHIVE_FILE_PROPERTY)
	@Expose
	public ArchiveFileProperty archiveFileProperty;

	@Column(name = "archiveFileLastModifyDate")
	@Expose
	public Date archiveFileLastModifyDate;
	
	
	
	// 扫描的数据
	@Column(name = ARCHIVE_TEXT_DATA)
	@Expose
	public String archiveTextData;
	
	

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

	public ArchiveFileProperty getArchiveFileProperty() {
		return archiveFileProperty;
	}

	public void setArchiveFileProperty(ArchiveFileProperty archiveType) {
		this.archiveFileProperty = archiveType;
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
	
	

	public String getArchiveTextData() {
    	return archiveTextData;
    }

	public void setArchiveTextData(String archiveTextData) {
    	this.archiveTextData = archiveTextData;
    }



	public enum ArchiveFileProperty {
		ATTACH_FILE, MAIN_FILE
	}

}
