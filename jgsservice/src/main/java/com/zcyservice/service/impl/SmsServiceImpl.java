package com.jgsservice.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgs.bean.BaseEntity;
import com.jgs.bean.SystemConfig;
import com.jgs.bean.User;
import com.jgs.cfg.CFGManager;
import com.jgs.dbhelper.DataBaseQueryBuilder;
import com.jgs.dbhelper.DataBaseQueryOpertion;
import com.jgs.exception.ResponseException;
import com.jgs.service.AbstractService;
import com.jgs.util.DateUtil;
import com.jgs.util.EcUtil;
import com.jgs.util.HttpClientUtil;
import com.jgsservice.bean.Manufacturer;
import com.jgsservice.bean.ServiceOrder;
import com.jgsservice.bean.ServiceProvider;
import com.jgsservice.bean.ServiceScore;
import com.jgsservice.bean.SmsMessage;
import com.jgsservice.bean.SmsTemplate;
import com.jgsservice.bean.vo.Callbox;
import com.jgsservice.bean.vo.ReturnSms;
import com.jgsservice.bean.vo.SearchVo;
import com.jgsservice.service.IOrderService;
import com.jgsservice.service.ISiteMessageService;
import com.jgsservice.service.ISmsService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Service("smsSerevice")
public class SmsServiceImpl extends AbstractService implements ISmsService {
	private static Logger logger = LogManager.getLogger(SmsServiceImpl.class);
	public static final String SMS_CFG_DISABLED = "smsDisabled";
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private ISiteMessageService sitemService;

	public void sendRegCode(String mobileNo, String regCode) {
		String msg = loadSmsTemplateContent(REG_CODE);
		msg = msg.replaceAll("\\{验证码\\}", regCode);
		sendSMS(mobileNo, msg, REG_CODE);
	}

	public void sendFgtPwdCode(String mobileNo, String code) {
		String msg = loadSmsTemplateContent(FORGOT_PWD);
		msg = msg.replaceAll("\\{验证码\\}", code);
		sendSMS(mobileNo, msg, FORGOT_PWD);
	}

	public void sendNewUserNotice(String mobileNo, String password) {
		String msg = loadSmsTemplateContent(NEW_USER);
		msg = msg.replaceAll("\\{密码\\}", password);
		sendSMS(mobileNo, msg, NEW_USER);
	}
	
	public void sendUserConfirmOrderNotice(ServiceOrder order) {
		ServiceOrder soOrder = (ServiceOrder) this.dao.findById(order.getId(), ServiceOrder.TABLE_NAME, ServiceOrder.class);
		ServiceProvider sp = (ServiceProvider) this.dao.findById(soOrder.getSpId(), ServiceProvider.TABLE_NAME, ServiceProvider.class);

		String msg = loadSmsTemplateContent(ORDER_CONFIRMED_BY_USER);
		msg = msg.replaceAll("\\{订单号\\}", soOrder.getSoCode());
		if (sp != null) {
			sendSMS(sp.getSpContactMobilePhone(), msg, ORDER_CONFIRMED_BY_USER);
		}
	}

	public void sendNewMfcNotice(String mobileNo, Manufacturer mfc, String password) {
		String msg = loadSmsTemplateContent(MFC_REG);
		msg = msg.replaceAll("\\{密码\\}", password);
		msg = msg.replaceAll("\\{厂商\\}", mfc.getMfcStoreName());
		msg = msg.replaceAll("\\{厂商编号\\}", mfc.getMfcCode());
		sendSMS(mobileNo, msg, MFC_REG);
	}

	public void sendNewSpNotice(String mobileNo, ServiceProvider sp, String password) {
		String msg = loadSmsTemplateContent(SP_REG);
		msg = msg.replaceAll("\\{密码\\}", password);
		msg = msg.replaceAll("\\{服务商\\}", sp.getSpUserName());
		msg = msg.replaceAll("\\{服务商编号\\}", sp.getSpCode());
		sendSMS(mobileNo, msg, SP_REG);
	}
	
	public void sendNewMfcRejectNotice(Manufacturer mfc){
		String msg = loadSmsTemplateContent(MFC_REG_REJECT);
		msg = msg.replaceAll("\\{厂商\\}", mfc.getMfcStoreName());
		msg = msg.replaceAll("\\{拒绝原因\\}", mfc.getRejectReson());
		sendSMS(mfc.getMfcContactMobilePhone(), msg, MFC_REG_REJECT);
	}
	
