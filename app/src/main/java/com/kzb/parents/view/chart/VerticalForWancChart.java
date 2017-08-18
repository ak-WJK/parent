package com.kzb.parents.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.kzb.parents.R;
import com.kzb.parents.util.DensityUtil;

/********************
 * 作者：malus
 * 日期：16/12/4
 * 时间：上午11:16
 * 注释：
 ********************/
public class VerticalForWancChart extends View {
    private double totalNum = 12.0;
    private int mWidth;
    private int mHeight;
    private int[] xUnGet = {0,0,0};
    private int[] xGet = {0,0,0};
    private String[] xTitle = {"较难","中等","简单"};
    private String title = "题目完成情况";

    int xSpec;
    int ySpec;
    Point point;//原点

    private int texta,gray,green,red,blue;

    public VerticalForWancChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        texta = context.getResources().getColor(R.color.text_a);
        gray = context.getResources().getColor(R.color.text_c);
        green = context.getResources().getColor(R.color.theme_green);
        red = context.getResources().getColor(R.color.theme_red);
        blue = context.getResources().getColor(R.color.theme_green);
    }

    public VerticalForWancChart(Context context) {
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
        paint.setTextSize(DensityUtil.dip2px(getContext(),15));
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 3));
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(title, mWidth / 2, DensityUtil.dip2px(getContext(), 30), paint);
        //原点
        point = new Point();
        paint.setAntiAlias(true);
        point.x = DensityUtil.dip2px(getContext(),18);
        point.y = getHeight() - DensityUtil.dip2px(getContext(),80);
        //画x线
        xSpec = (getWidth() - DensityUtil.dip2px(getContext(), 20)) / 3;
        int yLength = DensityUtil.dip2px(getContext(), 120);
        ySpec = yLength / 6;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 1));
        paint.setColor(Color.BLACK);
        paint.setTextSize(DensityUtil.dip2px(getContext(),10));
        canvas.drawLine(point.x-DensityUtil.dip2px(getContext(),5), point.y, point.x + xSpec * 5, point.y, paint);
        //画x点
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(DensityUtil.dip2px(getContext(), 15));
        for (int i = 0; i < 3; i++) {
            canvas.drawLine(point.x + xSpec * (1 + i), point.y, point.x + xSpec * (1 + i), point.y + DensityUtil.dip2px(getContext(), 4), paint);
            canvas.drawText(xTitle[i], point.x + xSpec * (1 + i) - xSpec/2 , point.y + DensityUtil.dip2px(getContext(), 20), paint);
        }
        //画y线
        canvas.drawLine(point.x, point.y + DensityUtil.dip2px(getContext(), 5), point.x, point.y - yLength, paint);
        paint.setTextAlign(Paint.Align.LEFT);
        //画y点
        int intSpec = (int)totalNum / 6;
        paint.setTextSize(DensityUtil.dip2px(getContext(), 10));
        for (int i = 0; i < 6; i++) {
            canvas.drawLine(point.x - DensityUtil.dip2px(getContext(), 5), point.y - ySpec * (1 + i), point.x, point.y - ySpec * (1 + i), paint);
            canvas.drawText(intSpec * (i + 1) + "", point.x - DensityUtil.dip2px(getContext(), 17), point.y - ySpec * (1 + i)+DensityUtil.dip2px(getContext(),5), paint);

        }

        //画网格
        paint.setStrokeWidth(1);
        paint.setColor(Color.GRAY);
        for (int i = 0; i < 3; i++) {
            //不要网格了
//            canvas.drawLine(point.x + xSpec * (1 + i), point.y, point.x + xSpec * (1 + i), point.y - yLength, paint);
        }
        for (int i = 0; i < 6; i++) {
            //不要网格了
//            canvas.drawLine(point.x, point.y - yLength + ySpec * (1 * i), point.x + xSpec * 5, point.y - yLength + ySpec * (1 * i), paint);
        }
        //画图
        for (int i = 0; i < xUnGet.length; i++) {
            double stopY = point.y - (xUnGet[i]+xGet[i])/totalNum * ySpec * 6;
            paint.setColor(red);
            paint.setStrokeWidth(xSpec - DensityUtil.dip2px(getContext(),30));
            paint.setTextSize(DensityUtil.dip2px(getContext(), 14));
            canvas.drawLine(point.x + xSpec * (1 + i) - xSpec / 2, point.y, point.x + xSpec * (1 + i) - xSpec / 2, (int) stopY, paint);
            if(xUnGet[i]>0){
                paint.setColor(texta);
                double y = stopY + (1.0/totalNum * ySpec * 6) * (xUnGet[i] / 2.0) + DensityUtil.dip2px(getContext(), 6);
                canvas.drawText((int)xUnGet[i] + "个", point.x + xSpec * (1 + i) - xSpec / 2 -DensityUtil.dip2px(getContext(),10), (float) y, paint);
            }
        }

        for (int i = 0; i < xGet.length; i++) {
            double stopY = point.y - xGet[i]/totalNum * ySpec * 6;
            paint.setColor(green);
            paint.setStrokeWidth(xSpec - DensityUtil.dip2px(getContext(),30));
            canvas.drawLine(point.x + xSpec * (1 + i) - xSpec / 2, point.y, point.x + xSpec * (1 + i) - xSpec / 2, (int) stopY, paint);
            if(xGet[i]>0){
                paint.setColor(texta);
                double y = stopY + (1.0/totalNum * ySpec * 6) * (xGet[i] / 2.0) + DensityUtil.dip2px(getContext(), 6);
                canvas.drawText((int) xGet[i] + "个", point.x + xSpec * (1 + i) - xSpec / 2 - DensityUtil.dip2px(getContext(), 10), (float) y, paint);
            }
        }

        int tX = getWidth()/2+DensityUtil.dip2px(getContext(),30);
        int tY = getHeight() - DensityUtil.dip2px(getContext(), 30);

        paint.setColor(green);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 20));
        RectF rectF = new RectF(tX-DensityUtil.dip2px(getContext(),150),tY-DensityUtil.dip2px(getContext(),10),tX-DensityUtil.dip2px(getContext(),110),tY+DensityUtil.dip2px(getContext(),10));
        canvas.drawRoundRect(rectF,DensityUtil.dip2px(getContext(),4),DensityUtil.dip2px(getContext(),4),paint);

