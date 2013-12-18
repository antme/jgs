package com.zcyservice.service;

import java.util.List;

import com.zcy.bean.EntityResults;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcyservice.bean.Complaint;
import com.zcyservice.bean.ComplaintType;
import com.zcyservice.bean.vo.ComplaintSearchVo;
import com.zcyservice.bean.vo.SearchVo;
import com.zcyservice.bean.vo.ServiceOrderForComplaintVO;

public interface IComplaintService {
	
	public void addComplaint(Complaint complaint);
	
	public void operComplaint(Complaint complaint);
	
	public EntityResults<ServiceOrderForComplaintVO> listComplaint(ComplaintSearchVo svo);
	
	public List<ComplaintType> listAllComplaintTypes();
	
	public EntityResults<ComplaintType> listPageComplaintTypes();
	
	public void addComplaintType(ComplaintType complaintType);
	
	public void delComplaintType(ComplaintType complaintType);

	public EntityResults<ServiceOrderForComplaintVO> listAllComplaintOrdersForAdmin(SearchVo svo);
	
	public DataBaseQueryBuilder getComplaintOrderQuery(SearchVo svo, boolean isAdmin);
}