	public void sendNewSpRejectNotice(ServiceProvider sp){
		String msg = loadSmsTemplateContent(SP_REG_REJECT);
		msg = msg.replaceAll("\\{服务商\\}", sp.getSpUserName());
		msg = msg.replaceAll("\\{拒绝原因\\}", sp.getRejectReson());
		sendSMS(sp.getSpContactMobilePhone(), msg, SP_REG_REJECT);
	}

	public void sendOrderAcceptedNotice(String orderId) {
		ServiceOrder order = (ServiceOrder) this.dao.findById(orderId, ServiceOrder.TABLE_NAME, ServiceOrder.class);
		
		String msg = loadSmsTemplateContent(ORDER_ACCEPTED_CUSTOMER);
		msg = msg.replaceAll("\\{订单号\\}", order.getSoCode());
		ServiceProvider sp = (ServiceProvider) this.dao.findById(order.getSpId(), ServiceProvider.TABLE_NAME, ServiceProvider.class);
		msg = msg.replaceAll("\\{服务商\\}", sp.getSpUserName());
		sendSMS(order.getPoReceiverMobilePhone(), msg, ORDER_ACCEPTED_CUSTOMER);
	}

	public void sendOrderAssignNotice(ServiceOrder order) {

		ServiceOrder soOrder = (ServiceOrder) this.dao.findById(order.getId(), ServiceOrder.TABLE_NAME, ServiceOrder.class);
		
		String msg = loadSmsTemplateContent(ORDER_ASSIGNED_CUSTOMER);
		msg = msg.replaceAll("\\{时间\\}", DateUtil.getDateString(soOrder.getEstInstallDate()));
		ServiceProvider sp = (ServiceProvider) this.dao.findById(soOrder.getSpId(), ServiceProvider.TABLE_NAME, ServiceProvider.class);
		msg = msg.replaceAll("\\{手机号\\}", sp.getSpContactMobilePhone());
		sendSMS(soOrder.getPoReceiverMobilePhone(), msg, ORDER_ASSIGNED_CUSTOMER);
	}

	public void sendOrderDoneNotice(String mobileNo, ServiceOrder order) {
	
		String msg = loadSmsTemplateContent(ORDER_DONE_CUSTOMER);
		SmsMessage sms = new SmsMessage();

		sms.setSmsRefKey(ServiceOrder.ID);
		sms.setSmsRefValue(order.getId());
		sendSMS(mobileNo, msg, true, sms, ORDER_DONE_CUSTOMER);

	}
	


	public void addOrderInstallNotice(ServiceOrder order) {
		ServiceOrder sorder = (ServiceOrder) this.dao.findById(order.getId(), ServiceOrder.TABLE_NAME, ServiceOrder.class);

		String msg = loadSmsTemplateContent(ORDER_INSTALL_BEFORE_SP);
		ServiceProvider sp = (ServiceProvider) this.dao.findById(sorder.getSpId(), ServiceProvider.TABLE_NAME, ServiceProvider.class);
		if (sp != null) {
			msg = msg.replaceAll("\\{服务商\\}", sp.getSpUserName());
			msg = msg.replaceAll("\\{时间\\}", DateUtil.getDateString(sorder.getEstInstallDate()));

			msg = msg.replaceAll("\\{订单号\\}", sorder.getSoCode());

			SmsMessage sms = new SmsMessage();
			sms.setMobileNumber(sp.getSpContactMobilePhone());
			msg = msg.replaceAll("\\{", "");
			msg = msg.replaceAll("\\}", "");
			sms.setContent(msg);

			sms.setSmsRefKey(ServiceOrder.ID);
			sms.setSmsRefValue(sorder.getId());

			Calendar c = Calendar.getInstance();
			c.setTime(sorder.getEstInstallDate());

			// TODO： may be put into config
			c.add(Calendar.DAY_OF_MONTH, -1);

			sms.setSendDate(c.getTime());

			DataBaseQueryBuilder builder = new DataBaseQueryBuilder(SmsMessage.TABLE_NAME);
			builder.and(SmsMessage.SMS_REF_KEY, ServiceOrder.ID);
			builder.and(SmsMessage.SMS_REF_VALUE, sorder.getId());
			builder.and(SmsMessage.SMS_TYPE, ORDER_INSTALL_BEFORE_SP);

			SmsMessage old = (SmsMessage) this.dao.findOneByQuery(builder, SmsMessage.class);

			if (old != null) {
				sms.setId(old.getId());
				sms.setIsSend(old.getIsSend());
				this.dao.updateById(sms);
			} else {
				sms.setIsSend(false);
				this.dao.insert(sms);
			}
		}

	}


