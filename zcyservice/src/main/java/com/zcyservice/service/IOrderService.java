package com.zcyservice.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zcy.bean.EntityResults;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcyservice.bean.Bill;
import com.zcyservice.bean.Category;
import com.zcyservice.bean.Location;
import com.zcyservice.bean.ProductOrder;
import com.zcyservice.bean.ServiceOrder;
import com.zcyservice.bean.ServiceScore;
import com.zcyservice.bean.vo.ComplaintSearchVo;
import com.zcyservice.bean.vo.IDS;
import com.zcyservice.bean.vo.SearchVo;
import com.zcyservice.bean.vo.ServiceOrderForComplaintVO;
import com.zcyservice.bean.vo.UserOrderVO;

public interface IOrderService {

	public List<String> importTaoBoOrder(InputStream inputStream, boolean isOverride);
	
	public void addProductOrder(ProductOrder order);
	
	public void addProductOrders(List<ProductOrder> porders);

	public void activeProductOrders(List<String> ids);
	
	public void deleteProductOrders(List<String> ids);
	
	public void deleteAllProductOrders();

	public EntityResults<ProductOrder> listMfcAllProductOrders(SearchVo search);
	
	public EntityResults<ProductOrder> listMfcNewProductOrders(SearchVo search);

	public EntityResults<ProductOrder> listProductOrdersForAdmin(SearchVo search);

	public EntityResults<ServiceOrder> listServiceOrdersForAdmin(SearchVo search);

	public EntityResults<ServiceOrder> listSpHistoryServiceOrders(SearchVo search);
	
	public EntityResults<ServiceOrder> listMfcServiceOrders(SearchVo search);
	
	public EntityResults<UserOrderVO> listUserServiceOrders(SearchVo search);
	
	public EntityResults<ServiceOrderForComplaintVO> listServiceOrdersForComplaint(ComplaintSearchVo svo);

	public void acceptServiceOrders(List<String> ids);

	public void finishServiceOrders(List<String> ids);

	public EntityResults<ServiceOrder> listSpNewServiceOrders(SearchVo search);
	
	public Map<String, Object> countSpNewServiceOrders();

	public EntityResults<ServiceOrder> listMyAcceptedServiceOrders(SearchVo search);

	public EntityResults<ServiceOrder> listMyAssignedServiceOrders(SearchVo search);

	public void assignServiceOrderWorker(ServiceOrder order);
	
	public void terminateServiceOrder(ServiceOrder order);
	
	public void rejectServiceOrder(ServiceOrder order);

	public EntityResults<ProductOrder> listSystemRejectedProductOrders(SearchVo search);

	public EntityResults<ServiceOrder> listUserRejectedServiceOrders(SearchVo search);
	
	public DataBaseQueryBuilder getSystemRejectOrderQueryBuilder(); 
	
	public DataBaseQueryBuilder getUserRejectedOrderQueryBuilder();

	public void cancelProductOrders(IDS ids);
	
	public void cancelServiceOrders(IDS ids);

	public EntityResults<ServiceOrder> listNoticeOrders(SearchVo search);
	
	public DataBaseQueryBuilder getOrderNoticeQueryBuilder(SearchVo search);

	public void cancelServiceOrderNotices(List<String> ids);

	public void splitProductOrders(List<ServiceOrder> orderList);
	
	public void changeServiceOrderSp(ServiceOrder order);

	public Map<String, Object> getMfcOrderStats(SearchVo svo);

	public EntityResults<ServiceOrder> listMfcOrderBillForAdmin(SearchVo svo);

	public List<ServiceOrder> listSpOrderBillForAdmin(SearchVo svo);
	
	public EntityResults<ServiceOrder> listSpOrderRecycleBillForAdmin(SearchVo svo);
	
	public EntityResults<ServiceOrder> listMfcOrderRecycleBillForAdmin(SearchVo svo);
	
	public EntityResults<Bill> listMfcOrderBillHistory(SearchVo search);
	
	public EntityResults<Bill> listSpOrderBillHistory(SearchVo search);
	
	public void confirmSpOrderBills(Bill bill, List<String> ids);

	public void confirmMfcOrderBills(Bill bill, List<String> ids);

	public void confirmUserOrder(ServiceOrder order);

	public void addServiceOrderScore(ServiceScore score);

	public Map<String, Object> exportMfcOrderBill(HttpServletRequest request, SearchVo svo);

	public Map<String, Object> exportSpOrderBill(HttpServletRequest request, SearchVo svo);

	public EntityResults<ServiceOrder> listServiceOrdersByProdcutOrder(ProductOrder po);
	
	public ProductOrder getProductOrderInfo(ProductOrder po);
	
	public ServiceOrder getServiceOrderInfo(ServiceOrder so);

	public void markerOrderNeedBill(ServiceOrder order);

	public EntityResults<Bill> listSpClosedBillForAdmin(SearchVo svo);
	
	public EntityResults<Bill> listMfcClosedBillHisitoryForAdmin(SearchVo svo);

	public EntityResults<ServiceOrder> listServiceOrdersByBill(Bill bill);

	public void recycleBillOrders(IDS ids);
	
	public void recycleBillMfcOrders(IDS ids);

	public void markerMfcOrderNeedBill(ServiceOrder order); 
	
	public Category updateOrderCategory(ProductOrder porder, ServiceOrder sor);
	
	public Location updateOrderLocation(ProductOrder porder, ServiceOrder sor);


	public void changeManualOrderOperators(String userId);
	
	public void updateServiceOrderOperator();
	public void updateProductOrderOperator();
	
	public void updateProductOrderComments(String orderId, String userId, String comments, String operation);
	
	
	public void updateServiceOrderComments(String orderId, String userId, String comments, String operation);

	public Map<String, Object> getOrderStats(SearchVo svo);

	public Map<String, Object> countMfcNewServiceOrders();


	public void markerOrderIsNoticed(IDS ids);
	
	public int countSpBillForAdmin(SearchVo search);
	
	public int countMfcBillForAdmin(SearchVo search);
	
	public int countSpBillForSpSelf(SearchVo search);
	
	public int countMfcBillForMfcSelf(SearchVo search);
	


}
