package com.zcyservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zcy.annotation.Permission;
import com.zcy.controller.AbstractController;
import com.zcyservice.bean.LocationPrice;
import com.zcyservice.bean.vo.SearchVo;
import com.zcyservice.bean.vo.SpCLVo;
import com.zcyservice.service.ILocationPriceService;
import com.zcyservice.util.PermissionConstants;

@Controller
@RequestMapping("/ecs/locationPrice")
public class LocationPriceController extends AbstractController {

	@Autowired
	private ILocationPriceService locationPriceService;

	private static Logger logger = LogManager.getLogger(LocationPriceController.class);

	@RequestMapping("/save.do")
	@Permission(groupName = PermissionConstants.ADM_SETPRICE_MANAGE, permissionID = PermissionConstants.ADM_SETPRICE_MANAGE)
	public void save(HttpServletRequest request, HttpServletResponse response) {
		List<LocationPrice> list = parserListJsonParameters(request, false, LocationPrice.class);

		locationPriceService.save(list);

		responseWithData(null, request, response);
	}

	@RequestMapping("/delete.do")
	@Permission(groupName = PermissionConstants.ADM_SETPRICE_MANAGE, permissionID = PermissionConstants.ADM_SETPRICE_MANAGE)
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		LocationPrice locationPrice = (LocationPrice) parserJsonParameters(request, false, LocationPrice.class);
		logger.info("delete locationPrice. " + locationPrice.toString());
		locationPriceService.delete(locationPrice);
		responseWithKeyValue("msg", "ok", request, response);
	}
	
	@RequestMapping("/list.do")
	public void list(HttpServletRequest request, HttpServletResponse response) {
		LocationPrice lp = (LocationPrice) parserJsonParameters(request,true, LocationPrice.class);
		responseWithDataPagnation(locationPriceService.list(lp), request, response);
	}
	
	@RequestMapping("/search.do")
	public void searchPriceList(HttpServletRequest request, HttpServletResponse response) {
		LocationPrice lp = (LocationPrice) parserJsonParameters(request,true, LocationPrice.class);
		responseWithDataPagnation(locationPriceService.searchPriceList(lp), request, response);
	}
	
	@RequestMapping("/adminsearch.do")
	public void searchPriceListForAdmin(HttpServletRequest request, HttpServletResponse response) {
		SearchVo svo = (SearchVo) parserJsonParameters(request,true, SearchVo.class);
		responseWithDataPagnation(locationPriceService.searchPriceListForAdmin(svo), request, response);
	}

	public ILocationPriceService getLocationPriceService() {
    	return locationPriceService;
    }

	public void setLocationPriceService(ILocationPriceService locationPriceService) {
    	this.locationPriceService = locationPriceService;
    }
	
	
}