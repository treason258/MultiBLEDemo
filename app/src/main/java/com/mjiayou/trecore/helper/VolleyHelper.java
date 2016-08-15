package com.mjiayou.trecore.helper;

import android.content.Context;

import com.android.volley.VolleyLog;
import com.mjiayou.trecore.util.LogUtil;
import com.mjiayou.trecore.widget.TCConfigs;

/**
 * Created by treason on 16/5/14.
 */
public class VolleyHelper {

    private static final String TAG = "VolleyHelper";

    /**
     * 初始化
     */
    public static void init(Context context) {
        LogUtil.i("初始化数据 -> " + TAG);

        VolleyLog.DEBUG = TCConfigs.DEBUG_VOLLEY;
    }
}
