package com.zcyservice.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;
import com.zcyservice.util.UserStatus;

@Table(name = Manufacturer.TABLE_NAME)
public class Manufacturer extends BaseEntity {

	public static final String MFC_LOCATION_PROVINCE_ID = "mfcLocationProvinceId";
	public static final String TABLE_NAME = "Manufacturer";
	public static final String MFC_CODE = "mfcCode";
	public static final String MFC_COMPANY_NAME = "mfcCompanyName";
	public static final String MFC_LOCATION = "mfcLocation";
	public static final String MFC_COMPANY_ADRESS = "mfcCompanyAdress";
	public static final String MFC_CONTACT_PERSON = "mfcContactPerson";
	public static final String MFC_CONTACT_PHONE = "mfcContactPhone";
	public static final String MFC_QQ = "mfcQQ";
	public static final String MFC_WANG_WANG = "mfcWangWang";
	public static final String MFC_STATUS = "mfcStatus";
	public static final String MFC_CONTACT_MOBILE_PHONE = "mfcContactMobilePhone";
	public static final String USER_ID = "userId";
	public static final String MFC_STORE_NAME = "mfcStoreName";

	@Column(name = MFC_CODE)
	@Expose
	public String mfcCode;

	@Column(name = MFC_STORE_NAME, unique = true)
	@Expose
	public String mfcStoreName;

	@Column(name = MFC_COMPANY_NAME)
	@Expose
	public String mfcCompanyName;

	@Column(name = MFC_LOCATION)
	@Expose
	public String mfcLocation;

	@Column(name = MFC_COMPANY_ADRESS)
	@Expose
	public String mfcCompanyAdress;

	@Column(name = MFC_CONTACT_PERSON)
	@Expose
	public String mfcContactPerson;

	@Column(name = MFC_CONTACT_PHONE)
	@Expose
	public String mfcContactPhone;

	@Column(name = MFC_CONTACT_MOBILE_PHONE)
	@Expose
	public String mfcContactMobilePhone;

	@Column(name = MFC_QQ)
	@Expose
	public String mfcQQ;

	@Column(name = MFC_WANG_WANG)
	@Expose
	public String mfcWangWang;

	@Column(name = MFC_STATUS)
	@Expose
	public UserStatus mfcStatus;

	@Column(name = USER_ID)
	@Expose
	public String userId;
	
	@Column(name = MFC_LOCATION_PROVINCE_ID)
	@Expose
	public String mfcLocationProvinceId;

	@Column(name = "mfcLocationCityId")
	@Expose
	public String mfcLocationCityId;

	@Column(name = "mfcLocationAreaId")
	@Expose
	public String mfcLocationAreaId;

	@Column(name = "mfcCompanyAdressProvinceId")
	@Expose
	public String mfcCompanyAdressProvinceId;

	@Column(name = "mfcCompanyAdressAreaId")
	@Expose
	public String mfcCompanyAdressAreaId;

	@Column(name = "mfcCompanyAdressCityId")
	@Expose
	public String mfcCompanyAdressCityId;
	
	//根据外键userId取User中的status字段
	//用户管理模块加此字段供列表显示
	@Expose
	public String userStatus;
	
	
	//审核拒绝时候的字段，会发送短信给注册者
	@Expose
	public String rejectReson;
	
	
	//审核拒绝时候的字段，会发送短信给注册者
	@Column(name = "password")
	@Expose
	public String password;
	
	//审核拒绝时候的字段，会发送短信给注册者
	@Column(name = "mfcServiceType")
	@Expose
	public String mfcServiceType;
	
	
	//审核拒绝时候的字段，会发送短信给注册者
	@Column(name = "mfcServiceTypeStr")
	@Expose
	public String mfcServiceTypeStr;
	
	
	@Expose
	public String[] mfcServiceTypeArray;

	
	
	public String[] getMfcServiceTypeArray() {
		return mfcServiceTypeArray;
	}

	public void setMfcServiceTypeArray(String[] mfcServiceTypeArray) {
		this.mfcServiceTypeArray = mfcServiceTypeArray;
	}

	public String getMfcLocationProvinceId() {
		return mfcLocationProvinceId;
	}

