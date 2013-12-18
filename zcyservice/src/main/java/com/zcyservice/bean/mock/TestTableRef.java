package com.zcyservice.bean.mock;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = TestTableRef.TEST_TABLE_REF)
public class TestTableRef extends BaseEntity {
	public static final String TEST_TABLE_ID = "testTableId";
	public static final String TEST_TABLE_REF = "TestTableRef";
	public static final String PASSWORD = "refPassword";
	public static final String USER_NAME = "refName";

	@Column(name = USER_NAME)
	@Expose
	public String refName;

	@Column(name = PASSWORD)
	@Expose
	public String refPassword;

	@Column(name = TEST_TABLE_ID)
	@Expose
	public String testTableId;

	public String getTestTableId() {
		return testTableId;
	}

	public void setTestTableId(String testTableId) {
		this.testTableId = testTableId;
	}

	public String getRefName() {
		return refName;
	}

	public void setRefName(String userName) {
		this.refName = userName;
	}

	public String getRefPassword() {
		return refPassword;
	}

	public void setRefPassword(String password) {
		this.refPassword = password;
	}

}
