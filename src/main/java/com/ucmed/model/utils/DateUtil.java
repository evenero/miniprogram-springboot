package com.ucmed.model.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	private final static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy/MM/dd");
	private final static SimpleDateFormat yyyyMMdd = new SimpleDateFormat(
			"yyyyMMdd");
	private final static SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat(
			"yyyy-MM-dd");
	private final static SimpleDateFormat week = new SimpleDateFormat(
			"EE");
	private final static SimpleDateFormat yyyymmddee = new SimpleDateFormat(
			"yyyy-MM-dd EE");
	private final static SimpleDateFormat yyyyMMddHHmm = new SimpleDateFormat(
			"yyyyMMddHHmm");
	
	private final static SimpleDateFormat HHmm = new SimpleDateFormat(
			"HHmm");

	private final static SimpleDateFormat sdf1 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	
	private final static SimpleDateFormat sdf2 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	
	private final static SimpleDateFormat sdf3 = new SimpleDateFormat(
			"HHmm");
	
	private final static SimpleDateFormat yyyyMMddhhmmssSSS = new SimpleDateFormat(
			"yyyyMMddhhmmssSSS");
	private final static SimpleDateFormat sdf15 = new SimpleDateFormat("HHmm");

	public  String simpleDate(Date date) {
		if (date == null)
			return "";
		return sdf.format(date);
	}

	public static String simpleDate1(String date1) {
		if (date1 == null)
			return "";
		return sdf.format(date1);
	}
	
	public static String simpleDate2(Date date) {
		if(yyyyMMddhhmmssSSS == null)
			return null;
		return yyyyMMddhhmmssSSS.format(date);
		
	}
	

	/**
	 * 取当前时间
	 * 
	 * @return
	 */
	public static String getCurrentTime() {

		Calendar calendar = Calendar.getInstance();

		int i = calendar.get(1);
		int j = calendar.get(2) + 1;
		int k = calendar.get(5);
		return "" + i + (j >= 10 ? "" + j : "0" + j)
				+ (k >= 10 ? "" + k : "0" + k);
	}

	public static Date calculateDate(Date startDay, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDay);
		cal.add(Calendar.DAY_OF_YEAR, days);
		return cal.getTime();
	}

	public static Date calculateMonth(Date startDay, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDay);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	/**
	 * String
	 * 获取当前时间yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurrentDateTime() {
		return dateToString4(getCurrentDate());
	}
	
	public static String getCurrentDateddHH() {
		return dateToString11(getCurrentDate());
	}
	public static String dateToString11(Date date) {
		try {
			return HHmm.format(date);
		} catch(Exception e) {}
		return null;
	}
	
	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}
	public static String getCurrentDateTime2() {
		return dateToString(getCurrentDate(), "yyyyMMdd");
	}
	public static String getCurrentDateTime3() {
		return dateToString(getCurrentDate(), "yyyy-MM-dd");
	}
	/**
	 * String
	 * 获取今天日期yyyyMMdd
	 * @return
	 */
	public static String getToday() {
		Calendar calendar = Calendar.getInstance();
		return DateUtil.getyyyyMMdd(calendar.getTime());
	}
	
	/**
	 * String
	 * 获取今天日期yyyy-MM-dd
	 * @return
	 */
	public static String getToday2() {
		Calendar calendar = Calendar.getInstance();
		return DateUtil.getyyyy_MM_dd(calendar.getTime());
	}
	/**
	 * String
	 * 获取今天日期week
	 * @return
	 */
	public static String getWeek() {
		Calendar calendar = Calendar.getInstance();
		return DateUtil.getWeek(calendar.getTime());
	}

	
	/**
	 * String
	 * 获取明天日期yyyyMMdd
	 * @return
	 */
	public static String getTomorrow() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return DateUtil.getyyyyMMdd(calendar.getTime());
	}

	
	/**
	 * String
	 * 获取几天后日期yyyyMMdd
	 * @return
	 */
	public static String getDateByDays(Integer days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return DateUtil.getyyyyMMdd(calendar.getTime());
	}
	
	/**
	 * String
	 * 获取几天后日期yyyy-MM-dd
	 * @return
	 */
	public static String getDateByDays2(Integer days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return DateUtil.getyyyy_MM_dd(calendar.getTime());
	}

	/**
	 * 将日期格式化为字符串 
	 * 
	 * @param date
	 * @return
	 */
	@Deprecated
	public static String dateToString(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(pattern).format(date);
	}
	public static String getWeek(Date date) {
		if (date == null)
			return "";
		return week.format(date);
	}
	public static String getyyyyMMdd(Date date) {
		if (date == null)
			return "";
		return yyyyMMdd.format(date);
	}
	
	public static String getyyyy_MM_dd(Date date) {
		if (date == null)
			return "";
		return yyyy_MM_dd.format(date);
	}
	public static String getyyyymmddee(Date date) {
		if (date == null)
			return "";
		return yyyymmddee.format(date);
	}
	
	public static Date getyyyy_MM_dd(String date) {
		try {
			return yyyy_MM_dd.parse(date);
		} catch (ParseException e) {
		}
		return new Date();
	}

	public static Date StringToDate(String date) {
		try {
			return yyyyMMdd.parse(date);
		} catch (ParseException e) {
		}
		return new Date();
	}

	/**
	 * String
	 * 获取时间 yyyy-MM-dd HH:mm
	 * @param date
	 * @return
	 */
	public static String format1(Date date) {
		if (date == null)
			date = new Date();
		return sdf1.format(date);
	}


	public static String dateToString1(Date date) {
		try {
			return sdf1.format(date);
		} catch(Exception e) {}
		return null;
	}

	/**
	 * Date
	 * 获取时间 yyyy-MM-dd HH:mm
	 * @param String
	 * @return Date
	 */
	public static Date StringToDate1(String date) {
		try {
			return sdf1.parse(date);
		} catch (ParseException e) {
		}
		return new Date();
	}
	
	/**
	 * Date
	 * 获取时间 yyyy/MM/dd 
	 * @param String
	 * @return Date
	 */
	public static Date StringToDate5(String date) {
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
		}
		return new Date();
	}
	


	/**
	 * Date
	 * 获取时间yyyyMMddHHmm
	 * @param String yyyyMMddHHmm
	 * @return Date
	 */
	public static Date StringToDate2(String date) {
		try {
			return yyyyMMddHHmm.parse(date);
		} catch (ParseException e) {
		}
		return new Date();
	}
	
	/**
	 * Date
	 * 获取时间yyyyMMdd
	 * @param String yyyyMMdd
	 * @return Date
	 */
	public static Date StringToDate3(String date) {
		try {
			return yyyyMMdd.parse(date);
		} catch (ParseException e) {
		}
		return new Date();
	}
	
	public static Date StringToDate4(String date) {
		try {
			return sdf2.parse(date);
		} catch (ParseException e) {
		}
		return new Date();
	}
	
	public static String dateToString4(Date date) {
		try {
			return sdf2.format(date);
		} catch(Exception e) {}
		return null;
	}
	
	public static String getHHmm(Date date) {
		try {
			return sdf3.format(date);
		} catch(Exception e) {}
		return null;
	}
	
	
	public static Integer getWeekId(String date) {
		Date d = new Date();
		try {
			d = yyyyMMdd.parse(date);
		} catch (ParseException e) {
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		Integer i =calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return i;
	}
	
	
	/**
	 * 获取周几
	 * @param date yyyyMMdd
	 * @return
	 */
	public static String getWeek(String date) {
		String week = "";
		switch (getWeekId(date)) {
		case 1:
			week = "周一";
			break;
		case 2:
			week = "周二";
			break;
		case 3:
			week = "周三";
			break;
		case 4:
			week = "周四";
			break;
		case 5:
			week = "周五";
			break;
		case 6:
			week = "周六";
			break;
		case 0:
			week = "周日";
			break;

		default:
			break;
		}
		return week;
	}
	
	/**
	 * 时间计算（日）
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date getDate(Date date, int n) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.DAY_OF_MONTH, n);
		return gc.getTime();
	}
	
	public static Date getDate(Date date, int field, int n) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(field, n);
		return gc.getTime();
	}
	
	/**
	 * 计算小时时间差
	 */
	private static final String formatStr = "HH:mm";
	private static SimpleDateFormat sdfHHmm = new SimpleDateFormat(formatStr);

	/**
	 * 当前时间大于开始时间小于结束时间
	 * @param tStart
	 * @param tEnd
	 * @param t
	 * @return
	 * @throws ParseException
	 */
	public static boolean isInZone(long tStart, long tEnd, long t)
			throws ParseException {
		return tStart < t && t <= tEnd;
	}
	
	/**
	 * 当前时间小于等于结束时间
	 * @param tStart
	 * @param tEnd
	 * @param t
	 * @return
	 * @throws ParseException
	 */
	public static boolean isInEndZone(long tEnd, long t)
			throws ParseException {
		return t <= tEnd;
	}

	public static long getLong(String timeStr) throws ParseException {
		return sdfHHmm.parse(timeStr).getTime();
	}

	public static long getCurrentTimeForHHmm() throws ParseException {
		return getLong(sdfHHmm.format(new Date()));
	}


	public static void main(String args[]) {

		System.out.print(DateUtil.dateToString1(DateUtil.StringToDate2("20130314"+"2359")));
		
	}
	
	public static String stringToDate15(Date date) {
		if (date == null)
			return "";
		return sdf15.format(date);

	}
	
	public static String getyyyy_MM_dd2(String date) throws ParseException {
		if (date == null)
			return "";
		return yyyy_MM_dd.format(yyyy_MM_dd.parse(date));
	}
}
