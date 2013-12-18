package com.zcyservice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zcy.annotation.LoginRequired;
import com.zcy.annotation.Permission;
import com.zcy.controller.AbstractController;
import com.zcy.exception.ResponseException;
import com.zcy.util.EcThreadLocal;
import com.zcyservice.bean.Bill;
import com.zcyservice.bean.ProductOrder;
import com.zcyservice.bean.ServiceOrder;
import com.zcyservice.bean.ServiceScore;
import com.zcyservice.bean.vo.ComplaintSearchVo;
import com.zcyservice.bean.vo.IDS;
import com.zcyservice.bean.vo.SearchVo;
import com.zcyservice.service.IOrderService;
import com.zcyservice.util.PermissionConstants;

@Controller
@RequestMapping("/ecs/order")
@Permission()
@LoginRequired()
public class OrderController extends AbstractController {
	private static Logger logger = LogManager.getLogger(OrderController.class);

	@Autowired
	private IOrderService pos;

	@RequestMapping("/mfc/pro/import.do")
	public void importProductOrder(HttpServletRequest request, HttpServletResponse response) {
		SearchVo search = (SearchVo)parserJsonParameters(request, true, SearchVo.class);

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile uploadFile = multipartRequest.getFile("file");
		List<String> msgs = new ArrayList<String>();
		String name = uploadFile.getOriginalFilename().toLowerCase().trim().replaceAll(" ", "");
		int index = name.lastIndexOf(".");
		if(index == -1){
			throw new ResponseException("请上传xls和xlsx格式的订单文件");
		}
		String fType = name.substring(index, name.length());
		if(!fType.equals(".xls") && !fType.equals(".xlsx")){
			throw new ResponseException("文件格式仅支持 xls和xlsx");
		}
		
		try {
			msgs = pos.importTaoBoOrder(uploadFile.getInputStream(), search.isOverride());
		} catch (IOException e) {
			logger.error("导入淘宝订单错误", e);
			throw new ResponseException("导入淘宝订单错误,  请稍后重试，重试后还出错，请联系客服。");
		}
		if (!msgs.isEmpty()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("msg", msgs);
			responseWithData(map, request, response);
		}else{
			responseWithData(null, request, response);
		}
	}
	
	@RequestMapping("/mfc/pro/mine.do")
	public void listMyProductOrders(HttpServletRequest request, HttpServletResponse response) {
		SearchVo search = (SearchVo)parserJsonParameters(request, true, SearchVo.class);
		responseWithDataPagnation(pos.listMfcAllProductOrders(search), request, response);
	}
	
	@RequestMapping("/mfc/po/listso.do")
	public void listServiceOrdersByProdcutOrder(HttpServletRequest request, HttpServletResponse response) {
		ProductOrder po = (ProductOrder)parserJsonParameters(request, true, ProductOrder.class);
		responseWithDataPagnation(pos.listServiceOrdersByProdcutOrder(po), request, response);
	}
	
	@RequestMapping("/pro/detail.do")
	public void getProductOrderInfo(HttpServletRequest request, HttpServletResponse response) {
		ProductOrder po = (ProductOrder)parserJsonParameters(request, true, ProductOrder.class);
		responseWithEntity(pos.getProductOrderInfo(po), request, response);
	}
	
	@RequestMapping("/so/detail.do")
	public void getServiceOrderInfo(HttpServletRequest request, HttpServletResponse response) {
		ServiceOrder so = (ServiceOrder)parserJsonParameters(request, true, ServiceOrder.class);
		responseWithEntity(pos.getServiceOrderInfo(so), request, response);
	}

