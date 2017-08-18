package com.kzb.parents.payzfb;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.parents.JsonGenericsSerializator;
import com.kzb.parents.base.XBaseRequest;
import com.kzb.parents.config.AddressConfig;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.main.model.VipResponse;
import com.kzb.parents.payzfb.model.ZFBOrderResponse;
import com.kzb.parents.view.DialogView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
/**
 * 重要说明:
 * <p>
 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
 */
public class PayDemoActivity extends FragmentActivity {

    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2017062107533999";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "2088721131590797";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    //  public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCZ7gTrxCEGdRFzaCOjRsJO+0qnW3gIFJrD1UNp9FF8duOXa6XHQLogumWF/mu+R0Ehy33PXzzQrUHxZPgYWvxmIQBMjh5hqRPMx4eV9dtJ867pc4sHOOIWVFMXgwKB8Qd5HBEMEl1EwhypdMDCdMXIp97fuPjs4DRARY3EvTUu4fEDI72pv6O56IQyIhaOuuyOClr5bOd3L4HidjAH2VVjD/vkLQSc22Ta9agZF+a/iMeXoelqsYX00gXoWz37eKGRs58Jxa0c0MLwY9RzZpxJBCGB/PWc/96m7Aplm9yIRaDvU63VsPu3S5N/JUL2xjdvrafiGqCLB0IZPrsI231xAgMBAAECggEAQS4XqVnwfhJ+ZDb5uD7TMqyX7Qv/ZLSM4FXi6O/KvYB+WPT9SsUuy4MCUF3uc7EqdEv9pxz13Isw/C28HFwSupBI1yHq3YHB35mx4R8tT0A/lZ0WhuVgXwIv+SnBmxJcDr2CvnDcS6xChMLqiKz3v0LOTj++MFez6G9LW76e0Y2w0uc5N5Z9x9+/TzD6YioYW6RdwKBXNIrsWy9xIsnThQzLIPSGZEtCsNfRYrH6dIXkE/uWGUZdMEdXYCwjLZC3/kJ2L+5UGrte2A5czuFPXIUq9EFXakDeSDoFRy+LroQ9aNKg/YA0R7ZygD2c2hvGc6huejXK9L45leMmxPZbkQKBgQD3Y42tWZgrpXMcEBQovQOAMfLbNRDD4CYO5TNyrqn446BgVO7lTA6tCZoZvU42LMCA31NQcfk11ScCF+ZINQhCm9PG9+xizMv08avuq0YzqiYiNPKJ8XmoZo5DRf6VTD02jmx5Y/nhjbSuvUdY/ET8mV0e6GOdyDXBACiEJeumBQKBgQCfSapD3229q0yYGmBsAVS7Bddyb+e48SwSNb8gCL6JvOFNhO6zt9hN/ScZ6s6mvoYJNaO3AlP6kO0mvoTZ7+o+vPPd/Q0WdXoHf8EWOohaP/AqepAVRf4TQrKKa/rKzv7QJ/w2L78Jodj/SB/4+xSHGsYKXfpOCli15Bn2fuRJfQKBgDH1nd/7Lvrpf7SUHq4XdcSG0cepEMit1KG/ccNgOJ01wuaRYGlYEcKqJL+79jaElR+xeg7N5YkUkbsffnboltF0zLDq0oclWq8Nuy4I0rWOUAMqdCydYnc6ttIpKX+XFOmWm5tsMEN3rZx4RxdWYswsYJzkS3HKw+hFEumVCuDNAoGBAJOGLUxJLSEh+oFQxIajQO8pvN0+6TTvIGsJHic0EgwNQ/mAqXcHSS0JzeICBzyFbiwvYp4HaPHCsfFVOEOEIMXUSGQLjsiDKu6xEopc0ZYMa4lcE/+v4F3EQwlScmbNCVCOB9fDpsHsBMaB7SKyn9N1J3/IhB8tE/X5+VXDNfkdAoGAKGEquVQutyLXT/7JEgLolplQOPR5hNChjucNxLBFGxpP4Nhy0eEvuVGRm+Urkj8NwCd2wp79hoQsOwK/sszxT8I7nSB80kXvQuS8ILV9OlAKoaMOKk9FubpIhy3G63DMCk22lY/DY32SwHFNzkWaMPwuqXtwaf07KWesAnrlODE=";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    private HttpConfig httpConfig;

