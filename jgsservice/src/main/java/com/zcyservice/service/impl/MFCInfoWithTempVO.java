package com.jgsservice.service.impl;

import com.google.gson.annotations.Expose;
import com.jgsservice.bean.Manufacturer;

public class MFCInfoWithTempVO extends Manufacturer {
	
	@Expose
	public String tempId;

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}
	

}
