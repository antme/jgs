package com.zcyservice.service;

import java.util.Calendar;
import java.util.Date;

import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcy.dbhelper.DataBaseQueryOpertion;
import com.zcy.service.AbstractService;
import com.zcy.util.DateUtil;
import com.zcy.util.EcUtil;
import com.zcyservice.bean.vo.SearchVo;

public abstract class AbstractArchiveService extends AbstractService {



	public DataBaseQueryBuilder mergeCommonSearchQuery(SearchVo search, DataBaseQueryBuilder builder, String table, String closeKey, boolean defaultQuery) {
		DataBaseQueryBuilder searchBuilder = new DataBaseQueryBuilder(table);
		

		

		if (!EcUtil.isEmpty(search.getStartDate())) {
			searchBuilder.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, closeKey, search.getStartDate());
			defaultQuery = false;
		}

		if (!EcUtil.isEmpty(search.getEndDate())) {
			Date endDate = getQueryEndDate(search);

			searchBuilder.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, closeKey, endDate);
			defaultQuery = false;
		}
		
		if(defaultQuery && EcUtil.isEmpty(search.getDateType())){
			search.setDateType("0");
		}
		if (!EcUtil.isEmpty(search.getDateType())) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			if (search.getDateType().equalsIgnoreCase("0")) {
				c.set(Calendar.DAY_OF_MONTH, 1);
				searchBuilder.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, closeKey, new Date());
				searchBuilder.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, closeKey, c.getTime());

			} else if (search.getDateType().equalsIgnoreCase("1")) {
				c.add(Calendar.MONTH, -1);
				c.set(Calendar.DAY_OF_MONTH, 1);

				searchBuilder.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, closeKey, c.getTime());
				c.set(Calendar.DAY_OF_MONTH, c.getMaximum(Calendar.DAY_OF_MONTH) + 1);
				searchBuilder.and(DataBaseQueryOpertion.LESS_THAN, closeKey, c.getTime());

			} else if (search.getDateType().equalsIgnoreCase("2")) {
				c.add(Calendar.MONTH, -3);		
				c.set(Calendar.DAY_OF_MONTH, 1);
				searchBuilder.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, closeKey, c.getTime());
				
				c.add(Calendar.MONTH, 2);		
				c.set(Calendar.DAY_OF_MONTH, c.getMaximum(Calendar.DAY_OF_MONTH) + 1);
				searchBuilder.and(DataBaseQueryOpertion.LESS_THAN, closeKey, c.getTime());
			}

		}

		if (!EcUtil.isEmpty(searchBuilder.getQueryStr())) {
			builder = builder.and(searchBuilder);
		}
		return builder;
	}
	
	

	public Date getQueryEndDate(SearchVo search) {
	    Date endDate = search.getEndDate();
	    if(DateUtil.getDateStringTime(endDate).endsWith("00:00:00")){
	    	endDate = new Date(endDate.getTime() + 86400000L - 1L);
	    }
	    return endDate;
    }
	




}
