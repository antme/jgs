package com.zcyservice.bean.mock;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = TestTable.TEST_TABLE)
public class TestTable extends BaseEntity {
	public static final String TEST_TABLE = "TestTable";
	public static final String SIZE = "size";
	public static final String PASSWORD = "password";
	public static final String USER_NAME = "userName";

	@Column(name = USER_NAME)
	@Expose
	public String userName;

	@Column(name = PASSWORD)
	@Expose
	public String password;
	
	@Column(name = SIZE)
	@Expose
	public Integer size;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer index) {
		this.size = index;
	}
	
	

}
