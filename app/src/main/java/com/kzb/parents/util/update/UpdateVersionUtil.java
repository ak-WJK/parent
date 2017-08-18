package com.kzb.parents.util.update;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.kzb.parents.util.CommonUtil;
import com.kzb.parents.util.DeviceUtil;
import com.kzb.parents.util.ToastUtils;
import com.kzb.parents.view.dialog.CommonTwoBtnDialog;

import java.io.File;

/********************
 * 作者：malus
 * 日期：16/12/23
 * 时间：下午1:12
 * 注释：
 ********************/
public class UpdateVersionUtil {
    Version mVersion;
    Context mContext;
    public Downloader loader;
    // 下载进度框
    private ProgressBarDialog pbDialog;
    public CommonTwoBtnDialog twoBtnDialog;

    private static final int HANDLER_MESSAGE_LOAD_SUCCESS = 101;
    private static final int HANDLER_MESSAGE_LOAD_FAILURE = 102;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_MESSAGE_LOAD_SUCCESS:
                    // 更新进度条
                    int size = (Integer) msg.obj;// Integer.parseInt(msg.obj+);
                    pbDialog.getProgressBar().setProgress(size);
                    float result = (float) pbDialog.getProgressBar().getProgress()
                            / (float) pbDialog.getProgressBar().getMax();
                    int p = (int) (result * 100);
                    pbDialog.getContentTV().setText("已完成" + p + "%");
                    pbDialog.getDialog_downloaded_size().setText(
                            CommonUtil.btyeToMbyte(size + "") + "MB");

                    // 下载完成 重置下载实例
                    if (pbDialog.getProgressBar().getProgress() == pbDialog
                            .getProgressBar().getMax()) {
                        pbDialog.getDialogTitle().setText("下载完成");
                        pbDialog.button_LL.setVisibility(View.VISIBLE);
                        pbDialog.getDialog_refresh_image().setVisibility(View.VISIBLE);
                        //showToast("下载完成");
                        loader = null;
                    }
                    break;
                case HANDLER_MESSAGE_LOAD_FAILURE:
                    Toast.makeText(mContext, "下载失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public UpdateVersionUtil(Context context, Version version) {
        this.mVersion = version;
        this.mContext = context;
        twoBtnDialog = new CommonTwoBtnDialog(mContext);
    }

    public Version getVersion() {
        return mVersion;
    }

    public void setVersion(Version version) {
        this.mVersion = version;
    }

    public void checkUpdate(boolean toast) {
        String currentVersion = DeviceUtil.getCurVersionCode(mContext) + "";
        if (currentVersion.compareTo(mVersion.getVersionCode()) >= 0) {
            if(toast){
                ToastUtils.show(mContext, "当前已是最新版本");
            }
            return;
        }
        twoBtnDialog.setBtn("取消", "更新");
        twoBtnDialog.setContent(mVersion.getUpdateMsg());
        twoBtnDialog.setOnDialogClickListener(new CommonTwoBtnDialog.OnDialogClickListener() {
            @Override
            public void onNagetiveClick() {

            }

            @Override
            public void onPositiveClick() {
                //更新
                initPbDialog();
                pbDialog.showDialog();
                File file;
                try {
                    file = SDDirUtils.getExternalApkDir(mContext);
                } catch (Exception e) {
                    ToastUtils.show(mContext, "SD卡初始化失败,无法进行下载操作");
                    pbDialog.hideDialog();
                    return;
                }
                download(mVersion.getUrl(), file, false);
            }
        });
        twoBtnDialog.showProgressDialog();
    }

