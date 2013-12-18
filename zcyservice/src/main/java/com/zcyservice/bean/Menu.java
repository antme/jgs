package com.zcyservice.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = Menu.TABLE_NAME)
public class Menu extends BaseEntity {
	public static final String MENU_ID = "menuId";

	public static final String GROUP_ID = "groupId";

	public static final String TABLE_NAME = "Menu";

	@Column(name = MENU_ID)
	@Expose
	public String menuId;

	@Column(name = GROUP_ID)
	@Expose
	public String groupId;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	
	
	

}
