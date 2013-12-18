package com.zcyservice.bean.vo;

import com.google.gson.annotations.Expose;
import com.zcyservice.bean.ServiceProvider;

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
