package com.zcyservice.service;

import java.util.Date;

import com.zcy.service.AbstractService;
import com.zcy.util.DateUtil;
import com.zcyservice.bean.vo.SearchVo;

public abstract class AbstractArchiveService extends AbstractService {




	public Date getQueryEndDate(SearchVo search) {
		Date endDate = search.getEndDate();
		if (DateUtil.getDateStringTime(endDate).endsWith("00:00:00")) {
			endDate = new Date(endDate.getTime() + 86400000L - 1L);
		}
		return endDate;
	}

}
