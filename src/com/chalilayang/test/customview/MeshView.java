package com.chalilayang.test.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.chalilayang.test.R;

/**
 * @author chalilayang 
 * TODO 
 * 2016-4-24
 */
public class MeshView extends View {
    private Bitmap bitmap;
    private final int WIDTH = 20;
    private final int HEIGHT = 20;
    private final int COUNT = (WIDTH + 1) * (HEIGHT + 1);
    private final float[] verts = new float[COUNT * 2];
    private final float[] orig = new float[COUNT * 2];

    public MeshView(Context context) {
        super(context);
        init();
    }

    public MeshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MeshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        setFocusable(true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.page1);
        float bitmapWidth = bitmap.getWidth();
        float bitmapHeight = bitmap.getHeight();
        int index = 0;
        for (int y = 0; y <= HEIGHT; y++) {
            float fy = bitmapHeight * y / HEIGHT;
            for (int x = 0; x <= WIDTH; x++) {
                float fx = bitmapWidth * x / WIDTH;
                orig[index * 2 + 0] = verts[index * 2 + 0] = fx;
                orig[index * 2 + 1] = verts[index * 2 + 1] = fy;
                index += 1;
            }
        }
        setBackgroundColor(Color.WHITE);
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawBitmapMesh(bitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);
    }

    // 工具方法,用于根据触摸事件的位置计算verts数组里各元素的值
    private void warp(float cx, float cy) {
        for (int i = 0; i < COUNT * 2; i += 2) {
            float dx = cx - orig[i + 0];
            float dy = cy - orig[i + 1];
            float dd = dx * dx + dy * dy;
            float distance = (float) Math.sqrt(dd);
            // 计算扭曲度，距离当前点(cx,cy)越远，扭曲度越小
            float pull = 80000 / ((float) (dd * distance));
            // 对verts数组(保存bitmap　上21 * 21个点经过扭曲后的坐标)重新赋值
            verts[i + 0] = orig[i + 0] + dx * pull;
            verts[i + 1] = orig[i + 1] + dy * pull;
        }
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                warp(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:

                break;

            case MotionEvent.ACTION_CANCEL:

                break;

            case MotionEvent.ACTION_POINTER_UP:

                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
