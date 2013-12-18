package com.zcyservice.bean.vo;

import javax.persistence.Column;

import com.google.gson.annotations.Expose;
import com.zcyservice.bean.SpCategoryLocation;

public class SpCLVo extends SpCategoryLocation{

	public static final String IS_VISIBLE = "isVisible";
	
	@Column(name = IS_VISIBLE)
	@Expose
	public Boolean isVisible;

	public Boolean getIsVisible() {
    	return isVisible;
    }

	public void setIsVisible(Boolean isVisible) {
    	this.isVisible = isVisible;
    }
	
	

}
