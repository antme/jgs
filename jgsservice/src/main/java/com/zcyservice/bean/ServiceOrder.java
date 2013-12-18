package com.jgsservice.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.jgs.annotation.IntegerColumn;
import com.jgs.bean.BaseEntity;
import com.jgsservice.util.ProductOrderStatus;

@Table(name = ServiceOrder.TABLE_NAME)
public class ServiceOrder extends BaseEntity {
	public static final String ASSIGN_DATE = "assignDate";
	public static final String IS_NOTICED = "isNoticed";
	public static final String SP_CHANGE_DATE = "spChangeDate";
	public static final String ACCEPTED_DATE = "acceptedDate";
	public static final String COMMENT_DATE = "commentDate";
	public static final String ALL_COMMENTS = "allComments";
	protected static final String CANCEL_DATE = "cancelDate";
	public static final String TERMINATE_DATE = "terminateDate";
	public static final String REJECT_DATE = "rejectDate";
	public static final String MFC_BILL_ID = "mfcBillId";
	public static final String MFC_NEED_BILL = "mfcNeedBill";
	public static final String SP_NEED_BILL = "spNeedBill";
	public static final String SP_BILL_ID = "spBillId";
	public static final String EXPIRED_DATE = "expiredDate";
	public static final String USER_CONFIRM_DATE = "userConfirmDate";
	public static final String OPERATOR_ID = "operatorId";
	public static final String MFC_USER_ID = "mfcUserId";
	public static final String BILL_STATUS = "billStatus";
	public static final String IS_GOODS_ARRIVED = "isGoodsArrived";
	public static final String CLOSE_DATE = "closeDate";
	public static final String LOCATION_ID = "locationId";
	public static final String CATE_ID = "cateId";
	public static final String LAT = "lat";
	public static final String LNG = "lng";
	public static final String SP_USER_ID = "spUserId";
	public static final String MFC_ID = "mfcId";
	public static final String SPLIT_REMARK = "splitRemark";
	public static final String SMS_INSTALL_NOTICED = "smsInstallNoticed";
	public static final String SP_ID = "spId";
	public static final String NEED_NOTICE = "needNotice";
	public static final String EST_INSTALL_DATE = "estInstallDate";
	public static final String COMMISION = "commision";
	public static final String PLUS_PRICE = "plusPrice";
	public static final String PRICE = "price";
	public static final String FINISH_DATE = "finishDate";
	public static final String EXPIRED = "expired";
	public static final String USER_ID = "userId";
	public static final String PO_ID = "poId";
	public static final String PO_CODE = "poCode";

	public static final String SO_CODE = "soCode";
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
	public static final String TABLE_NAME = "ServiceOrder";
	public static final String SO_STATUS = "soStatus";

	@Column(name = PO_CODE)
	@Expose
	public String poCode;

	@Column(name = PO_MEMBER_NAME)
	@Expose
	public String poMemberName;

	@Column(name = PO_RECEIVER_ADDRESS)
	@Expose
	public String poReceiverAddress;

	@Column(name = PO_RECEIVER_NAME)
	@Expose
	public String poReceiverName;

	@Column(name = PO_RECEIVER_PHONE)
	@Expose
	public String poReceiverPhone;

	@Column(name = PO_RECEIVER_MOBILE_PHONE)
	@Expose
	public String poReceiverMobilePhone;

	@Column(name = PO_GOODS_TITLE)
	@Expose
	public String poGoodsTitle;

	@Column(name = PO_GOODS_CATE)
	@Expose
	public String poGoodsCate;

	@Column(name = PO_LOGISTICS_NO)
	@Expose
	public String poLogisticsNo;

	@Column(name = PO_LOGISTICS_COMPANY)
	@Expose
	public String poLogisticsCompany;

	@Column(name = PO_ORDER_REMARK)
	@Expose
	public String poOrderRemark;

