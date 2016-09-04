package com.mjiayou.trecore;

/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
            佛祖保佑       永无BUG
*/

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.mjiayou.trecore.helper.GsonHelper;
import com.mjiayou.trecore.helper.ImageLoaderHelper;
import com.mjiayou.trecore.helper.PullToRefreshHelper;
import com.mjiayou.trecore.helper.StethoHelper;
import com.mjiayou.trecore.helper.UmengHelper;
import com.mjiayou.trecore.helper.VolleyHelper;
import com.mjiayou.trecore.util.LogUtil;
import com.mjiayou.trecore.util.ProcessUtil;
import com.mjiayou.trecore.widget.Configs;

/**
 * Created by treason on 16/5/14.
 */
public class TCApp extends Application {

    // APP-TAG
    private static final String TAG = "TCApp";
    // APP_NAME
    public final String APP_NAME = "trecoredemo";

    // 显示生命周期
    protected final String TAG_LIFE_CYCLE = "app_life_cycle";
    protected boolean SHOW_LIFE_CYCLE = true;

    // mApplication
    private static TCApp mApp;
    private static Context mContext;

    // mRequestQueue
    private RequestQueue mRequestQueue;

    /**
     * 获取Application对象
     */
    public static TCApp get() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        mContext = getApplicationContext();

        initApp();

        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onCreate");
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | process id -> " + ProcessUtil.getProcessId());
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | process name -> " + ProcessUtil.getProcessName(mContext));
        }
    }

    /**
     * 初始化APP
     */
    public void initApp() {

        /**
         * 初始化APP
         */
        LogUtil.i(TAG, "初始化数据 -> " + TAG);

        /**
         * 初始化 配置信息
         */
        Configs.init(mContext);

        /**
         * 初始化 第三方库
         */
        VolleyHelper.get().init(mContext);
        GsonHelper.init(mContext);
        ImageLoaderHelper.init(mContext);
        UmengHelper.init(mContext);
        PullToRefreshHelper.init(mContext);
        StethoHelper.init(mContext);
    }
}