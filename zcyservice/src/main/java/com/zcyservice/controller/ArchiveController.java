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
import com.zcyservice.bean.ArchiveBorrowing;
import com.zcyservice.bean.ArchiveFile;
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
	
	
	@RequestMapping("/listNew.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listNeddApproveArchives(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, true, SearchVo.class);
		responseWithDataPagnation(archiveService.listNeddApproveArchives(vo), request, response);
	}

	@RequestMapping("/files.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listArchiveFiles(HttpServletRequest request, HttpServletResponse response) {
		Archive archive = (Archive) parserJsonParameters(request, true, Archive.class);
		responseWithData(archiveService.listArchiveFiles(archive), request, response);
	}

	@RequestMapping("/add.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void addArchive(HttpServletRequest request, HttpServletResponse response) {
		Archive archive = (Archive) parserJsonParameters(request, true, Archive.class);
		archiveService.addArchive(archive);
		responseWithData(null, request, response);
	}
	
	
	@RequestMapping("/get.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void getArchive(HttpServletRequest request, HttpServletResponse response) {
		Archive archive = (Archive) parserJsonParameters(request, true, Archive.class);
		responseWithEntity(archiveService.getArchive(archive), request, response);
	}
	
	@RequestMapping("/destroy.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void destroyArchive(HttpServletRequest request, HttpServletResponse response) {
		Archive archive = (Archive) parserJsonParameters(request, true, Archive.class);
		archiveService.destroyArchive(archive);
		responseWithData(null, request, response);

	}
	
	
	@RequestMapping("/borrow/add.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void addArchiveBorrowRecord(HttpServletRequest request, HttpServletResponse response) {
		ArchiveBorrowing archive = (ArchiveBorrowing) parserJsonParameters(request, true, ArchiveBorrowing.class);
		archiveService.addArchiveBorrowRecord(archive);
		responseWithData(null, request, response);
	}
	
	
	@RequestMapping("/borrow/get.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void getArchiveBorrowRecord(HttpServletRequest request, HttpServletResponse response) {
		ArchiveBorrowing archive = (ArchiveBorrowing) parserJsonParameters(request, true, ArchiveBorrowing.class);
		
		responseWithEntity(archiveService.getArchiveBorrowRecord(archive), request, response);
	}
	
	@RequestMapping("/borrow/list.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listArchiveBorrowRecord(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, true, SearchVo.class);
		responseWithDataPagnation(archiveService.listArchiveBorrowRecord(vo), request, response);
	}


	
	@RequestMapping("/approve.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void approveArchive(HttpServletRequest request, HttpServletResponse response) {
		Archive archive = (Archive) parserJsonParameters(request, true, Archive.class);
		archiveService.approveArchive(archive);
		responseWithData(null, request, response);
	}
	
	
	
	@RequestMapping("/reject.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void rejectArchive(HttpServletRequest request, HttpServletResponse response) {
		Archive archive = (Archive) parserJsonParameters(request, true, Archive.class);
		archiveService.rejectArchive(archive);
		responseWithData(null, request, response);
	}
	
	
	
	@RequestMapping("/upload.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void uploadArchiveFile(HttpServletRequest request, HttpServletResponse response) {
		ArchiveFile archiveFile = (ArchiveFile) parserJsonParameters(request, true, ArchiveFile.class);
		String path = uploadFile(request, archiveFile.getArchiveUploadKey());
		responseWithKeyValue("data", path, request, response);
	}

}
