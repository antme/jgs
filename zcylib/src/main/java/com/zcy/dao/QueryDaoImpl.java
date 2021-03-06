package com.zcy.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Table;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zcy.bean.BaseEntity;
import com.zcy.bean.EntityResults;
import com.zcy.bean.OrderBy;
import com.zcy.bean.Pagination;
import com.zcy.constants.ZcyConstants;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcy.exception.ResponseException;
import com.zcy.log.EcJDBCAppender;
import com.zcy.util.EcThreadLocal;
import com.zcy.util.EcUtil;

@Repository(value = "commonDao")
public class QueryDaoImpl implements IQueryDao {
	private static Logger logger = LogManager.getLogger(QueryDaoImpl.class);

	@Autowired
	public IMyBatisDao dao;

	public IMyBatisDao getDao() {
		return dao;
	}

	public void setDao(IMyBatisDao dao) {
		this.dao = dao;
	}

	@Override
	public BaseEntity insert(BaseEntity entity) {

		if (EcUtil.isEmpty(entity.getId())) {
			entity.setId(UUID.randomUUID().toString());
		}
		entity.setCreatedOn(new Date());
		entity.setUpdatedOn(new Date());
		entity.setCreatorId(EcThreadLocal.getCurrentUserId());
		int result = dao.insert(entity);

		if (result == 1) {

		} else {
			throw new ResponseException("保存失败");
		}

	
		EcJDBCAppender.appendLog(entity, false, entity.getClass());
		return entity;

	}
	

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BaseEntity> List<T> listByQuery(DataBaseQueryBuilder builder, Class<T> classzz) {
	

		builder = mergeQueryBuilder(builder, classzz);

		List<Map<String, Object>> results = dao.listByQuery(builder);

		List<T> entityList = new ArrayList<T>();
		for (Map<String, Object> result : results) {
			entityList.add((T) EcUtil.toEntity(result, classzz));
		}
		return entityList;
	}
	
	public <T extends BaseEntity> List<T> listBySql(String sql, Class<T> classzz){
		List<Map<String, Object>> results = dao.listBySql(sql);

		List<T> entityList = new ArrayList<T>();
		for (Map<String, Object> result : results) {
			entityList.add((T) EcUtil.toEntity(result, classzz));
		}
		return entityList;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BaseEntity> EntityResults<T> listByQueryWithPagnation(DataBaseQueryBuilder builder, Class<T> classzz) {

		builder = mergeQueryBuilder(builder, classzz);
		
		List<Map<String, Object>> results = dao.listByQueryWithPagination(builder);

		List<T> entityList = new ArrayList<T>();
		for (Map<String, Object> result : results) {
			if(result == null){
				result = new HashMap<String, Object>();
			}
			entityList.add((T) EcUtil.toEntity(result, classzz));
		}

		return toListBean(builder, entityList);

	}

	private <T extends BaseEntity> DataBaseQueryBuilder mergeQueryBuilder(DataBaseQueryBuilder builder, Class<T> classzz) {
	
		
		if (builder == null) {

			Table table = classzz.getAnnotation(Table.class);
			builder = new DataBaseQueryBuilder(table.name());
		}
		if (EcThreadLocal.get(ZcyConstants.PAGENATION) != null) {
			builder.pagination((Pagination) EcThreadLocal.get(ZcyConstants.PAGENATION));
		}
		if (EcThreadLocal.get(ZcyConstants.DB_QUERY_ORDER_BY) != null) {
			OrderBy order = (OrderBy) EcThreadLocal.get(ZcyConstants.DB_QUERY_ORDER_BY);
			if(order.getOrder().equalsIgnoreCase("asc")){
				builder.orderBy(order.getSort(), true);
			}else{
				builder.orderBy(order.getSort(), false);
			}

		}
		if (EcUtil.isEmpty(builder.getOrderBy())) {
			builder.orderBy(BaseEntity.CREATED_ON, false);			
		}
	    return builder;
    }

	@Override
	public void updateById(BaseEntity entity) {
		
		if (EcUtil.isEmpty(entity.getId())) {
			throw new IllegalArgumentException("Must have id value when call updateById method");
		}
		//not need update created on field
		entity.setUpdatedOn(new Date());
		EcJDBCAppender.appendLog(entity, true, entity.getClass());
		dao.updateById(entity);
		
	}
	


	@Override
	public void updateByQuery(DataBaseQueryBuilder builder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(BaseEntity entity) {
		dao.deleteById(entity);
	}

	public void deleteAllByTableName(String table) {
		dao.deleteAllByTableName(table);
	}
	
	public void deleteByQuery(DataBaseQueryBuilder builder){
		dao.deleteByQuery(builder);
	}

	@Override
	public <T extends BaseEntity> BaseEntity findOneByQuery(DataBaseQueryBuilder builder, Class<T> classzz) {
		return EcUtil.toEntity(dao.findOneByQuery(builder), classzz);
	}
	
	public <T extends BaseEntity> BaseEntity findById(String id, String table, Class<T> classzz) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(table);
		builder.and(ZcyConstants.ID, id);
		
		Map<String, Object> result = dao.findOneByQuery(builder);

		if (EcUtil.isEmpty(result)) {
			return null;
		}
		return EcUtil.toEntity(result, classzz);
	}
	
	
	public <T extends BaseEntity> BaseEntity findByKeyValue(String key, String value, String table, Class<T> classzz) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(table);
		builder.and(key, value);
		Map<String, Object> result = dao.findOneByQuery(builder);

		if (EcUtil.isEmpty(result)) {
			return null;
		}
		return EcUtil.toEntity(result, classzz);
	}

	public int count(DataBaseQueryBuilder builder) {
		return dao.count(builder);
	}

	private <T extends BaseEntity> EntityResults<T> toListBean(DataBaseQueryBuilder builder, List<T> entityList) {

		EntityResults<T> listBean = new EntityResults<T>();
		listBean.setEntityList(entityList);

		int total = dao.count(builder);

		Pagination pagnation = new Pagination();
		pagnation.setTotal(total);
		listBean.setPagnation(pagnation);
		return listBean;

	}

	public boolean exists(DataBaseQueryBuilder builder) {

		if (this.count(builder) > 0) {
			return true;
		}
		return false;
	}
	
	public boolean exists(String key, String value, String table){
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(table);
		builder.and(key, value);
		return this.exists(builder);
	}

	
	public <T extends BaseEntity> List<T> distinctQuery(DataBaseQueryBuilder builder, Class<T> classzz) {
		return listByQuery(builder, classzz);
	}

}
