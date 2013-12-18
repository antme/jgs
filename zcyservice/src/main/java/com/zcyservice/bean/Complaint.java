package com.zcyservice.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.zcy.bean.BaseEntity;

@Table(name = Complaint.TABLE_NAME)
public class Complaint extends BaseEntity{

	public static final String CLOSE_DATE = "closeDate";

	public static final String TABLE_NAME = "Complaint";
	
	public static final String COMP_TYPE_ID = "compTypeId";
	public static final String COMP_STATUS = "compStatus";
	public static final String COMP_REMARK = "compRemark";
	public static final String SERVICE_ORDER_ID = "serviceOrderId";
	public static final String SOLVER_ID = "solverId";
	

	@Column(name = COMP_TYPE_ID)
	@Expose
	public String compTypeId;
	
	@Column(name = COMP_STATUS)
	@Expose
	public ComplaintStaus compStatus;
	
	@Column(name = COMP_REMARK)
	@Expose
	public String compRemark;
	
	@Column(name = SERVICE_ORDER_ID)
	@Expose
	public String serviceOrderId;
	
	@Column(name = SOLVER_ID)
	@Expose
	public String solverId;
	
	
	@Column(name = CLOSE_DATE)
	@Expose
	public Date closeDate;

	public String getCompTypeId() {
		return compTypeId;
	}

	public void setCompTypeId(String compTypeId) {
		this.compTypeId = compTypeId;
	}

	public ComplaintStaus getCompStatus() {
		return compStatus;
	}

	public void setCompStatus(ComplaintStaus compStatus) {
		this.compStatus = compStatus;
	}

	public String getCompRemark() {
		return compRemark;
	}

	public void setCompRemark(String compRemark) {
		this.compRemark = compRemark;
	}

	public String getServiceOrderId() {
		return serviceOrderId;
	}

	public void setServiceOrderId(String serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}

	public String getSolverId() {
		return solverId;
	}

	public void setSolverId(String solverId) {
		this.solverId = solverId;
	}

	
	public Date getCloseDate() {
    	return closeDate;
    }

	public void setCloseDate(Date closeDate) {
    	this.closeDate = closeDate;
    }


	public enum ComplaintStaus{
		NEW, PROCESSING, CLOSED, DONE
	}
	
}
