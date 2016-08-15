package com.mjiayou.trecore.util;

/**
 * Created by treason on 16/5/19.
 */
public class ThreadUtil {

    public static void start(Runnable runnable) {
        new Thread(runnable).start();
    }

    public static String getThreadName() {
        return Thread.currentThread().getName();
    }
}
