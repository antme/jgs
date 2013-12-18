package com.jgsservice.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgs.bean.EntityResults;
import com.jgs.bean.Log;
import com.jgs.bean.RoleGroup;
import com.jgs.bean.SystemConfig;
import com.jgs.bean.User;
import com.jgs.cfg.CFGManager;
import com.jgs.dbhelper.DataBaseQueryBuilder;
import com.jgs.dbhelper.DataBaseQueryOpertion;
import com.jgs.exception.ResponseException;
import com.jgs.util.EcUtil;
import com.jgs.util.HttpClientUtil;
import com.jgsservice.bean.Complaint;
import com.jgsservice.bean.Location;
import com.jgsservice.bean.Manufacturer;
import com.jgsservice.bean.Menu;
import com.jgsservice.bean.ProductOrder;
import com.jgsservice.bean.ServiceOrder;
import com.jgsservice.bean.ServiceProvider;
import com.jgsservice.bean.ServiceScore;
import com.jgsservice.bean.SpCategoryLocation;
import com.jgsservice.bean.Worker;
import com.jgsservice.bean.Complaint.ComplaintStaus;
import com.jgsservice.bean.vo.ReturnSms;
import com.jgsservice.bean.vo.SearchVo;
import com.jgsservice.bean.vo.WorkerReport;
import com.jgsservice.schedule.SystemSchedule;
import com.jgsservice.service.EcommerceService;
import com.jgsservice.service.ICategoryService;
import com.jgsservice.service.ILocationService;
import com.jgsservice.service.IOrderService;
import com.jgsservice.service.ISmsService;
import com.jgsservice.service.ISystemService;
import com.jgsservice.util.ProductOrderStatus;
import com.jgsservice.util.Role;

@Service(value = "sys")
public class SystemServiceImpl extends EcommerceService implements ISystemService {
	private static Logger logger = LogManager.getLogger(SystemServiceImpl.class);

	
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private ICategoryService cateService;
	
	@Autowired
	private ILocationService locationService;
	
