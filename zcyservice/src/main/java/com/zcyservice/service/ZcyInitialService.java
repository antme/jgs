package com.zcyservice.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.zcy.cfg.CFGManager;
import com.zcy.dao.IMyBatisDao;

public class ZcyInitialService {

	public static final Set<String> loginPath = new HashSet<String>();
	public static final Map<String, String> rolesValidationMap = new HashMap<String, String>();
	private static final Logger logger = LogManager.getLogger(ZcyInitialService.class);

	public static final String ADMIN_USER_NAME = "admin";

	/**
	 * 初始化数据库
	 * 
	 * @param dao
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 */
	public static void initSystem(IMyBatisDao dao, String packageName) throws SecurityException, ClassNotFoundException {

		String file = ZcyInitialService.class.getResource("/sigar/.sigar_shellrc").getFile();
		File classPath = new File(file).getParentFile();

		String path = System.getProperty("java.library.path");
		if (CFGManager.isWindows()) {
			try {
				path += ";" + classPath.getCanonicalPath();
				System.setProperty("java.library.path", path);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				path += ":" + classPath.getCanonicalPath();
				System.setProperty("java.library.path", path);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
