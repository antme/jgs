package com.zcyservice.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = SmsMessage.TABLE_NAME)
public class SmsMessage extends BaseEntity {
	public static final String SMS_TYPE = "smsType";

	public static final String REPLY_CONTENT = "replyContent";

	public static final String TASK_ID = "taskID";

	public static final String SMS_REF_VALUE = "smsRefValue";

	public static final String SMS_REF_KEY = "smsRefKey";

	public static final String SEND_DATE = "sendDate";

	public static final String IS_SEND = "isSend";

	public static final String TABLE_NAME = "SmsMessage";

	@Column(name = "mobileNumber")
	@Expose
	public String mobileNumber;

	@Column(name = "content")
	@Expose
	public String content;

	@Column(name = "response")
	@Expose
	public String response;

	@Column(name = IS_SEND)
	@Expose
	public Boolean isSend;

	@Column(name = SEND_DATE)
	@Expose
	public Date sendDate;

	@Column(name = TASK_ID)
	@Expose
	public String taskID;
	
	@Column(name = REPLY_CONTENT)
	@Expose
	public String replyContent;

	@Column(name = SMS_REF_KEY)
	@Expose
	public String smsRefKey;

	@Column(name = SMS_REF_VALUE)
	@Expose
	public String smsRefValue;
	
	@Column(name = SMS_TYPE)
	@Expose
	public String smsType;

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String to) {
		this.mobileNumber = to;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Boolean getIsSend() {
		return isSend;
	}

	public void setIsSend(Boolean isSend) {
		this.isSend = isSend;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getSmsRefKey() {
		return smsRefKey;
	}

	public void setSmsRefKey(String smsRefKey) {
		this.smsRefKey = smsRefKey;
	}

	public String getSmsRefValue() {
		return smsRefValue;
	}

	public void setSmsRefValue(String smsRefValue) {
		this.smsRefValue = smsRefValue;
	}

	public String getTaskID() {
		return taskID;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}
	
	

}
