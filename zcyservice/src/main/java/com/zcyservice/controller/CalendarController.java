package com.zcyservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zcy.annotation.LoginRequired;
import com.zcy.annotation.Permission;
import com.zcy.controller.AbstractController;
import com.zcyservice.bean.Calendar;
import com.zcyservice.service.ICalendarService;

@Controller
@RequestMapping("/ecs/calendar")
@Permission()
@LoginRequired()
public class CalendarController extends AbstractController {

	@Autowired
	private ICalendarService calendarService;
	
	@RequestMapping("/eventcal.do")
	public void eventcal(HttpServletRequest request, HttpServletResponse response) {
		Calendar cal = (Calendar) parserJsonParameters(request, false, Calendar.class);
		
		responseWithData(calendarService.eventCal(cal), request, response);
	}
	

}
