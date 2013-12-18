package com.zcyservice.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcy.service.AbstractService;
import com.zcyservice.bean.mock.TestTable;
import com.zcyservice.service.IUnitTestService;

@Service(value = "unitTestService")
public class UnitTestServiceImpl extends AbstractService implements IUnitTestService {

	public static final String CATTX = "cattx";



	@Override
	@Transactional
	public void insertTestData() {
		dao.deleteAllByTableName(new TestTable().getTable());

		TestTable tb = new TestTable();
		tb.setUserName(CATTX);

		dao.insert(tb);

		addTxFail(tb);

	}

	public void addTxFail(TestTable tb) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dao.insert(tb);
	}

}
