package com.kzb.parents.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

	private static AtomicInteger counter = new AtomicInteger(0);

	/*
	 * 分割包含点号的字符串,并获得第二个
	 */
	public static String splitBySpot(String str) {
		if (str == null)
			return str;
		String[] cardArray = str.split("[.]");
		if (cardArray != null && cardArray.length > 1) {
			str = cardArray[1];
		}
		return str;
	}

	public static int getCurVersionCode(Context ctx) {
		try {
			PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(
					ctx.getPackageName(), 0);
			return pInfo.versionCode;
		} catch (NameNotFoundException e) {
			return -1;
		}
	}

	public static String getCurVersionName(Context ctx) {
		try {
			PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(
					ctx.getPackageName(), 0);
			return pInfo.versionName;
		} catch (NameNotFoundException e) {
			return null;
		}
	}

	public static String displayByAsterisk(String cardNo, int num) {
		if (cardNo != null) {
			if (cardNo.length() > 2 * num) {
				String startStr = cardNo.substring(0, num);
				String endStr = cardNo.substring(cardNo.length() - num,
						cardNo.length());
				cardNo = startStr + "*******" + endStr;
			} else {
				cardNo = cardNo.charAt(0) + "**********"
						+ cardNo.charAt(cardNo.length() - 1);
			}
		}
		return cardNo;
	}

	public static String displayByAsteriskAndLogin(String cardNo) {
		return displayByAsterisk(cardNo, 3);
	}

	public static String displayByAsterisk(String cardNo) {
		return displayByAsterisk(cardNo, 5);
	}

	public static String formatAmountBySpot(String amount) {
		if (amount == null || "null".equals(amount) || "".equals(amount)) {
			return amount;
		}
		String[] amountArray = amount.split("[.]");
		if (amountArray != null && amountArray.length > 1) {
			amountArray[1] = (amountArray[1] + "00").substring(0, 2);
			amount = amountArray[0] + "." + amountArray[1];
		} else {
			amount += ".00";
		}
		return amount;
	}

	// 手机号验证
	public static boolean validatePhone(String mobiles) {
		boolean flag = false;
		try {
			// Pattern p =
			// Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9])|(17[0-9]))\\d{8}$");
			Pattern p = Pattern.compile("1[3-5]+\\d{9}|1[7-8]+\\d{9}");// ("1[3-8]+\\d{9}");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	// 验证密码
	public static boolean validateLoginPwd(String mobiles) {
		boolean flag = false;
		try {
			if (mobiles != null && mobiles.length() >= 6) {
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	// 验证密码字符 只能包含数字 字母 符号 ，不包含空格
	public static boolean validateLoginPwdChar(String pwd) {
		boolean flag = false;
		try {
			Pattern p = Pattern
					.compile("[a-zA-Z0-9`~!@#$%^&*()_+-=\\x7b\\x7d\\x5b\\x5d\\x7c\\x5c:;\\x22'<,>.?/]+");
			Matcher m = p.matcher(pwd);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	// 验证支付密码
	public static boolean validateTranPwd(String mobiles) {
		boolean flag = false;
		try {
			if (mobiles != null && mobiles.length() == 6) {
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	// 验证最多包含两位小数的字符串
	public static boolean validateMoney(String money) {
		boolean flag = false;
		try {
			Pattern p = Pattern
					.compile("^(([1-9]\\d*)(\\.\\d{1,2})?)$|(0\\.[1-9]{1}(\\d{0,1}))$|(0.0{1}([1-9]{1}))$");
			Matcher m = p.matcher(money);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;

	}

	/**
	 * 校验银行卡卡号
	 * 
	 * @param cardId
	 * @return
	 */
	public static boolean checkBankCard(String cardId) {
		char bit = getBankCardCheckCode(cardId
				.substring(0, cardId.length() - 1));
		if (bit == 'N') {
			return false;
		}
		return cardId.charAt(cardId.length() - 1) == bit;
	}

	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
	 * 
	 * @param nonCheckCodeCardId
	 * @return
	 */
	public static char getBankCardCheckCode(String nonCheckCodeCardId) {
		if (nonCheckCodeCardId == null
				|| nonCheckCodeCardId.trim().length() == 0
				|| !nonCheckCodeCardId.matches("\\d+")) {
			// 如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}

	// 得到最后四位的银行卡号
	public static String getSimpleBankCardNo(String bankCard) {
		if (bankCard == null || bankCard.length() <= 4) {
			return bankCard;
		} else {
			bankCard = bankCard.substring(bankCard.length() - 4,
					bankCard.length());
			return bankCard;
		}
	}

	// 汉字第一个字添加*号
	public static String displayNameByAsterisk(String name) {
		if (name == null) {
			return name;
		} else {
			if (name.length() > 1) {
				return "*" + name.substring(1, name.length());
			} else {
				return "*";
			}

		}
	}

	// 显示银行卡6789 **** ****** 1200
	public static String displayBankCardByAsterisk(String bankCard) {
		if (bankCard != null && bankCard.length() >= 8) {
			bankCard = bankCard.substring(0, 4)
					+ " **** ****** "
					+ bankCard.substring(bankCard.length() - 4,
							bankCard.length());
		}
		return bankCard;
	}

	/**
	 * 字节转成兆字节
	 * 
	 * @param size
	 * @return
	 */
	public static float btyeToMbyte(String size) {
		try {
			return (float) (Math
					.round(Float.parseFloat(size) / 1024 / 1024 * 100)) / 100;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 功能：身份证的有效验证
	 * 
	 * @param IDStr
	 *            身份证号
	 * @return 有效：返回"" 无效：返回String信息
	 */
	@SuppressWarnings("unchecked")
	public static String validateIDCard(String IDStr) {
		String errorInfo = "";// 记录错误信息
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",
				"3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
				"9", "10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			errorInfo = "身份证号码长度应该为15位或18位。";
			return errorInfo;
		}
		// =======================(end)========================

		// ================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			return errorInfo;
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
			errorInfo = "身份证生日无效。";
			return errorInfo;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(
							strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				errorInfo = "身份证生日不在有效范围。";
				return errorInfo;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			errorInfo = "身份证月份无效";
			return errorInfo;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			errorInfo = "身份证日期无效";
			return errorInfo;
		}
		// =====================(end)=====================

		// ================ 地区码时候有效 ================
		Hashtable h = GetAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			errorInfo = "身份证地区编码错误。";
			return errorInfo;
		}
		// ==============================================

		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi
					+ Integer.parseInt(String.valueOf(Ai.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				errorInfo = "身份证无效，不是合法的身份证号码";
				return errorInfo;
			}
		} else {
			return "";
		}
		// =====================(end)=====================
		return "";
	}

	/**
	 * 功能：设置地区编码
	 * 
	 * @return Hashtable 对象
	 */
	@SuppressWarnings("unchecked")
	private static Hashtable GetAreaCode() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**
	 * 功能：判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 功能：判断字符串是否为日期格式
	 * 
	 * @return
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}
		/**
	 * @param cardType //01社保卡  02院内卡/临时卡 03金融卡
	 *                 金融卡即健康卡
     */
	public static String getCardTypeFromCardCode(String cardType) {
		String ret = "";
		if(cardType!=null){
			switch (cardType) {
				case "01":
					ret = "社保卡";
					break;
				case "02":
					ret = "院内卡";
					break;
				case "03":
					ret = "健康卡";
					break;
			}
		}
		return ret;
	}

}
