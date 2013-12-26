package com.ecomm.dao;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.zcy.dao.IQueryDao;
import com.zcy.dao.QueryDaoImpl;
import com.zcyservice.service.IECommerceUserService;
import com.zcyservice.service.ISystemService;
import com.zcyservice.service.impl.ECommerceUserServiceImpl;
import com.zcyservice.service.impl.SystemServiceImpl;

public class BaseTestCase extends TestCase {
	private static Logger logger = LogManager.getLogger(BaseTestCase.class);

	protected static ApplicationContext ac;

	
	public IQueryDao dao;

	public IECommerceUserService userService;

	

	public ISystemService sys;
	
	
	
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

		userService = ac.getBean(ECommerceUserServiceImpl.class);
		
		
		sys = ac.getBean(SystemServiceImpl.class);
		
		

	}

	public void testEmpty() throws IOException, InterruptedException {
//		System.out.println(smsService.getSmsRemainingMongey(null));
		
	}

}
