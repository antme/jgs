package com.zcyservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zcy.annotation.LoginRequired;
import com.zcy.annotation.Permission;
import com.zcy.controller.AbstractController;
import com.zcyservice.bean.Archive;
import com.zcyservice.bean.vo.SearchVo;
import com.zcyservice.service.IArchiveService;
import com.zcyservice.util.PermissionConstants;

@Controller
@RequestMapping("/ecs/archive")
@Permission()
@LoginRequired()
public class ArchiveController extends AbstractController {

	@Autowired
	private IArchiveService archiveService;

	@RequestMapping("/listArchives.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listArchives(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, true, SearchVo.class);
		responseWithDataPagnation(archiveService.listArchives(vo), request, response);
	}
	
	@RequestMapping("/files.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listArchiveFiles(HttpServletRequest request, HttpServletResponse response) {
		Archive archive = (Archive) parserJsonParameters(request, true, Archive.class);
		responseWithData(archiveService.listArchiveFiles(archive), request, response);
	}
	

}
