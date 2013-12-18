package com.zcyservice.service;

import java.util.Map;

import com.zcy.bean.EntityResults;
import com.zcyservice.bean.Calendar;

public interface ICalendarService {
	
//	public Calendar addScheduler(Calendar c);
//	
//	public void editScheduler(Calendar c);
//	
//	public void deleteScheduler(String id);
//	
//	public Map<String, Object> listCalendarSchedulerByUserIdAndTime(Map<String, Object> params);
	
	public Map<String, Object> eventCal(Calendar c);
}
