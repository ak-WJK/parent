package com.kzb.parents.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.kzb.parents.R;
import com.kzb.parents.util.DensityUtil;

/********************
 * 作者：malus
 * 日期：16/12/4
 * 时间：上午11:16
 * 注释：
 ********************/
public class HorizontalChart extends View {
    private int totalNum = 100;
    private float num1 = 0;
    private float num2 = 0;
    private float num3 = 0;
    private int mWidth;
    private int mHeight;
    private String title = "成绩情况";
    private int texta,gray,green,red,blue;

    public HorizontalChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        texta = context.getResources().getColor(R.color.text_a);
        gray = context.getResources().getColor(R.color.text_c);
        green = context.getResources().getColor(R.color.theme_green);
        red = context.getResources().getColor(R.color.theme_red);
        blue = context.getResources().getColor(R.color.theme_green);
    }

    public HorizontalChart(Context context) {
        super(context);
        texta = context.getResources().getColor(R.color.text_a);
        gray = context.getResources().getColor(R.color.text_c);
        green = context.getResources().getColor(R.color.theme_green);
        red = context.getResources().getColor(R.color.theme_red);
        blue = context.getResources().getColor(R.color.theme_green);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(DensityUtil.dip2px(getContext(),20));
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 3));
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(title, mWidth / 2, DensityUtil.dip2px(getContext(), 30), paint);
        //原点
        Point point = new Point();
        point.x = DensityUtil.dip2px(getContext(),10);
        point.y = getHeight() - DensityUtil.dip2px(getContext(),80);
        //画x线
        int xSpec = (getWidth() - DensityUtil.dip2px(getContext(), 20)) / 5;
        int yLength = DensityUtil.dip2px(getContext(),130);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 1));
        paint.setColor(Color.BLACK);
        paint.setTextSize(DensityUtil.dip2px(getContext(),14));
        canvas.drawLine(point.x-DensityUtil.dip2px(getContext(),5), point.y, point.x + xSpec * 5, point.y, paint);
        //画x点
        for (int i = 0; i < 5; i++) {
            canvas.drawLine(point.x + xSpec * (1 + i), point.y, point.x + xSpec * (1 + i), point.y + DensityUtil.dip2px(getContext(), 4), paint);
            if (i == 4) {
                canvas.drawText(20 * (i + 1) + "", point.x + xSpec * (1 + i) - DensityUtil.dip2px(getContext(), 15), point.y + DensityUtil.dip2px(getContext(), 15), paint);
            }else{
                canvas.drawText(20 * (i + 1) + "", point.x + xSpec * (1 + i) - DensityUtil.dip2px(getContext(), 5), point.y + DensityUtil.dip2px(getContext(), 15), paint);
            }

        }

        //画y线
        canvas.drawLine(point.x, point.y + DensityUtil.dip2px(getContext(), 5), point.x, point.y - yLength, paint);
        //画网格
        paint.setStrokeWidth(1);
        paint.setColor(Color.GRAY);
        for (int i = 0; i < 5; i++) {
            //不要网格了
//            canvas.drawLine(point.x + xSpec * (1 + i), point.y, point.x + xSpec * (1 + i), point.y - yLength, paint);
        }
//        canvas.drawLine(point.x, point.y - yLength, point.x + xSpec * 5, point.y - yLength, paint);

        //最好成绩
        double stopX = (num1 / 100.0) * 5 * xSpec + point.x;
        int tempV1 = (int)num1;
        paint.setColor(green);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(),25));
        canvas.drawLine(point.x, point.y - yLength + DensityUtil.dip2px(getContext(), 25), (int) stopX, point.y - yLength + DensityUtil.dip2px(getContext(), 25), paint);
        paint.setColor(texta);
        canvas.drawText(tempV1+"分",(float)stopX+DensityUtil.dip2px(getContext(),2), point.y - yLength + DensityUtil.dip2px(getContext(), 30),paint);

        //我的成绩
        stopX = (num2 / 100.0) * 5 * xSpec + point.x;

        paint.setColor(red);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(),25));
        canvas.drawLine(point.x, point.y - yLength + DensityUtil.dip2px(getContext(), 60), (int) stopX, point.y - yLength + DensityUtil.dip2px(getContext(), 60), paint);
        paint.setColor(texta);
        canvas.drawText((int)num2+"分",(float)stopX+DensityUtil.dip2px(getContext(),2), point.y - yLength + DensityUtil.dip2px(getContext(), 65),paint);

        //平均成绩
        stopX = (num3 / 100.0) * 5 * xSpec + point.x;
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(),25));
        canvas.drawLine(point.x, point.y - yLength + DensityUtil.dip2px(getContext(), 95), (int) stopX, point.y - yLength + DensityUtil.dip2px(getContext(), 95), paint);
        paint.setColor(texta);
        canvas.drawText((int)num3+"分",(float)stopX+DensityUtil.dip2px(getContext(),2), point.y - yLength + DensityUtil.dip2px(getContext(), 100),paint);

        int tX = getWidth() / 2;
        int tY = getHeight() - DensityUtil.dip2px(getContext(), 30);

        paint.setColor(green);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(),20));
        RectF rectF = new RectF(tX-DensityUtil.dip2px(getContext(),180),tY-DensityUtil.dip2px(getContext(),10),tX-DensityUtil.dip2px(getContext(),140),tY+DensityUtil.dip2px(getContext(),10));
        canvas.drawRoundRect(rectF,DensityUtil.dip2px(getContext(),4),DensityUtil.dip2px(getContext(),4),paint);
