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
import com.zcyservice.bean.vo.WorkerReport;

public interface ISystemService {

	public List<SystemConfig> listSystemConfig();

	public void addSysConfig(List<SystemConfig> cfgList);


	public EntityResults<RoleGroup>  listRoleGroups();
	
	public EntityResults<RoleGroup>  listRoleGroupForSelect();
	
	public void addRoleGroup(RoleGroup group);

	public EntityResults<User> listBackendUsers();

	public void updateUserGroup(User user);

	public void updateMenuGroup(Menu menu);

	public Menu loadMenuGroup(Menu menu);

	public void clearData();

	public EntityResults<Log> listLogs(SearchVo search);

	public Map<String, Object> listUserReport(SearchVo search);

	public Map<String, Object> listMfcLocationReport(SearchVo search);
	
	public Map<String, Object> listSpLocationReport(SearchVo search);

	public Map<String, Object> getOrderStats(SearchVo svo);

	public Map<String, Object> getUserEffectiveReport(SearchVo svo);

	public Map<String, Object> getSpEffectiveReport(SearchVo svo);

	public Map<String, Object> getKfEffectiveReport(SearchVo svo);

	public Map<String, Object> getOrderEffectiveReport(SearchVo search);

	public Map<String, Object> listSpLocationCateReport(SearchVo search);

	public void testAccount(SearchVo svo);

	public List<WorkerReport> getWorkerReport(SearchVo svo);

}
