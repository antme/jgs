package com.zcyservice.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zcy.bean.EntityResults;
import com.zcy.bean.Pagination;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcy.dbhelper.DataBaseQueryOpertion;
import com.zcy.exception.ResponseException;
import com.zcy.service.AbstractService;
import com.zcy.util.EcThreadLocal;
import com.zcy.util.EcUtil;
import com.zcy.util.HttpClientUtil;
import com.zcyservice.bean.Location;
import com.zcyservice.bean.SpCategoryLocation;
import com.zcyservice.bean.vo.LocationSearchVO;
import com.zcyservice.service.ILocationService;
import com.zcyservice.service.ISpCategoryLocationService;

@Service("locationService")
public class LocationServiceImpl extends AbstractService implements ILocationService {
	private static Logger logger = LogManager.getLogger(LocationServiceImpl.class);
	
	@Autowired
	public ISpCategoryLocationService ispCls;

	@Override
    public Location save(Location location) {
		Location old = null;
		if (!EcUtil.isEmpty(location.getId())) {
			DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Location.TABLE_NAME);
			builder.and(Location.ID, location.getId());
			old = (Location) dao.findOneByQuery(builder, Location.class);
		}
		if (EcUtil.isEmpty(location.getParent_id()) && old != null) {
			location.setParent_id(old.getParent_id());
		}
		if(location.getParent_id() == null){
			location.setLevel("1");
		} else {
			DataBaseQueryBuilder parentBuilder = new DataBaseQueryBuilder(Location.TABLE_NAME);
			parentBuilder.and(Location.ID, location.getParent_id());
			Location parent = (Location) dao.findOneByQuery(parentBuilder, Location.class);
			Integer level = Integer.valueOf(parent.getLevel()) + 1;
			location.setLevel(level.toString());
		}
		
		if (location.id == null) {
			if (location.getIsVisible() == null) {
				location.setIsVisible(false);// 默认false
			}
			if (location.getIsHot() == null) {
				location.setIsHot(false);// 默认false
			}

			dao.insert(location);

			String address = getLocationString(location.getId(), location.getDefaultAddress());
			Map<String, Object> locationMap = HttpClientUtil.getLngAndLat(address);
			if (!EcUtil.isEmpty(locationMap)) {
				location.setLng(EcUtil.getDouble(locationMap.get("lng"), null));
				location.setLat(EcUtil.getDouble(locationMap.get("lat"), null));
				this.dao.updateById(location);
			}
		} else {
		

			dao.updateById(location);
			
			String defaultAddress = old.getDefaultAddress() == null? "" : old.getDefaultAddress();
			String oldDefaultAddress = location.getDefaultAddress() == null? "" : location.getDefaultAddress();
	
			if(!old.getName().equalsIgnoreCase(location.getName()) || !defaultAddress.equalsIgnoreCase(oldDefaultAddress)){
				if(old.getLevel().equalsIgnoreCase("3") || old.getLevel().equalsIgnoreCase("2")){
					ispCls.updateSpCategoryLocationGeoInfo(location);
				}
			}
			updateParent(location);
			updateChildren(location);
		}
	    return location;
    }

	/**更新父节点： 父影响子  子影响父*/
	private void updateParent(Location location){
		if(Boolean.TRUE.equals(location.getIsVisible()) && EcUtil.isValid(location.getParent_id())){
			Location parent = (Location) dao.findById(location.getParent_id(), Location.TABLE_NAME, Location.class);
			if(Boolean.FALSE.equals(parent.getIsVisible())){
				parent.setIsVisible(true);
				dao.updateById(parent);
				updateParent(parent);
			}
		}
	}
	
	/**更新子节点： 父影响子  子影响父*/
	private void updateChildren(Location location){
		Boolean isVisible = location.getIsVisible();
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Location.TABLE_NAME);
		builder.and(Location.PARENT_ID, location.getId());
		List<Location> children = dao.listByQuery(builder, Location.class);
		for(Location lo : children){
			lo.setIsVisible(isVisible);
			dao.updateById(lo);
			updateChildren(lo);
		}
	}
	
	@Override
    public void delete(Location location) {
		String id = location.getId();
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Location.TABLE_NAME);
		builder.and(Location.PARENT_ID, id);
		int children = dao.count(builder);
		if(children > 0){
			throw new ResponseException("请先删除子节点");
		}
		dao.deleteById(location);
    }

	@Override
    public List<Location> list() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Location.TABLE_NAME);
