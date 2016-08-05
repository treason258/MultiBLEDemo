package com.mjiayou.trecore.helper;

import android.content.Context;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.mjiayou.trecore.TCApp;
import com.mjiayou.trecore.util.LogUtil;

/**
 * Created by treason on 16/5/17.
 */
public class PullToRefreshHelper {

    private static final String TAG = "PullToRefreshHelper";

    /**
     * 初始化
     */
    public static void init(Context context) {
        LogUtil.i("初始化数据 -> " + TAG);

        PullToRefreshBase.DEBUG = TCApp.get().DEBUG_PULL_TO_REFRESH;
    }
}
