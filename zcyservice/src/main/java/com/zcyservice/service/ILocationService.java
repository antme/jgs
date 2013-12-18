package com.zcyservice.service;

import java.util.List;
import java.util.Map;

import com.zcy.bean.EntityResults;
import com.zcyservice.bean.Location;
import com.zcyservice.bean.vo.LocationSearchVO;

public interface ILocationService {

	public Location save(Location location);
	
	public List<Location> list();
	
	public void delete(Location location);
	
	public List<Location> listLocationByParent(LocationSearchVO locationSearchVO);
	
	
	public String getLocationString(String locationId, String address);
	
	
	public Location getLocationByLngAndLat(String address, String lng, String lat);
	
	
	public List<String> getAllChildren(List<String> ids);

	
}
