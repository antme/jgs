package com.jgsservice.service;

import java.util.List;
import java.util.Map;

import com.jgs.bean.EntityResults;
import com.jgs.bean.Log;
import com.jgs.bean.RoleGroup;
import com.jgs.bean.SystemConfig;
import com.jgs.bean.User;
import com.jgsservice.bean.Menu;
import com.jgsservice.bean.vo.SearchVo;
import com.jgsservice.bean.vo.WorkerReport;

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
