package com.jgsservice.service.impl;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import com.jgs.exception.ResponseException;
import com.jgs.log.EcJDBCAppender;
import com.jgs.util.DateUtil;
import com.jgs.util.EcThreadLocal;
import com.jgs.util.EcUtil;
import com.jgs.util.ExcleUtil;
import com.jgs.util.HttpClientUtil;
import com.jgs.validators.ValidatorUtil;
import com.jgsservice.bean.Bill;
import com.jgsservice.bean.BillStatus;
import com.jgsservice.bean.Category;
import com.jgsservice.bean.Complaint;
import com.jgsservice.bean.Location;
import com.jgsservice.bean.Manufacturer;
import com.jgsservice.bean.ProductOrder;
import com.jgsservice.bean.ServiceOrder;
import com.jgsservice.bean.ServiceProvider;
import com.jgsservice.bean.ServiceScore;
import com.jgsservice.bean.SpCategoryLocation;
import com.jgsservice.bean.Complaint.ComplaintStaus;
import com.jgsservice.bean.vo.ComplaintSearchVo;
import com.jgsservice.bean.vo.IDS;
import com.jgsservice.bean.vo.SearchVo;
import com.jgsservice.bean.vo.ServiceOrderForComplaintVO;
import com.jgsservice.bean.vo.UserOrderVO;
import com.jgsservice.service.EcommerceService;
import com.jgsservice.service.IComplaintService;
import com.jgsservice.service.IECommerceUserService;
import com.jgsservice.service.ILocationPriceService;
import com.jgsservice.service.IOrderService;
import com.jgsservice.service.ISProviderService;
import com.jgsservice.service.ISmsService;
import com.jgsservice.service.ISpCategoryLocationService;
import com.jgsservice.util.PermissionConstants;
import com.jgsservice.util.ProductOrderStatus;
import com.jgsservice.util.Role;
import com.jgsservice.util.UserStatus;

@Service("productOrderService")
public class OrderServiceImpl extends EcommerceService implements IOrderService {

	private static Logger logger = LogManager.getLogger(OrderServiceImpl.class);

	@Autowired
	private IECommerceUserService us;

	@Autowired
	private ISmsService sms;


	@Autowired
	private ILocationPriceService lps;

	
	@Autowired
	private ISProviderService sps;
	
	
	@Autowired
	private ISpCategoryLocationService spCateLocService;
	
	
	@Autowired
	private IComplaintService cmpService;


	@Override
	@Transactional
	public List<String> importTaoBoOrder(InputStream inputStream, boolean isOverride) {

		List<String> msgs = new ArrayList<String>();
		List<String> orders = new ArrayList<String>();

		if (inputStream == null) {
			throw new ResponseException("文件上传失败，请稍后再试");
		}

		ExcleUtil excleUtil = new ExcleUtil(inputStream);

		for (int index = 0; index < excleUtil.getNumberOfSheets(); index++) {
			List<String[]> list = excleUtil.getAllData(index);

			if(list.isEmpty()){
				continue ;
			}
			Map<String, Integer> keyMap = new LinkedHashMap<String, Integer>();

			if (list.get(0) != null) {
				for (int i = 0; i < list.get(0).length; i++) {
					String key = list.get(0)[i].trim();
					if (!EcUtil.isEmpty(key)) {
						keyMap.put(key, i);
					}
				}
			}

			for (int i = 1; i < list.size(); i++) {// 从第2行开始读数据
				String errorMsg = "";

				String[] row = list.get(i);

				String code = getRowColumnValue(row, keyMap, "订单编号");
				if (code.length() > 15 || code.indexOf("E") != -1) {
					try {
						BigDecimal bd = new BigDecimal(code);
						code = bd.toPlainString();
					} catch (Exception e) {
						logger.error("读取订单号错误: " + code);
						// do nothing
					}
				}

				String receiverName = getRowColumnValue(row, keyMap, "收货人姓名");
				String receiverAddress = getRowColumnValue(row, keyMap, "收货地址");
				String goodsTitle = getRowColumnValue(row, keyMap, "宝贝标题");
				Integer goodsNumber = null;
				String remark = getRowColumnValue(row, keyMap, "订单备注");
				String goodsNumStr = getRowColumnValue(row, keyMap, "宝贝总数量");
				String receiveMobilePhone = getRowColumnValue(row, keyMap, "联系手机");
				String orderFrom = getRowColumnValue(row, keyMap, "订单来源");

				String prefix = "TB";
				if (orderFrom != null) {
					if (orderFrom.contains("京东")) {
						prefix = "JD";
					} else if (orderFrom.contains("易讯")) {
						prefix = "YX";
					} else if (orderFrom.contains("亚马逊")) {
						prefix = "YMX";
					} else if (orderFrom.contains("苏宁")) {
						prefix = "SN";
					}

				}
				String poCode = prefix + "-" + code;

				if (EcUtil.isEmpty(receiveMobilePhone)) {
					errorMsg = errorMsg.concat("联系手机不能为空 ");
				}

				DataBaseQueryBuilder userQuery = new DataBaseQueryBuilder(User.TABLE_NAME);
				userQuery.and(User.MOBILE_NUMBER, receiveMobilePhone);
				userQuery.and(DataBaseQueryOpertion.NOT_EQUALS, User.ROLE_NAME, Role.USER);

				if (this.dao.exists(userQuery)) {
					errorMsg = errorMsg.concat("此订单手机号已经注册，但是用户为非用户组，此订单将被过滤掉");
				}

				userQuery = new DataBaseQueryBuilder(User.TABLE_NAME);
				userQuery.and(User.STATUS, UserStatus.LOCKED.toString());
				userQuery.and(User.MOBILE_NUMBER, receiveMobilePhone);

				if (this.dao.exists(userQuery)) {
					errorMsg = errorMsg.concat("此订单手机号用户已经被冻结，此订单将被过滤掉");
				}

				if (!EcUtil.isEmpty(goodsNumStr)) {
					try {
						goodsNumber = Integer.valueOf(goodsNumStr);
					} catch (Exception e) {
						logger.error("非法数字", e);
					}
				}

				if (EcUtil.isEmpty(code)) {
					// 订单编号为空，继续循环
					continue;
					// errorMsg = errorMsg.concat("订单编号不能为空 ");
				} else if (!EcUtil.isCharNum(code)) {
					errorMsg = errorMsg.concat("订单编号只能为字母和数字 ");
				}

				if (EcUtil.isEmpty(receiverName)) {
					errorMsg = errorMsg.concat("收货人姓名不能为空 ");
				}
				if (EcUtil.isEmpty(receiverAddress)) {
					errorMsg = errorMsg.concat("收货地址不能为空 ");
				}
				if (EcUtil.isEmpty(goodsTitle)) {
					errorMsg = errorMsg.concat("宝贝标题不能为空 ");
				}
				if (EcUtil.isEmpty(goodsNumber)) {
					errorMsg = errorMsg.concat("宝贝总数量不能为空或不是数字 ");
				}

				if (!needInstallOrder(remark)) {// 检测是否需要‘安装服务’，空字符床为true
					errorMsg = errorMsg.concat("此订单已备注无需安装");
				}

				if (goodsNumber != null && goodsNumber < 0) {
					errorMsg = errorMsg.concat("宝贝总数量不能小于0 ");
				}

				if (this.dao.exists(ProductOrder.PO_CODE, poCode, ProductOrder.TABLE_NAME) && !isOverride) {
					errorMsg = errorMsg.concat(String.format("此订单已经存在[%s] ", code));
				}

				if (isOverride) {
					DataBaseQueryBuilder query = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
					query.and(ProductOrder.PO_CODE, poCode);
					query.and(DataBaseQueryOpertion.NOT_EQUALS, ProductOrder.PO_STATUS, ProductOrderStatus.INACTIVE);

					if (this.dao.exists(query)) {
						errorMsg = errorMsg.concat(String.format("此订单已经存在[%s] ", code));
					}
				}

				if (!EcUtil.isEmpty(errorMsg)) {
					errorMsg = String.format("sheet【%s】, 第%s行：", excleUtil.getSheetName(index), i + 1).concat(errorMsg);
					msgs.add(errorMsg);
				} else {
					ProductOrder order = new ProductOrder();
					order.setPoCode(poCode);
					order.setPoMemberName(getRowColumnValue(row, keyMap, "买家会员名"));
					order.setPoReceiverName(receiverName);
					order.setPoReceiverAddress(receiverAddress);
					String contactPhone = getRowColumnValue(row, keyMap, "联系电话");
					receiveMobilePhone = receiveMobilePhone.replaceAll("'", "");
					contactPhone = contactPhone.replaceAll("'", "");
					order.setPoReceiverPhone(contactPhone);
					order.setPoReceiverMobilePhone(receiveMobilePhone);
					order.setPoGoodsTitle(goodsTitle);
					order.setPoGoodsCate(getRowColumnValue(row, keyMap, "宝贝种类"));
					order.setPoLogisticsNo(getRowColumnValue(row, keyMap, "物流单号"));
					order.setPoLogisticsCompany(getRowColumnValue(row, keyMap, "物流公司"));
					order.setPoOrderRemark(remark);
					order.setPoGoodsNumber(goodsNumber);

					ProductOrder old = (ProductOrder) this.dao.findByKeyValue(ProductOrder.PO_CODE, poCode, ProductOrder.TABLE_NAME, ProductOrder.class);
					if (old != null) {
						order.setId(old.getId());
					}
					orders.add(order.getPoCode());
					addProductOrder(order);
				}

			}
		}

		if (msgs.size() > 0) {
			if (orders.size() > 0) {
				String msg = String.format("成功导入【%s】个订单,导入失败【%s】个订单,请排查错误后再导入错误订单", orders.size(), msgs.size());
				msgs.add(0, msg);
			} else {
				String msg = String.format("有【%s】个订单导入失败,请排查错误后再导入错误订单", msgs.size());
				msgs.add(0, msg);
			}
		}

		return msgs;

	}

	private String getRowColumnValue(String[] row, Map<String, Integer> keyMap, String title) {

		if (keyMap.get(title) == null && !title.equalsIgnoreCase("订单来源")) {
			throw new ResponseException("模板的标题不对,请在订单导入页面下载模板");
		}
		
		if(keyMap.get(title) == null){
			return null;
		}

		if (keyMap.get(title) >= row.length) {
			return null;
		}

		return row[keyMap.get(title)].trim();
	}

	public EntityResults<ProductOrder> listMfcAllProductOrders(SearchVo search) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
		builder.and(ProductOrder.USER_ID, EcThreadLocal.getCurrentUserId());
		builder.and(DataBaseQueryOpertion.NOT_EQUALS, ProductOrder.PO_STATUS, ProductOrderStatus.INACTIVE);
		builder = mergeProductOrderSearchQuery(search, builder, null);
		builder.orderBy(ProductOrder.ACTIVE_DATE, false);
		builder.orderBy(ProductOrder.CREATED_ON, false);

