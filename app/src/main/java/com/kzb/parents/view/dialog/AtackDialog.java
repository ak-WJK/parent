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
import android.widget.TextView;

import com.kzb.parents.R;

public class AtackDialog extends Dialog {

    private ProgressDialogBack dialogBack;
    private TextView mTitle,mScore,mTime;
    private Button mReport,mReturn,mReplay;

    public AtackDialog(Context context) {
        super(context, R.style.IProgressDialog);
        setContentView(R.layout.dialog_atack);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCanceledOnTouchOutside(false);
        initView();
    }

    public AtackDialog(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.dialog_atack);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCanceledOnTouchOutside(false);
        initView();
    }

    public void initView() {
        mReturn = (Button) findViewById(R.id.dialog_atack_return);
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogBack != null) {
                    dialogBack.onReturn();
                }
                dismiss();
            }
        });
        mReplay = (Button) findViewById(R.id.dialog_atack_replay);
        mReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogBack != null) {
                    dialogBack.onReplay();
                }
                dismiss();
            }
        });
        mReport = (Button) findViewById(R.id.dialog_atack_report);
        mReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogBack != null) {
                    dialogBack.onReport();
                }
                dismiss();
            }
        });

        mTitle = (TextView)findViewById(R.id.dialog_atack_title);
        mScore = (TextView)findViewById(R.id.dialog_atack_score);
        mTime = (TextView)findViewById(R.id.dialog_atack_time);
    }



    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

//		ImageView imageView = (ImageView) this.findViewById(R.id.loading_img1);
//		AnimationDrawable animationDrawable1 = (AnimationDrawable) imageView
//				.getBackground();
//
//		imageView = (ImageView) this.findViewById(R.id.loading_img2);
//		AnimationDrawable animationDrawable2 = (AnimationDrawable) imageView
//				.getBackground();
//
//		imageView = (ImageView) this.findViewById(R.id.loading_img3);
//		AnimationDrawable animationDrawable3 = (AnimationDrawable) imageView
//				.getBackground();
//
//		animationDrawable1.start();
//		animationDrawable2.start();
//		animationDrawable3.start();
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
        void onReport();
        void onReturn();
        void onReplay();
    }

    public void setMsg(String title, String score, String time, boolean success) {
        if(success){
            mScore.setTextColor(getContext().getResources().getColor(R.color.theme_red));
        }else{
            mScore.setTextColor(getContext().getResources().getColor(R.color.text_3));
        }
        if (mTime != null) {
            mTime.setText(time);
        }
        if (mScore != null) {
            mScore.setText(score);
        }
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }
}