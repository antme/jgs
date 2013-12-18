package com.jgsservice.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class QuartzUtil {
	
	public static String genCronExpression(String dateTimeString){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = null;
		try {
	        Date date = sdf.parse(dateTimeString);
	        Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			// 当前年  
		    int year = cal.get(Calendar.YEAR);  
		    // 当前月  
		    int month = (cal.get(Calendar.MONTH)) + 1;  
		    // 当前月的第几天：即当前日  
		    int day_of_month = cal.get(Calendar.DAY_OF_MONTH);  
		    // 当前时：HOUR_OF_DAY-24小时制；HOUR-12小时制  
		    int hour = cal.get(Calendar.HOUR_OF_DAY);  
		    // 当前分  
		    int minute = cal.get(Calendar.MINUTE);  
		    // 当前秒  
		    int second = cal.get(Calendar.SECOND);
		    result = genCronExpression(year, month, day_of_month, hour, minute, second);
        } catch (ParseException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		return result;
	}
	
	public static String genCronExpression(int year, int mon, int day, int hour, int min, int sec){
		//sec min hour day month ? year
		String secS = String.valueOf(sec);
		return new StringBuffer(secS).append(" ").append(min).append(" ").append(hour).append(" ")
				.append(day).append(" ").append(mon).append(" ").append("? ")
				.append(year).toString();
	}
	
	public static String genCronExpression(String y, String m, String d, String h, String min, String sec){
		//sec min hour day month ? year
		return new StringBuffer(sec).append(" ").append(min).append(" ").append(h)
				.append(" ").append(d).append(" ")
				.append(m).append(" ").append("? ").append(y).toString();
	}
}
