package com.jgsservice.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.jgs.bean.Pagination;
import com.jgs.bean.SystemConfig;
import com.jgs.bean.User;
import com.jgs.cfg.CFGManager;
import com.jgs.constants.EConstants;
import com.jgs.dbhelper.DataBaseQueryBuilder;
import com.jgs.dbhelper.DataBaseQueryOpertion;
import com.jgs.exception.LoginException;
import com.jgs.exception.ResponseException;
import com.jgs.log.EcJDBCAppender;
import com.jgs.util.EcThreadLocal;
import com.jgs.util.EcUtil;
import com.jgs.util.HttpClientUtil;
import com.jgs.util.ImgUtil;
import com.jgs.validators.ValidatorUtil;
import com.jgsservice.bean.AdminApproveHistory;
import com.jgsservice.bean.Category;
import com.jgsservice.bean.Location;
import com.jgsservice.bean.LocationPrice;
import com.jgsservice.bean.Manufacturer;
import com.jgsservice.bean.ServiceOrder;
import com.jgsservice.bean.ServiceProvider;
import com.jgsservice.bean.ServiceProviderTemp;
import com.jgsservice.bean.ServiceScore;
import com.jgsservice.bean.SpCategoryLocation;
import com.jgsservice.bean.SpStore;
import com.jgsservice.bean.Worker;
import com.jgsservice.bean.vo.IDS;
import com.jgsservice.bean.vo.SPInfoWithTempVO;
import com.jgsservice.bean.vo.SearchVo;
import com.jgsservice.service.EcommerceService;
import com.jgsservice.service.ICategoryService;
import com.jgsservice.service.IECommerceUserService;
import com.jgsservice.service.ILocationPriceService;
import com.jgsservice.service.ILocationService;
import com.jgsservice.service.IOrderService;
import com.jgsservice.service.ISProviderService;
import com.jgsservice.service.ISiteMessageService;
import com.jgsservice.service.ISmsService;
import com.jgsservice.service.ISpCategoryLocationService;
import com.jgsservice.util.ApplyType;
import com.jgsservice.util.ApproveResult;
import com.jgsservice.util.PermissionConstants;
import com.jgsservice.util.Role;
import com.jgsservice.util.SPComparator;
import com.jgsservice.util.UserStatus;

@Service(value = "providerService")
public class SProviderServiceImpl extends EcommerceService implements ISProviderService {
	
	private static Logger logger = LogManager.getLogger(SProviderServiceImpl.class);

	@Autowired
	private IECommerceUserService us;
	
	@Autowired
	private ISmsService sms;
	
	
	@Autowired
	private ILocationService locationService;
	
	
	@Autowired
	private ISpCategoryLocationService spclService;

	
	@Autowired
	private ICategoryService  cateService;
	
	
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private ILocationPriceService lps;
	
	@Autowired
	private ISiteMessageService siteSms;
	

