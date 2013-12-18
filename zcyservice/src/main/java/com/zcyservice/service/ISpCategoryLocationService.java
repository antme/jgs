package com.zcyservice.service;

import java.util.List;
import java.util.Map;

import com.zcy.bean.EntityResults;
import com.zcyservice.bean.Category;
import com.zcyservice.bean.Location;
import com.zcyservice.bean.ServiceProvider;
import com.zcyservice.bean.SpCategoryLocation;
import com.zcyservice.bean.vo.SpCLVo;

public interface ISpCategoryLocationService {

	public SpCategoryLocation save(SpCategoryLocation entity);
	
	public void saveOrDeleteBatchSelf(List<SpCLVo> list);
	
	public EntityResults<SpCategoryLocation> list(SpCategoryLocation entity);
	
	public void delete(SpCategoryLocation entity);
	
	public Map<String,Object> listMy(SpCategoryLocation entity);
	
	
	public void initSpCategoryLocation(ServiceProvider sp);
	
	public List<ServiceProvider> searchSp(SpCategoryLocation entity);
	
	
	public List<SpCategoryLocation> listSpCategoryLocationByCate(Category cate);
	
	
	public void updateSpCategoryLocationGeoInfo(Location location);
	
	public void updateLocationInfo(SpCategoryLocation clo, Location location);
	
	public void updateCategoryInfo(SpCategoryLocation clo, String categoryId);
	
	public void updateLocationInfo(SpCategoryLocation clo, String locationId);

	public Map<String, Object> listSearchSpKeywords();
}
