package com.mjiayou.trecore.util;

/**
 * Created by treason on 16/5/14.
 */
public class ClickUtil {

    private final static String TAG = "ClickUtil";
    private final static int TIME_FAST_CLICK = 300; // 鉴定是否是重复点击的时间间隔，即x毫秒时间内的连续点击则认为是重复点击
    private final static int TIME_REPEAT_CLICK = 2000; // 坚定连续点击有效时间

    /**
     * 按钮连续点击
     */
    private static long lastFastClickTime;


    /**
     * 按钮连续点击
     */
    public synchronized static boolean isFastClick() {
        LogUtil.i(TAG, "检查到点击");
        long time = System.currentTimeMillis();
        if (time - lastFastClickTime < TIME_FAST_CLICK) {
            LogUtil.i(TAG, "检查到重复点击");
            return true;
        }
        lastFastClickTime = time;
        return false;
    }

    /**
     * 多次重复点击处理事件
     */
    public static class RepeatClickThread extends Thread {
        public static volatile int aliveCount = 0;

        @Override
        public void run() {
            aliveCount++;
            LogUtil.i(TAG, "aliveCount++ aliveCount -> " + aliveCount);
            try {
                Thread.sleep(TIME_REPEAT_CLICK);
                LogUtil.i(TAG, "Thread.sleep(TIME_REPEAT_CLICK);");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (aliveCount > 0) {
                aliveCount--;
                LogUtil.i(TAG, "aliveCount-- aliveCount -> " + aliveCount);
            }
            super.run();
        }
    }

    /**
     * 测试
     */
    public void test() {
        if (ClickUtil.isFastClick()) {
            return;
        }

        LogUtil.i(TAG, "点击一下");
    }
}
