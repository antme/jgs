package com.jgsservice.bean;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.jgs.bean.BaseEntity;

@Table(name = Calendar.TABLE_NAME)
public class Calendar extends BaseEntity{
	
	public static final String TABLE_NAME = "Calendar";
	
	public static final String USER_ID = "userId";
	public static final String START = "start";
	public static final String END = "end";
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";
	public static final String LOCATION = "location";
	public static final String CATEGORIES = "categories";
	public static final String ACCESS = "access";
	public static final String ALLDAY = "allDay";
	
	@Column(name = USER_ID)
//	@Expose
	public String userId;

	@Column(name = START)
	@Expose
	public Long start;

	@Column(name = END)
	@Expose
	public Long end;

	@Column(name = TITLE, unique = true)
	@Expose
	public String title;

	@Column(name = DESCRIPTION)
	@Expose
	public String description;

	/*@Column(name = LOCATION)
	@Expose
	public String location;

	@Column(name = CATEGORIES)
	@Expose
	public String categories;

	@Column(name = ACCESS)
	@Expose
	public String access;*/

	@Column(name = ALLDAY)
	@Expose
	public Boolean allDay;
	
	@Transient
	@Expose
	public String user_id;
	
	@Transient
	@Expose
	public String className;
	
	//页面传递过来的事件，不需要存数据库
	@Expose
	public String oper;
	
	//页面传递过来的事件ID，对应数据库ID,不需要存数据库
	@Expose
	public String event_id;
	
	//页面传递过来的是否全天，对应数据库allDay,不需要存数据库
	@Expose
	public Boolean all_day;
	
	
	
	public Boolean getAll_day() {
    	return all_day;
    }

	public void setAll_day(Boolean all_day) {
    	this.all_day = all_day;
    }

	public String getEvent_id() {
    	return event_id;
    }

	public void setEvent_id(String event_id) {
    	this.event_id = event_id;
    }

	public String getOper() {
    	return oper;
    }

	public void setOper(String oper) {
    	this.oper = oper;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getAllDay() {
		return allDay;
	}

	public void setAllDay(Boolean allDay) {
		this.allDay = allDay;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
}
