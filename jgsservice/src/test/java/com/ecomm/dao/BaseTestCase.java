package com.ecomm.dao;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.jgs.dao.IQueryDao;
import com.jgs.dao.QueryDaoImpl;
import com.jgsservice.service.ICategoryService;
import com.jgsservice.service.IComplaintService;
import com.jgsservice.service.IECommerceUserService;
import com.jgsservice.service.ILocationService;
import com.jgsservice.service.IOrderService;
import com.jgsservice.service.ISProviderService;
import com.jgsservice.service.ISmsService;
import com.jgsservice.service.ISystemService;
import com.jgsservice.service.impl.CategoryServiceImpl;
import com.jgsservice.service.impl.ComplaintServiceImpl;
import com.jgsservice.service.impl.ECommerceUserServiceImpl;
import com.jgsservice.service.impl.LocationServiceImpl;
import com.jgsservice.service.impl.OrderServiceImpl;
import com.jgsservice.service.impl.SProviderServiceImpl;
import com.jgsservice.service.impl.SmsServiceImpl;
import com.jgsservice.service.impl.SystemServiceImpl;

public class BaseTestCase extends TestCase {
	private static Logger logger = LogManager.getLogger(BaseTestCase.class);

	protected static ApplicationContext ac;

	protected ICategoryService categoryService ;
	
	public IQueryDao dao;

	public IECommerceUserService userService;

	public IOrderService pos;
	
	public ISmsService smsService;
	
	public ILocationService ls;

	public ISystemService sys;
	
	public ICategoryService cates;
	
	public ISProviderService isp;
	
	public IComplaintService cmpService;
	
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
		pos = ac.getBean(OrderServiceImpl.class);
		categoryService = ac.getBean(CategoryServiceImpl.class);
		smsService = ac.getBean(SmsServiceImpl.class);
		ls = ac.getBean(LocationServiceImpl.class);
		cates = ac.getBean(CategoryServiceImpl.class);
		
		isp= ac.getBean(SProviderServiceImpl.class);
		
		sys = ac.getBean(SystemServiceImpl.class);
		
		cmpService = ac.getBean(ComplaintServiceImpl.class);
		

	}

	public void testEmpty() throws IOException, InterruptedException {
//		System.out.println(smsService.getSmsRemainingMongey(null));
		
	}

}
