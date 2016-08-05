package com.mjiayou.trecore.util;

import android.os.Environment;

import com.mjiayou.trecore.TCApp;

/**
 * Created by treason on 15/6/14.
 */
public class DirectoryUtil {

    private final String TAG = "DirectoryUtil";

    private static DirectoryUtil mDirectoryUtil;

    /**
     * 默认手机存储常用目录
     */
    public String SYSTEM_APP_CACHE;
    public String SYSTEM_APP_FILES;
    public String SYSTEM_APP_EXTERNAL_CACHE;
    public String SYSTEM_APP_EXTERNAL_FILES_DOWNLOADS;
    public String SYSTEM_APP_EXTERNAL_FILES_DCIM;

    /**
     * 系统默认SD卡存储常用目录
     */
    public String SDCARD_ROOT;
    public String SDCARD_DOWNLOAD;
    public String SDCARD_DOWNLOAD_ABSOLUTE;
    public String SDCARD_DCIM;

    /**
     * APP根目录
     */
    public String APP_ROOT;
    /**
     * APP 常用目录
     */
    public String APP_LOG;
    public String APP_IMAGE;
    public String APP_DOWNLOAD;
    public String APP_CACHE;
    public String APP_CACHE_IMAGE;
    public String APP_CACHE_DATA;

