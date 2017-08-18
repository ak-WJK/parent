//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.kzb.parents.view.listview;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ListView;

public class ListViewInScrollview extends ListView {
    public ListViewInScrollview(Context context) {
        super(context);
    }

    public ListViewInScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewInScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
