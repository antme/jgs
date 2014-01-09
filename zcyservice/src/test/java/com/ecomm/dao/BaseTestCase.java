package com.ecomm.dao;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.zcy.dao.IQueryDao;
import com.zcy.dao.QueryDaoImpl;
import com.zcyservice.bean.Archive;
import com.zcyservice.service.IArchiveService;
import com.zcyservice.service.ISystemService;
import com.zcyservice.service.IUserService;
import com.zcyservice.service.impl.ArchiveServiceImpl;
import com.zcyservice.service.impl.SystemServiceImpl;
import com.zcyservice.service.impl.UserServiceImpl;

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
		archiveService.scanArchines();
		
//		Archive archive = new Archive();
//		archive.setId("53d4b469-c0e2-4a82-9abb-035736c52a78");
//		archiveService.listArchiveFiles(archive);
		// new IndexFiles().runIndex();
		// try {
		// new SearchFiles().search("money", true);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

//		try {
//			String pdfboxFileReader = new IndexFiles().PdfboxFileReader("/Users/ymzhou/Documents/pdf/demo.pdf", 1, 2);
//			pdfboxFileReader.length();
//			// System.out.println(pdfboxFileReader);
//			BufferedReader buff = new BufferedReader(new StringReader(pdfboxFileReader));
//			String line = buff.readLine();
//
//			while (line != null) {
//				System.out.println(line);
//				System.out.println(line.indexOf("文 件"));
//				line = buff.readLine();
//			}
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

}
