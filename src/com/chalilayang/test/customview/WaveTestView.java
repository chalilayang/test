package com.chalilayang.test.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.chalilayang.test.R;

public class WaveTestView extends View {

    private Bitmap bitmap;
    private Bitmap shadowMask;
    private Paint paint;
    private Shader maskShader;
    private int maxAlpha = 0xFF;

    private int width, height;
    private int centerX, centerY;

    private int bitmapWidth = 90;
    private int bitmapHeight = 20;

    private int mMeshWidth = 90;
    private int mMeshHeight = 90;

    private float[] mVerts;
    private float[] mOrigin;

    public WaveTestView(Context context) {
        this(context, null);
    }

    public WaveTestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("NewApi")
    public WaveTestView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private Handler mHandler;
    private boolean isAnimationing = false;
    private float mKey = 0.0F;
    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.page1);
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
        this.mMeshHeight = this.mMeshWidth * bitmapHeight / bitmapWidth;
        final int WIDTH = this.mMeshWidth;
        final int HEIGHT = this.mMeshHeight;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mVerts = new float[(WIDTH + 1) * (HEIGHT + 1) * 2];
        this.mOrigin = new float[(WIDTH + 1) * (HEIGHT + 1) * 2];
        int index = 0;
        for(int indexY = 0; indexY <= HEIGHT; indexY ++) {
            float fy = bitmapHeight * indexY / HEIGHT;
            for (int indexX = 0; indexX <= WIDTH; indexX ++) {
                float fx = bitmapWidth * indexX / WIDTH;
                this.mOrigin[index * 2 + 0] = this.mVerts[index * 2 + 0] = (float) (0.5*fx);
                this.mOrigin[index * 2 + 1] = this.mVerts[index * 2 + 1] = (float) (0.5*fy);
                index ++;
            }
        }
//        RialWave();
        mHandler = new Handler();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        centerX = width / 2;
        centerY = height / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (isAnimationing) {
                    stopAnimate();
                } else {
                    startAnimate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    private Runnable animate = new Runnable() {
        @Override
        public void run() {
            mKey += 0.1F;
            flagWave();
            invalidate();
            mHandler.postDelayed(this, 100);
        }
    };

    public void reset() {
        System.arraycopy(this.mOrigin, 0, this.mVerts, 0, this.mOrigin.length);
        invalidate();
    }

    public void startAnimate() {
        if (isAnimationing) {
            return;
        }
        Log.i("yangyong", "startAnimate()");
        mHandler.post(animate);
        isAnimationing = true;
    }
    public void stopAnimate() {
        Log.i("yangyong", "stopAnimate()");
        mHandler.removeCallbacks(animate);
        isAnimationing = false;
        reset();
    }
    @Override
    public void onDraw(Canvas canvas) {
        Log.i("yangyong", "width = " + width + "  height = " + height);
        canvas.drawBitmapMesh(this.bitmap, this.mMeshWidth, this.mMeshHeight, this.mVerts, 0, null, 0, null);
    }


    private void flagWave() {
        final int WIDTH = this.mMeshWidth;
        final int HEIGHT = this.mMeshHeight;
        int index = 0;
        for(int indexY = 0; indexY <= HEIGHT; indexY ++) {
            for (int indexX = 0; indexX <= WIDTH; indexX ++) {
                double offSet = Math.sin((float)(indexX * 1.0 / WIDTH * 3 * Math.PI + Math.PI * mKey));
                this.mVerts[index * 2 + 0] = (float) (this.mOrigin[index * 2 + 0] + 30 * offSet);
                this.mVerts[index * 2 + 1] = (float) (this.mOrigin[index * 2 + 1] + 30 * offSet);
                index ++;
            }
        }
    }

    private void RialWave() {
        final int WIDTH = this.mMeshWidth;
        final int HEIGHT = this.mMeshHeight;
        final int centerXpos = (int)(WIDTH * 0.5+0.5);
        final int centerYpos = (int)(HEIGHT * 0.5+0.5);
        float centerX = this.mOrigin[((WIDTH+1) * centerYpos + centerXpos)*2+0];
        float centerY = this.mOrigin[((WIDTH+1) * centerYpos + centerXpos)*2+1];
        final float DELTA = 30.0F;
        double mDelta = 0;
        int index = 0;
        for(int indexY = 0; indexY <= HEIGHT; indexY ++) {
            for (int indexX = 0; indexX <= WIDTH; indexX ++) {
                float tmpX = this.mOrigin[((WIDTH+1) * indexY + indexX)*2+0];
                float tmpY = this.mOrigin[((WIDTH+1) * indexY + indexX)*2+1];
                double distance
                        = Math.sqrt(
                        Math.pow((tmpX - centerX), 2) + Math.pow((tmpY - centerY), 2)
                );
                double angle = Math.atan((tmpX - centerX) * 1.0F /(tmpX - centerX));
                if (distance >= DELTA) {
                    mDelta = DELTA * Math.exp(-Math.max(0, distance - DELTA));
                } else {
                    mDelta = 0;
                }
                if (mDelta > 0) {
                    this.mVerts[index * 2 + 0] = this.mOrigin[index * 2 + 0] + (float) (mDelta * Math.cos(angle));
                    this.mVerts[index * 2 + 1] = this.mOrigin[index * 2 + 1] + (float) (mDelta * Math.sin(angle));
                } else {
                    this.mVerts[index * 2 + 0] = this.mOrigin[index * 2 + 0];
                    this.mVerts[index * 2 + 1] = this.mOrigin[index * 2 + 1];
                }
                index ++;
            }
        }
    }
}
