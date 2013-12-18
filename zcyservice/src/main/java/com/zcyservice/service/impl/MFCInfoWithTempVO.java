package com.zcyservice.service.impl;

import com.google.gson.annotations.Expose;
import com.zcyservice.bean.Manufacturer;

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
