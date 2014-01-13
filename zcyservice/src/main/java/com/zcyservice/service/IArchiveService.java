package com.zcyservice.service;

import java.util.Map;

import com.zcy.bean.EntityResults;
import com.zcyservice.bean.Archive;
import com.zcyservice.bean.ArchiveBorrowing;
import com.zcyservice.bean.vo.SearchVo;

public interface IArchiveService {

	
	public void scanArchines();

	public EntityResults<Archive> listArchives(SearchVo vo);

	public Map<String, Object> listArchiveFiles(Archive archive);

	public void addArchive(Archive archive);

	public EntityResults<Archive> listNewArchives(SearchVo vo);

	public void approveArchive(Archive archive);

	public void rejectArchive(Archive archive);

	public void addArchiveBorrowRecord(ArchiveBorrowing archive);

	public Archive getArchive(Archive archive);
	
	
}
