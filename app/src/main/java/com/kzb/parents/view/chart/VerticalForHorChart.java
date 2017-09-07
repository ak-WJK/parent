package com.kzb.parents.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.kzb.baselibrary.log.Log;
import com.kzb.parents.R;
import com.kzb.parents.util.DensityUtil;


/********************
 * 作者：malus
 * 日期：16/12/4
 * 时间：上午11:16
 * 注释：
 ********************/
public class VerticalForHorChart extends View {
    private double totalNum = 100;
    private int mWidth;
    private int mHeight;
    private int[] xUnGet = {0, 0, 0};
    private int[] xGet = {0, 0, 0};
    private String[] xTitle = {"最高", "我的", "平均"};
    private String title = "成绩情况";
    int xSpec;
    int ySpec;
    Point point;//原点
    private int texta, gray, green, red, blue;

    public VerticalForHorChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        texta = context.getResources().getColor(R.color.black);
        gray = context.getResources().getColor(R.color.text_c);
        green = context.getResources().getColor(R.color.theme_green);
        red = context.getResources().getColor(R.color.theme_red);
        blue = context.getResources().getColor(R.color.theme_green);
    }

    public VerticalForHorChart(Context context) {
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
        paint.setTextSize(DensityUtil.dip2px(getContext(), 17));
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 3));
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(title, mWidth / 2, DensityUtil.dip2px(getContext(), 30), paint);
        //原点
        point = new Point();
        paint.setAntiAlias(true);
        point.x = DensityUtil.dip2px(getContext(), 30);
        point.y = getHeight() - DensityUtil.dip2px(getContext(), 40);
        //画x线
        xSpec = (getWidth() - DensityUtil.dip2px(getContext(), 20)) / 3;
        int yLength = DensityUtil.dip2px(getContext(), 150);
        ySpec = yLength / 10;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 1));
        paint.setColor(Color.BLACK);
        paint.setTextSize(DensityUtil.dip2px(getContext(), 10));
        canvas.drawLine(point.x - DensityUtil.dip2px(getContext(), 5), point.y, point.x + xSpec * 5, point.y, paint);
        //画x点
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(DensityUtil.dip2px(getContext(), 15));
        for (int i = 0; i < 3; i++) {
            canvas.drawLine(point.x + xSpec * (1 + i), point.y, point.x + xSpec * (1 + i), point.y + DensityUtil.dip2px(getContext(), 4), paint);
            canvas.drawText(xTitle[i], point.x + xSpec * (1 + i) - xSpec / 2, point.y + DensityUtil.dip2px(getContext(), 30), paint);
        }
        //画y线
        canvas.drawLine(point.x, point.y + DensityUtil.dip2px(getContext(), 5), point.x, point.y - yLength, paint);
        paint.setTextAlign(Paint.Align.LEFT);
        //画y点
        int intSpec = (int) totalNum / 10;
        paint.setTextSize(DensityUtil.dip2px(getContext(), 10));
        for (int i = 0; i < 10; i++) {
            canvas.drawLine(point.x - DensityUtil.dip2px(getContext(), 5), point.y - ySpec * (1 + i), point.x, point.y - ySpec * (1 + i), paint);
            if (i == 9) {
                canvas.drawText(intSpec * (i + 1) + "", point.x - DensityUtil.dip2px(getContext(), 25), point.y - ySpec * (1 + i) + DensityUtil.dip2px(getContext(), 5), paint);
            } else {
                canvas.drawText(intSpec * (i + 1) + "", point.x - DensityUtil.dip2px(getContext(), 20), point.y - ySpec * (1 + i) + DensityUtil.dip2px(getContext(), 5), paint);
            }


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
            double stopY = point.y - (xUnGet[i] + xGet[i]) / totalNum * ySpec * 10;
            paint.setColor(red);
            paint.setStrokeWidth(xSpec - DensityUtil.dip2px(getContext(), 30));
            paint.setTextSize(DensityUtil.dip2px(getContext(), 14));
            canvas.drawLine(point.x + xSpec * (1 + i) - xSpec / 2, point.y, point.x + xSpec * (1 + i) - xSpec / 2, (int) stopY, paint);
            if (xUnGet[i] > 0) {
                paint.setColor(texta);
                double y = stopY + (1.0 / totalNum * ySpec * 10) * (xUnGet[i] / 2.0) + DensityUtil.dip2px(getContext(), 6);
                canvas.drawText((int) xUnGet[i] + "分", point.x + xSpec * (1 + i) - xSpec / 2 - DensityUtil.dip2px(getContext(), 10), (float) y, paint);
            }
        }

        for (int i = 0; i < xGet.length; i++) {
            double stopY = point.y - xGet[i] / totalNum * ySpec * 10;

            switch (i) {
                case 0:
                    paint.setColor(green);
                    break;
                case 1:
                    paint.setColor(red);
                    break;
                case 2:
                    paint.setColor(gray);
                    break;
                default:
                    paint.setColor(green);
                    break;
            }

            paint.setStrokeWidth(xSpec - DensityUtil.dip2px(getContext(), 30));
            canvas.drawLine(point.x + xSpec * (1 + i) - xSpec / 2, point.y, point.x + xSpec * (1 + i) - xSpec / 2, (int) stopY, paint);
            if (xGet[i] > 0) {
                paint.setColor(texta);
                double y = stopY + (1.0 / totalNum * ySpec * 10) * (xGet[i] / 2.0) + DensityUtil.dip2px(getContext(), 6);
                ;
                canvas.drawText((int) xGet[i] + "分", point.x + xSpec * (1 + i) - xSpec / 2 - DensityUtil.dip2px(getContext(), 10), (float) y, paint);
            }
        }

        int tX = getWidth() / 2 + DensityUtil.dip2px(getContext(), 30);
        int tY = getHeight() - DensityUtil.dip2px(getContext(), 30);
        //暂时注释
