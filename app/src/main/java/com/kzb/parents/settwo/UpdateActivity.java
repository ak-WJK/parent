package com.kzb.parents.settwo;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;

import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.util.LogUtils;
import com.kzb.parents.util.update.UpdateVersionUtil;
import com.kzb.parents.util.update.Version;
import com.kzb.parents.view.DialogView;

/**
 * Created by wanghaofei on 17/6/12.
 */

public class UpdateActivity extends BaseActivity {

    private String versioncode;
    private String versionname;
    private String content;
    private String url;
    private String required;
    private String timer;
    private String filesize;

    private TextView updateView;

    private TextView newUpdateContentView, newVNameView;

    private TextView titleLeft, titleCenter, titleRight;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        LogUtils.e("TAG", " apk更新页面 ");

        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);


        versioncode = getIntent().getStringExtra("versioncode");
        versionname = getIntent().getStringExtra("versionname");
        content = getIntent().getStringExtra("content");
        url = getIntent().getStringExtra("url");
        required = getIntent().getStringExtra("required");
        timer = getIntent().getStringExtra("timer");
        filesize = getIntent().getStringExtra("filesize");

        initView();
        initData();
    }

    @Override
    protected void initView() {
        updateView = getView(R.id.update_button);
        newVNameView = getView(R.id.update_version_name);

        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        titleRight = getView(R.id.common_head_right);

        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.finish(UpdateActivity.this);
            }
        });

        titleCenter.setText("个人设置");

        newVNameView.setText("已有新版本:" + versionname);
        updateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Version version = new Version();
                version.setFileSize(filesize);
                version.setVersionCode(versioncode);
                version.setVersionName(versionname);
                version.setUpdateMsg(content);
                version.setUrl(url);
//                version.setUrl("http://192.168.111.224:8080/ContackMsg/app.apk");


                UpdateVersionUtil updateVersionUtil = new UpdateVersionUtil(UpdateActivity.this, version);
                updateVersionUtil.checkUpdate();

            }
        });

    }

    @Override
    protected void initData() {

    }


}
