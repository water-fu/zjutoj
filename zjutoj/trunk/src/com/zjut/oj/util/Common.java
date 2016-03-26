package com.zjut.oj.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
	public static final String DATE_FORMAT_STRING = "yyyy/MM/dd HH:mm";
	public static final String DATE_TIME_FORMAT_STRING = "yyyy/MM/dd HH:mm:ss";

	/**
	 * 根据格式化的参数formatValue(例如:yyyy-MM-dd),获得对应精度的Date类型日期值
	 *
	 * @param dateValue
	 * @param formatValue
	 * @return
	 */
	public static Date getStringToDate(String dateValue,
									   java.lang.String formatValue) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatValue);
		Date newDate = null;
		try {
			newDate = formatter.parse(dateValue);
		} catch (ParseException e) {
			throw new ApplicationException("日期转换异常", e);
		}
		return newDate;
	}

	/**
	 * 获得字符串类型的当前日期
	 *
	 * @return
	 */
	public static String getCurrentDate() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		str = sdf.format(new Date());
		return str;
	}
}
