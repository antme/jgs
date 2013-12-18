package com.ecomm.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jgs.bean.BaseEntity;
import com.jgs.bean.Pagination;
import com.jgs.dbhelper.DataBaseQueryBuilder;
import com.jgs.dbhelper.DataBaseQueryOpertion;
import com.jgs.util.DataEncrypt;
import com.jgsservice.bean.mock.TestTable;
import com.jgsservice.bean.mock.TestTableRef;
import com.jgsservice.service.IUnitTestService;
import com.jgsservice.service.impl.ECommerceUserServiceImpl;
import com.jgsservice.service.impl.UnitTestServiceImpl;

public class QueryCommonDaoImpTest extends BaseTestCase {
	private static Logger logger = LogManager.getLogger(ECommerceUserServiceImpl.class);

	public static final String CAT = "cat";

	public void testInsert() {
		dao.deleteAllByTableName(new TestTable().getTable());
		TestTable testTable = new TestTable();
		testTable.setUserName(CAT);
		testTable.setPassword("21111");
		BaseEntity savedTestTable = dao.insert(testTable);
		assertNotNull(savedTestTable);
		assertNotNull(savedTestTable.getId());
	}

	public void testFindByQuery() {
		dao.deleteAllByTableName(new TestTable().getTable());
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(new TestTable().getTable());
		builder.and( TestTable.USER_NAME, CAT);
		TestTable testTable = (TestTable) dao.findOneByQuery(builder, TestTable.class);
		assertNull(testTable);
		testInsert();
		testTable = (TestTable) dao.findOneByQuery(builder, TestTable.class);
		assertNotNull(testTable);
		assertEquals(CAT, testTable.getUserName());
	}

	public void testFindByQueryLimitColumns() {
		testInsert();
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(new TestTable().getTable());
		builder.and( TestTable.USER_NAME, CAT);
		builder.limitColumns(new String[] { TestTable.USER_NAME });
		TestTable testTable = (TestTable) dao.findOneByQuery(builder, TestTable.class);
		assertNotNull(testTable);
		assertNull(testTable.getPassword());
		assertNotNull(testTable.getUserName());
	}

	public void testListByQuery() {
		dao.deleteAllByTableName(new TestTable().getTable());
		TestTable testTable = new TestTable();
		testTable.setUserName(CAT);
		testTable.setPassword("21111");
		dao.insert(testTable);

		testTable = new TestTable();
		testTable.setUserName("dylan2");
		testTable.setPassword("21111");
		dao.insert(testTable);

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(new TestTable().getTable());

		List<TestTable> TestTables = dao.listByQuery(builder, TestTable.class);

		assertNotNull(TestTables);
		assertEquals(2, TestTables.size());

		for (TestTable use : TestTables) {
			assertNotNull(testTable.getUserName());
			assertNotNull(testTable.getPassword());
		}

	}
	
	

	public void testListBySql() {
		dao.deleteAllByTableName(new TestTable().getTable());
		TestTable testTable = new TestTable();
		testTable.setUserName(CAT);
		testTable.setPassword("21111");
		dao.insert(testTable);

		testTable = new TestTable();
		testTable.setUserName("dylan2");
		testTable.setPassword("21111");
		dao.insert(testTable);

       String sql = "SELECT * FROM TestTable";
		List<TestTable> TestTables = dao.listBySql(sql, TestTable.class);

		assertNotNull(TestTables);
		assertEquals(2, TestTables.size());

		for (TestTable use : TestTables) {
			assertNotNull(testTable.getUserName());
			assertNotNull(testTable.getPassword());
		}

	}

	public void testLimitQuery() {

		dao.deleteAllByTableName(new TestTable().getTable());

		for (int i = 0; i < 100; i++) {
			TestTable testTable = new TestTable();
			testTable.setUserName(CAT + i);
			testTable.setPassword(DataEncrypt.generatePassword("1"));
			dao.insert(testTable);
		}

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(new TestTable().getTable());
		Pagination pag = new Pagination();
		pag.setPage(1);
		pag.setRows(20);
		builder.pagination(pag);

		List<TestTable> list = dao.listByQuery(builder, TestTable.class);

		assertNotNull(list);
		assertEquals(100, list.size());
		
		assertEquals(20, dao.listByQueryWithPagnation(builder, TestTable.class).getEntityList().size());

	}
	
	public void testNotNullQuery() {

		dao.deleteAllByTableName(new TestTable().getTable());

		for (int i = 0; i < 100; i++) {
			TestTable testTable = new TestTable();
			testTable.setUserName(CAT + i);
			testTable.setPassword(DataEncrypt.generatePassword("1"));
			dao.insert(testTable);
		}

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(new TestTable().getTable());
		builder.and(DataBaseQueryOpertion.NOT_NULL, TestTable.USER_NAME);

		List<TestTable> list = dao.listByQuery(builder, TestTable.class);

		assertNotNull(list);
		assertEquals(100, list.size());

	}

