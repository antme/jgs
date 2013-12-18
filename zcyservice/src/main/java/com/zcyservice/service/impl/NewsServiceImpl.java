package com.zcyservice.service.impl;


import java.util.Date;

import javax.servlet.ServletContext;

import org.quartz.JobDataMap;
import org.springframework.stereotype.Service;

import com.zcy.bean.EntityResults;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcy.dbhelper.DataBaseQueryOpertion;
import com.zcy.service.AbstractService;
import com.zcy.util.DateUtil;
import com.zcy.util.EcUtil;
import com.zcyservice.bean.News;
import com.zcyservice.bean.vo.SearchVo;
import com.zcyservice.schedule.CustomJob;
import com.zcyservice.schedule.NewsPublishJob;
import com.zcyservice.schedule.QuartzHandler;
import com.zcyservice.schedule.QuartzUtil;
import com.zcyservice.service.INewsService;
import com.zcyservice.util.NewsStatus;

@Service(value = "newsService")
public class NewsServiceImpl extends AbstractService implements INewsService {

	@Override
	public void addAnNew(News news, ServletContext sc) {
		String pt = (String) news.getPublishTime();
		Date now = new Date();
		Date ptDate = DateUtil.getDateTime(pt);
		if (ptDate.before(now)){
			news.setStatus(NewsStatus.PUBLISHED.toString());
		}else{
			news.setStatus(NewsStatus.PRE_PUBLISH.toString());
		}
		News n = (News) dao.insert(news);
		String nId = n.getId();
		
		if(NewsStatus.PRE_PUBLISH.toString().equalsIgnoreCase(n.getStatus())){
			CustomJob job = new CustomJob();  
	        job.setJobId(nId);  
	        job.setJobGroup("news_group");  
	        job.setJobName("News publist job name");  
	        job.setMemos("News publish job memo");  
	        job.setCronExpression(QuartzUtil.genCronExpression(pt)); 
//	        job.setCronExpression("0/5 * * * * ?");//每五秒执行一次  
	        job.setStateFulljobExecuteClass(NewsPublishJob.class);  
	          
	        JobDataMap paramsMap = new JobDataMap(); 
	        paramsMap.put("newsId", nId);
	        paramsMap.put("newsService", this);
	        QuartzHandler qh = new QuartzHandler(sc);
	        qh.enableCronSchedule(job, paramsMap, true);
		}
		
	}

	@Override
	public EntityResults<News> listNews(SearchVo search) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(News.TABLE_NAME);
		builder.and(News.STATUS, NewsStatus.PUBLISHED.toString());
		builder.and(DataBaseQueryOpertion.LARGER_THAN, News.EXPIRED_TIME, new Date());
		
		String keyword = search.getKeyword();
		if (!EcUtil.isEmpty(keyword)){
			builder.and(DataBaseQueryOpertion.LIKE, News.TITLE, keyword);
		}
		
		return dao.listByQueryWithPagnation(builder, News.class);
	}

	@Override
	public News viewNews(News news) {
		return (News) dao.findById(news.getId(), News.TABLE_NAME, News.class);
	}

	@Override
    public void publishNews(String id) {
	    News news = new News();
	    news.setId(id);
	    news.setStatus(NewsStatus.PUBLISHED.toString());
	    dao.updateById(news);
    }

}
