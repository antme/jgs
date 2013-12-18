package com.jgsservice.bean.vo;

import com.google.gson.annotations.Expose;
import com.jgs.bean.BaseEntity;

public class SiteMessageDetailVO extends BaseEntity {

	@Expose
	public String title;
	
	@Expose
	public String content;
	
	@Expose
	public String receivers;

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

	public String getReceivers() {
		return receivers;
	}

	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}

}
