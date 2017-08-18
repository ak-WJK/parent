package com.kzb.baselibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * 处理图片的工具类.
 * 
 */
public class MineImageUtils {



	public static Bitmap scaleBitmap(Bitmap img, int w, int h) {
		Bitmap result = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(result);
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG));
		canvas.drawBitmap(img, null, new Rect(0, 0, w, h), null);
		return result;
	}

	/**
	 * 图片去色,返回灰度图片
	 * 
	 * @param bmpOriginal
	 *            传入的图片
	 * @return 去色后的图片
	 */
	public static Bitmap toGrayBitmap(Bitmap bmpOriginal) {
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();

		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
				Config.RGB_565);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}

	/**
	 * 去色同时加圆角
	 * 
	 * @param bmpOriginal
	 *            原图
	 * @param pixels
	 *            圆角弧度
	 * @return 修改后的图片
	 */
	public static Bitmap toGrayRound(Bitmap bmpOriginal, int pixels) {
		return toRoundCorner(toGrayBitmap(bmpOriginal), pixels);
	}

	/***/
	/**
	 * 把图片变成圆角
	 * 
	 * @param bitmap
	 *            需要修改的图片
	 * @param pixels
	 *            圆角的弧度
	 * @return 圆角图片
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 使圆角功能支持BitampDrawable
	 * 
	 * @param bitmapDrawable
	 * @param pixels
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static BitmapDrawable toRoundCorner(BitmapDrawable bitmapDrawable,
			int pixels) {
		Bitmap bitmap = bitmapDrawable.getBitmap();
		bitmapDrawable = new BitmapDrawable(toRoundCorner(bitmap, pixels));
		return bitmapDrawable;
	}

	// 省内存方式加载资源文件图片
	public static Bitmap readBitmap(Context context, int resId) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// opts.inPreferredConfig = Config.RGB_565;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opts);
	}

	/**
	 * 480*800比例压缩图片
	 * 
	 * @param filePath
	 *            本地图片路径
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		return getSmallBitmap(filePath, 0);
	}

	/**
	 * 480*800 比例压缩 同时旋转图片
	 * 
	 * @param filePath
	 *            本地图片路径
	 * @param angle
	 *            旋转角度
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath, int angle) {
		Bitmap bitmap = null;// 此时返回bm为空
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = computeSampleSize(options, -1, 480 * 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		bitmap = BitmapFactory.decodeFile(filePath, options);
		if (angle != 0) {
			Matrix matrix = new Matrix();
			matrix.postRotate(angle);
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
					matrix, false);
			if (newBitmap != null)
				return newBitmap;
		}
		if (bitmap != null)
			return bitmap;
		else
			return null;
	}

	/**
	 * 根据尺寸压缩 同时旋转图片
	 * 
	 * @param filePath
	 *            路径
	 * @param maxSize
	 *            最大尺寸
	 * @param angle
	 *            角度
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath, int maxSize, int angle) {
		Bitmap bitmap = null;// 此时返回bm为空
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = computeSampleSize(options, -1, maxSize);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		bitmap = BitmapFactory.decodeFile(filePath, options);
		if (angle != 0) {
			Matrix matrix = new Matrix();
			matrix.postRotate(angle);
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
					matrix, false);
			if (newBitmap != null)
				return newBitmap;
		}
		if (bitmap != null)
			return bitmap;
		else
			return null;
	}

	/**
	 * 旋转图片
	 * 
	 * @param filePath
	 *            路径
	 * @param angle
	 *            角度
	 * @return
	 */
	public static Bitmap getAngleBitmap(String filePath, int angle) {
		Bitmap bitmap = BitmapFactory.decodeFile(filePath);
		if (angle != 0) {
			Matrix matrix = new Matrix();
			matrix.postRotate(angle);
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
					matrix, false);
			if (newBitmap != null)
				return newBitmap;
		}
		if (bitmap != null)
			return bitmap;
		else
			return null;
	}

	/**
	 * 旋转图片
	 * 
	 * @param bitmap
	 *            图片
	 * @param angle
	 *            角度
	 * @return
	 */
	public static Bitmap getAngleBitmap(Bitmap bitmap, int angle) {
		if (angle != 0) {
			Matrix matrix = new Matrix();
			matrix.postRotate(angle);
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
					matrix, false);
			if (newBitmap != null)
				return newBitmap;
		}
		if (bitmap != null)
			return bitmap;
		else
			return null;
	}

	/**
	 * 压缩图片到不超过100k
	 * 
	 * @param image
	 *            图片
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image) {
		return compressImage(image, 100 * 1024);
	}

	/**
	 * 指定文件大小来压缩图片
	 * 
	 * @param image
	 *            图片
	 * @param size
	 *            指定文件大小
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image, long size) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.PNG, 90, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
//		ZbjLog.e("start", baos.toByteArray().length / 1024 + "options:"
//				+ options);
		while (baos.toByteArray().length > size) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.PNG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
//		ZbjLog.e("end", baos.toByteArray().length + "options:" + options);
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片

		return bitmap;
	}

	/**
	 * 图片保存在本地
	 * 
	 * @param path
	 *            本地路径
	 * @param bit
	 *            图片
	 * @return
	 */
	public static File saveBitmap(String path, Bitmap bit) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
		}
		File f = new File(path);
		try {
			f.createNewFile();
			FileOutputStream fOut = null;
			fOut = new FileOutputStream(f);
			bit.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
			fOut.close();
		} catch (IOException e1) {
			f = null;
			e1.printStackTrace();
		}

		return f;
	}

	/**
	 * jpg图片保存在本地
	 * 
	 * @param path
	 *            本地路径
	 * @param bit
	 *            图片
	 * @return
	 */
	public static File saveBitmapJPG(String path, Bitmap bit) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
		}
		File f = new File(path);
		try {
			f.createNewFile();
			FileOutputStream fOut = null;
			fOut = new FileOutputStream(f);
			bit.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			fOut.flush();
			fOut.close();
		} catch (IOException e1) {
			f = null;
			e1.printStackTrace();
		}

		return f;
	}

	/**
	 * 本地文件读取文件并且按照不超过100*100像素取图片
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap getBitmapFromPath(String path) {

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize
		options.inSampleSize = computeSampleSize(options, -1, 100 * 100);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		Bitmap bitmap = BitmapFactory.decodeFile(path, options);

		return bitmap;
	}

	/**
	 * 
	 * getCutImage: 裁剪一个bitmap
	 * 
	 * @param bitmap
	 *            需要裁剪的图
	 * @param width
	 *            裁剪后的宽度
	 * @param height
	 *            裁剪后的高度
	 * @param isProportional
	 *            是否等比例缩放
	 * @return Bitmap
	 * @throws
	 * @author
	 * @since zhubajie　Ver 2.0.0
	 */
	public static Bitmap getCutImage(Bitmap bitmap, int width, int height,
			boolean isProportional) {

		int minEdge = 0;
		float scaleWidth = 0f;
		float scaleHeight = 0f;
		int beginPixW = 0;
		int beginPixH = 0;

		if (isProportional) {
			// 确定缩放标准，以长宽值小的为准
			minEdge = Math.min(bitmap.getWidth(), bitmap.getHeight());
			// 计算缩放率，新尺寸除原始尺寸
			scaleWidth = ((float) width) / bitmap.getWidth();
			scaleHeight = ((float) height) / bitmap.getHeight();

			beginPixW = bitmap.getWidth() - minEdge;
			beginPixH = bitmap.getHeight() - minEdge;

			// 缩放图片动作
			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleHeight);

			if (beginPixW < 0) {
				beginPixW = 0;
			}
			if (beginPixH < 0) {
				beginPixH = 0;
			}

			if (width >= bitmap.getWidth()) {
				width = bitmap.getWidth() - 1;
			}

			if (height >= bitmap.getHeight()) {
				height = bitmap.getHeight() - 1;
			}

			Bitmap newBitmap = Bitmap.createBitmap(bitmap, beginPixW / 2,
					beginPixH / 2, bitmap.getWidth() - beginPixW / 2 - 1,
					bitmap.getHeight() - beginPixH / 2 - 1, matrix, true);

			return newBitmap;
		} else {

			// 计算缩放率，新尺寸除原始尺寸
			scaleWidth = ((float) width) / bitmap.getWidth();
			scaleHeight = ((float) height) / bitmap.getHeight();

			// 缩放图片动作
			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleHeight);

			if (beginPixW < 0) {
				beginPixW = 0;
			}
			if (beginPixH < 0) {
				beginPixH = 0;
			}

			Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0,
					(bitmap.getWidth() - 1), (bitmap.getHeight() - 1), matrix,
					true);

			return newBitmap;
		}

	}

	private static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}
}
