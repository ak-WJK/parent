package com.kzb.baselibrary.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.json.JSONObject;

import java.lang.Character.UnicodeBlock;

/**
 * @author Lichengan 2014.10.3
 */
@SuppressLint("DefaultLocale")
public class MineJSONHelper {
	private final static String TAG = MineJSONHelper.class.getSimpleName();

	/**
	 * JAVA对象转json
	 * 
	 * @param obj
	 *            JAVA对象
	 * @return json
	 */
	public static String objToJson(Object obj) {
		try {
			return JSON.toJSONString(obj);
		} catch (Exception e) {
			Log.e(TAG, "-objToJson-" + e.getMessage());
		}
		return null;

	}

	/**
	 * @param json
	 * @param t
	 * @return JAVA对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object jsonToObject(String json, Class t) {
		try {
			Object object = JSON.parseObject(json, t);
			return object;
		} catch (Exception e) {
			Log.e(TAG, "-jsonToObjecterror-"+e.getMessage());
		}
		return null;

	}

	/* 第一次返回值， 0 没有 1 有 */
	public static Object[] getString(String content, String key) {

		if (TextUtils.isEmpty(key) || TextUtils.isEmpty(content)) {

			// 没有
			Object[] tmpRet = { 0 };
			return tmpRet;
		}

		try {

			JSONObject object = new JSONObject(content);
			if (!object.has(key)) {

				Object[] tmpRet = { 0 };
				return tmpRet;
			}

			// 有
			String tmp = object.optString(key, "");
			if (TextUtils.isEmpty(tmp) || "null".equals(tmp)) {

				tmp = "";
			}

			Object[] tmpRet = { 1, tmp };
			return tmpRet;
		} catch (Exception e) {

			Object[] tmpRet = { 0 };
			return tmpRet;
		}
	}

	/* 第一次返回值， 0 没有 1 有 */
	public static Object[] getInt(String content, String key) {

		if (TextUtils.isEmpty(key) || TextUtils.isEmpty(content)) {

			// 没有
			Object[] tmpRet = { 0 };
			return tmpRet;
		}

		try {

			JSONObject object = new JSONObject(content);
			if (!object.has(key)) {

				// 没有
				Object[] tmpRet = { 0 };
				return tmpRet;
			}

			// 有
			Object[] tmpRet = { 1, object.optInt(key) };
			return tmpRet;
		} catch (Exception e) {

			Object[] tmpRet = { 0 };
			return tmpRet;
		}
	}

	/**
	 * unicode 转换成 utf-8
	 * 
	 * @param unicodeString
	 * @return
	 * @author lichengan 2014-10-03
	 */
	public static String unicodeToUtf8(String unicodeString) {
		if (unicodeString == null) {
			return "";
		}
		char aChar;
		int len = unicodeString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = unicodeString.charAt(x++);
			if (aChar == '\\') {
				aChar = unicodeString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = unicodeString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}

	/**
	 * utf-8 转换成 unicode
	 * 
	 * @param utf8String
	 * @return
	 * @author lichengan 2014-10-03
	 */
	public static String utf8ToUnicode(String utf8String) {
		char[] myBuffer = utf8String.toCharArray();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < utf8String.length(); i++) {
			UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);
			if (ub == UnicodeBlock.BASIC_LATIN) {
				// 英文及数字等
				sb.append(myBuffer[i]);
			} else if (ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
				// 全角半角字符
				int j = (int) myBuffer[i] - 65248;
				sb.append((char) j);
			} else {
				// 汉字
				short s = (short) myBuffer[i];
				String hexS = Integer.toHexString(s);
				String unicode = "\\u" + hexS;
				sb.append(unicode.toLowerCase());
			}
		}
		return sb.toString();
	}

	// 对象列表转jsonarray
	public static String arrayToJson(Object object) {
		String array = "";
		try {
			array = JSON.toJSONString(object);
		} catch (Exception e) {
		}
		return array;
	}
}
