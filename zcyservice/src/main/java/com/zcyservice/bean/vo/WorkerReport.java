package com.zcyservice.bean.vo;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

public class WorkerReport extends BaseEntity {

	@Expose
	public String spUserName;
	

	@Expose
	public Integer count;


	public String getSpUserName() {
		return spUserName;
	}


	public void setSpUserName(String spUserName) {
		this.spUserName = spUserName;
	}


	public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}
