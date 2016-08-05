package com.mjiayou.trecore.util;

import java.io.Closeable;

/**
 * Created by treason on 16/5/14.
 */
public class StreamUtil {

    public static void closeQuietly(Closeable stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }
}
