package com.zcyservice.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = Archive.TABLE_NAME)
public class Archive extends BaseEntity {

	public static final String ARCHIVE_NAME = "archiveName";

	public static final String ARCHIVE_CODE = "archiveCode";

	public static final String ACHIVE_PROCESS_STATUS = "archiveProcessStatus";

	public static final String TABLE_NAME = "Archive";

	// 案号
	@Column(name = ARCHIVE_CODE)
	@Expose
	public String archiveCode;

	// 案由
	@Column(name = ARCHIVE_NAME)
	@Expose
	public String archiveName;

	@Column(name = "archiveDescription")
	@Expose
	public String archiveDescription;

	// 归档状态
	@Column(name = "archiveStatus")
	@Expose
	public ArchiveStatus archiveStatus;

	// 审批状态
	@Column(name = ACHIVE_PROCESS_STATUS)
	@Expose
	public ProcessStatus archiveProcessStatus;

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

	// 归档号数
	@Column(name = "destroyComments")
	@Expose
	public String destroyComments;
	
	
	
	
	//页面搜索字段
	@Expose
	public Date startDate;

	@Expose
	public Date endDate;

	@Expose
	public String keyword;
	

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

	public ArchiveStatus getArchiveStatus() {
		return archiveStatus;
	}

	public void setArchiveStatus(ArchiveStatus archiveStatus) {
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

	public ProcessStatus getArchiveProcessStatus() {
		return archiveProcessStatus;
	}

	public void setArchiveProcessStatus(ProcessStatus processStatus) {
		this.archiveProcessStatus = processStatus;
	}
	
	

	public String getDestroyComments() {
		return destroyComments;
	}

	public void setDestroyComments(String destroyComments) {
		this.destroyComments = destroyComments;
	}

	
	


	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}





	public enum ArchiveStatus {
		NEW, ARCHIVED

	}

	public enum ProcessStatus {
		DRAFT, NEW, APPROVED, REJECTED, DESTROYING, DESTROYED;

	}
	
	

}