    /**
     * 构造函数
     */
    private DirectoryUtil() {
        SYSTEM_APP_CACHE = TCApp.get().getCacheDir().getPath() + "/"; // /data/data/cn.soccerapp.soccer/cache/
        SYSTEM_APP_FILES = TCApp.get().getFilesDir().getPath() + "/"; // /data/data/cn.soccerapp.soccer/files/
        try {
            SYSTEM_APP_EXTERNAL_CACHE = TCApp.get().getExternalCacheDir().getPath() + "/"; // /storage/emulated/0/Android/data/cn.soccerapp.soccer/cache/
        } catch (Exception e) {
            e.printStackTrace();
            SYSTEM_APP_EXTERNAL_CACHE = SYSTEM_APP_CACHE;
        }

        try {
            SYSTEM_APP_EXTERNAL_FILES_DOWNLOADS = TCApp.get().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath() + "/"; // /storage/emulated/0/Android/data/cn.soccerapp.soccer/files/Download
        } catch (Exception e) {
            e.printStackTrace();
            SYSTEM_APP_EXTERNAL_FILES_DOWNLOADS = SYSTEM_APP_FILES + "Download/";
        }

        try {
            SYSTEM_APP_EXTERNAL_FILES_DCIM = TCApp.get().getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath() + "/";  // /storage/emulated/0/Android/data/cn.soccerapp.soccer/files/DCIM
        } catch (Exception e) {
            e.printStackTrace();
            SYSTEM_APP_EXTERNAL_FILES_DCIM = SYSTEM_APP_FILES + "DCIM/";
        }

        try {
            SDCARD_ROOT = Environment.getExternalStorageDirectory().getPath() + "/"; // /storage/emulated/0/
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            SDCARD_DOWNLOAD = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/"; // /storage/emulated/0/Download/
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            SDCARD_DOWNLOAD_ABSOLUTE = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/"; // /storage/emulated/0/Download/
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            SDCARD_DCIM = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/"; // /storage/emulated/0/DCIM/
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * APP根目录
         */
        APP_ROOT = SDCARD_ROOT + TCApp.get().APP_NAME + "/"; // /storage/emulated/0/hoho/
        /**
         * APP 常用目录
         */
        APP_LOG = APP_ROOT + "log/"; // /storage/emulated/0/hoho/log/
        APP_IMAGE = APP_ROOT + "image/";  // /storage/emulated/0/hoho/image/
        APP_DOWNLOAD = APP_ROOT + "download/";  // /storage/emulated/0/hoho/download/
        APP_CACHE = SYSTEM_APP_EXTERNAL_CACHE; // /storage/emulated/0/Android/data/com.mjiayou.trecoredemo/cache/
        APP_CACHE_IMAGE = APP_CACHE + "image/"; // /storage/emulated/0/Android/data/com.mjiayou.trecoredemo/cache/image/
        APP_CACHE_DATA = APP_CACHE + "data/"; // /storage/emulated/0/Android/data/com.mjiayou.trecoredemo/cache/data/
    }

    /**
     * 单例模式
     */
    public static DirectoryUtil get() {
        if (mDirectoryUtil == null) {
            mDirectoryUtil = new DirectoryUtil();
        }
        return mDirectoryUtil;
    }

    /**
     * 获得默认的文件目录信息
     */
    public String getDirectoryInfo() {
        StringBuilder builder = new StringBuilder();

        builder.append("\n");
        builder.append("APP_NAME（应用名称） = ").append(TCApp.get().APP_NAME).append("\n");

        builder.append("\n");
        builder.append("SYSTEM_APP_CACHE = ").append("\n").append(SYSTEM_APP_CACHE).append("\n");
        builder.append("SYSTEM_APP_FILES = ").append("\n").append(SYSTEM_APP_FILES).append("\n");
        builder.append("SYSTEM_APP_EXTERNAL_CACHE = ").append("").append(SYSTEM_APP_EXTERNAL_CACHE).append("\n");
        builder.append("SYSTEM_APP_EXTERNAL_FILES_DOWNLOADS = ").append("\n").append(SYSTEM_APP_EXTERNAL_FILES_DOWNLOADS).append("\n");
        builder.append("SYSTEM_APP_EXTERNAL_FILES_DCIM = ").append("\n").append(SYSTEM_APP_EXTERNAL_FILES_DCIM).append("\n");

        builder.append("\n");
        builder.append("SDCARD_ROOT（SD卡根目录） = ").append("\n").append(SDCARD_ROOT).append("\n");
        builder.append("SDCARD_DOWNLOAD（SD卡下载目录） = ").append("\n").append(SDCARD_DOWNLOAD).append("\n");
        builder.append("SDCARD_DOWNLOAD_ABSOLUTE（SD卡下载目录） = ").append("\n").append(SDCARD_DOWNLOAD_ABSOLUTE).append("\n");
        builder.append("SDCARD_DCIM（SD卡相册目录） = ").append("\n").append(SDCARD_DCIM).append("\n");

        builder.append("\n");
        builder.append("APP_ROOT（APP根目录） = ").append("\n").append(APP_ROOT).append("\n");
        builder.append("APP_LOG（APP日志） = ").append("\n").append(APP_LOG).append("\n");
        builder.append("APP_IMAGE（APP图片） = ").append("\n").append(APP_IMAGE).append("\n");
        builder.append("APP_DOWNLOAD（APP下载） = ").append("\n").append(APP_DOWNLOAD).append("\n");
        builder.append("APP_CACHE（APP缓存） = ").append("\n").append(APP_CACHE).append("\n");
        builder.append("APP_CACHE_IMAGE（APP缓存-图片缓存） = ").append("\n").append(APP_CACHE_IMAGE).append("\n");
        builder.append("APP_CACHE_DATA（APP缓存-数据缓存） = ").append("\n").append(APP_CACHE_DATA).append("\n");

        builder.append("** More **").append("\n");
        builder.append("Environment.getDataDirectory = ").append("\n").append(Environment.getDataDirectory().getPath()).append("\n"); // /data
        builder.append("Environment.getDownloadCacheDirectory = ").append("\n").append(Environment.getDownloadCacheDirectory().getPath()).append("\n"); // /cache
        builder.append("Environment.getExternalStorageDirectory = ").append("\n").append(Environment.getExternalStorageDirectory().getPath()).append("\n"); // // /storage/emulated/0
        builder.append("Environment.getExternalStoragePublicDirectory = ").append("\n").append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()).append("\n"); // // /storage/emulated/0/Download
        builder.append("Environment.getRootDirectory = ").append("\n").append(Environment.getRootDirectory().getPath()).append("\n"); // /system
        builder.append("Environment.getExternalStorageState = ").append("\n").append(Environment.getExternalStorageState()).append("\n"); // mounted
        builder.append("Environment.isExternalStorageEmulated = ").append("\n").append(Environment.isExternalStorageEmulated()).append("\n"); // true
        builder.append("Environment.isExternalStorageRemovable = ").append("\n").append(Environment.isExternalStorageRemovable()).append("\n"); // false

        return builder.toString();
    }
}
