package com.mjiayou.trecore.widget;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

/**
 * Created by treason on 16/6/6.
 */
public class CodeSegment extends FragmentActivity {

    private void init() {

        // 设置屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // 设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 设置屏幕方向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 人像-竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // 风景-横屏
    }
}
