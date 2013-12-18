package com.zcyservice.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = SmsTemplate.TABLE_NAME)
public class SmsTemplate extends BaseEntity {

	public static final String TEMPLATE = "template";

	public static final String TEMPLATE_ID = "templateId";

	public static final String TABLE_NAME = "SmsTemplate";

	@Column(name = TEMPLATE_ID)
	@Expose
	public String templateId;

	@Column(name = TEMPLATE)
	@Expose
	public String template;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	
	

}
