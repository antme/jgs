package com.zcy.dao;

import java.util.List;
import java.util.Map;

import com.zcy.bean.BaseEntity;
import com.zcy.bean.EntityResults;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcy.dbhelper.DataBaseQueryOpertion;

public interface IQueryDao {

	/**
	 * 
	 * 传人entity对象，table自动获取
	 * 
	 * @param entity
	 * @return 1 成功， 其它错误
	 */
	public BaseEntity insert(BaseEntity entity);

	/**
	 * 传人entity对象，数据操作取entity里面的table和id，其它属性和字段忽略
	 * 
	 * 
	 * @param entity
	 */
	public void updateById(BaseEntity entity);

	/**
	 * 传人DataBaseQueryBuilder对象，
	 * 
	 * 
	 * @param builder
	 */
	public void updateByQuery(DataBaseQueryBuilder builder);

	/**
	 * 传人entity对象，数据操作取entity里面的table和id，其它属性和字段忽略
	 * 
	 * 
	 * @param entity
	 */
	public void deleteById(BaseEntity entity);

	
	public void deleteByQuery(DataBaseQueryBuilder builder);

	/**
	 * 
	 * 根据table name一次性删除所有数据
	 * 
	 * @param table
	 */
	public void deleteAllByTableName(String table);

	
	
	public <T extends BaseEntity> List<T> listByQuery(DataBaseQueryBuilder builder, Class<T> classzz);
	
	public <T extends BaseEntity> List<T> listBySql(String sql, Class<T> classzz);


	public <T extends BaseEntity> EntityResults<T> listByQueryWithPagnation(DataBaseQueryBuilder builder, Class<T> classzz);

	public <T extends BaseEntity> BaseEntity findOneByQuery(DataBaseQueryBuilder builder, Class<T> classzz);
	
	public <T extends BaseEntity> BaseEntity findById(String id, String table, Class<T> classzz);
	
	public <T extends BaseEntity> BaseEntity findByKeyValue(String key, String value, String table, Class<T> classzz);

	public int count(DataBaseQueryBuilder builder);
	
	public boolean exists(DataBaseQueryBuilder builder);
	
	public boolean exists(String key, String value, String table);

	
	public <T extends BaseEntity> List<T> distinctQuery(DataBaseQueryBuilder builder, Class<T> classzz);
	
}
