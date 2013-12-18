package com.jgsservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jgs.annotation.LoginRequired;
import com.jgs.annotation.Permission;
import com.jgs.controller.AbstractController;
import com.jgs.exception.ResponseException;
import com.jgsservice.bean.Manufacturer;
import com.jgsservice.bean.ManufacturerTemp;
import com.jgsservice.bean.ServiceProvider;
import com.jgsservice.bean.ServiceProviderTemp;
import com.jgsservice.bean.vo.SearchVo;
import com.jgsservice.service.IManufacturerService;
import com.jgsservice.util.PermissionConstants;

@Controller
@RequestMapping("/ecs/mfc")
@Permission()
@LoginRequired()
public class ManufacturerController extends AbstractController {

	@Autowired
	private IManufacturerService mfs;

	@RequestMapping("/reg.do")
	@LoginRequired(required = false)
	public void registerMfc(HttpServletRequest request, HttpServletResponse response) {
		Manufacturer mfc = (Manufacturer) parserJsonParameters(request, false, Manufacturer.class);
		mfs.addManufacturer(mfc);
		clearLoginSession(request, response);
		responseWithData(null, request, response);
	}

	@RequestMapping("/get.do")
	public void getMfcInfo(HttpServletRequest request, HttpServletResponse response) {
		Manufacturer mfc = (Manufacturer) parserJsonParameters(request, true, Manufacturer.class);
		responseWithEntity(mfs.getMfcInfoWithMFCTempId(mfc), request, response);
	}

	@RequestMapping("/detail.do")
	public void loadMfcDetailInfo(HttpServletRequest request, HttpServletResponse response) {
		Manufacturer mfc = (Manufacturer) parserJsonParameters(request, true, Manufacturer.class);
		responseWithEntity(mfs.loadManufacturerDetailInfo(mfc), request, response);
	}
	
	@RequestMapping("/removeTemp.do")
	@LoginRequired
	public void removeMyMfcTemp(HttpServletRequest request, HttpServletResponse response) {
		ManufacturerTemp mfct = (ManufacturerTemp) parserJsonParameters(request, true, ManufacturerTemp.class);
		mfs.removeMFCTemp(mfct);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/admin/approve/get.do")
	public void getApproveMfcInfo(HttpServletRequest request, HttpServletResponse response) {
		Manufacturer mfc = (Manufacturer) parserJsonParameters(request, true, Manufacturer.class);
		responseWithEntity(mfs.loadApproveManufacturerInfo(mfc), request, response);
	}
	
	@RequestMapping("/update.do")
	public void updateMfcInfo(HttpServletRequest request, HttpServletResponse response) {
		ManufacturerTemp mfc = (ManufacturerTemp) parserJsonParameters(request, false, ManufacturerTemp.class);
		mfs.updateMfcInfo(mfc);
		responseWithData(null, request, response);
	}
	
	
	@RequestMapping("/listnew.do")
	@Permission(groupName = PermissionConstants.ADM_USER_APPROVE, permissionID = PermissionConstants.ADM_USER_APPROVE)
	public void listNewMfc(HttpServletRequest request, HttpServletResponse response) {
		responseWithDataPagnation(mfs.listNewManufacturers(), request, response);
	}
	
	@RequestMapping("/listupdate.do")
	@Permission(groupName = PermissionConstants.ADM_USER_APPROVE, permissionID = PermissionConstants.ADM_USER_APPROVE)
	public void listUpdatedMfc(HttpServletRequest request, HttpServletResponse response) {
		responseWithDataPagnation(mfs.listUpdatedManufacturers(), request, response);
	}
	
	@RequestMapping("/approve.do")
	@Permission(groupName = PermissionConstants.ADM_USER_APPROVE, permissionID = PermissionConstants.ADM_USER_APPROVE)
	public void approveMfc(HttpServletRequest request, HttpServletResponse response) {
		Manufacturer mfc = (Manufacturer) parserJsonParameters(request, false, Manufacturer.class);

		mfs.approveManufacturer(mfc);
		responseWithEntity(null, request, response);
	}

	@RequestMapping("/reject.do")
	@Permission(groupName = PermissionConstants.ADM_USER_APPROVE, permissionID = PermissionConstants.ADM_USER_APPROVE)
	public void rejectMfc(HttpServletRequest request, HttpServletResponse response) {
		Manufacturer mfc = (Manufacturer) parserJsonParameters(request, false, Manufacturer.class);
		mfs.rejectManufacturer(mfc);
		responseWithEntity(null, request, response);
	}

	@RequestMapping("/manage.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listForAdmin(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
		responseWithDataPagnation(mfs.listForAdmin(vo), request, response);
	}

	@RequestMapping("/edit.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void editMfc(HttpServletRequest request, HttpServletResponse response) {
		Manufacturer mfc = (Manufacturer) parserJsonParameters(request, false, Manufacturer.class);
		mfs.addManufacturerByAdmin(mfc);
		responseWithEntity(null, request, response);
	}
	
	@RequestMapping("/add.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void addMfc(HttpServletRequest request, HttpServletResponse response) {
		Manufacturer mfc = (Manufacturer) parserJsonParameters(request, false, Manufacturer.class);
		mfs.addManufacturerByAdmin(mfc);
		responseWithEntity(null, request, response);
	}
	
	@RequestMapping("/select.do")
	public void listForAdminSelect(HttpServletRequest request, HttpServletResponse response) {
		responseWithListData(mfs.listForAdminSelect(), request, response);
	}
	
}
