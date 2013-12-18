package com.zcyservice.bean.vo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

public class ReturnSms extends BaseEntity {

	@Expose
	public String returnstatus;

	@Expose
	public String message;

	@Expose
	public String payinfo;

	@Expose
	public String overage;

	@Expose
	public String sendTotal;

	@Expose
	public String successCounts;

	@Expose
	public String taskID;

	@Expose
	public String remainpoint;
	
	@Expose
	public String checkCounts;

	@Expose
	public List<Callbox> callboxList;

	public String getReturnstatus() {
		return returnstatus;
	}

	public void setReturnstatus(String returnstatus) {
		this.returnstatus = returnstatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPayinfo() {
		return payinfo;
	}

	public void setPayinfo(String payinfo) {
		this.payinfo = payinfo;
	}

	public String getOverage() {
		return overage;
	}

	public void setOverage(String overage) {
		this.overage = overage;
	}

	public String getSendTotal() {
		return sendTotal;
	}

	public void setSendTotal(String sendTotal) {
		this.sendTotal = sendTotal;
	}

	public List<Callbox> getCallboxList() {
		return callboxList;
	}

	public void setCallboxList(List<Callbox> callboxList) {
		this.callboxList = callboxList;
	}

	public String getSuccessCounts() {
		return successCounts;
	}

	public void setSuccessCounts(String successCounts) {
		this.successCounts = successCounts;
	}

	public String getTaskID() {
		return taskID;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	public String getRemainpoint() {
		return remainpoint;
	}

	public void setRemainpoint(String remainpoint) {
		this.remainpoint = remainpoint;
	}

	public String getCheckCounts() {
		return checkCounts;
	}

	public void setCheckCounts(String checkCounts) {
		this.checkCounts = checkCounts;
	}
	
	

}
