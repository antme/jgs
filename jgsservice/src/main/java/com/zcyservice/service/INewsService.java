package com.jgsservice.service;

import java.util.Map;

import javax.servlet.ServletContext;

import com.jgs.bean.EntityResults;
import com.jgsservice.bean.News;
import com.jgsservice.bean.vo.SearchVo;

public interface INewsService {
	
	public void addAnNew(News news, ServletContext sc);
	
	public EntityResults<News> listNews(SearchVo search);
	
	public News viewNews(News news);
	
	public void publishNews(String id);
}
