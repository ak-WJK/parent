package com.kzb.parents.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.kzb.parents.R;
import com.kzb.parents.util.DensityUtil;
import com.kzb.parents.view.chart.bean.Rate;

import java.util.ArrayList;
import java.util.List;

/********************
 * 作者：malus
 * 日期：16/11/25
 * 时间：上午11:30
 * 注释：
 ********************/
public class CircleForNanduChartView extends View {
    Context mContext;
    //环半径
    private int mRadius;
    //总宽
    private int mWidth;
    //总高
    private int mHeight;
    private List<Rate> mRates;
    private String title = "题目难度分布";
    private int mTopMargin;

    private int texta, gray, green, red, blue;

    public CircleForNanduChartView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public CircleForNanduChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public CircleForNanduChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mRadius = DensityUtil.dip2px(mContext, 70);
    }

    public void setRates(List<Rate> rates) {
        mRates = rates;
        postInvalidate();
    }

    //初始化
    private void init() {
        mRates = new ArrayList<>();
//        Rate rate = new Rate(0.1f,Color.GREEN,"一星","");
//        mRates.add(rate);
//        rate = new Rate(0.4f,Color.BLUE,"二星","");
//        mRates.add(rate);
//        rate = new Rate(0.5f,Color.DKGRAY,"三星","");
//        mRates.add(rate);
        mTopMargin = DensityUtil.dip2px(getContext(), 10);

        texta = getContext().getResources().getColor(R.color.black);
        gray = getContext().getResources().getColor(R.color.text_c);
        green = getContext().getResources().getColor(R.color.theme_green);
        red = getContext().getResources().getColor(R.color.theme_red);
        blue = getContext().getResources().getColor(R.color.theme_green);
    }
    public void setTitle(String title){
        this.title = title;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTitle(canvas);
        drawOutterRing(canvas);
//        drawInnerRing(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        int tX = getWidth() / 2;
        int tY = getHeight() - DensityUtil.dip2px(getContext(), 30);

        paint.setColor(Color.GRAY);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 20));
        RectF rectF = new RectF(tX - DensityUtil.dip2px(getContext(), 160), tY - DensityUtil.dip2px(getContext(), 10), tX - DensityUtil.dip2px(getContext(), 120), tY + DensityUtil.dip2px(getContext(), 10));
        canvas.drawRoundRect(rectF, DensityUtil.dip2px(getContext(), 4), DensityUtil.dip2px(getContext(), 4), paint);
//        canvas.drawLine(tX- DensityUtil.dip2px(getContext(), 160), tY, tX- DensityUtil.dip2px(getContext(), 120), tY, paint);

        paint.setColor(texta);
        paint.setTextSize(DensityUtil.dip2px(getContext(), 15));

        canvas.drawText("简单", tX - DensityUtil.dip2px(getContext(), 115), tY + DensityUtil.dip2px(getContext(), 6), paint);

        paint.setColor(green);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 20));
        rectF = new RectF(tX - DensityUtil.dip2px(getContext(), 40), tY - DensityUtil.dip2px(getContext(), 10), tX, tY + DensityUtil.dip2px(getContext(), 10));
        canvas.drawRoundRect(rectF, DensityUtil.dip2px(getContext(), 4), DensityUtil.dip2px(getContext(), 4), paint);

//        canvas.drawLine(tX-DensityUtil.dip2px(getContext(),40), tY, tX , tY, paint);

        paint.setColor(texta);
        paint.setTextSize(DensityUtil.dip2px(getContext(), 15));
        canvas.drawText("中等", tX + DensityUtil.dip2px(getContext(), 5), tY + DensityUtil.dip2px(getContext(), 6), paint);


        paint.setColor(red);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 20));
        rectF = new RectF(tX + DensityUtil.dip2px(getContext(), 80), tY - DensityUtil.dip2px(getContext(), 10), tX + DensityUtil.dip2px(getContext(), 120), tY + DensityUtil.dip2px(getContext(), 10));
        canvas.drawRoundRect(rectF, DensityUtil.dip2px(getContext(), 4), DensityUtil.dip2px(getContext(), 4), paint);

