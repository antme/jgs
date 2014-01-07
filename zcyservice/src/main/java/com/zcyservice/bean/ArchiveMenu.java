package com.zcyservice.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = ArchiveMenu.TABLE_NAME)
public class ArchiveMenu extends BaseEntity {
	public static final String TABLE_NAME = "ArchiveMenu";

	@Column(name = "startPage")
	@Expose
	public Integer startPage;

	@Column(name = "menuName")
	@Expose
	public String menuName;

	@Column(name = "archiveFileId")
	@Expose
	public String archiveFileId;

	public Integer getStartPage() {
		return startPage;
	}

	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getArchiveFileId() {
		return archiveFileId;
	}

	public void setArchiveFileId(String archiveFileId) {
		this.archiveFileId = archiveFileId;
	}

}
