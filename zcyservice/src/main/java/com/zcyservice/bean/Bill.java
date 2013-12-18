package com.zcyservice.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = Bill.TABLE_NAME)
public class Bill extends BaseEntity {

	public static final String TOTAL = "total";


	public static final String MFC_USER_ID = "mfcUserId";


	public static final String SP_USER_ID = "spUserId";


	public static final String MFC_ID = "mfcId";


	public static final String BILL_DATE_NUMBER = "billDateNumber";


	public static final String SP_ID = "spId";


	public static final String BILL_DATE = "billDate";


	public static final String TABLE_NAME = "Bill";

	
	@Column(name = BILL_DATE)
	@Expose
	public Date billDate;
	
	@Column(name = BILL_DATE_NUMBER)
	@Expose
	public Long billDateNumber;

	@Column(name = SP_ID)
	@Expose
	public String spId;

	@Column(name = MFC_ID)
	@Expose
	public String mfcId;

	@Column(name = SP_USER_ID)
	@Expose
	public String spUserId;

	@Column(name = MFC_USER_ID)
	@Expose
	public String mfcUserId;

	@Column(name = "orderUserId")
	@Expose
	public String orderUserId;

	@Column(name = TOTAL)
	@Expose
	public Integer total;

	@Column(name = "startDaate")
	@Expose
	public Date startDaate;

	@Column(name = "endDate")
	@Expose
	public Date endDate;
	
	
	// VO字段，现实服务商名字
	@Expose
	public String mfcContactPerson;
	
	@Expose
	public String  mfcContactMobilePhone;
	
	@Expose
	public String  mfcStoreName;
	
	
	// VO字段，现实服务商名字
	@Expose
	public String spUserName;
	
	@Expose
	public String  spContactMobilePhone;
	
	@Expose
	public String  spContactPerson;
	
	
	

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getMfcId() {
		return mfcId;
	}

	public void setMfcId(String mfcId) {
		this.mfcId = mfcId;
	}

	public String getSpUserId() {
		return spUserId;
	}

	public void setSpUserId(String spUserId) {
		this.spUserId = spUserId;
	}

	public String getMfcUserId() {
		return mfcUserId;
	}

	public void setMfcUserId(String mfcUserId) {
		this.mfcUserId = mfcUserId;
	}

	public String getOrderUserId() {
		return orderUserId;
	}

	public void setOrderUserId(String orderUserId) {
		this.orderUserId = orderUserId;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Date getStartDaate() {
		return startDaate;
	}

	public void setStartDaate(Date startDaate) {
		this.startDaate = startDaate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getBillDateNumber() {
		return billDateNumber;
	}

	public void setBillDateNumber(Long billDateNumber) {
		this.billDateNumber = billDateNumber;
	}

	public String getSpUserName() {
		return spUserName;
	}

	public void setSpUserName(String spUserName) {
		this.spUserName = spUserName;
	}

	public String getSpContactMobilePhone() {
		return spContactMobilePhone;
	}

	public void setSpContactMobilePhone(String spContactMobilePhone) {
		this.spContactMobilePhone = spContactMobilePhone;
	}

	public String getSpContactPerson() {
		return spContactPerson;
	}

	public void setSpContactPerson(String spContactPerson) {
		this.spContactPerson = spContactPerson;
	}

	public String getMfcContactPerson() {
		return mfcContactPerson;
	}

	public void setMfcContactPerson(String mfcContactPerson) {
		this.mfcContactPerson = mfcContactPerson;
	}

	public String getMfcContactMobilePhone() {
		return mfcContactMobilePhone;
	}

	public void setMfcContactMobilePhone(String mfcContactMobilePhone) {
		this.mfcContactMobilePhone = mfcContactMobilePhone;
	}

	public String getMfcStoreName() {
		return mfcStoreName;
	}

	public void setMfcStoreName(String mfcStoreName) {
		this.mfcStoreName = mfcStoreName;
	}
	
	
	
	

}
