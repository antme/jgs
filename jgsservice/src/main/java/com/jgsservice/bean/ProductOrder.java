package com.jgsservice.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.jgs.annotation.IntegerColumn;
import com.jgs.bean.BaseEntity;
import com.jgsservice.util.ProductOrderStatus;

@Table(name = ProductOrder.TABLE_NAME)
public class ProductOrder extends BaseEntity {
	public static final String SPLIT_ORDER_DATE = "splitOrderDate";
	public static final String ALL_COMMENTS = "allComments";
	public static final String CANCEL_DATE = "cancelDate";
	public static final String SYS_SPLIT_REASON = "sysSplitReason";
	public static final String SYS_SPLIT_REASON_REMARK = "sysSplitReasonRemark";
	public static final String ACTIVE_DATE = "activeDate";
	public static final String MFC_ID = "mfcId";
	public static final String PRICE = "price";
	public static final String USER_ID = "userId";
	public static final String PO_GOODS_NUMBER = "poGoodsNumber";
	public static final String PO_ORDER_REMARK = "poOrderRemark";
	public static final String PO_LOGISTICS_COMPANY = "poLogisticsCompany";
	public static final String PO_LOGISTICS_NO = "poLogisticsNo";
	public static final String PO_GOODS_CATE = "poGoodsCate";
	public static final String PO_GOODS_TITLE = "poGoodsTitle";
	public static final String PO_RECEIVER_MOBILE_PHONE = "poReceiverMobilePhone";
	public static final String PO_RECEIVER_PHONE = "poReceiverPhone";
	public static final String PO_RECEIVER_NAME = "poReceiverName";
	public static final String PO_RECEIVER_ADDRESS = "poReceiverAddress";
	public static final String PO_MEMBER_NAME = "poMemberName";
	public static final String PO_CODE = "poCode";
	public static final String TABLE_NAME = "ProductOrder";
	public static final String PO_STATUS = "poStatus";
	public static final String FINISH_DATE = "finishDate";
	public static final String CLOSE_DATE = "closeDate";
	public static final String BILL_STATUS = "billStatus";
	public static final String LOCATION_ID = "locationId";
	public static final String CATE_ID = "cateId";
	public static final String OPERATOR_ID = "operatorId";

	// 订单号
	@Column(name = PO_CODE, unique = true)
	@Expose
	public String poCode;

	// 买家会员名
	@Column(name = PO_MEMBER_NAME)
	@Expose
	public String poMemberName;

	// 买家收货地址
	@Column(name = PO_RECEIVER_ADDRESS)
	@Expose
	public String poReceiverAddress;

	// 收货人姓名
	@Column(name = PO_RECEIVER_NAME)
	@Expose
	public String poReceiverName;

	// 联系电话
	@Column(name = PO_RECEIVER_PHONE)
	@Expose
	public String poReceiverPhone;
	// 联系手机
	@Column(name = PO_RECEIVER_MOBILE_PHONE)
	@Expose
	public String poReceiverMobilePhone;

	// 宝贝标题
	@Column(name = PO_GOODS_TITLE)
	@Expose
	public String poGoodsTitle;
	// 宝贝种类
	@Column(name = PO_GOODS_CATE)
	@Expose
	public String poGoodsCate;
	// 物流单号
	@Column(name = PO_LOGISTICS_NO)
	@Expose
	public String poLogisticsNo;
	// 物流公司
	@Column(name = PO_LOGISTICS_COMPANY)
	@Expose
	public String poLogisticsCompany;
	// 订单备注
	@Column(name = PO_ORDER_REMARK)
	@Expose
	public String poOrderRemark;
	// 宝贝总数量
	@Column(name = PO_GOODS_NUMBER)
	@Expose
	@IntegerColumn
	public Integer poGoodsNumber;

	@Column(name = PO_STATUS)
	@Expose
	public ProductOrderStatus poStatus;

	@Column(name = PRICE)
	@Expose
	public Integer price;

	//属于哪个厂商
	@Column(name = USER_ID)
	@Expose
	public String userId;
	
	@Column(name = FINISH_DATE)
	@Expose
	public Date finishDate;
	
	@Column(name = SPLIT_ORDER_DATE)
	@Expose
	public Date splitOrderDate;
	
	@Column(name = ACTIVE_DATE)
	@Expose
	public Date activeDate;
	
	@Column(name = CLOSE_DATE)
	@Expose
	public Date closeDate;
	
	@Column(name = CANCEL_DATE)
	@Expose
	public Date cancelDate;
	
	@Column(name = BILL_STATUS)
	@Expose
	public BillStatus billStatus;

	
	@Column(name = MFC_ID)
	@Expose
	public String mfcId;
	
	
	@Column(name = CATE_ID)
	@Expose
	public String cateId;
	
	@Column(name = LOCATION_ID)
	@Expose
	public String locationId;
	
