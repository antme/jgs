package com.zcyservice.schedule;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.zcy.dao.IQueryDao;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcy.dbhelper.DataBaseQueryOpertion;
import com.zcyservice.bean.Archive;
import com.zcyservice.bean.Archive.ProcessStatus;
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
		updateArchiveIsNew();
		logger.info("schedule run for zcy end");
	}

	public void updateArchiveIsNew() {

		Calendar c = Calendar.getInstance();
		c.add(Calendar.WEEK_OF_YEAR, -2);

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Archive.TABLE_NAME);
		query.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, Archive.CREATED_ON, c.getTime());
		query.and(DataBaseQueryOpertion.IS_FALSE, Archive.IS_NEW);
		query.and(DataBaseQueryOpertion.NOT_IN, Archive.ACHIVE_PROCESS_STATUS, new String[] { ProcessStatus.NEW.toString(), ProcessStatus.REJECTED.toString() });

		List<Archive> list = this.dao.listByQuery(query, Archive.class);
		for (Archive archive : list) {

			archive.setIsNew(false);
			this.dao.updateById(archive);
		}

	}

	public void scanArchiveDocuments() {
		archiveService.scanArchines();
	}

}
