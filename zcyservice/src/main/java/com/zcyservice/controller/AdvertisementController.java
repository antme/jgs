package com.zcyservice.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zcy.annotation.LoginRequired;
import com.zcy.annotation.Permission;
import com.zcy.controller.AbstractController;
import com.zcyservice.bean.Advertisement;
import com.zcyservice.service.IAdvertisementService;
import com.zcyservice.util.PermissionConstants;

@Controller
@RequestMapping("/ecs/ad")
@Permission()
@LoginRequired()
public class AdvertisementController extends AbstractController {

	@Autowired
	private IAdvertisementService adService;

	@RequestMapping("/add.do")
	@Permission(groupName = PermissionConstants.ADM_ADV_MANAGE, permissionID = PermissionConstants.ADM_ADV_MANAGE)
	public void addAdvertisement(HttpServletRequest request, HttpServletResponse response) {
		Advertisement adv = (Advertisement) parserJsonParameters(request, false, Advertisement.class);
		adService.addAdvertisement(adv);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/list.do")
	public void listAds(HttpServletRequest request, HttpServletResponse response) {
		responseWithDataPagnation(adService.listAdvertisement(), request, response);
	}
	
	@RequestMapping("/upload.do")
	@Permission(groupName = PermissionConstants.ADM_ADV_MANAGE, permissionID = PermissionConstants.ADM_ADV_MANAGE)
	public void upload(HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
        MultipartFile uploadFile = multipartRequest.getFile("files"); 
        String webPath = request.getSession().getServletContext().getRealPath("/");
        try {
			InputStream inputStream = uploadFile.getInputStream();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("inputStream", inputStream);
			map.put("webPath", webPath);
			map.put("fileName", uploadFile.getOriginalFilename().trim().replaceAll(" ", ""));
			
			String path = adService.upload(map);
			
			
			Map<String, Object> re = new HashMap<String, Object>();
			re.put("url", path);
			responseWithData(re, request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