	public ServiceProvider addServiceProvider(ServiceProvider sper) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ServiceProvider.TABLE_NAME);
		builder.and(ServiceProvider.SP_USER_NAME, sper.getSpUserName());

		if (dao.exists(builder)) {
			throw new ResponseException("此用户名已经被注册");
		}

		builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.and(User.USER_NAME, sper.getSpUserName());
		if (dao.exists(builder)) {
			throw new ResponseException("此用户名已经被注册");
		}

		String spContactMobilePhone = sper.getSpContactMobilePhone();

		us.checkUserMobile(spContactMobilePhone);
		us.checkUserName( sper.getSpUserName());

		sper.setSpCode(us.generateCode("F", new ServiceProvider().getTable(), false));
		
		setSpCommonInfo(sper);
		sper.setScore(0);
		sper.setScoreBad(0);
		sper.setScoreGood(0);
		sper.setScoreMiddle(0);
		return (ServiceProvider) dao.insert(sper);

	}




	public void setSpCommonInfo(ServiceProvider sper) {
		String address = locationService.getLocationString(sper.getSpLocationAreaId(), "");
		Map<String, Object> location = HttpClientUtil.getLngAndLat(address);
		if (!EcUtil.isEmpty(location)) {
			if (!EcUtil.isEmpty(location.get("lng"))) {
				sper.setLng(EcUtil.getDouble(location.get("lng"), null));
			}

			if (!EcUtil.isEmpty(location.get("lat"))) {
				sper.setLat(EcUtil.getDouble(location.get("lat"), null));
			}
		}
		sper.setSpLocation(address);
		String[] ids = sper.getSpServiceTypeArray();
		String idType = "";

		if (ids != null) {
			for (String id : ids) {

				if (EcUtil.isEmpty(idType)) {
					idType = id;
				} else {
					idType = idType + "," + id;
				}
			}
		}
		sper.setSpServiceType(idType);
		if (ids != null) {
			sper.setSpServiceTypeStr(cateService.getCateString(ids));
		}
	}


	
	
	//修改信息，存放临时表，需要审核
	public void updateSpInfo(ServiceProviderTemp sper) {

		ServiceProvider old = (ServiceProvider) this.dao.findById(sper.getId(), ServiceProvider.TABLE_NAME, ServiceProvider.class);
		
		if (EcUtil.isEmpty(sper.getSpCode())) {
			sper.setSpCode(old.getSpCode());
		}
		if (EcUtil.isEmpty(sper.getUserId())) {
			sper.setUserId(old.getUserId());
		}
		sper.setSpUserName(old.getSpUserName());
		
		setSpCommonInfo(sper);
		
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ServiceProvider.TABLE_NAME_TEMP);
		builder.and(ServiceProviderTemp.ID, sper.getId());
		if (dao.exists(builder)) {
			
			this.dao.updateById(sper);
		} else {
			dao.insert(sper);
		}
	}

	
	@Override
    public ServiceProvider addSpScore(ServiceScore scoreItem) {
		String id = scoreItem.getSpId();
		ServiceProvider old = (ServiceProvider) this.dao.findById(id, ServiceProvider.TABLE_NAME, ServiceProvider.class);
		
		if(scoreItem.getUserScoreType() == null){
			throw new ResponseException("评价类型不能为空");
		}

		if(old == null){
			throw new ResponseException("服务商不存在！");
		}
		if(scoreItem.getUserScoreType() == ServiceScore.USER_SCORE_MIDDLE){
			old.setScore(old.getScore() + EcUtil.getInteger(CFGManager.getProperty(ServiceScore.USER_SCORE_MIDDLE_LAEBL), 0));
			old.setScoreMiddle(old.getScoreMiddle() + 1);
		} else if(scoreItem.getUserScoreType() == ServiceScore.USER_SCORE_GOOD){
			old.setScore(old.getScore() + EcUtil.getInteger(CFGManager.getProperty(ServiceScore.USER_SCORE_GOOD_LABEL), 0));
			old.setScoreGood(old.getScoreGood() + 1);			
		} else if(scoreItem.getUserScoreType() == ServiceScore.USER_SCORE_BAD){
			old.setScore(old.getScore() + EcUtil.getInteger(CFGManager.getProperty(ServiceScore.USER_SCORE_BAD_LABEL), 0));
			old.setScoreBad(old.getScoreBad() + 1);
		} else {
			throw new ResponseException("评分不在可接受范围内！");
		}
		
		if (old.getScore() != null && old.getScore() < 0) {
			old.setScore(0);
		}
		dao.updateById(old);

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ServiceProvider.TABLE_NAME);
		builder.and(ServiceProvider.ID, id);
		builder.limitColumns(new String[]{ServiceProvider.ID, ServiceProvider.SCORE, ServiceProvider.SCORE_GOOD, ServiceProvider.SCORE_BAD, ServiceProvider.SCORE_MIDDLE});
		return (ServiceProvider) dao.findOneByQuery(builder, ServiceProvider.class);
    }

	@Override
    public ServiceProvider getSpScore(ServiceProviderTemp sper) {
		String id = sper.getId();
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ServiceProvider.TABLE_NAME);
		if(EcUtil.isEmpty(id)){
			builder.and(ServiceProvider.USER_ID, EcThreadLocal.getCurrentUserId());
		} else {
			builder.and(ServiceProvider.ID, id);
		}
		builder.limitColumns(new String[]{ServiceProvider.ID, ServiceProvider.SCORE, ServiceProvider.SCORE_GOOD, ServiceProvider.SCORE_BAD, ServiceProvider.SCORE_MIDDLE});
		return (ServiceProvider) dao.findOneByQuery(builder, ServiceProvider.class);
    }

	@Transactional
	public void approveServiceProvider(ServiceProvider sp) {

		ServiceProviderTemp spTemp = (ServiceProviderTemp) this.dao.findById(sp.getId(), ServiceProviderTemp.TABLE_NAME_TEMP, ServiceProviderTemp.class);
		
		
		if (spTemp != null) {
			Date tempApplyTime = spTemp.getCreatedOn();

			ServiceProvider sper = (ServiceProvider) EcUtil.toEntity(spTemp.toMap(), ServiceProvider.class);
			ServiceProvider old = (ServiceProvider) this.dao.findByKeyValue(ServiceProvider.SP_CODE, spTemp.getSpCode(), ServiceProvider.TABLE_NAME, ServiceProvider.class);
			sper.setId(old.getId());
			this.dao.updateById(sper);

			this.dao.deleteById(spTemp);

			// 记录审核历史
			addAdminApproveHistoryForSp(spTemp.getId(), spTemp.getSpUserName(), tempApplyTime, true, ApplyType.UPDATE.toString(), spTemp.toString());

			// spclService.initSpCategoryLocation(sp);
			String spContactMobilePhone = sper.getSpContactMobilePhone();

			us.updateUserMobileNumber(old.getUserId(), spContactMobilePhone, old.getSpContactMobilePhone());


		} else {//注册 or Admin add

			ServiceProvider spinfo = (ServiceProvider) this.dao.findById(sp.getId(), ServiceProvider.TABLE_NAME, ServiceProvider.class);
			Date spApplyTime = spinfo.getCreatedOn();
			
			User user = new User();
			user.setUserName(spinfo.getSpUserName());
			user.setMobileNumber(spinfo.getSpContactMobilePhone());
			user.setRoleName(Role.SP.toString());
			String password = ImgUtil.getRandomWord(4);
			// user.setPassword(password);
			user.setPassword(spinfo.getPassword());
			user.setStatus(UserStatus.NORMAL.toString());
			us.regUser(user);

			spinfo.setUserId(user.getId());
			spinfo.setScore(0);
			spinfo.setScoreBad(0);
			spinfo.setScoreGood(0);
			spinfo.setScoreMiddle(0);
			String log = String.format("审核了新注册的服务商【%s】", spinfo.getSpUserName());
			logger.info(log);
			dao.updateById(spinfo);
			
			//记录审核历史
			addAdminApproveHistoryForSp(spinfo.getId(), spinfo.getSpUserName(), spApplyTime, true, ApplyType.NEW.toString(), spinfo.toString());
			
//			spclService.initSpCategoryLocation(sp);
			//暂时已手机号码为密码
			sms.sendNewSpNotice(user.getMobileNumber(), spinfo, spinfo.getPassword());

		}
	}


	@Transactional
	public void rejectServiceProvider(ServiceProvider sp) {
		
		ServiceProviderTemp st = (ServiceProviderTemp) dao.findById(sp.getId(), ServiceProviderTemp.TABLE_NAME_TEMP, ServiceProviderTemp.class);
		if(st != null){//Update reject
			//记录审核历史
			//ServiceProviderTemp st = (ServiceProviderTemp) dao.findById(sp.getId(), ServiceProviderTemp.TABLE_NAME_TEMP, ServiceProviderTemp.class);
			dao.deleteById(st);
			addAdminApproveHistoryForSp(st.getId(), st.getSpUserName(), st.getCreatedOn(), false, ApplyType.UPDATE.toString(), st.toString());
			siteSms.addSpRejectSiteMessage(st.getUserId(), sp.getRejectReson());

		} else {// New Reject
			    // 记录审核历史
			DataBaseQueryBuilder query = new DataBaseQueryBuilder(ServiceProvider.TABLE_NAME);
			query.and(ServiceProvider.ID, sp.getId());
			query.and(DataBaseQueryOpertion.NULL, ServiceProvider.USER_ID);

			ServiceProvider s = (ServiceProvider) dao.findOneByQuery(query, ServiceProvider.class);

			if (s == null) {
				throw new ResponseException("非法数据");
			}
			s.setRejectReson(sp.getRejectReson());
			dao.deleteById(s);
			sms.sendNewSpRejectNotice(s);
			addAdminApproveHistoryForSp(s.getId(), s.getSpUserName(), s.getCreatedOn(), false, ApplyType.NEW.toString(), s.toString());
		}

	}
	
	private void addAdminApproveHistoryForSp(String userId, String userName, Date applyTime, boolean approve, String applyType, String data){
		AdminApproveHistory aah = new AdminApproveHistory();
		aah.setUserId(userId);
		aah.setRoleName(Role.SP.toString());
		aah.setUserName(userName);
		aah.setApplyTime(applyTime);
		aah.setApproveTime(new Date());
		aah.setApplyType(applyType);
		aah.setLogData(data);
		if(approve){
			aah.setApproveResult(ApproveResult.APPROVED.toString());
		}else{
			aah.setApproveResult(ApproveResult.REJECT.toString());
		}
		dao.insert(aah);
	}

	public EntityResults<ServiceProvider> listServiceProviders(SearchVo search) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ServiceProvider.TABLE_NAME);
		query.orderBy(ServiceProvider.SCORE, false);

		if (!EcUtil.isEmpty(search.getStartDate())) {
			query.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, ServiceProvider.CREATED_ON, search.getStartDate());
		}

		if (!EcUtil.isEmpty(search.getEndDate())) {
			Date endDate = getQueryEndDate(search);
			query.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, ServiceProvider.CREATED_ON, endDate);
		}

		DataBaseQueryBuilder keywordBuilder = new DataBaseQueryBuilder(ServiceProvider.TABLE_NAME);
		if (!EcUtil.isEmpty(search.getKeyword())) {
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ServiceProvider.SP_USER_NAME, search.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ServiceProvider.SP_CONTACT_MOBILE_PHONE, search.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ServiceProvider.SP_COMPANY_NAME, search.getKeyword());
		}

		if (!EcUtil.isEmpty(keywordBuilder.getQueryStr())) {
			query = query.and(keywordBuilder);
		}

		return dao.listByQueryWithPagnation(query, ServiceProvider.class);
	}
	
	public List<ServiceProvider> listSpsForAdminsSelect() {
		return this.dao.listByQuery(new DataBaseQueryBuilder(ServiceProvider.TABLE_NAME), ServiceProvider.class);
	}
	
	@Transactional
	public List<ServiceProvider> listServiceProvidersForOrder(ServiceOrder order) {
		ServiceOrder o = null;
		if (!EcUtil.isEmpty(order.getId())) {
			DataBaseQueryBuilder orderBuilder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
			orderBuilder.and(ServiceOrder.ID, order.getId());
			o = (ServiceOrder) this.dao.findOneByQuery(orderBuilder, ServiceOrder.class);
		} else {
			o = order;
		}

		orderService.updateOrderLocation(null, o);
		Category cate = orderService.updateOrderCategory(null, o);
		Integer splitOrderNumber = null;

		if (cate != null) {
			splitOrderNumber = cate.getSplitOrderNumber();
		}
		if (cate != null && splitOrderNumber != null && order.getPoGoodsNumber() != null && splitOrderNumber < order.getPoGoodsNumber()) {
			String errormsg = String.format("订单数量限制, 品类数量[%s]，分类拆单数量[%s]， 转换为人工", order.getPoGoodsNumber(), splitOrderNumber);
			logger.info(errormsg);
			throw new ResponseException(String.format("订单数量限制, 品类数量[%s]，分类拆单数量[%s]", order.getPoGoodsNumber(), splitOrderNumber));
		}


		
		if (EcUtil.isEmpty(o.getCateId())) {
			logger.info(String.format("服务订单【%s】不能获取最近服务商由于根据宝贝标题【%s】获取品类失败", o.getSoCode()==null? "" : o.getSoCode(), o.getPoGoodsTitle()));
			throw new ResponseException("根据宝贝标题获取品类失败");
		}

		if (EcUtil.isEmpty(o.getLocationId())) {

			logger.info(String.format("服务订单【%s】不能获取最近服务商由于根据收货地址【%s】获取区域失败",  o.getSoCode()==null? "" : o.getSoCode(), o.getPoReceiverAddress()));
			throw new ResponseException("根据收货地址获取区域失败");
		}

		
		o.setPrice(lps.getPrice(o.getLocationId(), o.getCateId()));

		if (o.getPrice() == null || o.getPrice() < 1) {

			if (!EcUtil.isEmpty(o.getCateId()) && !EcUtil.isEmpty(o.getLocationId())) {

				Location location = (Location) this.dao.findByKeyValue(Location.ID, o.getLocationId(), Location.TABLE_NAME, Location.class);

				Location parentLocation = (Location) this.dao.findByKeyValue(Location.ID, location.getParent_id(), Location.TABLE_NAME, Location.class);

				Category parentCate = (Category) this.dao.findByKeyValue(Category.ID, cate.getParent_id(), Category.TABLE_NAME, Category.class);

				String log = null;
				if(o.getSoCode() == null){
					 log = String.format("产品订单根据品类【%s】和地域【%s】获取价格失败, 请管理员设置此区域品类的价格", parentCate.getName() + "-" + cate.getName(), parentLocation.getName() + "-"
						        + location.getName());
				}else{
				 log = String.format("服务订单【%s】根据品类【%s】和地域【%s】获取价格失败, 请管理员设置此区域品类的价格", o.getSoCode(), parentCate.getName() + "-" + cate.getName(), parentLocation.getName() + "-"
				        + location.getName());
				}
				logger.info(log);

				throw new ResponseException(log);

			}
			return new ArrayList<ServiceProvider>();
		}

		if (!EcUtil.isEmpty(o.getId())) {
			this.dao.updateById(o);
		}

		List<SpCategoryLocation> spCateList = spclService.listSpCategoryLocationByCate(cate);

		List<ServiceProvider> spSearchList = findServiceProvider(o, spCateList);
		if (spSearchList.isEmpty()) {
			String locationStr = "";
			if (o.getLocationId() != null) {
				Location location = (Location) this.dao.findByKeyValue(Location.ID, o.getLocationId(), Location.TABLE_NAME, Location.class);

				Location parentLocation = (Location) this.dao.findByKeyValue(Location.ID, location.getParent_id(), Location.TABLE_NAME, Location.class);

				locationStr = parentLocation.getName() + "-" + location.getName();
			}

			throw new ResponseException("找不到距离内的服务商" + locationStr);
		}
		return spSearchList;

	}

	public EntityResults<Worker> listMyWorkers() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Worker.TABLE_NAME);
		builder.and(Worker.OWNER_ID, EcThreadLocal.getCurrentUserId());
		return dao.listByQueryWithPagnation(builder, Worker.class);
	}

	public void addWorker(Worker worker) {
		ValidatorUtil.validate(worker, "sp", "addWorker", PermissionConstants.validateFiles);

		if(EcThreadLocal.getCurrentUserId() == null){
			throw new LoginException();
		}
		if (EcUtil.isEmpty(worker.getId())) {
			worker.setOwnerId(EcThreadLocal.getCurrentUserId());
			worker.setActive(true);
			DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Worker.TABLE_NAME);
			builder.and(Worker.ID_CARD, worker.getIdCard());
			if (this.dao.exists(builder)) {
				throw new ResponseException("工人已经存在");
			}
			this.dao.insert(worker);
		} else {
			this.dao.updateById(worker);
		}

	}

	public ServiceProvider getSpInfo(ServiceProvider sp) {

		if (EcUtil.isEmpty(sp.getId())) {
			return (ServiceProvider) this.dao.findByKeyValue(ServiceProvider.USER_ID, EcThreadLocal.getCurrentUserId(), ServiceProvider.TABLE_NAME, ServiceProvider.class);
		}
		return (ServiceProvider) this.dao.findById(sp.getId(), ServiceProvider.TABLE_NAME, ServiceProvider.class);
	}

	public ServiceProvider loadSpDetailInfo(ServiceProvider sp) {

		if (dao.exists(ServiceProvider.ID, sp.getId(), ServiceProvider.TABLE_NAME)) {
			return (ServiceProvider) this.dao.findById(sp.getId(), ServiceProvider.TABLE_NAME, ServiceProvider.class);
		}else{
			throw new ResponseException("该服务商不存在！");
		}
		
	}


	@Override
    public SPInfoWithTempVO getSpInfoWithSPTempId(ServiceProvider sp) {
		SPInfoWithTempVO vo = null;
		if (EcUtil.isEmpty(sp.getId())) {
			vo = (SPInfoWithTempVO) this.dao.findByKeyValue(ServiceProvider.USER_ID, EcThreadLocal.getCurrentUserId(), ServiceProvider.TABLE_NAME, SPInfoWithTempVO.class);
		}else{
			vo = (SPInfoWithTempVO) this.dao.findById(sp.getId(), ServiceProvider.TABLE_NAME, SPInfoWithTempVO.class);
		}

		String spid = vo.getId();
		
		if(dao.exists(ServiceProvider.ID, spid, ServiceProvider.TABLE_NAME_TEMP)){
			vo.setTempId(spid);
		}else{
			vo.setTempId(null);
		}
		return vo;
    }
	
	public ServiceProvider getApproveSpInfo(ServiceProvider sp) {

		ServiceProvider sper = (ServiceProviderTemp) this.dao.findById(sp.getId(), ServiceProviderTemp.TABLE_NAME_TEMP, ServiceProviderTemp.class);
		if (sper != null) {
			return sper;
		}

		return (ServiceProvider) this.dao.findById(sp.getId(), ServiceProvider.TABLE_NAME, ServiceProvider.class);
	}

	public EntityResults<ServiceProvider> listNewServiceProviders() {
		DataBaseQueryBuilder builder = getNewServiceProvidersQueryBuilder();
		return this.dao.listByQueryWithPagnation(builder, ServiceProvider.class);
	}


	public DataBaseQueryBuilder getNewServiceProvidersQueryBuilder() {
	    DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ServiceProvider.TABLE_NAME);
		builder.and(DataBaseQueryOpertion.NULL, ServiceProvider.USER_ID);
	    return builder;
    }
	
	public DataBaseQueryBuilder getUpdateServiceProvidersQueryBuilder(){
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ServiceProviderTemp.TABLE_NAME_TEMP);
		return builder;
	}
	
	public EntityResults<ServiceProviderTemp> listUpdatedServiceProviders() {
		return this.dao.listByQueryWithPagnation(getUpdateServiceProvidersQueryBuilder(), ServiceProviderTemp.class);
	}

	public Worker loadWorkerInfo(Worker worker) {
		return (Worker) this.dao.findById(worker.getId(), Worker.TABLE_NAME, Worker.class);
	}
	
	public EntityResults<ServiceProvider> searchSps(SearchVo search) {

		DataBaseQueryBuilder spCateQuery = new DataBaseQueryBuilder(SpCategoryLocation.TABLE_NAME);

		mergeLocationAndCateQuery(search, spCateQuery, SpCategoryLocation.CATEGORY_ID, SpCategoryLocation.LOCATION_ID);

		DataBaseQueryBuilder priceQuery = new DataBaseQueryBuilder(LocationPrice.TABLE_NAME);
		priceQuery.and(LocationPrice.CATEGORY_ID, search.getCategoryId());
		priceQuery.and(LocationPrice.LOCATION_ID, search.getCountyId());

		LocationPrice price = (LocationPrice) this.dao.findOneByQuery(priceQuery, LocationPrice.class);

		if (spCateQuery.getQueryStr() == null) {
			// return empty data query
			spCateQuery.and(SpCategoryLocation.ID, null);
		}
		spCateQuery.limitColumns(new String[] { SpCategoryLocation.ID, SpCategoryLocation.OWNER_ID });
		List<SpCategoryLocation> list = this.dao.listByQuery(spCateQuery, SpCategoryLocation.class);
		List<String> ids = new ArrayList<String>();
		for (SpCategoryLocation spCate : list) {
			ids.add(spCate.getOwner_Id());
		}

		DataBaseQueryBuilder idQuery = new DataBaseQueryBuilder(ServiceProvider.TABLE_NAME);
		idQuery.join(ServiceProvider.TABLE_NAME, User.TABLE_NAME, ServiceProvider.USER_ID, User.ID);
		idQuery.and(DataBaseQueryOpertion.NOT_EQUALS, User.TABLE_NAME.concat(".").concat(User.STATUS), UserStatus.LOCKED);
		idQuery.and(DataBaseQueryOpertion.IN, ServiceProvider.USER_ID, ids);
		idQuery.limitColumns(new ServiceProvider().getColumnList());

		EntityResults<ServiceProvider> results = this.dao.listByQueryWithPagnation(idQuery, ServiceProvider.class);
		if (price != null) {
			List<ServiceProvider> spList = results.getEntityList();

			if (spList != null) {
				for (ServiceProvider sp : spList) {
					sp.setPrice(price.getPrice());
				}
			}
		}

		return results;
		// ****
	}


	@Override
    public EntityResults<ServiceProvider> listForAdmin(SearchVo vo) {

		String keyword = vo.getKeyword();
		String userStatus = vo.getUserStatus();
		
		EntityResults<ServiceProvider> result = new EntityResults<ServiceProvider>();
		
		Pagination pagination = (Pagination) EcThreadLocal.get(EConstants.PAGENATION);
		int limitStart = 0;
		int limitRows = 10;
		if (pagination != null && pagination.getRows() > 0) {
			int currentPage = pagination.getPage();
			if (currentPage < 1) {
				currentPage = 1;
			}
			limitStart = (currentPage - 1) * pagination.getRows();
			limitRows = pagination.getRows();
		}
		StringBuffer sql = new StringBuffer("select sp.spCompanySize, sp.createdOn,sp.id,sp.spUserName,sp.spCode,sp.spServiceType,sp.storeImage,sp.spServiceTypeStr,sp.spCompanyName,sp.spCompanyAddress,sp.spLocation,sp.spContactPerson,sp.spContactPhone,sp.spLevel,sp.spLicenseNo,sp.spQQ,sp.spWangWang,sp.userId,sp.spLocationProvinceId,sp.spLocationCityId,sp.spLocationAreaId,sp.spCompanyAdressProvinceId,sp.spCompanyAdressAreaId,sp.spCompanyAdressCityId,sp.lng,sp.lat,sp.spContactMobilePhone,sp.spBranchAddress,user.status userStatus from ServiceProvider sp join User user on sp.userId=user.id where 1=1 ");
		
		if(!EcUtil.isEmpty(keyword)){
			sql.append(" and (sp.spCompanyName like \"%").append(keyword).append("%\"")
			.append(" or sp.spUserName like \"%").append(keyword).append("%\"")
			.append(" or sp.spCode like \"%").append(keyword).append("%\"")
			.append(" or sp.spServiceTypeStr like \"%").append(keyword).append("%\"")
			.append(" or sp.spContactMobilePhone like \"%").append(keyword).append("%\"")
			.append(" or sp.spContactPerson like \"%").append(keyword).append("%\")");
			//sql.append(" and sp.spCompanyName like \"%").append(keyword).append("%\"");
		}
		
		if (!EcUtil.isEmpty(userStatus)){
			sql.append(" and user.status=\"").append(userStatus).append("\"");
		}
		
		List<ServiceProvider> listForCount =  dao.listBySql(sql.toString(), ServiceProvider.class);
		Pagination pagnation = new Pagination();
		pagnation.setTotal(listForCount.size());
		result.setPagnation(pagnation);
		
		sql.append(" limit ").append(limitStart).append(",").append(limitRows).append(";");
		List<ServiceProvider> entityList =  dao.listBySql(sql.toString(), ServiceProvider.class);
		result.setEntityList(entityList);
		
		return result;
    }

	@Override
    public EntityResults<Worker> listWorkerForAdmin(SearchVo vo) {
		EntityResults<Worker> result = new EntityResults<Worker>();
		
		Pagination pagination = (Pagination) EcThreadLocal.get(EConstants.PAGENATION);
		int limitStart = 0;
		int limitRows = 10;
		if (pagination != null && pagination.getRows() > 0) {
			int currentPage = pagination.getPage();
			if (currentPage < 1) {
				currentPage = 1;
			}
			limitStart = (currentPage - 1) * pagination.getRows();
			limitRows = pagination.getRows();
		}
		StringBuffer sql = new StringBuffer("select w.workerType, w.id,w.workerName,w.mobilePhone,w.idCard,w.address,w.ownerId,w.isActive,u.userName from Worker w left join User u on w.ownerId=u.id where 1=1");
		String keyword = vo.getKeyword();
		if(!EcUtil.isEmpty(keyword)){
			sql.append(" and (w.workerName  like \"%").append(keyword).append("%\"")
			.append(" or w.mobilePhone like \"%").append(keyword).append("%\"")
			.append(" or u.userName like \"%").append(keyword).append("%\"")
			.append(" or w.idCard like \"%").append(keyword).append("%\")");
		}
		/*if(!EcUtil.isEmpty(vo.getUserStatus())){
			sql.append(" and w.isActive=\"").append(vo.getUserStatus()).append("\"");
			builder.and(Worker.IS_ACTIVE, vo.getUserStatus());
		}*/
		List<Worker> listForCount =  dao.listBySql(sql.toString(), Worker.class);
		Pagination pagnation = new Pagination();
		pagnation.setTotal(listForCount.size());
		result.setPagnation(pagnation);
		
		sql.append(" limit ").append(limitStart).append(",").append(limitRows).append(";");
		List<Worker> entityList =  dao.listBySql(sql.toString(), Worker.class);
		result.setEntityList(entityList);
		
		return result;
    }
	
	
	public void inactiveWorker(Worker worker) {
		worker.setActive(false);
		this.dao.updateById(worker);
	}

	public void activeWorker(Worker worker) {
		worker.setActive(true);
		this.dao.updateById(worker);
	}
	
	public EntityResults<Worker> listWorkerForOrderSelect() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Worker.TABLE_NAME);
		builder.and(DataBaseQueryOpertion.IS_TRUE, Worker.IS_ACTIVE);
		builder.and(Worker.OWNER_ID, EcThreadLocal.getCurrentUserId());
		return this.dao.listByQueryWithPagnation(builder, Worker.class);
	}


	@Override
	public void addServiceProviderByAdmin(ServiceProvider sper) {
		if (EcUtil.isEmpty(sper.getId())) {
			// register or admin add
			ServiceProvider sp = addServiceProvider(sper);
			approveServiceProvider(sp);

		} else {// admin edit
	
			ServiceProvider old = (ServiceProvider) this.dao.findByKeyValue(ServiceProvider.ID, sper.getId(), ServiceProvider.TABLE_NAME, ServiceProvider.class);

			if (!old.getSpContactMobilePhone().equalsIgnoreCase(sper.getSpContactMobilePhone())) {

				us.checkUserMobile(sper.getSpContactMobilePhone());
				us.updateUserMobileNumber(old.getUserId(), sper.getSpContactMobilePhone(), old.getSpContactMobilePhone());
			}
			setSpCommonInfo(sper);
			dao.updateById(sper);
		}

	}

	@Override
	public void adminAddWorker(Worker worker) {
		if (EcUtil.isEmpty(worker.getId())) {
//			worker.setOwnerId(EcThreadLocal.getCurrentUserId());
			worker.setActive(true);
			DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Worker.TABLE_NAME);
			builder.and(Worker.ID_CARD, worker.getIdCard());
			if (this.dao.exists(builder)) {
				throw new ResponseException("工人已经存在");
			}
			this.dao.insert(worker);
		} else {
			this.dao.updateById(worker);
		}
	}
	


	@Override
    public void lockWorkerById(BaseEntity be) {
		Worker w = (Worker) dao.findById(be.getId(), Worker.TABLE_NAME, Worker.class);
	    w.setActive(false);
	    dao.updateById(w);
	    
    }


	@Override
    public void unlockWorkerById(BaseEntity be) {
		Worker w = (Worker) dao.findById(be.getId(), Worker.TABLE_NAME, Worker.class);
	    w.setActive(true);
	    dao.updateById(w);
	    
    }


	@Override
    public EntityResults<AdminApproveHistory> listAdminApproveHistory(SearchVo vo) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(AdminApproveHistory.TABLE_NAME);
		builder.orderBy(AdminApproveHistory.APPROVE_TIME, false);
		return dao.listByQueryWithPagnation(builder, AdminApproveHistory.class);
    }


	@Override
    public void removeSpTemp(ServiceProviderTemp spt) {
	    dao.deleteById(spt);
    }


	@Override
    public void delWorkerById(Worker worker) {
	    dao.deleteById(worker);
    }

	@Override
	public SpStore saveSelfSpStore(SpStore entity) {

		Map<String, Object> location = HttpClientUtil.getLngAndLat(entity.getAddress());
		Location loc = null;
		if (!EcUtil.isEmpty(location)) {
			if (!EcUtil.isEmpty(location.get("lng"))) {
				entity.setLng(EcUtil.getDouble(location.get("lng"), null));
			}

			if (!EcUtil.isEmpty(location.get("lat"))) {
				entity.setLat(EcUtil.getDouble(location.get("lat"), null));
			}
			loc = locationService.getLocationByLngAndLat(entity.getAddress(), entity.getLng().toString(), entity.getLat().toString());

		}

		if (loc != null) {
			entity.setLocationId(loc.getId());
		}
		if (EcUtil.isValid(entity.getId())) {
			dao.updateById(entity);
		} else {
			entity.setOwnerId(EcThreadLocal.getCurrentUserId());
			dao.insert(entity);
		}
		return entity;
	}
	@Override
    public EntityResults<SpStore> listSelfSpStore() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(SpStore.TABLE_NAME);
		builder.and(SpStore.OWNER_ID, EcThreadLocal.getCurrentUserId());
	    return dao.listByQueryWithPagnation(builder, SpStore.class);
    }
	@Override
    public void deleteSelfSpStore(SpStore entity) {
		if(dao.exists(SpStore.ID,  entity.getId(), SpStore.TABLE_NAME)){
			dao.deleteById(entity);
		} else {
			throw new ResponseException("网点不存在！");
		}
    }
	
	
	public EntityResults<SpCategoryLocation> searchServiceRegional(SearchVo vo) {

		DataBaseQueryBuilder cateLocationJoinQuery = new DataBaseQueryBuilder(SpCategoryLocation.TABLE_NAME);
		cateLocationJoinQuery.join(SpCategoryLocation.TABLE_NAME, ServiceProvider.TABLE_NAME, SpCategoryLocation.OWNER_ID, ServiceProvider.USER_ID);
		cateLocationJoinQuery.joinColumns(ServiceProvider.TABLE_NAME, new String[]{ServiceProvider.SP_USER_NAME, ServiceProvider.SP_CONTACT_MOBILE_PHONE});
		cateLocationJoinQuery.limitColumns(new SpCategoryLocation().getColumnList());
		
		cateLocationJoinQuery.join(SpCategoryLocation.TABLE_NAME, LocationPrice.TABLE_NAME, SpCategoryLocation.LOCATION_ID, LocationPrice.LOCATION_ID);
		cateLocationJoinQuery.on(SpCategoryLocation.TABLE_NAME, LocationPrice.TABLE_NAME, SpCategoryLocation.CATEGORY_ID, LocationPrice.CATEGORY_ID);
		cateLocationJoinQuery.joinColumns(LocationPrice.TABLE_NAME, new String[] { LocationPrice.PRICE });
		
		
		cateLocationJoinQuery.join(SpCategoryLocation.TABLE_NAME, Category.TABLE_NAME, SpCategoryLocation.CATEGORY_ID, Category.ID);
		cateLocationJoinQuery.orderBy(Category.TABLE_NAME , Category.FINAL_SORT, true);
		cateLocationJoinQuery.orderBy(Category.TABLE_NAME , Category.CA_WHOLE_NAME, true);
		cateLocationJoinQuery.joinColumns(Category.TABLE_NAME, new String[]{Category.CA_WHOLE_NAME});
		
		cateLocationJoinQuery.join(SpCategoryLocation.TABLE_NAME, Location.TABLE_NAME, SpCategoryLocation.LOCATION_ID, Location.ID);
		cateLocationJoinQuery.orderBy(Location.TABLE_NAME, Location.LO_WHOLE_NAME, true);
		cateLocationJoinQuery.joinColumns(Location.TABLE_NAME, new String[]{Location.LO_WHOLE_NAME});
		
		if (!EcUtil.isEmpty(vo.getSpkeyword())) {
		
			cateLocationJoinQuery.or(DataBaseQueryOpertion.LIKE, ServiceProvider.TABLE_NAME + "." + ServiceProvider.SP_USER_NAME, vo.getSpkeyword());
			cateLocationJoinQuery.or(DataBaseQueryOpertion.LIKE, ServiceProvider.TABLE_NAME + "." + ServiceProvider.SP_CONTACT_MOBILE_PHONE, vo.getSpkeyword());
			
			
		} else {
			if(EcUtil.isEmpty(vo.getIsAdmin())){
				cateLocationJoinQuery.and(SpCategoryLocation.OWNER_ID, EcThreadLocal.getCurrentUserId());
			}
		}

		DataBaseQueryBuilder cateLocationQuery = new DataBaseQueryBuilder(SpCategoryLocation.TABLE_NAME);

		mergeLocationAndCateQuery(vo, cateLocationQuery, SpCategoryLocation.TABLE_NAME + "." + SpCategoryLocation.CATEGORY_ID, SpCategoryLocation.TABLE_NAME + "." + SpCategoryLocation.LOCATION_ID);
			
		if (cateLocationQuery.getQueryStr() != null) {
			cateLocationJoinQuery = cateLocationJoinQuery.and(cateLocationQuery);
		}
		

		EntityResults<SpCategoryLocation> clList = this.dao.listByQueryWithPagnation(cateLocationJoinQuery, SpCategoryLocation.class);
		
		

		return clList;

	}
	

	public List<ServiceProvider> findServiceProvider(ServiceOrder sor, List<SpCategoryLocation> spCateList) {
		logger.info("根据品类找到服务区域 ::::: " + spCateList.size() + ":::::个");
		SystemConfig cfg = (SystemConfig) this.dao.findByKeyValue(SystemConfig.CONFIG_ID, "firstCircle", SystemConfig.TABLE_NAME, SystemConfig.class);
		int compareDistance = 10 * 1000;
		if (cfg != null) {
			compareDistance = (EcUtil.getInteger(cfg.getCfgValue(), 10) * 1000);
		}
		Set<String> spUserids = new HashSet<String>();
		Set<String> allSpUserids = new HashSet<String>();
		Map<String, Double> distanceMap = new HashMap<String, Double>();

		if(sor.getLng() != null && sor.getLat() != null){
			for (SpCategoryLocation spca : spCateList) {
				// Map<String, Object> addressInfo =
				// HttpClientUtil.getAddressByLngAndLat(spca.getLng().toString(),
				// spca.getLat().toString());
				// logger.info("【服务区域】百度地图通过经纬度获取区域" + addressInfo);

				if (spca.getLng() != null && spca.getLat() != null) {
					allSpUserids.add(spca.getOwner_Id());

					double distance = EcUtil.GetDistance(sor.getLng(), sor.getLat(), spca.getLng(), spca.getLat());
					if (compareDistance >= distance) {
						spUserids.add(spca.getOwner_Id());

						Double savedDistance = distanceMap.get(spca.getOwner_Id());
						if (savedDistance != null) {
							if (savedDistance > distance) {
								distanceMap.put(spca.getOwner_Id(), distance);
							}
						} else {
							distanceMap.put(spca.getOwner_Id(), distance);
						}
					}
				}

			}
		}
		

		DataBaseQueryBuilder storeQuery = new DataBaseQueryBuilder(SpStore.TABLE_NAME);
		storeQuery.and(DataBaseQueryOpertion.IN, SpStore.OWNER_ID, allSpUserids);
		List<SpStore> storeList = this.dao.listByQuery(storeQuery, SpStore.class);
		logger.info("根据服务商网点地址来找最近的服务商: " + storeList);


		for (SpStore store : storeList) {

			if (store.getLng() != null && store.getLat() != null) {
				double distance = EcUtil.GetDistance(sor.getLng(), sor.getLat(), store.getLng(), store.getLat());
				if (compareDistance >= distance) {
					spUserids.add(store.getOwnerId());
					Double savedDistance = distanceMap.get(store.getOwnerId());
					if (savedDistance != null) {
						if (savedDistance > distance) {
							distanceMap.put(store.getOwnerId(), distance);
						}
					} else {
						distanceMap.put(store.getOwnerId(), distance);
					}
				}
			}

		}
		
		for (SpCategoryLocation spca : spCateList) {
			spca.setDistance(distanceMap.get(spca.getOwner_Id()));
		}	
		
		
		
		// 过滤已冻结的服务商
		DataBaseQueryBuilder userQuery = new DataBaseQueryBuilder(User.TABLE_NAME);
		userQuery.and(User.STATUS, UserStatus.LOCKED.toString());
		userQuery.limitColumns(new String[] { User.ID });

		List<User> userList = this.dao.listByQuery(userQuery, User.class);
		for (User user : userList) {
			spUserids.remove(user.getId());
		}
		
		List<ServiceProvider> spSearchList = new ArrayList<ServiceProvider>();

		if (spUserids.size() > 0) {
			DataBaseQueryBuilder spIdQuery = new DataBaseQueryBuilder(ServiceProvider.TABLE_NAME);
			spIdQuery.and(DataBaseQueryOpertion.IN, ServiceProvider.USER_ID, spUserids);
			if (sor.getSpId() != null) {
				spIdQuery.and(DataBaseQueryOpertion.NOT_EQUALS, ServiceProvider.ID, sor.getSpId());
			}
			spSearchList.addAll(this.dao.listByQuery(spIdQuery, ServiceProvider.class));
		}

		for (ServiceProvider sp : spSearchList) {
			sp.setDistance(distanceMap.get(sp.getUserId()));
		}
		
		SPComparator comparator = new SPComparator();
		Collections.sort(spSearchList, comparator);
		return spSearchList;
	}


	@Override
    public boolean checkSpTempIsExist(String tempId) {
		boolean tmp = dao.exists(BaseEntity.ID, tempId, ServiceProviderTemp.TABLE_NAME_TEMP);
		if(!tmp){
			ServiceProvider sp = (ServiceProvider) dao.findById(tempId, ServiceProvider.TABLE_NAME, ServiceProvider.class);
			if(sp.getUserId() == null){
				return true;
			}
		}
	    return tmp;
    }
	

	public void deleteMyRegionalList(IDS ids){
		for(String id: ids.getIds()){
			SpCategoryLocation spc = new SpCategoryLocation();
			spc.setId(id);
			this.dao.deleteById(spc);
		}
	}

}
