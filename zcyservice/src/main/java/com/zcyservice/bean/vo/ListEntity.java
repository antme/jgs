package com.zcyservice.bean.vo;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.zcy.bean.BaseEntity;

public class ListEntity<T extends BaseEntity> {

	public List<T> entitys;

	public List<T> getEntitys() {
		return entitys;
	}

	public void setEntitys(List<T> entitys) {
		this.entitys = entitys;
	}

}
