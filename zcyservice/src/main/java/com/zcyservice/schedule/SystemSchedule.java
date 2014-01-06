package com.zcyservice.schedule;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.zcy.dao.IQueryDao;
import com.zcyservice.service.IArchiveService;
import com.zcyservice.service.IUserService;

public class SystemSchedule {
	public static final String DEFAULT_COMMENTS = "默认好评";

	private static Logger logger = LogManager.getLogger(SystemSchedule.class);

	@Autowired
	public IQueryDao dao;

	@Autowired
	public IUserService us;

	@Autowired
	public IArchiveService archiveService;

	public void run() {
		logger.info("schedule run for zcy start");
		scanArchiveDocuments();

		logger.info("schedule run for zcy end");
	}

	public void scanArchiveDocuments() {
		archiveService.scanArchines();
	}

}
