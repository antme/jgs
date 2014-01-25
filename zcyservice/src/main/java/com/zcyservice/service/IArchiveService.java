package com.zcyservice.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zcy.bean.BaseEntity;
import com.zcy.bean.EntityResults;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcyservice.bean.Archive;
import com.zcyservice.bean.ArchiveBorrowing;
import com.zcyservice.bean.vo.SearchVo;

public interface IArchiveService {

	
	public void scanArchines();

	public EntityResults<Archive> listArchives(Archive archive);
	
	public EntityResults<Archive> listPubArchives(Archive archive);

	public Map<String, Object> listArchiveFiles(Archive archive);

	public Archive addArchive(Archive archive);

	public EntityResults<Archive> listNeddApproveArchives(Archive archive);
	
	public DataBaseQueryBuilder getNewApproveArchiveBuilder();
	
	public EntityResults<Archive> listNeedDestoryApproveArchives(Archive archive);
	
	public DataBaseQueryBuilder getNeedDestroyApproveBuilder();
	
	public DataBaseQueryBuilder getNeedRejectBuilder();

	public void approveArchive(Archive archive);

	public void rejectArchive(Archive archive);

	public void addArchiveBorrowRecord(ArchiveBorrowing archive);

	public Archive getArchive(Archive archive);

	public EntityResults<ArchiveBorrowing> listArchiveBorrowRecord(Archive archive);

	public BaseEntity getArchiveBorrowRecord(ArchiveBorrowing archive);

	public void destroyArchive(Archive archive);
	
	public void getDocumentInfo(String absolutePath, Archive archive);

	public EntityResults<Archive> countArchive(SearchVo searchvo);
	
	
	public Map<String, Object> listArchiveReportByYear();

	public String downloadArchiveFile(Archive archive, HttpServletRequest request, HttpServletResponse response);

	public void approveDestroyArchive(Archive archive);
	
	public void rejectDestoryArchive(Archive archive);

	public EntityResults<Archive> listNewArchives(Archive archive);
	
	
	
	
	
}
