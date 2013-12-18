package com.zcyservice.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zcy.bean.EntityResults;
import com.zcy.bean.Pagination;
import com.zcy.constants.EConstants;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcy.dbhelper.DataBaseQueryOpertion;
import com.zcy.exception.ResponseException;
import com.zcy.util.EcThreadLocal;
import com.zcy.util.EcUtil;
import com.zcyservice.bean.Complaint;
import com.zcyservice.bean.ComplaintType;
import com.zcyservice.bean.ProductOrder;
import com.zcyservice.bean.ServiceOrder;
import com.zcyservice.bean.ServiceProvider;
import com.zcyservice.bean.Complaint.ComplaintStaus;
import com.zcyservice.bean.vo.ComplaintSearchVo;
import com.zcyservice.bean.vo.SearchVo;
import com.zcyservice.bean.vo.ServiceOrderForComplaintVO;
import com.zcyservice.service.EcommerceService;
import com.zcyservice.service.IComplaintService;

@Service(value = "complaintService")
public class ComplaintServiceImpl extends EcommerceService implements IComplaintService {
	private static Logger logger = LogManager.getLogger(CategoryServiceImpl.class);

	@Override
	public void addComplaint(Complaint complaint) {

		if (!EcUtil.isEmpty(complaint.getId())) {
			if (complaint.getCompStatus() != null) {
				if (ComplaintStaus.CLOSED.toString().equalsIgnoreCase(complaint.getCompStatus().toString())) {
					complaint.setCloseDate(new Date());
				}
			}

			dao.updateById(complaint);

		} else {
			String cuid = EcThreadLocal.getCurrentUserId();
			complaint.setCreatorId(cuid);

			DataBaseQueryBuilder query = new DataBaseQueryBuilder(Complaint.TABLE_NAME);
			query.and(Complaint.SERVICE_ORDER_ID, complaint.getServiceOrderId());
			if (this.dao.exists(query)) {
				throw new ResponseException("此订单投诉已经存在，请刷新页面");
			}
			dao.insert(complaint);

		}

	}

	@Override
	public void operComplaint(Complaint complaint) {
		String cuid = EcThreadLocal.getCurrentUserId();
		complaint.setSolverId(cuid);

		if (complaint.getCompStatus() != null) {
			if (ComplaintStaus.CLOSED.toString().equalsIgnoreCase(complaint.getCompStatus().toString())) {
				complaint.setCloseDate(new Date());
			}
		}
		
		dao.updateById(complaint);

	}

