package com.onecoderspace.base.util;

import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

public class DateUtils {

	public final static String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";
	public final static String DATE_FORMAT_TIME = "yyyy-MM-dd HH:mm";
	public final static String DATE_FORMAT_ALL = "yyyy-MM-dd HH:mm:ss";
	public final static String DATE_CHINA_DEFAULT = "yyyy年MM月dd日";

	/**
	 * Get the date of num days before and after the specified date
	 * @author yangwk
	 * @time September 14, 2017 11:13:18 AM
	 * @param date
	 * @param num positive number how many days after the date negative number how many days later
	 * @return
	 */
	public static String getDay(String date,int num){
		return getDay(date, num,DATE_FORMAT_DEFAULT);
	}

	/**
	 * Get the date of num days before and after the specified date
	 * @author yangwk
	 * @time September 14, 2017 11:13:18 AM
	 * @param date
	 * @param num positive number how many days after the date negative number how many days later
	 * @param format date format
	 * @return
	 */
	public static String getDay(String date,int num,String format){
		long t = parseStringToLong(date);
		return getDay(t, num, DATE_FORMAT_DEFAULT);
	}

	/**
	 * Get the date of num days before and after the specified date
	 * @author yangwk
	 * @time September 14, 2017 11:13:18 AM
	 * @param date
	 * @param num positive number how many days after the date negative number how many days later
	 * @return
	 */
	public static String getDay(long date,int num){
		return getDay(date, num, DATE_FORMAT_DEFAULT);
	}

	/**
	 * Get the date of num days before and after the specified date
	 * @author yangwk
	 * @time September 14, 2017 11:13:18 AM
	 * @param date
	 * @param num positive number how many days after the date negative number how many days later
	 * @param format date format
	 * @return
	 */
	public static String getDay(long date,int num,String format){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+num);
		return longToString(calendar.getTimeInMillis(),format);
	}

	/**
	 * Convert the millisecond time to the time in yyyy-MM-dd format
	 * @author yangwenkui
	 * @time October 6, 2017 5:56:40 PM
	 * @param time milliseconds
	 * @return
	 */
	public static String longToString(long time) {
		return longToString(time, DATE_FORMAT_DEFAULT);
	}

	/**
	 * Convert the millisecond time to the specified format
	 * @author yangwenkui
	 * @time October 6, 2017 5:56:40 PM
	 * @param time milliseconds
	 * @param format date format
	 * @return
	 */
	public static String longToString(long time, String format) {
		if (StringUtils.isBlank(format)) {
			format = DATE_FORMAT_DEFAULT;
		}
		DateTime dTime = new DateTime(time);
		return dTime.toString(format);
	}

	/**
	 * Get the time to start today
	 * @author yangwenkui
	 * @time October 6, 2017 5:58:18 PM
	 * @return
	 */
	public static Timestamp getTodayStartTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 001);
		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * Get the start time of the day starting on the specified date
	 * @author yangwenkui
	 * @time October 6, 2017 5:58:33 PM
	 * @param date
	 * @return
	 */
	public static long getDayStartTime(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(parseStringToLong(date));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 001);
		return cal.getTimeInMillis();
	}

	/**
	 * Get the specified date end time
	 * @author yangwenkui
	 * @time October 6, 2017 5:58:58 PM
	 * @param date
	 * @return
	 */
	public static long getDayEndTime(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(parseStringToLong(date));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTimeInMillis();
	}

	/**
	 * Get current date
	 */
	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd");
	}

	/**
	 * Get current time
	 * @param format date format
	 * @return
	 */
	public static String getCurrentTime(String format) {
		DateTime dTime = new DateTime();
		return dTime.toString(format);
	}

	/**
	 * Convert date of string type to milliseconds
	 * @author yangwenkui
	 * @time October 6, 2017 6:00:27 PM
	 * @param dateStr
	 * @return
	 */
	public static long parseStringToLong(String dateStr) {
		dateStr = dateStr.trim();
		if (dateStr.length() == 19 || dateStr.length() == 23) {
			try {
				java.util.Calendar cal = java.util.Calendar.getInstance();
				cal.set(Integer.parseInt(dateStr.substring(0, 4)),
						Integer.parseInt(dateStr.substring(5, 7)) - 1,
						Integer.parseInt(dateStr.substring(8, 10)),
						Integer.parseInt(dateStr.substring(11, 13)),
						Integer.parseInt(dateStr.substring(14, 16)),
						Integer.parseInt(dateStr.substring(17, 19)));
				cal.set(java.util.Calendar.MILLISECOND, 0);
				return (cal.getTime().getTime());
			} catch (Exception e) {
				return 0;
			}

		} else if (dateStr.length() == 16) {
			try {
				java.util.Calendar cal = java.util.Calendar.getInstance();
				cal.set(Integer.parseInt(dateStr.substring(0, 4)),
						Integer.parseInt(dateStr.substring(5, 7)) - 1,
						Integer.parseInt(dateStr.substring(8, 10)),
						Integer.parseInt(dateStr.substring(11, 13)),
						Integer.parseInt(dateStr.substring(14, 16)));
				cal.set(java.util.Calendar.MILLISECOND, 0);
				return (cal.getTime().getTime());
			} catch (Exception e) {
				return 0;
			}

		} else if (dateStr.length() == 14) {
			try {
				java.util.Calendar cal = java.util.Calendar.getInstance();
				cal.set(Integer.parseInt(dateStr.substring(0, 4)),
						Integer.parseInt(dateStr.substring(4, 6)) - 1,
						Integer.parseInt(dateStr.substring(6, 8)),
						Integer.parseInt(dateStr.substring(8, 10)),
						Integer.parseInt(dateStr.substring(10, 12)),
						Integer.parseInt(dateStr.substring(12, 14)));
				cal.set(java.util.Calendar.MILLISECOND, 0);
				return (cal.getTime().getTime());
			} catch (Exception e) {
				return 0;
			}
		} else if (dateStr.length() == 10 || dateStr.length() == 11) {
			try {
				java.util.Calendar cal = java.util.Calendar.getInstance();
				cal.set(Integer.parseInt(dateStr.substring(0, 4)),
						Integer.parseInt(dateStr.substring(5, 7)) - 1,
						Integer.parseInt(dateStr.substring(8, 10)), 0, 0, 0);
				cal.set(java.util.Calendar.MILLISECOND, 0);
				return (cal.getTime().getTime());
			} catch (Exception e) {
				return 0;
			}
		} else if (dateStr.length() == 8 ) {
			try {
				java.util.Calendar cal = java.util.Calendar.getInstance();
				cal.set(Integer.parseInt(dateStr.substring(0, 4)),
						Integer.parseInt(dateStr.substring(4, 6)) - 1,
						Integer.parseInt(dateStr.substring(6, 8)), 0, 0, 0);
				cal.set(java.util.Calendar.MILLISECOND, 0);
				return (cal.getTime().getTime());
			} catch (Exception e) {
				return 0;
			}
		} else {
			try {
				return Long.parseLong(dateStr);
			} catch (Exception e) {
				return 0;
			}

		}
	}

	public static void main(String[] args) {
		System.out.println(getDay(System.currentTimeMillis(), -30));
	}
}