package com.jgsservice.bean.vo;

import com.google.gson.annotations.Expose;
import com.jgs.bean.BaseEntity;

public class ServiceOrderForComplaintVO extends BaseEntity {
	//TODO add sp
	//订单编号
	@Expose
	public String poCode;

	//收货人姓名
	@Expose
	public String poReceiverName;

	//收货地址
	@Expose
	public String poReceiverAddress;

	//联系电话
	@Expose
	public String poReceiverPhone;

	//联系手机
	@Expose
	public String poReceiverMobilePhone;

	//宝贝标题
	@Expose
	public String poGoodsTitle;

	//物流单号
	@Expose
	public String poLogisticsNo;

	//物流公司
	@Expose
	public String poLogisticsCompany;

	//订单备注
	@Expose
	public String poOrderRemark;

	//宝贝数总量
	@Expose
	public Integer poGoodsNumber;

	//服务单号
	@Expose
	public String soCode;

	//服务商
	//@Expose
	//public String ;

	//服务价格
	@Expose
	public String price;

	//服务单状态
	@Expose
	public String soStatus;

	//投诉id
	@Expose
	public String complaintId;

	//投诉状态
	@Expose
	public String complaintStatus;

	//投诉类型
	@Expose
	public String complaintType;
	

	//投诉类型名称
	@Expose
	public String complaintTypeName;

	//投诉备注
	@Expose
	public String complaintRemark;

	//服务订单id
	@Expose
	public String serviceOrderId;
	
	
	@Expose
	public String spUserName;

	public String getPoCode() {
		return poCode;
	}

	public void setPoCode(String poCode) {
		this.poCode = poCode;
	}

	public String getPoReceiverName() {
		return poReceiverName;
	}

	public void setPoReceiverName(String poReceiverName) {
		this.poReceiverName = poReceiverName;
	}

	public String getPoReceiverAddress() {
		return poReceiverAddress;
	}

	public void setPoReceiverAddress(String poReceiverAddress) {
		this.poReceiverAddress = poReceiverAddress;
	}

	public String getPoReceiverPhone() {
		return poReceiverPhone;
	}

	public void setPoReceiverPhone(String poReceiverPhone) {
		this.poReceiverPhone = poReceiverPhone;
	}

	public String getPoReceiverMobilePhone() {
		return poReceiverMobilePhone;
	}

	public void setPoReceiverMobilePhone(String poReceiverMobilePhone) {
		this.poReceiverMobilePhone = poReceiverMobilePhone;
	}

	public String getPoGoodsTitle() {
		return poGoodsTitle;
	}

	public void setPoGoodsTitle(String poGoodsTitle) {
		this.poGoodsTitle = poGoodsTitle;
	}

	public String getPoLogisticsNo() {
		return poLogisticsNo;
	}

	public void setPoLogisticsNo(String poLogisticsNo) {
		this.poLogisticsNo = poLogisticsNo;
	}

	public String getPoLogisticsCompany() {
		return poLogisticsCompany;
	}

	public void setPoLogisticsCompany(String poLogisticsCompany) {
		this.poLogisticsCompany = poLogisticsCompany;
	}

	public String getPoOrderRemark() {
		return poOrderRemark;
	}

	public void setPoOrderRemark(String poOrderRemark) {
		this.poOrderRemark = poOrderRemark;
	}

	public Integer getPoGoodsNumber() {
		return poGoodsNumber;
	}

	public void setPoGoodsNumber(Integer poGoodsNumber) {
		this.poGoodsNumber = poGoodsNumber;
	}

	public String getSoCode() {
		return soCode;
	}

	public void setSoCode(String soCode) {
		this.soCode = soCode;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSoStatus() {
		return soStatus;
	}

	public void setSoStatus(String soStatus) {
		this.soStatus = soStatus;
	}

	public String getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}

	public String getComplaintStatus() {
		return complaintStatus;
	}

	public void setComplaintStatus(String complaintStatus) {
		this.complaintStatus = complaintStatus;
	}

	public String getComplaintType() {
    	return complaintType;
    }

	public void setComplaintType(String complaintType) {
    	this.complaintType = complaintType;
    }

	public String getComplaintRemark() {
    	return complaintRemark;
    }

	public void setComplaintRemark(String complaintRemark) {
    	this.complaintRemark = complaintRemark;
    }

	public String getComplaintTypeName() {
		return complaintTypeName;
	}

	public void setComplaintTypeName(String complaintTypeName) {
		this.complaintTypeName = complaintTypeName;
	}

	public String getServiceOrderId() {
		return serviceOrderId;
	}

	public void setServiceOrderId(String serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}


}
