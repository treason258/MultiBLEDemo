package com.mjiayou.trecore.test;

/**
 * Created by treason on 16/8/7.
 */
public class TestTreJNIUtil {

    public native String getHello();

    static {
        System.loadLibrary("test_trejni");
    }

}
