package com.zcyservice.service;

import org.springframework.stereotype.Service;

import com.zcyservice.bean.mock.TestTable;

@Service(value = "unitTestService")
public interface IUnitTestService {

	public void insertTestData();
	
	public void addTxFail(TestTable tb);
}
