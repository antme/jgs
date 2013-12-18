package com.jgsservice.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.jgs.bean.BaseEntity;
import com.jgsservice.util.UserStatus;

@Table(name = ServiceProvider.TABLE_NAME)
public class ServiceProvider extends BaseEntity {
	public static final String SP_LOCATION_PROVINCE_ID = "spLocationProvinceId";
	public static final String SP_SERVICE_TYPE_STR = "spServiceTypeStr";
	public static final String LAT = "lat";
	public static final String LNG = "lng";
	public static final String SP_LOCATION_AREA_ID = "spLocationAreaId";

	public static final String TABLE_NAME = "ServiceProvider";
	//修改信息时候审核临时表
	public static final String TABLE_NAME_TEMP = "ServiceProviderTemp";

	public static final String SP_STATUS = "spStatus";

	public static final String SP_WANG_WANG = "spWangWang";

	public static final String SP_QQ = "spQQ";

	public static final String SP_LICENSE_NO = "spLicenseNo";

	public static final String SP_LEVEL = "spLevel";

	public static final String SP_CONTACT_MOBILE_PHONE = "spContactMobilePhone";

	public static final String SP_CONTACT_PHONE = "spContactPhone";

	public static final String SP_CONTACT_PERSON = "spContactPerson";

	public static final String SP_LOCATION = "spLocation";

	public static final String SP_COMPANY_ADDRESS = "spCompanyAddress";

	public static final String SP_SERVICE_TYPE = "spServiceType";

	public static final String SP_USER_NAME = "spUserName";

	public static final String SP_COMPANY_NAME = "spCompanyName";

	public static final String SP_CODE = "spCode";

	public static final String USER_ID = "userId";

	public static final String SCORE = "score";//评分
	
	public static final String SCORE_GOOD = "scoreGood";//好评次数
	
	public static final String SCORE_BAD = "scoreBad";//差评次数
	
	public static final String SCORE_MIDDLE = "scoreMiddle";//中评次数

	public static final String SP_BRANCH_ADDRESS = "spBranchAddress";//网点地址  (已作废)
	
	public static final String SP_STORE_IMAGE ="storeImage";
	
	@Column(name = SP_STORE_IMAGE)
	@Expose
	public String storeImage;
	
	@Column(name = SP_BRANCH_ADDRESS)
	@Expose
	public String spBranchAddress;
	
	@Column(name = SCORE)
	@Expose
	public Integer score;

	@Column(name = SCORE_GOOD)
	@Expose
	public Integer scoreGood;
	
	@Column(name = SCORE_BAD)
	@Expose
	public Integer scoreBad;
	
	@Column(name = SCORE_MIDDLE)
	@Expose
	public Integer scoreMiddle;
	
	@Column(name = SP_USER_NAME, unique = true)
	@Expose
	public String spUserName;

	@Column(name = SP_CODE)
	@Expose
	public String spCode;

	@Column(name = SP_SERVICE_TYPE)
	@Expose
	public String spServiceType;
	
	@Column(name = SP_SERVICE_TYPE_STR)
	@Expose
	public String spServiceTypeStr;

	@Column(name = SP_COMPANY_NAME)
	@Expose
	public String spCompanyName;

	@Column(name = SP_COMPANY_ADDRESS)
	@Expose
	public String spCompanyAddress;

	@Column(name = SP_LOCATION)
	@Expose
	public String spLocation;

	@Column(name = SP_CONTACT_PERSON)
	@Expose
	public String spContactPerson;

	@Column(name = SP_CONTACT_PHONE)
	@Expose
	public String spContactPhone;

	@Column(name = SP_CONTACT_MOBILE_PHONE)
	@Expose
	public String spContactMobilePhone;

	@Column(name = SP_LEVEL)
	@Expose
	public String spLevel;

	@Column(name = SP_LICENSE_NO)
	@Expose
	public String spLicenseNo;

