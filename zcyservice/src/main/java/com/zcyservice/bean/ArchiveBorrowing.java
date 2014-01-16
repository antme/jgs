package com.zcyservice.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = ArchiveBorrowing.TABLE_NAME)
public class ArchiveBorrowing extends BaseEntity {

	public static final String TABLE_NAME = "ArchiveBorrowing";

	public static final String ARCHIVE_ID = "archiveId";

	@Column(name = ARCHIVE_ID)
	@Expose
	public String archiveId;

	// 调阅人
	@Column(name = "borrowingName")
	@Expose
	public String borrowingName;

	// 调阅单位
	@Column(name = "borrowingOrganization")
	@Expose
	public String borrowingOrganization;

	// 调阅日期
	@Column(name = "borrowingDate")
	@Expose
	public Date borrowingDate;

	// 备注
	@Column(name = "remark")
	@Expose
	public String remark;
	
	
	
	//join 查询
	// 案号
	@Expose
	public String archiveCode;

	// 案由
	@Expose
	public String archiveName;

	public String getArchiveId() {
		return archiveId;
	}

	public void setArchiveId(String archiveId) {
		this.archiveId = archiveId;
	}

	public String getBorrowingName() {
		return borrowingName;
	}

	public void setBorrowingName(String borrowingName) {
		this.borrowingName = borrowingName;
	}

	public String getBorrowingOrganization() {
		return borrowingOrganization;
	}

	public void setBorrowingOrganization(String borrowingOrganization) {
		this.borrowingOrganization = borrowingOrganization;
	}

	public Date getBorrowingDate() {
		return borrowingDate;
	}

	public void setBorrowingDate(Date borrowingDate) {
		this.borrowingDate = borrowingDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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
	
	

}