	@Autowired
	private ISmsService smsService;
	
	
	public List<SystemConfig> listSystemConfig() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(SystemConfig.TABLE_NAME);
		return this.dao.listByQuery(builder, SystemConfig.class);
	}

	public void addSysConfig(List<SystemConfig> cfgList) {

		for (SystemConfig cfg : cfgList) {

			SystemConfig config = (SystemConfig) this.dao.findByKeyValue(SystemConfig.CONFIG_ID, cfg.getConfigId(), SystemConfig.TABLE_NAME, SystemConfig.class);

			String log = String.format("修改了系统配置【%s】", cfgList.toString());
			logger.info(log);
			
			if (config != null) {
				config.setCfgValue(cfg.getCfgValue());
				this.dao.updateById(config);
			} else {
				this.dao.insert(cfg);
			}
			CFGManager.remove(SystemConfig.BAIDU_MAP_KEY_ERROR);
			CFGManager.remove(SystemConfig.SMS_ACCOUNT_ID_ERROR);

			CFGManager.loadDbConfig();
		}
		


	}


	public EntityResults<RoleGroup>  listRoleGroups() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(RoleGroup.TABLE_NAME);
		return this.dao.listByQueryWithPagnation(builder, RoleGroup.class);
	}
	
	public EntityResults<RoleGroup>  listRoleGroupForSelect(){
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(RoleGroup.TABLE_NAME);
		return this.dao.listByQueryWithPagnation(builder, RoleGroup.class);
	}

	public void addRoleGroup(RoleGroup group) {
		if (!EcUtil.isEmpty(group.getId())) {
			this.dao.updateById(group);
		} else {
			this.dao.insert(group);
		}
	}

	public EntityResults<User> listBackendUsers() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.or(User.ROLE_NAME, Role.ADMIN.toString());
		builder.or(User.ROLE_NAME, Role.CUSTOMER_SERVICE.toString());
		builder.or(User.ROLE_NAME, Role.SUPPER_ADMIN.toString());
		
		DataBaseQueryBuilder roleQuery = new DataBaseQueryBuilder(RoleGroup.TABLE_NAME);
		List<RoleGroup> groupList = this.dao.listByQuery(roleQuery, RoleGroup.class);
		
		
		
		EntityResults<User> userData = this.dao.listByQueryWithPagnation(builder, User.class);
		
		List<User> userList = userData.getEntityList();
		for (User user : userList) {

			String name = null;
			if (user.getGroupId() != null) {

				for(RoleGroup group: groupList){
					if(user.getGroupId().contains(group.getId())){
						
						if(name == null){
							name = group.getGroupName();
							
						}else{
							 name = name + ", " + group.getGroupName();
						}
					}
				}
			}
			user.setGroupName(name);
		}
		return userData;
	}

	public void updateUserGroup(User user) {
		this.dao.updateById(user);
	}

	public void updateMenuGroup(Menu menu) {

		Menu old = (Menu) this.dao.findByKeyValue(Menu.MENU_ID, menu.getMenuId(), Menu.TABLE_NAME, Menu.class);
		if (old != null) {
			old.setGroupId(menu.getGroupId());
			this.dao.updateById(old);
		} else {
			this.dao.insert(menu);
		}
	}

	public Menu loadMenuGroup(Menu menu) {

		return (Menu) this.dao.findById(menu.getId(), Menu.TABLE_NAME, Menu.class);
	}

	public void clearData() {
//		List<ProductOrder> porderList = this.dao.listByQuery(new DataBaseQueryBuilder(ProductOrder.TABLE_NAME), ProductOrder.class);
//		for (ProductOrder order : porderList) {
//
//			DataBaseQueryBuilder mfcQuery = new DataBaseQueryBuilder(Manufacturer.TABLE_NAME);
//			mfcQuery.and(Manufacturer.USER_ID, order.getUserId());
//
//			Manufacturer mfc = (Manufacturer) this.dao.findOneByQuery(mfcQuery, Manufacturer.class);
//			if (mfc != null) {
//				order.setMfcId(mfc.getId());
//				this.dao.updateById(order);
//			}
//
//		}
//
//		List<ServiceOrder> sorderList = this.dao.listByQuery(new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME), ServiceOrder.class);
//		for (ServiceOrder order : sorderList) {
//
//			DataBaseQueryBuilder poQuery = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
//			poQuery.and(ProductOrder.ID, order.getPoId());
//			ProductOrder po = (ProductOrder) this.dao.findOneByQuery(poQuery, ProductOrder.class);
//			if (po != null) {
//				order.setMfcId(po.getMfcId());
//				order.setMfcUserId(po.getUserId());
//				this.dao.updateById(order);
//			}
//
//		}
		
		
		DataBaseQueryBuilder firstLevel = new DataBaseQueryBuilder(Location.TABLE_NAME);
		firstLevel.and(DataBaseQueryOpertion.NULL, Location.PARENT_ID);
		List<Location> locationList = this.dao.listByQuery(firstLevel, Location.class);
		for (Location location : locationList) {
			save(location);

			DataBaseQueryBuilder childLevel = new DataBaseQueryBuilder(Location.TABLE_NAME);
			childLevel.and(Location.PARENT_ID, location.getId());

			List<Location> childLocationList = this.dao.listByQuery(childLevel, Location.class);
			for (Location childLocation : childLocationList) {

				save(childLocation);

		
				DataBaseQueryBuilder finalChildLevel = new DataBaseQueryBuilder(Location.TABLE_NAME);
				finalChildLevel.and(Location.PARENT_ID, childLocation.getId());

				List<Location> finalChildLocationList = this.dao.listByQuery(finalChildLevel, Location.class);
				for (Location finalChildLocation : finalChildLocationList) {

					save(finalChildLocation);
				}
			}

		}

	}
	
	public void save(Location location) {

		if (location.getParent_id() == null) {
			location.setLevel("1");
		} else {
			DataBaseQueryBuilder parentBuilder = new DataBaseQueryBuilder(Location.TABLE_NAME);
			parentBuilder.and(Location.ID, location.getParent_id());
			Location parent = (Location) dao.findOneByQuery(parentBuilder, Location.class);
			Integer level = Integer.valueOf(parent.getLevel()) + 1;
			location.setLevel(level.toString());
		}


		dao.updateById(location);

	}


	

	public EntityResults<Log> listLogs(SearchVo search) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Log.TABLE_NAME);
		query.limitColumns(new String[] { Log.MESSAGE, Log.URL_PATH, Log.CREATED_ON, Log.DATA_ID, Log.LOG_TABLE_NAME, Log.DATA, Log.DISPLAY_VALUE });

		query.join(Log.TABLE_NAME, User.TABLE_NAME, Log.OPERATOR_ID, User.ID);
		query.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME });
		if (search.getKeyword() != null) {
			query.or(DataBaseQueryOpertion.LIKE, Log.MESSAGE, search.getKeyword());
			query.or(DataBaseQueryOpertion.LIKE, Log.DATA, search.getKeyword());
			query.or(DataBaseQueryOpertion.LIKE, Log.DATA_ID, search.getKeyword());
			query.or(DataBaseQueryOpertion.LIKE, Log.URL_PATH, search.getKeyword());
			
			
//			DataBaseQueryBuilder userQuery = new DataBaseQueryBuilder(User.TABLE_NAME);
//			userQuery.or(DataBaseQueryOpertion.LIKE, User.TABLE_NAME.concat(".").concat(User.USER_NAME), search.getKeyword());
//			query= query.or(userQuery);

		}
		
		
		if (search.getLogType() != null) {

			if("order_history".equalsIgnoreCase(search.getLogType())){
				DataBaseQueryBuilder pathQuery = new DataBaseQueryBuilder(Log.TABLE_NAME);
				pathQuery.join(Log.TABLE_NAME, User.TABLE_NAME, Log.OPERATOR_ID, User.ID);

				pathQuery.or(DataBaseQueryOpertion.LIKE, Log.URL_PATH, "/so/changesp.do");
				pathQuery.or(DataBaseQueryOpertion.LIKE, Log.URL_PATH, "/pro/split.do");
				query = query.and(pathQuery);
			}
		}

	


		return this.dao.listByQueryWithPagnation(query, Log.class);
	}
	
	
	public Map<String, Object> listUserReport(SearchVo search) {
		Role roles[] = Role.values();
		Map<String, Object> result = new HashMap<String, Object>();

		for (Role role : roles) {
			DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
			builder.and(User.ROLE_NAME, role.toString());

			builder = mergeCommonSearchQuery(search, builder, User.TABLE_NAME, User.CREATED_ON, false);

			result.put(role.toString(), this.dao.count(builder));

		}
		
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Worker.TABLE_NAME);
		result.put("WORKER", this.dao.count(builder));
		
		return result;
	}
	
	public Map<String, Object> listMfcLocationReport(SearchVo search) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Manufacturer.TABLE_NAME);
		query.distinct(Manufacturer.MFC_LOCATION_PROVINCE_ID);

		List<Manufacturer> list = this.dao.distinctQuery(query, Manufacturer.class);
		int total = this.dao.count(new DataBaseQueryBuilder(Manufacturer.TABLE_NAME));

		Set<String> ids = new HashSet<String>();

		for (Manufacturer mfc : list) {
			ids.add(mfc.getMfcLocationProvinceId());

		}

		List<Location> locationList = this.dao.listByQuery(
		        new DataBaseQueryBuilder(Location.TABLE_NAME).and(DataBaseQueryOpertion.IN, Location.ID, ids).limitColumns(new String[] { Location.NAME,Location.ID }), Location.class);
		Map<String, String> locationNameMap = new HashMap<String, String>();
		for (Location location : locationList) {
			locationNameMap.put(location.getId(), location.getName());

		}
		List<List<Object>> results = new ArrayList<List<Object>>();
		for (Manufacturer mfc : list) {

			List<Object> data = new ArrayList<Object>();
			DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Manufacturer.TABLE_NAME).and(Manufacturer.MFC_LOCATION_PROVINCE_ID, mfc.getMfcLocationProvinceId());
			builder = mergeCommonSearchQuery(search, builder, Manufacturer.TABLE_NAME, Manufacturer.CREATED_ON, false);
			int count = this.dao.count(builder);
			String locationName = locationNameMap.get(mfc.getMfcLocationProvinceId());
			if (EcUtil.isValid(locationName)) {
				data.add(locationName);
			} else {
				data.add("未知");
			}
			float value = (float)count/(float)total;
			data.add((float)(Math.round(value*100))/100);

			results.add(data);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", results);
		result.put("total", total);
		return result;
	}
	
	public Map<String, Object> getOrderEffectiveReport(SearchVo search) {

		DataBaseQueryBuilder scoreQuery = new DataBaseQueryBuilder(ServiceScore.TABLE_NAME);
		scoreQuery.and(ServiceScore.USER_SCORE_COMMENT, SystemSchedule.DEFAULT_COMMENTS);
		int defautComments = this.dao.count(scoreQuery);

		DataBaseQueryBuilder proQuery = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
		scoreQuery.and(ProductOrder.PO_STATUS, ProductOrderStatus.MANUAL);
		int systemCount = this.dao.count(proQuery);

		DataBaseQueryBuilder userQuery = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		userQuery.and(DataBaseQueryOpertion.IN, ServiceOrder.SO_STATUS, new String[] { ProductOrderStatus.REJECTED.toString(), ProductOrderStatus.TERMINATED.toString() });
		int userRejectCount = this.dao.count(userQuery);

		int complaintCount = this.dao.count(new DataBaseQueryBuilder(Complaint.TABLE_NAME).and(DataBaseQueryOpertion.NOT_EQUALS, Complaint.COMP_STATUS,
		        ComplaintStaus.CLOSED.toString()));

		int total = this.dao.count(new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME).and(ServiceOrder.EXPIRED, false));

		total = systemCount + total;

		List<List<Object>> results = new ArrayList<List<Object>>();

		List<Object> data = new ArrayList<Object>();
		data.add("系统无法处理 ");
		float value = (float) systemCount / (float) total;
		data.add((float)(Math.round(value*100))/100);


		results.add(data);

		data = new ArrayList<Object>();
		data.add("服务商拒绝 ");
		value= (float) userRejectCount / (float) total;
		data.add((float)(Math.round(value*100))/100);

		results.add(data);

		data = new ArrayList<Object>();
		data.add("投诉订单 ");
		value = (float) complaintCount / (float) total;
		data.add((float)(Math.round(value*100))/100);
		
		results.add(data);

		data = new ArrayList<Object>();
		data.add("默认好评");
		value = (float) defautComments / (float) total;
		data.add((float)(Math.round(value*100))/100);

		results.add(data);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", results);
		result.put("total", total);
		return result;

	}

	
	public Map<String, Object> listSpLocationReport(SearchVo search) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ServiceProvider.TABLE_NAME);
		query.distinct(ServiceProvider.SP_LOCATION_PROVINCE_ID);

		List<ServiceProvider> spList = this.dao.distinctQuery(query, ServiceProvider.class);
		int total = this.dao.count(new DataBaseQueryBuilder(ServiceProvider.TABLE_NAME));

		Set<String> provinceIds = new HashSet<String>();

		for (ServiceProvider sp : spList) {
			provinceIds.add(sp.getSpLocationProvinceId());

		}

		List<Location> locationList = this.dao.listByQuery(
		        new DataBaseQueryBuilder(Location.TABLE_NAME).and(DataBaseQueryOpertion.IN, Location.ID, provinceIds).limitColumns(new String[] { Location.NAME, Location.ID }),
		        Location.class);
		Map<String, String> locationNameMap = new HashMap<String, String>();
		for (Location province : locationList) {
			locationNameMap.put(province.getId(), province.getName());

		}
		List<List<Object>> results = new ArrayList<List<Object>>();
		for (ServiceProvider sp : spList) {

			List<Object> data = new ArrayList<Object>();
			DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ServiceProvider.TABLE_NAME).and(ServiceProvider.SP_LOCATION_PROVINCE_ID, sp.getSpLocationProvinceId());
			mergeCommonSearchQuery(search, builder, ServiceProvider.TABLE_NAME, ServiceProvider.CREATED_ON, false);
			int count = this.dao.count(builder);
			data.add(locationNameMap.get(sp.getSpLocationProvinceId()));
			float time = (float) count / (float) total;
			data.add((float)(Math.round(time*100))/100);

			results.add(data);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", results);
		result.put("total", total);
		return result;

	}
	
	public Map<String, Object> getOrderStats(SearchVo svo){
		
			return orderService.getOrderStats(svo);
		
		
	}
	
	public Map<String, Object> getUserEffectiveReport(SearchVo svo) {

		Calendar c = Calendar.getInstance();
		Date startDate = null;
		Date endDate = null;
		if (svo.getStartDate() == null) {
			c.add(Calendar.MONTH, -3);
			startDate = c.getTime();
		}else{
			startDate = svo.getStartDate();
		}

		if (svo.getEndDate() == null) {
			endDate = new Date();
		}else{
			endDate = svo.getEndDate();
		}
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		query.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, ServiceOrder.FINISH_DATE, startDate);
		query.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, ServiceOrder.FINISH_DATE, endDate);
		query.and(DataBaseQueryOpertion.NOT_NULL, ServiceOrder.COMMENT_DATE);
		query.orderBy(ServiceOrder.FINISH_DATE, true);
		List<ServiceOrder> orderlist = this.dao.listByQuery(query, ServiceOrder.class);
		Map<String, Object> result = new HashMap<String, Object>();

		int field = Calendar.MONTH;
		result.put("title", "每月效率分析");

		String effectiveType = svo.getEffectiveType();
		if (effectiveType == null) {
			effectiveType = "week";
		}
		if (effectiveType.equalsIgnoreCase("week")) {
			field = Calendar.WEEK_OF_YEAR;
			result.put("title", "每周效率分析");

		}
			

		Set<String> categories = new LinkedHashSet<String>();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		int start = calendar.get(field);

		calendar.setTime(endDate);
		int end = calendar.get(field);

		for (int i = start; i <= end; i++) {
			categories.add(String.valueOf(i+1));
		}
		
		Map<String, List<ServiceOrder>> orderMap = new LinkedHashMap<String, List<ServiceOrder>>();
		for (ServiceOrder order : orderlist) {
			Date date = order.getFinishDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			String dateType = String.valueOf(cal.get(field) +1);
			List<ServiceOrder> list = orderMap.get(dateType);
			if (list != null) {
				list.add(order);
			} else {
				list = new ArrayList<ServiceOrder>();
				list.add(order);
				orderMap.put(dateType, list);
			}
		}
		List<Float> times = new ArrayList<Float>();
		
		for (String key : categories) {
			List<ServiceOrder> orders = orderMap.get(key);
			long total = 0;
			
			if(orders == null){
				orders = new ArrayList<ServiceOrder>();
			}
			for (ServiceOrder order : orders) {

				total = total + order.getCommentDate().getTime() - order.getFinishDate().getTime();

			}

			if (orders.size() == 0) {
				times.add((float)0);
			} else {
				float time = (float) total / (float) (orders.size() * 60 * 60 * 1000);
				times.add((float)(Math.round(time*100))/100);
			}

		}

		result.put("categories", categories);
		result.put("times", times);
		
		return result;
	}
	
	public Map<String, Object> getSpEffectiveReport(SearchVo svo){


		Calendar c = Calendar.getInstance();
		Date startDate = null;
		Date endDate = null;
		if (svo.getStartDate() == null) {
			c.add(Calendar.MONTH, -3);
			startDate = c.getTime();
		}else{
			startDate = svo.getStartDate();
		}

		if (svo.getEndDate() == null) {
			endDate = new Date();
		}else{
			endDate = svo.getEndDate();
		}

		Map<String, Object> result = new HashMap<String, Object>();

		int field = Calendar.MONTH;
		result.put("title", "每月效率分析");

		String effectiveType = svo.getEffectiveType();
		if (effectiveType == null) {
			effectiveType = "week";
		}
		if (effectiveType.equalsIgnoreCase("week")) {
			field = Calendar.WEEK_OF_YEAR;
			result.put("title", "每周效率分析");

		}
			

		Set<String> categories = new LinkedHashSet<String>();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		int start = calendar.get(field);

		calendar.setTime(endDate);
		int end = calendar.get(field);

		for (int i = start; i <= end; i++) {
			categories.add(String.valueOf(i+1));
		}
		
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		query.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, ServiceOrder.CREATED_ON, startDate);
		query.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, ServiceOrder.CREATED_ON, endDate);
		query.and(DataBaseQueryOpertion.NOT_NULL, ServiceOrder.ACCEPTED_DATE);
		if(EcUtil.isValid(svo.getSpId())){
			query.and(ServiceOrder.SP_ID, svo.getSpId());
		}
		query.orderBy(ServiceOrder.ACCEPTED_DATE, true);
		
	
		List<ServiceOrder> orderlist = this.dao.listByQuery(query, ServiceOrder.class);
		Map<String, List<ServiceOrder>> orderMap = new LinkedHashMap<String, List<ServiceOrder>>();
		for (ServiceOrder order : orderlist) {
			Date date = order.getAcceptedDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			String dateType = String.valueOf(cal.get(field)+1);
			List<ServiceOrder> list = orderMap.get(dateType);
			if (list != null) {
				list.add(order);
			} else {
				list = new ArrayList<ServiceOrder>();
				list.add(order);
				orderMap.put(dateType, list);
			}
		}
		List<Float> times = new ArrayList<Float>();
		
		for (String key : categories) {
			List<ServiceOrder> orders = orderMap.get(key);
			long total = 0;
			
			if(orders == null){
				orders = new ArrayList<ServiceOrder>();
			}
			for (ServiceOrder order : orders) {

				total = total + order.getAcceptedDate().getTime() - order.getCreatedOn().getTime();

			}

			if (orders.size() == 0) {
				times.add((float)0);
			} else {
				float time = (float) total / (float) (orders.size() * 60 * 60 * 1000);
				times.add((float)(Math.round(time*100))/100);
			}

		}

		result.put("categories", categories);
		result.put("times", times);
		
		query = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		query.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, ServiceOrder.ACCEPTED_DATE, startDate);
		query.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, ServiceOrder.ACCEPTED_DATE, endDate);
		query.and(DataBaseQueryOpertion.NOT_NULL, ServiceOrder.ASSIGN_DATE);
		if(EcUtil.isValid(svo.getSpId())){
			query.and(ServiceOrder.SP_ID, svo.getSpId());
		}
		query.orderBy(ServiceOrder.ASSIGN_DATE, true);
		
	
		orderlist = this.dao.listByQuery(query, ServiceOrder.class);
	    orderMap = new LinkedHashMap<String, List<ServiceOrder>>();
		for (ServiceOrder order : orderlist) {
			Date date = order.getAssignDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			String dateType = String.valueOf(cal.get(field)+1);
			List<ServiceOrder> list = orderMap.get(dateType);
			if (list != null) {
				list.add(order);
			} else {
				list = new ArrayList<ServiceOrder>();
				list.add(order);
				orderMap.put(dateType, list);
			}
		}
		times = new ArrayList<Float>();
		
		for (String key : categories) {
			List<ServiceOrder> orders = orderMap.get(key);
			long total = 0;
			
			if(orders == null){
				orders = new ArrayList<ServiceOrder>();
			}
			for (ServiceOrder order : orders) {

				total = total + order.getAcceptedDate().getTime() - order.getCreatedOn().getTime();

			}

			if (orders.size() == 0) {
				times.add((float)0);
			} else {
				float time = (float) total / (float) (orders.size() * 60 * 60 * 1000);
				times.add((float)(Math.round(time*100))/100);
			}

		}

		result.put("assgintimes", times);
		
		return result;
	
	}
	
	public Map<String, Object> getKfEffectiveReport(SearchVo svo) {

		Map<String, Object> result = new HashMap<String, Object>();

		Calendar c = Calendar.getInstance();
		Date startDate = null;
		Date endDate = null;
		if (svo.getStartDate() == null) {
			c.add(Calendar.MONTH, -3);
			startDate = c.getTime();
		}else{
			startDate = svo.getStartDate();
		}

		if (svo.getEndDate() == null) {
			endDate = new Date();
		}else{
			endDate = svo.getEndDate();
		}

		Set<String> categories = new LinkedHashSet<String>();

		int field = Calendar.MONTH;
		result.put("title", "每月效率分析");

		String effectiveType = svo.getEffectiveType();
		if (effectiveType == null) {
			effectiveType = "week";
		}
		if (effectiveType.equalsIgnoreCase("week")) {
			field = Calendar.WEEK_OF_YEAR;
			result.put("title", "每周效率分析");

		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		int start = calendar.get(field);

		calendar.setTime(endDate);
		int end = calendar.get(field);

		for (int i = start; i <= end; i++) {
			categories.add(String.valueOf(i+1));
		}

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
		query.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, ProductOrder.ACTIVE_DATE, startDate);
		query.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, ProductOrder.ACTIVE_DATE, endDate);
		query.and(DataBaseQueryOpertion.NOT_NULL, ProductOrder.SPLIT_ORDER_DATE);
		if (EcUtil.isValid(svo.getKfId())) {
			query.and(ProductOrder.OPERATOR_ID, svo.getKfId());
		}
		query.orderBy(ProductOrder.SPLIT_ORDER_DATE, true);

		List<ProductOrder> orderlist = this.dao.listByQuery(query, ProductOrder.class);

		Map<String, List<ProductOrder>> orderMap = new LinkedHashMap<String, List<ProductOrder>>();
		for (ProductOrder order : orderlist) {
			Date date = order.getSplitOrderDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			String dateType = String.valueOf(cal.get(field)+1);
			List<ProductOrder> list = orderMap.get(dateType);
			if (list != null) {
				list.add(order);
			} else {
				list = new ArrayList<ProductOrder>();
				list.add(order);
				orderMap.put(dateType, list);
			}
		}
		List<Float> times = new ArrayList<Float>();

		for (String key : categories) {
			List<ProductOrder> orders = orderMap.get(key);
			long total = 0;

			if (orders == null) {
				orders = new ArrayList<ProductOrder>();
			}
			for (ProductOrder order : orders) {

				total = total + order.getSplitOrderDate().getTime() - order.getActiveDate().getTime();

			}

			if (orders.size() == 0) {
				times.add((float) 0);
			} else {
				float time = (float) total / (float) (orders.size() * 60 * 60 * 1000);
				times.add((float)(Math.round(time*100))/100);
			}

		}

		result.put("categories", categories);
		result.put("proordertimes", times);

		DataBaseQueryBuilder servicequery = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);

		DataBaseQueryBuilder sdatequery = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		sdatequery.or(DataBaseQueryOpertion.GREATER_THAN_EQUALS, ServiceOrder.REJECT_DATE, startDate);
		sdatequery.or(DataBaseQueryOpertion.GREATER_THAN_EQUALS, ServiceOrder.TERMINATE_DATE, startDate);

		servicequery.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, ServiceOrder.SP_CHANGE_DATE, endDate);
		servicequery.and(sdatequery);

		servicequery.and(DataBaseQueryOpertion.NOT_NULL, ServiceOrder.SP_CHANGE_DATE);
		if (EcUtil.isValid(svo.getKfId())) {
			servicequery.and(ServiceOrder.OPERATOR_ID, svo.getKfId());
		}
		servicequery.orderBy(ServiceOrder.SP_CHANGE_DATE, true);

		List<ServiceOrder> serviceorderlist = this.dao.listByQuery(servicequery, ServiceOrder.class);

		Map<String, List<ServiceOrder>> serviceorderMap = new LinkedHashMap<String, List<ServiceOrder>>();
		for (ServiceOrder order : serviceorderlist) {
			Date date = order.getSpChangeDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			String dateType = String.valueOf(cal.get(field)+1);
			List<ServiceOrder> list = serviceorderMap.get(dateType);
			if (list != null) {
				list.add(order);
			} else {
				list = new ArrayList<ServiceOrder>();
				list.add(order);
				serviceorderMap.put(dateType, list);
			}
		}
		List<Float> sptimes = new ArrayList<Float>();

		for (String key : categories) {
			List<ServiceOrder> orders = serviceorderMap.get(key);
			long total = 0;

			if (orders == null) {
				orders = new ArrayList<ServiceOrder>();
			}
			for (ServiceOrder order : orders) {

				if (order.getRejectDate() != null) {
					total = total + order.getSpChangeDate().getTime() - order.getRejectDate().getTime();
				} else {
					total = total + order.getSpChangeDate().getTime() - order.getTerminateDate().getTime();

				}

			}

			if (orders.size() == 0) {
				sptimes.add((float) 0);
			} else {
				
				float time = (float) total / (float) (orders.size() * 60 * 60 * 1000);
				sptimes.add((float)(Math.round(time*100))/100);
			}

		}
		result.put("serviceordertimes", sptimes);

		DataBaseQueryBuilder complaintQuery = new DataBaseQueryBuilder(Complaint.TABLE_NAME);

		complaintQuery.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, Complaint.CREATED_ON, startDate);

		complaintQuery.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, Complaint.CREATED_ON, endDate);

		complaintQuery.and(DataBaseQueryOpertion.NOT_NULL, Complaint.CLOSE_DATE);
		if (EcUtil.isValid(svo.getKfId())) {
			complaintQuery.and(Complaint.SOLVER_ID, svo.getKfId());
		}
		complaintQuery.orderBy(Complaint.CLOSE_DATE, true);

		List<Complaint> complaintlist = this.dao.listByQuery(complaintQuery, Complaint.class);

		Map<String, List<Complaint>> complaintMap = new LinkedHashMap<String, List<Complaint>>();
		for (Complaint complaint : complaintlist) {
			Date date = complaint.getCloseDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			String dateType = String.valueOf(cal.get(field)+1);
			List<Complaint> list = complaintMap.get(dateType);
			if (list != null) {
				list.add(complaint);
			} else {
				list = new ArrayList<Complaint>();
				list.add(complaint);
				complaintMap.put(dateType, list);
			}
		}
		List<Float> cmptimes = new ArrayList<Float>();

		for (String key : categories) {
			List<Complaint> complaints = complaintMap.get(key);
			long total = 0;

			if (complaints == null) {
				complaints = new ArrayList<Complaint>();
			}
			for (Complaint complaint : complaints) {

				total = total + complaint.getCloseDate().getTime() - complaint.getCreatedOn().getTime();

			}

			if (complaints.size() == 0) {
				cmptimes.add((float) 0);
			} else {
				float time = (float) total / (float) (complaints.size() * 60 * 60 * 1000);
				cmptimes.add((float)(Math.round(time*100))/100);
			}

		}
		result.put("complainttimes", cmptimes);

		return result;

	}
	
	public Map<String, Object> listSpLocationCateReport(SearchVo search) {

		List<String> tempIds = new ArrayList<String>();
		tempIds.add(search.getCategoryId());
		List<String> cateIds = cateService.getAllChildren(tempIds);

		tempIds = new ArrayList<String>();
		tempIds.add(search.getLocationId());
		List<String> locationIds = locationService.getAllChildren(tempIds);

		DataBaseQueryBuilder spCateQuery = new DataBaseQueryBuilder(SpCategoryLocation.TABLE_NAME);
		cateIds.remove("0");
		locationIds.remove("0");
		if (cateIds.size() > 0) {
			spCateQuery.and(DataBaseQueryOpertion.IN, SpCategoryLocation.CATEGORY_ID, cateIds);
		}

		if (locationIds.size() > 0) {
			spCateQuery.and(DataBaseQueryOpertion.IN, SpCategoryLocation.LOCATION_ID, locationIds);
		}
		spCateQuery.distinct(SpCategoryLocation.OWNER_ID);
		List<SpCategoryLocation> list = this.dao.listByQuery(spCateQuery, SpCategoryLocation.class);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("COUNT", list.size());
		return result;

	}
	
	public void testAccount(SearchVo svo) {

		if (svo.getBaiduMapKey() != null) {
			testBaiduAccount(svo);
		}

		if (svo.getSmsAccountUserid() != null) {
			testSmsAccount(svo);
		}
	}

	private void testSmsAccount(SearchVo svo) {

		ReturnSms sms = smsService.getSmsRemainingMongey(svo);

		if (sms.getReturnstatus() == null || !(sms.getReturnstatus().equalsIgnoreCase("Sucess") || sms.getReturnstatus().equalsIgnoreCase("Success"))) {
			throw new ResponseException(sms.getMessage());
		}
	}

	private void testBaiduAccount(SearchVo svo) {
		HttpClientUtil.getLngAndLat("北京市百度大厦", svo.getBaiduMapKey());
	}
	
	public List<WorkerReport> getWorkerReport(SearchVo svo) {

		List<WorkerReport> list = new ArrayList<WorkerReport>();

		if (svo.getSpId() != null) {
			DataBaseQueryBuilder query = new DataBaseQueryBuilder(ServiceProvider.TABLE_NAME);
			query.and(ServiceProvider.ID, svo.getSpId());
			query.limitColumns(new String[] { ServiceProvider.SP_USER_NAME });
			ServiceProvider sp = (ServiceProvider) this.dao.findOneByQuery(query, ServiceProvider.class);
			DataBaseQueryBuilder spQuery = new DataBaseQueryBuilder(Worker.TABLE_NAME);

			spQuery.and(Worker.OWNER_ID, sp.getUserId());

			WorkerReport report = new WorkerReport();
			report.setSpUserName(sp.getSpUserName());
			report.setCount(this.dao.count(spQuery));
			list.add(report);

		} else {
			DataBaseQueryBuilder query = new DataBaseQueryBuilder(ServiceProvider.TABLE_NAME);
			query.limitColumns(new String[] { ServiceProvider.SP_USER_NAME, ServiceProvider.USER_ID });
			List<ServiceProvider> pList = this.dao.listByQuery(query, ServiceProvider.class);
			for (ServiceProvider sp : pList) {

				WorkerReport report = new WorkerReport();
				DataBaseQueryBuilder spQuery = new DataBaseQueryBuilder(Worker.TABLE_NAME);

				spQuery.and(Worker.OWNER_ID, sp.getUserId());

				report.setSpUserName(sp.getSpUserName());
				report.setCount(this.dao.count(spQuery));
				list.add(report);
			}

		}

		return list;

	}

}
