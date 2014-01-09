package com.zcyservice.service;

import java.util.List;
import java.util.Map;

import com.zcy.bean.EntityResults;
import com.zcyservice.bean.Archive;
import com.zcyservice.bean.vo.ArchiveTree;
import com.zcyservice.bean.vo.SearchVo;

public interface IArchiveService {

	
	public void scanArchines();

	public EntityResults<Archive> listArchives(SearchVo vo);

	public Map<String, Object> listArchiveFiles(Archive archive);
	
	
}
