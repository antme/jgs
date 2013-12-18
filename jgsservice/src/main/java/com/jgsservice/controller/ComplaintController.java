package com.jgsservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jgs.annotation.LoginRequired;
import com.jgs.annotation.Permission;
import com.jgs.controller.AbstractController;
import com.jgsservice.bean.Complaint;
import com.jgsservice.bean.ComplaintType;
import com.jgsservice.bean.vo.ComplaintSearchVo;
import com.jgsservice.bean.vo.SearchVo;
import com.jgsservice.service.IComplaintService;
import com.jgsservice.util.PermissionConstants;

@Controller
@RequestMapping("/ecs/complaint")
@Permission()
@LoginRequired()
public class ComplaintController extends AbstractController {

	@Autowired
	private IComplaintService complaintService;

	@RequestMapping("/add.do")
	@Permission(groupName = PermissionConstants.ADM_COMPLAINT_MANAGE, permissionID = PermissionConstants.ADM_COMPLAINT_MANAGE)
	public void addNewComplaint(HttpServletRequest request, HttpServletResponse response) {
		Complaint cmp = (Complaint) parserJsonParameters(request,true, Complaint.class);
		complaintService.addComplaint(cmp);
		responseWithData(null, request, response);
	}

	@RequestMapping("/oper.do")
	@Permission(groupName = PermissionConstants.ADM_COMPLAINT_MANAGE, permissionID = PermissionConstants.ADM_COMPLAINT_MANAGE)
	public void operComplaint(HttpServletRequest request, HttpServletResponse response) {
		Complaint cmp = (Complaint) parserJsonParameters(request,true, Complaint.class);
		complaintService.operComplaint(cmp);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/list.do")
	public void listComplaints(HttpServletRequest request, HttpServletResponse response) {
		ComplaintSearchVo svo = (ComplaintSearchVo) parserJsonParameters(request,true, ComplaintSearchVo.class);
		responseWithDataPagnation(complaintService.listComplaint(svo), request, response);
	}
	
	@RequestMapping("/admin/listAll.do")
	public void listAllComplaintOrdersForAdmin(HttpServletRequest request, HttpServletResponse response) {
		SearchVo svo = (SearchVo) parserJsonParameters(request,true, SearchVo.class);
		responseWithDataPagnation(complaintService.listAllComplaintOrdersForAdmin(svo), request, response);
	}
	
	@RequestMapping("/complaintTypes.do")
	public void listComplaintTypes(HttpServletRequest request, HttpServletResponse response) {
		responseWithListData(complaintService.listAllComplaintTypes(), request, response);
	}
	
	@RequestMapping("/complaintType/list.do")
	public void listPageComplaintTypes(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request,true, SearchVo.class);
		responseWithDataPagnation(complaintService.listPageComplaintTypes(), request, response);
	}

	@RequestMapping("/complaintType/add.do")
	@Permission(groupName = PermissionConstants.ADM_COMPLAINT_TYPE_MANAGE, permissionID = PermissionConstants.ADM_COMPLAINT_TYPE_MANAGE)
	public void addNewComplaintType(HttpServletRequest request, HttpServletResponse response) {
		ComplaintType cmpType = (ComplaintType) parserJsonParameters(request,true, ComplaintType.class);
		complaintService.addComplaintType(cmpType);
		responseWithData(null, request, response);
	}

	@RequestMapping("/complaintType/del.do")
	@Permission(groupName = PermissionConstants.ADM_COMPLAINT_TYPE_MANAGE, permissionID = PermissionConstants.ADM_COMPLAINT_TYPE_MANAGE)
	public void delComplaintType(HttpServletRequest request, HttpServletResponse response) {
		ComplaintType cmpType = (ComplaintType) parserJsonParameters(request,true, ComplaintType.class);
		complaintService.delComplaintType(cmpType);
		responseWithData(null, request, response);
	}
}
