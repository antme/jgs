package com.zcy.dao;

import java.util.List;
import java.util.Map;

import com.zcy.bean.BaseEntity;
import com.zcy.dbhelper.DataBaseQueryBuilder;

public interface IMyBatisDao {

	public int insert(BaseEntity entity);

	public List<Map<String, Object>> listByQuery(DataBaseQueryBuilder builder);
	
	public List<Map<String, Object>> listByQueryWithPagination(DataBaseQueryBuilder builder);

	public void updateById(BaseEntity entity);
	
	public void updateByQuery(DataBaseQueryBuilder builder);

	public void deleteById(BaseEntity entity);
	
	public void deleteByQuery(DataBaseQueryBuilder builder);
		
	public Map<String, Object> findOneByQuery(DataBaseQueryBuilder builder);

	public void deleteAllByTableName(String table);

	public int count(DataBaseQueryBuilder builder);
	
	public List<Map<String, Object>> listBySql(String sql);


}
