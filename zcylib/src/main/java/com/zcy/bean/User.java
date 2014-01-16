package com.zcy.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = User.TABLE_NAME)
public class User extends BaseEntity {

	public static final String EMAIL = "email";
	public static final String GROUP_ID = "groupId";
	public static final String TABLE_NAME = "User";
	public static final String ROLE_NAME = "roleName";
	public static final String PASSWORD = "password";
	public static final String ADDRESSES = "addresses";
	public static final String MOBILE_NUMBER = "mobileNumber";
	public static final String USER_NAME = "userName";
	public static final String STATUS = "userStatus";
	


	@Column(name = USER_NAME, unique = true)
	@Expose
	public String userName;

	@Column(name = MOBILE_NUMBER)
	@Expose
	public String mobileNumber;

	

	@Column(name = "name")
	@Expose
	public String name;

	@Column(name = ADDRESSES)
	@Expose
	public String addresses;

	@Column(name = PASSWORD)
	@Expose
	public String password;

	
	@Column(name = EMAIL)
	@Expose
	public String email;
	
	

	@Column(name = ROLE_NAME)
	@Expose
	public String roleName;

	@Column(name = STATUS)
	@Expose
	public String userStatus;

	@Column(name = GROUP_ID)
	@Expose
	public String groupId;



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



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String status) {
		this.userStatus = status;
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


	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	

}
