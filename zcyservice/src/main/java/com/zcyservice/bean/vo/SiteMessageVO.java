package com.zcyservice.bean.vo;


import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

public class SiteMessageVO extends BaseEntity {


	@Expose
	public String title;
	
	@Expose
	public String content;
	
	@Expose
	public String status;

	@Expose
	public String siteMessageId;
	
	@Expose
	public String siteMessageUserId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSiteMessageId() {
		return siteMessageId;
	}

	public void setSiteMessageId(String siteMessageId) {
		this.siteMessageId = siteMessageId;
	}

	public String getSiteMessageUserId() {
		return siteMessageUserId;
	}

	public void setSiteMessageUserId(String siteMessageUserId) {
		this.siteMessageUserId = siteMessageUserId;
	}

	
	
	
}
