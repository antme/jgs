package com.zcyservice.bean.vo;

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

public class SearchVo extends BaseEntity {

	@Expose
	public Date startDate;

	@Expose
	public Date endDate;

	@Expose
	public String keyword;

	@Expose
	public String userStatus;

	@Expose
	public String roleName;

	// 日志类型
	@Expose
	public String logType;

	// 报表类型
	@Expose
	public String reportType;

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

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