	@Column(name = PO_GOODS_NUMBER)
	@Expose
	@IntegerColumn
	public Integer poGoodsNumber;

	@Column(name = SO_STATUS)
	@Expose
	public ProductOrderStatus soStatus;

	@Column(name = SO_CODE, unique = true)
	@Expose
	public String soCode;

	@Column(name = PO_ID)
	@Expose
	public String poId;

	@Column(name = USER_ID)
	@Expose
	public String userId;

	@Column(name = SP_ID)
	@Expose
	public String spId;

	@Column(name = SP_USER_ID)
	@Expose
	public String spUserId;

	@Column(name = MFC_ID)
	@Expose
	public String mfcId;
	
	@Column(name = MFC_USER_ID)
	@Expose
	public String mfcUserId;
	
	@Column(name = CATE_ID)
	@Expose
	public String cateId;
	
	@Column(name = LOCATION_ID)
	@Expose
	public String locationId;

	@Column(name = EXPIRED)
	@Expose
	public Boolean expired;
	
	@Column(name = IS_GOODS_ARRIVED)
	@Expose
	public Boolean isGoodsArrived;

	@Column(name = FINISH_DATE)
	@Expose
	public Date finishDate;
	

	@Column(name = ACCEPTED_DATE)
	@Expose
	public Date acceptedDate;


	@Column(name = ASSIGN_DATE)
	@Expose
	public Date assignDate;
	

	@Column(name = COMMENT_DATE)
	@Expose
	public Date commentDate;
	
	
	@Column(name = CLOSE_DATE)
	@Expose
	public Date closeDate;
	
	@Column(name = USER_CONFIRM_DATE)
	@Expose
	public Date userConfirmDate;
	
	@Column(name = EXPIRED_DATE)
	@Expose
	public Date expiredDate;

	@Column(name = SP_CHANGE_DATE)
	@Expose
	public Date spChangeDate;

	@Column(name = PRICE)
	@Expose
	public Integer price;

	@Column(name = PLUS_PRICE)
	@Expose
	@IntegerColumn
	public Integer plusPrice;

	@Column(name = SPLIT_REMARK)
	@Expose
	public String splitRemark;

	@Column(name = COMMISION)
	@Expose
	@IntegerColumn
	public Integer commision;

	@Column(name = BILL_STATUS)
	@Expose
	public BillStatus billStatus;

	@Column(name = "terminateRemark")
	@Expose
	public String terminateRemark;

	@Column(name = "terminateReason")
	@Expose
	public String terminateReason;

	@Column(name = "assignRemark")
	@Expose
	public String assignRemark;

	@Column(name = "rejectRemark")
	@Expose
	public String rejectRemark;

	@Column(name = "rejectReason")
	@Expose
	public String rejectReason;
	
	@Column(name = "workerId")
	@Expose
	public String workerId;

	@Column(name = EST_INSTALL_DATE)
	@Expose
	public Date estInstallDate;
	
	@Column(name = REJECT_DATE)
	@Expose
	public Date rejectDate;
	
	@Column(name = TERMINATE_DATE)
	@Expose
	public Date terminateDate;
	
	@Column(name = CANCEL_DATE)
	@Expose
	public Date cancelDate;

	@Column(name = "estInstallDateRegion")
	@Expose
	public String estInstallDateRegion;

	@Column(name = "estInstallDateTime")
	@Expose
	public String estInstallDateTime;

	@Column(name = NEED_NOTICE)
	@Expose
	public Boolean needNotice;

	@Column(name = IS_NOTICED)
	@Expose
	public Boolean isNoticed;


	@Column(name = LNG)
	@Expose
	public Double lng;

	@Column(name = LAT)
	@Expose
	public Double lat;
	
	@Column(name = OPERATOR_ID)
	@Expose
	public String operatorId;

	// 安装前24小时是否提醒过
	@Column(name = SMS_INSTALL_NOTICED)
	@Expose
	public Boolean smsInstallNoticed;
	
