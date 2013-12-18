package com.zcy.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = User.TABLE_NAME)
public class User extends BaseEntity {

	public static final String GROUP_ID = "groupId";
	public static final String TABLE_NAME = "User";
	public static final String ROLE_NAME = "roleName";
	public static final String PASSWORD = "password";
	public static final String ADDRESSES = "addresses";
	public static final String DEFAULT_ADDRESS = "defaultAddress";
	public static final String PHONE = "phone";
	public static final String MOBILE_NUMBER = "mobileNumber";
	public static final String USER_NAME = "userName";
	public static final String STATUS = "status";
	public static final String INDEX_PAGE = "indexPage";
	
	public static final String USER_LOCATION_PID = "userLocationProvinceId";
	public static final String USER_LOCATION_CID = "userLocationCityId";
	public static final String USER_LOCATION_AID = "userLocationAreaId";
	public static final String USER_LOCATION = "userLocation";
	public static final String USER_EXT_PHONE = "userExtPhone";
	public static final String USER_CODE = "userCode";
	public static final String USER_QQ = "userQQ";


	@Column(name = USER_NAME, unique = true)
	@Expose
	public String userName;

	@Column(name = MOBILE_NUMBER)
	@Expose
	public String mobileNumber;

	@Column(name = PHONE)
	@Expose
	public String phone;

	@Column(name = "name")
	@Expose
	public String name;

	@Column(name = DEFAULT_ADDRESS)
	@Expose
	public String defaultAddress;

	@Column(name = ADDRESSES)
	@Expose
	public String addresses;

	@Column(name = PASSWORD)
	@Expose
	public String password;

	@Column(name = "sex")
	@Expose
	public String sex;

	@Column(name = ROLE_NAME)
	@Expose
	public String roleName;

	@Column(name = STATUS)
	@Expose
	public String status;

	@Column(name = GROUP_ID)
	@Expose
	public String groupId;

	@Column(name = USER_LOCATION_PID)
	@Expose
	public String userLocationProvinceId;

	@Column(name = USER_LOCATION_CID)
	@Expose
	public String userLocationCityId;

	@Column(name = USER_LOCATION_AID)
	@Expose
	public String userLocationAreaId;

	@Column(name = USER_LOCATION)
	@Expose
	public String userLocation;

	@Column(name = USER_EXT_PHONE)
	@Expose
	public String userExtPhone;

	@Column(name = USER_CODE)
	@Expose
	public String userCode;

	@Column(name = USER_QQ)
	@Expose
	public String userQQ;

	// 图片验证码, 不存数据库
	@Expose
	public String imgCode;

	// 短信验证码, 不存数据库
	@Expose
	public String regCode;

	// 忘记密码短信验证码, 不存数据库
	@Expose
	public String pwdCode;

	// 重置密码, 不存数据库
	@Expose
	public String newPwd;
	
	//首页信息，不存数据库
	@Expose
	public String indexPage;
	
	//权限组的字段，页面显示用 
	@Expose
	public String groupName;
	

	public String getPwdCode() {
		return pwdCode;
	}

	public void setPwdCode(String pwdCode) {
		this.pwdCode = pwdCode;
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(String defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	public String getAddresses() {
		return addresses;
	}

	public void setAddresses(String addresses) {
		this.addresses = addresses;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getIndexPage() {
    	return indexPage;
    }

	public void setIndexPage(String indexPage) {
    	this.indexPage = indexPage;
    }

	public String getUserLocationProvinceId() {
		return userLocationProvinceId;
	}

	public void setUserLocationProvinceId(String userLocationProvinceId) {
		this.userLocationProvinceId = userLocationProvinceId;
	}

	public String getUserLocationCityId() {
		return userLocationCityId;
	}

	public void setUserLocationCityId(String userLocationCityId) {
		this.userLocationCityId = userLocationCityId;
	}

	public String getUserLocationAreaId() {
		return userLocationAreaId;
	}

	public void setUserLocationAreaId(String userLocationAreaId) {
		this.userLocationAreaId = userLocationAreaId;
	}

	public String getUserExtPhone() {
		return userExtPhone;
	}

	public void setUserExtPhone(String userExtPhone) {
		this.userExtPhone = userExtPhone;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserQQ() {
		return userQQ;
	}

	public void setUserQQ(String userQQ) {
		this.userQQ = userQQ;
	}

	public String getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
	
	

}
