package com.jgsservice.controller;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jgs.annotation.LoginRequired;
import com.jgs.annotation.Permission;
import com.jgs.controller.AbstractController;
import com.jgsservice.bean.News;
import com.jgsservice.bean.vo.SearchVo;
import com.jgsservice.service.INewsService;
import com.jgsservice.util.PermissionConstants;

@Controller
@RequestMapping("/ecs/news")
@Permission()
@LoginRequired()
public class NewsController extends AbstractController {

	@Autowired
	private INewsService newsService;

	@RequestMapping("/add.do")
	@Permission(groupName = PermissionConstants.ADM_NEWS_MANAGE, permissionID = PermissionConstants.ADM_NEWS_MANAGE)
	public void addNews(HttpServletRequest request, HttpServletResponse response) {
		News news = (News) parserJsonParameters(request, false, News.class);
		ServletContext sc =  request.getSession().getServletContext();
		newsService.addAnNew(news,sc);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/list.do")
	public void listNews(HttpServletRequest request, HttpServletResponse response) {
		SearchVo search = (SearchVo)parserJsonParameters(request, true, SearchVo.class);
		responseWithDataPagnation(newsService.listNews(search), request, response);
	}
	
	@RequestMapping("/detail.do")
	public void loadNewsDetail(HttpServletRequest request, HttpServletResponse response) {
		News news = (News) parserJsonParameters(request, false, News.class);
		responseWithEntity(newsService.viewNews(news), request, response);
	}
	
	@RequestMapping("/get.do")
	public void getNews(HttpServletRequest request, HttpServletResponse response) {
		News news = (News) parserJsonParameters(request, false, News.class);
		responseWithEntity(newsService.viewNews(news), request, response);
	}

}
