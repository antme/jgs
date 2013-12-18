package com.jgsservice.bean.vo;

import com.google.gson.annotations.Expose;
import com.jgs.bean.BaseEntity;

public class CategoryVO extends BaseEntity {
	
	@Expose
	public String name;

	@Expose
	public String level;

	@Expose
	public String sort;

	@Expose
	public String iconImage;

	@Expose
	public String showImage;

	@Expose
	public Boolean isVisible;

	@Expose
	public String parent_id;
	
	@Expose
	public String keyword;
	
	@Expose
	public Boolean mergeTree;
	
	@Expose
	public String color;

	public String getColor() {
    	return color;
    }

	public void setColor(String color) {
    	this.color = color;
    }

	public String getName() {
    	return name;
    }

	public void setName(String name) {
    	this.name = name;
    }

	public String getLevel() {
    	return level;
    }

	public void setLevel(String level) {
    	this.level = level;
    }

	public String getSort() {
    	return sort;
    }

	public void setSort(String sort) {
    	this.sort = sort;
    }

	public String getIconImage() {
    	return iconImage;
    }

	public void setIconImage(String iconImage) {
    	this.iconImage = iconImage;
    }

	public String getShowImage() {
    	return showImage;
    }

	public void setShowImage(String showImage) {
    	this.showImage = showImage;
    }

	public Boolean getIsVisible() {
    	return isVisible;
    }

	public void setIsVisible(Boolean isVisible) {
    	this.isVisible = isVisible;
    }

	public String getParent_id() {
    	return parent_id;
    }

	public void setParent_id(String parent_id) {
    	this.parent_id = parent_id;
    }

	public String getKeyword() {
    	return keyword;
    }

	public void setKeyword(String keyword) {
    	this.keyword = keyword;
    }

	public Boolean getMergeTree() {
    	return mergeTree;
    }

	public void setMergeTree(Boolean mergeTree) {
    	this.mergeTree = mergeTree;
    }
	
}