	@Column(name = SP_QQ)
	@Expose
	public String spQQ;

	@Column(name = SP_WANG_WANG)
	@Expose
	public String spWangWang;

	@Column(name = SP_STATUS)
	@Expose
	public UserStatus spStatus;

	@Column(name = USER_ID)
	@Expose
	public String userId;

	@Column(name = SP_LOCATION_PROVINCE_ID)
	@Expose
	public String spLocationProvinceId;

	@Column(name = "spLocationCityId")
	@Expose
	public String spLocationCityId;

	@Column(name = SP_LOCATION_AREA_ID)
	@Expose
	public String spLocationAreaId;

	@Column(name = "spCompanyAdressProvinceId")
	@Expose
	public String spCompanyAdressProvinceId;

	@Column(name = "spCompanyAdressAreaId")
	@Expose
	public String spCompanyAdressAreaId;

	@Column(name = "spCompanyAdressCityId")
	@Expose
	public String spCompanyAdressCityId;
	
	@Column(name = "spCompanySize")
	@Expose
	public String spCompanySize;
	
	//审核拒绝时候的字段，会发送短信给注册者
	@Column(name = "password")
	@Expose
	public String password;
	
	//精度纬度，计算距离用
	@Column(name = LNG)
	@Expose
	public Double lng;

	@Column(name = LAT)
	@Expose
	public Double lat;
	
	//根据外键userId取User中的status字段
	//用户管理模块加此字段供列表显示
	@Expose
	public String userStatus;
	
	//审核拒绝时候的字段，会发送短信给注册者
	@Expose
	public String rejectReson;
	
	//人为返回订单换服务商时候距离
	@Expose
	public Double distance;
	
	
	//查询服务商服务价格字段
	@Expose
	public Double price;
	
	
	public Double getPrice() {
    	return price;
    }

	public void setPrice(Double price) {
    	this.price = price;
    }

	@Expose
	public String[] spServiceTypeArray;
	

	public String getSpLocationProvinceId() {
		return spLocationProvinceId;
	}

	public void setSpLocationProvinceId(String spLocationProvinceId) {
		this.spLocationProvinceId = spLocationProvinceId;
	}

	public String getSpLocationCityId() {
		return spLocationCityId;
	}

	public void setSpLocationCityId(String spLocationCityId) {
		this.spLocationCityId = spLocationCityId;
	}

	public String getSpLocationAreaId() {
		return spLocationAreaId;
	}

	public void setSpLocationAreaId(String spLocationAreaId) {
		this.spLocationAreaId = spLocationAreaId;
	}

	public String getSpCompanyAdressProvinceId() {
		return spCompanyAdressProvinceId;
	}

	public void setSpCompanyAdressProvinceId(String spCompanyAdressProvinceId) {
		this.spCompanyAdressProvinceId = spCompanyAdressProvinceId;
	}

	public String getSpCompanyAdressAreaId() {
		return spCompanyAdressAreaId;
	}

	public void setSpCompanyAdressAreaId(String spCompanyAdressAreaId) {
		this.spCompanyAdressAreaId = spCompanyAdressAreaId;
	}

	public String getSpCompanyAdressCityId() {
		return spCompanyAdressCityId;
	}

	public void setSpCompanyAdressCityId(String spCompanyAdressCityId) {
		this.spCompanyAdressCityId = spCompanyAdressCityId;
	}

	public ServiceProvider() {
	}

	public String getSpUserName() {
		return spUserName;
	}

	public void setSpUserName(String spUserName) {
		this.spUserName = spUserName;
	}

	public String getSpCode() {
		return spCode;
	}

	public void setSpCode(String spCode) {
		this.spCode = spCode;
	}

	public String getSpServiceType() {
		return spServiceType;
	}

	public void setSpServiceType(String spServiceType) {
		this.spServiceType = spServiceType;
	}

	public String getSpCompanyName() {
		return spCompanyName;
	}

