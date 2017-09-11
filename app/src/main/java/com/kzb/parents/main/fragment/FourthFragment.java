package com.kzb.parents.main.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseFragment;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.login.LoginTypeActivity;
import com.kzb.parents.set.SetCourseActivity;
import com.kzb.parents.settwo.SchoolShenHeActivity;
import com.kzb.parents.settwo.SetDetailActivity;
import com.kzb.parents.settwo.UpdateActivity;
import com.kzb.parents.settwo.model.UpdateResponse;
import com.kzb.parents.util.DeviceUtil;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.util.LogUtils;
import com.kzb.parents.view.DialogView;
import com.kzb.parents.view.RoundImageView;
import com.kzb.parents.view.usericon.MPoPuWindow;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

import static com.kzb.parents.R.id.first_banben_gengxin;
import static com.kzb.parents.application.Application.mContext;


/**
 * Created by wanghaofei on 17/2/15.
 */

public class FourthFragment extends BaseFragment implements View.OnClickListener {

    private HttpConfig httpConfig;
    private DialogView dialogView;

    private TextView headTitle, userName;
    private RelativeLayout setLayout, courseLayout, lgLayout, msgLayout;
    private RelativeLayout schoolRenzheng, bianhaoLyaout;
    private RelativeLayout banbenGengxin;
    private UpdateResponse.UpdateModel updateModel;
    private RoundImageView userIcon;

    private MPoPuWindow puWindow;


    private Uri ImgUri;

    private String path;
    private File file;
    private ImageView userbg;
    private Type type;
    private Bitmap diskBitmap;

