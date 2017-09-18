package com.kzb.parents.settwo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.R;
import com.kzb.parents.base.BaseActivity;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.settwo.adapter.ProSpinerAdapter;
import com.kzb.parents.settwo.adapter.ProSpinerAdapter.IOnItemSelectListener;
import com.kzb.parents.settwo.adapter.ProSpinerPopWindow;
import com.kzb.parents.settwo.adapter.SchoolAdapter;
import com.kzb.parents.settwo.model.AreaResponse;
import com.kzb.parents.settwo.model.CityResponse;
import com.kzb.parents.settwo.model.EventModel;
import com.kzb.parents.settwo.model.ProResponse;
import com.kzb.parents.settwo.model.ProResponse.ProModel;
import com.kzb.parents.settwo.model.SchoolResponse;
import com.kzb.parents.util.IntentUtil;
import com.kzb.parents.view.DialogView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by wanghaofei on 17/6/12.
 */

public class SchoolRenZhengActivity extends BaseActivity implements View.OnClickListener, IOnItemSelectListener, SchoolAdapter.SchoolItemClick {

    private TextView proView, areaView, cityView;
    private LinearLayout cityViewLayout, proViewLayout, areaViewLaout;
    private ListView renzhenListView;

    ProSpinerPopWindow proSpinerPopWindow;
    ProSpinerAdapter proSpinerAdapter;

    List<ProResponse.ProModel> listPros = new ArrayList<>();
    List<ProResponse.ProModel> listCitys = new ArrayList<>();
    List<ProResponse.ProModel> listAreas = new ArrayList<>();

    private String currentProId, currentProName;
    private String currentCityId, currentCityName;
    private String currentAreaId, currentAreaName;

    private List<SchoolResponse.SchoolModel> listSchools = new ArrayList<>();

    private int type = 0;//0表示省，1表示市，2表区域

    //筛选按钮
    private TextView shaiView;

    SchoolAdapter schoolAdapter;

    SchoolResponse.SchoolModel schoolModelMo;


    private TextView titleLeft, titleCenter,titleRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuexiao_renzheng);
        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);
        initView();
        initData();

        type = 0;

