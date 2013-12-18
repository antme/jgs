package com.jgsservice.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jgs.bean.EntityResults;
import com.jgs.dbhelper.DataBaseQueryBuilder;
import com.jgs.dbhelper.DataBaseQueryOpertion;
import com.jgs.service.AbstractService;
import com.jgs.util.EcThreadLocal;
import com.jgs.util.EcUtil;
import com.jgsservice.bean.Category;
import com.jgsservice.bean.Location;
import com.jgsservice.bean.LocationPrice;
import com.jgsservice.bean.ServiceProvider;
import com.jgsservice.bean.SpCategoryLocation;
import com.jgsservice.bean.vo.SearchVo;
import com.jgsservice.service.ICategoryService;
import com.jgsservice.service.ILocationPriceService;
import com.jgsservice.service.ILocationService;

@Service("locationPriceService")
public class LocationPriceServiceImpl extends AbstractService implements ILocationPriceService {

	@Autowired
	private ICategoryService cateService;
	
	@Autowired
	private ILocationService locationService;
	/**
	 * @param location_id
	 *            category_id price must
	 * */
	@Override
	@Transactional
	public LocationPrice save(LocationPrice locationPrice) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(LocationPrice.TABLE_NAME);
		builder.and(LocationPrice.LOCATION_ID, locationPrice.getLocation_id());
		builder.and(LocationPrice.CATEGORY_ID, locationPrice.getCategory_id());
		LocationPrice entity = (LocationPrice) dao.findOneByQuery(builder, LocationPrice.class);

		if (entity == null) {
			dao.insert(locationPrice);
		} else {
			if (locationPrice.getPrice() != null) {
				entity.setPrice(locationPrice.getPrice());
			}
			if (locationPrice.getCategory_id() != null) {
				entity.setCategory_id(locationPrice.getCategory_id());
			}
			if (locationPrice.getLocation_id() != null) {
				entity.setLocation_id(locationPrice.getLocation_id());
			}
			if (locationPrice.getDescription() != null) {
				entity.setDescription(locationPrice.getDescription());
			}
			dao.updateById(entity);
		}