	public void setMfcLocationProvinceId(String mfcLocationProvinceId) {
		this.mfcLocationProvinceId = mfcLocationProvinceId;
	}

	public String getMfcLocationCityId() {
		return mfcLocationCityId;
	}

	public void setMfcLocationCityId(String mfcLocationCityId) {
		this.mfcLocationCityId = mfcLocationCityId;
	}

	public String getMfcLocationAreaId() {
		return mfcLocationAreaId;
	}

	public void setMfcLocationAreaId(String mfcLocationAreaId) {
		this.mfcLocationAreaId = mfcLocationAreaId;
	}

	public String getMfcCompanyAdressProvinceId() {
		return mfcCompanyAdressProvinceId;
	}

	public void setMfcCompanyAdressProvinceId(String mfcCompanyAdressProvinceId) {
		this.mfcCompanyAdressProvinceId = mfcCompanyAdressProvinceId;
	}

	public String getMfcCompanyAdressAreaId() {
		return mfcCompanyAdressAreaId;
	}

	public void setMfcCompanyAdressAreaId(String mfcCompanyAdressAreaId) {
		this.mfcCompanyAdressAreaId = mfcCompanyAdressAreaId;
	}

	public String getMfcCompanyAdressCityId() {
		return mfcCompanyAdressCityId;
	}

	public void setMfcCompanyAdressCityId(String mfcCompanyAdressCityId) {
		this.mfcCompanyAdressCityId = mfcCompanyAdressCityId;
	}

	public Manufacturer() {
	}

	public String getMfcCode() {
		return mfcCode;
	}

	public void setMfcCode(String mfcCode) {
		this.mfcCode = mfcCode;
	}

	public String getMfcStoreName() {
		return mfcStoreName;
	}

	public void setMfcStoreName(String mfcStoreName) {
		this.mfcStoreName = mfcStoreName;
	}

	public String getMfcCompanyName() {
		return mfcCompanyName;
	}

	public void setMfcCompanyName(String mfcCompanyName) {
		this.mfcCompanyName = mfcCompanyName;
	}

	public String getMfcLocation() {
		return mfcLocation;
	}

	public void setMfcLocation(String mfcLocation) {
		this.mfcLocation = mfcLocation;
	}

	public String getMfcCompanyAdress() {
		return mfcCompanyAdress;
	}

	public void setMfcCompanyAdress(String mfcCompanyAdress) {
		this.mfcCompanyAdress = mfcCompanyAdress;
	}

	public String getMfcContactPerson() {
		return mfcContactPerson;
	}

	public void setMfcContactPerson(String mfcContactPerson) {
		this.mfcContactPerson = mfcContactPerson;
	}

	public String getMfcContactPhone() {
		return mfcContactPhone;
	}

	public void setMfcContactPhone(String mfcContactPhone) {
		this.mfcContactPhone = mfcContactPhone;
	}

	public String getMfcContactMobilePhone() {
		return mfcContactMobilePhone;
	}

	public void setMfcContactMobilePhone(String mfcContactMobilePhone) {
		this.mfcContactMobilePhone = mfcContactMobilePhone;
	}

	public String getMfcQQ() {
		return mfcQQ;
	}

	public void setMfcQQ(String mfcQQ) {
		this.mfcQQ = mfcQQ;
	}

	public String getMfcWangWang() {
		return mfcWangWang;
	}

	public void setMfcWangWang(String mfcWangWang) {
		this.mfcWangWang = mfcWangWang;
	}

	public UserStatus getMfcStatus() {
		return mfcStatus;
	}

	public void setMfcStatus(UserStatus mfcStatus) {
		this.mfcStatus = mfcStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getRejectReson() {
		return rejectReson;
	}

	public void setRejectReson(String rejectReson) {
		this.rejectReson = rejectReson;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMfcServiceType() {
		return mfcServiceType;
	}

	public void setMfcServiceType(String mfcServiceType) {
		this.mfcServiceType = mfcServiceType;
	}

	public String getMfcServiceTypeStr() {
		return mfcServiceTypeStr;
	}

	public void setMfcServiceTypeStr(String mfcServiceTypeStr) {
		this.mfcServiceTypeStr = mfcServiceTypeStr;
	}
	
	
	
	

}
