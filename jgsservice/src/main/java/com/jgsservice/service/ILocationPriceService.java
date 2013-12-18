package com.jgsservice.service;

import java.util.List;
import java.util.Map;

import com.jgs.bean.BaseEntity;
import com.jgs.bean.EntityResults;
import com.jgsservice.bean.LocationPrice;
import com.jgsservice.bean.ServiceOrder;
import com.jgsservice.bean.vo.SearchVo;

public interface ILocationPriceService {

	public LocationPrice save(LocationPrice locationPrice);
	
	public EntityResults<LocationPrice> list(LocationPrice lp);
	
	public void delete(LocationPrice location);
	
	public int getPrice(String locationId, String cateId);

	public void save(List<LocationPrice> list);

	public EntityResults<LocationPrice> searchPriceList(LocationPrice lp);

	public EntityResults<LocationPrice> searchPriceListForAdmin(SearchVo svo);
	
}
