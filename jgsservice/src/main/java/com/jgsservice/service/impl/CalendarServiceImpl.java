package com.jgsservice.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jgs.bean.EntityResults;
import com.jgs.dbhelper.DataBaseQueryBuilder;
import com.jgs.dbhelper.DataBaseQueryOpertion;
import com.jgs.exception.ResponseException;
import com.jgs.service.AbstractService;
import com.jgs.util.EcThreadLocal;
import com.jgs.util.EcUtil;
import com.jgsservice.bean.Calendar;
import com.jgsservice.bean.Category;
import com.jgsservice.bean.ServiceOrder;
import com.jgsservice.service.ICalendarService;
import com.jgsservice.util.ProductOrderStatus;

@Service(value = "calendarService")
public class CalendarServiceImpl extends AbstractService implements ICalendarService {

	private static Logger logger = LogManager.getLogger(CalendarServiceImpl.class);
	
	/**
	 * jqScheduler format return {"event_id":"11"}
	 * @param params
	 * @return
	 */
    public Map<String, Object> addScheduler(Calendar c) {
    	Map<String, Object> result = new HashMap<String, Object>();
	 	c.setId(c.getEvent_id());
    	c.setUserId(EcThreadLocal.getCurrentUserId());
	    c.setAllDay(c.getAll_day());	
	    Calendar reC = (Calendar) dao.insert(c);

	    result.put("event_id", reC.getId());	   
	    return result;
    }

    /**
     * jqScheduler format return null
     * @param c
     */
    public void editScheduler(Calendar c) {
    	c.setId(c.getEvent_id());
    	c.setUserId(EcThreadLocal.getCurrentUserId());
	    c.setAllDay(c.getAll_day());	    
	    dao.updateById(c);
    }

    /**
     * jqscheduler format return null
     * @param c
     */
    public void deleteScheduler(Calendar c) {
    	c.setId(c.getEvent_id());    	
    	dao.deleteById(c);
    }

    /**
     * jqscheduler format return {rows=[....]}
     * @param params
     * @return
     */
    public Map<String, Object> listCalendarSchedulerByUserIdAndTime(Calendar c) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Calendar.TABLE_NAME);
		builder.and(DataBaseQueryOpertion.LARGER_THAN, "start", c.getStart());
		builder.and(DataBaseQueryOpertion.LESS_THAN, "end", c.getEnd());
		String cuid = EcThreadLocal.getCurrentUserId();
		builder.and("userId", cuid);
		EntityResults<Calendar> listBean = dao.listByQueryWithPagnation(builder, Calendar.class);
		List<Calendar> entityList = listBean.getEntityList();
		for(Calendar cs : entityList){
			cs.setUser_id(cs.getUserId());
			cs.setClassName("personal"); //do not know this filed's function
		}
		
		
		//集成订单提醒日历
		Date startDate = new Date(c.getStart() * 1000l);
		Date endDate = new Date(c.getEnd() * 1000l);
		DataBaseQueryBuilder orderQuery = new DataBaseQueryBuilder(ServiceOrder.TABLE_NAME);
		orderQuery.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, ServiceOrder.EST_INSTALL_DATE, startDate);
		orderQuery.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, ServiceOrder.EST_INSTALL_DATE, endDate);
		orderQuery.and(ServiceOrder.SO_STATUS, ProductOrderStatus.ASSIGNED);
		orderQuery.and(ServiceOrder.SP_USER_ID, EcThreadLocal.getCurrentUserId());
		
		List<ServiceOrder> orderList = this.dao.listByQuery(orderQuery, ServiceOrder.class);
		if (orderList != null && orderList.size() > 0) {
			for (ServiceOrder order : orderList) {
				
				int time = 0;

				//日历控件是按秒来计算，所以只需要乘以60*60
				if (!EcUtil.isEmpty(order.getEstInstallDateTime())) {
					String[] times = order.getEstInstallDateTime().split(":");
					try {
						time = Integer.parseInt(times[0]) * 60 * 60;
					} catch (Exception e) {
						logger.error(String.format("解析安装时间出错 [%s]", order.getEstInstallDateTime()), e);
					}
				}

				if (!EcUtil.isEmpty(order.getEstInstallDateRegion())) {
					if (order.getEstInstallDateRegion().equalsIgnoreCase("all")) {
						time = 12 * 60 * 60;
					} else if (order.getEstInstallDateRegion().equalsIgnoreCase("am")) {
						time = 9 * 60 * 60;
					} else if (order.getEstInstallDateRegion().equalsIgnoreCase("pm")) {
						time = 14 * 60 * 60;
					}
				}
				System.out.println("订单提醒具体时间" + time);

				if (time == 0) {
					time = 12 * 60 * 60;
				}
				//不返回id，这样页面编辑的时候无save remove等按钮，只可以看
				Calendar orderc = new Calendar();
				orderc.setStart((long)(order.getEstInstallDate().getTime()/1000) + time);
				orderc.setEnd((long)(order.getEstInstallDate().getTime()/1000) + time);
				orderc.setClassName("personal");
				orderc.setDescription(order.getPoReceiverAddress());
				Category category = (Category) this.dao.findById(order.getCateId(), Category.TABLE_NAME, Category.class);
				String cateName = "";
				if(category !=null){
					cateName = category.getName();
				}
				
				orderc.setTitle("【安装提醒】" + order.getPoReceiverName() + "--" + order.getPoReceiverMobilePhone() + "--" + cateName);
				orderc.setAllDay(false);
				
				entityList.add(orderc);
			}
		}
		
		
		result.put("rows", entityList);
		return result;
    }
	


	@Override
    public Map<String, Object> eventCal(Calendar c) {
		Map<String, Object> result = null;
		
		if(EcUtil.isEmpty(c.getOper())){
			throw new ResponseException("事件操作不能为空");
		}
		String oper = c.getOper();
		if ("getEvent".equals(oper)){
			result = listCalendarSchedulerByUserIdAndTime(c);
		}else if ("newEvent".equals(oper)){
			result = addScheduler(c);
		}else if ("editEvent".equals(oper)){
			editScheduler(c);
		}else if ("removeEvent".equals(oper)){
			deleteScheduler(c);
		}
	    return result;
    }

}
