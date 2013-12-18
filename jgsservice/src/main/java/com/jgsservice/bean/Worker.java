package com.jgsservice.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.jgs.bean.BaseEntity;

@Table(name = Worker.TABLE_NAME)
public class Worker extends BaseEntity {

	public static final String IS_ACTIVE = "isActive";

	public static final String ID_CARD = "idCard";

	public static final String OWNER_ID = "ownerId";

	public static final String MOBILE_PHONE = "mobilePhone";

	public static final String ADDRESS = "address";

	public static final String WORKER_NAME = "workerName";

	public static final String TABLE_NAME = "Worker";

	@Column(name = WORKER_NAME, unique = true)
	@Expose
	public String workerName;

	@Column(name = ADDRESS)
	@Expose
	public String address;

	@Column(name = MOBILE_PHONE)
	@Expose
	public String mobilePhone;

	@Column(name = OWNER_ID)
	@Expose
	public String ownerId;
	
	@Column(name = ID_CARD)
	@Expose
	public String idCard;
	
	@Column(name = "workerType")
	@Expose
	public String workerType;
	
	@Column(name = IS_ACTIVE)
	@Expose
	public Boolean isActive;
	
	public Boolean isActive() {
		return isActive;
	}

	public void setActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getWorkerType() {
		return workerType;
	}

	public void setWorkerType(String workerType) {
		this.workerType = workerType;
	}
	
	

}
