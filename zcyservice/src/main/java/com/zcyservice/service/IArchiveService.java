package com.zcyservice.service;

import java.util.Map;

import com.zcy.bean.BaseEntity;
import com.zcy.bean.EntityResults;
import com.zcyservice.bean.Archive;
import com.zcyservice.bean.ArchiveBorrowing;
import com.zcyservice.bean.vo.SearchVo;

public interface IArchiveService {

	
	public void scanArchines();

	public EntityResults<Archive> listArchives(Archive archive);

	public Map<String, Object> listArchiveFiles(Archive archive);

	public Archive addArchive(Archive archive);

	public EntityResults<Archive> listNeddApproveArchives(Archive archive);
	
	public EntityResults<Archive> listNeedDestoryApproveArchives(Archive archive);

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
	
	
}
