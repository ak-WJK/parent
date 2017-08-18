package com.kzb.parents.view.rate;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.kzb.parents.R;


public class RateTextView extends TextView {
	private float mWidthRate = 1;
	private float mWidthHeightRate = 1;

	public RateTextView(Context context) {
		super(context);
	}

	public RateTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typeArray = context.obtainStyledAttributes(attrs,
				R.styleable.RateImageView);
		mWidthRate = typeArray.getFloat(R.styleable.RateImageView_widthRate, 1);
		mWidthHeightRate = typeArray.getFloat(
				R.styleable.RateImageView_widthHeightRate, 1);
		typeArray.recycle();
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = getMeasuredWidth();
		float height = width * mWidthHeightRate;
		setMeasuredDimension( width, (int) height);
		postInvalidate();
		setLayoutParams(getLayoutParams());
	}
}
