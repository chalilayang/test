package com.chalilayang.test.customview;

import com.chalilayang.test.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CustomSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {

	private static int span = 5;
	private MyThread wtf;
	private Paint paint;
	int sleepSpan = 150; // 动画的时延ms
	Bitmap logo_s, logo_l; // logo图片引用
	int width_s; // 图片大小
	int height_s;
	float currentX_s; // 图片位置
	float currentY_s;
	float currentX_l; // 图片位置
	float currentY_l;
	private Rect src;
	private RectF dst;
	private int currentAlpha = 0;

	public CustomSurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.getHolder().addCallback(this);// 设置生命周期回调接口的实现者
		paint = new Paint();// 创建画笔
		paint.setAntiAlias(true);// 打开抗锯齿
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		logo_s = BitmapFactory.decodeResource(getResources(),
				R.drawable.dropbox);
		logo_l = BitmapFactory.decodeResource(getResources(),
				R.drawable.behance);
		// TODO Auto-generated method stub
		width_s = logo_s.getWidth();
		height_s = logo_s.getHeight();
		src = new Rect(0, 0, 0, height_s);
		// 大图片的位置
		currentX_s = 5;
		currentY_s = 5;
		dst = new RectF(currentX_s, currentY_s, currentX_s, currentY_s
				+ height_s);
		currentX_l = currentX_s + width_s - logo_l.getWidth();
		// 小图片的位置
		currentY_l = currentY_s + height_s;
		currentAlpha = 0;
		wtf = new MyThread();
		wtf.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		// 绘制黑填充矩形清背景
		super.onDraw(canvas);

		paint.setAlpha(120);// 设置不透明度为255
		paint.setColor(Color.BLACK);// 设置画笔颜色
		canvas.drawColor(Color.BLACK);
		// 进行平面贴图
		if (logo_s == null || logo_l == null)
			return;
		src.right += span;
		dst.right += span;
		canvas.drawBitmap(logo_s, src, dst, paint);
		paint.setAlpha(currentAlpha);
		canvas.drawBitmap(logo_l, currentX_l, currentY_l, paint);
		// canvas.drawBitmap(bitmap, src, dst, paint);
		/*
		 * Rect src = new Rect(x1, y2, cx1,cy1); Rect dst = new Rect(x2, y2,
		 * cx2, cy2); 第一个矩形，是你想截取的bitmap里面的哪一段。 第二个矩形，是你想显示在屏幕上的什么位置。
		 * 两个矩形可以不一样大小，在绘制的时候，会自动拉伸。
		 */
	}

	class MyThread extends Thread {
		@SuppressLint("WrongCall")
		public void run() {
			SurfaceHolder mholder = CustomSurfaceView.this.getHolder();// 获取回调接口
			// 绘制tatans
			try {
				sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (int i = 0; i <= width_s / span; i++) {
				Canvas canvas = mholder.lockCanvas();// 获取画布
				try {
					synchronized (mholder) // 同步
					{
						onDraw(canvas);// 进行
					}
					sleep(20);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (canvas != null) {
						mholder.unlockCanvasAndPost(canvas);
					}
				}

			}
			// 绘制天坦智慧
			for (int i = 0; i <= 25; i++) {
				currentAlpha = i * 10;
				Canvas canvas = mholder.lockCanvas();// 获取画布
				try {
					synchronized (mholder) // 同步
					{
						onDraw(canvas);// 进行
					}
					sleep(25);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (canvas != null) {
						mholder.unlockCanvasAndPost(canvas);
					}
				}

			}
		}
	}

}
