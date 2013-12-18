package com.jgsservice.service;

import java.util.Map;

import com.jgs.bean.EntityResults;
import com.jgsservice.bean.Calendar;

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
