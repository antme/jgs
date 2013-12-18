package com.jgsservice.bean.vo;

import com.google.gson.annotations.Expose;
import com.jgsservice.bean.ServiceOrder;

public class UserOrderVO extends ServiceOrder{

	@Expose
	public Integer userScoreType;


	@Expose
	public String userScoreComment;


	public Integer getUserScoreType() {
    	return userScoreType;
    }


	public void setUserScoreType(Integer userScoreType) {
    	this.userScoreType = userScoreType;
    }


	public String getUserScoreComment() {
    	return userScoreComment;
    }


	public void setUserScoreComment(String userScoreComment) {
    	this.userScoreComment = userScoreComment;
    }
	
	
	
}
