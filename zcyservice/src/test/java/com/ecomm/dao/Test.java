package com.ecomm.dao;

import java.util.List;

import com.zcy.util.PdfUtil;
import com.zcyservice.bean.Archive;

public class Test {

	public static void main(String[] args) {

		
		for(int i =0; i< 100; i++){
			
			String result = "";
			int total = 0; 
			for(int j=0; j<3; j++){
			
				int random  = 1+ new java.util.Random().nextInt(6);
				total = total + random;
				result = result + random + ",";
			}
			
			System.out.println(result + " ======= " + total);
		}
//		
//		// String s = "卷宗目录";
//		// System.out.println(s.charAt(0));
//
//		Archive archive = new Archive();
//		List<String> lines = PdfUtil.getLines("/Users/ymzhou/Documents/TDSH-CG-2013-0258_s.pdf", 0, 1);
//		String code = null;
//		String reason = null;
//		String results = null;
//		String applicant = null;
//		String applicantBad = null;
//		String thirdApplicant = null;
//		String judgePerson = null;
//		String dateType = "";
//
//		for (String line : lines) {
//			line = line.replaceAll(" ", "").trim();
//
//			if (line.contains("年度第")) {
//				code = line;
//			} else if (line.startsWith("案由")) {
//				reason = line.replaceFirst("案由", "");
//			} else if (line.startsWith("处理结果")) {
//				reason = line.replaceFirst("处理结果", "");
//			} else if (line.startsWith("申请人")) {
//				applicant = line.replaceFirst("申请人", "");
//			} else if (line.startsWith("被申请人")) {
//				applicantBad = line.replaceFirst("被申请人", "");
//			} else if (line.startsWith("第三人")) {
//				thirdApplicant = line.replaceFirst("第三人", "");
//			} else if (line.startsWith("承办人")) {
//				judgePerson = line.replaceFirst("承办人", "");
//			} else if (line.startsWith("立案")) {
//				dateType = "立案";
//			} else if (line.startsWith("结案")) {
//				dateType = "结案";
//			} else if (line.startsWith("归档")) {
//				dateType = "归档";
//			} else if (line.startsWith("号数")) {
//				dateType = "号数";
//			}else if (line.contains("年") && line.contains("月") && line.contains("日")) {
//
//				if (dateType == "立案") {
//
//					System.out.println(line);
//				} else if (dateType == "结案") {
//					System.out.println(line);
//				} else if (dateType == "归档") {
//					System.out.println(line);
//				}
//
//				dateType = "";
//			} else if (dateType == "号数") {
//				System.out.println(line);
//				dateType = "";
//			}
//
//			
//		}

	}

}
