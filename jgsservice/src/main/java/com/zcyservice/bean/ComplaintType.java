package com.jgsservice.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.jgs.annotation.IntegerColumn;
import com.jgs.bean.BaseEntity;

@Table(name = ComplaintType.TABLE_NAME)
public class ComplaintType extends BaseEntity {

	public static final String ORDER_POSITION = "orderPosition";

	public static final String TABLE_NAME = "ComplaintType";
	
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String EXPIRED_TIME = "expiredTime";
	
	@Column(name = NAME)
	@Expose
	public String name;
	
	@Column(name = DESCRIPTION)
	@Expose
	public String description;

	@Column(name = EXPIRED_TIME)
	@Expose
	public Date expiredTime;
	
	@Column(name = ORDER_POSITION)
	@Expose
	@IntegerColumn
	public Integer orderPosition;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public Integer getOrderPosition() {
    	return orderPosition;
    }

	public void setOrderPosition(Integer orderPosition) {
    	this.orderPosition = orderPosition;
    }


	
	

	
}