//        dialogView.handleDialog(true);


    }


    @Override
    protected void initView() {
        titleLeft = getView(R.id.common_head_left);
        titleCenter = getView(R.id.common_head_center);
        titleRight = getView(R.id.common_head_right);

        titleCenter.setText("学校认证");
        titleLeft.setVisibility(View.VISIBLE);
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setText("完成");
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.finish(SchoolRenZhengActivity.this);
            }
        });

        titleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schoolModelMo == null) {
                    Toast.makeText(SchoolRenZhengActivity.this, "请选择学校...", Toast.LENGTH_SHORT).show();
                    return;
                }

                //这里进行判空操作
                EventModel eventModel = new EventModel();
                eventModel.setArea_id(currentProId);
                eventModel.setCity_id(currentCityId);
                eventModel.setDistinct_id(currentAreaId);
                eventModel.setSchool_id(schoolModelMo.getId());
                eventModel.setSchool_name(schoolModelMo.getSchoolname());

                EventBus.getDefault().post(eventModel);
                SchoolRenZhengActivity.this.finish();
            }
        });

        proView = getView(R.id.zhenti_pro);
        areaView = getView(R.id.zhenti_area);
        cityView = getView(R.id.zhenti_city);
        cityViewLayout = getView(R.id.zhenti_city_layout);
        proViewLayout = getView(R.id.zhenti_pro_layout);
        areaViewLaout = getView(R.id.zhenti_area_layout);
        renzhenListView = getView(R.id.renzhen_list_view);


        shaiView = getView(R.id.zhenti_shai_view);

        proViewLayout.setOnClickListener(this);
        areaViewLaout.setOnClickListener(this);
        cityViewLayout.setOnClickListener(this);
        shaiView.setOnClickListener(this);

        schoolAdapter = new SchoolAdapter(SchoolRenZhengActivity.this, listSchools);
        schoolAdapter.setSchoolItemClic(this);
        renzhenListView.setAdapter(schoolAdapter);


    }

    @Override
    protected void initData() {

    }

    //获取省份数据
    private void getProData() {
        JSONObject json = new JSONObject();
        httpConfig.doPostRequest(HttpConfig.getBaseRequest(AddressConfig.IMPROVE_PROVINCE_URL, json), new GenericsCallback<ProResponse>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
                Toast.makeText(SchoolRenZhengActivity.this, "获取列表错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ProResponse response, int id) {
                dialogView.handleDialog(false);
                if (response != null) {
                    if (response.getContent() != null) {

                        listPros = response.getContent();

                        setProVal();
//                        listAreas.clear();
//                        listAreas = response.getContent();
//                        setProData(listAreas);
                    }
                } else {
                    Toast.makeText(SchoolRenZhengActivity.this, "获取列表错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setProVal() {
        proSpinerAdapter = new ProSpinerAdapter(this);
        proSpinerAdapter.refreshData(listPros, 0);

        proSpinerPopWindow = new ProSpinerPopWindow(this);
        proSpinerPopWindow.setAdatper(proSpinerAdapter);
        proSpinerPopWindow.setItemListener(this);
        showSpinWindow();
    }


    private void showSpinWindow() {
//        proSpinerPopWindow.setWidth(proViewLayout.getWidth());
        proSpinerPopWindow.showAsDropDown(proViewLayout);
    }


    //获取市区数据
    private void getCityData() {

        if (TextUtils.isEmpty(currentProId)) {
            Toast.makeText(SchoolRenZhengActivity.this, "请先选择省份...", Toast.LENGTH_SHORT).show();

            return;
        }

        JSONObject json = new JSONObject();
        try {
            json.put("id", currentProId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpConfig.doPostRequest(HttpConfig.getBaseRequest(AddressConfig.IMPROVE_CITY_URL, json), new GenericsCallback<CityResponse>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
                Toast.makeText(SchoolRenZhengActivity.this, "获取列表错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(CityResponse response, int id) {
                dialogView.handleDialog(false);
                if (response != null) {
                    if (response.getContent() != null) {

                        if (listCitys != null && listCitys.size() > 0) {
                            listCitys.clear();
                        }

                        for (int i = 0; i < response.getContent().size(); i++) {
                            CityResponse.CityModel cityModel = response.getContent().get(i);
                            ProModel proModel = new ProModel();
                            proModel.setId(cityModel.getId());
                            proModel.setAreaname(cityModel.getCityname());
                            listCitys.add(proModel);
                        }

                        setCityVal();
                    }
                } else {
                    Toast.makeText(SchoolRenZhengActivity.this, "获取列表错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void setCityVal() {
        proSpinerAdapter = new ProSpinerAdapter(this);
        proSpinerAdapter.refreshData(listCitys, 0);

        proSpinerPopWindow = new ProSpinerPopWindow(this);
        proSpinerPopWindow.setAdatper(proSpinerAdapter);
        proSpinerPopWindow.setItemListener(this);
        showCitySpinWindow();
    }


    private void showCitySpinWindow() {
//        proSpinerPopWindow.setWidth(cityViewLayout.getWidth());
        proSpinerPopWindow.showAsDropDown(cityViewLayout);
    }


    //获取区域数据
    private void getAreaData() {

        if (TextUtils.isEmpty(currentCityId)) {
            Toast.makeText(SchoolRenZhengActivity.this, "请先选择市...", Toast.LENGTH_SHORT).show();

            return;
        }

        JSONObject json = new JSONObject();
        try {
            json.put("id", currentCityId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        httpConfig.doPostRequest(HttpConfig.getBaseRequest(AddressConfig.IMPROVE_QUYU_URL, json), new GenericsCallback<AreaResponse>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
                Toast.makeText(SchoolRenZhengActivity.this, "获取列表错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(AreaResponse response, int id) {
                dialogView.handleDialog(false);
                if (response != null) {
                    if (response.getContent() != null) {

                        if (listAreas != null && listAreas.size() > 0) {
                            listAreas.clear();
                        }

                        for (int i = 0; i < response.getContent().size(); i++) {
                            AreaResponse.AreaModel areaModel = response.getContent().get(i);
                            ProModel proModel = new ProModel();
                            proModel.setId(areaModel.getId());
                            proModel.setAreaname(areaModel.getDistinctname());
                            listAreas.add(proModel);
                        }

                        setAreaVal();
                    }
                } else {
                    Toast.makeText(SchoolRenZhengActivity.this, "获取列表错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //获取学校
    private void getSchoolVal() {

        if (TextUtils.isEmpty(currentProId)) {
            Toast.makeText(SchoolRenZhengActivity.this, "请先选择省...", Toast.LENGTH_SHORT).show();

            return;
        }

        if (TextUtils.isEmpty(currentCityId)) {
            Toast.makeText(SchoolRenZhengActivity.this, "请先选择市...", Toast.LENGTH_SHORT).show();

            return;
        }


        if (TextUtils.isEmpty(currentAreaId)) {
            Toast.makeText(SchoolRenZhengActivity.this, "请先选择区域...", Toast.LENGTH_SHORT).show();

            return;
        }

        JSONObject json = new JSONObject();
        try {
            json.put("area_id", currentProId);
            json.put("city_id", currentCityId);
            json.put("distinct_id", currentAreaId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        httpConfig.doPostRequest(HttpConfig.getBaseRequest(AddressConfig.SET_GET_SCHOOL_URL, json), new GenericsCallback<SchoolResponse>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);
                Toast.makeText(SchoolRenZhengActivity.this, "获取列表错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(SchoolResponse response, int id) {
                dialogView.handleDialog(false);
                if (response != null) {
                    if (response.getContent() != null) {
                        listSchools = response.getContent();
                        schoolAdapter.setItems(listSchools);
                    }
                } else {
                    Toast.makeText(SchoolRenZhengActivity.this, "获取列表错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void setAreaVal() {
        proSpinerAdapter = new ProSpinerAdapter(this);
        proSpinerAdapter.refreshData(listAreas, 0);

        proSpinerPopWindow = new ProSpinerPopWindow(this);
        proSpinerPopWindow.setAdatper(proSpinerAdapter);
        proSpinerPopWindow.setItemListener(this);
        showAreaSpinWindow();
    }


    private void showAreaSpinWindow() {
//        proSpinerPopWindow.setWidth(areaViewLaout.getWidth());
        proSpinerPopWindow.showAsDropDown(areaViewLaout);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zhenti_pro_layout:
                type = 0;
                getProData();

                if (currentCityName != null) {
                    currentCityName = null;
                    currentCityId = null;
                    cityView.setText("城市");

                    currentAreaName = null;
                    currentAreaId = null;
                    areaView.setText("区域");
                }

                break;
            case R.id.zhenti_city_layout:
                type = 1;
                getCityData();

                if (currentAreaName != null) {
                    currentAreaName = null;
                    currentAreaId = null;
                    areaView.setText("区域");
                }

                break;
            case R.id.zhenti_area_layout:
                type = 2;
                getAreaData();
                break;
            case R.id.zhenti_shai_view:
                getSchoolVal();
                break;
        }
    }

    @Override
    public void onItemClick(int pos) {
        if (pos >= 0 && pos <= listPros.size()) {
            if (type == 0) {
                currentProId = listPros.get(pos).getId();
                currentProName = listPros.get(pos).getAreaname();
                proView.setText(currentProName);
            } else if (type == 1) {

                currentCityId = listCitys.get(pos).getId();
                currentCityName = listCitys.get(pos).getAreaname();
                cityView.setText(currentCityName);
            } else if (type == 2) {
                currentAreaId = listAreas.get(pos).getId();
                currentAreaName = listAreas.get(pos).getAreaname();
                areaView.setText(currentAreaName);
            }
        }
    }

    @Override
    public void sItemClick(SchoolResponse.SchoolModel schoolModel) {
        schoolModelMo = schoolModel;
//        Toast.makeText(SchoolRenZhengActivity.this,schoolModel.getSchoolname(),Toast.LENGTH_SHORT).show();
    }
}
