package com.jgsservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jgs.annotation.LoginRequired;
import com.jgs.annotation.Permission;
import com.jgs.controller.AbstractController;
import com.jgsservice.bean.Calendar;
import com.jgsservice.service.ICalendarService;

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
