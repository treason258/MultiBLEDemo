package com.mjiayou.trecore.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;

import java.util.List;

/**
 * Created by treason on 16/8/14.
 */
public class ProcessUtil {

    public static int getProcessId() {
        return Process.myPid();
    }

    public static String getProcessName(Context context, int processId) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == processId) {
                return procInfo.processName;
            }
        }
        return null;
    }

    public static String getProcessName(Context context) {
        return getProcessName(context, getProcessId());
    }

    public static int getTid() {
        return Process.myTid();
    }

    public static int getUid() {
        return Process.myUid();
    }
}