    public DialogView dialogView;

    private String goodsId;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);

                    Log.e("kzb", "payResult=" + payResult.toString());

                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        EventBus.getDefault().post(new VipResponse());
                        Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    PayDemoActivity.this.finish();
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(PayDemoActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(PayDemoActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		setContentView(R.layout.pay_main);

        httpConfig = new HttpConfig();
        dialogView = new DialogView(this);

        goodsId = getIntent().getStringExtra("id");

//        Toast.makeText(PayDemoActivity.this, goodsId, Toast.LENGTH_SHORT).show();

        //获取订信息
        getData();
//		payV2();

    }


    private void getData() {
        dialogView.handleDialog(true);
        XBaseRequest baseRequest = new XBaseRequest();
        baseRequest.setUrl(AddressConfig.ORDER_INFO_URL);

        Map<String, String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("id", goodsId);
            object.put("uid",SpSetting.loadLoginInfo().getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("info", object.toString());

        baseRequest.setRequestParams(map);

        httpConfig.doPostRequest(baseRequest, new GenericsCallback<ZFBOrderResponse>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
                dialogView.handleDialog(false);

            }

            @Override
            public void onResponse(ZFBOrderResponse response, int id) {
                dialogView.handleDialog(false);
                if (response != null && response.getContent() != null) {

                    payV2(response.getContent());

//					initData(response.getContent());
                    //成功
//                    IntentUtil.startActivity(SetCourseActivity.this,MainPageActivity.class);

                } else {
                }

            }
        });
    }


    /**
     * 支付宝支付业务
     */
    public void payV2(final String orderInfo) {
//        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
//            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialoginterface, int i) {
//                            //
//                            finish();
//                        }
//                    }).show();
//            return;
//        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
//        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
//        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//
//        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
//        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
//		final String orderInfo = orderParam + "&" + sign;

//		Log.e("view",orderInfo);

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayDemoActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝账户授权业务
     *
     * @param v
     */
//    public void authV2(View v) {
//        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
//                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
//                || TextUtils.isEmpty(TARGET_ID)) {
//            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialoginterface, int i) {
//                        }
//                    }).show();
//            return;
//        }
//
//        /**
//         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
//         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
//         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
//         *
//         * authInfo的获取必须来自服务端；
//         */
//        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
//        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
//        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
//
//        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
//        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
//        final String authInfo = info + "&" + sign;
//        Runnable authRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                // 构造AuthTask 对象
//                AuthTask authTask = new AuthTask(PayDemoActivity.this);
//                // 调用授权接口，获取授权结果
//                Map<String, String> result = authTask.authV2(authInfo, true);
//
//                Message msg = new Message();
//                msg.what = SDK_AUTH_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//
//        // 必须异步调用
//        Thread authThread = new Thread(authRunnable);
//        authThread.start();
//    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
     *
     * @param v
     */
    public void h5Pay(View v) {
        Intent intent = new Intent(this, H5PayDemoActivity.class);
        Bundle extras = new Bundle();
        /**
         * url 是要测试的网站，在 Demo App 中会使用 H5PayDemoActivity 内的 WebView 打开。
         *
         * 可以填写任一支持支付宝支付的网站（如淘宝或一号店），在网站中下订单并唤起支付宝；
         * 或者直接填写由支付宝文档提供的“网站 Demo”生成的订单地址
         * （如 https://mclient.alipay.com/h5Continue.htm?h5_route_token=303ff0894cd4dccf591b089761dexxxx）
         * 进行测试。
         *
         * H5PayDemoActivity 中的 MyWebViewClient.shouldOverrideUrlLoading() 实现了拦截 URL 唤起支付宝，
         * 可以参考它实现自定义的 URL 拦截逻辑。
         */
        String url = "http://m.taobao.com";
        extras.putString("url", url);
        intent.putExtras(extras);
        startActivity(intent);
    }

}
