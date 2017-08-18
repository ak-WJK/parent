package com.kzb.baselibrary.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.kzb.baselibrary.config.MineConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 
 * @author tqf I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，
 *         区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread
 *         更有效率。 不要把计算工作放在 io() 中，可以避免创建不必要的线程
 * 
 *         COMPUTATION CPU 密集型计算，不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler
 *         使用的固定的线程池。 不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU. eg.
 *         Schedulers .subscribeOn(Schedulers.IO) .callback(new
 *         Schedulers.ScheduleCallBack() {
 * @Override public void onCallBack(Object result) { ImageView imag =
 *           (ImageView) findViewById(R.id.img);
 *           imag.setImageDrawable((Drawable)result); } }) .run(new
 *           Schedulers.SRunnable<Drawable>() {
 * @Override public Drawable callable() { return
 *           getResources().getDrawable(R.drawable.avatar_13); } });
 */
public class MineSchedulers {

	/** 适用于I/O操作 eg.读写文件、读写数据库、网络信息交互等 **/
	public static final int IO = 1001;
	public static ExecutorService cachedThreadPool;

	/** 适用于密集型计算操作 eg.例如图形的计算、加密算法计算、以及各种复杂动画插值计算的的等 **/
	public static final int COMPUTATION = 1002;
	public static ExecutorService singleThreadExecutor;

	private static Handler handler = new Handler(Looper.getMainLooper());

	/** 调度类型 **/
	private int mScheduleType = 0;

	/** 调度完成时回调 **/
	private ScheduleCallBack mCallback;

	public static MineSchedulers subscribeOn(int scheduleType) {
		MineSchedulers schedulers = new MineSchedulers ();
		schedulers.setmScheduleType(scheduleType);
		return schedulers;
	}

	public static int io() {
		return IO;
	}

	public static int computation() {
		return COMPUTATION;
	}

	public MineSchedulers callback(ScheduleCallBack callback) {
		this.mCallback = callback;
		return this;
	}

	public void run(ZbjRunnable runnable) {
		runnable.setCallBack(mCallback);
		switch (mScheduleType) {
		case IO:
			if (cachedThreadPool == null) {
				cachedThreadPool = Executors.newCachedThreadPool();
			}
			cachedThreadPool.execute(runnable);
			break;
		case COMPUTATION:
			if (singleThreadExecutor == null) {
				singleThreadExecutor = Executors.newCachedThreadPool();
			}
			singleThreadExecutor.execute(runnable);
			break;
		default:
			break;
		}
	}

	public int getmScheduleType() {
		return mScheduleType;
	}

	public void setmScheduleType(int mScheduleType) {
		this.mScheduleType = mScheduleType;
	}

	public static interface ScheduleCallBack {
		public void onCallBack(Object result);
	}

	public static abstract class SRunnable<T extends Object> extends
			ZbjRunnable {

		@Override
		public void run() {
			long t1 = System.currentTimeMillis();
			final T result = callable();
			if (MineConfig.DEBUG) {
				Log.d("---Schedulers---",
						"Schedulers为你节约时间：" + (System.currentTimeMillis() - t1)
								+ "毫秒");
			}
			if (callBack != null && result != null) {
				handler.post(new Runnable() {

					@Override
					public void run() {
						callBack.onCallBack(result);
					}
				});
			}
		}

		public abstract T callable();
	}

	public static abstract class SNullRunnable extends ZbjRunnable {

		public void run() {
			long t1 = System.currentTimeMillis();
			callable();
			if (MineConfig.DEBUG) {
				Log.d("---Schedulers---",
						"Schedulers为你节约时间：" + (System.currentTimeMillis() - t1)
								+ "毫秒");
			}
			if (callBack != null) {
				handler.post(new Runnable() {

					@Override
					public void run() {
						callBack.onCallBack(null);
					}
				});
			}
		}

		public void setCallBack(ScheduleCallBack callBack) {
			this.callBack = callBack;
		}

		public abstract void callable();
	}

	private static abstract class ZbjRunnable implements Runnable {

		public ScheduleCallBack callBack;

		public void setCallBack(ScheduleCallBack callBack) {
			this.callBack = callBack;
		}

	}

}
