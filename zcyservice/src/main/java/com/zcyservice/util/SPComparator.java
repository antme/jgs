package com.zcyservice.util;

import java.util.Comparator;

import com.zcyservice.bean.ServiceProvider;


public class SPComparator implements Comparator<ServiceProvider> {

	public int compare(ServiceProvider sp1, ServiceProvider sp2) {

		// 首先比较年龄，如果年龄相同，则比较名字

		int flag = sp1.getDistance().compareTo(sp2.getDistance());

		return flag;

	}

}