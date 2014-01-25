package com.zcyservice.util;

import com.zcy.cfg.CFGManager;

public class ZcyUtil {

	public static String getUploadPath() {

		return CFGManager.getProperty(ZcyServiceConstants.DOCUMENT_UPLOAD_PATH);
	}

	public static String getDocumentPath() {

		return CFGManager.getProperty(ZcyServiceConstants.DOCUMENT_SCAN_PATH);
	}

	public static void imgageToPdf(String imageFile, String targetPdfFile) {

		Document document = new Document();

		try {
			PdfWriter.getInstance(document, new FileOutputStream(targetPdfFile));
			document.open();

			Image image1 = Image.getInstance(imageFile);
			image1.scalePercent(80f);
			document.add(image1);

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
