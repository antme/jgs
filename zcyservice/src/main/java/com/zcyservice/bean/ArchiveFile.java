package com.zcyservice.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = ArchiveFile.TABLE_NAME)
public class ArchiveFile extends BaseEntity {
	public static final String TABLE_NAME = "ArchiveFile";

	@Column(name = "archiveId")
	@Expose
	public String archiveId;

	@Column(name = "archiveFileName")
	@Expose
	public String archiveFileName;

	@Column(name = "archiveFileLastModifyDate")
	@Expose
	public Date archiveFileLastModifyDate;

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

}