//        canvas.drawLine(tX + DensityUtil.dip2px(getContext(), 80), tY, tX + DensityUtil.dip2px(getContext(), 120), tY, paint);
        paint.setColor(texta);
        paint.setTextSize(DensityUtil.dip2px(getContext(), 15));
        canvas.drawText("较难", tX + DensityUtil.dip2px(getContext(), 125), tY + DensityUtil.dip2px(getContext(), 6), paint);
    }

    /**
     * 画外圆
     *
     * @param canvas
     */
    public void drawOutterRing(Canvas canvas) {
        RectF oval2 = new RectF(mWidth / 2 - mRadius, mHeight / 2 - mRadius + mTopMargin, mWidth / 2 + mRadius, mHeight / 2 + mRadius + mTopMargin);// 设置个新的长方形，扫描测量
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(DensityUtil.dip2px(mContext, 1));
        paint.setAntiAlias(true);
        float startAngle = 0;
        for (int i = 0; i < mRates.size(); i++) {
            Rate rate = mRates.get(i);
            float angle = 360 * rate.rate;
            paint.setColor(rate.color);

            //画环
            canvas.drawArc(oval2, startAngle, angle, true, paint);
//            //画线 小点
            float x = mWidth / 2 + (float) Math.cos((angle / 2 + startAngle) * Math.PI / 180) * mRadius;
            float y = mHeight / 2 + (float) Math.sin((angle / 2 + startAngle) * Math.PI / 180) * mRadius + mTopMargin;
//            canvas.drawCircle(x, y, DensityUtil.dip2px(mContext,3), paint);
            //放label线的长度
            float labelL = DensityUtil.dip2px(mContext, 80);
            if ((startAngle + angle / 2) < 90) {
                float zX = x + DensityUtil.dip2px(mContext, 20);
                float zY = y + DensityUtil.dip2px(mContext, 10);
                canvas.drawLine(x, y, zX, zY, paint);
                canvas.drawLine(zX, zY, zX + labelL, zY, paint);
                //写字
                float tx = zX + labelL - DensityUtil.dip2px(mContext, 70);
                float ty = zY;
                canvas.drawCircle(tx, ty - DensityUtil.dip2px(mContext, 7), DensityUtil.dip2px(mContext, 2), paint);
                paint.setColor(texta);
                paint.setTextSize(DensityUtil.dip2px(mContext, 14));
                canvas.drawText(rate.title, tx + 10, ty - DensityUtil.dip2px(mContext, 5), paint);
                canvas.drawText(rate.label, tx + 10, ty + DensityUtil.dip2px(mContext, 10), paint);
                //写字
            } else if ((startAngle + angle / 2) < 180) {
                float zX = x - DensityUtil.dip2px(mContext, 20);
                float zY = y + DensityUtil.dip2px(mContext, 10);
                canvas.drawLine(x, y, zX, zY, paint);
                canvas.drawLine(zX, zY, zX - labelL, zY, paint);
                //写字
                float tx = zX - labelL + 10;
                float ty = zY;
                canvas.drawCircle(tx, ty - DensityUtil.dip2px(mContext, 7), DensityUtil.dip2px(mContext, 2), paint);
                paint.setTextSize(DensityUtil.dip2px(mContext, 14));
                paint.setColor(texta);
                canvas.drawText(rate.title, tx + 10, ty - DensityUtil.dip2px(mContext, 5), paint);
                canvas.drawText(rate.label, tx + 10, ty + DensityUtil.dip2px(mContext, 10), paint);
            } else if ((startAngle + angle / 2) < 270) {
                float zX = x - DensityUtil.dip2px(mContext, 20);
                float zY = y - DensityUtil.dip2px(mContext, 10);
                canvas.drawLine(x, y, zX, zY, paint);
                canvas.drawLine(zX, zY, zX - labelL, zY, paint);
                //写字
                float tx = zX - labelL + 10;
                float ty = zY;
                canvas.drawCircle(tx, ty - DensityUtil.dip2px(mContext, 7), DensityUtil.dip2px(mContext, 2), paint);
                paint.setColor(texta);
                paint.setTextSize(DensityUtil.dip2px(mContext, 14));
                canvas.drawText(rate.title, tx + 10, ty - DensityUtil.dip2px(mContext, 5), paint);
                canvas.drawText(rate.label, tx + 10, ty + DensityUtil.dip2px(mContext, 10), paint);
            } else if ((startAngle + angle / 2) < 360) {
                float zX = x + DensityUtil.dip2px(mContext, 20);
                float zY = y - DensityUtil.dip2px(mContext, 10);
                canvas.drawLine(x, y, zX, zY, paint);
                canvas.drawLine(zX, zY, zX + labelL, zY, paint);
                //写字
                //写字
                float tx = zX + labelL - DensityUtil.dip2px(mContext, 70);
                float ty = zY;
                canvas.drawCircle(tx, ty - DensityUtil.dip2px(mContext, 7), DensityUtil.dip2px(mContext, 2), paint);
                paint.setColor(texta);
                paint.setTextSize(DensityUtil.dip2px(mContext, 14));
                canvas.drawText(rate.title, tx + 10, ty - DensityUtil.dip2px(mContext, 5), paint);
                canvas.drawText(rate.label, tx + 10, ty + DensityUtil.dip2px(mContext, 10), paint);
            }

            startAngle = startAngle + angle;
        }

//        paint.setColor(Color.BLUE);
//
//
//        //画环
//        canvas.drawArc(oval2, 0, 140, true, paint);
//        //画线
//        float x = mWidth / 2 + (float) Math.cos(10 * Math.PI / 180) * mRadius;
//        float y = mHeight / 2 + (float) Math.sin(10 * Math.PI / 180) * mRadius;
//        canvas.drawCircle(x, y, 10, paint);
//
//        paint.setColor(Color.RED);
//        //画环
//        canvas.drawArc(oval2, 140, 90, true, paint);
//        //画线
//        x = mWidth / 2 + (float) Math.cos(150 * Math.PI / 180) * mRadius;
//        y = mHeight / 2 + (float) Math.sin(150 * Math.PI / 180) * mRadius;
//        canvas.drawCircle(x, y, 10, paint);
//
//        paint.setColor(Color.YELLOW);
//        //画线
//        x = mWidth / 2 + (float) Math.cos(240 * Math.PI / 180) * mRadius;
//        y = mHeight / 2 + (float) Math.sin(240 * Math.PI / 180) * mRadius;
//        canvas.drawCircle(x, y, 10, paint);
//        //画环
//        canvas.drawArc(oval2, 230, 130, true, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //点击的点
        float mx = event.getX();
        float my = event.getY();

        if (mx < mWidth / 2 + mRadius && mx > mWidth / 2 - mRadius && my > mHeight / 2 - mRadius && my < mHeight / 2 + mRadius) {
            double rate = getClickRate(mx, my);
            if (mRates != null && mRates.size() > 0) {
                double whe = 0;
                for (int i = 0; i < mRates.size(); i++) {
                    whe += mRates.get(i).rate;
                    if (whe > rate) {
                        if (onChartClick != null) {
                            onChartClick.onChartClick(2-i);
                        }
                        return super.onTouchEvent(event);
                    }
                }
            }

        }
        return super.onTouchEvent(event);
    }

    private double getClickRate(float mx, float my) {
        float px = mWidth/2;
        float py = mHeight/2;


        float x = Math.abs(px-mx);
        float y = Math.abs(py-my);
        double z = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));

        double cos = y/z;
        double radina = Math.acos(cos);//用反三角函数求弧度
        double angle = Math.floor(180/(Math.PI/radina));//将弧度转换成角度

        if(mx>px&&my>py){//鼠标在第四象限
            angle = 180 - angle;
        }

        if(mx==px&&my>py){//鼠标在y轴负方向上
            angle = 180;
        }

        if(mx>px&&my==py){//鼠标在x轴正方向上
            angle = 90;
        }

        if(mx<px&&my>py){//鼠标在第三象限
            angle = 180+angle;
        }

        if(mx<px&&my==py){//鼠标在x轴负方向
            angle = 270;
        }

        if(mx<px&&my<py){//鼠标在第二象限
            angle = 360 - angle;
        }
        angle = angle - 90;

        if(angle<0){
            angle = angle + 360;
        }
        return angle / 360;
    }

    /**
     * 画内圆
     *
     * @param canvas
     */
    public void drawInnerRing(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius - DensityUtil.dip2px(mContext, 30), paint);
    }

    public void drawTitle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(DensityUtil.dip2px(getContext(), 17));
        paint.setColor(texta);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 3));
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(title, mWidth / 2, DensityUtil.dip2px(getContext(), 30), paint);
    }

    OnChartClick onChartClick;
    public void setOnChartClick(OnChartClick onChartClick){
        this.onChartClick = onChartClick;
    }
    public interface OnChartClick{
        void onChartClick(int position);
    }


}

