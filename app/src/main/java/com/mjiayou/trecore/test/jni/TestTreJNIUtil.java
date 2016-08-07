package com.mjiayou.trecore.test.jni;

/**
 * Created by treason on 16/8/7.
 */
public class TestTreJNIUtil {

    public native String getHello();

    static {
//        javah -jni com.mjiayou.trecore.test.jni.TestTreJNIUtil
        System.loadLibrary("test_trejni");
    }
}
