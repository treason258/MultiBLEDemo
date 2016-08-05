package com.mjiayou.trecore.helper;

import android.content.Context;

import com.facebook.stetho.Stetho;
import com.mjiayou.trecore.util.LogUtil;

/**
 * Created by treason on 16/7/15.
 */
public class StethoHelper {

    private static final String TAG = "StethoHelper";

    /**
     * 初始化
     */
    public static void init(Context context) {
        LogUtil.i("初始化数据 -> " + TAG);

        Stetho.initializeWithDefaults(context);

//        new OkHttpClient.Builder()
//                .addNetworkInterceptor(new StethoInterceptor())
//                .build();
    }
}