//
//        paint.setColor(green);
//        paint.setTextAlign(Paint.Align.LEFT);
//        paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 20));
//        RectF rectF = new RectF(tX-DensityUtil.dip2px(getContext(),150),tY-DensityUtil.dip2px(getContext(),10),tX-DensityUtil.dip2px(getContext(),110),tY+DensityUtil.dip2px(getContext(),10));
//        canvas.drawRoundRect(rectF,DensityUtil.dip2px(getContext(),4),DensityUtil.dip2px(getContext(),4),paint);
//
////        canvas.drawLine(tX- DensityUtil.dip2px(getContext(), 150), tY, tX- DensityUtil.dip2px(getContext(), 110), tY, paint);
//
//        paint.setColor(texta);
//        paint.setTextSize(DensityUtil.dip2px(getContext(),16));
//        canvas.drawText("已掌握数量",tX-DensityUtil.dip2px(getContext(),105),tY+DensityUtil.dip2px(getContext(),6),paint);
//
//        paint.setColor(red);
//        paint.setTextAlign(Paint.Align.LEFT);
//        paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 20));
//        rectF = new RectF(tX,tY-DensityUtil.dip2px(getContext(),10),tX+DensityUtil.dip2px(getContext(),40),tY+DensityUtil.dip2px(getContext(),10));
//        canvas.drawRoundRect(rectF,DensityUtil.dip2px(getContext(),4),DensityUtil.dip2px(getContext(),4),paint);
//
////        canvas.drawLine(tX, tY, tX + DensityUtil.dip2px(getContext(), 50), tY, paint);
//        paint.setColor(texta);
//
//        paint.setTextSize(DensityUtil.dip2px(getContext(),16));
//        canvas.drawText("未掌握数量",tX+DensityUtil.dip2px(getContext(),55),tY+DensityUtil.dip2px(getContext(),6),paint);

//        //我的成绩
//        stopX = (num2 / 100.0) * 5 * xSpec;
//        paint.setColor(Color.RED);
//        paint.setStrokeWidth(DensityUtil.dip2px(getContext(),30));
//        canvas.drawLine(point.x, point.y - yLength + DensityUtil.dip2px(getContext(), 70), (int) stopX, point.y - yLength + DensityUtil.dip2px(getContext(), 70), paint);
    }

    public void setNum(float max, int[] unGet, int[] get) {

        Log.e("kzb", "max=" + max);
        Log.e("kzb", "unGet=" + unGet.length);
        Log.e("kzb", "get=" + get.length);

        if (max > 0) {
            totalNum = max;
        }
        xUnGet = unGet;
        xGet = get;
        postInvalidate();
    }

    //    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        float x = event.getX();
//        float y = event.getY();
//
//        int lineX1 = point.x + xSpec / 2;
//        int lineX2 = point.x + xSpec * 3 / 2;
//        int lineX3 = point.x + xSpec * 5 / 2;
//        int width = DensityUtil.dip2px(getContext(),15);
//        if (x > lineX1 - width && x < lineX1 + width) {
//            //position 0
//            double stopY = point.y - (xUnGet[0] + xGet[0]) / totalNum * ySpec * 10;
//            if (y > stopY && y < point.y) {
//                onChartClick.onChartClick(0);
//            }
//        }
//        if (x > lineX2 - width && x < lineX2 + width) {
//            //position 1
//            double stopY = point.y - (xUnGet[1] + xGet[1]) / totalNum * ySpec * 10;
//            if (y > stopY && y < point.y) {
//                onChartClick.onChartClick(1);
//            }
//        }
//        if (x > lineX3 - width && x < lineX3 + width) {
//            //position 2
//            double stopY = point.y - (xUnGet[2] + xGet[2]) / totalNum * ySpec * 10;
//            if (y > stopY && y < point.y) {
//                onChartClick.onChartClick(2);
//            }
//        }
//
//        return super.onTouchEvent(event);
//
//    }
    CircleChartView.OnChartClick onChartClick;

    public void setOnChartClick(CircleChartView.OnChartClick onChartClick) {
        this.onChartClick = onChartClick;
    }

    public interface OnChartClick {
        void onChartClick(int position);
    }

}
