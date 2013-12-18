package com.zcyservice.service;

import java.util.List;
import java.util.Map;

import com.zcy.bean.BaseEntity;
import com.zcy.bean.EntityResults;
import com.zcyservice.bean.LocationPrice;
import com.zcyservice.bean.ServiceOrder;
import com.zcyservice.bean.vo.SearchVo;

public interface ILocationPriceService {

	public LocationPrice save(LocationPrice locationPrice);
	
	public EntityResults<LocationPrice> list(LocationPrice lp);
	
	public void delete(LocationPrice location);
	
	public int getPrice(String locationId, String cateId);

	public void save(List<LocationPrice> list);

	public EntityResults<LocationPrice> searchPriceList(LocationPrice lp);

	public EntityResults<LocationPrice> searchPriceListForAdmin(SearchVo svo);
	
}
