package com.zcyservice.bean.vo;

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

public class SearchVo extends BaseEntity {

	@Expose
	public Date startDate;

	@Expose
	public Date endDate;

	@Expose
	public String keyword;

	@Expose
	public String orderStatus;
	@Expose
	public String isGoodsArrived;

	// 区域和分类查询服务商
	@Expose
	public String locationId;

	@Expose
	public String categoryGrandpaId;
	
	@Expose
	public String categoryParentId;
	
	// 区域和分类查询服务商,账单查询
	@Expose
	public String categoryId;

	// 24,48小时订单提醒查询类型
	@Expose
	public int orderNoticType;

	// 账单查询
	@Expose
	public String provinceId;
	@Expose
	public String cityId;
	@Expose
	public String countyId;
	@Expose
	public String dateType;
	@Expose
	public String spId;
	@Expose
	public String mfcId;


	// 用户管理
	@Expose
	public String userStatus;

	// 按角色获取user列表
	@Expose
	public String roleName;

	// 日志类型
	@Expose
	public String logType;
	
	@Expose
	public String cityName;
	
	@Expose
	public String cateGoryName;
	
	@Expose
	public String spkeyword;
	
	@Expose
	public String isAdmin;
	
	@Expose
	public boolean isOverride = false;
	
	@Expose
	public String effectiveType;
	
	
	
	@Expose
	public String baiduMapKey;
	
	@Expose
	public String smsAccountUserid;
	
	
	@Expose
	public String smsAccountName;
	
	@Expose
	public String smsAccountPassword;
	
	
	
	//厂商服务商审核时候拒绝原因
	@Expose
	public String rejectReason;
	
	@Expose
	public String kfId;

	public int getOrderNoticType() {
		return orderNoticType;
	}

	public void setOrderNoticType(int orderNoticType) {
		this.orderNoticType = orderNoticType;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keywords) {
		this.keyword = keywords;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCountyId() {
		return countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getMfcId() {
		return mfcId;
	}

	public void setMfcId(String mfcId) {
		this.mfcId = mfcId;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getIsGoodsArrived() {
		return isGoodsArrived;
	}

	public void setIsGoodsArrived(String isGoodsArrived) {
		this.isGoodsArrived = isGoodsArrived;
	}

	public String getRejectReason() {
    	return rejectReason;
    }

	public void setRejectReason(String rejectReason) {
    	this.rejectReason = rejectReason;
    }

	public String getCityName() {
    	return cityName;
    }

	public void setCityName(String cityName) {
    	this.cityName = cityName;
    }

	public String getCateGoryName() {
    	return cateGoryName;
    }

	public void setCateGoryName(String cateGoryName) {
    	this.cateGoryName = cateGoryName;
    }

	public String getSpkeyword() {
    	return spkeyword;
    }

	public void setSpkeyword(String spkeyword) {
    	this.spkeyword = spkeyword;
    }

	public String getIsAdmin() {
    	return isAdmin;
    }

	public void setIsAdmin(String isAdmin) {
    	this.isAdmin = isAdmin;
    }

	public boolean isOverride() {
    	return isOverride;
    }

	public void setOverride(boolean isOverride) {
    	this.isOverride = isOverride;
    }

	public String getEffectiveType() {
		return effectiveType;
	}

	public void setEffectiveType(String effectiveType) {
		this.effectiveType = effectiveType;
	}

	public String getKfId() {
    	return kfId;
    }

	public void setKfId(String kfId) {
    	this.kfId = kfId;
    }

	public String getBaiduMapKey() {
    	return baiduMapKey;
    }

	public void setBaiduMapKey(String baiduMapKey) {
    	this.baiduMapKey = baiduMapKey;
    }

	public String getSmsAccountUserid() {
    	return smsAccountUserid;
    }

	public void setSmsAccountUserid(String smsAccountUserid) {
    	this.smsAccountUserid = smsAccountUserid;
    }

	public String getSmsAccountName() {
    	return smsAccountName;
    }

	public void setSmsAccountName(String smsAccountName) {
    	this.smsAccountName = smsAccountName;
    }

	public String getSmsAccountPassword() {
    	return smsAccountPassword;
    }

	public void setSmsAccountPassword(String smsAccountPassword) {
    	this.smsAccountPassword = smsAccountPassword;
    }

	public String getCategoryGrandpaId() {
    	return categoryGrandpaId;
    }

	public void setCategoryGrandpaId(String categoryGrandpaId) {
    	this.categoryGrandpaId = categoryGrandpaId;
    }

	public String getCategoryParentId() {
    	return categoryParentId;
    }

	public void setCategoryParentId(String categoryParentId) {
    	this.categoryParentId = categoryParentId;
    }
	

	

}
