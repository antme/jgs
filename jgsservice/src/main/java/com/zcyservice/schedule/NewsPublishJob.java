package com.jgsservice.schedule;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean.StatefulMethodInvokingJob;

import com.jgsservice.service.INewsService;
import com.jgsservice.service.impl.NewsServiceImpl;

public class NewsPublishJob extends StatefulMethodInvokingJob {
	
	private static Logger logger = LogManager.getLogger(NewsPublishJob.class);
	
	private INewsService newsService;

	@Override  
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException { 
		
		JobDataMap jdm = context.getJobDetail().getJobDataMap();
		String id = (String) jdm.get("newsId");
		
		newsService = (NewsServiceImpl) jdm.get("newsService");
		
		logger.info("***Start publish an news id :###" + id);
		newsService.publishNews(id);
		logger.info("***End   publish an news id :###" + id);
	}
}
