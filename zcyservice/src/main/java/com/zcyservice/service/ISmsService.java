package com.zcyservice.service;

import java.util.List;

import com.zcyservice.bean.Manufacturer;
import com.zcyservice.bean.ServiceOrder;
import com.zcyservice.bean.ServiceProvider;
import com.zcyservice.bean.SmsMessage;
import com.zcyservice.bean.SmsTemplate;
import com.zcyservice.bean.vo.ReturnSms;
import com.zcyservice.bean.vo.SearchVo;

public interface ISmsService {
	public static final String NEW_USER = "newUser";
	public static final String REG_CODE = "regCode";
	public static final String ORDER_INSTALL_NOTICE_SP = "spOrderInstallNotice";
	public static final String ORDER_INSTALL_NOTICE_CUSTOMER = "customerOrderInstallNotice";
	public static final String MFC_REG = "mfcReg";
	public static final String SP_REG = "spReg";
	public static final String MFC_REG_REJECT = "mfcReject";
	public static final String SP_REG_REJECT = "spReject";
	public static final String FORGOT_PWD = "fgetPwd";
	public static final String ORDER_ACCEPTED_CUSTOMER = "customerOrderAccept";
	public static final String ORDER_ASSIGNED_CUSTOMER = "customerOrderAssign";
	public static final String ORDER_DONE_CUSTOMER = "customerOrderDone";
	public static final String ORDER_INSTALL_BEFORE_SP = "orderBefore24Hours";
	public static final String ORDER_ASSIGNED_WORKER = "workerOrderAssign";
	public static final String ORDER_INSTALL_AFTERE_SP = "orderAfter24Hours";
	public static final String ORDER_CONFIRMED_BY_USER = "orderConfirmedByUser";
	


	public void sendRegCode(String mobileNo, String regCode);

	public void sendFgtPwdCode(String mobileNumber, String code);

	public void sendNewUserNotice(String mobileNo, String password);

	public void sendNewMfcNotice(String mobileNo, Manufacturer mfc, String password);
	
	public void sendNewMfcRejectNotice(Manufacturer mfc);

	public void sendNewSpNotice(String mobileNumber, ServiceProvider sp, String password);
	
	public void sendNewSpRejectNotice(ServiceProvider sp);
	
	public void sendUserConfirmOrderNotice(ServiceOrder order);

	public void sendOrderAcceptedNotice(String orderId);

	public void sendOrderAssignNotice(ServiceOrder order);

	public void sendOrderDoneNotice(String mobileNo, ServiceOrder order);
	

	public void addOrderInstallNotice(ServiceOrder order);

	public void addOrderPassedNotice(ServiceOrder order);
	
	
	public void addSmsTemplate(SmsTemplate template);

	public SmsTemplate loadSmsTemplate(String templateId);

	public List<SmsTemplate> listSmsTemplates();
	
	public void listSmsReply();
	
	public ReturnSms getSmsRemainingMongey(SearchVo vo);
	
	public void sendSms(SmsMessage sms);
	
	public void checkSmsContent(String content);


}
