package com.jgsservice.bean.vo;

import com.google.gson.annotations.Expose;
import com.jgs.bean.BaseEntity;

public class LocationSearchVO extends BaseEntity {
	
	@Expose
	public String parent;

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
	
	
}
