package com.jgsservice.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jgs.bean.EntityResults;
import com.jgsservice.bean.Advertisement;

public interface IAdvertisementService {
	
	public void addAdvertisement(Advertisement adv);

	public EntityResults<Advertisement> listAdvertisement();
	
	public String upload(Map<String, Object> params);
	
	public String getUploadImage(HttpServletRequest request, String name, String errorMsg);
}
