package com.kzb.parents.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import com.kzb.parents.R;

public class CommonOneDialog extends Dialog {

    private ProgressDialogBack dialogBack;

    private Button sureBtn,cancelBtn;
    private int check = 1;
    public CommonOneDialog(Context context) {
        super(context, R.style.IProgressDialog);
        setContentView(R.layout.dialog_one_exam);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCanceledOnTouchOutside(false);
        initView();
    }

    public int getCheck(){
        return check;
    }

    public CommonOneDialog(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.dialog_one_exam);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCanceledOnTouchOutside(false);
        initView();
    }

    public void initView() {
        sureBtn = (Button) findViewById(R.id.dialog_sure_btn);
        cancelBtn = (Button) findViewById(R.id.dialog_cancel_btn);


        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogBack != null) {
                    dialogBack.onBackPressed();
                }
                dismiss();
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }


    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }

    public void showProgressDialog() {
        showProgressDialog(null);
    }

    public void showProgressDialog(String strMessage) {
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


    public void setParams(Activity activity, int px) {
        LayoutParams lay = getWindow().getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Rect rect = new Rect();
        View view = getWindow().getDecorView();
        view.getWindowVisibleDisplayFrame(rect);
        // lay.height = dm.heightPixels - rect.top;
        lay.width = dm.widthPixels;
    }

    public void setCuOnBackPressed(ProgressDialogBack back) {
        this.dialogBack = back;
    }

    public interface ProgressDialogBack {
        public void onBackPressed();
    }
}