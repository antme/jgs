package com.jgsservice.service;

import java.util.List;

import com.jgs.bean.EntityResults;
import com.jgs.dbhelper.DataBaseQueryBuilder;
import com.jgsservice.bean.Complaint;
import com.jgsservice.bean.ComplaintType;
import com.jgsservice.bean.vo.ComplaintSearchVo;
import com.jgsservice.bean.vo.SearchVo;
import com.jgsservice.bean.vo.ServiceOrderForComplaintVO;

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
