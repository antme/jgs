package com.jgsservice.bean.vo;

import com.google.gson.annotations.Expose;
import com.jgsservice.bean.ServiceProvider;

public class SPInfoWithTempVO extends ServiceProvider {
	
	@Expose
	public String tempId;

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}
	
}
