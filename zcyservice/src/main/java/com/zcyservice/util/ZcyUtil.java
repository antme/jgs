package com.zcyservice.util;

import com.zcy.cfg.CFGManager;

public class ZcyUtil {

	
	public static String getUploadPath(){
		
		return CFGManager.getProperty(ZcyServiceConstants.DOCUMENT_UPLOAD_PATH);
	}
	
	
	public static String getDocumentPath(){
		
		return CFGManager.getProperty(ZcyServiceConstants.DOCUMENT_SCAN_PATH);
	}
}