/*		User user = (User) dao.findByKeyValue(User.ID, EcThreadLocal.getCurrentUserId(), User.TABLE_NAME, User.class);
		String role = user.getRoleName();
		if(Role.ADMIN.name().equals(role) || Role.SUPPER_ADMIN.name().equals(role) ){
			//do nothing
		} else {
			builder.and(Location.IS_VISIBLE, 1);
		}*/
		builder.orderBy(Location.LEVEL, false);
		builder.orderBy(Location.SORT_INDEX, true);	
		List<Location> itemList = dao.listByQuery(builder, Location.class);
		
		Map<String,Location> map = new LinkedHashMap<String,Location>();
		for(Location item : itemList){
			map.put(item.getId(), item);
		}
		
		List<Location> list = new ArrayList<Location>();
		for(Location item : itemList){
			if(item.getParent_id() != null){
				if(map.containsKey(item.getParent_id())){
					map.get(item.getParent_id()).getChilden().add(item);
				}
			} else {
				list.add(map.get(item.getId()));
			}
		}

		return list;
    }

	@Override
	public List<Location> listLocationByParent(LocationSearchVO locationSearchVO) {
		String parentId = locationSearchVO.getParent();
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Location.TABLE_NAME);
		if (EcUtil.isEmpty(parentId)) {
			builder.and(DataBaseQueryOpertion.NULL, Location.PARENT_ID);
		} else {
			builder.and(Location.PARENT_ID, parentId);
		}

		Set<String> ids = new HashSet<String>();
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(SpCategoryLocation.TABLE_NAME);
		query.and(SpCategoryLocation.OWNER_ID, EcThreadLocal.getCurrentUserId());
		List<SpCategoryLocation> spCateLocationList = this.dao.listByQuery(query, SpCategoryLocation.class);

		for (SpCategoryLocation spl : spCateLocationList) {
			ids.add(spl.getLocation_id());
			ids.add(spl.getCityId());
			ids.add(spl.getProvinceId());
		}

		List<Location> locationList = dao.listByQuery(builder, Location.class);

		if (ids.size() > 0) {
			for (Location location : locationList) {

				if (ids.contains(location.getId())) {
					location.setChecked("checked");
				}
			}
		}
		return locationList;
	}
	
	public String getLocationString(String locationId, String address) {
		return getLocationRecu(locationId, address) + address;
	}

	private String getLocationRecu(String locationId, String address) {
		address = address == null ? "" : address;
		String locationStr = "";
		Location location = (Location) this.dao.findById(locationId, Location.TABLE_NAME, Location.class);

		if (location != null) {
			locationStr = location.getName();

			if (location.getParent_id() != null) {
				String locationString = getLocationRecu(location.getParent_id(), address);
				if (!address.contains(locationString) && !locationStr.contains(locationString)) {
					locationStr = locationString + locationStr;
				}
			}
		}
		return locationStr;
	}

	
	public Location getLocationByLngAndLat(String address, String lng, String lat){
		Location findLocation = null;

		if (lng != null && lat != null) {

			Map<String, Object> addressInfo = HttpClientUtil.getAddressByLngAndLat(lng, lat, null);
			if (!EcUtil.isEmpty(addressInfo)) {
				String provice = (String) addressInfo.get("province");
				String city = (String) addressInfo.get("city");
				String district = (String) addressInfo.get("district");
				logger.info(String.format("【%s】获取百度对应省市区区域[%s][%s][%s]", address, provice, city, district));
				findLocation = findLoation(lng, lat, findLocation, provice, city, district);
				
				if(findLocation == null){
					Map<String, Object> v2addressInfo = HttpClientUtil.getAddressByLngAndLatV2(lng, lat, null);
					
					if(v2addressInfo.get("result")!=null){
						Map<String, Object> result = (Map<String, Object>) v2addressInfo.get("result");
						Map<String, Object> addressComponent = (Map<String, Object>) result.get("addressComponent");
						if(addressComponent!=null){
							
							findLocation = findLoation(lng, lat, findLocation, (String)addressComponent.get("province"), (String)addressComponent.get("city"), (String)addressComponent.get("district"));

						}
					}

				}

			}
		}
		
		return findLocation;
	}

	public Location findLoation(String lng, String lat, Location findLocation, String provice, String city, String district) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Location.TABLE_NAME);
		builder.or(DataBaseQueryOpertion.LIKE, Location.NAME, provice);
		builder.or(DataBaseQueryOpertion.LIKE, Location.NAME, city);
		builder.or(DataBaseQueryOpertion.LIKE, Location.NAME, district);

		DataBaseQueryBuilder levelQuery = new DataBaseQueryBuilder(Location.TABLE_NAME);
		levelQuery.and(Location.LEVEL, "3");
		// levelQuery.or(Location.LEVEL, "2");
		builder.and(levelQuery);

		List<Location> locationList = this.dao.listByQuery(builder, Location.class);

		double minDistance = 0;
		for (Location location : locationList) {
			if (location.getParent_id() != null) {

				if (location.getLat() == null || (location.getLat() != null && location.getLat() < 1)) {
					String locaddress = getLocationString(location.getId(), location.getDefaultAddress());
					Map<String, Object> locationMap = HttpClientUtil.getLngAndLat(locaddress);
					if (!EcUtil.isEmpty(locationMap)) {
						location.setLng(EcUtil.getDouble(locationMap.get("lng"), null));
						location.setLat(EcUtil.getDouble(locationMap.get("lat"), null));
						this.dao.updateById(location);
					}
				}

				if (location.getLat() != null) {
					double distance = EcUtil.GetDistance(location.getLng(), location.getLat(), Double.parseDouble(lng), Double.parseDouble(lat));
					if (findLocation == null) {
						findLocation = location;
						minDistance = distance;
					} else {
						if (distance < minDistance) {
							findLocation = location;
						}
					}
				}
			}
		}
		return findLocation;
	}
	
	public List<String> getAllChildren(List<String> ids){
		List<String> result = new ArrayList<String>();
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Location.TABLE_NAME);
		builder.and(DataBaseQueryOpertion.IN, Location.PARENT_ID, ids);
		builder.limitColumns(new String[] {Location.ID});
		List<Location> list = dao.listByQuery(builder, Location.class);
		for(Location item : list){
			result.add(item.getId());
		}
		if(result.size() > 0){
			result.addAll(getAllChildren(result));
		}
		return result;
	}
}
