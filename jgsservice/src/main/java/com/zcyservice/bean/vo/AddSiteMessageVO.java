package com.jgsservice.bean.vo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.jgs.bean.BaseEntity;

public class AddSiteMessageVO extends BaseEntity {
	@Expose
	public String title;
	
	@Expose
	public String content;
	
//	public String publishTime;
	@Expose
	public List<String> userIds;

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

	public List<String> getUserIds() {
    	return userIds;
    }

	public void setUserIds(List<String> userIds) {
    	this.userIds = userIds;
    }
	
	
}