	public void addOrderPassedNotice(ServiceOrder order) {
		ServiceOrder sorder = (ServiceOrder) this.dao.findById(order.getId(), ServiceOrder.TABLE_NAME, ServiceOrder.class);

		String msg = loadSmsTemplateContent(ORDER_INSTALL_AFTERE_SP);
		ServiceProvider sp = (ServiceProvider) this.dao.findById(sorder.getSpId(), ServiceProvider.TABLE_NAME, ServiceProvider.class);
		msg = msg.replaceAll("\\{服务商\\}", sp.getSpUserName());
		msg = msg.replaceAll("\\{时间\\}", DateUtil.getDateString(sorder.getEstInstallDate()));

		msg = msg.replaceAll("\\{订单号\\}", sorder.getSoCode());

		SmsMessage sms = new SmsMessage();
		sms.setMobileNumber(sp.getSpContactMobilePhone());
		msg = msg.replaceAll("\\{", "");
		msg = msg.replaceAll("\\}", "");
		sms.setContent(msg);

		sms.setSmsRefKey(ServiceOrder.ID);
		sms.setSmsRefValue(sorder.getId());

		sms.setSmsType(ORDER_INSTALL_AFTERE_SP);
		sms.setSendDate(new Date());

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(SmsMessage.TABLE_NAME);
		builder.and(SmsMessage.SMS_REF_KEY, ServiceOrder.ID);
		builder.and(SmsMessage.SMS_REF_VALUE, sorder.getId());
		builder.and(SmsMessage.SMS_TYPE, ORDER_INSTALL_AFTERE_SP);

		SmsMessage old = (SmsMessage) this.dao.findOneByQuery(builder, SmsMessage.class);

		if (old != null) {
			sms.setId(old.getId());
			sms.setIsSend(old.getIsSend());
			this.dao.updateById(sms);
		} else {
			sms.setIsSend(false);
			this.dao.insert(sms);
		}

	}
	
	

	private void sendSMS(String mobileNo, String smsText, boolean log, SmsMessage sms,String type) {

		if (!EcUtil.isEmpty(mobileNo) && !EcUtil.isEmpty(smsText) && smsText.length() > 10) {

			sendSmsViaDXT(mobileNo, smsText, log, sms, type);
		} else {
			logger.error(String.format("请检查手机号码或者短信内容是否太少【%s】！手机号[%s]", smsText,  mobileNo));
			
			if(sms!=null){
				if (!EcUtil.isEmpty(sms.getId())) {				
					this.dao.updateById(sms);
				}
			}
		}
	}