    public void checkUpdate() {
        String currentVersion = DeviceUtil.getCurVersionCode(mContext) + "";
        if (currentVersion.compareTo(mVersion.getVersionCode()) >= 0) {
            ToastUtils.show(mContext, "当前已是最新版本");
            return;
        }
        twoBtnDialog.setBtn("取消", "更新");
        twoBtnDialog.setContent(mVersion.getUpdateMsg());
        twoBtnDialog.setOnDialogClickListener(new CommonTwoBtnDialog.OnDialogClickListener() {
            @Override
            public void onNagetiveClick() {

            }

            @Override
            public void onPositiveClick() {
                //更新
                initPbDialog();
                pbDialog.showDialog();
                File file;
                try {
                    file = SDDirUtils.getExternalApkDir(mContext);
                } catch (Exception e) {
                    ToastUtils.show(mContext, "SD卡初始化失败,无法进行下载操作");
                    pbDialog.hideDialog();
                    return;
                }
                download(mVersion.getUrl(), file, false);
            }
        });
        twoBtnDialog.showProgressDialog();
    }


    public void initPbDialog() {
        if (pbDialog == null) {
            pbDialog = new ProgressBarDialog(mContext);
            initPbDialog();
            pbDialog.setEnterOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    pbDialog.hideDialog();
                    setupApk(SDDirUtils.getExternalApkDir(mContext) + "/"
                            + getFileName(mVersion.getUrl()));
                }
            });
            pbDialog.setCanelOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    pbDialog.hideDialog();
                }
            });
            pbDialog.setBackPressListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    pbDialog.hideDialog();
                    stopDownload();
                }
            });
            pbDialog.getDialog_refresh_image().setOnClickListener(
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            initPbDialog();
                            File file;
                            try {
                                file = SDDirUtils.getExternalApkDir(mContext);
                            } catch (Exception e) {
                                ToastUtils.show(mContext, "SD卡初始化失败,无法进行下载操作");
                                return;
                            }
                            download(mVersion.getUrl(), file, true);
                        }
                    });
        }

        pbDialog.getContentTV().setText("已完成0%");
        pbDialog.getProgressBar().setProgress(0);
        pbDialog.getDialogTitle().setText("正在下载");
        pbDialog.button_LL.setVisibility(View.GONE);
        pbDialog.getEnterBN().setText("安装");
        pbDialog.getDialog_refresh_image().setVisibility(View.GONE);
        pbDialog.getDialog_downloaded_size().setText("0MB");
        pbDialog.getDialog_file_size().setText(
                "/" + CommonUtil.btyeToMbyte(mVersion.getFileSize()) + "MB");
    }

    public String getFileName(String url) {
        return mVersion.getVersionCode() + "_" + url.substring(url.lastIndexOf('/') + 1);
    }


    public void stopDownload() {
        if (loader != null) {
            loader.stopDownload();
        }
    }

    // 对于UI控件的更新只能由主线程(UI线程)负责，如果在非UI线程更新UI控件，更新的结果不会反映在屏幕上，某些控件还会出错
    public void download(final String path, final File dir,
                         final boolean needReDownload) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    if (loader == null)
                        loader = new Downloader(mContext,
                                path, dir, 5, needReDownload, "" + mVersion.getVersionCode());
                    final int length = loader.getFileSize();// 获取文件的长度
                    if (loader.isFinish()) {// 已经下载完
                        sendMsg(HANDLER_MESSAGE_LOAD_SUCCESS, length);
                    } else {
                        pbDialog.getProgressBar().setMax(length);// 设置processBar
                        loader.download(new DownloadProgressListener() {
                            public void onDownloadSize(int size) {// 可以实时得到文件下载的长度
                                // if(length==size){
                                sendMsg(HANDLER_MESSAGE_LOAD_SUCCESS, size);
                                // }
                            }
                        });
                    }

                } catch (Exception e) {
                    sendMsg(HANDLER_MESSAGE_LOAD_FAILURE, e.getMessage());
                }
            }
        }).start();
    }


    // 安装应用
    public void setupApk(String appPath) {
        install(mContext, appPath);
    }

    public static boolean install(Context context, String filePath) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        File file = new File(filePath);
        if (file != null && file.length() > 0 && file.exists() && file.isFile()) {
            i.setDataAndType(Uri.parse("file://" + filePath),
                    "application/vnd.android.package-archive");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }
        return false;
    }


    public void sendMsg(int what, Object obj) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = obj;
        mHandler.sendMessage(msg);
    }

}