	@Column(name = OPERATOR_ID)
	@Expose
	public String operatorId;
	
	@Column(name = SYS_SPLIT_REASON)
	@Expose
	public String sysSplitReason;
	
	@Column(name = SYS_SPLIT_REASON_REMARK)
	@Expose
	public String sysSplitReasonRemark;
	
	@Column(name = ALL_COMMENTS)
	@Expose
	public String  allComments;
	
	
	// VO字段，显示JOIN查询字段
	@Expose
	public String mfcStoreName;
	
	@Expose
	public String kfName;

	
	@Expose
	public String  mfcContactMobilePhone;
	
	@Expose
	public String  mfcContactPerson;
	
	@Expose
	public String  comments;
	

	
	

	public String getKfName() {
    	return kfName;
    }

	public void setKfName(String kfName) {
    	this.kfName = kfName;
    }

	public String getPoCode() {
		return poCode;
	}

	public void setPoCode(String poCode) {
		this.poCode = poCode;
	}

	public String getPoMemberName() {
		return poMemberName;
	}

	public void setPoMemberName(String poMemberName) {
		this.poMemberName = poMemberName;
	}

	public String getPoReceiverAddress() {
		return poReceiverAddress;
	}

	public void setPoReceiverAddress(String poReceiveraddress) {
		this.poReceiverAddress = poReceiveraddress;
	}

	public String getPoReceiverName() {
		return poReceiverName;
	}

	public void setPoReceiverName(String poReceiverName) {
		this.poReceiverName = poReceiverName;
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

	public String getPoGoodsCate() {
		return poGoodsCate;
	}

	public void setPoGoodsCate(String poGoodsCate) {
		this.poGoodsCate = poGoodsCate;
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

	public void setPoGoodsNumber(Integer poGoodsNo) {
		this.poGoodsNumber = poGoodsNo;
	}

	public ProductOrderStatus getPoStatus() {
		return poStatus;
	}

	public void setPoStatus(ProductOrderStatus poStatus) {
		this.poStatus = poStatus;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getPrice() {
    	return price;
    }

	public void setPrice(Integer price) {
    	this.price = price;
    }

	public Date getFinishDate() {
    	return finishDate;
    }

	public void setFinishDate(Date finishDate) {
    	this.finishDate = finishDate;
    }

	public Date getCloseDate() {
    	return closeDate;
    }

	public void setCloseDate(Date closeDate) {
    	this.closeDate = closeDate;
    }

	public BillStatus getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(BillStatus billStatus) {
		this.billStatus = billStatus;
	}

	public String getMfcId() {
		return mfcId;
	}

	public void setMfcId(String mfcId) {
		this.mfcId = mfcId;
	}

	public String getCateId() {
    	return cateId;
    }

	public void setCateId(String cateId) {
    	this.cateId = cateId;
    }

	public String getLocationId() {
    	return locationId;
    }

	public void setLocationId(String locationId) {
    	this.locationId = locationId;
    }

	public String getOperatorId() {
    	return operatorId;
    }

	public void setOperatorId(String operatorId) {
    	this.operatorId = operatorId;
    }

	public Date getActiveDate() {
    	return activeDate;
    }

	public void setActiveDate(Date activeDate) {
    	this.activeDate = activeDate;
    }

	public String getSysSplitReason() {
		return sysSplitReason;
	}

	public void setSysSplitReason(String sysSplitReason) {
		this.sysSplitReason = sysSplitReason;
	}

	public String getSysSplitReasonRemark() {
		return sysSplitReasonRemark;
	}

	public void setSysSplitReasonRemark(String sysSplitReasonRemark) {
		this.sysSplitReasonRemark = sysSplitReasonRemark;
	}

	public String getMfcStoreName() {
		return mfcStoreName;
	}

	public void setMfcStoreName(String mfcStoreName) {
		this.mfcStoreName = mfcStoreName;
	}

	public String getMfcContactMobilePhone() {
		return mfcContactMobilePhone;
	}

	public void setMfcContactMobilePhone(String mfcContactMobilePhone) {
		this.mfcContactMobilePhone = mfcContactMobilePhone;
	}

	public String getMfcContactPerson() {
		return mfcContactPerson;
	}

	public void setMfcContactPerson(String mfcContactPerson) {
		this.mfcContactPerson = mfcContactPerson;
	}

	public Date getCancelDate() {
    	return cancelDate;
    }

	public void setCancelDate(Date cancelDate) {
    	this.cancelDate = cancelDate;
    }

	public String getAllComments() {
		return allComments;
	}

	public void setAllComments(String allComments) {
		this.allComments = allComments;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getSplitOrderDate() {
		return splitOrderDate;
	}

	public void setSplitOrderDate(Date splitOrderDate) {
		this.splitOrderDate = splitOrderDate;
	}
	

	
	

}
