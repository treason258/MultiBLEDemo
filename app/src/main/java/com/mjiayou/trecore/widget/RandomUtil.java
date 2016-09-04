package com.mjiayou.trecore.widget;

import java.util.Random;

/**
 * Created by treason on 16/9/3.
 */
public class RandomUtil {

    public static boolean isTrue() {
        return (new Random().nextInt(10) / 2 == 0);
    }

    public static int getInt3() {
        return new Random().nextInt(10) / 3;
    }
}
