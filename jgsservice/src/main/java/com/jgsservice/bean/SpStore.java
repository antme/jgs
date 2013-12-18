package com.jgsservice.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.jgs.bean.BaseEntity;

@Table(name = SpStore.TABLE_NAME)
public class SpStore extends BaseEntity{
	
	public static final String LOCATION_ID = "locationId";
	public static final String TABLE_NAME = "SpStore";
	public static final String ADDRESS = "address";
	public static final String DESCRIBION = "description";
	public static final String OWNER_ID = "ownerId";
	public static final String LAT = "lat";
	public static final String LNG = "lng";
	
	@Column(name = ADDRESS)
	@Expose
	public String address;
	
	@Column(name = DESCRIBION)
	@Expose
	public String description;
	
	@Column(name = OWNER_ID)
	@Expose
	public String ownerId;
	
	@Column(name = LOCATION_ID)
	@Expose
	public String locationId;
	
	
	@Column(name = LNG)
	@Expose
	public Double lng;

	@Column(name = LAT)
	@Expose
	public Double lat;
	
	

	public String getAddress() {
    	return address;
    }

	public void setAddress(String address) {
    	this.address = address;
    }

	public String getDescription() {
    	return description;
    }

	public void setDescription(String description) {
    	this.description = description;
    }

	public String getOwnerId() {
    	return ownerId;
    }

	public void setOwnerId(String ownerId) {
    	this.ownerId = ownerId;
    }

	public String getLocationId() {
    	return locationId;
    }

	public void setLocationId(String locationId) {
    	this.locationId = locationId;
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
	
	
	
}
