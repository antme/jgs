package com.zcyservice.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.zcy.bean.Pagination;
import com.zcy.bean.SystemConfig;
import com.zcy.bean.User;
import com.zcy.cfg.CFGManager;
import com.zcy.dao.IQueryDao;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcy.dbhelper.DataBaseQueryOpertion;
import com.zcy.exception.ResponseException;
import com.zcy.util.EcUtil;
import com.zcy.util.HttpClientUtil;
import com.zcyservice.bean.Category;
import com.zcyservice.bean.Location;
import com.zcyservice.bean.ServiceOrder;
import com.zcyservice.bean.ServiceScore;
import com.zcyservice.bean.SmsMessage;
import com.zcyservice.bean.SpCategoryLocation;
import com.zcyservice.bean.vo.ReturnSms;
import com.zcyservice.bean.vo.SearchVo;
import com.zcyservice.service.ICategoryService;
import com.zcyservice.service.IECommerceUserService;
import com.zcyservice.service.ILocationService;
import com.zcyservice.service.IOrderService;
import com.zcyservice.service.ISmsService;
import com.zcyservice.service.ISpCategoryLocationService;
import com.zcyservice.util.ProductOrderStatus;

public class SystemSchedule {
	public static final String DEFAULT_COMMENTS = "默认好评";

	private static Logger logger = LogManager.getLogger(SystemSchedule.class);

	@Autowired
	public IQueryDao dao;

	@Autowired
	public ISmsService smsService;

	@Autowired
	public IOrderService orderService;

	@Autowired
	public IECommerceUserService us;

	@Autowired
	public ILocationService los;

	@Autowired
	public ISpCategoryLocationService spcates;

	@Autowired
	public ICategoryService cateService;

	public void run() {
		logger.info("schedule run start");
		sendOrderIntallNoticeMsg();

		// 测试环境不允许读取数据
		if (CFGManager.isProductEnviroment()) {
			getSmsReply();
		}

		generateOrder24HoursNotice();

		orderService.updateServiceOrderOperator();

		orderService.updateProductOrderOperator();

		changeOrderToOtherSp();

		updateLocationGeoInfo();

		updateSpcategoryInfo();

		closeSercieOrder();

		checkAccountStatus();

		updateLocationAndCategoryName();

		logger.info("schedule run end");
	}

	private void updateLocationAndCategoryName() {
		DataBaseQueryBuilder locationQuery = new DataBaseQueryBuilder(Location.TABLE_NAME);
		locationQuery.and(DataBaseQueryOpertion.NULL, Location.LO_WHOLE_NAME);
		List<Location> locationList = this.dao.listByQuery(locationQuery, Location.class);
		for (Location location : locationList) {

			location.setLoWholeName(los.getLocationString(location.getId(), ""));
			this.dao.updateById(location);
		}

		DataBaseQueryBuilder cateQuery = new DataBaseQueryBuilder(Category.TABLE_NAME);
		cateQuery.and(DataBaseQueryOpertion.NULL, Category.CA_WHOLE_NAME);
		List<Category> cateList = this.dao.listByQuery(cateQuery, Category.class);
		for (Category cate : cateList) {

			cate.setCaWholeName(cateService.getCateStringRecu(cate.getId()));
			this.dao.updateById(cate);
		}

	}

	private void checkAccountStatus() {

		try {
			HttpClientUtil.getLngAndLat("北京市百度大厦", null);
			CFGManager.remove(SystemConfig.BAIDU_MAP_KEY_ERROR);
		} catch (ResponseException e) {
			CFGManager.setProperties(SystemConfig.BAIDU_MAP_KEY_ERROR, e.getMessage());
		}

		ReturnSms sms = smsService.getSmsRemainingMongey(null);

		if (sms != null) {
			if (sms.getReturnstatus() == null || !(sms.getReturnstatus().equalsIgnoreCase("Sucess") || sms.getReturnstatus().equalsIgnoreCase("Success"))) {
				CFGManager.setProperties(SystemConfig.SMS_ACCOUNT_ID_ERROR, "短信账号异常:" + sms.getMessage());
			} else if (EcUtil.getInteger(sms.getOverage(), 0) < 2000) {
				CFGManager.setProperties(SystemConfig.SMS_ACCOUNT_ID_ERROR, "短信账号余额不足,目前为:" + sms.getOverage() + "条,请尽快充值");
			} else {
				CFGManager.remove(SystemConfig.SMS_ACCOUNT_ID_ERROR);
			}
		}

	}

