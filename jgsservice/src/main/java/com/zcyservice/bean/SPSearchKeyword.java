package com.jgsservice.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.jgs.bean.BaseEntity;

@Table(name = SPSearchKeyword.TABLE_NAME)
public class SPSearchKeyword extends BaseEntity {

	public static final String SEARCH_TIMES = "searchTimes";

	public static final String LOCATION_NAME = "locationName";

	public static final String CATEGORY_NAME = "categoryName";

	public static final String TABLE_NAME = "SPSearchKeyword";

	@Column(name = CATEGORY_NAME)
	@Expose
	public String categoryName;

	@Column(name = LOCATION_NAME)
	@Expose
	public String locationName;

	@Column(name = SEARCH_TIMES)
	@Expose
	public Integer searchTimes;

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

	public Integer getSearchTimes() {
		return searchTimes;
	}

	public void setSearchTimes(Integer searchTimes) {
		this.searchTimes = searchTimes;
	}

}
