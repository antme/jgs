package com.zcyservice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zcy.annotation.LoginRequired;
import com.zcy.annotation.Permission;
import com.zcy.bean.RoleGroup;
import com.zcy.bean.SystemConfig;
import com.zcy.bean.User;
import com.zcy.controller.AbstractController;
import com.zcyservice.bean.Menu;
import com.zcyservice.bean.vo.SearchVo;
import com.zcyservice.service.ISystemService;
import com.zcyservice.util.PermissionConstants;

@Controller
@RequestMapping("/ecs/sys")
@Permission()
@LoginRequired()
public class SystemController extends AbstractController {


	@Autowired
	private ISystemService sys;

	@RequestMapping("/cfg/list.do")
	public void listSystemConfig(HttpServletRequest request, HttpServletResponse response) {
		List<SystemConfig> cfgList = sys.listSystemConfig();
		Map<String, Object> result = new HashMap<String, Object>();
		for (SystemConfig cfg : cfgList) {
			result.put(cfg.getConfigId(), cfg.getCfgValue());
		}

		responseWithData(result, request, response);
	}

	@RequestMapping("/cfg/add.do")
	@Permission(groupName = PermissionConstants.ADM_RULE_MANAGE, permissionID = PermissionConstants.ADM_RULE_MANAGE)
	public void addSystemConfig(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> parameters = parserJsonParameters(request, false);

		List<SystemConfig> cfgList = new ArrayList<SystemConfig>();

		for (String key : parameters.keySet()) {
			SystemConfig cfg = new SystemConfig();
			cfg.setConfigId(key);
			cfg.setCfgValue(parameters.get(key).toString());
			cfgList.add(cfg);
		}
		sys.addSysConfig(cfgList);
		responseWithListData(null, request, response);
	}

	@RequestMapping("/group/list.do")
	public void listRoleGroups(HttpServletRequest request, HttpServletResponse response) {

		responseWithDataPagnation(sys.listRoleGroups(), request, response);

	}
	
	@RequestMapping("/group/select.do")
	public void listRoleGroupForSelect(HttpServletRequest request, HttpServletResponse response) {

		responseWithDataPagnation(sys.listRoleGroupForSelect(), request, response);

	}

	@RequestMapping("/group/add.do")
	public void addRoleGroup(HttpServletRequest request, HttpServletResponse response) {
		RoleGroup group = (RoleGroup) parserJsonParameters(request, false, RoleGroup.class);
		sys.addRoleGroup(group);
		responseWithData(null, request, response);

	}
	
	@RequestMapping("/cfg/user/group/update.do")
	public void updateUserGroup(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, false, User.class);
		sys.updateUserGroup(user);
		responseWithData(null, request, response);

	}
	
	@RequestMapping("/cfg/menu/group/update.do")
	public void updateMenuGroup(HttpServletRequest request, HttpServletResponse response) {
		Menu menu = (Menu) parserJsonParameters(request, false, Menu.class);
		sys.updateMenuGroup(menu);
		responseWithData(null, request, response);

	}
	
	@RequestMapping("/cfg/menu/group/load.do")
	public void loadMenuGroup(HttpServletRequest request, HttpServletResponse response) {
		Menu menu = (Menu) parserJsonParameters(request, false, Menu.class);
		responseWithEntity(sys.loadMenuGroup(menu), request, response);
	}
	
	
	@RequestMapping("/cfg/role/bakend/user/list.do")
	public void listBackendUsers(HttpServletRequest request, HttpServletResponse response) {
		
		responseWithDataPagnation(sys.listBackendUsers(), request, response);

	}
	

	@RequestMapping("/log/listlog.do")
	public void listLogs(HttpServletRequest request, HttpServletResponse response) {
		SearchVo search = (SearchVo)parserJsonParameters(request, true, SearchVo.class);

		responseWithDataPagnation(sys.listLogs(search), request, response);

	}
	
	



	

}
