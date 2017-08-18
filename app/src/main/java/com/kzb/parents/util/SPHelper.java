package com.kzb.parents.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.UserManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/********************
 * 作者：malus 日期：15/11/23 时间：上午11:26 注释：sharePrefresh帮助类
 ********************/
public class SPHelper {
	private static SPHelper spHelper;
	private Context context;
	private static SharedPreferences sp;

	private SPHelper(Context context) {
		this.context = context;
		sp = context.getSharedPreferences("kaozhibao_share_p", 0);
	}

	public static void init(Context context) {
		spHelper = new SPHelper(context);
	}




	/**
	 * 已经做过的题目
	 * 
	 * @return
	 */
	public static String getHasDoQuestion() {
		return sp.getString("has_do_question", "");
	}

	public static void setHasDoQuestion(String doneQuestion) {
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("has_do_question", doneQuestion);
		editor.apply();
	}

}