	/**
	 * listType:1,2,3
	 * 1: 全部投诉: 平台上所有的投诉记录
	 * 2: 投诉记录：记录的是你查询的手机号对应的客户的所有投诉记录（customerId）
	 * 3: 我的投诉：就记录当前客服添加的投诉和处理的投诉
	 */
	@Override
	public EntityResults<ServiceOrderForComplaintVO> listComplaint(ComplaintSearchVo svo) {
		
		EntityResults<ServiceOrderForComplaintVO> result = new EntityResults<ServiceOrderForComplaintVO>();
		
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
		
		int listType = Integer.parseInt(svo.getListType());
		//StringBuffer sql = new StringBuffer("select so.id,so.poCode,so.poReceiverName,so.poReceiverAddress,so.poReceiverPhone,so.poReceiverMobilePhone,so.poGoodsTitle,so.poLogisticsNo,so.poLogisticsCompany,so.poOrderRemark,so.poGoodsNumber,so.soCode,so.price,so.soStatus,c.id complaintId,c.compStatus complaintStatus,c.compTypeId complaintType,c.compRemark complaintRemark from ServiceOrder so right join complaint c on so.id=c.serviceOrderId");
		StringBuffer sql = new StringBuffer("select so.id,so.soCode,so.poCode,so.poReceiverName,so.poReceiverAddress,so.poReceiverPhone,so.poReceiverMobilePhone,so.poGoodsTitle,so.poLogisticsNo,so.poLogisticsCompany,so.poOrderRemark,so.poGoodsNumber,so.soCode,so.price,so.soStatus,c.id complaintId, c.serviceOrderId serviceOrderId, c.compStatus complaintStatus,c.compTypeId complaintType,ct.name complaintTypeName,c.compRemark complaintRemark from complaint c left join ServiceOrder so on so.id=c.serviceOrderId left join ComplaintType ct on c.compTypeId=ct.id");
		if (listType == 1){
			sql.append(" limit ").append(limitStart).append(",").append(limitRows).append(";");
			List<ServiceOrderForComplaintVO> entityList =  dao.listBySql(sql.toString(), ServiceOrderForComplaintVO.class);
			result.setEntityList(entityList);
			
			DataBaseQueryBuilder countBuilder = new DataBaseQueryBuilder(Complaint.TABLE_NAME);
			Pagination pagnation = new Pagination();
			pagnation.setTotal(dao.count(countBuilder));
			result.setPagnation(pagnation);
		}else if (listType == 2){//FIXME pagination info logic should fix
			String customerPhone = svo.getMobilePhone();
			
			//StringBuffer sql = new StringBuffer("select c.compStatus complaintStatus,c.compTypeId complaintType,so.soCode,so.poReceiverMobilePhone from Complaint c join ServiceOrder so on c.serviceOrderId=so.id")
			sql.append(" where c.serviceOrderId in (select id from ServiceOrder where poReceiverMobilePhone = \"")
			.append(customerPhone).append("\"").append(")")
			.append(" limit ").append(limitStart).append(",").append(limitRows).append(";");
			List<ServiceOrderForComplaintVO> entityList =  dao.listBySql(sql.toString(), ServiceOrderForComplaintVO.class);
			result.setEntityList(entityList);
			
			//StringBuffer countSql = new StringBuffer("select c.compStatus complaintStatus,c.compTypeId complaintType,so.soCode,so.poReceiverMobilePhone from Complaint c join ServiceOrder so on c.serviceOrderId=so.id")
			StringBuffer countSql = new StringBuffer("select so.id,so.poCode,so.poReceiverName,so.poReceiverAddress,so.poReceiverPhone,so.poReceiverMobilePhone,so.poGoodsTitle,so.poLogisticsNo,so.poLogisticsCompany,so.poOrderRemark,so.poGoodsNumber,so.soCode,so.price,so.soStatus,c.id complaintId,c.compStatus complaintStatus,c.compTypeId complaintType,c.compRemark complaintRemark from ServiceOrder so right join Complaint c on so.id=c.serviceOrderId")
			.append(" where c.serviceOrderId in (select id from ServiceOrder where poReceiverMobilePhone = \"")
			.append(customerPhone).append("\"").append(")").append(";");
			List<ServiceOrderForComplaintVO> listForCount =  dao.listBySql(countSql.toString(), ServiceOrderForComplaintVO.class);
			Pagination pagnation = new Pagination();
			pagnation.setTotal(listForCount.size());
			result.setPagnation(pagnation);
		}else{//listType == 3//FIXME pagination info logic should fix
			String cuid = EcThreadLocal.getCurrentUserId();
			
			//StringBuffer sql = new StringBuffer("select c.compStatus complaintStatus,c.compTypeId complaintType,so.soCode,so.poReceiverMobilePhone from Complaint c join ServiceOrder so on c.serviceOrderId=so.id")
			sql.append(" where c.creatorId = \"").append(cuid).append("\"").append(" or c.solverId = \"").append(cuid).append("\"")
			.append(" limit ").append(limitStart).append(",").append(limitRows).append(";");
			List<ServiceOrderForComplaintVO> entityList =  dao.listBySql(sql.toString(), ServiceOrderForComplaintVO.class);
			result.setEntityList(entityList);
			
			DataBaseQueryBuilder countBuilder = new DataBaseQueryBuilder(Complaint.TABLE_NAME);
			countBuilder.or("creatorId", cuid);
			countBuilder.or(Complaint.SOLVER_ID, cuid);
			Pagination pagnation = new Pagination();
			pagnation.setTotal(dao.count(countBuilder));
			result.setPagnation(pagnation);
		}
		return result;
	}
	
	public EntityResults<ServiceOrderForComplaintVO> listAllComplaintOrdersForAdmin(SearchVo svo) {
		DataBaseQueryBuilder query = getComplaintOrderQuery(svo, true);
		
		return this.dao.listByQueryWithPagnation(query, ServiceOrderForComplaintVO.class);
	}

