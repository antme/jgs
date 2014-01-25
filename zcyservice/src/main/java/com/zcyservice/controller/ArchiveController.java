package com.zcyservice.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zcy.annotation.LoginRequired;
import com.zcy.annotation.Permission;
import com.zcy.controller.AbstractController;
import com.zcy.util.EcUtil;
import com.zcyservice.bean.Archive;
import com.zcyservice.bean.ArchiveBorrowing;
import com.zcyservice.bean.ArchiveFile;
import com.zcyservice.bean.vo.SearchVo;
import com.zcyservice.service.IArchiveService;
import com.zcyservice.util.PermissionConstants;
import com.zcyservice.util.ZcyUtil;

@Controller
@RequestMapping("/ecs/archive")
@Permission()
@LoginRequired()
public class ArchiveController extends AbstractController {

	@Autowired
	private IArchiveService archiveService;

	@RequestMapping("/listArchives.do")
	@Permission(groupName = PermissionConstants.adm_archive_manage, permissionID = PermissionConstants.adm_archive_manage)
	public void listArchives(HttpServletRequest request, HttpServletResponse response) {
		Archive archive = (Archive) parserJsonParameters(request, true, Archive.class);
		responseWithDataPagnation(archiveService.listArchives(archive), request, response);
	}

	@RequestMapping("/listPubArchives.do")
	@Permission(groupName = PermissionConstants.adm_archive_query, permissionID = PermissionConstants.adm_archive_query)
	public void listPubArchives(HttpServletRequest request, HttpServletResponse response) {
		Archive archive = (Archive) parserJsonParameters(request, true, Archive.class);
		responseWithDataPagnation(archiveService.listPubArchives(archive), request, response);
	}

	@RequestMapping("/listNew.do")
	@Permission(groupName = PermissionConstants.adm_archive_approve, permissionID = PermissionConstants.adm_archive_approve)
	public void listNeddApproveArchives(HttpServletRequest request, HttpServletResponse response) {
		Archive archive = (Archive) parserJsonParameters(request, true, Archive.class);
		responseWithDataPagnation(archiveService.listNeddApproveArchives(archive), request, response);
	}

	@RequestMapping("/listdestroy.do")
	@Permission(groupName = PermissionConstants.adm_archive_destory_approve, permissionID = PermissionConstants.adm_archive_destory_approve)
	public void listNeedDestoryApproveArchives(HttpServletRequest request, HttpServletResponse response) {
		Archive archive = (Archive) parserJsonParameters(request, true, Archive.class);
		responseWithDataPagnation(archiveService.listNeedDestoryApproveArchives(archive), request, response);
	}

	@RequestMapping("/files.do")
	@Permission(groupName = PermissionConstants.adm_archive_query, permissionID = PermissionConstants.adm_archive_query)
	public void listArchiveFiles(HttpServletRequest request, HttpServletResponse response) {
		Archive archive = (Archive) parserJsonParameters(request, true, Archive.class);
		responseWithData(archiveService.listArchiveFiles(archive), request, response);
	}

	@RequestMapping("/add.do")
	@Permission(groupName = PermissionConstants.adm_archive_manage, permissionID = PermissionConstants.adm_archive_manage)
	public void addArchive(HttpServletRequest request, HttpServletResponse response) {
		Archive archive = (Archive) parserJsonParameters(request, true, Archive.class);
		responseWithEntity(archiveService.addArchive(archive), request, response);
	}

	@RequestMapping("/count.do")
	public void countArchive(HttpServletRequest request, HttpServletResponse response) {
		SearchVo searchvo = (SearchVo) parserJsonParameters(request, true, SearchVo.class);
		responseWithDataPagnation(archiveService.countArchive(searchvo), request, response);
	}

	@RequestMapping("/indexcount.do")
	public void countArchiveByYear(HttpServletRequest request, HttpServletResponse response) {
		responseWithData(archiveService.listArchiveReportByYear(), request, response);
	}

	@RequestMapping("/get.do")
	public void getArchive(HttpServletRequest request, HttpServletResponse response) {
		Archive archive = (Archive) parserJsonParameters(request, true, Archive.class);
		responseWithEntity(archiveService.getArchive(archive), request, response);
	}