	@RequestMapping("/mfc/pro/delete.do")
	public void deleteProductOrders(HttpServletRequest request, HttpServletResponse response) {
		IDS ids = (IDS) parserJsonParameters(request, false, IDS.class);
		pos.deleteProductOrders(ids.getIds());
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/mfc/pro/deleteAll.do")
	public void deleteAllProductOrders(HttpServletRequest request, HttpServletResponse response) {
		pos.deleteAllProductOrders();
		responseWithData(null, request, response);
	}	
	
	
	@RequestMapping("/pro/admin/list.do")
	@Permission(groupName = PermissionConstants.ADM_ORDER_QUERY, permissionID = PermissionConstants.ADM_ORDER_QUERY)
	public void listProductOrdersForAdmin(HttpServletRequest request, HttpServletResponse response) {
		SearchVo search = (SearchVo)parserJsonParameters(request, true, SearchVo.class);
		responseWithDataPagnation(pos.listProductOrdersForAdmin(search), request, response);
	}
	
	@RequestMapping("/so/admin/list.do")
	@Permission(groupName = PermissionConstants.ADM_ORDER_QUERY, permissionID = PermissionConstants.ADM_ORDER_QUERY)
	public void listServiceOrdersForAdmin(HttpServletRequest request, HttpServletResponse response) {
		SearchVo search = (SearchVo)parserJsonParameters(request, true, SearchVo.class);
		responseWithDataPagnation(pos.listServiceOrdersForAdmin(search), request, response);
	}
	
	@RequestMapping("/mfc/pro/newlist.do")
	public void listMyNewProductOrders(HttpServletRequest request, HttpServletResponse response) {
		SearchVo search = (SearchVo)parserJsonParameters(request, true, SearchVo.class);
		responseWithDataPagnation(pos.listMfcNewProductOrders(search), request, response);
	}
	
	
	@RequestMapping("/pro/sysrejectlist.do")
	@Permission(groupName = PermissionConstants.ADM_ORDER_MANAGE, permissionID = PermissionConstants.ADM_ORDER_MANAGE)
	public void listSystemRejectedProductOrders(HttpServletRequest request, HttpServletResponse response) {
		SearchVo search = (SearchVo) parserJsonParameters(request, true, SearchVo.class);
		responseWithDataPagnation(pos.listSystemRejectedProductOrders(search), request, response);
	}
	
	
	@RequestMapping("/so/userrejectlist.do")
	@Permission(groupName = PermissionConstants.ADM_ORDER_MANAGE, permissionID = PermissionConstants.ADM_ORDER_MANAGE)
	public void listUserRejectedServiceOrders(HttpServletRequest request, HttpServletResponse response) {
		SearchVo search = (SearchVo) parserJsonParameters(request, true, SearchVo.class);

		responseWithDataPagnation(pos.listUserRejectedServiceOrders(search), request, response);
	}
	
	@RequestMapping("/so/ordernoticelist.do")
	@Permission(groupName = PermissionConstants.ADM_ORDER_NOTICE_MANAGE, permissionID = PermissionConstants.ADM_ORDER_NOTICE_MANAGE)
	public void listNoticeOrders(HttpServletRequest request, HttpServletResponse response) {
		SearchVo search = (SearchVo)parserJsonParameters(request, true, SearchVo.class);
		responseWithDataPagnation(pos.listNoticeOrders(search), request, response);
	}




	@RequestMapping("/pro/add.do")
	public void addProductOrders(HttpServletRequest request, HttpServletResponse response) {
		List<ProductOrder> ps = this.parserListJsonParameters(request, false, ProductOrder.class);
		pos.addProductOrders(ps);
		responseWithDataPagnation(null, request, response);
	}
	
	@RequestMapping("/mfc/pro/edit.do")
	public void editProductOrder(HttpServletRequest request, HttpServletResponse response) {
		ProductOrder ps = (ProductOrder) parserJsonParameters(request, false, ProductOrder.class);
		pos.addProductOrder(ps);
		responseWithData(null, request, response);
	}

	@RequestMapping("/pro/active.do")
	public void activeProductOrders(HttpServletRequest request, HttpServletResponse response) {
		IDS ids = (IDS) parserJsonParameters(request, false, IDS.class);
		pos.activeProductOrders(ids.getIds());
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/pro/cancel.do")
	@Permission(groupName = PermissionConstants.ADM_ORDER_MANAGE, permissionID = PermissionConstants.ADM_ORDER_MANAGE)
	public void cancelProductOrders(HttpServletRequest request, HttpServletResponse response) {
		IDS ids = (IDS) parserJsonParameters(request, false, IDS.class);
		pos.cancelProductOrders(ids);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/so/cancel.do")
	@Permission(groupName = PermissionConstants.ADM_ORDER_MANAGE, permissionID = PermissionConstants.ADM_ORDER_MANAGE)
	public void cancelServiceOrders(HttpServletRequest request, HttpServletResponse response) {
		IDS ids = (IDS) parserJsonParameters(request, false, IDS.class);
		pos.cancelServiceOrders(ids);
		responseWithData(null, request, response);
	}

	@RequestMapping("/mfc/mineso.do")
	public void listMfcServiceOrders(HttpServletRequest request, HttpServletResponse response) {
		SearchVo search = (SearchVo)parserJsonParameters(request, true, SearchVo.class);
		responseWithDataPagnation(pos.listMfcServiceOrders(search), request, response);
	}
	
	@RequestMapping("/sp/so/historylist.do")
	public void listSpServiceOrders(HttpServletRequest request, HttpServletResponse response) {
		SearchVo search = (SearchVo)parserJsonParameters(request, true, SearchVo.class);
		responseWithDataPagnation(pos.listSpHistoryServiceOrders(search), request, response);
	}
	
	@RequestMapping("/user/so/mine.do")
	public void listUserServiceOrders(HttpServletRequest request, HttpServletResponse response) {
		SearchVo search = (SearchVo)parserJsonParameters(request, true, SearchVo.class);
		responseWithDataPagnation(pos.listUserServiceOrders(search), request, response);
	}

	@RequestMapping("/so/listforcomplaint.do")
	@Permission(groupName = PermissionConstants.ADM_COMPLAINT_MANAGE, permissionID = PermissionConstants.ADM_COMPLAINT_MANAGE)
	public void listServiceOrdersByCustomerPhoneNo(HttpServletRequest request, HttpServletResponse response) {
		ComplaintSearchVo svo = (ComplaintSearchVo) parserJsonParameters(request,true, ComplaintSearchVo.class);
		responseWithDataPagnation(pos.listServiceOrdersForComplaint(svo), request, response);
	}
	
	
	@RequestMapping("/sp/so/newlist.do")
	public void listSpNewServiceOrders(HttpServletRequest request, HttpServletResponse response) {
		SearchVo search = (SearchVo)parserJsonParameters(request, true, SearchVo.class);
		responseWithDataPagnation(pos.listSpNewServiceOrders(search), request, response);
	}
	
	@RequestMapping("/sp/so/new/count.do")
	public void countSpNewServiceOrders(HttpServletRequest request, HttpServletResponse response) {
		responseWithData(pos.countSpNewServiceOrders(), request, response);
	}
	
	@RequestMapping("/mfc/pro/new/count.do")
	public void countMfcNewServiceOrders(HttpServletRequest request, HttpServletResponse response) {
		responseWithData(pos.countMfcNewServiceOrders(), request, response);
	}
	
	@RequestMapping("/so/acceptlist.do")
	public void listMyAcceptedServiceOrders(HttpServletRequest request, HttpServletResponse response) {
		SearchVo search = (SearchVo)parserJsonParameters(request, true, SearchVo.class);
		responseWithDataPagnation(pos.listMyAcceptedServiceOrders(search), request, response);
	}
	
	
	@RequestMapping("/so/assignlist.do")
	public void listMyAssignedServiceOrders(HttpServletRequest request, HttpServletResponse response) {
		SearchVo search = (SearchVo)parserJsonParameters(request, true, SearchVo.class);
		responseWithDataPagnation(pos.listMyAssignedServiceOrders(search), request, response);
	}
	

	@RequestMapping("/so/accept.do")
	public void acceptServiceOrders(HttpServletRequest request, HttpServletResponse response) {
		IDS ids = (IDS) parserJsonParameters(request, false, IDS.class);
		pos.acceptServiceOrders(ids.getIds());
		responseWithDataPagnation(null, request, response);
	}
	
	
	@RequestMapping("/so/assign.do")
	public void assignServiceOrderWorker(HttpServletRequest request, HttpServletResponse response) {
		ServiceOrder order = (ServiceOrder) parserJsonParameters(request, false, ServiceOrder.class);
		pos.assignServiceOrderWorker(order);
		responseWithDataPagnation(null, request, response);
	}
	
	@RequestMapping("/so/terminate.do")
	public void terminateServiceOrder(HttpServletRequest request, HttpServletResponse response) {
		ServiceOrder order = (ServiceOrder) parserJsonParameters(request, false, ServiceOrder.class);
		pos.terminateServiceOrder(order);
		responseWithDataPagnation(null, request, response);
	}
	
	@RequestMapping("/so/finish.do")
	public void finishServiceOrders(HttpServletRequest request, HttpServletResponse response) {
		IDS ids = (IDS) parserJsonParameters(request, false, IDS.class);
		pos.finishServiceOrders(ids.getIds());
		responseWithDataPagnation(null, request, response);
	}

	@RequestMapping("/so/rejectorder.do")
	public void rejectServiceOrder(HttpServletRequest request, HttpServletResponse response) {
		ServiceOrder order = (ServiceOrder) parserJsonParameters(request, false, ServiceOrder.class);
		pos.rejectServiceOrder(order);
		responseWithDataPagnation(null, request, response);
	}
	

	
	@RequestMapping("/so/cancelNotice.do")
	@Permission(groupName = PermissionConstants.ADM_ORDER_MANAGE, permissionID = PermissionConstants.ADM_ORDER_MANAGE)
	public void cancelServiceOrderNotices(HttpServletRequest request, HttpServletResponse response) {
		IDS ids = (IDS) parserJsonParameters(request, false, IDS.class);
		pos.cancelServiceOrderNotices(ids.getIds());
		responseWithDataPagnation(null, request, response);
	}

	

	@RequestMapping("/pro/split.do")
	@Permission(groupName = PermissionConstants.ADM_ORDER_MANAGE, permissionID = PermissionConstants.ADM_ORDER_MANAGE)
	public void splitProductOrders(HttpServletRequest request, HttpServletResponse response) {
		List<ServiceOrder> orderList = this.parserListJsonParameters(request, false, ServiceOrder.class);
		pos.splitProductOrders(orderList);
		responseWithDataPagnation(null, request, response);

	}
	
	@RequestMapping("/so/changesp.do")
	@Permission(groupName = PermissionConstants.ADM_ORDER_MANAGE, permissionID = PermissionConstants.ADM_ORDER_MANAGE)
	public void changeServiceOrderSp(HttpServletRequest request, HttpServletResponse response) {
		ServiceOrder order = (ServiceOrder) parserJsonParameters(request, false, ServiceOrder.class);
		pos.changeServiceOrderSp(order);
		responseWithDataPagnation(null, request, response);

	}
	
	@RequestMapping("/mfc/static.do")
	public void getMfcOrderStats(HttpServletRequest request, HttpServletResponse response) {
		SearchVo svo = (SearchVo)parserJsonParameters(request, false, SearchVo.class);
		responseWithData(pos.getMfcOrderStats(svo), request, response);

	}
	
	@RequestMapping("/mfc/closed/billhistory.do")
	public void listMfcOrderBillHistory(HttpServletRequest request, HttpServletResponse response) {
		SearchVo svo = (SearchVo)parserJsonParameters(request, false, SearchVo.class);	
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("totalPrice", pos.countMfcBillForMfcSelf(svo));
		responseWithDataPagnation(pos.listMfcOrderBillHistory(svo), results, request, response);
	}
	
	
	@RequestMapping("/sp/closed/billhistory.do")
	public void listSpOrderBillHistory(HttpServletRequest request, HttpServletResponse response) {
		SearchVo svo = (SearchVo)parserJsonParameters(request, false, SearchVo.class);		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("totalPrice", pos.countSpBillForSpSelf(svo));
		responseWithDataPagnation(pos.listSpOrderBillHistory(svo), results, request, response);
	}
	
	@RequestMapping("/admin/mfc/bill.do")
	@Permission(groupName = PermissionConstants.ADM_BILL_MANAGE, permissionID = PermissionConstants.ADM_BILL_MANAGE)
	public void listMfcOrderBillForAdmin(HttpServletRequest request, HttpServletResponse response) {
		SearchVo svo = (SearchVo)parserJsonParameters(request, false, SearchVo.class);		
		
		List<ServiceOrder> orderList = pos.listMfcOrderBillForAdmin(svo).getEntityList();
		Map<String, Object> list = mergeOrderPrice(orderList);

		responseWithData(list, request, response);
		
	}
	

	@RequestMapping("/admin/sp/bill.do")
	@Permission(groupName = PermissionConstants.ADM_BILL_MANAGE, permissionID = PermissionConstants.ADM_BILL_MANAGE)
	public void listSpOrderBillForAdmin(HttpServletRequest request, HttpServletResponse response) {
		SearchVo svo = (SearchVo)parserJsonParameters(request, false, SearchVo.class);	
		
		List<ServiceOrder> orderList = pos.listSpOrderBillForAdmin(svo);
		
		Map<String, Object> list = mergeOrderPrice(orderList);
		
		responseWithData(list, request, response);
	}

	private Map<String, Object> mergeOrderPrice(List<ServiceOrder> orderList) {
		int price = 0;

		for (ServiceOrder order : orderList) {

			if (order.getPrice() != null) {
				price = price + order.getPoGoodsNumber() * order.getPrice();
			}

			if (order.getPlusPrice() != null) {
				price = price + order.getPlusPrice();
			}
		}

		Map<String, Object> list = new HashMap<String, Object>();
		list.put("rows", orderList);
		list.put("price", price);
		return list;
	}
	
	@RequestMapping("/admin/sp/recycle/bill.do")
	@Permission(groupName = PermissionConstants.ADM_BILL_MANAGE, permissionID = PermissionConstants.ADM_BILL_MANAGE)
	public void listSpOrderRecycleBillForAdmin(HttpServletRequest request, HttpServletResponse response) {
		SearchVo svo = (SearchVo)parserJsonParameters(request, false, SearchVo.class);		
		responseWithDataPagnation(pos.listSpOrderRecycleBillForAdmin(svo), request, response);
	}
	
	@RequestMapping("/admin/mfc/recycle/bill.do")
	@Permission(groupName = PermissionConstants.ADM_BILL_MANAGE, permissionID = PermissionConstants.ADM_BILL_MANAGE)
	public void listMfcOrderRecycleBillForAdmin(HttpServletRequest request, HttpServletResponse response) {
		SearchVo svo = (SearchVo)parserJsonParameters(request, false, SearchVo.class);		
		responseWithDataPagnation(pos.listMfcOrderRecycleBillForAdmin(svo), request, response);
	}
	
	@RequestMapping("/admin/sp/bill/recycle.do")
	@Permission(groupName = PermissionConstants.ADM_BILL_MANAGE, permissionID = PermissionConstants.ADM_BILL_MANAGE)
	public void recycleBillOrders(HttpServletRequest request, HttpServletResponse response) {
		IDS ids = (IDS) parserJsonParameters(request, false, IDS.class);

		pos.recycleBillOrders(ids);
		responseWithData(null, request, response);
	}	
	
	@RequestMapping("/admin/mfc/bill/recycle.do")
	@Permission(groupName = PermissionConstants.ADM_BILL_MANAGE, permissionID = PermissionConstants.ADM_BILL_MANAGE)
	public void recycleBillMfcOrders(HttpServletRequest request, HttpServletResponse response) {
		IDS ids = (IDS) parserJsonParameters(request, false, IDS.class);

		pos.recycleBillMfcOrders(ids);
		responseWithData(null, request, response);
	}	
	

	@RequestMapping("/admin/sp/closed/bill.do")
	@Permission(groupName = PermissionConstants.ADM_BILL_MANAGE, permissionID = PermissionConstants.ADM_BILL_MANAGE)
	public void listSpClosedBillForAdmin(HttpServletRequest request, HttpServletResponse response) {
		SearchVo svo = (SearchVo)parserJsonParameters(request, false, SearchVo.class);	
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("totalPrice", pos.countSpBillForAdmin(svo));
		responseWithDataPagnation(pos.listSpClosedBillForAdmin(svo), results, request, response);
	}
	
	@RequestMapping("/admin/mfc/closed/billhistory.do")
	@Permission(groupName = PermissionConstants.ADM_BILL_MANAGE, permissionID = PermissionConstants.ADM_BILL_MANAGE)
	public void listMfcClosedBillHisitoryForAdmin(HttpServletRequest request, HttpServletResponse response) {
		SearchVo svo = (SearchVo)parserJsonParameters(request, false, SearchVo.class);		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("totalPrice", pos.countMfcBillForAdmin(svo));
		responseWithDataPagnation(pos.listMfcClosedBillHisitoryForAdmin(svo), results, request, response);
	}
	
	@RequestMapping("/sp/bill/confirm.do")
	@Permission(groupName = PermissionConstants.ADM_BILL_MANAGE, permissionID = PermissionConstants.ADM_BILL_MANAGE)
	public void confirmSpOrderBills(HttpServletRequest request, HttpServletResponse response) {
		IDS ids = (IDS) parserJsonParameters(request, false, IDS.class);
		Bill bill = (Bill) parserJsonParameters(request, false, Bill.class);

		pos.confirmSpOrderBills(bill, ids.getIds());
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/mfc/bill/confirm.do")
	@Permission(groupName = PermissionConstants.ADM_BILL_MANAGE, permissionID = PermissionConstants.ADM_BILL_MANAGE)
	public void confirmMfcOrderBills(HttpServletRequest request, HttpServletResponse response) {
		IDS ids = (IDS) parserJsonParameters(request, false, IDS.class);
		Bill bill = (Bill) parserJsonParameters(request, false, Bill.class);

		pos.confirmMfcOrderBills(bill, ids.getIds());
		responseWithData(null, request, response);
	}
	
	
	@RequestMapping("/user/so/confirm.do")
	public void confirmUserOrder(HttpServletRequest request, HttpServletResponse response) {
		ServiceOrder order = (ServiceOrder) parserJsonParameters(request, false, ServiceOrder.class);
		pos.confirmUserOrder(order);
		responseWithData(null, request, response);
	}
	
	
	@RequestMapping("/so/score/add.do")
	public void addServiceOrderScore(HttpServletRequest request, HttpServletResponse response) {
		ServiceScore score = (ServiceScore) parserJsonParameters(request, false, ServiceScore.class);
		pos.addServiceOrderScore(score);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/mfc/bill/export.do")
	public void exportMfcOrderBill(HttpServletRequest request, HttpServletResponse response) {

		SearchVo svo = (SearchVo) parserJsonParameters(request, true, SearchVo.class);

		responseWithData(pos.exportMfcOrderBill(request, svo), request, response);

	}
	
	@RequestMapping("/sp/bill/export.do")
	public void exportSpOrderBill(HttpServletRequest request, HttpServletResponse response) {
		SearchVo svo = (SearchVo) parserJsonParameters(request, true, SearchVo.class);
		
		responseWithData(pos.exportSpOrderBill(request, svo), request, response);

		
	}
	
	
	@RequestMapping("/so/bill/marker.do")
	public void markerOrderNeedBill(HttpServletRequest request, HttpServletResponse response) {
		ServiceOrder order = (ServiceOrder) parserJsonParameters(request, false, ServiceOrder.class);
		pos.markerOrderNeedBill(order);

		responseWithData(null, request, response);
	}
	
	@RequestMapping("/pro/bill/marker.do")
	public void markerProductOrderNeedBill(HttpServletRequest request, HttpServletResponse response) {
		ServiceOrder order = (ServiceOrder) parserJsonParameters(request, false, ServiceOrder.class);
		pos.markerMfcOrderNeedBill(order);

		responseWithData(null, request, response);
	}
	
	
	@RequestMapping("/bill/so/listso.do")
	public void listServiceOrdersByBill(HttpServletRequest request, HttpServletResponse response) {
		Bill bill = (Bill)parserJsonParameters(request, true, Bill.class);
		responseWithDataPagnation(pos.listServiceOrdersByBill(bill), request, response);
	}
	
	@RequestMapping("/pro/comments/add.do")
	public void addProductComments(HttpServletRequest request, HttpServletResponse response) {
		ProductOrder order = (ProductOrder) parserJsonParameters(request, false, ProductOrder.class);

		pos.updateProductOrderComments(order.getId(), EcThreadLocal.getCurrentUserId(), order.getComments(), "新增备注");
		responseWithData(null, request, response);
	}
	
	
	@RequestMapping("/so/comments/add.do")
	public void addServiceOrderComments(HttpServletRequest request, HttpServletResponse response) {
		ServiceOrder order = (ServiceOrder) parserJsonParameters(request, false, ServiceOrder.class);

		pos.updateServiceOrderComments(order.getId(), EcThreadLocal.getCurrentUserId(), order.getComments(), "新增备注");
		responseWithData(null, request, response);
	}
	
	
	@RequestMapping("/so/isnoticed/marker.do")
	public void markerOrderIsNoticed(HttpServletRequest request, HttpServletResponse response) {
		IDS ids = (IDS) parserJsonParameters(request, false, IDS.class);
		pos.markerOrderIsNoticed(ids);

		responseWithData(null, request, response);
	}
	
	
}
