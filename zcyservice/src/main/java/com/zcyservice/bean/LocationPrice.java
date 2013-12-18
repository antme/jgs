package com.zcyservice.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = LocationPrice.TABLE_NAME)
public class LocationPrice extends BaseEntity{

	public static final String TABLE_NAME = "LocationPrice";
	public static final String PRICE = "price";
	public static final String DESCRIBION = "description";
	public static final String LOCATION_ID = "location_id";
	public static final String CATEGORY_ID = "category_id";
	
	@Column(name = PRICE)
	@Expose
	public Double price;
	
	@Column(name = DESCRIBION)
	@Expose
	public String description;
	
	@Column(name = LOCATION_ID)
	@Expose
	public String location_id;
	
	@Column(name = CATEGORY_ID)
	@Expose
	public String category_id;
	
	//join 查询 分类名字
	@Expose
	public String name;


	//VO 字段，前台显示
	@Expose
	public String categoryName;
	
	@Expose
	public String locationName;
	
	
	public Double getPrice() {
    	return price;
    }

	public void setPrice(Double price) {
    	this.price = price;
    }

	public String getDescription() {
    	return description;
    }

	public void setDescription(String description) {
    	this.description = description;
    }

	public String getLocation_id() {
    	return location_id;
    }

	public void setLocation_id(String location_id) {
    	this.location_id = location_id;
    }

	public String getCategory_id() {
    	return category_id;
    }

	public void setCategory_id(String category_id) {
    	this.category_id = category_id;
    }

	public String getName() {
    	return name;
    }

	public void setName(String name) {
    	this.name = name;
    }

	public String getCategoryName() {
    	return categoryName;
    }

	public void setCategoryName(String categoryName) {
    	this.categoryName = categoryName;
    }

	public String getLocationName() {
    	return locationName;
    }

	public void setLocationName(String locationName) {
    	this.locationName = locationName;
    }
	
	
	
}