	@RequestMapping("/destroy.do")
	@Permission(groupName = PermissionConstants.adm_archive_manage, permissionID = PermissionConstants.adm_archive_manage)
	public void destroyArchive(HttpServletRequest request, HttpServletResponse response) {
		Archive archive = (Archive) parserJsonParameters(request, true, Archive.class);
		archiveService.destroyArchive(archive);
		responseWithData(null, request, response);

	}

	@RequestMapping("/borrow/add.do")
	public void addArchiveBorrowRecord(HttpServletRequest request, HttpServletResponse response) {
		ArchiveBorrowing archive = (ArchiveBorrowing) parserJsonParameters(request, true, ArchiveBorrowing.class);
		archiveService.addArchiveBorrowRecord(archive);
		responseWithData(null, request, response);
	}

	@RequestMapping("/borrow/get.do")
	public void getArchiveBorrowRecord(HttpServletRequest request, HttpServletResponse response) {
		ArchiveBorrowing archive = (ArchiveBorrowing) parserJsonParameters(request, true, ArchiveBorrowing.class);

		responseWithEntity(archiveService.getArchiveBorrowRecord(archive), request, response);
	}

	@RequestMapping("/borrow/list.do")
	public void listArchiveBorrowRecord(HttpServletRequest request, HttpServletResponse response) {
		Archive archive = (Archive) parserJsonParameters(request, true, Archive.class);
		responseWithDataPagnation(archiveService.listArchiveBorrowRecord(archive), request, response);
	}

	@RequestMapping("/approve.do")
	@Permission(groupName = PermissionConstants.adm_archive_approve, permissionID = PermissionConstants.adm_archive_approve)
	public void approveArchive(HttpServletRequest request, HttpServletResponse response) {
		Archive archive = (Archive) parserJsonParameters(request, true, Archive.class);
		archiveService.approveArchive(archive);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/destroy/approve.do")
	@Permission(groupName = PermissionConstants.adm_archive_approve, permissionID = PermissionConstants.adm_archive_approve)
	public void approveDestroyArchive(HttpServletRequest request, HttpServletResponse response) {
		Archive archive = (Archive) parserJsonParameters(request, true, Archive.class);
		archiveService.approveDestroyArchive(archive);
		responseWithData(null, request, response);
	}

	@RequestMapping("/reject.do")
	@Permission(groupName = PermissionConstants.adm_archive_approve, permissionID = PermissionConstants.adm_archive_approve)
	public void rejectArchive(HttpServletRequest request, HttpServletResponse response) {
		Archive archive = (Archive) parserJsonParameters(request, true, Archive.class);
		archiveService.rejectArchive(archive);
		responseWithData(null, request, response);
	}

	@RequestMapping("/upload.do")
	@Permission(groupName = PermissionConstants.adm_archive_manage, permissionID = PermissionConstants.adm_archive_manage)
	public void uploadArchiveFile(HttpServletRequest request, HttpServletResponse response) {

		ArchiveFile af = (ArchiveFile) parserJsonParameters(request, false, ArchiveFile.class);

		String relativeFilePath = UUID.randomUUID().toString();
		String fileName = uploadArchiveFile(request, ZcyUtil.getUploadPath() + File.separator + relativeFilePath);

		String fileN = ZcyUtil.getUploadPath() + File.separator + relativeFilePath + File.separator + fileName;
		Archive archive = new Archive();
		if (EcUtil.isValid(af.getArchiveUploadKey()) && af.getArchiveUploadKey().equalsIgnoreCase("first")) {
			archiveService.getDocumentInfo(fileN, archive);
		}
		archive.setFilePath(relativeFilePath + File.separator + fileName);

		responseWithEntity(archive, request, response);
	}

	@RequestMapping("/dowload.do")
	@Permission(groupName = PermissionConstants.adm_archive_download, permissionID = PermissionConstants.adm_archive_download)
	public void downloadArchiveFile(HttpServletRequest request, HttpServletResponse response) {

		Archive archive = (Archive) parserJsonParameters(request, true, Archive.class);

		String targetName = archiveService.downloadArchiveFile(archive, request, response);
		String fileName = ZcyUtil.getUploadPath() + File.separator + targetName;
		File downloadFile = new File(fileName);
		try {

			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(downloadFile));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename= " + URLEncoder.encode(targetName, "utf-8"));
			response.addHeader("Content-Length", "" + downloadFile.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
