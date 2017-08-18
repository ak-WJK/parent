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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kzb.parents.R;

public class ExamDialog extends Dialog {

    private ProgressDialogBack dialogBack;
    private TextView mTitle;
    private TextView mMsg1;
    private TextView mMsg2;
    private TextView mMsg3;
    private TextView signView;
    private Button mBtn;
    private RadioGroup mGroup;
    private RadioButton mButton1;
    private RadioButton mButton2;

    private LinearLayout mGroupLayout;
    private int check = 1;
    public ExamDialog(Context context) {
        super(context, R.style.IProgressDialog);
        setContentView(R.layout.dialog_exam);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCanceledOnTouchOutside(false);
        initView();
    }

    public void showRadio(){
        if (mGroupLayout != null) {
//            mGroupLayout.setVisibility(View.VISIBLE);
            signView.setVisibility(View.VISIBLE);
            mGroup.setVisibility(View.VISIBLE);
        }
        if (mGroup != null) {
            mGroup.check(R.id.dialog_exam_radio_1);
            mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.dialog_exam_radio_1:
                            check = 1;
                            break;
                        case R.id.dialog_exam_radio_2:
                            check = 2;
                            break;
                    }
                }
            });
        }
    }

    public int getCheck(){
        return check;
    }

    public ExamDialog(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.dialog_exam);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCanceledOnTouchOutside(false);
        initView();
    }

    public void initView() {
        mBtn = (Button) findViewById(R.id.dialog_exam_btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogBack != null) {
                    dialogBack.onBackPressed();
                }
                dismiss();
            }
        });
        mTitle = (TextView)findViewById(R.id.dialog_exam_title);
        mMsg1 = (TextView) findViewById(R.id.dialog_exam_msg_1);
        mMsg2 = (TextView) findViewById(R.id.dialog_exam_msg_2);
        mMsg3 = (TextView) findViewById(R.id.dialog_exam_msg_3);
        mGroup = (RadioGroup) findViewById(R.id.dialog_exam_radio);
        mButton1 = (RadioButton) findViewById(R.id.dialog_exam_radio_1);
        mButton2 = (RadioButton) findViewById(R.id.dialog_exam_radio_2);

        signView = (TextView) findViewById(R.id.dialog_exam_sign_view);

        mGroupLayout = (LinearLayout) findViewById(R.id.dialog_exam_radio_layout);
    }

    public void setTitle(String title){
        mTitle.setText(title);
    }
    public void setBtn(String title){
        mBtn.setText(title);
    }
    public void setMessage(String msg1, String msg2, String msg3) {
        if (msg1 != null) {
            mMsg1.setText(msg1);
        }
        if (msg2 != null) {
            mMsg2.setText(msg2);
        }
        if (msg3 != null) {
            mMsg3.setText(msg3);
        }

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
//
//    @Override
//    public void onBackPressed() {
//        if (dialogBack != null) {
//            dialogBack.onBackPressed();
//        } else {
//            dismiss();
//        }
//    }

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
    public static final int MODE_SB = 1;
    public static final int MODE_1B = 0;
    private int mode = MODE_1B;
    public void setMode(int mode){
        this.mode = mode;
        if(mode==MODE_SB){
            mButton1.setText("随机60道");
            mButton2.setText("每个知识点1道");
        }
    }
}