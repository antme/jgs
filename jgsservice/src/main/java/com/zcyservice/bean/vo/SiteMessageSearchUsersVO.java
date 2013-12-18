package com.jgsservice.bean.vo;

import com.google.gson.annotations.Expose;
import com.jgs.bean.BaseEntity;

public class SiteMessageSearchUsersVO extends BaseEntity {
	@Expose
	public String group;
	
	@Expose
	public String province;
	
	@Expose
	public String city;
	
	@Expose
	public String district;
	
	@Expose
	public String status;
	
	@Expose
	public String keyword;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	

}