    public enum Type {
        PHONE, CAMERA
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth, parent, false);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(mContext);
        initView(view);
        checkVersion();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

    }


    protected void initView(View view) {
        setLayout = (RelativeLayout) view.findViewById(R.id.fourth_set_view);
        courseLayout = (RelativeLayout) view.findViewById(R.id.fourth_course_layout);
        lgLayout = (RelativeLayout) view.findViewById(R.id.first_fourth_lgout);
        msgLayout = (RelativeLayout) view.findViewById(R.id.fourth_msg_layout);
        schoolRenzheng = (RelativeLayout) view.findViewById(R.id.first_school_renzheng);
        bianhaoLyaout = (RelativeLayout) view.findViewById(R.id.first_shebei_bianhao);
        banbenGengxin = (RelativeLayout) view.findViewById(first_banben_gengxin);
        userIcon = (RoundImageView) view.findViewById(R.id.user_icon);
        userbg = (ImageView) view.findViewById(R.id.user_bg);


        userName = (TextView) view.findViewById(R.id.user_name);
        userName.setText(SpSetting.loadLoginInfo().getName());

        userIcon.setImageResource(R.mipmap.login_green);

        //从内存获取头像并设置上
//        String path = Environment.getExternalStorageDirectory() + "/user_icon/" + "myicon.jpg";
        String path = Environment.getExternalStorageDirectory() + "/w65/icon_bitmap/" + "myicon.jpg";



        if (path != null) {
            File file = new File(path);
            if(file.exists()) {

                diskBitmap = getDiskBitmap(path);
                userIcon.setImageBitmap(diskBitmap);
            }else {

                userIcon.setImageResource(R.mipmap.login_green);
            }

        }


//        vipLayout = (RelativeLayout) view.findViewById(R.id.first_vip_lgout);
        setLayout.setOnClickListener(this);
        courseLayout.setOnClickListener(this);
        lgLayout.setOnClickListener(this);
        msgLayout.setOnClickListener(this);
        schoolRenzheng.setOnClickListener(this);
        bianhaoLyaout.setOnClickListener(this);
        banbenGengxin.setOnClickListener(this);
        userIcon.setOnClickListener(this);
//        vipLayout.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fourth_msg_layout:
                Toast.makeText(getActivity(), "此功能暂未开通...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.first_fourth_lgout:
                IntentUtil.startActivity(getActivity(), LoginTypeActivity.class);
//                SpSetting.clearLoginInfo();

                break;
            case R.id.fourth_course_layout:
                IntentUtil.startActivity(getActivity(), SetCourseActivity.class);
                break;
            case R.id.fourth_set_view:
                IntentUtil.startActivity(getActivity(), SetDetailActivity.class);
                break;
            case R.id.first_school_renzheng:
                IntentUtil.startActivity(getActivity(), SchoolShenHeActivity.class);
                break;
            case R.id.first_shebei_bianhao:
                EventBus.getDefault().post("finishOne");
                break;
            case R.id.first_banben_gengxin:


                Map<String, String> mapVal = new HashMap<>();
                mapVal.put("versioncode", updateModel.getVersioncode());
                mapVal.put("versionname", updateModel.getVersionname());
                mapVal.put("content", updateModel.getContent());
                mapVal.put("url", updateModel.getUrl());
                mapVal.put("required", updateModel.getRequired());
                mapVal.put("timer", updateModel.getTimer());
                mapVal.put("filesize", updateModel.getFilesize());
                IntentUtil.startActivity(getActivity(), UpdateActivity.class, mapVal);
                break;
            case R.id.user_icon:

                puWindow = new MPoPuWindow(mContext, getActivity() , this);

                puWindow.showPopupWindow(userIcon);
                puWindow.setOnGetTypeClckListener(new MPoPuWindow.onGetTypeClckListener() {
                    @Override
                    public void getType(Type type1) {
                        type = type1;
                    }

                    @Override
                    public void getImgUri(Uri ImgUri1, File file1) {
                        ImgUri = ImgUri1;
                        file = file1;
                    }
                });


                break;
        }
    }




    public void checkVersion() {
        dialogView.handleDialog(true);
        JSONObject json = new JSONObject();
        httpConfig.doPostRequest(HttpConfig.getBaseRequest(AddressConfig.CHECK_VERSION, json), new GenericsCallback<UpdateResponse>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
            }

            @Override
            public void onResponse(UpdateResponse response, int id) {
                dialogView.handleDialog(false);
                if (response != null && response.errorCode == 0 && response.getContent() != null) {
                    updateModel = response.getContent();


                    LogUtils.e("TAG", "updateModel  = " + updateModel.toString());


                    if (!TextUtils.isEmpty(updateModel.getVersioncode())) {

                        int backCode = Integer.parseInt(updateModel.getVersioncode());
                        int curCode = DeviceUtil.getCurVersionCode(mContext);
                        if (backCode > curCode) {
//                            redSignView.setVisibility(View.VISIBLE);
//                            updateLayout.setEnabled(true);
                        } else {
//                            updateLayout.setEnabled(false);
                            Toast.makeText(mContext, "当前已是最新版本!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(mContext, "获取版本失败,点击版本更新,重新获取!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //重写带结果的回调得到头像

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        FourthFragment.this.onActivityResult(requestCode, resultCode, data);
//
        LogUtils.e("requestCode---------------------------", type + "");

        if (requestCode == 1) {
            if (ImgUri != null) {
                puWindow.onPhoto(ImgUri, 300, 300);
            }
        } else if (requestCode == 2) {
            if (data != null) {
                Uri uri = data.getData();

                puWindow.onPhoto(uri, 300, 300);
            }
        } else if (requestCode == 3) {
            if (type == Type.PHONE) {
                if (data != null) {
                    Bundle extras = data.getExtras();
                    Bitmap bitmap = (Bitmap) extras.get("data");
                    if (bitmap != null) {
                        userIcon.setImageBitmap(bitmap);
                        try {

                            saveFile(bitmap);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else if (type == Type.CAMERA) {
                userIcon.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));
            }
        }


    }


    /**
     * 保存文件
     *
     * @param bm
     * @throws IOException
     */
    public File saveFile(Bitmap bm) throws IOException {
        path = Environment.getExternalStorageDirectory().toString() + "/w65/icon_bitmap/";
        Log.e("TAG", "path ===   " + path);

        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File myIconFile = new File(path + "myicon.jpg");
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myIconFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myIconFile;
    }


    /**
     * 从本地获取图片
     *
     * @param pathString 文件路径
     * @return 图片
     */
    public Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {

        }
        return bitmap;
    }


}
