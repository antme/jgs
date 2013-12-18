package com.zcyservice.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = SpCategoryLocation.TABLE_NAME)
public class SpCategoryLocation extends BaseEntity {

	public static final String SECOND_CATEGORY_ID = "secondCategoryId";
	public static final String PRIMARY_CATEGORY_ID = "primaryCategoryId";
	public static final String PROVINCE_ID = "provinceId";
	public static final String CITY_ID = "cityId";
	public static final String TABLE_NAME = "SpCategoryLocation";
	public static final String OWNER_ID = "owner_Id";
	public static final String LOCATION_ID = "location_id";
	public static final String CATEGORY_ID = "category_id";
	public static final String DESCRIBION = "description";

	public static final String LAT = "lat";
	public static final String LNG = "lng";

	@Column(name = OWNER_ID)
	@Expose
	public String owner_Id;

	@Column(name = DESCRIBION)
	@Expose
	public String description;

	@Column(name = LOCATION_ID)
	@Expose
	public String location_id;
	
	@Column(name = CITY_ID)
	@Expose
	public String cityId;
	
	@Column(name = PROVINCE_ID)
	@Expose
	public String provinceId;

	@Column(name = CATEGORY_ID)
	@Expose
	public String category_id;
	

	@Column(name = SECOND_CATEGORY_ID)
	@Expose
	public String secondCategoryId;
	
	@Column(name = PRIMARY_CATEGORY_ID)
	@Expose
	public String primaryCategoryId;
	
	

	@Column(name = LNG)
	@Expose
	public Double lng;

	@Column(name = LAT)
	@Expose
	public Double lat;
	
	
	//临时距离，用来计算订单送货地址和服务商之间的距离
	public Double distance;
	
	
	//VO 字段，前台显示
	@Expose
	public String categoryName;
	
	@Expose
	public String locationName;
	
	@Expose
	public String spUserName;
	
	@Expose
	public Integer price;
	
	@Expose
	public String loWholeName;
	
	@Expose
	public String caWholeName;

	public String getOwner_Id() {
		return owner_Id;
	}

	public void setOwner_Id(String owner_Id) {
		this.owner_Id = owner_Id;
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

	public Double getDistance() {
    	return distance;
    }

	public void setDistance(Double distance) {
    	this.distance = distance;
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

	public String getSpUserName() {
    	return spUserName;
    }

	public void setSpUserName(String spUserName) {
    	this.spUserName = spUserName;
    }

	public String getCityId() {
    	return cityId;
    }

	public void setCityId(String cityId) {
    	this.cityId = cityId;
    }

	public String getProvinceId() {
    	return provinceId;
    }

	public void setProvinceId(String provinceId) {
    	this.provinceId = provinceId;
    }

	public String getSecondCategoryId() {
    	return secondCategoryId;
    }

	public void setSecondCategoryId(String secondCategoryId) {
    	this.secondCategoryId = secondCategoryId;
    }

	public String getPrimaryCategoryId() {
    	return primaryCategoryId;
    }

	public void setPrimaryCategoryId(String primaryCategoryId) {
    	this.primaryCategoryId = primaryCategoryId;
    }

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getLoWholeName() {
		return loWholeName;
	}

	public void setLoWholeName(String loWholeName) {
		this.loWholeName = loWholeName;
	}

	public String getCaWholeName() {
		return caWholeName;
	}

	public void setCaWholeName(String caWholeName) {
		this.caWholeName = caWholeName;
	}
	
	
	

}
