package com.zcyservice.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = Archive.TABLE_NAME)
public class Archive extends BaseEntity {

	public static final String TABLE_NAME = "Archive";

	// 案号
	@Column(name = "archiveCode")
	@Expose
	public String archiveCode;

	// 案由
	@Column(name = "archiveName")
	@Expose
	public String archiveName;

	@Column(name = "archiveDescription")
	@Expose
	public String archiveDescription;

	// 归档状态
	@Column(name = "archiveStatus")
	@Expose
	public String archiveStatus;

	// 处理结果
	@Column(name = "archiveResult")
	@Expose
	public String archiveResult;

	// 申请人
	@Column(name = "archiveApplicant")
	@Expose
	public String archiveApplicant;

	// 申请人
	@Column(name = "archiveThirdPerson")
	@Expose
	public String archiveThirdPerson;

	// 被申请人
	@Column(name = "archiveOppositeApplicant")
	@Expose
	public String archiveOppositeApplicant;

	// 承办人
	@Column(name = "archiveJudge")
	@Expose
	public String archiveJudge;

	// 立案日期
	@Column(name = "archiveOpenDate")
	@Expose
	public Date archiveOpenDate;

	// 结案日期
	@Column(name = "archiveCloseDate")
	@Expose
	public Date archiveCloseDate;

	// 归档日期
	@Column(name = "archiveDate")
	@Expose
	public Date archiveDate;

	// 归档号数
	@Column(name = "archiveSerialNumber")
	@Expose
	public String archiveSerialNumber;

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

	public String getArchiveStatus() {
		return archiveStatus;
	}

	public void setArchiveStatus(String archiveStatus) {
		this.archiveStatus = archiveStatus;
	}

	public String getArchiveApplicant() {
		return archiveApplicant;
	}

	public void setArchiveApplicant(String archiveApplicant) {
		this.archiveApplicant = archiveApplicant;
	}

	public String getArchiveResult() {
		return archiveResult;
	}

	public void setArchiveResult(String archiveResult) {
		this.archiveResult = archiveResult;
	}

	public String getArchiveThirdPerson() {
		return archiveThirdPerson;
	}

	public void setArchiveThirdPerson(String archiveThirdPerson) {
		this.archiveThirdPerson = archiveThirdPerson;
	}

	public String getArchiveOppositeApplicant() {
		return archiveOppositeApplicant;
	}

	public void setArchiveOppositeApplicant(String archiveOppositeApplicant) {
		this.archiveOppositeApplicant = archiveOppositeApplicant;
	}

	public String getArchiveJudge() {
		return archiveJudge;
	}

	public void setArchiveJudge(String archiveJudge) {
		this.archiveJudge = archiveJudge;
	}

	public Date getArchiveOpenDate() {
		return archiveOpenDate;
	}

	public void setArchiveOpenDate(Date archiveOpenDate) {
		this.archiveOpenDate = archiveOpenDate;
	}

	public Date getArchiveCloseDate() {
		return archiveCloseDate;
	}

	public void setArchiveCloseDate(Date archiveCloseDate) {
		this.archiveCloseDate = archiveCloseDate;
	}

	public Date getArchiveDate() {
		return archiveDate;
	}

	public void setArchiveDate(Date archiveDate) {
		this.archiveDate = archiveDate;
	}

	public String getArchiveSerialNumber() {
		return archiveSerialNumber;
	}

	public void setArchiveSerialNumber(String archiveSerialNumber) {
		this.archiveSerialNumber = archiveSerialNumber;
	}
	
	

}