	@Column(name = SP_BILL_ID)
	@Expose
	public String spBillId;
	
	@Column(name = MFC_BILL_ID)
	@Expose
	public String mfcBillId;
	

	@Column(name = SP_NEED_BILL)
	@Expose
	public Boolean spNeedBill;
	
	
	@Column(name = MFC_NEED_BILL)
	@Expose
	public Boolean mfcNeedBill;
	
	
	@Column(name = ALL_COMMENTS)
	@Expose
	public String  allComments;
	
	
	// VO字段，显示JOIN查询服务商字段
	@Expose
	public String spUserName;
	
	@Expose
	public String  spContactMobilePhone;
	
	@Expose
	public String  spContactPerson;
	
	@Expose
	public String mfcStoreName;
	
	@Expose
	public String  mfcContactMobilePhone;
	
	@Expose
	public String  mfcContactPerson;
	
	
	@Expose
	public Date  billDate;
	
	@Expose
	public String  comments;
	
	@Expose
	public String kfName;
	
	@Expose
	public String categoryName;
	
	public String getCategoryName() {
    	return categoryName;
    }

	public void setCategoryName(String categoryName) {
    	this.categoryName = categoryName;
    }

	public String getKfName() {
    	return kfName;
    }

	public void setKfName(String kfName) {
    	this.kfName = kfName;
    }

	public void setNeedNotice(Boolean isNoticed) {
		this.needNotice = isNoticed;
	}
	
	

	public Boolean getIsNoticed() {
		return isNoticed;
	}

	public void setIsNoticed(Boolean isNoticed) {
		this.isNoticed = isNoticed;
	}

	public String getTerminateRemark() {
		return terminateRemark;
	}

	public void setTerminateRemark(String terminateRemark) {
		this.terminateRemark = terminateRemark;
	}

	public String getTerminateReason() {
		return terminateReason;
	}

	public void setTerminateReason(String terminateReason) {
		this.terminateReason = terminateReason;
	}

	public String getAssignRemark() {
		return assignRemark;
	}

	public void setAssignRemark(String assignRemark) {
		this.assignRemark = assignRemark;
	}