	private void sendSmsViaDXT(String mobileNo, String smsText, boolean log, SmsMessage sms, String type) {
	    Map<String, Object> parameters = new HashMap<String, Object>();
	    parameters.put("action", "send");
	    setSmsAccount(parameters);
	    parameters.put("mobile", mobileNo);
	    parameters.put("content", smsText);
	    parameters.put("mobilenumber", 1);
	    parameters.put("countnumber", 1);
	    
	    if(sms == null){
	    	sms = new SmsMessage();
	    }
	    sms.setIsSend(true);
	    // 限制为空或者号码在限制的号码类都会发短信
	    logger.info(String.format("发送短信到[%s], 类容[%s]", mobileNo, smsText));
	    if (CFGManager.isProductEnviroment()) {
	    	if (CFGManager.getProperty(SMS_CFG_DISABLED) != null && CFGManager.getProperty(SMS_CFG_DISABLED).trim().equalsIgnoreCase("no")) {
	    		String mobilePhones = CFGManager.getProperty("smsMobilePhonesLimit");
	    		if (EcUtil.isEmpty(mobilePhones) || mobilePhones.indexOf(mobileNo) != -1) {

	    			try {
	    				String response = HttpClientUtil.doGet("http://114.113.227.101:8888/sms.aspx", parameters);
	    				sms.setResponse(response);

	    				System.out.println(response);
	    				XStream xs = new XStream(new DomDriver());
	    				xs.alias("returnsms", ReturnSms.class);
	    				xs.alias("callbox", Callbox.class);

	    				xs.addImplicitCollection(ReturnSms.class, "callboxList");
	    				ReturnSms resms = (ReturnSms) xs.fromXML(response);

	    				sms.setTaskID(resms.getTaskID());

	    			} catch (Exception e) {
	    				sms.setIsSend(false);
	    				logger.error("发送短信失败", e);
	    			}
	    		} else {
	    			logger.warn("手机号不在限制范围内，不发送短信, 请修改config.properties配置文件");
	    		}

	    	} else {
	    		logger.warn("系统禁用短信，不发送短信, 请到后台启用发送短信");
	    	}
	    } else {
	    	logger.warn("开发环境不发送短信");
	    }

	    if (log) {
	    	sms.setMobileNumber(mobileNo);
	    	smsText = smsText.replaceAll("\\{", "");
	    	smsText = smsText.replaceAll("\\}", "");
	    	sms.setContent(smsText);
	    	sms.setSmsType(type);
	    	
	    	if (EcUtil.isEmpty(sms.getId())) {
	    		this.dao.insert(sms);
	    	} else {
	    		this.dao.updateById(sms);
	    	}
	    }
    }

	private void setSmsAccount(Map<String, Object> parameters) {
		parameters.put("userid", CFGManager.getProperty(SystemConfig.SMS_ACCOUNT_ID));
		parameters.put("account", CFGManager.getProperty(SystemConfig.SMS_ACCOUNT_NAME));
		parameters.put("password", CFGManager.getProperty(SystemConfig.SMS_ACCOUNT_PASSWORD));
	}

	private void sendSMS(String mobileNo, String smsText, String type) {
		sendSMS(mobileNo, smsText, true, null, type);
	}

	public void sendSms(SmsMessage sms) {
		sendSMS(sms.getMobileNumber(), sms.getContent(), false, null, null);
		sms.setIsSend(true);
		this.dao.updateById(sms);
	}



	public void addSmsTemplate(SmsTemplate template) {

		checkSmsContent(template.getTemplate());

		BaseEntity entity = this.dao.findByKeyValue(SmsTemplate.TEMPLATE_ID, template.getTemplateId(), SmsTemplate.TABLE_NAME, SmsTemplate.class);
		if (entity != null) {
			SmsTemplate st = (SmsTemplate) this.dao.findByKeyValue(SmsTemplate.TEMPLATE_ID, template.getTemplateId(), SmsTemplate.TABLE_NAME, SmsTemplate.class);

			st.setTemplate(template.getTemplate());
			this.dao.updateById(st);
		} else {
			this.dao.insert(template);
		}
	}

	public SmsTemplate loadSmsTemplate(String templateId) {
		return (SmsTemplate) this.dao.findByKeyValue(SmsTemplate.TEMPLATE_ID, templateId, SmsTemplate.TABLE_NAME, SmsTemplate.class);

	}

	public String loadSmsTemplateContent(String templateId) {
		SmsTemplate st = (SmsTemplate) this.dao.findByKeyValue(SmsTemplate.TEMPLATE_ID, templateId, SmsTemplate.TABLE_NAME, SmsTemplate.class);

		if (st != null && !EcUtil.isEmpty(st.getTemplate())) {
			
			if(EcUtil.isEmpty(st.getTemplate())){
				return "";
			}
			return st.getTemplate();
		}
		return "";

	}

