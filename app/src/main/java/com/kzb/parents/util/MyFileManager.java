package com.kzb.parents.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by wanghaofei on 17/1/16.
 */

public class MyFileManager {
    private static MyFileManager me;
    private String SD_DIR;// 客户端定义的SD卡缓存资源存储根目录目录

    private MyFileManager() {
    }

    public static MyFileManager getInstance() {
        if (me == null) {
            synchronized (MyFileManager.class) {
                if (me == null) {
                    me = new MyFileManager();
                }
            }
        }
        return me;
    }

    /**
     * 初始化方法
     *
     * @param fileDir SD卡资源缓存目录
     */
    public void init(String fileDir) {
        SD_DIR = Environment.getExternalStorageDirectory().getPath() + "/"
                + fileDir;
        File file = new File(SD_DIR);
        if (!file.exists()) {
            file.mkdir();
        }
    }


    public String getDir() {
        return SD_DIR;
    }

}
