package com.mjiayou.trecore.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by treason on 16/5/14.
 */
public class ToastUtil {

    private static final String TAG = "ToastUtil";

    private static Toast mToast = null;

    /**
     * 显示Toast
     */
    public static void show(Context context, String text, int duration) {
        showToast(context, text, duration);
    }

    /**
     * 显示Toast - LENGTH_SHORT
     */
    public static void show(Context context, String text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast - core
     */
    private static void showToast(Context context, String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
        }
        mToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        mToast.show();
        LogUtil.i(TAG, text);
    }
}
