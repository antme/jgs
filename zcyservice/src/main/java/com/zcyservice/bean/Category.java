package com.zcyservice.bean;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = Category.TABLE_NAME)
public class Category extends BaseEntity {

	public static final String CA_WHOLE_NAME = "caWholeName";
	public static final String FINAL_SORT = "finalSort";
	public static final String KEYWORD = "keyword";
	public static final String SPLIT_ORDER_NUMBER = "splitOrderNumber";
	public static final String TABLE_NAME = "Category";
	public static final String NAME = "name";
	public static final String COLOR = "color";
	public static final String LEVEL = "level"; // 等级 1->n
	public static final String SORT_INDEX = "sortIndex"; // 排序
	public static final String ICON_IMAGE = "iconImage";
	public static final String SHOW_IMAGE = "showImage";
	public static final String IS_VISIBLE = "isVisible";
	public static final String DESCRIBION = "description";
	public static final String PARENT_ID = "parent_id";
	public static final String CHILDEN = "childen";
	@Column(name = NAME, unique = true)
	@Expose
	public String name;

	@Column(name = COLOR)
	@Expose
	public String color;
	
	@Column(name = LEVEL)
	@Expose
	public String level;

	@Column(name = SORT_INDEX)
	@Expose
	public Integer sortIndex;
	
	@Column(name = FINAL_SORT)
	@Expose
	public Integer finalSort;

	@Column(name = ICON_IMAGE)
	@Expose
	public String iconImage;
	
	@Column(name = SHOW_IMAGE)
	@Expose
	public String showImage;
	
	@Column(name = IS_VISIBLE)
	@Expose
	public Boolean isVisible;
	
	@Column(name = DESCRIBION)
	@Expose
	public String description;

	@Column(name = PARENT_ID)
	@Expose
	public String parent_id;

	@Column(name = SPLIT_ORDER_NUMBER)
	@Expose
	public Integer splitOrderNumber;
		
	@Column(name = KEYWORD)
	@Expose
	public String keyword;
	
	//服务商服务区域是否选中
	@Expose
	public String checked;
	
	@Column(name = CA_WHOLE_NAME)
	@Expose
	public String caWholeName;
	
	

	public String getCaWholeName() {
		return caWholeName;
	}

	public void setCaWholeName(String caWholeName) {
		this.caWholeName = caWholeName;
	}

	public String getChecked() {
    	return checked;
    }

	public void setChecked(String checked) {
    	this.checked = checked;
    }

	public Set<Category> childen = new LinkedHashSet<Category>();

	public Set<Category> getChilden() {
		return childen;
	}

	public void setChilden(Set<Category> childen) {
		this.childen = childen;
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

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sort) {
		this.sortIndex = sort;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public Integer getSplitOrderNumber() {
		return splitOrderNumber;
	}

	public void setSplitOrderNumber(Integer splitOrderNumber) {
		this.splitOrderNumber = splitOrderNumber;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

	public String getColor() {
    	return color;
    }

	public void setColor(String color) {
    	this.color = color;
    }

	public Integer getFinalSort() {
		return finalSort;
	}

	public void setFinalSort(Integer finalSort) {
		this.finalSort = finalSort;
	}
	
	

}
