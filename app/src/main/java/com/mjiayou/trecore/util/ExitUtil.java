package com.mjiayou.trecore.util;


import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;

import java.util.ArrayList;

/**
 * 程序退出工具类，通过将Activity逐一finish的方法实现程序完全退出
 *
 * @author treason
 * @date 2014年9月27日
 */
public class ExitUtil {

    // 退出时间
    private static long mExitTime = 0;

    /**
     * 存储Activity队列
     */
    public static ArrayList<Activity> mListActivity = new ArrayList<Activity>();

    /**
     * 新建Activity则add到listActivity
     */
    public static void addActivity(Activity activity) {
        mListActivity.add(activity);
    }

    /**
     * 将listActivity中的Activity逐一finish掉，实现完全退出应用
     */
    public static void removeAllActivity() {
        if (mListActivity != null) {
            for (Activity activity : mListActivity) {
                if (activity != null && !activity.isFinishing())
                    activity.finish();
            }
        }
    }

    public static void removeActivity(Class<?> cls) {
        if (mListActivity != null) {
            for (Activity activity : mListActivity) {
                if (activity != null && !activity.isFinishing()) {
                    if (activity.getClass().getSimpleName().equals(cls.getSimpleName())) {
                        activity.finish();
                    }
                }
            }
        }
    }

    public static void removeActivityExcept(Class<?> cls) {
        if (mListActivity != null) {
            for (Activity activity : mListActivity) {
                if (activity != null && !activity.isFinishing())
                    if (!activity.getClass().getSimpleName().equals(cls.getSimpleName())) {
                        activity.finish();
                    }
            }
        }
    }

    /**
     * restartApp
     */
    public static void restartApp(Activity activity) {
        ExitUtil.removeActivityExcept(activity.getClass());

        Intent intent = activity.getPackageManager().getLaunchIntentForPackage(activity.getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    /**
     * 完全退出应用
     */
    public static void forceFinish(Activity activity) {
        removeAllActivity();
        activity.finish();
        System.exit(0);
    }

    /**
     * 再按一次退出
     */
    public static boolean pressAgainToExit(Activity activity, int keyCode, KeyEvent event) {
        if (event != null && event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - mExitTime) >= 2000) {
                ToastUtil.show(activity, "再按一次退出");
                mExitTime = currentTime;
            } else {
                removeAllActivity();
            }
            return true;
        }
        return false;
    }
}
