package com.zcyservice.service;

import java.util.List;

import com.zcy.bean.EntityResults;
import com.zcyservice.bean.Archive;
import com.zcyservice.bean.vo.ArvhiveTree;
import com.zcyservice.bean.vo.SearchVo;

public interface IArchiveService {

	
	public void scanArchines();

	public EntityResults<Archive> listArchives(SearchVo vo);

	public List<ArvhiveTree> listArchiveFiles(SearchVo vo);
	
	
}
