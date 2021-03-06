package com.zcyservice.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zcy.bean.EntityResults;
import com.zcy.bean.Log;
import com.zcy.bean.RoleGroup;
import com.zcy.bean.SystemConfig;
import com.zcy.bean.User;
import com.zcy.cfg.CFGManager;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcy.dbhelper.DataBaseQueryOpertion;
import com.zcy.exception.ResponseException;
import com.zcy.util.EcUtil;
import com.zcyservice.bean.vo.SearchVo;
import com.zcyservice.service.AbstractArchiveService;
import com.zcyservice.service.ISystemService;
import com.zcyservice.util.Role;

@Service(value = "sys")
public class SystemServiceImpl extends AbstractArchiveService implements ISystemService {
	private static Logger logger = LogManager.getLogger(SystemServiceImpl.class);

	public List<SystemConfig> listSystemConfig() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(SystemConfig.TABLE_NAME);
		return this.dao.listByQuery(builder, SystemConfig.class);
	}

	public void addSysConfig(List<SystemConfig> cfgList) {

		for (SystemConfig cfg : cfgList) {

			SystemConfig config = (SystemConfig) this.dao.findByKeyValue(SystemConfig.CONFIG_ID, cfg.getConfigId(), SystemConfig.TABLE_NAME, SystemConfig.class);

			String log = String.format("修改了系统配置【%s】", cfgList.toString());
			logger.info(log);

			if (config != null) {
				config.setCfgValue(cfg.getCfgValue());
				this.dao.updateById(config);
			} else {
				this.dao.insert(cfg);
			}

			CFGManager.loadDbConfig();
		}

	}

	public EntityResults<RoleGroup> listRoleGroups() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(RoleGroup.TABLE_NAME);
		return this.dao.listByQueryWithPagnation(builder, RoleGroup.class);
	}

	public EntityResults<RoleGroup> listRoleGroupForSelect() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(RoleGroup.TABLE_NAME);
		return this.dao.listByQueryWithPagnation(builder, RoleGroup.class);
	}

	public void addRoleGroup(RoleGroup group) {

		if (EcUtil.isEmpty(group.getGroupName())) {
			throw new ResponseException("权限组名字不能为空");
		}

		if (!EcUtil.isEmpty(group.getId())) {
			DataBaseQueryBuilder query = new DataBaseQueryBuilder(RoleGroup.TABLE_NAME);
			query.and(RoleGroup.GROUP_NAME, group.getGroupName());
			query.and(DataBaseQueryOpertion.NOT_EQUALS, RoleGroup.ID, group.getId());
			if (this.dao.exists(query)) {
				throw new ResponseException("权限组名字不能重复");
			}

			this.dao.updateById(group);
		} else {
			if (this.dao.exists(RoleGroup.GROUP_NAME, group.getGroupName(), RoleGroup.TABLE_NAME)) {
				throw new ResponseException("权限组名字重复");
			}
			this.dao.insert(group);
		}
	}

	@Override
	public EntityResults<Log> listLogs(SearchVo search) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteRoleGroups(List<String> ids) {
		for (String id : ids) {
			RoleGroup group = new RoleGroup();
			group.setId(id);
			this.dao.deleteById(group);
		}
	}

}
