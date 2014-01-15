package com.zcyservice.service.impl;

import java.util.List;
import java.util.Map;

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
import com.zcy.util.EcUtil;
import com.zcyservice.bean.Menu;
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
			CFGManager.remove(SystemConfig.BAIDU_MAP_KEY_ERROR);
			CFGManager.remove(SystemConfig.SMS_ACCOUNT_ID_ERROR);

			CFGManager.loadDbConfig();
		}
		


	}


	public EntityResults<RoleGroup>  listRoleGroups() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(RoleGroup.TABLE_NAME);
		return this.dao.listByQueryWithPagnation(builder, RoleGroup.class);
	}
	
	public EntityResults<RoleGroup>  listRoleGroupForSelect(){
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(RoleGroup.TABLE_NAME);
		return this.dao.listByQueryWithPagnation(builder, RoleGroup.class);
	}

	public void addRoleGroup(RoleGroup group) {
		if (!EcUtil.isEmpty(group.getId())) {
			this.dao.updateById(group);
		} else {
			this.dao.insert(group);
		}
	}

	public EntityResults<User> listBackendUsers() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.or(User.ROLE_NAME, Role.ADMIN.toString());
		builder.or(User.ROLE_NAME, Role.CUSTOMER_SERVICE.toString());
		builder.or(User.ROLE_NAME, Role.SUPPER_ADMIN.toString());
		
		DataBaseQueryBuilder roleQuery = new DataBaseQueryBuilder(RoleGroup.TABLE_NAME);
		List<RoleGroup> groupList = this.dao.listByQuery(roleQuery, RoleGroup.class);
		
		
		
		EntityResults<User> userData = this.dao.listByQueryWithPagnation(builder, User.class);
		
		List<User> userList = userData.getEntityList();
		for (User user : userList) {

			String name = null;
			if (user.getGroupId() != null) {

				for(RoleGroup group: groupList){
					if(user.getGroupId().contains(group.getId())){
						
						if(name == null){
							name = group.getGroupName();
							
						}else{
							 name = name + ", " + group.getGroupName();
						}
					}
				}
			}
			user.setGroupName(name);
		}
		return userData;
	}

	public void updateUserGroup(User user) {
		this.dao.updateById(user);
	}

	public void updateMenuGroup(Menu menu) {

		Menu old = (Menu) this.dao.findByKeyValue(Menu.MENU_ID, menu.getMenuId(), Menu.TABLE_NAME, Menu.class);
		if (old != null) {
			old.setGroupId(menu.getGroupId());
			this.dao.updateById(old);
		} else {
			this.dao.insert(menu);
		}
	}

	public Menu loadMenuGroup(Menu menu) {

		return (Menu) this.dao.findById(menu.getId(), Menu.TABLE_NAME, Menu.class);
	}






	@Override
    public EntityResults<Log> listLogs(SearchVo search) {
	    // TODO Auto-generated method stub
	    return null;
    }

	

}
