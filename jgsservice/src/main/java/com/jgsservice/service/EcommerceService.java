package com.jgsservice.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.jgs.dbhelper.DataBaseQueryBuilder;
import com.jgs.dbhelper.DataBaseQueryOpertion;
import com.jgs.service.AbstractService;
import com.jgs.util.DateUtil;
import com.jgs.util.EcUtil;
import com.jgsservice.bean.ServiceOrder;
import com.jgsservice.bean.vo.SearchVo;

public abstract class EcommerceService extends AbstractService {


	@Autowired
	protected ICategoryService cateService;
	
	@Autowired
	protected ILocationService locationService;
	
	public DataBaseQueryBuilder mergeCommonSearchQuery(SearchVo search, DataBaseQueryBuilder builder, String table, String closeKey, boolean defaultQuery) {
		DataBaseQueryBuilder searchBuilder = new DataBaseQueryBuilder(table);
		
	
		
		if(!EcUtil.isEmpty(search.getSpId())){
			searchBuilder.and(ServiceOrder.SP_ID, search.getSpId());
			defaultQuery = false;
		}
		
		if(!EcUtil.isEmpty(search.getMfcId())){
			searchBuilder.and(ServiceOrder.MFC_ID, search.getMfcId());
			defaultQuery = false;
		}
		

		if (!EcUtil.isEmpty(search.getStartDate())) {
			searchBuilder.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, closeKey, search.getStartDate());
			defaultQuery = false;
		}

		if (!EcUtil.isEmpty(search.getEndDate())) {
			Date endDate = getQueryEndDate(search);

			searchBuilder.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, closeKey, endDate);
			defaultQuery = false;
		}
		
		if(defaultQuery && EcUtil.isEmpty(search.getDateType())){
			search.setDateType("0");
		}
		if (!EcUtil.isEmpty(search.getDateType())) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			if (search.getDateType().equalsIgnoreCase("0")) {
				c.set(Calendar.DAY_OF_MONTH, 1);
				searchBuilder.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, closeKey, new Date());
				searchBuilder.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, closeKey, c.getTime());

			} else if (search.getDateType().equalsIgnoreCase("1")) {
				c.add(Calendar.MONTH, -1);
				c.set(Calendar.DAY_OF_MONTH, 1);

				searchBuilder.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, closeKey, c.getTime());
				c.set(Calendar.DAY_OF_MONTH, c.getMaximum(Calendar.DAY_OF_MONTH) + 1);
				searchBuilder.and(DataBaseQueryOpertion.LESS_THAN, closeKey, c.getTime());

			} else if (search.getDateType().equalsIgnoreCase("2")) {
				c.add(Calendar.MONTH, -3);		
				c.set(Calendar.DAY_OF_MONTH, 1);
				searchBuilder.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, closeKey, c.getTime());
				
				c.add(Calendar.MONTH, 2);		
				c.set(Calendar.DAY_OF_MONTH, c.getMaximum(Calendar.DAY_OF_MONTH) + 1);
				searchBuilder.and(DataBaseQueryOpertion.LESS_THAN, closeKey, c.getTime());
			}

		}

		if (!EcUtil.isEmpty(searchBuilder.getQueryStr())) {
			builder = builder.and(searchBuilder);
		}
		return builder;
	}
	
	

	public Date getQueryEndDate(SearchVo search) {
	    Date endDate = search.getEndDate();
	    if(DateUtil.getDateStringTime(endDate).endsWith("00:00:00")){
	    	endDate = new Date(endDate.getTime() + 86400000L - 1L);
	    }
	    return endDate;
    }
	
	
	
	protected void mergeLocationAndCateQuery(SearchVo search, DataBaseQueryBuilder builder, String cateKey, String locationKey) {

		buildCateSearch(search, builder, cateKey);

		buildLocationSearch(search, builder, locationKey);
	}



	public void buildCateSearch(SearchVo search, DataBaseQueryBuilder builder, String cateKey) {
	    String cateId = search.getCategoryId();
		if (EcUtil.isValid(cateId) && !search.getCategoryId().equalsIgnoreCase("0")) {
			mergeCateQuery(cateId, builder, cateKey);
		} else if (EcUtil.isValid(search.getCategoryParentId()) && !search.getCategoryParentId().equalsIgnoreCase("0")) {
			mergeCateQuery(search.getCategoryParentId(), builder, cateKey);
		} else if (EcUtil.isValid(search.getCategoryGrandpaId()) && !search.getCategoryGrandpaId().equalsIgnoreCase("0")) {
			mergeCateQuery(search.getCategoryGrandpaId(), builder, cateKey);
		}
    }



	public void mergeCateQuery(String cateID, DataBaseQueryBuilder builder, String cateKey) {
	    List<String> ids = new ArrayList<String>();
	    ids.add(cateID);
	    List<String> cateIds = cateService.getAllChildren(ids);
	    Set<String> searchIds = new HashSet<String>();
	    for (String id : cateIds) {
	    	searchIds.add(id);

	    }
	    searchIds.addAll(ids);
	    builder.and(DataBaseQueryOpertion.IN, cateKey, searchIds);
    }

	public void buildLocationSearch(SearchVo search, DataBaseQueryBuilder builder, String locationKey) {
	    String countyId = search.getCountyId();
		if (EcUtil.isValid(countyId) && !countyId.equalsIgnoreCase("0")) {

			buildLocationSearch(builder, countyId, locationKey);
		} else if (EcUtil.isValid(search.getCityId()) && !search.getCityId().equalsIgnoreCase("0")) {
			buildLocationSearch(builder, search.getCityId(), locationKey);

		} else if (EcUtil.isValid(search.getProvinceId()) && !search.getProvinceId().equalsIgnoreCase("0")) {
			buildLocationSearch(builder, search.getProvinceId(), locationKey);

		}
    }
	

	protected void buildLocationSearch(DataBaseQueryBuilder searchBuilder, String countyId, String locationKey) {
	    List<String> ids = new ArrayList<String>();
	    ids.add(countyId);

	    List<String> cateIds = locationService.getAllChildren(ids);
	    Set<String> searchIds = new HashSet<String>();
	    for (String id : cateIds) {
	    	searchIds.add(id);

	    }
	    searchIds.addAll(ids);
	    searchBuilder.and(DataBaseQueryOpertion.IN, locationKey, searchIds);
    }

}
