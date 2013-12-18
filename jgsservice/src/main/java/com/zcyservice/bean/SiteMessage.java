package com.jgsservice.bean;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.jgs.bean.BaseEntity;

@Table(name = SiteMessage.TABLE_NAME)
public class SiteMessage extends BaseEntity {

	public static final String TABLE_NAME = "SiteMessage";
	
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	//public static final String PUBLISH_TIME = "publishTime";
	

	@Column(name = TITLE)
	@Expose
	public String title;
	
	@Column(name = CONTENT)
	@Expose
	public String content;
	
//	@Column(name = PUBLISH_TIME)
//	@Expose
//	public String publishTime;

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
