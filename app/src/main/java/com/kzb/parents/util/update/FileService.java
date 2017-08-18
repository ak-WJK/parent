package com.kzb.parents.util.update;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务bean
 */
public class FileService {
    public static String Lock = "dblock";
    private DBOpenHelper openHelper;
    /*private static FileService fileService;

	public static synchronized FileService getInstance(Context context){
		if(fileService==null){
			fileService=new FileService(context);
		}
		return fileService;
	}*/

    public FileService(Context context) {
        openHelper = new DBOpenHelper(context);
    }

    /**
     * 获取每条线程已经下载的文件长度
     *
     * @param path
     * @return
     */
    public synchronized Map<Integer, Integer> getData(String path) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select threadid, downlength from filedownlog where downpath=?", new String[]{path});
        Map<Integer, Integer> data = new HashMap<Integer, Integer>();
        while (cursor.moveToNext()) {
            data.put(cursor.getInt(0), cursor.getInt(1));
        }
        cursor.close();
        db.close();
        return data;
    }

    /**
     * 保存每条线程已经下载的文件长度
     *
     * @param path
     * @param map
     */
    public synchronized boolean save(String path, Map<Integer, Integer> map) {//int threadid, int position
        //synchronized (Lock) {

        SQLiteDatabase db = null;
        try {
            db = openHelper.getWritableDatabase();
        } catch (Exception e) {
            if(db!=null){
                db.close();
            }
            return false;
        }
        if (db == null) {
            return false;
        }
        db.beginTransaction();
        try {
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                db.execSQL("insert into filedownlog(downpath, threadid, downlength) values(?,?,?)",
                        new Object[]{path, entry.getKey(), entry.getValue()});
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }

        return true;
        //}
    }

    /**
     * 实时更新每条线程已经下载的文件长度
     *
     * @param path
     * @param map
     */
    public synchronized boolean update(String path, Map<Integer, Integer> map) {
        //synchronized (Lock) {
        SQLiteDatabase db = null;
        try {
            db = openHelper.getWritableDatabase();
        } catch (Exception e) {
            return false;
        }
        if (db == null) {
            return false;
        }
        db.beginTransaction();
        try {
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                db.execSQL("update filedownlog set downlength=? where downpath=? and threadid=?",
                        new Object[]{entry.getValue(), path, entry.getKey()});
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();
        return true;
        //}
    }

    /**
     * 当文件下载完成后，删除对应的下载记录
     *
     * @param path
     */
    public synchronized boolean delete(String path) {
        //synchronized (Lock) {
        SQLiteDatabase db = null;
        try {
            db = openHelper.getWritableDatabase();
        } catch (Exception e) {
            return false;
        }
        if (db == null) {
            return false;
        }
        db.execSQL("delete from filedownlog where downpath=?", new Object[]{path});
        db.close();
        return true;
        //}
    }

}
