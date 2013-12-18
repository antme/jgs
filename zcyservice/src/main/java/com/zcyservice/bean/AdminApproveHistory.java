package com.zcyservice.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = AdminApproveHistory.TABLE_NAME)
public class AdminApproveHistory extends BaseEntity {

	public static final String LOG_DATA = "logData";

	public static final String TABLE_NAME = "AdminApproveHistory";

	public static final String ROLE_NAME = "roleName";
	public static final String USER_ID = "userId";
	public static final String USER_NAME = "userName";
	public static final String APPLY_TIME = "applyTime";
	public static final String APPROVE_TIME = "approveTime";
	public static final String APPROVE_RESULT = "approveResult";
	public static final String APPLY_TYPE = "applyType";

	@Column(name = ROLE_NAME)
	@Expose
	public String roleName;
	
	@Column(name = USER_NAME)
	@Expose
	public String userName;
	
	@Column(name = USER_ID)
	@Expose
	public String userId;
	
	@Column(name = "APPLY_TIME")
	@Expose
	public Date applyTime;
	
	@Column(name = "APPROVE_TIME")
	@Expose
	public Date approveTime;
	
	@Column(name = APPROVE_RESULT)
	@Expose
	public String approveResult;
	
	@Column(name = APPLY_TYPE)
	@Expose
	public String applyType;
	
	
	@Column(name = LOG_DATA)
	@Expose
	public String logData;


	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLogData() {
    	return logData;
    }

	public void setLogData(String logData) {
    	this.logData = logData;
    }
	
	
	

}
