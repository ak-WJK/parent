package com.kzb.parents.config;

import com.kzb.baselibrary.config.MineConfig;

/**
 * Created by wanghaofei on 16/11/29.
 *
 */

public class Config {

    public static final int TYPE_LOCAL = 1;// 本地测试环境
    public static final int TYPE_PUB = 2;// 正式
    public static String JAVA_API_URL="http://t.kaozhibao.com/"; // java接口地址


    public static final int re_error = 0x7f040000;

    //微信ID,secret


    /**
     * 变化的常量
     **/
    public static int type = TYPE_PUB;// 当前环境

    public static void init(int type) {
        if (MineConfig.DEBUG) {
            // 如果用户在app中切换了环境，就使用app中的设置，否则使用原始的配置
//            int eType = Settings.getEnvironmentType();
//            if (eType != -1) {
//                type = eType;
//            }

            type = TYPE_LOCAL;
        } else {
            type = TYPE_PUB;
        }

        switch (type){
            case TYPE_LOCAL:
                JAVA_API_URL="http://t.kaozhibao.com/";
                break;
            case TYPE_PUB:
                JAVA_API_URL="";
                break;
        }


    }



}
