package com.kzb.parents.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class DeviceUtil {

	// 获取是否存在NavigationBar
	public static boolean checkDeviceHasNavigationBar(Context context) {
		boolean hasNavigationBar = false;
		Resources rs = context.getResources();
		int id = rs
				.getIdentifier("config_showNavigationBar", "bool", "android");
		if (id > 0) {
			hasNavigationBar = rs.getBoolean(id);
		}
		try {
			Class systemPropertiesClass = Class
					.forName("android.os.SystemProperties");
			Method m = systemPropertiesClass.getMethod("get", String.class);
			String navBarOverride = (String) m.invoke(systemPropertiesClass,
					"qemu.hw.mainkeys");
			if ("1".equals(navBarOverride)) {
				hasNavigationBar = false;
			} else if ("0".equals(navBarOverride)) {
				hasNavigationBar = true;
			}
		} catch (Exception e) {

		}
		return hasNavigationBar;
	}

	/**
	 * 我们约定 uuid一定是44位
	 * 
	 * @param context
	 * @return
	 */
	public static String getUUid(Context context) {
			String deviceUid = getDeviceUUid(context);
			if (deviceUid.length() > 44) {
				deviceUid.substring(0, 44);
			} else if (deviceUid.length() < 44) {
				int i = deviceUid.length();
				for (; i < 44; i++) {
					deviceUid = deviceUid + "0";
				}
			}
		if (TextUtils.isEmpty(deviceUid)) {
			deviceUid = "";
		}
		return "Android-" + deviceUid;
	}

	private static String getDeviceUUid(Context context) {
		String PREFS_FILE = "device_id.xml";
		String PREFS_DEVICE_ID = "device_id";
		UUID uuid = null;
		if (uuid == null) {
			final SharedPreferences prefs = context.getSharedPreferences(
					PREFS_FILE, 0);
			final String id = prefs.getString(PREFS_DEVICE_ID, null);
			if (id != null) {
				uuid = UUID.fromString(id);
			} else {
				final String androidId = Secure.getString(
						context.getContentResolver(), Secure.ANDROID_ID);
				// Use the Android ID unless it's broken, in which
				// casefallbackon deviceId,
				// unless it's not available, thenfallbackon a
				// random number which we store
				// to aprefsfile
				try {
					if (!"9774d56d682e549c".equals(androidId)) {
						uuid = UUID.nameUUIDFromBytes(androidId
								.getBytes("utf8"));
					} else {
						final String deviceId = ((TelephonyManager) context
								.getSystemService(Context.TELEPHONY_SERVICE))
								.getDeviceId();
						uuid = deviceId != null ? UUID
								.nameUUIDFromBytes(deviceId.getBytes("utf8"))
								: UUID.randomUUID();
					}
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
				// Write the value out to theprefsfile
				prefs.edit().putString(PREFS_DEVICE_ID, uuid.toString())
						.commit();
			}
		}
		return uuid.toString();
	}

	// 获取本地IP地址
	public static String getLocationIp(Context context) {
		String locationIp = "";
		if (isNetConnected(context)) {
			if (isWifiConnected(context)) {
				locationIp = getWIFILocalIpAdress(context);
			} else if (isMobileConnected(context)) {
				locationIp = getGPRSLocalIpAddress();
			}
		}
		if (TextUtils.isEmpty(locationIp)) {
			locationIp = "";
		}
		return locationIp;
	}

	/**
	 * 检测网络是否连接
	 * 
	 * @return
	 */
	public static boolean isNetConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo[] infos = cm.getAllNetworkInfo();
			if (infos != null) {
				for (NetworkInfo ni : infos) {
					if (ni.isConnected()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 检测wifi是否连接
	 * 
	 * @return
	 */
	private static boolean isWifiConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if (networkInfo != null
					&& networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检测3G是否连接
	 * 
	 * @return
	 */
	private static boolean isMobileConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if (networkInfo != null
					&& networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
				return true;
			}
		}
		return false;
	}

	/*
	 * 使用WIFI时，获取本机IP地址
	 * 
	 * @param mContext
	 * 
	 * @return
	 */
	public static String getWIFILocalIpAdress(Context context) {

		// 获取wifi服务
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		// 判断wifi是否开启
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		String ip = formatIpAddress(ipAddress);
		return ip;
	}

	private static String formatIpAddress(int ipAdress) {
		return (ipAdress & 0xFF) + "." + ((ipAdress >> 8) & 0xFF) + "."
				+ ((ipAdress >> 16) & 0xFF) + "." + (ipAdress >> 24 & 0xFF);
	}

	/**
	 * 使用GPRS时，获取本机IP地址
	 * 
	 * @return
	 */
	public static String getGPRSLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String getWidthAndHeight(Activity activity) {
		String widthHeight = activity.getWindowManager().getDefaultDisplay()
				.getWidth()
				+ "*"
				+ activity.getWindowManager().getDefaultDisplay().getHeight();
		return widthHeight;
	}
	/**
	 * 检查手机是否安装了指定包名的app
	 * @param context
	 * @param packageName 包名
	 * @return
	 */
	public static boolean isInstalledApp(Context context,String packageName){
		//获取packageManager
		final PackageManager packageManager = context.getPackageManager();
		// 获取所有已安装程序的包信息
		List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
		// 用于存储所有已安装程序的包名
		List<String> packageNames = new ArrayList<>();
		if(packageInfos!=null){
			//将包信息转换成包名，一一加入数组
			for (PackageInfo info:packageInfos) {
				packageNames.add(info.packageName);
			}
		}
		//返回是否存在该包名，如果存在，则证明安装，返回true。否则未安装，返回false。
		return packageNames.contains(packageName);
	}

	/**
	 * 当前版本号
	 * @param ctx
	 * @return
     */
	public static int getCurVersionCode(Context ctx) {
		try {
			PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(
					ctx.getPackageName(), 0);
			return pInfo.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			return -1;
		}
	}

	/**
	 * 当前版本名称
	 * @param ctx
	 * @return
     */
	public static String getCurVersionName(Context ctx) {
		try {
			PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(
					ctx.getPackageName(), 0);
			return pInfo.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			return null;
		}
	}


}
