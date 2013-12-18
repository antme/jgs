package com.jgs.cfg;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jgs.bean.SystemConfig;
import com.jgs.dao.IQueryDao;
import com.jgs.dbhelper.DataBaseQueryBuilder;
import com.jgs.util.EcUtil;

public class CFGManager {
	private static Logger logger = LogManager.getLogger(CFGManager.class);

	private static Properties properties = new Properties();

	public static IQueryDao dao;
	
	public static void setConfiguraion(String configFiles, IQueryDao dao) {
		CFGManager.dao = dao;
		if (properties.isEmpty()) {

			String files[] = configFiles.split(",");

			for (String file : files) {
				try {
					// load resource from class root path
					properties.load(CFGManager.class.getResourceAsStream("/".concat(file)));
				} catch (IOException e) {
					logger.fatal("Load property file failed: ".concat(file), e);
				}

			}
		}
		
		loadDbConfig();
	}

	public static void loadDbConfig() {
	    DataBaseQueryBuilder query = new DataBaseQueryBuilder(SystemConfig.TABLE_NAME);
		query.limitColumns(new String[]{SystemConfig.CONFIG_ID, SystemConfig.CONFIG_VALUE});
		List<SystemConfig> list = dao.listByQuery(query, SystemConfig.class);
		for(SystemConfig sc: list){
			properties.put(sc.getConfigId(), sc.getCfgValue());
		}
    }

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	public static String getTipMessage(String key) {

		if (properties.getProperty(key) != null) {
			try {
				return new String(properties.getProperty(key).getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// do nothing
			}
		} else {
			return String.format("please configure the error message [%s] in applicationResources.properties", key);
		}
		return null;
	}

	public static void setProperties(String key, String value) {
		properties.setProperty(key, value);
	}
	
	public static void remove(String key) {
		properties.remove(key);
	}

	public static boolean isProductEnviroment() {
		String env = CFGManager.getProperty("enviroment");

		if (EcUtil.isEmpty(env)) {
			return false;
		}

		if (CFGManager.getProperty("enviroment").equalsIgnoreCase("product")) {
			return true;
		}

		return false;
	}

	public static boolean isDevEnviroment() {
		return !isProductEnviroment();
	}
}
