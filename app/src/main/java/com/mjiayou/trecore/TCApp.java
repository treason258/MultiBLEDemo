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
import com.android.volley.toolbox.Volley;
import com.mjiayou.trecore.helper.GsonHelper;
import com.mjiayou.trecore.helper.ImageLoaderHelper;
import com.mjiayou.trecore.helper.PullToRefreshHelper;
import com.mjiayou.trecore.helper.StethoHelper;
import com.mjiayou.trecore.helper.UmengHelper;
import com.mjiayou.trecore.helper.VolleyHelper;
import com.mjiayou.trecore.util.LogUtil;
import com.mjiayou.trecore.widget.TCConfigs;

/**
 * Created by treason on 16/5/14.
 */
public class TCApp extends Application {

    // APP TAG
    private static final String TAG = "TCApp";
    // APP_NAME
    public final String APP_NAME = "tcdemo";

    // mApplication
    private static TCApp mApp;
    private static Context mContext;

    // DEBUG开关
    public boolean DEBUG_SERVER; // 服务器
    public boolean DEBUG_TEST; // TestActivity
    public boolean DEBUG_LOG; // LOG
    public boolean DEBUG_LOG_SHOW_PATH; // LOG PATH
    public boolean DEBUG_VOLLEY;
    public boolean DEBUG_GSON;
    public boolean DEBUG_IMAGE_LOADER;
    public boolean DEBUG_UMENG;
    public boolean DEBUG_PULL_TO_REFRESH;
    public boolean DEBUG_ALIYUN_OSS;
    // SWITCH开关
    public boolean SWITCH_UMENG_ANALYTICS_ON; // 友盟统计
    // 打包模式
    private final int MODE_DEBUG = 0; // 调试模式
    private final int MODE_RELEASE = 1; // 生产模式
    // 当前APP打包模式
    private int mMode = MODE_DEBUG;

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
         * 启动模式
         */
        switch (mMode) {
            case MODE_DEBUG:
                // DEBUG模式开关
                DEBUG_SERVER = true;
                DEBUG_TEST = true;
                DEBUG_LOG = true;
                DEBUG_LOG_SHOW_PATH = false;
                DEBUG_VOLLEY = false;
                DEBUG_GSON = false;
                DEBUG_IMAGE_LOADER = false;
                DEBUG_UMENG = true;
                DEBUG_PULL_TO_REFRESH = false;
                DEBUG_ALIYUN_OSS = false;
                // SWITCH-一些开关
                SWITCH_UMENG_ANALYTICS_ON = true;
                break;
            case MODE_RELEASE:
                // DEBUG模式开关
                DEBUG_SERVER = false;
                DEBUG_TEST = false;
                DEBUG_LOG = false;
                DEBUG_LOG_SHOW_PATH = false;
                DEBUG_VOLLEY = false;
                DEBUG_GSON = false;
                DEBUG_IMAGE_LOADER = false;
                DEBUG_UMENG = false;
                DEBUG_PULL_TO_REFRESH = false;
                DEBUG_ALIYUN_OSS = false;
                // SWITCH-一些开关
                SWITCH_UMENG_ANALYTICS_ON = true;
                break;
        }

        /**
         * 初始化 配置信息
         */
        TCConfigs.init(mContext);

        /**
         * 初始化 第三方库
         */
        VolleyHelper.init(mContext);
        GsonHelper.init(mContext);
        ImageLoaderHelper.init(mContext);
        UmengHelper.init(mContext);
        PullToRefreshHelper.init(mContext);
        StethoHelper.init(mContext);
    }

    /**
     * 获取RequestQueue对象
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }
}