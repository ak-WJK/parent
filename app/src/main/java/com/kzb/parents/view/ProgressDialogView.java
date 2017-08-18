package com.kzb.parents.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.Window;
import android.widget.ImageView;

import com.kzb.parents.R;
import com.kzb.parents.view.dialog.RoundedRectProgressBar;

/**
 * Created by wanghaofei on 16/10/19.
 */

public class ProgressDialogView extends Dialog {
    RoundedRectProgressBar bar;
    public ProgressDialogView(Context context) {
        super(context);
        initView();
    }

    public ProgressDialogView(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public ProgressDialogView(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public void setProgress(int progress){
        if (bar != null) {
            bar.setProgress(progress);
        }
    }

    public void initView(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setFeatureDrawableAlpha(0, 0);
        setContentView(R.layout.common_dialog_view_progress);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        GradientDrawable dra=new GradientDrawable();
        dra.setColor(Color.parseColor("#00000000"));
        getWindow().setBackgroundDrawable(dra);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            getWindow().setDimAmount(0.5f);
        }
        bar = (RoundedRectProgressBar) findViewById(R.id.bar);

        AnimationDrawable animationDrawable = (AnimationDrawable)( (ImageView)findViewById(R.id.dialog_img)).getDrawable();
        animationDrawable.start();
    }

//    View gudiView;
//    public DialogView(Activity activity){
//        gudiView = LayoutInflater.from(activity).inflate(R.layout.common_dialog_view, null);
//        gudiView.setClickable(true);
//        ViewGroup contentView = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
//        contentView.addView(gudiView);
//        gudiView.setVisibility(View.GONE);
//
//        AnimationDrawable animationDrawable = (AnimationDrawable)( (ImageView)gudiView.findViewById(R.id.dialog_img)).getDrawable();
//        animationDrawable.start();
//    }

    public  void handleDialog(boolean isShow){


        if(isShow){
            show();
        }else {
            dismiss();
        }

    }




}