//        canvas.drawLine(tX- DensityUtil.dip2px(getContext(), 160), tY, tX- DensityUtil.dip2px(getContext(), 120), tY, paint);

        paint.setColor(texta);
        paint.setTextSize(DensityUtil.dip2px(getContext(),16));

        canvas.drawText("最高",tX-DensityUtil.dip2px(getContext(),135),tY+DensityUtil.dip2px(getContext(),6),paint);

        paint.setColor(red);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 20));
        rectF = new RectF(tX-DensityUtil.dip2px(getContext(),40),tY-DensityUtil.dip2px(getContext(),10),tX,tY+DensityUtil.dip2px(getContext(),10));
        canvas.drawRoundRect(rectF,DensityUtil.dip2px(getContext(),4),DensityUtil.dip2px(getContext(),4),paint);

//        canvas.drawLine(tX-DensityUtil.dip2px(getContext(),40), tY, tX , tY, paint);

        paint.setColor(texta);
        paint.setTextSize(DensityUtil.dip2px(getContext(),16));
        canvas.drawText("我的",tX+DensityUtil.dip2px(getContext(),5),tY+DensityUtil.dip2px(getContext(),6),paint);


        paint.setColor(Color.GRAY);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 20));
        rectF = new RectF(tX+DensityUtil.dip2px(getContext(),100),tY-DensityUtil.dip2px(getContext(),10),tX+DensityUtil.dip2px(getContext(),140),tY+DensityUtil.dip2px(getContext(),10));
        canvas.drawRoundRect(rectF,DensityUtil.dip2px(getContext(),4),DensityUtil.dip2px(getContext(),4),paint);

//        canvas.drawLine(tX + DensityUtil.dip2px(getContext(), 80), tY, tX + DensityUtil.dip2px(getContext(), 120), tY, paint);
        paint.setColor(texta);
        paint.setTextSize(DensityUtil.dip2px(getContext(),16));
        canvas.drawText("平均",tX+DensityUtil.dip2px(getContext(),145),tY+DensityUtil.dip2px(getContext(),6),paint);

//
//        int tX = getWidth()/2+DensityUtil.dip2px(getContext(),20);
//        int tY = getHeight() - DensityUtil.dip2px(getContext(), 30);
//
//        paint.setColor(Color.RED);
//        paint.setTextAlign(Paint.Align.LEFT);
//        paint.setStrokeWidth(DensityUtil.dip2px(getContext(),20));
//        canvas.drawLine(tX- DensityUtil.dip2px(getContext(), 140), tY, tX- DensityUtil.dip2px(getContext(), 100), tY, paint);
//
//        paint.setColor(Color.parseColor("#333333"));
//        paint.setTextSize(DensityUtil.dip2px(getContext(),20));
//        canvas.drawText("我的成绩",tX-DensityUtil.dip2px(getContext(),95),tY+DensityUtil.dip2px(getContext(),6),paint);
//
//        paint.setColor(Color.GREEN);
//        paint.setTextAlign(Paint.Align.LEFT);
//        paint.setStrokeWidth(DensityUtil.dip2px(getContext(),20));
//        RectF rectF = new RectF(tX,tY-DensityUtil.dip2px(getContext(),10),tX + DensityUtil.dip2px(getContext(),40),tY+DensityUtil.dip2px(getContext(),10));
//        canvas.drawRoundRect(rectF,DensityUtil.dip2px(getContext(),4),DensityUtil.dip2px(getContext(),4),paint);
//        paint.setColor(Color.parseColor("#333333"));
//
//        paint.setTextSize(DensityUtil.dip2px(getContext(),20));
//        canvas.drawText("最高成绩",tX+DensityUtil.dip2px(getContext(),45),tY+DensityUtil.dip2px(getContext(),6),paint);
    }

    /**
     * 设置分数
     * @param secord
     * @param totalSecord
     */
    public void setSecord(String secord,String totalSecord,String avgSecord){
        num1 = Float.parseFloat(totalSecord);
        num2 = Float.parseFloat(secord);
        num3 = Float.parseFloat(avgSecord);
        postInvalidate();
    }
}
