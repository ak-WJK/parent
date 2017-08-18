package com.kzb.parents.paywx;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.base.XBaseRequest;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.paywx.PayModel.PModel;
import com.kzb.parents.view.DialogView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class PayActivity extends Activity {

	private IWXAPI api;

//	private Button payBtn;

	private HttpConfig httpConfig;

	public DialogView dialogView;

	PModel pModel;
	private String goodsId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.pay);
		api = WXAPIFactory.createWXAPI(this,null);
		api.registerApp("wx83a4b020f3b50ac6");
		httpConfig = new HttpConfig();
		dialogView = new DialogView(this);

//		payBtn = (Button)this.findViewById(R.id.pay);


		goodsId = getIntent().getStringExtra("id");


		getData();




	}




	private void getData() {
		dialogView.handleDialog(true);
		XBaseRequest baseRequest = new XBaseRequest();
		baseRequest.setUrl(AddressConfig.WXORDER_INFO_URL);

		Map<String, String> map = new HashMap<>();

		JSONObject object = new JSONObject();
		try {
			object.put("id", goodsId);
			object.put("uid", SpSetting.loadLoginInfo().getUid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("info", object.toString());

		baseRequest.setRequestParams(map);

		httpConfig.doPostRequest(baseRequest, new GenericsCallback<PayModel>(new JsonGenericsSerializator()) {

			@Override
			public void onError(Call call, Exception e, int id) {
				dialogView.handleDialog(false);

			}

			@Override
			public void onResponse(PayModel response, int id) {
				dialogView.handleDialog(false);
				if (response != null && response.getContent() != null) {
					pModel = response.getContent();
					payMoney(pModel);
				} else {
				}

			}
		});
	}



	private void payMoney(PModel pModel){

		if(null == pModel){
			Toast.makeText(PayActivity.this,"获取订单失败,请重新获取",Toast.LENGTH_SHORT).show();
			return;
		}

		PayReq req = new PayReq();
		req.appId = pModel.getAppid();
		req.partnerId = pModel.getPartnerid();
		req.prepayId = pModel.getPrepayid();
		req.nonceStr = pModel.getNoncestr();
		req.timeStamp = pModel.getTimestamp();
		req.packageValue = "Sign=WXPay";
		req.sign = pModel.getSign();
		req.extData = "app data"; // optional
		// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
		api.sendReq(req);
		PayActivity.this.finish();
	}


}
