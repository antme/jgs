package com.zcyservice.service;

import java.util.List;
import java.util.Map;

import com.zcy.bean.EntityResults;
import com.zcy.bean.Log;
import com.zcy.bean.RoleGroup;
import com.zcy.bean.SystemConfig;
import com.zcy.bean.User;
import com.zcyservice.bean.Menu;
import com.zcyservice.bean.vo.SearchVo;

public interface ISystemService {

	public List<SystemConfig> listSystemConfig();

	public void addSysConfig(List<SystemConfig> cfgList);


	public EntityResults<RoleGroup>  listRoleGroups();
	
	public EntityResults<RoleGroup>  listRoleGroupForSelect();
	
	public void addRoleGroup(RoleGroup group);

	public EntityResults<User> listBackendUsers();



	public EntityResults<Log> listLogs(SearchVo search);





}
