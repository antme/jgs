package com.zcyservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zcy.annotation.LoginRequired;
import com.zcy.annotation.Permission;
import com.zcy.controller.AbstractController;
import com.zcyservice.bean.SiteMessage;
import com.zcyservice.bean.SiteMessageUser;
import com.zcyservice.bean.vo.AddSiteMessageVO;
import com.zcyservice.bean.vo.IDS;
import com.zcyservice.bean.vo.SiteMessageSearchUsersVO;
import com.zcyservice.service.ISiteMessageService;
import com.zcyservice.util.PermissionConstants;

@Controller
@RequestMapping("/ecs/sitemessage")
@Permission()
@LoginRequired()
public class SiteMessageController extends AbstractController {

	@Autowired
	private ISiteMessageService siteMessageService;
	
	@RequestMapping("/add.do")
	@Permission(groupName = PermissionConstants.ADM_SITE_MSG_MANAGE, permissionID = PermissionConstants.ADM_SITE_MSG_MANAGE)
	public void addSiteMessage(HttpServletRequest request, HttpServletResponse response) {
//		SiteMessage message = (SiteMessage) parserJsonParameters(request, false, SiteMessage.class);
		AddSiteMessageVO vo = (AddSiteMessageVO)  parserJsonParameters(request, false, AddSiteMessageVO.class);
		siteMessageService.addSiteMessage(vo);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/list.do")
	public void listSiteMessages(HttpServletRequest request, HttpServletResponse response) {
		responseWithDataPagnation(siteMessageService.listSiteMessage(), request, response);
	}
	
	@RequestMapping("/admin/list.do")
	@Permission(groupName = PermissionConstants.ADM_SITE_MSG_MANAGE, permissionID = PermissionConstants.ADM_SITE_MSG_MANAGE)
	public void listSiteMessagesForAdmin(HttpServletRequest request, HttpServletResponse response) {
		responseWithDataPagnation(siteMessageService.listSiteMessagesForAdmin(), request, response);
	}
	
	@RequestMapping("/searchusers.do")
	public void searchUsers(HttpServletRequest request, HttpServletResponse response) {
		SiteMessageSearchUsersVO vo = (SiteMessageSearchUsersVO) parserJsonParameters(request,true, SiteMessageSearchUsersVO.class);
		responseWithListData(siteMessageService.searchUsers(vo), request, response);
	}
	
	@RequestMapping("/messcount.do")
	public void getMyMessageCount(HttpServletRequest request, HttpServletResponse response) {
		responseWithData(siteMessageService.getCurrentUserMessageCount(), request, response);
	}
	
	@RequestMapping("/del.do")
	public void delSiteMessage(HttpServletRequest request, HttpServletResponse response) {
		IDS ids = (IDS) parserJsonParameters(request, false, IDS.class);
		siteMessageService.deleteSMByUser(ids);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/detail.do")
	public void loadSiteMessageDetail(HttpServletRequest request, HttpServletResponse response) {
		SiteMessageUser smu = (SiteMessageUser) parserJsonParameters(request, true, SiteMessageUser.class);
		responseWithEntity(siteMessageService.loadSiteMessageDetail(smu), request, response);
	}
	
	@RequestMapping("/admindetail.do")
	@Permission(groupName = PermissionConstants.ADM_SITE_MSG_MANAGE, permissionID = PermissionConstants.ADM_SITE_MSG_MANAGE)
	public void loadSiteMessageDetailForAdmin(HttpServletRequest request, HttpServletResponse response) {
		SiteMessageUser smu = (SiteMessageUser) parserJsonParameters(request, true, SiteMessageUser.class);
		responseWithEntity(siteMessageService.loadSiteMessageDetailForAdmin(smu), request, response);
	}


}
