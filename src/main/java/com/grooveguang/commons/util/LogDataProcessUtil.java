package com.grooveguang.commons.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogDataProcessUtil {
	public static String generateLogTableName(int offset) {
		Calendar calendar=Calendar.getInstance();
		//在当前的月 份 上加上偏移量  得到对应的月份
		calendar.add(Calendar.MONTH, offset);
		
		//把日历类型转换为  日期
		Date date=calendar.getTime();
		//注意在mysql  表格中 一定要写成 _   不要写成-  这样的减号
		String  tableName="AUTO_LOG_TABLE_"+new SimpleDateFormat("yyyy_MM").format(date);
		return tableName;
	}
}
