package com.kzb.parents.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.kzb.parents.R;

/**
 * Created by wuguicheng on 16/12/5.
 */

public class CustomTextView extends TextView {


    Paint mPaint;
    int sign = 1;//1为完成,2为未完成,3为百分比

    float onePer = 0.5f;


    public CustomTextView(Context context) {
        super(context);
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void setSign(int vals) {

        sign = vals;
        invalidate();
    }


    public void setSign(int vals, float begin) {
        sign = vals;
        this.onePer = begin;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {

//        LogUtil.i("getMeasuredHeight=" + getMeasuredHeight());
//        LogUtil.i("getMeasuredWidth=" + getMeasuredWidth());


        if (sign == 1) {
            mPaint.setColor(getResources().getColor(R.color.item_red_color));
            mPaint.setStyle(Paint.Style.FILL);//设置填满
            canvas.drawRoundRect(0, 0, getMeasuredWidth(), getMeasuredHeight(),20,20, mPaint);
//            canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        } else if (sign == 2) {
            mPaint.setColor(getResources().getColor(R.color.item_green_color));
            mPaint.setStyle(Paint.Style.FILL);//设置填满

            canvas.drawRoundRect(0, 0, getMeasuredWidth(), getMeasuredHeight(),20,20, mPaint);
//            canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        } else {

            int firstRect = (int) (getMeasuredWidth() * onePer);

            mPaint.setColor(getResources().getColor(R.color.item_red_color));
            mPaint.setStyle(Paint.Style.FILL);//设置填满
            canvas.drawRoundRect(0, 0, getMeasuredWidth(), getMeasuredHeight(),20,20, mPaint);
//            canvas.drawRect(firstRect, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);


            mPaint.setColor(getResources().getColor(R.color.item_green_color));
            mPaint.setStyle(Paint.Style.FILL);//设置填满

            canvas.drawRoundRect(0, 0, firstRect, getMeasuredHeight(),20,20, mPaint);
             //思路就是重新绘制一遍，在距离右边20的地方绘制一个矩形
            if((getMeasuredWidth() - firstRect)<=20){
                canvas.drawRoundRect(0, 0, firstRect, getMeasuredHeight(),20,20, mPaint);
            }else {
                canvas.drawRect(firstRect-20, 0, firstRect, getMeasuredHeight(), mPaint);
            }
        }
        super.onDraw(canvas);
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        this.setPadding(0,20,0,20);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }
}
