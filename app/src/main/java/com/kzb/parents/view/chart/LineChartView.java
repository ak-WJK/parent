package com.kzb.parents.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.kzb.parents.util.DensityUtil;
import com.kzb.parents.view.chart.bean.LineRankBean;
import com.kzb.parents.view.chart.bean.Point;

import java.util.ArrayList;
import java.util.List;

public class LineChartView extends View {
    Context mContext;
    //宽度
    private int mWidth;
    //高度
    private int mHeight;
    private String mTextColor = "#00afff";
    private String mLineColor = "#00afff";

    //原点
    private Point mPoint;
    private int mTextSize = 14;

    List<LineRankBean> mData;
    private float total = 100f;

    private int yLevel = 5;
    private int mPointI = 0;

    public LineChartView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mTextSize = DensityUtil.dip2px(mContext, mTextSize);
        mData = new ArrayList<>();
//        mData.add(new LineRankBean(1,"malus","11-11","",99));
//        mData.add(new LineRankBean(3,"malus","11-12","",95));
//        mData.add(new LineRankBean(12,"malus","11-13","",89));
//        mData.add(new LineRankBean(5,"malus","11-14","",93));
//        mData.add(new LineRankBean(7,"malus","11-15","",91));
//        mData.add(new LineRankBean(1,"malus","11-16","",100));
//        mData.add(new LineRankBean(33,"malus","11-17","",76));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mWidth = getMeasuredWidth() - DensityUtil.dip2px(mContext, 10);
        mHeight = getMeasuredHeight();
        mPoint = new Point(DensityUtil.dip2px(getContext(), 20), mHeight - DensityUtil.dip2px(getContext(), 20));
        drawXYAxis(canvas);
        drawLine(canvas);
        drawTouchLine(canvas);
        super.onDraw(canvas);
    }

    /**
     * 画线
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(mLineColor));
        paint.setStrokeWidth(DensityUtil.dip2px(mContext, 1));

        int specX = (mWidth - mPoint.x) / (mData.size() + 1);

        for (int i = 0; i < mData.size() - 1; i++) {
            LineRankBean startBean = mData.get(i);
            LineRankBean endBean = mData.get(i + 1);
            float rate = startBean.rank / total * mPoint.y;
            Point startP = new Point((i + 1) * specX + mPoint.x, (int) rate);
            rate = endBean.rank / total * mPoint.y;
            Point endP = new Point((i + 2) * specX + mPoint.x, (int) rate);

            canvas.drawLine(startP.x, startP.y, endP.x, endP.y, paint);
        }
    }

    /**
     * 画x y坐标线
     *
     * @param canvas
     */
    public void drawXYAxis(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        int spec = (mWidth - mPoint.x) / (mData.size() + 1);
        //x
        for (int i = 0; i < mData.size(); i++) {
            if (mPointI == i) {
                paint.setColor(Color.parseColor(mTextColor));
                paint.setTextSize(DensityUtil.dip2px(mContext, 12));
                paint.setFakeBoldText(true);
            } else {
                paint.setColor(Color.parseColor(mTextColor));
                paint.setFakeBoldText(false);
                paint.setTextSize(mTextSize);
            }
            canvas.drawText(mData.get(i).date, (i + 1) * spec, mHeight, paint);
            //竖虚线
            paint.setColor(Color.parseColor("#dfdfdf"));
            paint.setStrokeWidth(1);
            canvas.drawLine(mPoint.x + spec * (i + 1), mPoint.y, mPoint.x + spec * (i + 1), 0, paint);
        }

        //y间隔
        spec = mPoint.y / yLevel;
        for (int i = 0; i < yLevel; i++) {
            paint.setColor(Color.parseColor(mTextColor));
            if (i == 0) {
                //y字
                canvas.drawText("1", mPoint.x - DensityUtil.dip2px(getContext(), 18), (i) * spec + DensityUtil.dip2px(getContext(), 15), paint);
            }else{
                //y字
                canvas.drawText((20 * (i)) + "", mPoint.x - DensityUtil.dip2px(getContext(), 18), (i) * spec + DensityUtil.dip2px(getContext(), 15), paint);
            }

            //横虚线
            paint.setStrokeWidth(1);
            paint.setColor(Color.parseColor("#dfdfdf"));
            if (i == 0) {
                canvas.drawLine(mPoint.x, spec/20, mWidth, spec/20, paint);
            }else{
                canvas.drawLine(mPoint.x, (i ) * spec, mWidth, (i) * spec, paint);
            }

        }

        //画线
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 2));
        paint.setColor(Color.parseColor(mLineColor));
        canvas.drawLine(mPoint.x, mPoint.y, mWidth, mPoint.y, paint);
        canvas.drawLine(mPoint.x, mPoint.y, mPoint.x, 0, paint);
        //轴线单位
        paint.setTextSize(DensityUtil.dip2px(getContext(), 15));
        paint.setColor(Color.parseColor(mTextColor));
        canvas.drawText("时间", mWidth - DensityUtil.dip2px(getContext(), 50), mHeight - DensityUtil.dip2px(getContext(), 5), paint);
        canvas.drawText("名次", mPoint.x + DensityUtil.dip2px(getContext(), 4), DensityUtil.dip2px(getContext(), 20), paint);

    }

    //设置点击的那条线
    private void drawTouchLine(Canvas canvas) {
        if (mPointI < 0 || mPointI >= mData.size()) {
            return;
        }

        int specX = (mWidth - mPoint.x) / (mData.size() + 1);

        float x = specX * (mPointI + 1) + mPoint.x;
        float y = mData.get(mPointI).rank / total * mPoint.y;


        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#beb8cc"));
        paint.setStrokeWidth(DensityUtil.dip2px(mContext, 1));
        paint.setTextSize(DensityUtil.dip2px(mContext, 2));
        //画线
        canvas.drawLine(x, 0, x, mHeight - DensityUtil.dip2px(mContext, 30), paint);

        //画点
        canvas.drawCircle(x, y, DensityUtil.dip2px(mContext, 3), paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        //点内部是白色
        canvas.drawCircle(x, y, DensityUtil.dip2px(mContext, 2), paint);
        paint.setColor(Color.parseColor("#beb8cc"));
        //最底部点
        canvas.drawCircle(x, mHeight - DensityUtil.dip2px(mContext, 30), DensityUtil.dip2px(mContext, 2), paint);
        //画字
        if (mData != null && mData.size() > mPointI) {
            LineRankBean bean = mData.get(mPointI);
            float drawX = x + 10;
            float drawY = DensityUtil.dip2px(mContext, 11);
            if (drawX + DensityUtil.dip2px(mContext, 70) > mWidth) {
                drawX = drawX - DensityUtil.dip2px(mContext, 70);
            }
            paint.setColor(Color.parseColor(mTextColor));
            paint.setTextSize(DensityUtil.dip2px(mContext, 9));

            canvas.drawText(bean.rank + "名", drawX, drawY, paint);
            drawY += DensityUtil.dip2px(mContext, 11);
            canvas.drawText(bean.score + "分", drawX, drawY, paint);
            drawY += DensityUtil.dip2px(mContext, 11);
            if (bean.date != null) {
                canvas.drawText(bean.date, drawX, drawY, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        if (mData != null && mPoint != null) {
            int spec = (mWidth - mPoint.x) / (mData.size() + 1);
            int p = (int) (x / spec - 0.5);
            if (mPointI != p && p < mData.size()) {

                mPointI = p;
                postInvalidate();
            }
        }

        return true;
    }

    public List<LineRankBean> getData() {
        return mData;
    }

    public void setData(List<LineRankBean> data) {
        if (data != null) {
            this.mData.addAll(data);
        }
        postInvalidate();
    }
}