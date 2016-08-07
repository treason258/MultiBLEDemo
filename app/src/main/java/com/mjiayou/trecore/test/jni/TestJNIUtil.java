package com.mjiayou.trecore.test.jni;

/**
 * Created by treason on 16/8/7.
 */
public class TestJNIUtil {

    public native String getHello();

    static {
//        cd build/intermediates/classes/alpha/debug/
//        javah -jni com.mjiayou.trecore.test.jni.TestJNIUtil
        System.loadLibrary("testjni");
    }
}