	public void testLikeQuery() {

		dao.deleteAllByTableName(new TestTable().getTable());

		for (int i = 0; i < 100; i++) {
			TestTable testTable = new TestTable();
			testTable.setUserName(CAT + i);
			testTable.setPassword(DataEncrypt.generatePassword("1"));
			dao.insert(testTable);
		}

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(new TestTable().getTable());
		builder.and(DataBaseQueryOpertion.LIKE, TestTable.USER_NAME, CAT);

		List<TestTable> list = dao.listByQuery(builder, TestTable.class);

		assertNotNull(list);
		assertEquals(100, list.size());

		builder = new DataBaseQueryBuilder(new TestTable().getTable());
		builder.and(DataBaseQueryOpertion.LIKE, TestTable.USER_NAME, "sssss");

		list = dao.listByQuery(builder, TestTable.class);

		assertNotNull(list);
		assertEquals(0, list.size());
	}

	public void testInQuery() {

		dao.deleteAllByTableName(new TestTable().getTable());

		for (int i = 0; i < 30; i++) {
			TestTable testTable = new TestTable();
			testTable.setUserName(CAT + i);
			testTable.setPassword(DataEncrypt.generatePassword("1"));
			dao.insert(testTable);
		}

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(new TestTable().getTable());
		builder.and(DataBaseQueryOpertion.IN, TestTable.USER_NAME, CAT);

		List<TestTable> list = dao.listByQuery(builder, TestTable.class);

		assertNotNull(list);
		assertEquals(0, list.size());

		builder = new DataBaseQueryBuilder(new TestTable().getTable());
		builder.and(DataBaseQueryOpertion.IN, TestTable.USER_NAME, CAT + 0);

		list = dao.listByQuery(builder, TestTable.class);

		assertNotNull(list);
		assertEquals(1, list.size());

		String[] cats = new String[] { "CAT0", "CAT20" };
		builder = new DataBaseQueryBuilder(new TestTable().getTable());
		builder.and(DataBaseQueryOpertion.IN, TestTable.USER_NAME, cats);

		list = dao.listByQuery(builder, TestTable.class);

		assertNotNull(list);
		assertEquals(2, list.size());

		List<String> catsList = new ArrayList<String>();
		catsList.add("CAT20");
		catsList.add("CAT2");
		catsList.add("CAT27");
		builder = new DataBaseQueryBuilder(new TestTable().getTable());
		builder.and(DataBaseQueryOpertion.IN, TestTable.USER_NAME, catsList);

		list = dao.listByQuery(builder, TestTable.class);

		assertNotNull(list);
		assertEquals(3, list.size());

		Set<String> catsSet = new HashSet<String>();
		catsSet.add("CAT20");
		catsSet.add("CAT2");
		catsSet.add("CAT27");
		builder = new DataBaseQueryBuilder(new TestTable().getTable());
		builder.and(DataBaseQueryOpertion.IN, TestTable.USER_NAME, catsSet);

		list = dao.listByQuery(builder, TestTable.class);

		assertNotNull(list);
		assertEquals(3, list.size());

		builder = new DataBaseQueryBuilder(new TestTable().getTable());
		builder.and(DataBaseQueryOpertion.NOT_IN, TestTable.USER_NAME, catsSet);

		list = dao.listByQuery(builder, TestTable.class);

		assertNotNull(list);
		assertEquals(27, list.size());

	}

	public void testUpdateById() {
		dao.deleteAllByTableName(new TestTable().getTable());
		TestTable testTable = new TestTable();
		testTable.setUserName(CAT);
		testTable.setPassword("21111");

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(new TestTable().getTable());
		String id = dao.insert(testTable).getId();
		builder.and(BaseEntity.ID, id);
		TestTable tb = (com.jgsservice.bean.mock.TestTable) dao.findOneByQuery(builder, TestTable.class);
		assertNotNull(tb);

		TestTable up = new TestTable();
		up.setUserName("update");
		up.setId(id);
		dao.updateById(up);

		tb = (com.jgsservice.bean.mock.TestTable) dao.findOneByQuery(builder, TestTable.class);
		assertNotNull(tb);
		assertEquals("update", tb.getUserName());

	}

