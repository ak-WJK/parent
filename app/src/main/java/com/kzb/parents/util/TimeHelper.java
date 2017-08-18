package com.kzb.parents.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 与时间相关的操作
 */
public class TimeHelper {
	private static Context mContext;
	private Calendar calendar;
	private static TimeHelper timeHelper;
	private static SharedPreferences sp;

	private TimeHelper(Context context) {
		mContext = context;
		sp = context.getSharedPreferences("mama", Context.MODE_PRIVATE);

	}

	public static void initTimeHelper(Context context) {
		timeHelper = new TimeHelper(context);
	}

	public static TimeHelper getInstance() {
		if (mContext == null) {
			throw new IllegalArgumentException(
					"must init timeHelper before use");
		}
		return timeHelper;
	}

	/**
	 * 返回当前毫秒数
	 * 
	 * @return
	 */
	public long getCurrentMillisTime() {
		calendar = Calendar.getInstance();
		long timeInMillis = calendar.getTimeInMillis();
		return timeInMillis;
	}

	/**
	 * 返回格式化的当前时间
	 * 
	 * @return 返回的时间格式为 2015-03-22
	 */
	public String getCurrentData() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(getCurrentMillisTime());
		return format.format(date);
	}

	/**
	 * 返回格式化的当前时间 params time 单位秒
	 * 
	 * @return 返回的时间格式为 2015-03-22 12:32:23
	 */
	public String getDataTime(String time) {
		Long sTime = Long.parseLong(time);
		sTime = sTime * 1000;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(sTime);
		return format.format(date);
	}




	public static String getDataTimeVal(String time) {
		Long sTime = Long.parseLong(time);
		sTime = sTime * 1000;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(sTime);
		return format.format(date);
	}


	public static String getDataTimeType(String time) {
		Long sTime = Long.parseLong(time);
		sTime = sTime * 1000;
		SimpleDateFormat format = new SimpleDateFormat("MM-dd");
		Date date = new Date(sTime);
		return format.format(date);
	}

	/**
	 * 获取时间
	 *
	 * @param count 如 5400
	 * @return 如 90:00
	 */
	public static String getTime(int count) {
		StringBuilder sb = new StringBuilder();
		if (count > 60) {
			int min = count / 60;
			if (min < 10) {
				sb.append("0" + min + ":");
			}else{
				sb.append(min + ":");
			}
			int sec = count % 60;
			if (sec < 10) {
				sb.append("0" + sec);
			}else{
				sb.append(sec);
			}
		} else {
			if (count < 10) {
				sb.append("00:0" + count);
			} else {
				sb.append("00:" + count);
			}
		}
		return sb.toString();
	}

	/**
	 * 返回格式化的当前时间 params time 单位秒
	 * 
	 * @return 返回的时间格式为 2015年03月22日
	 */
	public String getData(String time) {
		Long sTime = Long.parseLong(time);
		sTime = sTime * 1000;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(sTime);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String data = year + "年" + month + "月" + day + "日";
		return data;
	}

	/**
	 * 获取时间戳，但是是秒
	 * 
	 * @return 返回的时间格式为 148756463
	 */
	public long getMillSecond(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		return calendar.getTimeInMillis() / 1000;
	}

	/**
	 * 返回格式化的当前时间
	 * 
	 * @return 返回的时间格式为 2015-03-22
	 */
	public String getCurrentTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(getCurrentMillisTime());
		return format.format(date);
	}

	// ====================底下是存储上一次刷新的时间，和获取刷新时间===========
	// ...................圈子列表最新帖子.........................
	/**
	 * 设置圈子列表最新发帖的时间
	 */
	public void setRefreshRingBbsTime() {
		if (sp == null) {
			throw new IllegalArgumentException(
					"you must initSPHelper before use it");
		}
		SharedPreferences.Editor editor = sp.edit();
		editor.putLong("refreshRingBbsTime", getCurrentMillisTime());
		editor.commit();
	}

	/**
	 * 获取圈子列表最近帖子的上次刷新时间
	 * 
	 * @return
	 */
	public String getRefreshRingBbsTime() {
		long refreshRingBbsTime = sp.getLong("refreshRingBbsTime",
				getCurrentMillisTime());
		long time = getCurrentMillisTime() - refreshRingBbsTime;
		return getMillTimeToString(time);
	}

	// ........................圈子列表最近回复...............................
	/**
	 * 设置圈子列表最新回复的时间
	 */
	public void setRefreshRingReplyTime() {
		if (sp == null) {
			throw new IllegalArgumentException(
					"you must initSPHelper before use it");
		}
		SharedPreferences.Editor editor = sp.edit();
		editor.putLong("refreshRingReplyTime", getCurrentMillisTime());
		editor.commit();
	}

	/**
	 * 获取圈子列表最近回复的上次刷新时间
	 * 
	 * @return
	 */
	public String getRefreshRingReplyTime() {
		long refreshRingReplyTime = sp.getLong("refreshRingReplyTime",
				getCurrentMillisTime());
		long time = getCurrentMillisTime() - refreshRingReplyTime;
		return getMillTimeToString(time);
	}

	/**
	 * 利用毫秒数来获取中文时间，如1天2小时34分
	 * 
	 * @return
	 */
	private String getMillTimeToString(long time) {
		if (time < 60000) {
			return "刚刚更新";
		}
		long minute = time / 60000;
		if (minute < 60) {
			return minute + "分钟前更新";
		}
		long hour = minute / 60;
		if (hour < 24) {
			return hour + "小时" + (minute % 60) + "分钟前更新";
		}
		long day = hour / 24;
		return day + "天" + (hour % 24) + "小时前更新";
	}

	/**
	 * 将秒数转换成如5月15日 9:30的时间
	 * 
	 * @param time
	 * @return
	 */
	public String getCreateTime(String time) {
		StringBuffer sb = new StringBuffer();
		Calendar calendar = Calendar.getInstance();
		Long mill = Long.parseLong(time);
		calendar.setTimeInMillis(mill * 1000);
		sb.append(calendar.get(Calendar.MONTH) + 1);
		sb.append("月");
		sb.append(calendar.get(Calendar.DAY_OF_MONTH));
		sb.append("日");
		sb.append("  ");
		sb.append(calendar.get(Calendar.HOUR_OF_DAY));
		sb.append(":");
		if (calendar.get(Calendar.MINUTE) < 10) {
			sb.append("0" + calendar.get(Calendar.MINUTE));
		} else {
			sb.append(calendar.get(Calendar.MINUTE));
		}
		return sb.toString();
	}

	/**
	 * 将秒数转换成日期 格式如 05.05
	 * 
	 * @param time
	 * @return
	 */
	public String getDotDate(String time) {
		StringBuffer sb = new StringBuffer();
		Calendar calendar = Calendar.getInstance();
		Long mill = Long.parseLong(time);
		calendar.setTimeInMillis(mill * 1000);
		int month = calendar.get(Calendar.MONTH) + 1;
		if (month < 10) {
			sb.append("0" + month);
		} else {
			sb.append(month);
		}
		sb.append(".");
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day < 10) {
			sb.append("0" + day);
		} else {
			sb.append(day);
		}
		return sb.toString();
	}

	/**
	 * 将秒数转换成年 格式如 2015
	 * 
	 * @param time
	 * @return
	 */
	public String getYear(String time) {
		StringBuffer sb = new StringBuffer();
		Calendar calendar = Calendar.getInstance();
		Long mill = Long.parseLong(time);
		calendar.setTimeInMillis(mill * 1000);
		return "" + calendar.get(Calendar.YEAR);
	}

	/**
	 * 距离现在时间
	 * 
	 * @param time
	 * @return
	 */
	public String getGoTime(String time) {
		StringBuffer sb = new StringBuffer();
		Calendar calendar = Calendar.getInstance();
		long current = calendar.getTimeInMillis();
		long mill = Long.parseLong(time) * 1000;

		long goTime = current - mill;
		// 以天为单位
		long day = 24 * 60 * 60 * 1000;
		// 以小时为单位
		long hour = 60 * 60 * 1000;
		// 以分钟为单位
		long minute = 60 * 1000;
		if (goTime > day) {
			sb.append(goTime / day + "天前");
		} else if (goTime > hour) {
			sb.append(goTime / hour + "小时前");
		} else if (goTime > minute) {
			sb.append(goTime / minute + "分钟前");
		} else {
			sb.append("刚刚");
		}
		return sb.toString();
	}

	/**
	 * 利用毫秒数来获取距离现在时间 如 1小时 3天 2年 等
	 * 
	 * @return
	 */
	public String getHowLong(long time) {
		Calendar c = Calendar.getInstance();
		long current = c.getTimeInMillis();
		time = current - time * 1000;

		if (time < 60000) {
			return time / 1000 + "秒";
		}
		long minute = time / 60000;
		if (minute < 60) {
			return minute + "分钟";
		}
		long hour = minute / 60;
		if (hour < 24) {
			return hour + "小时";
		}
		long day = hour / 24;
		return day + "天";
	}
}
