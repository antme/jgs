package com.jgsservice.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.jgs.bean.BaseEntity;

@Table(name = Advertisement.TABLE_NAME)
public class Advertisement extends BaseEntity {

	public static final String TABLE_NAME = "advertisement";

	public static final String PAGE = "page";
	public static final String LOCATION = "location";
	public static final String IMAGE_URL = "imageUrl";
	public static final String HREF = "href";
	public static final String PUBLISH_TIME = "publishTime";
	

	@Column(name = PAGE)
	@Expose
	public String page;
	
	@Column(name = LOCATION)
	@Expose
	public String location;
	
	@Column(name = IMAGE_URL)
	@Expose
	public String imageUrl;
	
	@Column(name = HREF)
	@Expose
	public String href;
	
	@Column(name = PUBLISH_TIME)
	@Expose
	public String publishTime;

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	
	

}
