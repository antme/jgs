package com.zcy.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.zcy.bean.BaseEntity;
import com.zcy.dao.IQueryDao;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcy.dbhelper.DataBaseQueryOpertion;
import com.zcy.exception.ResponseException;
import com.zcy.util.DateUtil;
import com.zcy.util.EcThreadLocal;

public abstract class AbstractService {

	@Autowired
	public IQueryDao dao;

	public IQueryDao getDao() {
		return dao;
	}

	public void setDao(IQueryDao dao) {
		this.dao = dao;
	}

	protected void checkOwner(BaseEntity entity) {
		checkOwner(entity, BaseEntity.CREATOR_ID, null, null);
	}
	
	protected void checkOwner(BaseEntity entity, String checkKey, Object checkValue) {
		checkOwner(entity, BaseEntity.CREATOR_ID, checkKey, checkValue);
	}
	
	protected void checkValue(BaseEntity entity, String checkKey, Object checkValue) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(entity.getTable());

		query.and(BaseEntity.ID, entity.getId());
		if (checkKey != null && checkValue != null) {
			query.and(checkKey, checkValue);
		}

		if (!this.dao.exists(query)) {
			throw new ResponseException("非法数据");
		}

	}

	protected void checkOwner(BaseEntity entity, String ownerKey, String checkKey, Object checkValue) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(entity.getTable());

		if (ownerKey != null) {

			query.and(ownerKey, EcThreadLocal.getCurrentUserId());

		}
		if (checkKey != null && checkValue != null) {
			query.and(checkKey, checkValue);
		}

		if (!this.dao.exists(query)) {
			throw new ResponseException("非法数据");
		}

	}
	
	protected void checkOwner(BaseEntity entity, String ownerKey, DataBaseQueryBuilder dbQuery) {
		if (ownerKey != null) {

			DataBaseQueryBuilder query = new DataBaseQueryBuilder(entity.getTable());
			query.and(ownerKey, EcThreadLocal.getCurrentUserId());

			dbQuery.and(query);
		}

		if (!this.dao.exists(dbQuery)) {
			throw new ResponseException("非法数据");
		}

	}

	protected void checkIdsStatus(DataBaseQueryBuilder query, List<String> ids) {
		checkIdsStatus(query, ids, BaseEntity.CREATOR_ID);

	}
	
	protected void checkIdsStatus(DataBaseQueryBuilder query, List<String> ids, String ownerKey) {
		if (ids.size() > 0) {
			DataBaseQueryBuilder idsQuery = new DataBaseQueryBuilder(query.getTable());
			if (ownerKey != null) {
				idsQuery.and(ownerKey, EcThreadLocal.getCurrentUserId());

			}
			idsQuery.and(DataBaseQueryOpertion.IN, BaseEntity.ID, ids);
			idsQuery.and(query);
			if (this.dao.count(idsQuery) != ids.size()) {
				throw new ResponseException("非法数据");
			}

		}

	}

}