	public DataBaseQueryBuilder getComplaintOrderQuery(SearchVo svo, boolean isAdmin) {
	    DataBaseQueryBuilder query = new DataBaseQueryBuilder(Complaint.TABLE_NAME);
		query.join(Complaint.TABLE_NAME, ServiceOrder.TABLE_NAME, Complaint.SERVICE_ORDER_ID, ServiceOrder.ID);
		query.joinColumns(ServiceOrder.TABLE_NAME, new String[] { ServiceOrder.ID, ServiceOrder.ACCEPTED_DATE, ServiceOrder.ASSIGN_DATE, ServiceOrder.FINISH_DATE, ServiceOrder.PO_GOODS_NUMBER, ServiceOrder.PO_CODE, ServiceOrder.SO_CODE, ServiceOrder.PO_RECEIVER_NAME, ServiceOrder.PO_RECEIVER_MOBILE_PHONE,
		        ServiceOrder.SO_STATUS, ServiceOrder.PRICE });

		query.join(ServiceOrder.TABLE_NAME, ServiceProvider.TABLE_NAME, ServiceOrder.SP_ID, ServiceProvider.ID);
		query.joinColumns(ServiceProvider.TABLE_NAME, new String[] { ServiceProvider.SP_USER_NAME });
		
		query.join(Complaint.TABLE_NAME, ComplaintType.TABLE_NAME, Complaint.COMP_TYPE_ID, ComplaintType.ID);
		query.joinColumns(ComplaintType.TABLE_NAME, new String[] { ComplaintType.NAME + ",complaintTypeName"});

		query.limitColumns(new String[] { Complaint.COMP_TYPE_ID, Complaint.COMP_STATUS + ",complaintStatus"  });
		
		if (!isAdmin) {
			query.and(ServiceOrder.TABLE_NAME + "." + ServiceOrder.SP_USER_ID, EcThreadLocal.getCurrentUserId());
		}
		
		if(EcUtil.isValid(svo.getSpId())){
			query.and(ServiceOrder.TABLE_NAME + "." + ServiceOrder.SP_ID, svo.getSpId());
		}

		mergeCommonSearchQuery(svo, query, Complaint.TABLE_NAME, Complaint.TABLE_NAME + "." + Complaint.CREATED_ON, false);
		
		DataBaseQueryBuilder keywordBuilder = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		if (!EcUtil.isEmpty(svo.getKeyword())) {
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ServiceOrder.TABLE_NAME + "." + ServiceOrder.PO_RECEIVER_MOBILE_PHONE, svo.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ServiceOrder.TABLE_NAME + "." + ServiceOrder.PO_RECEIVER_ADDRESS, svo.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ServiceOrder.TABLE_NAME + "." + ServiceOrder.PO_MEMBER_NAME, svo.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ServiceOrder.TABLE_NAME + "." + ServiceOrder.PO_RECEIVER_NAME, svo.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ServiceOrder.TABLE_NAME + "." + ServiceOrder.PO_GOODS_TITLE, svo.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ServiceOrder.TABLE_NAME + "." + ServiceOrder.PO_RECEIVER_PHONE, svo.getKeyword());
			keywordBuilder.or(DataBaseQueryOpertion.LIKE, ServiceOrder.TABLE_NAME + "." + ServiceOrder.PO_CODE, svo.getKeyword());
		}

		if (!EcUtil.isEmpty(keywordBuilder.getQueryStr())) {
			query = query.and(keywordBuilder);
		}
	    return query;
    }

	@Override
    public List<ComplaintType> listAllComplaintTypes() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ComplaintType.TABLE_NAME);
		builder.and(DataBaseQueryOpertion.NULL, ComplaintType.EXPIRED_TIME);
		builder.orderBy(ComplaintType.ORDER_POSITION, true);
	    return dao.listByQuery(builder, ComplaintType.class);
    }

	@Override
	public void addComplaintType(ComplaintType complaintType) {

		if (complaintType.getOrderPosition() == null) {
			complaintType.setOrderPosition(1);
		}
		if (!EcUtil.isEmpty(complaintType.getId())) {
			dao.updateById(complaintType);
		} else {
			dao.insert(complaintType);
		}
	}

	@Override
    public EntityResults<ComplaintType> listPageComplaintTypes() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(ComplaintType.TABLE_NAME);
		builder.and(DataBaseQueryOpertion.NULL, ComplaintType.EXPIRED_TIME);
	    return dao.listByQueryWithPagnation(builder, ComplaintType.class);
    }

	@Override
    public void delComplaintType(ComplaintType complaintType) {
		complaintType.setExpiredTime(new Date());
	    dao.updateById(complaintType);
    }

}
