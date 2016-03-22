package com.chalilayang.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import org.apache.http.util.EncodingUtils;

import com.homer.pager.Pager;
import com.homer.pager.PagerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PagerScrollActivity extends Activity {
	private Pager mPager;
	Bitmap mCurPageBitmap, mNextPageBitmap;
	Canvas mCurPageCanvas, mNextPageCanvas;
	PagerFactory pagerFactory;

	@SuppressLint("WrongCall") @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		DisplayMetrics dm = new DisplayMetrics();
		dm = this.getResources().getDisplayMetrics();
		int screenWidth = 480;
		int screenHeight = 800;
		System.out.println("screenWidth = " + screenWidth + "; screenHeight = " + screenHeight);
		Log.i("yangyong", dm.toString());
		
		mPager = new Pager(this, screenWidth, screenHeight);
		setContentView(mPager);

		mCurPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
		mNextPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);

		mCurPageCanvas = new Canvas(mCurPageBitmap);
		mNextPageCanvas = new Canvas(mNextPageBitmap);
		pagerFactory = new PagerFactory(screenWidth, screenHeight);	

		pagerFactory.setBgBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.bg));

		try {
			write2sd("test.txt");
			pagerFactory.openbook("/data/data/com.homer.pager/files/test.txt");
			pagerFactory.onDraw(mCurPageCanvas);
		} catch (IOException e1) {
			e1.printStackTrace();
			Toast.makeText(this, "电子书不存在！请把电子书放到SDCard更目录下...", Toast.LENGTH_SHORT).show();
		}

		mPager.setBitmaps(mCurPageBitmap, mCurPageBitmap);
		mPager.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				boolean ret = false;
				if (v == mPager) {
					if (e.getAction() == MotionEvent.ACTION_DOWN) {
						mPager.abortAnimation();
						mPager.calcCornerXY(e.getX(), e.getY());

						pagerFactory.onDraw(mCurPageCanvas);
						if (mPager.DragToRight()) {	// 向右滑动，显示前一页
							try {
								pagerFactory.prePage();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							if (pagerFactory.isfirstPage()) return false;
							pagerFactory.onDraw(mNextPageCanvas);
						} else {		// 向左滑动，显示后一页
							try {
								pagerFactory.nextPage();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							if (pagerFactory.islastPage()) return false;
							pagerFactory.onDraw(mNextPageCanvas);
						}
						mPager.setBitmaps(mCurPageBitmap, mNextPageBitmap);
					}

					ret = mPager.doTouchEvent(e);
					return ret;
				}
				return false;
			}

		});
	}
	
	private void write2sd(String fileName) {
		String text = "null";
		
		try {
			InputStream is = getResources().getAssets().open(fileName);
			int len = is.available();
			byte []buffer = new byte[len];
			is.read(buffer);
			text = EncodingUtils.getString(buffer, "utf-8");
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			FileOutputStream os = openFileOutput(fileName, MODE_PRIVATE);
			byte []buffer = text.getBytes();
			os.write(buffer);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}