	public List<SmsTemplate> listSmsTemplates() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(SmsTemplate.TABLE_NAME);
		return this.dao.listByQuery(builder, SmsTemplate.class);
	}

	public void listSmsReply() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("action", "query");
		setSmsAccount(parameters);

		try {
			String response = HttpClientUtil.doGet("http://114.113.227.101:8888/callApi.aspx", parameters);
			// String response =
			// HttpClientUtil.doGet("http://localhost/pages/admin/user/msg.json",
			// parameters);

			XStream xs = new XStream(new DomDriver());
			xs.alias("returnsms", ReturnSms.class);
			xs.alias("callbox", Callbox.class);

			xs.addImplicitCollection(ReturnSms.class, "callboxList");
			ReturnSms sms = (ReturnSms) xs.fromXML(response);

			if (sms.getCallboxList() != null) {

				for (Callbox box : sms.getCallboxList()) {

					DataBaseQueryBuilder builder = new DataBaseQueryBuilder(SmsMessage.TABLE_NAME);
					builder.and(DataBaseQueryOpertion.NULL, SmsMessage.REPLY_CONTENT);
					builder.and(SmsMessage.TASK_ID, box.getTaskid());

					SmsMessage msg = (SmsMessage) this.dao.findOneByQuery(builder, SmsMessage.class);

					if (msg == null) {
						logger.error(String.format("没有需要回复的短信或者已经回复过了[%s]", box));
					} else {

						if (!EcUtil.isEmpty(box.getContent())) {
							msg.setReplyContent(box.getContent());
							this.dao.updateById(msg);
							ServiceScore score = new ServiceScore();
							if (msg.getSmsRefKey()!=null && msg.getSmsRefKey().equalsIgnoreCase("id")) {
								score.setSoId(msg.getSmsRefValue());

								ServiceOrder order = (ServiceOrder) this.dao.findByKeyValue(ServiceOrder.ID, msg.getSmsRefValue(), ServiceOrder.TABLE_NAME, ServiceOrder.class);

								if (order != null) {
									score.setSpId(order.getSpId());
								}

								User user = (User) this.dao.findByKeyValue(User.MOBILE_NUMBER, msg.getMobileNumber(), User.TABLE_NAME, User.class);

								if (user != null) {

									score.setUserId(user.getId());
								}
							}
							
							if (box.getContent() != null) {
								if (box.getContent().trim().equalsIgnoreCase(String.valueOf(ServiceScore.USER_SCORE_BAD))) {
									score.setUserScoreType(ServiceScore.USER_SCORE_BAD);

								} else if (box.getContent().trim().equalsIgnoreCase(String.valueOf(ServiceScore.USER_SCORE_GOOD))) {
									score.setUserScoreType(ServiceScore.USER_SCORE_GOOD);

								} else if (box.getContent().trim().equalsIgnoreCase(String.valueOf(ServiceScore.USER_SCORE_MIDDLE))) {
									score.setUserScoreType(ServiceScore.USER_SCORE_MIDDLE);

								} else {
									logger.error(String.format("回复的内容不受支持[%s]", box));

								}
							}
							
							orderService.addServiceOrderScore(score);
						}
					}
				}
			}
			System.out.println(sms);

		} catch (Exception e) {
			logger.error("获取短信回复失败", e);
		}

	}

	public ReturnSms getSmsRemainingMongey(SearchVo vo) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("action", "overage");

		if (vo != null && vo.getSmsAccountUserid() != null) {
			parameters.put("userid", vo.getSmsAccountUserid());
			parameters.put("account", vo.getSmsAccountName());
			parameters.put("password", vo.getSmsAccountPassword());
		} else {
			setSmsAccount(parameters);
		}

		try {
			String response = HttpClientUtil.doGet("http://114.113.227.101:8888/sms.aspx", parameters);

			XStream xs = new XStream(new DomDriver());
			xs.alias("returnsms", ReturnSms.class);
			ReturnSms sms = (ReturnSms) xs.fromXML(response);

	

			return sms;
		} catch (Exception e) {
			logger.error("获取余额失败", e);
		}
		
		return null;

	}
	
	public void checkSmsContent(String content) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("action", "checkkeyword");
		setSmsAccount(parameters);
		parameters.put("content", content);
		ReturnSms sms = null;
		try {
			String response = HttpClientUtil.doGet("http://114.113.227.101:8888/sms.aspx", parameters);
			XStream xs = new XStream(new DomDriver());
			xs.alias("returnsms", ReturnSms.class);
			sms = (ReturnSms) xs.fromXML(response);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (sms!=null && sms.getReturnstatus() != null && sms.getReturnstatus().startsWith("包含")) {
			throw new ResponseException(sms.getReturnstatus());
		}

	}

}
