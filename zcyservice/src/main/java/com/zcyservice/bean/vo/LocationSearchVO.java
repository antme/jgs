package com.zcyservice.bean.vo;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

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
