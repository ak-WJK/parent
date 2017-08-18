package com.kzb.parents.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kzb.parents.R;


public class CommonTwoBtnDialog extends AlertDialog {
    private Button nagetive, positive;
    private TextView title;
    private TextView content;

    private String nagetiveStr, positiveStr, titleStr, contentStr;

    public CommonTwoBtnDialog(Context context) {
        super(context, R.style.dialogFull);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_two_btn_dialog);

        nagetive = (Button) findViewById(R.id.dialog_negative);
        positive = (Button) findViewById(R.id.dialog_positive);
        title = (TextView) findViewById(R.id.dialog_title);
        content = (TextView) findViewById(R.id.dialog_content);
        setData();

        nagetive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideProgressDialog();
                if (onDialogClickListener != null) {
                    onDialogClickListener.onNagetiveClick();
                }
            }
        });
        positive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideProgressDialog();
                if (onDialogClickListener != null) {
                    onDialogClickListener.onPositiveClick();
                }
            }
        });
    }

    public void setData() {
        if (!TextUtils.isEmpty(titleStr)) {
            this.title.setText(titleStr);
        } else {
            this.title.setText("温馨提示");
        }
        if (!TextUtils.isEmpty(contentStr)) {
            this.content.setText(Html.fromHtml(contentStr));
        } else {
            this.content.setText("温馨提示");
        }
        if (!TextUtils.isEmpty(nagetiveStr)) {
            this.nagetive.setText(nagetiveStr);
        } else {
            this.nagetive.setText("取消");
        }
        if (!TextUtils.isEmpty(positiveStr)) {
            this.positive.setText(positiveStr);
        } else {
            this.positive.setText("确定");
        }

    }

    public void setData(String title, String content, String nagetive,
                        String positive) {
        titleStr = title;
        contentStr = content;
        nagetiveStr = nagetive;
        positiveStr = positive;
    }

    public void setTitle(String title) {
        titleStr = title;
        if (this.title != null) {
            this.title.setText(titleStr);
        }
    }

    public void setContent(String content) {
        contentStr = content;
        if (this.content != null) {
            this.content.setText(Html.fromHtml(contentStr));
        }

    }

    public void setBtn(String nagetive, String positive) {
        nagetiveStr = nagetive;
        positiveStr = positive;
        if (this.nagetive != null) {
            if (!TextUtils.isEmpty(nagetiveStr)) {
                this.nagetive.setText(nagetiveStr);
            } else {
                this.nagetive.setText("取消");
            }
        }
        if (this.positive != null) {
            if (!TextUtils.isEmpty(positiveStr)) {
                this.positive.setText(positiveStr);
            } else {
                this.positive.setText("确定");
            }
        }
    }

    OnDialogClickListener onDialogClickListener;

    public void setOnDialogClickListener(
            OnDialogClickListener onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
    }

    public interface OnDialogClickListener {
        void onNagetiveClick();

        void onPositiveClick();
    }

    public void showProgressDialog() {
        showProgressDialog(null);
    }

    public void showProgressDialog(final String strMessage) {
//         TextView tvMsg = (TextView) this.findViewById(R.id.tv_loadingmsg);
//         if (tvMsg != null) {
//         if (strMessage != null)
//         tvMsg.setText(strMessage);
//         }
        if (!TextUtils.isEmpty(strMessage)) {
            if (content != null) {
                content.setText(strMessage);
            }
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                try {

                    show();
                } catch (Exception e) {

                }
            }
        });
    }

    public void hideProgressDialog() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                try {
                    dismiss();
                } catch (Exception e) {

                }
            }
        });
    }
}
