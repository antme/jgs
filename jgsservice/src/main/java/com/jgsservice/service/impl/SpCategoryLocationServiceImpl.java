package com.jgsservice.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jgs.bean.BaseEntity;
import com.jgs.bean.EntityResults;
import com.jgs.dbhelper.DataBaseQueryBuilder;
import com.jgs.dbhelper.DataBaseQueryOpertion;
import com.jgs.exception.ResponseException;
import com.jgs.service.AbstractService;
import com.jgs.util.EcThreadLocal;
import com.jgs.util.EcUtil;
import com.jgs.util.HttpClientUtil;
import com.jgsservice.bean.Category;
import com.jgsservice.bean.Location;
import com.jgsservice.bean.SPSearchKeyword;
import com.jgsservice.bean.ServiceProvider;
import com.jgsservice.bean.SpCategoryLocation;
import com.jgsservice.bean.vo.SpCLVo;
import com.jgsservice.service.ICategoryService;
import com.jgsservice.service.ILocationService;
import com.jgsservice.service.ISpCategoryLocationService;

@Service("spLocation")
public class SpCategoryLocationServiceImpl extends AbstractService implements ISpCategoryLocationService {
	private static Logger logger = LogManager.getLogger(SpCategoryLocationServiceImpl.class);

	@Autowired
	private ILocationService los;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Override
	public SpCategoryLocation save(SpCategoryLocation entity) {

		boolean save = true;
		if (EcUtil.isEmpty(entity.getCategory_id())) {
			save = false;
			logger.error(entity);
		}
		if (EcUtil.isEmpty(entity.getLocation_id())) {
			save = false;
			logger.error(entity);
		}

		if (!save) {
			return null;
		}

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(SpCategoryLocation.TABLE_NAME);
		builder.and(SpCategoryLocation.OWNER_ID, EcThreadLocal.getCurrentUserId());
		builder.and(SpCategoryLocation.CATEGORY_ID, entity.getCategory_id());
		builder.and(SpCategoryLocation.LOCATION_ID, entity.getLocation_id());

		Location loc = (Location) this.dao.findById(entity.getLocation_id(), Location.TABLE_NAME, Location.class);
		String locationDefAddress = "";
		if (!EcUtil.isEmpty(loc.getDefaultAddress())) {
			locationDefAddress = loc.getDefaultAddress();
		}

		Map<String, Object> location = new HashMap<String, Object>();

		if (!EcUtil.isEmpty(loc.getLat()) && loc.getLat() > 0) {
			location.put("lat", loc.getLat());
			location.put("lng", loc.getLng());
		}

		if (EcUtil.isEmpty(location)) {
			String address = los.getLocationString(entity.getLocation_id(), locationDefAddress);
			location = HttpClientUtil.getLngAndLat(address);
		}

		if (!EcUtil.isEmpty(location)) {
			entity.setLng(EcUtil.getDouble(location.get("lng"), null));
			entity.setLat(EcUtil.getDouble(location.get("lat"), null));
		}

		SpCategoryLocation old = (SpCategoryLocation) this.dao.findOneByQuery(builder, SpCategoryLocation.class);

		if (old != null) {
			updateLocationInfo(old, loc);
			updateCategoryInfo(old, old.getCategory_id());
			this.dao.updateById(old);
			// do nothing
		} else {
			updateLocationInfo(entity, loc);
			updateCategoryInfo(entity, entity.getCategory_id());
			dao.insert(entity);
		}
		return entity;
	}
	
	
	public void updateLocationInfo(SpCategoryLocation clo, Location location) {
		Location city = null;
		city = (Location) this.dao.findById(location.getParent_id(), Location.TABLE_NAME, Location.class);

		if (clo.getCityId() == null) {
			clo.setCityId(city.getId());
		}

		if (clo.getProvinceId() == null) {
			Location province = (Location) this.dao.findById(city.getParent_id(), Location.TABLE_NAME, Location.class);
			clo.setProvinceId(province.getId());
		}
	}
	public void updateLocationInfo(SpCategoryLocation clo, String locationId){
		Location location = (Location) this.dao.findById(locationId, Location.TABLE_NAME, Location.class);
		updateLocationInfo(clo, location);
	}
	
