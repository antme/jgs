package com.zcyservice.bean.vo;

import java.util.List;

import com.zcy.bean.BaseEntity;

public class ArchiveTree extends BaseEntity {

	public String text;

	public String iconCls;
	
	public String filePath;
	
	public int pdfMenuPage;

	public List<ArchiveTree> children;

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

	public List<ArchiveTree> getChildren() {
		return children;
	}

	public void setChildren(List<ArchiveTree> children) {
		this.children = children;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getPdfMenuPage() {
		return pdfMenuPage;
	}

	public void setPdfMenuPage(int pdfMenuPage) {
		this.pdfMenuPage = pdfMenuPage;
	}

	
	

}
