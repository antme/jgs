package com.jgsservice.bean.vo;

import com.google.gson.annotations.Expose;

public class Callbox {
	@Expose
	public String mobile;

	@Expose
	public String taskid;

	@Expose
	public String content;

	@Expose
	public String receivetime;
	
	@Expose
	public String extno;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(String receivetime) {
		this.receivetime = receivetime;
	}

	
	
}
