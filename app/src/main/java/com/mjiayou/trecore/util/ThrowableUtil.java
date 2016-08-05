package com.mjiayou.trecore.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by treason on 15-12-29.
 */
public class ThrowableUtil {

    /**
     * getThrowableInfo()
     */
    public static String getThrowableInfo(Throwable throwable) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        Throwable throwableCause = throwable.getCause();
        while (throwableCause != null) {
            throwableCause.printStackTrace(printWriter);
            throwableCause = throwableCause.getCause();
        }
        printWriter.close();
        return writer.toString();
    }
}
