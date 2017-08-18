package com.kzb.parents.view.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

/********************
 * 作者：malus
 * 日期：17/2/9
 * 时间：下午6:05
 * 注释：
 ********************/

public class MyDrawable extends Drawable {

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(-100,-100,getIntrinsicWidth()+100,getIntrinsicHeight()+100,new Paint());
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