	public void updateCategoryInfo(SpCategoryLocation clo, String categoryId) {
		Category category = (Category) this.dao.findById(categoryId, Category.TABLE_NAME, Category.class);
		
		Category sencondCate = null;
		sencondCate = (Category) this.dao.findById(category.getParent_id(), Category.TABLE_NAME, Category.class);

		if (clo.getSecondCategoryId() == null) {
			clo.setSecondCategoryId(sencondCate.getId());
		}

		if (clo.getPrimaryCategoryId() == null) {
			Category province = (Category) this.dao.findById(sencondCate.getParent_id(), Category.TABLE_NAME, Category.class);
			clo.setPrimaryCategoryId(province.getId());
		}
	}
	
	
	public void updateSpCategoryLocationGeoInfo(Location location) {

		String locationDefAddress = "";
		if (!EcUtil.isEmpty(location.getDefaultAddress())) {
			locationDefAddress = location.getDefaultAddress();
		}
		String address = los.getLocationString(location.getId(), locationDefAddress);

		Map<String, Object> locationMap = HttpClientUtil.getLngAndLat(address);
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(SpCategoryLocation.TABLE_NAME);
		builder.and(SpCategoryLocation.LOCATION_ID, location.getId());

		List<SpCategoryLocation> list = this.dao.listByQuery(builder, SpCategoryLocation.class);
		if (location != null) {

			if (!EcUtil.isEmpty(locationMap)) {
				for (SpCategoryLocation entity : list) {
					entity.setLng(EcUtil.getDouble(locationMap.get("lng"), null));
					entity.setLat(EcUtil.getDouble(locationMap.get("lat"), null));
					this.dao.updateById(entity);
				}
			}

		}

	}

