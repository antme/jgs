package com.zcyservice.bean.vo;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

public class ComplaintSearchVo extends BaseEntity {
	
	@Expose
	public String listType;
	
	@Expose
	public String mobilePhone;

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}


}
