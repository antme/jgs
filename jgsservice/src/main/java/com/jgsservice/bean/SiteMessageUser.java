package com.jgsservice.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.jgs.bean.BaseEntity;

@Table(name = SiteMessageUser.TABLE_NAME)
public class SiteMessageUser extends BaseEntity {

	public static final String TABLE_NAME = "SiteMessageUser";
	
	public static final String USER_ID = "userId";
	
	public static final String SITE_MESSAGE_ID = "siteMessageId";
	
	public static final String STATUS = "status";
	
	public static final String TITLE = "title";
	public static final String CONTENT = "content";

	@Column(name = USER_ID)
	@Expose
	public String userId;
	
	@Column(name = SITE_MESSAGE_ID)
	@Expose
	public String siteMessageId;
	
	@Column(name = STATUS)
	@Expose
	public Boolean status;  //0:未读;1:已读
	


	@Column(name = TITLE)
	@Expose
	public String title;
	
	@Column(name = CONTENT)
	@Expose
	public String content;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSiteMessageId() {
		return siteMessageId;
	}

	public void setSiteMessageId(String siteMessageId) {
		this.siteMessageId = siteMessageId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

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
	
	

}