		updateChildren(locationPrice);
		return entity;
	}
	
	@Transactional
	public void save(List<LocationPrice> list) {
		for (LocationPrice price : list) {
			save(price);
		}
	}

	/** 更新子节点价格 */
	private void updateChildren(LocationPrice locationPrice) {
		String locationId = locationPrice.getLocation_id();
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Location.TABLE_NAME);
		builder.and(Location.PARENT_ID, locationId);
		List<Location> children = dao.listByQuery(builder, Location.class);
		for (Location item : children) {
			LocationPrice lp = new LocationPrice();
			lp.setLocation_id(item.getId());
			lp.setCategory_id(locationPrice.getCategory_id());
			lp.setPrice(locationPrice.getPrice());
			save(lp);
		}
	}

	@Override
	public void delete(LocationPrice locationPrice) {
		// check used ?
		dao.deleteById(locationPrice);
	}

	@Override
	public EntityResults<LocationPrice> list(LocationPrice lp) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(LocationPrice.TABLE_NAME);
		if (lp.getPrice() != null) {
			builder.and(LocationPrice.PRICE, EcUtil.getDouble(lp.getPrice(), 0.0));
		}
		if (lp.getLocation_id() != null) {
			builder.and(LocationPrice.LOCATION_ID, lp.getLocation_id());
		}
		if (lp.getCategory_id() != null) {
			builder.and(LocationPrice.CATEGORY_ID, lp.getCategory_id());
		}
		builder.limitColumns(new String[] { LocationPrice.ID, LocationPrice.PRICE, LocationPrice.LOCATION_ID, LocationPrice.CATEGORY_ID, LocationPrice.DESCRIBION });
		return dao.listByQueryWithPagnation(builder, LocationPrice.class);
	}
	
	
	public EntityResults<LocationPrice> searchPriceList(LocationPrice lp) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(LocationPrice.TABLE_NAME);
		builder.join(LocationPrice.TABLE_NAME, Category.TABLE_NAME, LocationPrice.CATEGORY_ID, Category.ID);
		builder.joinColumns(Category.TABLE_NAME, new String[]{Category.NAME});
		builder.limitColumns(lp.getColumnList());
		
		builder.and(LocationPrice.LOCATION_ID, lp.getLocation_id());

		if (lp.getCategory_id() != null) {
			List<String> list = new ArrayList<String>();
			list.add(lp.getCategory_id());

			builder.and(DataBaseQueryOpertion.IN, LocationPrice.CATEGORY_ID, cateService.getAllChildren(list));
		} else {
			builder.and(LocationPrice.CATEGORY_ID, lp.getCategory_id());

		}
		builder.limitColumns(new String[] { LocationPrice.ID, LocationPrice.PRICE, LocationPrice.LOCATION_ID, LocationPrice.CATEGORY_ID, LocationPrice.DESCRIBION });
		return dao.listByQueryWithPagnation(builder, LocationPrice.class);
	}
	
	public EntityResults<LocationPrice> searchPriceListForAdmin(SearchVo vo) {
		DataBaseQueryBuilder cateLocationJoinQuery = new DataBaseQueryBuilder(LocationPrice.TABLE_NAME);
		cateLocationJoinQuery.join(LocationPrice.TABLE_NAME, Location.TABLE_NAME, LocationPrice.LOCATION_ID, Location.ID);
		cateLocationJoinQuery.joinColumns(Location.TABLE_NAME, new String[] { Location.NAME });
		cateLocationJoinQuery.limitColumns(new LocationPrice().getColumnList());

		DataBaseQueryBuilder cateLocationQuery = new DataBaseQueryBuilder(LocationPrice.TABLE_NAME);

		if (!EcUtil.isEmpty(vo.getCityName())) {
			DataBaseQueryBuilder cityQuery = new DataBaseQueryBuilder(Location.TABLE_NAME);
			cityQuery.and(DataBaseQueryOpertion.LIKE, Location.NAME, vo.getCityName());
			cityQuery.limitColumns(new String[] { Location.ID });

			List<Location> cityList = this.dao.listByQuery(cityQuery, Location.class);
			Set<String> cityIds = new HashSet<String>();
			for (Location city : cityList) {
				cityIds.add(city.getId());
			}

			List<String> ids = new ArrayList<String>();

			for (String id : cityIds) {
				if (!ids.contains(id)) {
					ids.add(id);
				}
			}

			List<String> finalIds = locationService.getAllChildren(ids);
			finalIds.addAll(ids);
			cateLocationQuery.and(DataBaseQueryOpertion.IN, LocationPrice.TABLE_NAME + "." + LocationPrice.LOCATION_ID, finalIds);

		}

		if (!EcUtil.isEmpty(vo.getCateGoryName())) {
			DataBaseQueryBuilder cateQuery = new DataBaseQueryBuilder(Category.TABLE_NAME);
			cateQuery.and(DataBaseQueryOpertion.LIKE, Category.NAME, vo.getCateGoryName());
			cateQuery.limitColumns(new String[] { Category.ID });

			List<Category> cateList = this.dao.listByQuery(cateQuery, Category.class);
			Set<String> cateIds = new HashSet<String>();
			for (Category cate : cateList) {
				cateIds.add(cate.getId());
			}

			cateLocationQuery.and(DataBaseQueryOpertion.IN, LocationPrice.TABLE_NAME + "." + LocationPrice.CATEGORY_ID, cateIds);

		}

		if (cateLocationQuery.getQueryStr() != null) {
			cateLocationJoinQuery = cateLocationJoinQuery.and(cateLocationQuery);
		}

		EntityResults<LocationPrice> clList = this.dao.listByQueryWithPagnation(cateLocationJoinQuery, LocationPrice.class);

		List<LocationPrice> locationLost = clList.getEntityList();
		Set<String> locationIds = new HashSet<String>();
		Set<String> cateIds = new HashSet<String>();

		for (LocationPrice location : locationLost) {
			locationIds.add(location.getLocation_id());
			cateIds.add(location.getCategory_id());
		}

		DataBaseQueryBuilder locationQuery = new DataBaseQueryBuilder(Location.TABLE_NAME);
		locationQuery.and(DataBaseQueryOpertion.IN, Location.ID, locationIds);
		List<Location> llist = this.dao.listByQuery(locationQuery, Location.class);
		Map<String, Location> locationMap = new HashMap<String, Location>();
		for (Location l : llist) {
			locationMap.put(l.getId(), l);
		}

		DataBaseQueryBuilder cateQuery = new DataBaseQueryBuilder(Category.TABLE_NAME);
		cateQuery.and(DataBaseQueryOpertion.IN, Category.ID, cateIds);
		List<Category> clist = this.dao.listByQuery(cateQuery, Category.class);

		Map<String, Category> cateMap = new HashMap<String, Category>();
		for (Category c : clist) {
			cateMap.put(c.getId(), c);
		}

		for (LocationPrice location : locationLost) {
			Category category = cateMap.get(location.getCategory_id());
			if (category != null) {
				location.setCategoryName(category.getName());
			}
			Location loc = locationMap.get(location.getLocation_id());
			if (loc != null) {
				location.setLocationName(locationService.getLocationString(loc.getId(), ""));
			}
		}

		return clList;
	}

	public int getPrice(String locationId, String cateId) {

		if (EcUtil.isEmpty(locationId) || EcUtil.isEmpty(cateId)) {
			return 0;
		}

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(LocationPrice.TABLE_NAME);

		builder.and(LocationPrice.LOCATION_ID, locationId);

		builder.and(LocationPrice.CATEGORY_ID, cateId);

		LocationPrice lp = (LocationPrice) this.dao.findOneByQuery(builder, LocationPrice.class);

		if (lp != null) {
			return (int) lp.getPrice().doubleValue();
		}

		return 0;
	}

}
