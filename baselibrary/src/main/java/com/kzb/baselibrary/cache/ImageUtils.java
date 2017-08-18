package com.kzb.baselibrary.cache;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.L;

@SuppressLint("NewApi")
public class ImageUtils {
	// private static final int MEMORY_MAX_SIZE = 2 * 1024 * 1024; // 2M
	private static final int DISC_MAX_SIZE = 50 * 1024 * 1024; // 10M

	public static void Init(Context context) {

		if (null == context) {

			return;
		}

		DisplayImageOptions displayOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisk(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.resetViewBeforeLoading(false).considerExifParams(false)
				.build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context.getApplicationContext())
				// 内存缓存的设置选项 (最大图片宽度,最大图片高度) 默认当前屏幕分辨率
				// .memoryCacheExtraOptions(480, 800)
				// 设置图片加载线程的优先级,默认为Thread.NORM_PRIORITY-1
				// 注:如果设置了taskExecutor或者taskExecutorForCachedImages 此设置无效
				.threadPriority(Thread.NORM_PRIORITY)
				// 设置拒绝缓存在内存中一个图片多个大小 默认为允许,(同一个图片URL)根据不同大小的imageview保存不同大小图片
				.denyCacheImageMultipleSizesInMemory()
				// 设置内存缓存的最大大小 默认为一个当前应用可用内存的1/8
				// .memoryCacheSize(MEMORY_MAX_SIZE)
				// 设置硬盘缓存的最大大小
				.diskCacheSize(DISC_MAX_SIZE)
				// 设置硬盘缓存的文件的最多个数
				.diskCacheFileCount(80)
				.defaultDisplayImageOptions(displayOptions)
				// 设置显示图片线程池大小，默认为3
				// 注:如果设置了taskExecutor或者taskExecutorForCachedImages 此设置无效
				.threadPoolSize(10)
				// 设置内存缓存最大大小占当前应用可用内存的百分比 默认为一个当前应用可用内存的1/
				.memoryCacheSizePercentage(10)
//				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();
		L.disableLogging();
		ImageLoader.getInstance().init(config);
	}

	public static void displayImage(ImageView view, String url, int defaultRes) {

		displayImage(view, url, defaultRes, false, null);
	}

	public static void displayImage(ImageView view, String url,
			boolean bPutGray, int defaultRes) {

		displayImage(view, url, defaultRes, bPutGray, null);
	}

	public static void displaySmallImage(ImageView view, String url,
			int defaultRes, boolean bPutGray, ImageLoadingListener listener) {

		BitmapDisplayer displayer = null;
		if (bPutGray) {

			displayer = new ImageGrayDisplayer(bPutGray);
		} else {

			displayer = new SimpleBitmapDisplayer();
		}

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(defaultRes)
				.showImageForEmptyUri(defaultRes).showImageOnFail(defaultRes)
				.displayer(displayer).cacheInMemory(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();

		ImageLoader.getInstance().displayImage(url, view, options, listener);
	}

	public static void displayImage(ImageView view, String url, int defaultRes,
			boolean bPutGray, ImageLoadingListener listener) {

		BitmapDisplayer displayer = null;
		if (bPutGray) {

			displayer = new ImageGrayDisplayer(bPutGray);
		} else {

			displayer = new SimpleBitmapDisplayer();
		}

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(defaultRes)
				.showImageForEmptyUri(defaultRes).showImageOnFail(defaultRes)
				.displayer(displayer).cacheInMemory(true).cacheOnDisk(true)
				// .imageScaleType(ImageScaleType.NONE)
				.build();

		ImageLoader.getInstance().displayImage(url, view, options, listener);
	}

	public static void displayBigImage(ImageView view, String url,
			int defaultRes, boolean bPutGray, ImageLoadingListener listener) {

		BitmapDisplayer displayer = null;
		if (bPutGray) {

			displayer = new ImageGrayDisplayer(bPutGray);
		} else {

			displayer = new SimpleBitmapDisplayer();
		}

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(defaultRes)
				.showImageForEmptyUri(defaultRes).showImageOnFail(defaultRes)
				.displayer(displayer).cacheInMemory(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.NONE).build();

		ImageLoader.getInstance().displayImage(url, view, options, listener);
	}

}
