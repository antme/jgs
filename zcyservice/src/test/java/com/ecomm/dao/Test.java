package com.ecomm.dao;

import com.zcy.util.PdfUtil;

public class Test {

	public static void main(String[] args) {

//		String s = "卷宗目录";
//		System.out.println(s.charAt(0));
		
		
		System.out.println(PdfUtil.getLines("/Users/ymzhou/Documents/TDSH-CG-2013-0258_s.pdf", 0, 1));

	}

}
