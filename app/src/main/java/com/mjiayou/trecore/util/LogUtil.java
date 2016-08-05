package com.mjiayou.trecore.util;

import android.util.Log;

import com.mjiayou.trecore.TCApp;

import java.util.Stack;

/**
 * Created by treason on 16/5/14.
 */
public class LogUtil {

    private static final String TAG = "LogUtil";
    private static final String TAG_TRACE_TIME = "LogUtil_TraceTime";

    /**
     * Send a VERBOSE log message.
     */
    public static void v(String tag, String msg) {
        if (TCApp.get().DEBUG_LOG) {
            Log.v(tag, buildMessage(msg));
        }
    }

    public static void v(String msg) {
        if (TCApp.get().DEBUG_LOG) {
            Log.v(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a DEBUG_LOG log message.
     */
    public static void d(String tag, String msg) {
        if (TCApp.get().DEBUG_LOG) {
            Log.d(tag, buildMessage(msg));
        }
    }

    public static void d(String msg) {
        if (TCApp.get().DEBUG_LOG) {
            Log.d(TAG, buildMessage(msg));
        }
    }

    /**
     * Send an INFO log message.
     */
    public static void i(String tag, String msg) {
        if (TCApp.get().DEBUG_LOG) {
            Log.i(tag, buildMessage(msg));
        }
    }

    public static void i(String msg) {
        if (TCApp.get().DEBUG_LOG) {
            Log.i(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a WARN log message
     */
    public static void w(String tag, String msg) {
        if (TCApp.get().DEBUG_LOG) {
            Log.w(tag, buildMessage(msg));
        }
    }

    public static void w(String msg) {
        if (TCApp.get().DEBUG_LOG) {
            Log.w(TAG, buildMessage(msg));
        }
    }

    /**
     * Send an ERROR log message.
     */
    public static void e(String tag, String msg, Throwable throwable) {
        if (TCApp.get().DEBUG_LOG) {
            Log.e(tag, buildMessage(msg), throwable);
        }
    }

    public static void e(String tag, String msg) {
        if (TCApp.get().DEBUG_LOG) {
            Log.e(tag, buildMessage(msg));
        }
    }

    public static void e(String msg) {
        if (TCApp.get().DEBUG_LOG) {
            Log.e(TAG, buildMessage(msg));
        }
    }

    public static void e(String msg, Throwable throwable) {
        if (TCApp.get().DEBUG_LOG) {
            Log.e(TAG, buildMessage(msg), throwable);
        }
    }


    /**
     * printStackTrace
     */
    public static void printStackTrace(Throwable throwable) {
        if (TCApp.get().DEBUG_LOG) {
            Log.e(TAG, buildMessage("printStackTrace -> "), throwable);
            throwable.printStackTrace();
        }
    }

    /**
     * TraceTime
     */
    private static Stack<Long> traceTimeStack = new Stack<>();

    public static void resetTraceTime() {
        traceTimeStack.clear();
    }

    public static void startTraceTime(String msg) {
        traceTimeStack.push(System.currentTimeMillis());
        if (TCApp.get().DEBUG_LOG) {
            Log.d(TAG_TRACE_TIME, msg + " time = " + System.currentTimeMillis());
        }
    }

    public static void stopTraceTime(String msg) {
        if (!traceTimeStack.isEmpty()) {
            long time = traceTimeStack.pop();
            long diff = System.currentTimeMillis() - time;
            if (TCApp.get().DEBUG_LOG) {
                Log.d(TAG_TRACE_TIME, "[" + diff + "]" + msg + " time = " + System.currentTimeMillis());
            }
        }
    }

    /**
     * Building Message
     */
    protected static String buildMessage(String msg) {
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
        StringBuilder builder = new StringBuilder();
        if (TCApp.get().DEBUG_LOG_SHOW_PATH) {
            builder.append(caller.getClassName());
            builder.append(".");
            builder.append(caller.getMethodName());
            builder.append("(): \n");
        }
        builder.append(msg);
        return builder.toString();
    }
}
