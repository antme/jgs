package com.jgsservice.bean.vo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.jgs.bean.BaseEntity;

public class IDS extends BaseEntity {

	@Expose
	public List<String> ids;

	@Expose
	public String comments;

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
