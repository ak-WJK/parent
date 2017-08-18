//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.kzb.parents.view.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;
import android.widget.ListView;

public class ExpandableListViewInScrollview extends ExpandableListView {
    public ExpandableListViewInScrollview(Context context) {
        super(context);
    }

    public ExpandableListViewInScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableListViewInScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
