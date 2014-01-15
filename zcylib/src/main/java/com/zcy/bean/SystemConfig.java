package com.zcy.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = SystemConfig.TABLE_NAME)
public class SystemConfig extends BaseEntity {


	
	public static final String CONFIG_VALUE = "cfgValue";

	public static final String CONFIG_ID = "configId";

	public static final String TABLE_NAME = "SystemConfig";

	@Column(name = CONFIG_ID)
	@Expose
	public String configId;

	@Column(name = CONFIG_VALUE)
	@Expose
	public String cfgValue;

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getCfgValue() {
		return cfgValue;
	}

	public void setCfgValue(String value) {
		this.cfgValue = value;
	}

}
