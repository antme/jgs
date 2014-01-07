package com.ecomm.dao;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.zcy.dao.IQueryDao;
import com.zcy.dao.QueryDaoImpl;
import com.zcy.lucene.IndexFiles;
import com.zcy.lucene.SearchFiles;
import com.zcyservice.service.IArchiveService;
import com.zcyservice.service.IUserService;
import com.zcyservice.service.ISystemService;
import com.zcyservice.service.impl.ArchiveServiceImpl;
import com.zcyservice.service.impl.UserServiceImpl;
import com.zcyservice.service.impl.SystemServiceImpl;

public class BaseTestCase extends TestCase {
	private static Logger logger = LogManager.getLogger(BaseTestCase.class);

	protected static ApplicationContext ac;

	public IQueryDao dao;

	public IUserService userService;

	public ISystemService sys;

	public IArchiveService archiveService;

	public IQueryDao getDao() {
		return dao;
	}

	public void setDao(IQueryDao dao) {
		this.dao = dao;
	}

	public BaseTestCase() {

		if (ac == null) {
			ac = new FileSystemXmlApplicationContext("/src/main/WEB-INF/application.xml");
		}
		dao = ac.getBean(QueryDaoImpl.class);

		userService = ac.getBean(UserServiceImpl.class);

		sys = ac.getBean(SystemServiceImpl.class);

		archiveService = ac.getBean(ArchiveServiceImpl.class);

	}

	public void testEmpty() throws IOException, InterruptedException {

//		 archiveService.scanArchines();
		new IndexFiles().runIndex();
		try {
	        new SearchFiles().search("money", true);
        } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }

	}

}
