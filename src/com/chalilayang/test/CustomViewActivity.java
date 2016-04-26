package com.chalilayang.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Bundle;
import android.view.Display;
import android.view.View;

import com.chalilayang.test.customview.BitmapMeshView;
import com.chalilayang.test.customview.MeshView;
import com.chalilayang.test.customview.PlasmaView;

public class CustomViewActivity extends Activity {
    /* load our native library */
    static {
        System.loadLibrary("plasma");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        setContentView(R.layout.activity_customview_layout);
    }
}