	public void testTx() {

		try {
			IUnitTestService ts = ac.getBean(UnitTestServiceImpl.class);
			ts.insertTestData();
			fail("Should throw exception");
		} catch (Exception e) {
			logger.info("Transaction test", e);
			DataBaseQueryBuilder builder = new DataBaseQueryBuilder(new TestTable().getTable());
			builder.and(TestTable.USER_NAME, UnitTestServiceImpl.CATTX);
			List<TestTable> dataList = dao.listByQuery(builder, TestTable.class);
			assertNotNull(dataList);
			assertEquals(0, dataList.size());
		}

	}
	
	
	public void testOrderBy(){
		
		dao.deleteAllByTableName(new TestTable().getTable());
		TestTable testTable = new TestTable();
		testTable.setUserName(CAT);
		testTable.setPassword("21111");
		testTable.setSize(100);
		dao.insert(testTable);
		
		testTable = new TestTable();
		testTable.setUserName(CAT+ 1);
		testTable.setPassword("21111");
		testTable.setSize(30);
		dao.insert(testTable);
		
		testTable = new TestTable();
		testTable.setUserName(CAT + 3);
		testTable.setPassword("21111");
		testTable.setSize(20);
		dao.insert(testTable);
		

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(TestTable.TEST_TABLE);
		builder.orderBy(TestTable.SIZE, true);
		
		List<TestTable> tables = dao.listByQuery(builder, TestTable.class);
		assertEquals("20", tables.get(0).getSize().toString());
		assertEquals("30", tables.get(1).getSize().toString());
		assertEquals("100", tables.get(2).getSize().toString());
		
		builder = new DataBaseQueryBuilder(TestTable.TEST_TABLE);
		builder.orderBy(TestTable.SIZE, false);
		tables = dao.listByQuery(builder, TestTable.class);
		assertEquals("20", tables.get(2).getSize().toString());
		assertEquals("30", tables.get(1).getSize().toString());
		assertEquals("100", tables.get(0).getSize().toString());

	}
	
	
	public void testComplexQuery() {

		dao.deleteAllByTableName(new TestTable().getTable());
		TestTable testTable = new TestTable();
		testTable.setUserName(CAT);
		testTable.setPassword("password");
		testTable.setSize(100);
		dao.insert(testTable);

		testTable = new TestTable();
		testTable.setUserName(CAT + 1);
		testTable.setPassword("abc123_");
		testTable.setSize(30);
		dao.insert(testTable);

		testTable = new TestTable();
		testTable.setUserName(CAT + 3);
		testTable.setPassword("22222");
		testTable.setSize(20);
		dao.insert(testTable);

		testTable = new TestTable();
		testTable.setUserName(CAT + 4);
		testTable.setPassword("22222");
		testTable.setSize(20);
		dao.insert(testTable);

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(TestTable.TEST_TABLE);
		builder.and(TestTable.PASSWORD, "22222");

		List<TestTable> tables = dao.listByQuery(builder, TestTable.class);
		assertEquals(2, tables.size());
		
		DataBaseQueryBuilder childBuilder = new DataBaseQueryBuilder(TestTable.TEST_TABLE);
		childBuilder.or(TestTable.USER_NAME, CAT);
		
		builder.or(childBuilder);
		tables = dao.listByQuery(builder, TestTable.class);
		assertEquals(3, tables.size());
		
		
		builder = new DataBaseQueryBuilder(TestTable.TEST_TABLE);
		builder.and(TestTable.PASSWORD, "22222");
		
		builder.and(childBuilder);
		tables = dao.listByQuery(builder, TestTable.class);
		assertEquals(0, tables.size());
		
		

	}
	
	
	public void testRefQuery(){
		
		dao.deleteAllByTableName(new TestTable().getTable());
		dao.deleteAllByTableName(new TestTableRef().getTable());
		TestTable testTable = new TestTable();
		testTable.setUserName(CAT);
		testTable.setPassword("password");
		testTable.setSize(100);
		dao.insert(testTable);
		
		TestTableRef ref = new TestTableRef();
		ref.setTestTableId(testTable.getId());
		ref.setRefName("ref" + 1);
		dao.insert(ref);

		testTable = new TestTable();
		testTable.setUserName(CAT + 1);
		testTable.setPassword("abc123_");
		testTable.setSize(30);
		dao.insert(testTable);
		
		ref = new TestTableRef();
		ref.setTestTableId(testTable.getId());
		ref.setRefName("ref" + 2);
		dao.insert(ref);
		
		
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(TestTable.TEST_TABLE);
		builder.join(TestTable.TEST_TABLE, TestTableRef.TEST_TABLE_REF, TestTable.ID, TestTableRef.TEST_TABLE_ID);
		builder.joinColumns(TestTableRef.TEST_TABLE_REF, new String[]{TestTableRef.USER_NAME});
		builder.limitColumns(new String[]{TestTable.USER_NAME});

		
		logger.debug(builder.toString());
		System.out.println(this.dao.listByQuery(builder, TestTable.class));
	}


}
