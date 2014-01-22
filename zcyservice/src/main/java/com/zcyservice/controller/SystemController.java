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
import com.zcy.controller.AbstractController;
import com.zcyservice.bean.vo.IDS;
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
	@Permission(groupName = PermissionConstants.ADM_SYS_SETTINGS, permissionID = PermissionConstants.ADM_SYS_SETTINGS)
	public void listSystemConfig(HttpServletRequest request, HttpServletResponse response) {
		List<SystemConfig> cfgList = sys.listSystemConfig();
		Map<String, Object> result = new HashMap<String, Object>();
		for (SystemConfig cfg : cfgList) {
			result.put(cfg.getConfigId(), cfg.getCfgValue());
		}

		responseWithData(result, request, response);
	}

	@RequestMapping("/cfg/add.do")
	@Permission(groupName = PermissionConstants.ADM_SYS_SETTINGS, permissionID = PermissionConstants.ADM_SYS_SETTINGS)
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
	@Permission(groupName = PermissionConstants.adm_role_manage, permissionID = PermissionConstants.adm_role_manage)
	public void listRoleGroups(HttpServletRequest request, HttpServletResponse response) {

		responseWithDataPagnation(sys.listRoleGroups(), request, response);

	}
	
	@RequestMapping("/group/del.do")
	@Permission(groupName = PermissionConstants.adm_role_manage, permissionID = PermissionConstants.adm_role_manage)
	public void deleteRoleGroups(HttpServletRequest request, HttpServletResponse response) {

		IDS ids = (IDS) parserJsonParameters(request, false, IDS.class);
		sys.deleteRoleGroups(ids.getIds());
		responseWithData(null, request, response);

	}
	
	@RequestMapping("/group/select.do")
	public void listRoleGroupForSelect(HttpServletRequest request, HttpServletResponse response) {

		responseWithDataPagnation(sys.listRoleGroupForSelect(), request, response);

	}

	@RequestMapping("/group/add.do")
	@Permission(groupName = PermissionConstants.adm_role_manage, permissionID = PermissionConstants.adm_role_manage)
	public void addRoleGroup(HttpServletRequest request, HttpServletResponse response) {
		RoleGroup group = (RoleGroup) parserJsonParameters(request, false, RoleGroup.class);
		sys.addRoleGroup(group);
		responseWithData(null, request, response);

	}

	

	

	@RequestMapping("/log/listlog.do")
	public void listLogs(HttpServletRequest request, HttpServletResponse response) {
		SearchVo search = (SearchVo)parserJsonParameters(request, true, SearchVo.class);

		responseWithDataPagnation(sys.listLogs(search), request, response);

	}
	
	



	

}
