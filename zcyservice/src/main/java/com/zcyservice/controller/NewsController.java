package com.zcyservice.controller;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zcy.annotation.LoginRequired;
import com.zcy.annotation.Permission;
import com.zcy.controller.AbstractController;
import com.zcyservice.bean.News;
import com.zcyservice.bean.vo.SearchVo;
import com.zcyservice.service.INewsService;
import com.zcyservice.util.PermissionConstants;

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
