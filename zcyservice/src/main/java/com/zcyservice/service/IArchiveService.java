package com.zcyservice.service;

import com.zcy.bean.EntityResults;
import com.zcyservice.bean.Archive;
import com.zcyservice.bean.vo.SearchVo;

public interface IArchiveService {

	
	public void scanArchines();

	public EntityResults<Archive> listArchives(SearchVo vo);
	
	
}
