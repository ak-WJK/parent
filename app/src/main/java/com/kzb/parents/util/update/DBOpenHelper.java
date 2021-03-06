package com.kzb.parents.util.update;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

public class DBOpenHelper extends SQLiteOpenHelper {
	private static final String DBNAME = "itcast.db";
	private static final int VERSION = 1;
	
	public DBOpenHelper(Context context) {
		super(context, DBNAME, null, VERSION);
		/*if( Build.VERSION.SDK_INT >= 11){
		     getWritableDatabase().enableWriteAheadLogging();
		}*/
	}
	public DBOpenHelper(Context context,boolean enableWAL) {
		super(context, DBNAME, null, VERSION);
		if( enableWAL&&Build.VERSION.SDK_INT >= 11){
		     getWritableDatabase().enableWriteAheadLogging();
		}
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog (id integer primary key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS filedownlog");
		onCreate(db);
	}

}
