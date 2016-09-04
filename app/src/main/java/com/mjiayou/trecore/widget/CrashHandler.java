package com.mjiayou.trecore.widget;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.mjiayou.trecore.TCApp;
import com.mjiayou.trecore.helper.Configs;
import com.mjiayou.trecore.helper.UmengHelper;
import com.mjiayou.trecore.util.AppUtil;
import com.mjiayou.trecore.util.DateUtil;
import com.mjiayou.trecore.util.DeviceUtil;
import com.mjiayou.trecore.util.DirectoryUtil;
import com.mjiayou.trecore.util.FileUtil;
import com.mjiayou.trecore.util.ThrowableUtil;

import java.util.Date;

/**
 * Created by treason on 15/11/18.
 */
public class CrashHandler {

    public static final String TAG = "CrashHandler";

    // CrashHandler实例
    private static CrashHandler mCrashHandler;
    // 程序的Context对象
    private Context mContext;
    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    /**
     * 保证只有一个实例
     */
    private CrashHandler() {
    }

    /**
     * 单例模式获取CrashHandler实例
     */
    public static CrashHandler get() {
        if (mCrashHandler == null) {
            mCrashHandler = new CrashHandler();
        }
        return mCrashHandler;
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        Log.i(TAG, "初始化数据 -> " + TAG);

        mContext = context;

        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                // 手动向Umeng提交 Throwable
                try {
                    UmengHelper.reportError(TCApp.get(), throwable);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 处理未捕获异常
                if (handleException(throwable)) { // 用户处理异常
                    try {
                        Thread.sleep(Configs.DELAY_CRASH_FINISH);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 退出程序
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                } else { // 如果用户没有处理则让系统默认的异常处理器来处理
                    if (mDefaultHandler != null) {
                        mDefaultHandler.uncaughtException(thread, throwable);
                    }
                }
            }
        });
    }

    /**
     * 自定义错误处理，收集错误信息、发送错误报告等操作均在此完成。
     *
     * @return true：如果处理了该异常信息；否则返回false。
     */
    private boolean handleException(Throwable throwable) {
        if (throwable == null) {
            return false;
        }

        try {
            // 使用Toast来显示异常信息
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(mContext, "很抱歉，程序出现异常，即将退出。", Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }.start();

            // 收集设备参数信息，保存错误信息到文件中
            dealWithCrash(throwable);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 收集设备参数信息，保存错误信息到文件中
     */
    private String dealWithCrash(Throwable throwable) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("----------------").append("\n");
        // version
        buffer.append("VersionInfo -> ").append("\n");
        buffer.append(AppUtil.getVersionInfo(mContext));
        buffer.append("----------------").append("\n");
        // info
        buffer.append("BuildInfo ->").append("\n");
        buffer.append(DeviceUtil.getBuildInfo());
        buffer.append("----------------").append("\n");
        // throwable
        buffer.append("ThrowableInfo -> ").append("\n");
        buffer.append(ThrowableUtil.getThrowableInfo(throwable));
        buffer.append("----------------").append("\n");

        try {
            // 设置保存路径和内容
            String fileDir = DirectoryUtil.get().APP_LOG;
            String fileName = "crash-" + DateUtil.parseString(new Date(), DateUtil.FormatType.FORMAT_302) + ".log";
            String filePath = fileDir + fileName;
            String fileContent = buffer.toString();

            // 打印保存路径和内容
            Log.e(TAG, "--------------------------------");
            Log.e(TAG, "Crash日志路径 ->");
            Log.e(TAG, filePath);
            Log.e(TAG, "--------------------------------");
            Log.e(TAG, "Crash日志内容 ->");
            Log.e(TAG, fileContent);
            Log.e(TAG, "--------------------------------");

            // 保存文件
            FileUtil.writeToFile(filePath, fileContent.getBytes());
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