	private void closeSercieOrder() {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		query.and(DataBaseQueryOpertion.NULL, ServiceOrder.CLOSE_DATE);
		query.and(ServiceOrder.SO_STATUS, ProductOrderStatus.DONE);
		int days = EcUtil.getInteger(CFGManager.getProperty(SystemConfig.ORDER_EVALUATION_DEFAULT_DAYS), 3);

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -days);
		query.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, ServiceOrder.FINISH_DATE, c.getTime());

		query.limitColumns(new String[] { ServiceOrder.ID, ServiceOrder.USER_ID, ServiceOrder.SP_ID });

		List<ServiceOrder> orderList = this.dao.listByQuery(query, ServiceOrder.class);
		for (ServiceOrder order : orderList) {
			order.setCloseDate(new Date());
			this.dao.updateById(order);

			ServiceScore score = new ServiceScore();
			score.setSoId(order.getId());
			score.setUserScoreType(ServiceScore.USER_SCORE_GOOD);
			score.setUserScoreComment(DEFAULT_COMMENTS);

			score.setUserId(order.getUserId());
			score.setSpId(order.getSpId());

			orderService.addServiceOrderScore(score);
		}

	}

	private void updateSpcategoryInfo() {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(SpCategoryLocation.TABLE_NAME);
		query.or(DataBaseQueryOpertion.NULL, SpCategoryLocation.CITY_ID);
		query.or(DataBaseQueryOpertion.NULL, SpCategoryLocation.PROVINCE_ID);
		query.or(DataBaseQueryOpertion.NULL, SpCategoryLocation.SECOND_CATEGORY_ID);
		query.or(DataBaseQueryOpertion.NULL, SpCategoryLocation.PRIMARY_CATEGORY_ID);
		List<SpCategoryLocation> list = this.dao.listByQuery(query, SpCategoryLocation.class);

		for (SpCategoryLocation sl : list) {
			spcates.updateCategoryInfo(sl, sl.getCategory_id());
			spcates.updateLocationInfo(sl, sl.getLocation_id());
		}

	}

	private void updateLocationGeoInfo() {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Location.TABLE_NAME);

		query.or(DataBaseQueryOpertion.LESS_THAN_EQUAILS, Location.LNG, 0);
		query.or(DataBaseQueryOpertion.NULL, Location.LNG);

		Pagination pagination = new Pagination();
		pagination.setPage(0);
		// default is 10;
		pagination.setRows(200);
		query.pagination(pagination);
		List<Location> locationList = this.dao.listByQueryWithPagnation(query, Location.class).getEntityList();
		logger.info("update location geo info" + locationList.size());
		for (Location loc : locationList) {
			String address = los.getLocationString(loc.getId(), loc.getDefaultAddress());
			Map<String, Object> location = HttpClientUtil.getLngAndLat(address);
			if (!EcUtil.isEmpty(location)) {
				loc.setLng(EcUtil.getDouble(location.get("lng"), null));
				loc.setLat(EcUtil.getDouble(location.get("lat"), null));
				this.dao.updateById(loc);
			}

		}

	}

	private void changeOrderToOtherSp() {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		query.and(DataBaseQueryOpertion.LESS_THAN, ServiceOrder.EXPIRED_DATE, new Date());
		query.and(DataBaseQueryOpertion.IN, ServiceOrder.SO_STATUS, new String[] { ProductOrderStatus.ACCEPTED.toString(), ProductOrderStatus.NEED_SP_CONFIRM.toString() });

		List<ServiceOrder> list = this.dao.listByQuery(query, ServiceOrder.class);
		for (ServiceOrder order : list) {
			order.setSoStatus(ProductOrderStatus.TERMINATED);
			if (order.getSoStatus().equals(ProductOrderStatus.NEED_SP_CONFIRM)) {
				order.setRejectRemark(String.format("用户确认到货后订单【%s】由于超过系统设置时间未被接受转人工", order.getPoCode()));
				order.setRejectReason(String.format("超时未接受订单", order.getPoCode()));
				logger.info(String.format("用户确认到货后订单【%s】由于超过系统设置时间未被接受转人工", order.getPoCode()));

			} else {
				order.setRejectRemark(String.format("用户确认到货后订单【%s】由于超过系统设置时间未派工转人工", order.getPoCode()));
				order.setRejectReason(String.format("超时未派工", order.getPoCode()));
				logger.info(String.format("用户确认到货后订单【%s】由于超过系统设置时间未被接受转人工", order.getPoCode()));

			}
			this.dao.updateById(order);
		}

	}

	private void generateOrder24HoursNotice() {
		updateOrderOperator(0);
		updateOrderOperator(1);
	}

	public void updateOrderOperator(int type) {
		SearchVo vo = new SearchVo();
		vo.setOrderNoticType(type);
		DataBaseQueryBuilder builder = orderService.getOrderNoticeQueryBuilder(vo);
		List<ServiceOrder> orders = this.dao.listByQuery(builder, ServiceOrder.class);

		logger.info(type);
		logger.info(orders);

		for (ServiceOrder order : orders) {
			smsService.addOrderPassedNotice(order);
			User user = us.getServiceOrderNoticeOperator(order);
			if (user != null) {
				order.setOperatorId(user.getId());
			}

			this.dao.updateById(order);

		}
	}

	private void getSmsReply() {
		logger.debug("Run get sms reply");
		smsService.listSmsReply();
	}

	private void sendOrderIntallNoticeMsg() {
		logger.debug("Run system schedule");
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(SmsMessage.TABLE_NAME);
		builder.and(DataBaseQueryOpertion.IS_FALSE, SmsMessage.IS_SEND);

		Calendar c = Calendar.getInstance();
		builder.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, SmsMessage.SEND_DATE, c.getTime());

		List<SmsMessage> smsList = this.dao.listByQuery(builder, SmsMessage.class);
		for (SmsMessage sms : smsList) {
			smsService.sendSms(sms);
		}
	}

}
