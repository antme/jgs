package com.zcyservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zcy.annotation.LoginRequired;
import com.zcy.annotation.Permission;
import com.zcy.controller.AbstractController;
import com.zcyservice.bean.Location;
import com.zcyservice.bean.vo.LocationSearchVO;
import com.zcyservice.service.ILocationService;
import com.zcyservice.util.PermissionConstants;

@Controller
@RequestMapping("/ecs/location")
public class LocationController extends AbstractController {

	@Autowired
	private ILocationService locationService;

	private static Logger logger = LogManager.getLogger(LocationController.class);

	@RequestMapping("/save.do")
	@Permission(groupName = PermissionConstants.ADM_LOCATION_MANAGE, permissionID = PermissionConstants.ADM_LOCATION_MANAGE)
	public void save(HttpServletRequest request, HttpServletResponse response) {
		Location location = (Location) parserJsonParameters(request, false, Location.class);
		logger.info("save location. " + location.toString());
		locationService.save(location);
		responseWithKeyValue(Location.ID, location.getId(), request, response);
	}

	@RequestMapping("/delete.do")
	@Permission(groupName = PermissionConstants.ADM_LOCATION_MANAGE, permissionID = PermissionConstants.ADM_LOCATION_MANAGE)
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		Location location = (Location) parserJsonParameters(request, false, Location.class);
		logger.info("delete location. " + location.toString());
		locationService.delete(location);
		responseWithKeyValue("msg", "ok", request, response);
	}
	
	@RequestMapping("/list.do")
	@LoginRequired(required = false)
	public void list(HttpServletRequest request, HttpServletResponse response) {
		responseWithListData(locationService.list(), request, response);
	}
	
	@RequestMapping("/listbyparent.do")
	public void listbyparent(HttpServletRequest request, HttpServletResponse response) {
		LocationSearchVO lsvo = (LocationSearchVO) parserJsonParameters(request,true, LocationSearchVO.class);
		
		responseWithListData(locationService.listLocationByParent(lsvo), request, response);
	}

	public ILocationService getLocationService() {
    	return locationService;
    }

	public void setLocationService(ILocationService locationService) {
    	this.locationService = locationService;
    }
	
	
}