	public String getWorkerId() {
		return workerId;
	}

	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}

	public Date getEstInstallDate() {
		return estInstallDate;
	}

	public void setEstInstallDate(Date estInstallDate) {
		this.estInstallDate = estInstallDate;
	}

	public String getEstInstallDateRegion() {
		return estInstallDateRegion;
	}

	public void setEstInstallDateRegion(String estInstallDateRegion) {
		this.estInstallDateRegion = estInstallDateRegion;
	}

	public String getEstInstallDateTime() {
		return estInstallDateTime;
	}

	public void setEstInstallDateTime(String estInstallDateTime) {
		this.estInstallDateTime = estInstallDateTime;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getPlusPrice() {
		return plusPrice;
	}

	public void setPlusPrice(Integer plusPrice) {
		this.plusPrice = plusPrice;
	}

	public Integer getCommision() {
		return commision;
	}

	public void setCommision(Integer commision) {
		this.commision = commision;
	}

	public BillStatus getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(BillStatus billStatus) {
		this.billStatus = billStatus;
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

	public ProductOrderStatus getSoStatus() {
		return soStatus;
	}

	public void setSoStatus(ProductOrderStatus poStatus) {
		this.soStatus = poStatus;
	}

	public String getSoCode() {
		return soCode;
	}

	public void setSoCode(String soCode) {
		this.soCode = soCode;
	}

	public String getPoId() {
		return poId;
	}

	public void setPoId(String soId) {
		this.poId = soId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setExpired(Boolean expired) {
		this.expired = expired;
	}

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public Boolean getExpired() {
		return expired;
	}

	public Boolean getNeedNotice() {
		return needNotice;
	}

	public Boolean getSmsInstallNoticed() {
		return smsInstallNoticed;
	}

	public void setSmsInstallNoticed(Boolean smsInstallNoticed) {
		this.smsInstallNoticed = smsInstallNoticed;
	}

	public String getSplitRemark() {
		return splitRemark;
	}

	public void setSplitRemark(String splitRemark) {
		this.splitRemark = splitRemark;
	}

	public String getMfcId() {
		return mfcId;
	}

	public void setMfcId(String mfcId) {
		this.mfcId = mfcId;
	}

	public String getSpUserId() {
		return spUserId;
	}

	public void setSpUserId(String spUserId) {
		this.spUserId = spUserId;
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

	public Date getCloseDate() {
    	return closeDate;
    }

	public void setCloseDate(Date closeDate) {
    	this.closeDate = closeDate;
    }

	public Boolean getIsGoodsArrived() {
    	return isGoodsArrived;
    }

	public void setIsGoodsArrived(Boolean isGoodsArrived) {
    	this.isGoodsArrived = isGoodsArrived;
    }

	public String getRejectRemark() {
    	return rejectRemark;
    }

	public void setRejectRemark(String rejectRemark) {
    	this.rejectRemark = rejectRemark;
    }

	public String getRejectReason() {
    	return rejectReason;
    }

	public void setRejectReason(String rejectReason) {
    	this.rejectReason = rejectReason;
    }

	public String getMfcUserId() {
		return mfcUserId;
	}

	public void setMfcUserId(String mfcUserId) {
		this.mfcUserId = mfcUserId;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Date getUserConfirmDate() {
		return userConfirmDate;
	}

	public void setUserConfirmDate(Date userConfirmDate) {
		this.userConfirmDate = userConfirmDate;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getSpUserName() {
		return spUserName;
	}

	public void setSpUserName(String mfcName) {
		this.spUserName = mfcName;
	}

	public String getSpContactMobilePhone() {
    	return spContactMobilePhone;
    }

	public void setSpContactMobilePhone(String spContactMobilePhone) {
    	this.spContactMobilePhone = spContactMobilePhone;
    }

	public String getSpContactPerson() {
    	return spContactPerson;
    }

	public void setSpContactPerson(String spContactPerson) {
    	this.spContactPerson = spContactPerson;
    }

	public String getSpBillId() {
		return spBillId;
	}

	public void setSpBillId(String billId) {
		this.spBillId = billId;
	}

	public Boolean getSpNeedBill() {
		return spNeedBill;
	}

	public void setSpNeedBill(Boolean needBill) {
		this.spNeedBill = needBill;
	}

	public Boolean getMfcNeedBill() {
    	return mfcNeedBill;
    }

	public void setMfcNeedBill(Boolean mfcNeedBill) {
    	this.mfcNeedBill = mfcNeedBill;
    }

	public String getMfcBillId() {
    	return mfcBillId;
    }

	public void setMfcBillId(String mfcBillId) {
    	this.mfcBillId = mfcBillId;
    }

	public Date getAcceptedDate() {
    	return acceptedDate;
    }

	public void setAcceptedDate(Date acceptedDate) {
    	this.acceptedDate = acceptedDate;
    }

	public Date getAssignDate() {
    	return assignDate;
    }

	public void setAssignDate(Date assignDate) {
    	this.assignDate = assignDate;
    }

	public Date getCommentDate() {
    	return commentDate;
    }

	public void setCommentDate(Date commentDate) {
    	this.commentDate = commentDate;
    }

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
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

	public Date getRejectDate() {
    	return rejectDate;
    }

	public void setRejectDate(Date rejectDate) {
    	this.rejectDate = rejectDate;
    }

	public Date getTerminateDate() {
    	return terminateDate;
    }

	public void setTerminateDate(Date terminateDate) {
    	this.terminateDate = terminateDate;
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

	public Date getSpChangeDate() {
    	return spChangeDate;
    }

	public void setSpChangeDate(Date spChangeDate) {
    	this.spChangeDate = spChangeDate;
    }
	
	
	

}
