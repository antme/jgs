package com.jgsservice.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.jgs.bean.BaseEntity;

@Table(name = News.TABLE_NAME)
public class News extends BaseEntity {

	public static final String TABLE_NAME = "news";

	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String IS_TOP = "isTop";
	public static final String PUBLISH_TIME = "publishTime";
	public static final String EXPIRED_TIME = "expiredTime";
	public static final String STATUS = "status";


	@Column(name = TITLE)
	@Expose
	public String title;

	@Column(name = CONTENT)
	@Expose
	public String content;

	@Column(name = IS_TOP)
	@Expose
	public String isTop;

	@Column(name = PUBLISH_TIME)
	@Expose
	public String publishTime;

	@Column(name = PUBLISH_TIME)
	@Expose
	public String expiredTime;

	@Column(name = STATUS)
	@Expose
	public String status;

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

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(String expiredTime) {
		this.expiredTime = expiredTime;
	}

}