		return dao.listByQueryWithPagnation(builder, ProductOrder.class);
	}

	public EntityResults<ProductOrder> listProductOrdersForAdmin(SearchVo search) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
		mergeMfcJoinQuery(builder);
		builder = mergeProductOrderSearchQuery(search, builder, null);

		return dao.listByQueryWithPagnation(builder, ProductOrder.class);
	}

	public EntityResults<ServiceOrder> listServiceOrdersForAdmin(SearchVo search) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		mergeSpJoinQuery(builder);

		builder = mergeServiceOrderSearchQuery(search, builder);
		
		return dao.listByQueryWithPagnation(builder, ServiceOrder.class);
	}

	public EntityResults<ProductOrder> listMfcNewProductOrders(SearchVo search) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
		builder.and(ProductOrder.USER_ID, EcThreadLocal.getCurrentUserId());
		builder.and(ProductOrder.PO_STATUS, ProductOrderStatus.INACTIVE);
		builder = mergeProductOrderSearchQuery(search, builder, null);
		return dao.listByQueryWithPagnation(builder, ProductOrder.class);
	}

	public EntityResults<ProductOrder> listSystemRejectedProductOrders(SearchVo search) {
		DataBaseQueryBuilder builder = getSystemRejectOrderQueryBuilder();
		builder = mergeProductOrderSearchQuery(search, builder, ProductOrder.ACTIVE_DATE);

		return dao.listByQueryWithPagnation(builder, ProductOrder.class);
	}

	public DataBaseQueryBuilder getSystemRejectOrderQueryBuilder() {
	    DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
		builder.and(ProductOrder.PO_STATUS, ProductOrderStatus.MANUAL);
		
		if (EcThreadLocal.getCurrentUserId() != null) {
			builder.and(ProductOrder.OPERATOR_ID, EcThreadLocal.getCurrentUserId());
		}

	    return builder;
    }

	public EntityResults<ServiceOrder> listUserRejectedServiceOrders(SearchVo search) {
		DataBaseQueryBuilder builder = getUserRejectedOrderQueryBuilder();
		builder = mergeServiceOrderSearchQuery(search, builder);
		return dao.listByQueryWithPagnation(builder, ServiceOrder.class);
	}

	public DataBaseQueryBuilder getUserRejectedOrderQueryBuilder() {
	    DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
	    mergeSpJoinQuery(builder);
		builder.or(ServiceOrder.SO_STATUS, ProductOrderStatus.REJECTED);
		builder.or(ServiceOrder.SO_STATUS, ProductOrderStatus.TERMINATED);
	
		DataBaseQueryBuilder cbuilder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		cbuilder.and(DataBaseQueryOpertion.IS_FALSE, ServiceOrder.EXPIRED);
		
		if (EcThreadLocal.getCurrentUserId() != null) {
			cbuilder.and(ServiceOrder.OPERATOR_ID, EcThreadLocal.getCurrentUserId());
		}
		
		builder = builder.and(cbuilder);
	    return builder;
    }

	@Transactional
	public void addProductOrders(List<ProductOrder> porders) {
		if(porders.isEmpty()){
			throw new ResponseException("你没有添加任何订单哦， 请先添加订单再导入");
		}
		
		for (ProductOrder order : porders) {	
			addProductOrder(order);
		}
	}

	public void addProductOrder(ProductOrder order) {

		if (order.getId() != null) {
			checkOwner(order);
			dao.updateById(order);
		} else {
			ValidatorUtil.validate(order, "order", "add", PermissionConstants.validateFiles);

			if (this.dao.exists(ProductOrder.PO_CODE, order.getPoCode(), ProductOrder.TABLE_NAME)) {
				throw new ResponseException("订单已经存在 : " + order.getPoCode());
			}
			String currentUserId = EcThreadLocal.getCurrentUserId();
			if (order.getPoStatus() == null) {
				order.setPoStatus(ProductOrderStatus.INACTIVE);
				order.setUserId(currentUserId);
			}
			DataBaseQueryBuilder mfcQuery = new DataBaseQueryBuilder(Manufacturer.TABLE_NAME);
			mfcQuery.and(Manufacturer.USER_ID, currentUserId);
			mfcQuery.limitColumns(new String[] { Manufacturer.ID });

			order.setMfcId(this.dao.findOneByQuery(mfcQuery, Manufacturer.class).getId());

			dao.insert(order);

		}

	}

	@Transactional
	public void activeProductOrders(List<String> ids) {
		checkBatchProductOrderIds(ids);

		for (String id : ids) {
			convertToServiceOrder(id);
		}
		
		addProductOrderOpLog(ids, "激活了如下订单【%s】");

	}
	public void addServiceOrderOpLogOne(String id, String log) {
		List<String> ids = new ArrayList<String>();
		if(EcUtil.isValid(id)){
			ids.add(id);
		}
		addServiceOrderOpLog(ids, log);
	}
	public void addServiceOrderOpLog(List<String> ids, String log) {
		if (ids.size() > 0) {
			DataBaseQueryBuilder query = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
			query.and(DataBaseQueryOpertion.IN, ServiceOrder.ID, ids);
			query.limitColumns(new String[] { ServiceOrder.SO_CODE });
			List<ServiceOrder> orderList = this.dao.listByQuery(query, ServiceOrder.class);

			if (orderList != null) {
				logger.info(String.format(log, orderList.toString()));
			}
		}
    }
	
	public void addProductOrderOpLogOne(String id, String log) {
		List<String> ids = new ArrayList<String>();
		if(EcUtil.isValid(id)){
			ids.add(id);
		}
		addProductOrderOpLog(ids, log);
	}
	public void addProductOrderOpLog(List<String> ids, String log) {
		if (ids.size() > 0) {
			DataBaseQueryBuilder query = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
			query.and(DataBaseQueryOpertion.IN, ProductOrder.ID, ids);
			query.limitColumns(new String[] { ProductOrder.PO_CODE });
			List<ProductOrder> orderList = this.dao.listByQuery(query, ProductOrder.class);

			if (orderList != null) {
				logger.info(String.format(log, orderList.toString()));
			}
		}
    }

	@Transactional
	public void deleteProductOrders(List<String> ids) {
		checkBatchProductOrderIds(ids);
		DataBaseQueryBuilder deleteQuery = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
		deleteQuery.and(DataBaseQueryOpertion.IN, ProductOrder.ID, ids);
		this.dao.deleteByQuery(deleteQuery);
		addProductOrderOpLog(ids, "删除了如下订单【%s】");
	}
	
	
	public void deleteAllProductOrders() {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
		query.and(ProductOrder.USER_ID, EcThreadLocal.getCurrentUserId());
		query.and(ProductOrder.PO_STATUS, ProductOrderStatus.INACTIVE);
		dao.deleteByQuery(query);
		logger.info("删除了所有未激活的产品订单");

		
	}

	public void checkBatchProductOrderIds(List<String> ids) {
	    DataBaseQueryBuilder query = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
		query.and(ProductOrder.PO_STATUS, ProductOrderStatus.INACTIVE);
		checkIdsStatus(query, ids);
    }

	public void cancelProductOrders(IDS idEntity) {
		DataBaseQueryBuilder checkQuery = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
		checkQuery.and(ProductOrder.PO_STATUS, ProductOrderStatus.MANUAL.toString());
		checkIdsStatus(checkQuery, idEntity.getIds(), null);
		
		List<String> ids = idEntity.getIds();
		DataBaseQueryBuilder builder = getSystemRejectOrderQueryBuilder();
		checkIdsStatus(builder, ids, null);
		
		for (String id : ids) {
			ProductOrder po = new ProductOrder();
			po.setId(id);
			po.setPoStatus(ProductOrderStatus.CANCELLED);
			po.setCancelDate(new Date());
			
			DataBaseQueryBuilder query = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
			query.and(ProductOrder.ID, id);
			query.limitColumns(new String[]{ProductOrder.PO_CODE});
			ProductOrder order = (ProductOrder) this.dao.findOneByQuery(query, ProductOrder.class);
			String log = String.format("产品订单【%s】被管理员取消", order.getPoCode());
			logger.info(log);
			this.dao.updateById(po);
			
			updateProductOrderComments(id, EcThreadLocal.getCurrentUserId(), idEntity.getComments(), "取消订单");
			
			
	
		}
	}

	public void cancelServiceOrders(IDS idEntity) {
		
		List<String> ids = idEntity.getIds();
		DataBaseQueryBuilder checkQuery = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		checkQuery.or(ServiceOrder.SO_STATUS, ProductOrderStatus.REJECTED.toString());
		checkQuery.or(ServiceOrder.SO_STATUS, ProductOrderStatus.TERMINATED.toString());
		checkIdsStatus(checkQuery, ids, null);

		DataBaseQueryBuilder builder = getUserRejectedOrderQueryBuilder();
		checkIdsStatus(builder, ids, null);
		for (String id : ids) {
			ServiceOrder so = new ServiceOrder();
			so.setId(id);
			so.setSoStatus(ProductOrderStatus.CANCELLED);
			so.setCancelDate(new Date());
			
			DataBaseQueryBuilder query = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
			query.and(ServiceOrder.ID, id);
			query.limitColumns(new String[]{ServiceOrder.SO_CODE});
			ServiceOrder order = (ServiceOrder) this.dao.findOneByQuery(query, ServiceOrder.class);
			String log = String.format("服务订单【%s】被管理员取消", order.getSoCode());
			logger.info(log);
            
			this.dao.updateById(so);

			updateServiceOrderComments(id, EcThreadLocal.getCurrentUserId(), idEntity.getComments(), "取消订单");
			this.updateProductOrderStatus(id, ProductOrderStatus.CANCELLED);
			


		}
	}

	private boolean needInstallOrder(String remark){
		boolean need = true;
		if (EcUtil.isValid(remark)) {
			String ignoreKeyword = CFGManager.getProperty(SystemConfig.ORDER_IMPORT_IGNORE_KEYWORD);
			if (EcUtil.isValid(ignoreKeyword)) {
				String[] keywordInstall = ignoreKeyword.split(" ");
				for (String key : keywordInstall) {
					if (remark.indexOf(key) > -1) {
						need = false;
						break;
					}
				}
			}
		}
		return need;
	}
	
	@Transactional
	private void convertToServiceOrder(String pdoId) {
		
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
		builder.and(BaseEntity.ID, pdoId);

		ProductOrder productOrder = (ProductOrder) this.dao.findOneByQuery(builder, ProductOrder.class);

		ServiceOrder sor = (ServiceOrder) EcUtil.toEntity(productOrder.toMap(), ServiceOrder.class);

		sor.setPoCode(productOrder.getPoCode());
		sor.setPoId(productOrder.getId());
		sor.setSoCode("FW" + productOrder.getPoCode());
		sor.setSoStatus(ProductOrderStatus.NEED_SP_CONFIRM);

		sor.setNeedNotice(true);
		sor.setIsNoticed(false);
		sor.setExpired(false);
		sor.setSmsInstallNoticed(false);
		sor.setIsGoodsArrived(false);
		sor.setSpNeedBill(true);
		sor.setMfcNeedBill(true);

		sor.setMfcId(productOrder.getMfcId());
		sor.setMfcUserId(productOrder.getUserId());
		sor.setPrice(0);
		boolean findSp = false;
		
		try {
			findSp = findSpForServiceOrder(productOrder, sor);
		} catch (ResponseException e) {
			logger.error(e);
		}

		productOrder.setActiveDate(new Date());

		if (!findSp) {
			productOrder.setPoStatus(ProductOrderStatus.MANUAL);
			
			if(productOrder.getOperatorId() == null){
				User user = us.getManualProductOrderOperator(productOrder);
				if(user!=null){
					productOrder.setOperatorId(user.getId());
				}
			}
			this.dao.updateById(productOrder);

		} else {
			dao.insert(sor);
			
			productOrder.setPoStatus(ProductOrderStatus.NEED_SP_CONFIRM);
			this.dao.updateById(productOrder);
		}
	}

	@Transactional
	private boolean findSpForServiceOrder(ProductOrder porder, ServiceOrder sor) {
	    boolean findSp = false;
		
	    Location loc = updateOrderLocation(porder, sor);


		Category cate = updateOrderCategory(porder, sor);
		

		List<SpCategoryLocation> spCateList = spCateLocService.listSpCategoryLocationByCate(cate);
		
		if (spCateList.isEmpty()) {
			String errormsg = String.format("通过订单标题[%s]获取服务商失败", sor.getPoGoodsTitle());
			logger.error(errormsg);
			
			String reson = "获取服务商失败";

			updateProductOrderSplitReson(porder, errormsg, reson);
		}
		
		Integer splitOrderNumber = null;
		
		if(cate != null){
			splitOrderNumber = cate.getSplitOrderNumber();
		}

		if(splitOrderNumber == null){
			splitOrderNumber = 1;
		}
		
		boolean splitOrderNumberLimit = false;
		if (splitOrderNumber != null && splitOrderNumber < sor.getPoGoodsNumber()) {
			String errormsg = String.format("订单数量限制, 品类数量[%s]，分类拆单数量[%s]， 转换为人工", sor.getPoGoodsNumber(), splitOrderNumber);
			logger.info(errormsg);
			updateProductOrderSplitReson(porder, errormsg, "拆单数量限制");
			splitOrderNumberLimit = true;
		}

		if ((cate == null || EcUtil.isEmpty(loc) || spCateList.isEmpty() || splitOrderNumberLimit) && CFGManager.isProductEnviroment()) {
			// do nothing, only log
			logger.info(String.format("订单【%s】将转为人工处理", sor.getSoCode()));
		} else {

			
			List<ServiceProvider> spSearchList = sps.findServiceProvider(sor, spCateList);

			
		
			if (spSearchList.isEmpty() && CFGManager.isProductEnviroment()) {
				logger.info(String.format("订单【%s】将转为人工处理由于找不到距离内的服务商", sor.getSoCode()));
				// do nothing, only log
				String errormsg = String.format("订单【%s】将转为人工处理找不到距离内的服务商", sor.getSoCode());
				logger.info(errormsg);
				updateProductOrderSplitReson(porder, errormsg, "搜索距离内服务商失败");
				
			} else {


				List<ServiceProvider> spList = new ArrayList<ServiceProvider>();
				if (!spSearchList.isEmpty()) {
					// 查询差评服务商，过滤掉
					User user = (User) this.dao.findByKeyValue(User.MOBILE_NUMBER, sor.getPoReceiverMobilePhone(), User.TABLE_NAME, User.class);
					Set<String> spIds = new HashSet<String>();

					if (user != null) {
						DataBaseQueryBuilder scoreBuilder = new DataBaseQueryBuilder(ServiceScore.TABLE_NAME);
						scoreBuilder.and(ServiceScore.USER_ID, user.getId());
						scoreBuilder.and(DataBaseQueryOpertion.NOT_NULL, ServiceScore.SP_ID);
						scoreBuilder.and(ServiceScore.USER_SCORE_TYPE, ServiceScore.USER_SCORE_BAD);
						List<ServiceScore> scoreList = this.dao.listByQuery(scoreBuilder, ServiceScore.class);
						for (ServiceScore score : scoreList) {
							spIds.add(score.getSpId());
						}
					}

					for (ServiceProvider sp : spSearchList) {
						if(spIds.isEmpty()){
							spList.add(sp);
						}else{
							if (!spIds.contains(sp.getId())) {
								spList.add(sp);
							} else {
								logger.info(String.format("<<<<<<<<<<<<<<<<<<<订单用户[%s]过滤差评服务商[%s][%s]", sor.getPoReceiverMobilePhone(), sp.getSpCode(), sp.getSpUserName()));
							}
						}
					}
				}

				if (spList.isEmpty() && CFGManager.isDevEnviroment()) {
					// 如果测试环境，方便找不到服务商的时候从数据库直接获取所有的服务商来模拟数据

					spList = this.dao.listByQuery(new DataBaseQueryBuilder(ServiceProvider.TABLE_NAME).and(DataBaseQueryOpertion.NOT_NULL, ServiceProvider.USER_ID),
					        ServiceProvider.class);
					int random = (int) (Math.random() * 100);
					if (random <= spList.size() + 40) {
						spList = new ArrayList<ServiceProvider>();
					} else {
						logger.info("**********************测试环境，模拟获取服务商数据**********************");
					}

				}
				
				if( sor.getPrice() == null || sor.getPrice() < 1){
					// do nothing, only log
					String cateName = cate == null? "" :  cate.getName();
					String locationName = loc == null? "": loc.getName();
					String errormsg = String.format("服务订单【%s】根据品类【%s】和地域【%s】获取价格失败,请管理员设置此区域品类的价格", sor.getSoCode(), cateName, locationName);
					logger.info(errormsg);
					updateProductOrderSplitReson(porder, errormsg, "搜索服务价格失败");
					
					if(CFGManager.isProductEnviroment()){
						spList = new ArrayList<ServiceProvider>();
					}
		
				}
				
				if (spList.isEmpty()) {
					// do nothing, only log
					String errormsg = String.format("订单【%s】将转为人工处理找不到距离内的服务商", sor.getSoCode());
					logger.info(errormsg);
					updateProductOrderSplitReson(porder, errormsg, "搜索距离内服务商失败");
				} else {
					int minDistance = 0;

					for (ServiceProvider provider : spList) {
						int distance = 0;

						if(provider.getDistance()!=null){
							distance = provider.getDistance().intValue();

						}

						if (minDistance == 0) {
							minDistance = distance;
						} else {

							if (minDistance > distance) {
								minDistance = distance;
							}
						}
						logger.info(String.format("订单【%s】【%S】距离服务商【%s】最近的服务点距离是【%s】米", sor.getSoCode(), sor.getPoReceiverAddress(), provider.getSpUserName(), distance));

					}
					
					List<ServiceProvider> spSelectList = new ArrayList<ServiceProvider>();
					for (ServiceProvider provider : spList) {
						int distance = 0;
						if(provider.getDistance()!=null){
							distance = provider.getDistance().intValue();
						}
			
						if (distance == minDistance) {
							spSelectList.add(provider);
						}

					}

					int index = (int) (Math.random() * 100) % (spSelectList.size());

					if (index >= spSelectList.size()) {
						index = spSelectList.size() - 1;
					}

					ServiceProvider sp = spSelectList.get(index);
			
					sor.setSpUserId(sp.getUserId());
					sor.setSpId(sp.getId());

					findSp = true;

					generateNewUserByOrder(sor);

				}
			}
		}
	    return findSp;
    }



	private void updateProductOrderSplitReson(ProductOrder porder, String errormsg, String reson) {
	    if(porder !=null){
	    	if(porder.getSysSplitReason() !=null){
	    		porder.setSysSplitReason(porder.getSysSplitReason() + ", " + reson);
	    	}else{
	    		porder.setSysSplitReason(reson);
	    	}
	    	if(porder.getSysSplitReasonRemark() !=null){
	    		porder.setSysSplitReasonRemark(porder.getSysSplitReasonRemark() + ", "  + errormsg);
	    	}else{
	    		porder.setSysSplitReasonRemark(errormsg);
	    	}
	    	
	    }
    }

	public Category updateOrderCategory(ProductOrder porder, ServiceOrder sor) {
		Category cate = null;
		if (EcUtil.isEmpty(sor.getCateId())) {
			cate = setCategoryInfo(porder, sor);
		} else {
			cate = (Category) this.dao.findById(sor.getCateId(), Category.TABLE_NAME, Category.class);
			
			if(cate == null){
				cate = setCategoryInfo(porder, sor);
			}
		}
		if (cate != null && sor.getLocationId() != null) {
			sor.setPrice(lps.getPrice(sor.getLocationId(), cate.getId()));
		}
		return cate;
	}

	public Category setCategoryInfo(ProductOrder porder, ServiceOrder sor) {
	    Category cate;
	    // 更具品类查询服务商
	    cate = cateService.searchCategoryByKeyword(sor.getPoGoodsTitle());
	    if (cate != null) {
	    	sor.setCateId(cate.getId());

	    

	    	logger.info(String.format("通过订单标题[%s]获取分类[%s]", sor.getPoGoodsTitle(), cate.getName()));
	    } else {
	    	String errormsg = String.format("通过订单标题[%s]获取分类失败", sor.getPoGoodsTitle());
	    	if (porder != null) {
	    		updateProductOrderSplitReson(porder, errormsg, "品类匹配失败");
	    	}
	    	logger.error(errormsg);
	    }
	    return cate;
    }

	public Location updateOrderLocation(ProductOrder porder, ServiceOrder sor) {
	    // 查询地理位置
		Location loc = null;
		
		Map<String, Object> location = HttpClientUtil.getLngAndLat(sor.getPoReceiverAddress().replaceAll(" ", ""));
		if (!EcUtil.isEmpty(location)) {
			if (!EcUtil.isEmpty(location.get("lng"))) {
				sor.setLng(EcUtil.getDouble(location.get("lng"), null));
			}

			if (!EcUtil.isEmpty(location.get("lat"))) {
				sor.setLat(EcUtil.getDouble(location.get("lat"), null));
			}

			logger.info(String.format("通过订单收货人地址[%s]获取地理百度经度纬度[%s,%s]", sor.getPoReceiverAddress(), location.get("lng"), location.get("lat")));

			if (sor.getLng() != null && sor.getLat() != null) {
				loc = locationService.getLocationByLngAndLat(sor.getPoReceiverAddress(), sor.getLng().toString(), sor.getLat().toString());
			}
			if (loc != null) {
				sor.setLocationId(loc.getId());
				logger.info(String.format("通过订单收货人地址[%s]获取系统区域地理信息[%s]", sor.getPoReceiverAddress(), loc.getName()));
				
				if (sor.getPoId() != null) {
					ProductOrder po = new ProductOrder();
					po.setId(sor.getPoId());
					po.setLocationId(sor.getLocationId());
					this.dao.updateById(po);
				}
				

			} else {
				String errormsg = String.format("通过订单收货人地址[%s]获取系统区域地理信息失败", sor.getPoReceiverAddress());
				logger.error(errormsg);
				if (porder != null) {

					updateProductOrderSplitReson(porder, errormsg, "地址解析失败");
				}
			}
		} else {
			String errormsg = String.format("通过订单收货人地址[%s]获取地理百度位置信息失败", sor.getPoReceiverAddress());

			if (porder != null) {

				updateProductOrderSplitReson(porder, errormsg, "地址解析失败");
			}

			logger.error(errormsg);
		}
	    return loc;
    }
	


	public void generateNewUserByOrder(ServiceOrder sor) {
	    
	    
	    User user = new User();

	    if (!this.dao.exists(User.MOBILE_NUMBER, sor.getPoReceiverMobilePhone(), User.TABLE_NAME)) {

	    	user.setAddresses(sor.getPoReceiverAddress());
	    	user.setDefaultAddress(sor.getPoReceiverAddress());
	    	user.setRoleName(Role.USER.toString());
	    	user.setMobileNumber(sor.getPoReceiverMobilePhone());
	    	user.setName(sor.getPoReceiverName());
	    	user.setUserName(sor.getPoReceiverMobilePhone());
	    	user.setStatus(UserStatus.NORMAL.toString());

	    	User temp = us.regUser(user);

	    	sor.setUserId(temp.getId());
	    	// 初始密码为手机号
			sms.sendNewUserNotice(user.getMobileNumber(), user.getMobileNumber());
	    } else {
	    	user = (User) this.dao.findByKeyValue(User.MOBILE_NUMBER, sor.getPoReceiverMobilePhone(), User.TABLE_NAME, User.class);
	    	sor.setUserId(user.getId());
	    }

	 
    }

	public EntityResults<ServiceOrder> listSpHistoryServiceOrders(SearchVo search) {
		DataBaseQueryBuilder builder = null;
		if (EcUtil.isValid(search.getOrderStatus()) && search.getOrderStatus().equalsIgnoreCase("COMPLAINT")) {
			builder = cmpService.getComplaintOrderQuery(search, false);
			builder.orderBy(Complaint.UPDATED_ON, false);
		} else {
			builder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
			builder.and(ServiceOrder.SP_USER_ID, EcThreadLocal.getCurrentUserId());
			builder.and(DataBaseQueryOpertion.NOT_IN, ServiceOrder.SO_STATUS, new String[] { ProductOrderStatus.NEED_SP_CONFIRM.toString(), ProductOrderStatus.ACCEPTED.toString(),
			        ProductOrderStatus.ASSIGNED.toString() });
			builder = mergeServiceOrderSearchQuery(search, builder);
			builder.orderBy(ServiceOrder.UPDATED_ON, false);
		}
		return dao.listByQueryWithPagnation(builder, ServiceOrder.class);
	}

	public EntityResults<ServiceOrder> listMfcServiceOrders(SearchVo search) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		mergeSpJoinQuery(builder);
		builder.join(ServiceOrder.TABLE_NAME, Category.TABLE_NAME, ServiceOrder.CATE_ID, Category.ID);
		builder.joinColumns(Category.TABLE_NAME, new String[] { Category.NAME + ",categoryName" });
		
		builder.and(ServiceOrder.MFC_USER_ID, EcThreadLocal.getCurrentUserId());
		builder.and(ServiceOrder.EXPIRED, false);
		builder = mergeServiceOrderSearchQuery(search, builder);
		return dao.listByQueryWithPagnation(builder, ServiceOrder.class);
	}

	private void mergeSpJoinQuery(DataBaseQueryBuilder builder) {
	    builder.join(ServiceOrder.TABLE_NAME, ServiceProvider.TABLE_NAME, ServiceOrder.SP_ID, ServiceProvider.ID);
		builder.joinColumns( ServiceProvider.TABLE_NAME, new String[]{ServiceProvider.SP_USER_NAME, ServiceProvider.SP_CONTACT_PERSON, ServiceProvider.SP_CONTACT_MOBILE_PHONE});
		builder.limitColumns(new ServiceOrder().getColumnList());
    }
	
	private void mergeMfcJoinQuery(DataBaseQueryBuilder builder) {

		builder.join(ProductOrder.TABLE_NAME, Manufacturer.TABLE_NAME, ProductOrder.MFC_ID, Manufacturer.ID);
		builder.joinColumns(Manufacturer.TABLE_NAME, new String[] { Manufacturer.MFC_STORE_NAME, Manufacturer.MFC_CONTACT_PERSON, Manufacturer.MFC_CONTACT_MOBILE_PHONE });
		builder.limitColumns(new ProductOrder().getColumnList());

    }

	public EntityResults<UserOrderVO> listUserServiceOrders(SearchVo search) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		builder.join(ServiceOrder.TABLE_NAME, ServiceScore.TABLE_NAME, ServiceOrder.ID, ServiceScore.SO_ID);
		builder.on(ServiceOrder.TABLE_NAME, ServiceScore.TABLE_NAME, ServiceOrder.USER_ID, ServiceScore.USER_ID);
		builder.limitColumns(new ServiceOrder().getColumnList());
		builder.joinColumns(ServiceScore.TABLE_NAME, new String[]{ServiceScore.USER_SCORE_TYPE, ServiceScore.USER_SCORE_COMMENT});
		
		
		builder.and(ServiceOrder.USER_ID, EcThreadLocal.getCurrentUserId());
		builder = mergeServiceOrderSearchQuery(search, builder);
		
	
		return dao.listByQueryWithPagnation(builder, UserOrderVO.class);
	}

	public EntityResults<ServiceOrder> listSpNewServiceOrders(SearchVo search) {
		DataBaseQueryBuilder builder = getNewOrderQueryBuilder();
		
		builder = mergeServiceOrderSearchQuery(search, builder);
		return dao.listByQueryWithPagnation(builder, ServiceOrder.class);
	}

	public DataBaseQueryBuilder getNewOrderQueryBuilder() {
	    DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		builder.and(ServiceOrder.SO_STATUS, ProductOrderStatus.NEED_SP_CONFIRM);		
		builder.and(ServiceOrder.SP_USER_ID, EcThreadLocal.getCurrentUserId());
	    return builder;
    }
	
	public Map<String, Object> countSpNewServiceOrders(){
		Map<String, Object> result = new HashMap<String, Object>();		
		result.put("count", this.dao.count(getNewOrderQueryBuilder()));
		return result;
	}
	
	public Map<String, Object> countMfcNewServiceOrders(){
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
		builder.and(ProductOrder.USER_ID, EcThreadLocal.getCurrentUserId());
		builder.and(ProductOrder.PO_STATUS, ProductOrderStatus.INACTIVE);
		
		Map<String, Object> result = new HashMap<String, Object>();		
		result.put("count", this.dao.count(builder));
		return result;
	}


	public EntityResults<ServiceOrder> listMyAcceptedServiceOrders(SearchVo search) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		builder.and(ServiceOrder.SO_STATUS, ProductOrderStatus.ACCEPTED);
		builder.and(ServiceOrder.SP_USER_ID, EcThreadLocal.getCurrentUserId());
		builder = mergeServiceOrderSearchQuery(search, builder, ServiceOrder.ACCEPTED_DATE);
		builder.orderBy(ServiceOrder.ACCEPTED_DATE, false);
		return dao.listByQueryWithPagnation(builder, ServiceOrder.class);
	}

	public EntityResults<ServiceOrder> listMyAssignedServiceOrders(SearchVo search) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		builder.and(ServiceOrder.SO_STATUS, ProductOrderStatus.ASSIGNED);
		builder.and(ServiceOrder.SP_USER_ID, EcThreadLocal.getCurrentUserId());
		builder = mergeServiceOrderSearchQuery(search, builder, ServiceOrder.ASSIGN_DATE);
		builder.orderBy(ServiceOrder.ASSIGN_DATE, false);
		return dao.listByQueryWithPagnation(builder, ServiceOrder.class);
	}



	public void acceptServiceOrders(List<String> ids) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		query.and(ServiceOrder.SO_STATUS, ProductOrderStatus.NEED_SP_CONFIRM);
		checkIdsStatus(query, ids, ServiceOrder.SP_USER_ID);
		
		
		for (String id : ids) {
			ServiceOrder sorder = new ServiceOrder();
			sorder.setSoStatus(ProductOrderStatus.ACCEPTED);
			sorder.setAcceptedDate(new Date());
			sorder.setId(id);
			this.dao.updateById(sorder);

			updateProductOrderStatus(id, ProductOrderStatus.ACCEPTED);
			sms.sendOrderAcceptedNotice(id);
		}
		
		addServiceOrderOpLog(ids, "接受了如下订单【%s】");

	}

	@Override
	public void rejectServiceOrder(ServiceOrder order) {

		checkOwner(order, ServiceOrder.SP_USER_ID, ServiceOrder.SO_STATUS, ProductOrderStatus.NEED_SP_CONFIRM);

		order.setSoStatus(ProductOrderStatus.REJECTED);
		order.setNeedNotice(false);
		order.setRejectDate(new Date());

		this.dao.updateById(order);
		updateProductOrderStatus(order.getId(), ProductOrderStatus.REJECTED);

		addServiceOrderOpLogOne(order.getId(), "拒绝了如下订单【%s】");
	}

	@Transactional
	public void finishServiceOrders(List<String> ids) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		query.and(ServiceOrder.SO_STATUS, ProductOrderStatus.ASSIGNED);
		
		checkIdsStatus(query, ids, ServiceOrder.SP_USER_ID);
		for (String id : ids) {
			ServiceOrder sorder = new ServiceOrder();
			sorder.setId(id);
			sorder.setSoStatus(ProductOrderStatus.DONE);
			sorder.setBillStatus(BillStatus.NEW);
			sorder.setFinishDate(new Date());
			//设置假的结算日期，方便搜索
			this.dao.updateById(sorder);

			updateProductOrderStatus(id, ProductOrderStatus.DONE);

			ServiceOrder so = (ServiceOrder) this.dao.findById(id, ServiceOrder.TABLE_NAME, ServiceOrder.class);
			sms.sendOrderDoneNotice(so.getPoReceiverMobilePhone(), so);
			
		}
		
		addServiceOrderOpLog(ids, "完成了如下订单【%s】");

	}

	@Override
	public EntityResults<ServiceOrderForComplaintVO> listServiceOrdersForComplaint(ComplaintSearchVo svo) {
		EntityResults<ServiceOrderForComplaintVO> result = new EntityResults<ServiceOrderForComplaintVO>();

		String cPhoneNo = svo.getMobilePhone();

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
		/*
		 * DataBaseQueryBuilder builder = new
		 * DataBaseQueryBuilder(ServiceOrder.TABLE_NAME); if(cPhoneNo != null){
		 * builder.and(ServiceOrder.PO_RECEIVER_MOBILE_PHONE, cPhoneNo); }
		 * return dao.listByQueryWithPagnation(builder, ServiceOrder.class);
		 */

		StringBuffer sql = new StringBuffer(
		        "select so.id serviceOrderId,so.poCode,so.poReceiverName,so.poReceiverAddress,so.poReceiverPhone,so.poReceiverMobilePhone,so.poGoodsTitle,so.poLogisticsNo,so.poLogisticsCompany,so.poOrderRemark,so.poGoodsNumber,so.soCode,so.price,so.soStatus,c.id complaintId,c.compStatus complaintStatus,c.compTypeId complaintType,c.compRemark complaintRemark,ct.name complaintTypeName from ServiceOrder so left join complaint c on so.id=c.serviceOrderId left join ComplaintType ct on c.compTypeId=ct.id");
		if (!EcUtil.isEmpty(cPhoneNo)) {
			sql.append(" where so.poReceiverMobilePhone = \"").append(cPhoneNo).append("\"");
		}
		sql.append(" limit ").append(limitStart).append(",").append(limitRows).append(";");
		List<ServiceOrderForComplaintVO> entityList = dao.listBySql(sql.toString(), ServiceOrderForComplaintVO.class);

		result.setEntityList(entityList);

		DataBaseQueryBuilder countBuilder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		if (!EcUtil.isEmpty(cPhoneNo)) {
			countBuilder.and(ServiceOrder.PO_RECEIVER_MOBILE_PHONE, cPhoneNo);
		}
		Pagination pagnation = new Pagination();
		pagnation.setTotal(dao.count(countBuilder));
		result.setPagnation(pagnation);

		return result;
	}

	public void assignServiceOrderWorker(ServiceOrder order) {

		DataBaseQueryBuilder statusQuery = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		statusQuery.or(ServiceOrder.SO_STATUS, ProductOrderStatus.ACCEPTED);
		statusQuery.or(ServiceOrder.SO_STATUS, ProductOrderStatus.ASSIGNED);

		checkOwner(order, ServiceOrder.SP_USER_ID,statusQuery);
		
		if (order.getEstInstallDate() == null) {
			throw new ResponseException("预约安装时间不能为空");
		}


		ServiceOrder old = (ServiceOrder) this.dao.findById(order.getId(), ServiceOrder.TABLE_NAME, ServiceOrder.class);

		
		int oldDay = 0;
		Calendar c = Calendar.getInstance();

		if (old.getEstInstallDate() != null) {
			c.setTime(old.getEstInstallDate());
			oldDay = c.get(Calendar.DAY_OF_YEAR);
		}

		c.setTime(order.getEstInstallDate());
		int newDay = c.get(Calendar.DAY_OF_YEAR);
		order.setAssignDate(new Date());
		order.setSoStatus(ProductOrderStatus.ASSIGNED);
		this.dao.updateById(order);
		updateProductOrderStatus(order.getId(), ProductOrderStatus.ASSIGNED);
		
		if (oldDay != newDay) {
			sms.sendOrderAssignNotice(order);
		}
		sms.addOrderInstallNotice(order);
	}

	public void terminateServiceOrder(ServiceOrder order) {
		DataBaseQueryBuilder statusQuery = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		statusQuery.or(ServiceOrder.SO_STATUS, ProductOrderStatus.ACCEPTED);
		statusQuery.or(ServiceOrder.SO_STATUS, ProductOrderStatus.ASSIGNED);

		checkOwner(order, ServiceOrder.SP_USER_ID,statusQuery);

		order.setSoStatus(ProductOrderStatus.TERMINATED);
		order.setNeedNotice(false);
		order.setTerminateDate(new Date());
		if(order.getOperatorId() == null){
			User user = us.getManualServiceOrderOperator(order);
			if(user!=null){
				order.setOperatorId(user.getId());
			}
		}
		
		updateProductOrderStatus(order.getId(), ProductOrderStatus.TERMINATED);

		this.dao.updateById(order);
		addServiceOrderOpLogOne(order.getId(), "无法完工如下订单【%s】");

	}

	public EntityResults<ServiceOrder> listNoticeOrders(SearchVo search) {
		DataBaseQueryBuilder builder = getOrderNoticeQueryBuilder(search);
		return dao.listByQueryWithPagnation(builder, ServiceOrder.class);
	}

	public DataBaseQueryBuilder getOrderNoticeQueryBuilder(SearchVo search) {
	    DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		mergeSpJoinQuery(builder);

		Calendar cal = Calendar.getInstance();
		if (search.getOrderNoticType() == 0) {

			// 48 - 24小时之间
			cal.add(Calendar.DAY_OF_MONTH, -1);
			Date queryDate = cal.getTime();
			builder.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, ServiceOrder.EST_INSTALL_DATE, queryDate);

			cal.add(Calendar.DAY_OF_MONTH, -1);
			queryDate = cal.getTime();
			builder.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, ServiceOrder.EST_INSTALL_DATE, queryDate);

		} else {

			// 48
			cal.add(Calendar.DAY_OF_MONTH, -2);
			Date queryDate = cal.getTime();
			builder.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, ServiceOrder.EST_INSTALL_DATE, queryDate);
		}
		builder.and(ServiceOrder.SO_STATUS, ProductOrderStatus.ASSIGNED);
		builder.and(DataBaseQueryOpertion.IS_TRUE, ServiceOrder.NEED_NOTICE);
		
		if(EcThreadLocal.getCurrentUserId()!=null){
			builder.and(ServiceOrder.OPERATOR_ID, EcThreadLocal.getCurrentUserId());
		}
	    return builder;
    }

	public void cancelServiceOrderNotices(List<String> ids) {

		for (String id : ids) {
			ServiceOrder sorder = new ServiceOrder();
			sorder.setId(id);
			sorder.setNeedNotice(false);
			this.dao.updateById(sorder);
		}
	}

	@Transactional
	public void splitProductOrders(List<ServiceOrder> orderList) {
		int i = 1;
		ProductOrder porder = null;
		List<String> logs = new ArrayList<String>();
		
		for (ServiceOrder order : orderList) {
			String poCode = order.getPoCode();

			if (porder == null) {
				porder = (ProductOrder) this.dao.findByKeyValue(ProductOrder.PO_CODE, poCode, ProductOrder.TABLE_NAME, ProductOrder.class);
				checkOwner(porder, null, ProductOrder.PO_STATUS, ProductOrderStatus.MANUAL);
			}

			ServiceOrder sor = (ServiceOrder) EcUtil.toEntity(porder.toMap(), ServiceOrder.class);

			sor.setPoCode(porder.getPoCode());
			sor.setPoId(porder.getId());
			sor.setSoCode("FW" + porder.getPoCode() + "-" + i);
			i++;
			sor.setSoStatus(ProductOrderStatus.NEED_SP_CONFIRM);
			sor.setNeedNotice(true);
			sor.setIsNoticed(false);
			sor.setExpired(false);
			sor.setSmsInstallNoticed(false);
			sor.setIsGoodsArrived(false);
			sor.setSpNeedBill(true);
			sor.setMfcNeedBill(true);

			sor.setPoMemberName(order.getPoMemberName());
			sor.setPoGoodsNumber(order.getPoGoodsNumber());
			sor.setPoReceiverMobilePhone(order.getPoReceiverMobilePhone());
			sor.setPlusPrice(order.getPlusPrice());
			sor.setSplitRemark(order.getSplitRemark());
			sor.setPoReceiverName(order.getPoReceiverName());
			sor.setMfcId(porder.getMfcId());
			sor.setCateId(order.getCateId());
			sor.setMfcUserId(porder.getUserId());
			
			

			if (EcUtil.isEmpty(order.getSpId())) {
				throw new ResponseException("请为拆单订单选择服务商");

			}else{
				// 设置基本信息
				
				updateOrderLocation(porder, sor);
				updateOrderCategory(porder, sor);
				// 如果是管理员选择的服务商
				sor.setSpId(order.getSpId());
				ServiceProvider sp = (ServiceProvider) this.dao.findById(order.getSpId(), ServiceProvider.TABLE_NAME, ServiceProvider.class);
				sor.setSpUserId(sp.getUserId());
			}


			generateNewUserByOrder(sor);
			sor.setId(null);
			
			
			if (sor.getPlusPrice() != null && sor.getPlusPrice() < 0) {
				String log = String.format("产品订单【%s】被拆为服务订单【%s】，减价【%s】备注【%s】", porder.getPoCode(), sor.getSoCode(), String.valueOf(sor.getPlusPrice()).replace("-", ""), sor.getSplitRemark());
				logs.add(log);

			} else {
				String log = String.format("产品订单【%s】被拆为服务订单【%s】，加价【%s】备注【%s】", porder.getPoCode(), sor.getSoCode(), sor.getPlusPrice(), sor.getSplitRemark());
				logs.add(log);
			}
			this.dao.insert(sor);
	
			
			if (sor.getSplitRemark() != null) {
				updateServiceOrderComments(sor.getId(), EcThreadLocal.getCurrentUserId(), sor.getSplitRemark(), "拆单");
			}

		}

		if (porder != null) {

			porder.setPoStatus(ProductOrderStatus.NEED_SP_CONFIRM);
			porder.setSplitOrderDate(new Date());
			this.dao.updateById(porder);
		}
		for(String log: logs){
			logger.info(log);
		}
	}

	@Transactional
	public void changeServiceOrderSp(ServiceOrder order) {

		ServiceOrder old = (ServiceOrder) this.dao.findById(order.getId(), ServiceOrder.TABLE_NAME, ServiceOrder.class);

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		query.or(ServiceOrder.SO_STATUS, ProductOrderStatus.REJECTED.toString());
		query.or(ServiceOrder.SO_STATUS, ProductOrderStatus.TERMINATED.toString());

		checkOwner(old, null, query);
		old.setId(order.getId());
		old.setExpired(true);
		old.setSpChangeDate(new Date());
		this.dao.updateById(old);

		ServiceOrder newOrder = (ServiceOrder) EcUtil.toEntity(old.toString(), ServiceOrder.class);
		if (order.getPlusPrice() == null) {
			order.setPlusPrice(0);
		}
		newOrder.setFinishDate(null);
		newOrder.setCloseDate(null);
		newOrder.setCreatedOn(new Date());
		newOrder.setEstInstallDate(null);
		newOrder.setEstInstallDateRegion(null);
		newOrder.setEstInstallDateTime(null);
		newOrder.setNeedNotice(true);
		newOrder.setIsNoticed(false);
		newOrder.setExpired(false);
		newOrder.setPlusPrice(order.getPlusPrice());
		newOrder.setWorkerId(null);
		newOrder.setUpdatedOn(new Date());
		newOrder.setTerminateReason(null);
		newOrder.setTerminateRemark(null);
		newOrder.setSoStatus(ProductOrderStatus.NEED_SP_CONFIRM);
		newOrder.setAcceptedDate(null);
		newOrder.setAssignDate(null);
		newOrder.setFinishDate(null);
		newOrder.setRejectDate(null);
		newOrder.setRejectReason(null);
		newOrder.setRejectRemark(null);
		newOrder.setTerminateDate(null);
		
		
		ServiceProvider oldSp = (ServiceProvider) this.dao.findById(old.getSpId(), ServiceProvider.TABLE_NAME, ServiceProvider.class);

		if(EcUtil.isEmpty(order.getSpId())){
			throw new ResponseException("请选择服务商");
		}
		ServiceProvider newSp = (ServiceProvider) this.dao.findById(order.getSpId(), ServiceProvider.TABLE_NAME, ServiceProvider.class);
		newOrder.setSpUserId(newSp.getUserId());
		newOrder.setSpId(order.getSpId());
		
		newOrder.setId(null);
		

		if (newOrder.getPlusPrice() != null && newOrder.getPlusPrice() < 0) {
			String log = String.format("改变订单【%s】服务商，从【%s】到【%s】，并且减价【%s】元", old.getSoCode(), oldSp.getSpUserName(), newSp.getSpUserName(), String.valueOf(newOrder.getPlusPrice()).replace("-", ""));
			logger.info(log);

		} else {
			String log = String.format("改变订单【%s】服务商，从【%s】到【%s】，并且加价【%s】元", old.getSoCode(), oldSp.getSpUserName(), newSp.getSpUserName(), newOrder.getPlusPrice());
			logger.info(log);
		}
		
		
		this.dao.insert(newOrder);

		if (newOrder.getSplitRemark() != null) {
			String info = String.format("更换服务商，原服务商： %s，新服务商： %s", oldSp.getSpUserName(), newSp.getSpUserName());
			updateServiceOrderComments(newOrder.getId(), EcThreadLocal.getCurrentUserId(), newOrder.getSplitRemark(), info);
		}
		
	}

	public Map<String, Object> getMfcOrderStats(SearchVo svo) {
		ProductOrderStatus status[] = ProductOrderStatus.values();
		Map<String, Object> result = new HashMap<String, Object>();

		for (ProductOrderStatus statu : status) {
			DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
			builder.and(ProductOrder.PO_STATUS, statu.toString());
			builder.and(ProductOrder.USER_ID, EcThreadLocal.getCurrentUserId());

			mergeCommonSearchQuery(svo, builder, ProductOrder.TABLE_NAME, ProductOrder.CREATED_ON, true);

			result.put(statu.toString(), this.dao.count(builder));

		}

		return result;

	}
	
	public Map<String, Object> getOrderStats(SearchVo svo) {

		Map<String, Object> result = new HashMap<String, Object>();

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
		builder.and(ProductOrder.PO_STATUS, ProductOrderStatus.INACTIVE);
		mergeCommonSearchQuery(svo, builder, ProductOrder.TABLE_NAME, ProductOrder.CREATED_ON, true);
		result.put("INACTIVE", this.dao.count(builder));
		
		builder = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
		builder.and(ProductOrder.PO_STATUS, ProductOrderStatus.MANUAL);
		mergeCommonSearchQuery(svo, builder, ProductOrder.TABLE_NAME, ProductOrder.CREATED_ON, true);
		result.put("SYS_MANUAL", this.dao.count(builder));
		
		
		
		builder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		builder.and(DataBaseQueryOpertion.IN, ServiceOrder.SO_STATUS, new String[] { ProductOrderStatus.ACCEPTED.toString(), ProductOrderStatus.ASSIGNED.toString(),
		        ProductOrderStatus.NEED_SP_CONFIRM.toString() });
		mergeCommonSearchQuery(svo, builder, ServiceOrder.TABLE_NAME, ServiceOrder.CREATED_ON, true);
		result.put("SP_COUNT", this.dao.count(builder));


		builder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		builder.and(DataBaseQueryOpertion.IN, ServiceOrder.SO_STATUS, new String[] { ProductOrderStatus.DONE.toString()});
		mergeCommonSearchQuery(svo, builder, ServiceOrder.TABLE_NAME, ServiceOrder.CREATED_ON, true);
		result.put("DONE", this.dao.count(builder));
		
		builder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		builder.and(DataBaseQueryOpertion.NOT_IN, ServiceOrder.BILL_STATUS, new String[] { BillStatus.MFC_DONE.toString()});
		mergeCommonSearchQuery(svo, builder, ServiceOrder.TABLE_NAME, ServiceOrder.CREATED_ON, true);
		result.put("CLOSED", this.dao.count(builder));
		
		builder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		builder.and(DataBaseQueryOpertion.IN, ServiceOrder.SO_STATUS, new String[] { ProductOrderStatus.REJECTED.toString(), ProductOrderStatus.TERMINATED.toString()});
		mergeCommonSearchQuery(svo, builder, ServiceOrder.TABLE_NAME, ServiceOrder.CREATED_ON, true);
		result.put("USER_MANUAL", this.dao.count(builder));
		
		
		builder = new DataBaseQueryBuilder(Complaint.TABLE_NAME);
		builder.and(DataBaseQueryOpertion.IN, Complaint.COMP_STATUS, new String[] { ComplaintStaus.NEW.toString(), ComplaintStaus.PROCESSING.toString(),  ComplaintStaus.DONE.toString()});
		mergeCommonSearchQuery(svo, builder, Complaint.TABLE_NAME, Complaint.CREATED_ON, true);
		result.put("COMPLAINT", this.dao.count(builder));
		


		return result;

	}
	

	private DataBaseQueryBuilder mergeProductOrderSearchQuery(SearchVo search, DataBaseQueryBuilder builder, String dateField) {
		
		if(EcUtil.isEmpty(dateField)){
			dateField = ProductOrder.CREATED_ON;
		}
		if (!EcUtil.isEmpty(search.getOrderStatus())) {
			if (search.getOrderStatus().equalsIgnoreCase(ProductOrderStatus.MANUAL.toString())) {
				DataBaseQueryBuilder statusQuery = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
				statusQuery.or(ProductOrder.PO_STATUS, ProductOrderStatus.MANUAL);
				statusQuery.or(ProductOrder.PO_STATUS, ProductOrderStatus.REJECTED);
				statusQuery.or(ProductOrder.PO_STATUS, ProductOrderStatus.TERMINATED);
				builder.and(statusQuery);
			} else {
				builder.and(DataBaseQueryOpertion.EQUAILS, ProductOrder.PO_STATUS, search.getOrderStatus());
			}
		}

		if (!EcUtil.isEmpty(search.getStartDate())) {
			builder.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, dateField, search.getStartDate());
		}

		if (!EcUtil.isEmpty(search.getEndDate())) {
			Date endDate = getQueryEndDate(search);
			builder.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, dateField, endDate);
		}
		
		if(EcUtil.isValid(search.getMfcId())){
			builder.and(ProductOrder.MFC_ID, search.getMfcId());
		}

		DataBaseQueryBuilder keywordBuilder = new DataBaseQueryBuilder(ProductOrder.TABLE_NAME);
		if (!EcUtil.isEmpty(search.getKeyword())) {
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ProductOrder.PO_RECEIVER_MOBILE_PHONE, search.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ProductOrder.PO_RECEIVER_ADDRESS, search.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ProductOrder.PO_MEMBER_NAME, search.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ProductOrder.PO_RECEIVER_NAME, search.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ProductOrder.PO_GOODS_TITLE, search.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ProductOrder.PO_RECEIVER_PHONE, search.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ProductOrder.PO_CODE, search.getKeyword());
		}

		if (!EcUtil.isEmpty(keywordBuilder.getQueryStr())) {
			builder = builder.and(keywordBuilder);
		}
		return builder;
	}

	private DataBaseQueryBuilder mergeServiceOrderSearchQuery(SearchVo search, DataBaseQueryBuilder builder) {

		return mergeServiceOrderSearchQuery(search, builder, null);

	}

	private DataBaseQueryBuilder mergeServiceOrderSearchQuery(SearchVo search, DataBaseQueryBuilder builder, String dField) {

		String dateField = ServiceOrder.CREATED_ON;
		if(EcUtil.isValid(dField)){
			dateField = dField;
		}
		if (!EcUtil.isEmpty(search.getIsGoodsArrived())) {
			if (search.getIsGoodsArrived().equalsIgnoreCase("true")) {
				builder.and(DataBaseQueryOpertion.IS_TRUE, ServiceOrder.IS_GOODS_ARRIVED);
			} else {
				builder.and(DataBaseQueryOpertion.IS_FALSE, ServiceOrder.IS_GOODS_ARRIVED);
			}
		}
		
		if (!EcUtil.isEmpty(search.getOrderStatus())) {
			if (search.getOrderStatus().equalsIgnoreCase("CLOSED")) {
				builder.and(DataBaseQueryOpertion.IN, ServiceOrder.BILL_STATUS, new String[] { BillStatus.SP_DONE.toString(), BillStatus.MFC_DONE.toString() });
			} else if (search.getOrderStatus().equalsIgnoreCase("MANUAL")) {
				builder.and(DataBaseQueryOpertion.IN, ServiceOrder.SO_STATUS, new String[] { ProductOrderStatus.REJECTED.toString(), ProductOrderStatus.TERMINATED.toString() });
			} else {
				builder.and(DataBaseQueryOpertion.EQUAILS, ServiceOrder.SO_STATUS, search.getOrderStatus());
			}
		}

		if (!EcUtil.isEmpty(search.getStartDate())) {
			builder.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, dateField, search.getStartDate());
		}

		if (!EcUtil.isEmpty(search.getEndDate())) {
			Date endDate = getQueryEndDate(search);
			builder.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, dateField, endDate);
		}
		
		if (EcUtil.isValid(search.getSpId())) {
			builder.and(ServiceOrder.SP_ID, search.getSpId());

		}

		DataBaseQueryBuilder keywordBuilder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		if (!EcUtil.isEmpty(search.getKeyword())) {
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ServiceOrder.PO_RECEIVER_MOBILE_PHONE, search.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ServiceOrder.PO_RECEIVER_ADDRESS, search.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ServiceOrder.PO_MEMBER_NAME, search.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ServiceOrder.PO_RECEIVER_NAME, search.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ServiceOrder.PO_GOODS_TITLE, search.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ServiceOrder.PO_RECEIVER_PHONE, search.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ServiceOrder.SO_CODE, search.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ServiceOrder.PO_CODE, search.getKeyword());

		}
		
		buildCateSearch(search, keywordBuilder, ServiceOrder.CATE_ID);

		if (!EcUtil.isEmpty(keywordBuilder.getQueryStr())) {
			builder = builder.and(keywordBuilder);
		}
		return builder;
	}

	private void updateProductOrderStatus(String sid, ProductOrderStatus status) {

		ServiceOrder sorder = (ServiceOrder) this.dao.findById(sid, ServiceOrder.TABLE_NAME, ServiceOrder.class);
		ProductOrder porder = (ProductOrder) this.dao.findById(sorder.getPoId(), ProductOrder.TABLE_NAME, ProductOrder.class);

		String statusOrder[] = new String[] { ProductOrderStatus.NEED_SP_CONFIRM.toString(), ProductOrderStatus.ACCEPTED.toString(), ProductOrderStatus.ASSIGNED.toString(),
		        ProductOrderStatus.REJECTED.toString(), ProductOrderStatus.TERMINATED.toString(), ProductOrderStatus.DONE.toString(), ProductOrderStatus.CANCELLED.toString() };

		for (String statusStr : statusOrder) {

			DataBaseQueryBuilder statusQuery = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
			statusQuery.and(ServiceOrder.PO_ID, porder.getId());
			statusQuery.and(ServiceOrder.SO_STATUS, statusStr);

			if (this.dao.exists(statusQuery)) {
				porder.setPoStatus(ProductOrderStatus.valueOf(statusStr));
				if (statusStr.equals(ProductOrderStatus.DONE.toString())) {
					porder.setFinishDate(sorder.getFinishDate());

				}
				this.dao.updateById(porder);
				break;
			}
		}

	}

	public EntityResults<ServiceOrder> listMfcOrderBillForAdmin(SearchVo svo) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		mergeSpJoinQuery(builder);
		builder.and(ServiceOrder.BILL_STATUS, BillStatus.SP_DONE);
		builder.and(DataBaseQueryOpertion.IS_TRUE, ServiceOrder.MFC_NEED_BILL);
		builder = mergeCommonSearchQuery(svo, builder, ServiceOrder.TABLE_NAME, ServiceOrder.CLOSE_DATE, false);
		return this.dao.listByQueryWithPagnation(builder, ServiceOrder.class);
	}
	
	public EntityResults<Bill> listMfcOrderBillHistory(SearchVo search) {
		
		DataBaseQueryBuilder builder = getMfcOrderBillHistorySearch(search);

		return dao.listByQueryWithPagnation(builder, Bill.class);
	}

	public DataBaseQueryBuilder getMfcOrderBillHistorySearch(SearchVo search) {
	    DataBaseQueryBuilder builder = getMfcOrderBillQuery(search);
		
		builder = mergeCommonSearchQuery(search, builder, Bill.TABLE_NAME, Bill.BILL_DATE, true);
	    return builder;
    }

	public DataBaseQueryBuilder getMfcOrderBillQuery(SearchVo search) {
	    DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Bill.TABLE_NAME);
		builder.join(Bill.TABLE_NAME, ServiceProvider.TABLE_NAME, Bill.SP_ID, ServiceProvider.ID);
		builder.joinColumns(ServiceProvider.TABLE_NAME, new String[] { ServiceProvider.SP_USER_NAME, ServiceProvider.SP_CONTACT_PERSON, ServiceProvider.SP_CONTACT_MOBILE_PHONE });
		builder.limitColumns(new Bill().getColumnList());
		builder.and(Bill.MFC_USER_ID, EcThreadLocal.getCurrentUserId());


		builder = mergeCommonSearchQuery(search, builder, Bill.TABLE_NAME, Bill.BILL_DATE, false);
	    return builder;
    }
	
	public Map<String, Object> exportMfcOrderBill(HttpServletRequest request, SearchVo svo){
		DataBaseQueryBuilder builder = getMfcOrderBillQuery(svo);

		List<Bill> billList = this.dao.listByQuery(builder, Bill.class);
		
		List<String> ids = new ArrayList<String>();
		for(Bill bill: billList){			
			ids.add(bill.getId());
		}
		DataBaseQueryBuilder location = new  DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);		
		mergeLocationAndCateQuery(svo, location, ServiceOrder.CATE_ID, ServiceOrder.LOCATION_ID);
		
		return exportOrders(request, location, ids, ServiceOrder.MFC_BILL_ID);
		
	}



	private Map<String, Object> exportOrders(HttpServletRequest request, DataBaseQueryBuilder query, List<String> ids, String key) {
	    DataBaseQueryBuilder orderQuery = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		
//	    builder.join(ServiceOrder.TABLE_NAME, Bill.TABLE_NAME, ServiceOrder.MFC_BILL_ID, Bill.ID);
//		builder.joinColumns( ServiceOrder.TABLE_NAME, new String[]{Bill.BILL_DATE});
//		builder.limitColumns(new ServiceOrder().getColumnList());
		
		orderQuery.and(DataBaseQueryOpertion.IN, key, ids);
		orderQuery.and(query);
		
		
		List<ServiceOrder> orderList = this.dao.listByQuery(orderQuery, ServiceOrder.class);
		
		String webPath = request.getSession().getServletContext().getRealPath("/");
		webPath = webPath + "/upload/atta/";
		String colunmHeaders[] = new String[] { ServiceOrder.SO_CODE, ServiceOrder.PO_CODE, ServiceOrder.PO_GOODS_TITLE, ServiceOrder.PO_GOODS_NUMBER,
		        ServiceOrder.PO_RECEIVER_ADDRESS, ServiceOrder.PRICE, ServiceOrder.PLUS_PRICE, ServiceOrder.CLOSE_DATE, };
		String colunmTitleHeaders[] = new String[] { "服务订单编号", "产品订单编号", "宝贝标题", "宝贝总数量", "收货地址", "安装价格", "额外加价", "结算日期" };

		String file = ExcleUtil.createEqcostExcel(webPath, colunmTitleHeaders, colunmHeaders, orderList);
		File efile = new File(file);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("path", "/upload/atta/" + efile.getName());
		result.put("rows", orderList);
		return result;
    }

	public List<ServiceOrder> listSpOrderBillForAdmin(SearchVo svo) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		mergeSpJoinQuery(builder);
		builder.and(ServiceOrder.BILL_STATUS, BillStatus.NEW);
		builder.and(DataBaseQueryOpertion.NOT_NULL, ServiceOrder.COMMENT_DATE);
		builder.and(DataBaseQueryOpertion.IS_TRUE, ServiceOrder.SP_NEED_BILL);

		
		builder = mergeCommonSearchQuery(svo, builder, ServiceOrder.TABLE_NAME, ServiceOrder.CLOSE_DATE, false);
		return this.dao.listByQuery(builder, ServiceOrder.class);
	}
	public EntityResults<ServiceOrder> listSpOrderRecycleBillForAdmin(SearchVo svo){
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		mergeSpJoinQuery(builder);
		builder.and(ServiceOrder.SO_STATUS, ProductOrderStatus.DONE);
		builder.and(DataBaseQueryOpertion.IS_FALSE, ServiceOrder.SP_NEED_BILL);
		
		builder = mergeCommonSearchQuery(svo, builder, ServiceOrder.TABLE_NAME, ServiceOrder.CLOSE_DATE, false);
		return this.dao.listByQueryWithPagnation(builder, ServiceOrder.class);
		
	}
	
	public EntityResults<ServiceOrder> listMfcOrderRecycleBillForAdmin(SearchVo svo){
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		mergeSpJoinQuery(builder);
		builder.and(ServiceOrder.BILL_STATUS, BillStatus.SP_DONE);
		builder.and(DataBaseQueryOpertion.IS_FALSE, ServiceOrder.MFC_NEED_BILL);
		
		builder = mergeCommonSearchQuery(svo, builder, ServiceOrder.TABLE_NAME, ServiceOrder.CLOSE_DATE, false);
		return this.dao.listByQueryWithPagnation(builder, ServiceOrder.class);
		
	}
	
	public EntityResults<Bill> listSpOrderBillHistory(SearchVo search) {
		DataBaseQueryBuilder builder = getSpOrderBillHistorySearch(search);

		return dao.listByQueryWithPagnation(builder, Bill.class);
	}
	
	public int countSpBillForAdmin(SearchVo search){
		int total = 0;
		DataBaseQueryBuilder builder = getSpAdminBillSearch(search);

		builder.limitColumns(new String[]{Bill.TOTAL});
		List<Bill> bills = this.dao.listByQuery(builder, Bill.class);
		for(Bill bill: bills){
			total = total + bill.getTotal();
		}
		return total;

	}
	

	public int countMfcBillForAdmin(SearchVo search){
		int total = 0;
		DataBaseQueryBuilder builder = getMfcBillSearchForADMIN(search);
		
		builder.limitColumns(new String[]{Bill.TOTAL});
		List<Bill> bills = this.dao.listByQuery(builder, Bill.class);
		for(Bill bill: bills){
			total = total + bill.getTotal();
		}
		return total;
	}
	
	
	public int countSpBillForSpSelf(SearchVo search){
		int total = 0;
		DataBaseQueryBuilder builder = getSpOrderBillHistorySearch(search);
		
		builder.limitColumns(new String[]{Bill.TOTAL});
		List<Bill> bills = this.dao.listByQuery(builder, Bill.class);
		for(Bill bill: bills){
			total = total + bill.getTotal();
		}
		return total;
	}
	
	public int countMfcBillForMfcSelf(SearchVo search){
		int total = 0;
		DataBaseQueryBuilder builder = getMfcOrderBillHistorySearch(search);
		
		builder.limitColumns(new String[]{Bill.TOTAL});
		List<Bill> bills = this.dao.listByQuery(builder, Bill.class);
		for(Bill bill: bills){
			total = total + bill.getTotal();
		}
		return total;
	}

	public DataBaseQueryBuilder getSpOrderBillHistorySearch(SearchVo search) {
	    DataBaseQueryBuilder builder = getSpOrderBillQuery(search);
		builder = mergeCommonSearchQuery(search, builder, Bill.TABLE_NAME, Bill.BILL_DATE, true);
	    return builder;
    }

	public DataBaseQueryBuilder getSpOrderBillQuery(SearchVo search) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Bill.TABLE_NAME);
		builder.join(Bill.TABLE_NAME, ServiceProvider.TABLE_NAME, Bill.SP_ID, ServiceProvider.ID);
		builder.joinColumns(ServiceProvider.TABLE_NAME, new String[] { ServiceProvider.SP_USER_NAME, ServiceProvider.SP_CONTACT_PERSON, ServiceProvider.SP_CONTACT_MOBILE_PHONE });
		builder.limitColumns(new Bill().getColumnList());
		
		builder.and(Bill.SP_USER_ID, EcThreadLocal.getCurrentUserId());
		builder = mergeCommonSearchQuery(search, builder, Bill.TABLE_NAME, Bill.BILL_DATE, false);
		return builder;
	}

	public Map<String, Object> exportSpOrderBill(HttpServletRequest request, SearchVo svo) {

		DataBaseQueryBuilder builder = getSpOrderBillQuery(svo);
		List<Bill> billList = this.dao.listByQuery(builder, Bill.class);
		
		List<String> ids = new ArrayList<String>();
		for(Bill bill: billList){			
			ids.add(bill.getId());
		}
		DataBaseQueryBuilder location = new  DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);		
		mergeLocationAndCateQuery(svo, location, ServiceOrder.CATE_ID, ServiceOrder.LOCATION_ID);
		return exportOrders(request, location, ids, ServiceOrder.SP_BILL_ID);
		
	}

	public void confirmSpOrderBills(Bill bill, List<String> ids) {
		Date billDate = new Date();

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		query.and(DataBaseQueryOpertion.IN, ServiceOrder.ID, ids);
		List<ServiceOrder> list = this.dao.listByQuery(query, ServiceOrder.class);

		Map<String, Object> spMap = new HashMap<String, Object>();
		Map<String, Object> orderMap = new HashMap<String, Object>();
		Map<String, Integer> moneyMap = new HashMap<String, Integer>();

		for (ServiceOrder order : list) {

			if (order.getSpUserId() != null) {
				spMap.put(order.getSpId(), order.getSpUserId());
			}

			int price = 0;
			if (order.getPrice() != null) {
				price = order.getPrice() * order.getPoGoodsNumber();
			}

			if (order.getPlusPrice() != null) {
				price = order.getPlusPrice() + price;
			}

			orderMap.put(order.getId(), order.getSpId());

			if (moneyMap.get(order.getSpId()) != null) {
				moneyMap.put(order.getSpId(), price + moneyMap.get(order.getSpId()));
			} else {
				moneyMap.put(order.getSpId(), price);

			}

		}

		for (String spId : spMap.keySet()) {
			bill.setId(null);
			bill.setSpId(spId);
			bill.setSpUserId(spMap.get(spId).toString());
			bill.setBillDate(billDate);
			bill.setBillDateNumber(billDate.getTime());
			bill.setTotal(moneyMap.get(spId));
			this.dao.insert(bill);
		}

		for (String id : ids) {

			ServiceOrder order = new ServiceOrder();
			order.setId(id);
			order.setBillStatus(BillStatus.SP_DONE);
			order.setCloseDate(new Date());

			DataBaseQueryBuilder billQuery = new DataBaseQueryBuilder(Bill.TABLE_NAME);
			billQuery.and(Bill.BILL_DATE_NUMBER, billDate.getTime());
			billQuery.and(Bill.SP_ID, orderMap.get(id));

			Bill old = (Bill) this.dao.findOneByQuery(billQuery, Bill.class);

			if (old != null) {
				order.setSpBillId(old.getId());
			}

			this.dao.updateById(order);
		}
	}
	
	public void confirmMfcOrderBills(Bill bill, List<String> ids) {
		Date billDate = new Date();

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		query.and(DataBaseQueryOpertion.IN, ServiceOrder.ID, ids);
		List<ServiceOrder> list = this.dao.listByQuery(query, ServiceOrder.class);

		Map<String, Object> mfcMap = new HashMap<String, Object>();
		Map<String, Object> orderMfcMap = new HashMap<String, Object>();
		Map<String, Integer> moneyMap = new HashMap<String, Integer>();

		for (ServiceOrder order : list) {

			if (order.getSpUserId() != null) {
				mfcMap.put(order.getMfcId(), order.getMfcUserId());
			}

			int price = 0;
			if (order.getPrice() != null) {
				price = order.getPrice();
			}

			if (order.getPlusPrice() != null) {
				price = order.getPlusPrice() + price;
			}

			orderMfcMap.put(order.getId(), order.getMfcId());

			if (moneyMap.get(order.getMfcId()) != null) {
				moneyMap.put(order.getMfcId(), price + moneyMap.get(order.getMfcId()));
			} else {
				moneyMap.put(order.getMfcId(), price);

			}

		}

		for (String mfcId : mfcMap.keySet()) {
			bill.setId(null);
			bill.setMfcId(mfcId);
			bill.setMfcUserId(mfcMap.get(mfcId).toString());
			bill.setBillDate(billDate);
			bill.setBillDateNumber(billDate.getTime());
			bill.setTotal(moneyMap.get(mfcId));
			this.dao.insert(bill);
		}

		for (String id : ids) {

			ServiceOrder order = new ServiceOrder();
			order.setId(id);
			order.setBillStatus(BillStatus.MFC_DONE);
			order.setCloseDate(new Date());

			DataBaseQueryBuilder billQuery = new DataBaseQueryBuilder(Bill.TABLE_NAME);
			billQuery.and(Bill.BILL_DATE_NUMBER, billDate.getTime());
			billQuery.and(Bill.MFC_ID, orderMfcMap.get(id));

			Bill old = (Bill) this.dao.findOneByQuery(billQuery, Bill.class);

			if (old != null) {
				order.setMfcBillId(old.getId());
			}

			this.dao.updateById(order);

		}
	}	
	
	public void confirmUserOrder(ServiceOrder order) {
		order.setUserConfirmDate(new Date());
		Calendar c = Calendar.getInstance();
		int hours = EcUtil.getInteger(CFGManager.getProperty(SystemConfig.ORDER_TO_SP_AFTER_CONFIRMED_HOURS), 0);
		c.add(Calendar.HOUR_OF_DAY, hours);
		order.setExpiredDate(c.getTime());
		order.setIsGoodsArrived(true);
		this.dao.updateById(order);
		addServiceOrderOpLogOne(order.getId(), "确认了如下订单【%s】已到货");

//		sms.sendUserConfirmOrderNotice(order);
	}
	
	
	public void addServiceOrderScore(ServiceScore score) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ServiceScore.TABLE_NAME);
		String currentUserId = EcThreadLocal.getCurrentUserId();

		if (score.getUserId() != null) {
			currentUserId = score.getUserId();
		}
		builder.and(ServiceScore.USER_ID, currentUserId);
		builder.and(ServiceScore.SO_ID, score.getSoId());
		builder.and(ServiceScore.SP_ID, score.getSpId());

		ServiceScore old = (ServiceScore) this.dao.findOneByQuery(builder, ServiceScore.class);

		if (score.getUserScoreType() == null) {
			score.setUserScoreType(ServiceScore.USER_SCORE_GOOD);
		}

		if (EcUtil.isEmpty(score.getUserId())) {
			score.setUserId(currentUserId);
		}

		if (old != null) {
			score.setId(old.getId());
			this.dao.updateById(score);
		} else {
			this.dao.insert(score);
		}

		ServiceOrder order = new ServiceOrder();
		order.setId(score.getSoId());
		order.setCommentDate(new Date());
		order.setCloseDate(new Date());
		
		this.dao.updateById(order);

		sps.addSpScore(score);
	}
	
	public EntityResults<ServiceOrder> listServiceOrdersByProdcutOrder(ProductOrder po) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		mergeSpJoinQuery(query);
		query.and(ServiceOrder.PO_ID, po.getId());
		query.and(DataBaseQueryOpertion.IS_FALSE, ServiceOrder.EXPIRED);

		return this.dao.listByQueryWithPagnation(query, ServiceOrder.class);
	}
	
	public ProductOrder getProductOrderInfo(ProductOrder po) {

		ProductOrder order = (ProductOrder) this.dao.findByKeyValue(ProductOrder.PO_CODE, po.getId(), ProductOrder.TABLE_NAME, ProductOrder.class);
		if (order == null) {
			order = (ProductOrder) this.dao.findById(po.getId(), ProductOrder.TABLE_NAME, ProductOrder.class);
		}

		Manufacturer mfc = (Manufacturer) this.dao.findByKeyValue(Manufacturer.ID, order.getMfcId(), Manufacturer.TABLE_NAME, Manufacturer.class);

		if (mfc != null) {
			order.setMfcStoreName(mfc.getMfcStoreName());
		}
		
		if (order.getOperatorId() != null) {
			User kf = (User) this.dao.findByKeyValue(User.ID, order.getOperatorId(), User.TABLE_NAME, User.class);

			if (kf != null) {
				order.setKfName(kf.getUserName());
			}

		}

		return order;
	}
	
	public ServiceOrder getServiceOrderInfo(ServiceOrder so) {
		ServiceOrder order = (ServiceOrder) this.dao.findByKeyValue(ServiceOrder.SO_CODE, so.getId(), ServiceOrder.TABLE_NAME, ServiceOrder.class);
		if (order == null) {
			order = (ServiceOrder) this.dao.findById(so.getId(), ServiceOrder.TABLE_NAME, ServiceOrder.class);
		}

		Manufacturer mfc = (Manufacturer) this.dao.findByKeyValue(Manufacturer.ID, order.getMfcId(), Manufacturer.TABLE_NAME, Manufacturer.class);

		if (mfc != null) {
			order.setMfcStoreName(mfc.getMfcStoreName());
		}

		ServiceProvider sp = (ServiceProvider) this.dao.findByKeyValue(ServiceProvider.ID, order.getSpId(), ServiceProvider.TABLE_NAME, ServiceProvider.class);

		if (sp != null) {
			order.setSpUserName(sp.getSpUserName());
		}
		
		if (order.getOperatorId() != null) {
			
			User kf = (User) this.dao.findByKeyValue(User.ID, order.getOperatorId(), User.TABLE_NAME, User.class);

			if (kf != null) {
				order.setKfName(kf.getUserName());
			}

		}
		
		if (order.getCateId() != null) {
			Category cate = (Category) this.dao.findByKeyValue(Category.ID, order.getCateId(), Category.TABLE_NAME, Category.class);

			if (cate != null) {
				order.setCategoryName(cate.getName());
			}

		}

		return order;

	}
	
	public void markerOrderNeedBill(ServiceOrder order){
		
		this.dao.updateById(order);
	}
	
	
	
	public void markerMfcOrderNeedBill(ServiceOrder order){
		this.dao.updateById(order);
	}
	
	public EntityResults<Bill> listSpClosedBillForAdmin(SearchVo svo) {
		DataBaseQueryBuilder builder = getSpAdminBillSearch(svo);

		return this.dao.listByQueryWithPagnation(builder, Bill.class);
	}

	public DataBaseQueryBuilder getSpAdminBillSearch(SearchVo svo) {
	    DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Bill.TABLE_NAME);
		builder.join(Bill.TABLE_NAME, ServiceProvider.TABLE_NAME, Bill.SP_ID, ServiceProvider.ID);
		builder.joinColumns(ServiceProvider.TABLE_NAME, new String[] { ServiceProvider.SP_USER_NAME, ServiceProvider.SP_CONTACT_PERSON, ServiceProvider.SP_CONTACT_MOBILE_PHONE });
		builder.limitColumns(new Bill().getColumnList());
		builder.and(DataBaseQueryOpertion.NOT_NULL, Bill.SP_ID);

		builder = mergeCommonSearchQuery(svo, builder, Bill.TABLE_NAME, Bill.BILL_DATE, true);
	    return builder;
    }
	
	public EntityResults<Bill> listMfcClosedBillHisitoryForAdmin(SearchVo svo) {
		DataBaseQueryBuilder builder = getMfcBillSearchForADMIN(svo);
		
		return this.dao.listByQueryWithPagnation(builder, Bill.class);
	}

	public DataBaseQueryBuilder getMfcBillSearchForADMIN(SearchVo svo) {
	    DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Bill.TABLE_NAME);
		builder.join(Bill.TABLE_NAME, Manufacturer.TABLE_NAME, Bill.MFC_ID, Manufacturer.ID);
		builder.joinColumns(Manufacturer.TABLE_NAME, new String[] { Manufacturer.MFC_STORE_NAME, Manufacturer.MFC_CONTACT_PERSON, Manufacturer.MFC_CONTACT_MOBILE_PHONE });
		builder.limitColumns(new Bill().getColumnList());
		builder.and(DataBaseQueryOpertion.NOT_NULL, Bill.MFC_ID);
		
		builder = mergeCommonSearchQuery(svo, builder, Bill.TABLE_NAME, Bill.BILL_DATE, true);
	    return builder;
    }

	public EntityResults<ServiceOrder> listServiceOrdersByBill(Bill bill) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);

		mergeSpJoinQuery(query);

		query.or(ServiceOrder.SP_BILL_ID, bill.getId());
		query.or(ServiceOrder.MFC_BILL_ID, bill.getId());

		return this.dao.listByQueryWithPagnation(query, ServiceOrder.class);
	}
	
	public void recycleBillOrders(IDS ids){
		for(String id: ids.getIds()){
			
			ServiceOrder order = new ServiceOrder();
			order.setId(id);
			order.setSpNeedBill(true);
			this.dao.updateById(order);
		}
	}
	
	public void recycleBillMfcOrders(IDS ids){
		for(String id: ids.getIds()){			
			ServiceOrder order = new ServiceOrder();
			order.setId(id);
			order.setMfcNeedBill(true);
			this.dao.updateById(order);
		}
	}
	
	public void changeManualOrderOperators(String userId) {
		DataBaseQueryBuilder squery = getUserRejectedOrderQueryBuilder();
		squery.and(ServiceOrder.OPERATOR_ID, userId);
		List<ServiceOrder> orderList = dao.listByQuery(squery, ServiceOrder.class);
		for (ServiceOrder order : orderList) {
			order.setOperatorId(null);
			this.dao.updateById(order);
		}

		DataBaseQueryBuilder pquery = getSystemRejectOrderQueryBuilder();

		pquery.and(ProductOrder.OPERATOR_ID, userId);
		List<ProductOrder> polist = dao.listByQuery(pquery, ProductOrder.class);
		for (ProductOrder order : polist) {
			order.setOperatorId(null);
			this.dao.updateById(order);
		}

	}
	
	
	public void updateServiceOrderOperator() {
		DataBaseQueryBuilder query = getUserRejectedOrderQueryBuilder();

		List<ServiceOrder> orders = this.dao.listByQuery(query, ServiceOrder.class);
		for (ServiceOrder order : orders) {
			User user = us.getManualServiceOrderOperator(order);
			
			User oldOperator = null;
			if(order.getOperatorId()!=null){
				oldOperator = (User) this.dao.findById(order.getOperatorId(), User.TABLE_NAME, User.class);
			}
			
			
			if (oldOperator != null && UserStatus.LOCKED.toString().equalsIgnoreCase(oldOperator.getStatus())) {
				order.setOperatorId(null);
			}
			
			
			if (oldOperator != null && !us.inRole(oldOperator.getGroupId(), ECommerceUserServiceImpl.ADM_ORDER_MANAGE)) {
				order.setOperatorId(null);
			}
			if (user != null && order.getOperatorId() == null) {

				order.setOperatorId(user.getId());
				this.dao.updateById(order);
			}
		}
	}

	public void updateProductOrderOperator() {
		DataBaseQueryBuilder query = getSystemRejectOrderQueryBuilder();
		List<ProductOrder> orders = this.dao.listByQuery(query, ProductOrder.class);
		for (ProductOrder order : orders) {
			User user = us.getManualProductOrderOperator(order);

			User oldOperator = null;
			if(order.getOperatorId()!=null){
				oldOperator = (User) this.dao.findById(order.getOperatorId(), User.TABLE_NAME, User.class);
			}
			
			if(oldOperator!=null && UserStatus.LOCKED.toString().equalsIgnoreCase(oldOperator.getStatus())){
				order.setOperatorId(null);
			}
			if (oldOperator != null && !us.inRole(oldOperator.getGroupId(), ECommerceUserServiceImpl.ADM_ORDER_MANAGE)) {
				order.setOperatorId(null);
			}
			
			if (user != null && order.getOperatorId() == null) {

				order.setOperatorId(user.getId());
				this.dao.updateById(order);
			}
		}
	}

	public void updateProductOrderComments(String orderId, String userId, String comments, String operation) {
		if (EcUtil.isValid(comments)) {
			DataBaseQueryBuilder userQuery = new DataBaseQueryBuilder(User.TABLE_NAME);
			userQuery.and(User.ID, userId);
			userQuery.limitColumns(new String[] { User.USER_NAME });
			User user = (User) this.dao.findOneByQuery(userQuery, User.class);

			if (user != null) {

			}

			ProductOrder order = (ProductOrder) this.dao.findById(orderId, ProductOrder.TABLE_NAME, ProductOrder.class);
			if (order != null) {
				String newComments = String.format("【%s】于【%s】对此订单做了操作【%s】，备注【%s】", user.getUserName(), DateUtil.getDateStringTime(new Date()), operation, comments);
				if (order.getAllComments() == null) {
					order.setAllComments(newComments);
				} else {
					order.setAllComments(order.getAllComments() + "<br>" + newComments);
				}
				this.dao.updateById(order);
			}
		}
	}

	public void updateServiceOrderComments(String orderId, String userId, String comments, String operation) {

		if (EcUtil.isValid(comments)) {

			DataBaseQueryBuilder userQuery = new DataBaseQueryBuilder(User.TABLE_NAME);
			userQuery.and(User.ID, userId);
			userQuery.limitColumns(new String[] { User.USER_NAME });
			User user = (User) this.dao.findOneByQuery(userQuery, User.class);

			ServiceOrder order = (ServiceOrder) this.dao.findByKeyValue(ServiceOrder.SO_CODE, orderId, ServiceOrder.TABLE_NAME, ServiceOrder.class);
			if (order == null) {
				order = (ServiceOrder) this.dao.findById(orderId, ServiceOrder.TABLE_NAME, ServiceOrder.class);
			}
			if (order != null) {
				String newComments = String.format("【%s】于【%s】对此订单做了操作【%s】，备注【%s】", user.getUserName(), DateUtil.getDateStringTime(new Date()), operation, comments);
				if (order.getAllComments() == null) {
					order.setAllComments(newComments);
				} else {
					order.setAllComments(order.getAllComments() + "<br>" + newComments);
				}

				this.dao.updateById(order);
			}
		}

	}
	
	public void markerOrderIsNoticed(IDS ids){
		List<String> idList = ids.getIds();
		for(String id: idList){
			ServiceOrder order = new ServiceOrder();
			order.setId(id);
			order.setIsNoticed(true);
			this.dao.updateById(order);
		}
		
	}

}
