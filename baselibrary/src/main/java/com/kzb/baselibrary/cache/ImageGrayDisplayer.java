package com.kzb.baselibrary.cache;

import android.graphics.Bitmap;

import com.kzb.baselibrary.utils.MineImageUtils;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;



public class ImageGrayDisplayer implements BitmapDisplayer
{
	private boolean putGray = false;

	public ImageGrayDisplayer(boolean putGray)
	{
		this.putGray = putGray;
	}

	@Override
	public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
		
		Bitmap tmpBitmap = putGray ? MineImageUtils.toGrayBitmap(bitmap) : bitmap;
		imageAware.setImageBitmap(tmpBitmap);
	}	
}