	public void setSpCompanyName(String spCompanyName) {
		this.spCompanyName = spCompanyName;
	}

	public String getSpCompanyAddress() {
		return spCompanyAddress;
	}

	public void setSpCompanyAddress(String spCompanyAddress) {
		this.spCompanyAddress = spCompanyAddress;
	}

	public String getSpLocation() {
		return spLocation;
	}

	public void setSpLocation(String spLocation) {
		this.spLocation = spLocation;
	}

	public String getSpContactPerson() {
		return spContactPerson;
	}

	public void setSpContactPerson(String spContactPerson) {
		this.spContactPerson = spContactPerson;
	}

	public String getSpContactPhone() {
		return spContactPhone;
	}

	public void setSpContactPhone(String spContactPhone) {
		this.spContactPhone = spContactPhone;
	}

	public String getSpLevel() {
		return spLevel;
	}

	public void setSpLevel(String spLevel) {
		this.spLevel = spLevel;
	}

	public String getSpLicenseNo() {
		return spLicenseNo;
	}

	public void setSpLicenseNo(String spLicenseNo) {
		this.spLicenseNo = spLicenseNo;
	}

	public UserStatus getSpStatus() {
		return spStatus;
	}

	public void setSpStatus(UserStatus spStatus) {
		this.spStatus = spStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSpQQ() {
		return spQQ;
	}

	public void setSpQQ(String spQQ) {
		this.spQQ = spQQ;
	}

	public String getSpWangWang() {
		return spWangWang;
	}

	public void setSpWangWang(String spWangWang) {
		this.spWangWang = spWangWang;
	}

	public Double getLng() {
    	return lng;
    }

	public void setLng(Double lng) {
    	this.lng = lng;
    }

	public Double getLat() {
    	return lat;
    }

	public void setLat(Double lat) {
    	this.lat = lat;
    }

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public Integer getScore() {
    	return score;
    }

	public void setScore(Integer score) {
    	this.score = score;
    }

	public Integer getScoreGood() {
    	return scoreGood;
    }

	public void setScoreGood(Integer scoreGood) {
    	this.scoreGood = scoreGood;
    }

	public Integer getScoreBad() {
    	return scoreBad;
    }

	public void setScoreBad(Integer scoreBad) {
    	this.scoreBad = scoreBad;
    }

	public Integer getScoreMiddle() {
    	return scoreMiddle;
    }

	public void setScoreMiddle(Integer scoreMiddle) {
    	this.scoreMiddle = scoreMiddle;
    }

	public String getSpServiceTypeStr() {
    	return spServiceTypeStr;
    }

	public void setSpServiceTypeStr(String spServiceTypeStr) {
    	this.spServiceTypeStr = spServiceTypeStr;
    }

	public String getSpBranchAddress() {
    	return spBranchAddress;
    }

	public void setSpBranchAddress(String spBranchAddress) {
    	this.spBranchAddress = spBranchAddress;
    }

	public String getSpContactMobilePhone() {
		return spContactMobilePhone;
	}

	public void setSpContactMobilePhone(String spContactMobilePhone) {
		this.spContactMobilePhone = spContactMobilePhone;
	}
	
	public String getStoreImage() {
    	return storeImage;
    }

	public void setStoreImage(String storeImage) {
    	this.storeImage = storeImage;
    }

	public String getRejectReson() {
		return rejectReson;
	}

	public void setRejectReson(String rejectReson) {
		this.rejectReson = rejectReson;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String[] getSpServiceTypeArray() {
    	return spServiceTypeArray;
    }

	public void setSpServiceTypeArray(String[] spServiceTypeArray) {
    	this.spServiceTypeArray = spServiceTypeArray;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSpCompanySize() {
		return spCompanySize;
	}

	public void setSpCompanySize(String spCompanySize) {
		this.spCompanySize = spCompanySize;
	}
	
	
	
	
	
	
}
