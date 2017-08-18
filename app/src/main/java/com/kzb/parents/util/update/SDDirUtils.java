package com.kzb.parents.util.update;

import java.io.File;

import android.content.Context;

public class SDDirUtils {

	public static String menu="/menu";
	public static String apk="/apk";
	public static String photo="/photo";
	
	public static boolean isabelExternalDirPath(Context context){
		try{
			if(context.getExternalFilesDir(null)==null){
				return false;
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}
	public static String getExternalDirPath(Context context){
		String filesPath=context.getExternalFilesDir(null).getPath();
		return filesPath.substring(0, filesPath.lastIndexOf("/"));
	}
	
	/**
	 * 获取菜单缓存路径(File)
	 * @param context
	 * @return
	 */
	public static File getExternalMenuDir(Context context){
		return new File(getExternalMenuDirPath(context));
	}
	/**
	 * 获取菜单缓存路径(String)
	 * @param context
	 * @return
	 */
	public static String getExternalMenuDirPath(Context context){
		return getExternalDirPath(context)+menu;
	}
	/**
	 * 获取apk缓存路径(File)
	 * @param context
	 * @return
	 */
	public static File getExternalApkDir(Context context){
		return new File(getExternalApkDirPath(context));
	}
	/**
	 * 获取apk缓存路径(String)
	 * @param context
	 * @return
	 */
	public static String getExternalApkDirPath(Context context){
		return getExternalDirPath(context)+apk;
	}
	
	/**
	 * 获取照片缓存路径(File)
	 * @param context
	 * @return
	 */
	public static File getExternalPhotoDir(Context context){
		return new File(getExternalMenuDirPath(context));
	}
	/**
	 * 获取照片缓存路径(String)
	 * @param context
	 * @return
	 */
	public static String getExternalPhotoDirPath(Context context){
		return getExternalDirPath(context)+photo;
	}
}
/*
File dateDir = Environment.getDataDirectory(); 
String dirName = dateDir.getAbsolutePath(); //result is: /data  
dateDir = Environment.getExternalStorageDirectory(); 
StringBuilder sbBuilder = new StringBuilder(); 
sbBuilder.append("The sdcard mounted?" + Environment.MEDIA_MOUNTED +"\n"); 
sbBuilder.append("GetDatedirectory:"+dirName+"\n"); 
sbBuilder.append("Environment.getExternalStorageDirectory:"+dateDir.getAbsolutePath()+"\n");         
sbBuilder.append("context.getExternalFilesDir:"+this.getExternalFilesDir(null)+"\n"); 
sbBuilder.append("context.getFiledir:"+this.getFilesDir().getAbsolutePath()+"\n"); 
sbBuilder.append("context.getCachedir:"+this.getCacheDir().getAbsolutePath()+"\n"); 
TextView textView = new TextView(this); 
textView.setText(sbBuilder.toString()); 

File dateDir = Environment.getDataDirectory();
String dirName = dateDir.getAbsolutePath(); //result is: /data
dateDir = Environment.getExternalStorageDirectory();
StringBuilder sbBuilder = new StringBuilder();
sbBuilder.append("The sdcard mounted?" + Environment.MEDIA_MOUNTED +"\n");
sbBuilder.append("GetDatedirectory:"+dirName+"\n");
sbBuilder.append("Environment.getExternalStorageDirectory:"+dateDir.getAbsolutePath()+"\n");  
sbBuilder.append("context.getExternalFilesDir:"+this.getExternalFilesDir(null)+"\n");
sbBuilder.append("context.getFiledir:"+this.getFilesDir().getAbsolutePath()+"\n");
sbBuilder.append("context.getCachedir:"+this.getCacheDir().getAbsolutePath()+"\n");
TextView textView = new TextView(this);
textView.setText(sbBuilder.toString());
*/