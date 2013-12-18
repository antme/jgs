package com.zcyservice.service;

import java.util.Map;

import javax.servlet.ServletContext;

import com.zcy.bean.EntityResults;
import com.zcyservice.bean.News;
import com.zcyservice.bean.vo.SearchVo;

public interface INewsService {
	
	public void addAnNew(News news, ServletContext sc);
	
	public EntityResults<News> listNews(SearchVo search);
	
	public News viewNews(News news);
	
	public void publishNews(String id);
}
