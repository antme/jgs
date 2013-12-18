package com.jgsservice.bean;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.jgs.annotation.IntegerColumn;
import com.jgs.bean.BaseEntity;

@Table(name = Location.TABLE_NAME)
public class Location extends BaseEntity{
	
	public static final String LO_WHOLE_NAME = "loWholeName";
	public static final String TABLE_NAME = "Location";
	public static final String NAME = "name";
	public static final String LEVEL = "level";	//等级 1->n
	public static final String SORT_INDEX= "sortIndex";	//排序
	public static final String DESCRIBION = "description";
	public static final String PARENT_ID = "parent_id";
	public static final String CHILDEN = "childen";
	public static final String IS_VISIBLE = "isVisible";
	public static final String IS_HOT = "isHot";//是否热门区域
	public static final String DEFAULT_ADDRESS = "defaultAddress";//默认地址 其实就是一个中心点   
	public static final String LAT = "lat";
	public static final String LNG = "lng";

	@Column(name = DEFAULT_ADDRESS)
	@Expose
	public String defaultAddress;
	
	@Column(name = NAME)
	@Expose
	public String name;
	
	@Column(name = LEVEL)
	@Expose
	public String level;

	@Column(name = SORT_INDEX)
	@Expose
	@IntegerColumn
	public Integer sortIndex;
	
	@Column(name = DESCRIBION)
	@Expose
	public String description;
	
	@Column(name = IS_HOT)
	@Expose
	public Boolean isHot;
	
	@Column(name = IS_VISIBLE)
	@Expose
	public Boolean isVisible;
	
	@Column(name = PARENT_ID)
	@Expose
	public String parent_id;
	
	@Column(name = LNG)
	@Expose
	public Double lng;

	@Column(name = LAT)
	@Expose
	public Double lat;
	
	@Column(name = LO_WHOLE_NAME)
	@Expose
	public String loWholeName;
	
	
	//服务商服务区域是否选中
	@Expose
	public String checked;
	
	
	

	
	public String getChecked() {
    	return checked;
    }

	public void setChecked(String checked) {
    	this.checked = checked;
    }

	public Set<Location> childen = new LinkedHashSet<Location>();
	
	public Set<Location> getChilden() {
    	return childen;
    }

	public void setChilden(Set<Location> childen) {
    	this.childen = childen;
    }

	public String getName() {
    	return name;
    }

	public void setName(String name) {
    	this.name = name;
    }

	public String getLevel() {
    	return level;
    }

	public void setLevel(String level) {
    	this.level = level;
    }

	public Integer getSortIndex() {
    	return sortIndex;
    }

	public void setSortIndex(Integer sort) {
    	this.sortIndex = sort;
    }

	public Boolean getIsVisible() {
    	return isVisible;
    }

	public void setIsVisible(Boolean isVisible) {
    	this.isVisible = isVisible;
    }

	public String getDescription() {
    	return description;
    }

	public void setDescription(String description) {
    	this.description = description;
    }

	public String getParent_id() {
    	return parent_id;
    }

	public void setParent_id(String parent_id) {
    	this.parent_id = parent_id;
    }

	public String getDefaultAddress() {
    	return defaultAddress;
    }

	public void setDefaultAddress(String defaultAddress) {
    	this.defaultAddress = defaultAddress;
    }

	public Boolean getIsHot() {
    	return isHot;
    }

	public void setIsHot(Boolean isHot) {
    	this.isHot = isHot;
    }

	public Double getLng() {
    	return lng;
    }

	public void setLng(Double lng) {
    	this.lng = lng;
    }

	public Double getLat() {
    	return lat;
    }

	public void setLat(Double lat) {
    	this.lat = lat;
    }

	public String getLoWholeName() {
		return loWholeName;
	}

	public void setLoWholeName(String wholeName) {
		this.loWholeName = wholeName;
	}
	
	

}
