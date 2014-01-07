package com.zcyservice.bean.vo;

import java.util.List;

import com.zcy.bean.BaseEntity;

public class ArvhiveTree extends BaseEntity {

	public String text;

	public String iconCls;

	public List<ArvhiveTree> children;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public List<ArvhiveTree> getChildren() {
		return children;
	}

	public void setChildren(List<ArvhiveTree> children) {
		this.children = children;
	}

}
