package com.mjiayou.trecore.test.jni;

/**
 * Created by treason on 16/8/7.
 */
public class TestJNIUtil {

    public native String getHello();

    private static volatile TestJNIUtil mInstance;

    /**
     * 单例模式，获取实例
     */
    public static TestJNIUtil get() {
        if (mInstance == null) {
            synchronized (TestJNIUtil.class) {
                if (mInstance == null) {
                    mInstance = new TestJNIUtil();
                }
            }
        }
        return mInstance;
    }

    static {
        /**
         * cd app/build/intermediates/classes/alpha/debug/
         * cd app/src/main/
         * mkdir jni
         * cd app/src/main/jni/
         *
         * cd app/src/main/java/
         * javah -jni com.mjiayou.trecore.test.jni.TestJNIUtil
         */
        System.loadLibrary("testjni");
    }
}