//        canvas.drawLine(tX- DensityUtil.dip2px(getContext(), 150), tY, tX- DensityUtil.dip2px(getContext(), 110), tY, paint);

        paint.setColor(texta);
        paint.setTextSize(DensityUtil.dip2px(getContext(),15));
        canvas.drawText("正确数",tX-DensityUtil.dip2px(getContext(),105),tY+DensityUtil.dip2px(getContext(),6),paint);

        paint.setColor(red);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 20));
        rectF = new RectF(tX,tY-DensityUtil.dip2px(getContext(),10),tX+DensityUtil.dip2px(getContext(),40),tY+DensityUtil.dip2px(getContext(),10));
        canvas.drawRoundRect(rectF,DensityUtil.dip2px(getContext(),4),DensityUtil.dip2px(getContext(),4),paint);

//        canvas.drawLine(tX, tY, tX + DensityUtil.dip2px(getContext(), 50), tY, paint);
        paint.setColor(texta);

        paint.setTextSize(DensityUtil.dip2px(getContext(),15));
        canvas.drawText("错误数",tX+DensityUtil.dip2px(getContext(),55),tY+DensityUtil.dip2px(getContext(),6),paint);

//        //我的成绩
//        stopX = (num2 / 100.0) * 5 * xSpec;
//        paint.setColor(Color.RED);
//        paint.setStrokeWidth(DensityUtil.dip2px(getContext(),30));
//        canvas.drawLine(point.x, point.y - yLength + DensityUtil.dip2px(getContext(), 70), (int) stopX, point.y - yLength + DensityUtil.dip2px(getContext(), 70), paint);
    }

    public void setNum(float max, int[] unGet, int[] get) {
        if (max > 0) {
            totalNum = max;
        }
        xUnGet = unGet;
        xGet = get;
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        int lineX1 = point.x + xSpec / 2;
        int lineX2 = point.x + xSpec * 3 / 2;
        int lineX3 = point.x + xSpec * 5 / 2;
        int width = DensityUtil.dip2px(getContext(),15);
        if (x > lineX1 - width && x < lineX1 + width) {
            //position 0
            double stopY = point.y - (xUnGet[0] + xGet[0]) / totalNum * ySpec * 6;
            if (y > stopY && y < point.y) {
                onChartClick.onChartClick(0);
            }
        }
        if (x > lineX2 - width && x < lineX2 + width) {
            //position 1
            double stopY = point.y - (xUnGet[1] + xGet[1]) / totalNum * ySpec * 6;
            if (y > stopY && y < point.y) {
                onChartClick.onChartClick(1);
            }
        }
        if (x > lineX3 - width && x < lineX3 + width) {
            //position 2
            double stopY = point.y - (xUnGet[2] + xGet[2]) / totalNum * ySpec * 6;
            if (y > stopY && y < point.y) {
                onChartClick.onChartClick(2);
            }
        }

        return super.onTouchEvent(event);

    }
    CircleChartView.OnChartClick onChartClick;
    public void setOnChartClick(CircleChartView.OnChartClick onChartClick){
        this.onChartClick = onChartClick;
    }
    public interface OnChartClick{
        void onChartClick(int position);
    }

}