	@Override
	public EntityResults<SpCategoryLocation> list(SpCategoryLocation entity) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(SpCategoryLocation.TABLE_NAME);
		if (entity.getOwner_Id() != null) {
			builder.and(SpCategoryLocation.OWNER_ID, entity.getOwner_Id());
		}
		if (entity.getCategory_id() != null) {
			builder.and(SpCategoryLocation.CATEGORY_ID, entity.getCategory_id());
		}
		if (entity.getLocation_id() != null) {
			builder.and(SpCategoryLocation.LOCATION_ID, entity.getLocation_id());
		}
		return dao.listByQueryWithPagnation(builder, SpCategoryLocation.class);
	}

	@Override
	public Map<String, Object> listMy(SpCategoryLocation entity) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(SpCategoryLocation.TABLE_NAME);
		builder.and(SpCategoryLocation.OWNER_ID, EcThreadLocal.getCurrentUserId());
		
		if (entity.getCategory_id() != null) {
			builder.and(SpCategoryLocation.CATEGORY_ID, entity.getCategory_id());
		}
		if (entity.getLocation_id() != null) {
			builder.and(SpCategoryLocation.LOCATION_ID, entity.getLocation_id());
		}
		List<SpCategoryLocation> list = dao.listByQuery(builder, SpCategoryLocation.class);

		Set<String> ids = new HashSet<String>();
		for (SpCategoryLocation item : list) {
			ids.add(item.getCategory_id());
			if (item.getSecondCategoryId() != null) {
				ids.add(item.getSecondCategoryId());
			}
			if (item.getPrimaryCategoryId() != null) {
				ids.add(item.getPrimaryCategoryId());
			}
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", ids);
		return result;
	}

	@Override
	public void delete(SpCategoryLocation entity) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(SpCategoryLocation.TABLE_NAME);
		if(EcUtil.isValid(entity.getId())){
			builder.and(SpCategoryLocation.ID, entity.getId());
			dao.deleteByQuery(builder);
		} else {
			builder.and(SpCategoryLocation.CATEGORY_ID, entity.getCategory_id());
			builder.and(SpCategoryLocation.LOCATION_ID, entity.getLocation_id());
			builder.and(SpCategoryLocation.OWNER_ID, EcThreadLocal.getCurrentUserId());
			dao.deleteByQuery(builder);
		}
	
		
	}

	// 服务商注册的时候初始化主服务类型和地址的数据
	public void initSpCategoryLocation(ServiceProvider sp) {
		ServiceProvider spinfo = (ServiceProvider) this.dao.findById(sp.getId(), ServiceProvider.TABLE_NAME, ServiceProvider.class);

		String[] ids = spinfo.getSpServiceType().split(",");
		for (String id : ids) {
			DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Category.TABLE_NAME);
			builder.and(Category.PARENT_ID, id);
			builder.limitColumns(new String[] { Category.PARENT_ID });
			Category category = (Category) this.dao.findByKeyValue(Category.PARENT_ID, id, Category.TABLE_NAME, Category.class);
			if (category != null) {
				SpCategoryLocation spcl = new SpCategoryLocation();
				spcl.setCategory_id(category.getId());
				spcl.setOwner_Id(EcThreadLocal.getCurrentUserId());
				spcl.setLocation_id(spinfo.getSpLocationAreaId());

				spcl.setLng(spinfo.getLng());
				spcl.setLat(spinfo.getLat());
				save(spcl);
			}

		}

	}

	@Override
    public List<ServiceProvider> searchSp(SpCategoryLocation entity) {
		List<ServiceProvider> result = new ArrayList<ServiceProvider>();
		
	    //get all children's id
	    List<String> cateIds = new ArrayList<String>();
	    if(EcUtil.isValid(entity.getCategory_id())){
	    	cateIds.add(entity.getCategory_id());
	    	cateIds.addAll(categoryService.getAllChildren(cateIds));
	    }
	    List<String> loIds = new ArrayList<String>();
	    if(EcUtil.isValid(entity.getLocation_id())){
	    	loIds.add(entity.getLocation_id());
	    	loIds.addAll(los.getAllChildren(loIds));
	    }

	    //search all spcategorylocation
		if(EcUtil.isValid(cateIds)){
		    DataBaseQueryBuilder builder = new DataBaseQueryBuilder(SpCategoryLocation.TABLE_NAME);
			builder.and(DataBaseQueryOpertion.IN, SpCategoryLocation.CATEGORY_ID, cateIds);
			if(EcUtil.isValid(loIds)){
				builder.and(DataBaseQueryOpertion.IN, SpCategoryLocation.LOCATION_ID, loIds);
			}
			builder.limitColumns(new String[] {SpCategoryLocation.OWNER_ID});
			List<String> spids = new ArrayList<String>();
			List<SpCategoryLocation> sclList = dao.listByQuery(builder, SpCategoryLocation.class);
			for(SpCategoryLocation item : sclList){
				spids.add(item.getOwner_Id());
			}
			if(EcUtil.isValid(spids)){
				DataBaseQueryBuilder builderSP = new DataBaseQueryBuilder(ServiceProvider.TABLE_NAME);
				builderSP.and(DataBaseQueryOpertion.IN, ServiceProvider.USER_ID, spids);
				builderSP.limitColumns(new String[] {ServiceProvider.SP_STORE_IMAGE,ServiceProvider.SP_USER_NAME,ServiceProvider.SP_COMPANY_NAME,ServiceProvider.SP_SERVICE_TYPE, ServiceProvider.SP_SERVICE_TYPE_STR, ServiceProvider.SP_LOCATION,ServiceProvider.SP_CONTACT_MOBILE_PHONE});
				result.addAll(dao.listByQuery(builderSP, ServiceProvider.class));
			}
		}
		
		if (EcUtil.isValid(entity.getLocation_id())) {
			DataBaseQueryBuilder keywordQuery = new DataBaseQueryBuilder(SPSearchKeyword.TABLE_NAME);
			Location location = (Location) this.dao.findById(entity.getLocation_id(), Location.TABLE_NAME, Location.class);
			if (location != null) {

				Location parentLocation = (Location) this.dao.findById(location.getParent_id(), Location.TABLE_NAME, Location.class);
				if (parentLocation != null) {
					keywordQuery.and(SPSearchKeyword.LOCATION_NAME, parentLocation.getName());
					SPSearchKeyword keyword = (SPSearchKeyword) this.dao.findOneByQuery(keywordQuery, SPSearchKeyword.class);
					if (keyword != null) {
						keyword.setSearchTimes(keyword.getSearchTimes() + 1);
						this.dao.updateById(keyword);
					} else {
						keyword = new SPSearchKeyword();
						keyword.setLocationName(parentLocation.getName());
						keyword.setSearchTimes(1);
						this.dao.insert(keyword);
					}
				}

			}

		}

		if (EcUtil.isValid(entity.getCategory_id())) {
			DataBaseQueryBuilder keywordQuery = new DataBaseQueryBuilder(SPSearchKeyword.TABLE_NAME);
			Category category = (Category) this.dao.findById(entity.getCategory_id(), Category.TABLE_NAME, Category.class);
			keywordQuery.and(SPSearchKeyword.CATEGORY_NAME, category.getName());
			SPSearchKeyword keyword = (SPSearchKeyword) this.dao.findOneByQuery(keywordQuery, SPSearchKeyword.class);
			if (keyword != null) {
				keyword.setSearchTimes(keyword.getSearchTimes() + 1);
				this.dao.updateById(keyword);
			} else {
				keyword = new SPSearchKeyword();
				keyword.setCategoryName(category.getName());
				keyword.setSearchTimes(1);
				this.dao.insert(keyword);
			}

		}
		return result;
    }

	@Transactional
	@Override
    public void saveOrDeleteBatchSelf(List<SpCLVo> list) {
	    if(list.isEmpty()){
	    	throw new ResponseException("您没有提交任何数据");
	    }
	    for(SpCLVo entity : list){
	    	entity.setOwner_Id(EcThreadLocal.getCurrentUserId());
	    	SpCategoryLocation slp = (SpCategoryLocation) EcUtil.toEntity(entity.toMap(), SpCategoryLocation.class);
	    	if(Boolean.TRUE.equals(entity.getIsVisible())){//add or update
	    		save(slp);
	    	} else {
	    		delete(slp);
	    	}
	    }
    }
	
	public List<SpCategoryLocation> listSpCategoryLocationByCate(Category cate) {
		List<SpCategoryLocation> spCateList = new ArrayList<SpCategoryLocation>();
		if (cate != null) {
			DataBaseQueryBuilder spLocationQuery = new DataBaseQueryBuilder(SpCategoryLocation.TABLE_NAME);
			spLocationQuery.or(DataBaseQueryOpertion.LIKE, SpCategoryLocation.CATEGORY_ID, cate.getId());
			spLocationQuery.or(DataBaseQueryOpertion.LIKE, SpCategoryLocation.CATEGORY_ID, cate.getParent_id());
			spCateList = this.dao.listByQuery(spLocationQuery, SpCategoryLocation.class);

		}
		return spCateList;

	}
	
	public Map<String, Object> listSearchSpKeywords() {
		Map<String, Object> results = new HashMap<String, Object>();
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(SPSearchKeyword.TABLE_NAME);
		query.and(DataBaseQueryOpertion.NOT_NULL, SPSearchKeyword.LOCATION_NAME);
		query.orderBy(SPSearchKeyword.SEARCH_TIMES, false);
		query.limitColumns(new String[] { SPSearchKeyword.LOCATION_NAME });

		results.put("location", this.dao.listByQueryWithPagnation(query, SPSearchKeyword.class).getEntityList());

		query = new DataBaseQueryBuilder(SPSearchKeyword.TABLE_NAME);
		query.and(DataBaseQueryOpertion.NOT_NULL, SPSearchKeyword.CATEGORY_NAME);
		query.orderBy(SPSearchKeyword.SEARCH_TIMES, false);
		query.limitColumns(new String[] { SPSearchKeyword.CATEGORY_NAME });

		results.put("category", this.dao.listByQueryWithPagnation(query, SPSearchKeyword.class).getEntityList());

		return results;
	}

	
}
