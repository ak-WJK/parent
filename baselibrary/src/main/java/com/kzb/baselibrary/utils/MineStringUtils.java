package com.kzb.baselibrary.utils;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.zip.GZIPInputStream;

/**
 * Created by wanghaofei on 16/7/8.
 */
public class MineStringUtils {

    private static final String tag = MineStringUtils.class.getSimpleName();

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            if (MineStringUtils.isEmpty(sb.toString())) {
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static boolean checkEmail(String mail) {
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(mail);
        return m.find();
    }

    public static String assemblingAuthentity(String email, String pws) {
        JSONObject object = new JSONObject();
        String result = null;
        try {
            object.put("user_email", email);
            object.put("user_pws", pws);
            result = object.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return result;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String quote(String str) {
        return "\"" + str + "\"";
    }

    public static int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    public static Double parseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public static long parseLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static int strToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String MD5(String text) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("MD5");
            byte[] md5hash = new byte[32];
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            md5hash = md.digest();
            return convertToHex(md5hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 是否是电话号码
    public static boolean isPhoneNumber(String phoneNumber) {
        return phoneNumber.length() == 11;
    }

    public static final String key = "a6#43d%1f064@eb";

    // 客户端加密
    public static String xirEnCode(String str) {
        StringBuilder result = new StringBuilder();
        StringBuilder sb = new StringBuilder(str);
        StringBuilder sbKey = new StringBuilder(key);
        for (int i = 0; i < sb.length(); i++) {
            int cr = sb.charAt(i) ^ sbKey.charAt(i % sbKey.length());
            result.append((char) cr);
        }
        return new String(Base64.encodeBytes(result.toString().getBytes()));
    }

    // 转换时间
    public static String date(Date time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String SDateTime = formatter.format(time);
        return SDateTime;
    }

    public static String date(Date time, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String SDateTime = formatter.format(time);
        return SDateTime;
    }

    /**
     * 把yyyy-mm-dd转换成yyyy年mm月dd日
     *
     * @param date
     * @return date
     */
    public static String dateToDate(String date) {
        String str = date.replaceFirst("-", "年").replaceFirst("-", "月") + "日";
        return str;

    }

    /**
     * 把GZIP流解压缩
     *
     * @return
     */
    public static String isStringGZIP(InputStream is) {
        String jsonString = null;
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(is);
            bis.mark(2);
            // 取前两个字节
            byte[] header = new byte[2];
            int result = bis.read(header);
            // reset输入流到开始位置
            bis.reset();
            // 判断是否是GZIP格式
            int headerData = (int) ((header[0] << 8) | header[1] & 0xFF);
            // Gzip 流 的前两个字节是 0x1f8b

            if (result != -1 && headerData == 0x1f8b) {
                is = new GZIPInputStream(bis);
            } else {
                is = bis;
            }
            InputStreamReader reader = new InputStreamReader(is, "utf-8");
            char[] data = new char[100];
            int readSize;
            StringBuilder sb = new StringBuilder();
            while ((readSize = reader.read(data)) > 0) {
                sb.append(data, 0, readSize);
            }
            jsonString = sb.toString();
            bis.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    /**
     * 获取一个字符串的长度。不同之处在于，一个汉字等于2个字符长度
     *
     * @param @param s
     * @param @return
     * @return int
     * @throws
     * @author
     * @since zhubajie　Ver 2.0.0
     */
    public static int getGBKStringLength(String s) {

        int length = 0;

        byte[] bytes = s.getBytes();
        int stringLength = bytes.length;

        for (int i = 0; i < stringLength; i++) {

            if (bytes[i] < 0) { // 如果字节码为负数，则为汉字
                length = length + 2;
                i = i + 2;
            } else {
                length = length + 1;
            }

        }

        return length;
    }

    /**
     *
     * isLetter:验证是否字母
     *
     * @param @param s
     * @param @return
     * @return boolean
     * @throws
     * @author
     * @since zhubajie　Ver 2.0.1
     */
    public static boolean isLetter(String s) {
        char c = s.charAt(0);
        int i = (int) c;
        if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * isNumeric:是否包含数字
     *
     * @param @param str
     * @param @return
     * @return boolean
     * @throws
     * @author
     * @since zhubajie　Ver 2.0.1
     */
    public static boolean isNumeric(String str) {
        String regex = "[0-9]+?";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).find();
    }

    /**
     * 验证是否是允许上传的图片格式
     */
    public static boolean isImage(String s) {

        if (s == null) {
            return false;
        }

        int length = s.lastIndexOf(".");

        String fileType = null;

        if (length != -1) {
            fileType = s.substring(length, s.length());
        }

        if (".jpg".equalsIgnoreCase(fileType)
                || ".png".equalsIgnoreCase(fileType)) {
            return true;
        }

        return false;
    }

    /**
     *
     * isSpecialNum:特殊字符验证
     *
     * @param @param str
     * @param @return
     * @return boolean
     * @throws
     * @author
     * @since zhubajie　Ver 2.0.1
     */
    public static boolean isSpecialNum(String str) {
        boolean result = false;
        if (str.replaceAll("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*-*_*", "").length() == 0)
            result = true;
        else
            result = false;
        return result;
    }

    /**
     * 等级转换为中文
     */
    public static String convertNumber(int index) {
        String[] arrNum = { "新手", "猪一戒", "猪二戒", "猪三戒", "猪四戒", "猪五戒", "猪六戒",
                "猪七戒", "猪八戒", "猪九戒", "猪十戒", "猪十一戒", "猪十二戒", "猪十三戒", "猪十四戒",
                "猪十五戒", "猪十六戒", "猪十七戒", "猪十八戒", "猪十九戒", "猪二十戒", "猪二十一戒",
                "猪二十二戒", "猪二十三戒", "猪二十四戒", "二十五戒", "猪二十六戒", "猪二十七戒", "猪二十八戒",
                "猪二十九戒", "猪三十戒", "猪三十一戒", "猪三十二戒" }; // 大写数字
        return arrNum[index];
    }


    // 正则截取value
    public static String getValue(String str, String key) {
        Pattern mPattern = Pattern.compile("(" + key
                + "\":\\s*\")(.*?)(\\s*\",)");
        Matcher mMatcher = mPattern.matcher(str);
        while (mMatcher.find()) {
            return mMatcher.group(2);
        }
        return null;
    }

    /**
     * 半角转全角，解决TextView排版混乱问题
     * */
    public static String ToDBC(String input) {
        if (input == null || "".equals(input)){
            return "";
        }
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 替换、过滤特殊字符
     * */
    public static String StringFilter(String str) throws PatternSyntaxException {
        str = str.replaceAll("【", "[").replaceAll("】", "]")
                .replaceAll("！", "!").replaceAll("\n", "").replaceAll("\r", "");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

}
