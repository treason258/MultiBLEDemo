package com.mjiayou.trecore.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * Created by treason on 15/6/8.
 */
public class DeviceUtil {

    private final static String TAG = "DeviceUtil";

    /**
     * 获得设备信息
     */
    public static String getDeviceInfo(Context context) {
        StringBuilder builder = new StringBuilder();

        // getBuildInfo
        builder.append("\n");
        builder.append("**** getBuildInfo ****").append("\n");
        builder.append(getBuildInfo());

//        // android.os.Build
//        builder.append("\n");
//        builder.append("**** android.os.Build ****").append("\n");
//        builder.append("BOARD（运营商） = ").append(android.os.Build.BOARD).append("\n");
//        builder.append("BOOTLOADER = ").append(android.os.Build.BOOTLOADER).append("\n");
//        builder.append("BRAND = ").append(android.os.Build.BRAND).append("\n");
//        builder.append("CPU_ABI = ").append(android.os.Build.CPU_ABI).append("\n");
//        builder.append("CPU_ABI2 = ").append(android.os.Build.CPU_ABI2).append("\n");
//        builder.append("DEVICE（驱动） = ").append(android.os.Build.DEVICE).append("\n");
//        builder.append("DISPLAY（显示） = ").append(android.os.Build.DISPLAY).append("\n");
//        builder.append("FINGERPRINT（指纹） = ").append(android.os.Build.FINGERPRINT).append("\n");
//        builder.append("HARDWARE（硬件） = ").append(android.os.Build.HARDWARE).append("\n");
//        builder.append("HOST = ").append(android.os.Build.HOST).append("\n");
//        builder.append("ID = ").append(android.os.Build.ID).append("\n");
//        builder.append("MANUFACTURER（生产厂家） = ").append(android.os.Build.MANUFACTURER).append("\n");
//        builder.append("MODEL（机型） = ").append(android.os.Build.MODEL).append("\n");
//        builder.append("PRODUCT = ").append(android.os.Build.PRODUCT).append("\n");
//        builder.append("RADIO = ").append(android.os.Build.RADIO).append("\n");
//        builder.append("SERIAL = ").append(android.os.Build.SERIAL).append("\n");
//        builder.append("TAGS = ").append(android.os.Build.TAGS).append("\n");
//        builder.append("TIME = ").append(android.os.Build.TIME).append("\n");
//        builder.append("TYPE = ").append(android.os.Build.TYPE).append("\n");
//        builder.append("UNKNOWN = ").append(android.os.Build.UNKNOWN).append("\n");
//        builder.append("USER = ").append(android.os.Build.USER).append("\n");
//        builder.append("VERSION.RELEASE（固件版本） = ").append(android.os.Build.VERSION.RELEASE).append("\n");
//        builder.append("VERSION.CODENAME（固件版本） = ").append(android.os.Build.VERSION.CODENAME).append("\n");
//        builder.append("VERSION.INCREMENTAL（基带版本） = ").append(android.os.Build.VERSION.INCREMENTAL).append("\n");
//        builder.append("VERSION.SDK（SDK版本） = ").append(android.os.Build.VERSION.SDK).append("\n");
//        builder.append("VERSION.SDK_INT（SDK版本） = ").append(android.os.Build.VERSION.SDK_INT).append("\n");

        // java.util.Locale
        builder.append("\n");
        builder.append("**** java.util.Locale ****").append("\n");
        builder.append("Country = ").append(java.util.Locale.getDefault().getCountry()).append("\n");
        builder.append("Language = ").append(java.util.Locale.getDefault().getLanguage()).append("\n");
        builder.append("DisplayCountry = ").append(java.util.Locale.getDefault().getDisplayCountry()).append("\n");
        builder.append("DisplayLanguage = ").append(java.util.Locale.getDefault().getDisplayLanguage()).append("\n");

        // DisplayMetrics
        builder.append("\n");
        builder.append("**** DisplayMetrics ****").append("\n");
        builder.append("Screen = ").append(getScreenInfo(context)).append("\n");
        builder.append("Width = ").append(getScreenWidth(context)).append("\n");
        builder.append("Height = ").append(getScreenHeight(context)).append("\n");

        // TelephonyManager
        builder.append("\n");
        builder.append("**** TelephonyManager ****").append("\n");
        builder.append("DeviceId（IMEI国际移动台设备识别码） = ").append(getIMEI(context)).append("\n");
        builder.append("SubscriberId（IMSI国际移动客户识别码） = ").append(getIMSI(context)).append("\n");
        builder.append("PhoneNumber（手机号码） = ").append(getPhoneNumber(context)).append("\n");
        builder.append("SimOperatorName（服务商） = ").append(getSimOperatorName(context)).append("\n");
        builder.append("SimSerialNumber（SIM卡序列号） = ").append(getSimSerialNumber(context)).append("\n");

        // WifiManager
        builder.append("\n");
        builder.append("**** WifiManager ****").append("\n");
        builder.append("MacAddress = ").append(getMacAddress(context)).append("\n");
        builder.append("SSID = ").append(getSSID(context)).append("\n");

        // LocationManager
        builder.append("\n");
        builder.append("**** LocationManager ****").append("\n");
        builder.append("Longitude（经度） = ").append(getLongitude(context)).append("\n");
        builder.append("Latitude（纬度） = ").append(getLatitude(context)).append("\n");

        // ConnectivityManager
        builder.append("\n");
        builder.append("**** ConnectivityManager ****").append("\n");
        builder.append("网络是否连接 = ").append(isNetConnected(context)).append("\n");
        builder.append("网络是否是wifi连接 = ").append(isWifiConnected(context)).append("\n");

        // StatFs
        builder.append("\n");
        builder.append("**** 手机存储容量使用情况 ****").append("\n");
        builder.append("手机存储的总空间大小 = ").append(getInternalTotalMemorySize() / (1024 * 1024) / 1024 + "G").append("\n");
        builder.append("手机存储的可用空间大小 = ").append(getInternalAvailableMemorySize() / (1024 * 1024) / 1024 + "G").append("\n");
        builder.append("是否存在SD卡 = ").append(existExternalCard()).append("\n");
        builder.append("SD卡存储的总空间大小 = ").append(getExternalTotalMemorySize() / (1024 * 1024) / 1024 + "G").append("\n");
        builder.append("SD卡存储的可用空间大小 = ").append(getExternalAvailableMemorySize() / (1024 * 1024) / 1024 + "G").append("\n");

        // other
        builder.append("\n");
        builder.append("**** other ****").append("\n");
        builder.append("UDID = ").append(getUDID(context)).append("\n");
        builder.append("ActionBar的高度 = ").append(getActionBarHeight(context)).append("\n");
        builder.append("状态栏高度 = ").append(getStatusBarHeight(context)).append("\n");

        return builder.toString();
    }

    /**
     * getBuildInfo()
     */
    public static String getBuildInfo() {
        StringBuffer buffer = new StringBuffer();
        for (Field field : Build.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                buffer.append(field.getName() + " = " + field.get(null).toString()).append("\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Field field : Build.VERSION.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                buffer.append(field.getName() + " = " + field.get(null).toString()).append("\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return buffer.toString();
    }

    // ******************************** DisplayMetrics ********************************

    /**
     * 获得DisplayMetrics对象
     */
    private static DisplayMetrics getDisplayMetrics(Context context) {
        if (context instanceof Activity) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics;
        } else {
            return context.getResources().getDisplayMetrics();
        }
    }

    /**
     * 获得屏幕尺寸
     */
    public static String getScreenInfo(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        if (displayMetrics != null) {
            return displayMetrics.widthPixels + "*" + displayMetrics.heightPixels;
        }
        return null;
    }

    /**
     * 获得屏幕宽度（widthPixels）
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        if (displayMetrics != null) {
            return displayMetrics.widthPixels;
        }
        return 0;
    }

    /**
     * 获得屏幕高度（heightPixels）
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        if (displayMetrics != null) {
            return displayMetrics.heightPixels;
        }
        return 0;
    }

    /**
     * 获得Display对象
     */
    private static Display getDisplay(Context context) {
        if (context instanceof Activity) {
            return ((Activity) context).getWindowManager().getDefaultDisplay();
        }
        return null;
    }

    /**
     * 获得屏幕尺寸
     */
    @Deprecated
    public static String getScreenInfoByDisplay(Context context) {
        Display display = getDisplay(context);
        if (display != null) {
            return display.getWidth() + "*" + display.getHeight();
        }
        return "null";
    }

    // ******************************** TelephonyManager ********************************

    /**
     * 获得TelephonyManager对象
     */
    private static TelephonyManager getTelephonyManager(Context context) throws Exception {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager;
    }

    /**
     * 获得IMEI（telephonyManager.getDeviceId）
     */
    public static String getIMEI(Context context) {
        try {
            TelephonyManager telephonyManager = getTelephonyManager(context);
            return telephonyManager.getDeviceId();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    /**
     * 获得IMSI（telephonyManager.getSubscriberId）
     */
    public static String getIMSI(Context context) {
        try {
            TelephonyManager telephonyManager = getTelephonyManager(context);
            return telephonyManager.getSubscriberId();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    /**
     * 获得手机号（telephonyManager.getLine1Number）
     */
    public static String getPhoneNumber(Context context) {
        try {
            TelephonyManager telephonyManager = getTelephonyManager(context);
            return telephonyManager.getLine1Number();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    /**
     * 获得服务商名称（telephonyManager.getSimOperatorName）
     */
    public static String getSimOperatorName(Context context) {
        try {
            TelephonyManager telephonyManager = getTelephonyManager(context);
            return telephonyManager.getSimOperatorName();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    /**
     * 获得SIM卡序列号（telephonyManager.getSimSerialNumber）
     */
    public static String getSimSerialNumber(Context context) {
        try {
            TelephonyManager telephonyManager = getTelephonyManager(context);
            return telephonyManager.getSimSerialNumber();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    // ******************************** WifiManager ********************************

    /**
     * 获得WifiInfo对象
     */
    private static WifiInfo getWifiInfo(Context context) throws Exception {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo;
    }

    /**
     * 获得Mac地址（wifiInfo.getMacAddress()）
     */
    public static String getMacAddress(Context context) {
        try {
            WifiInfo wifiInfo = getWifiInfo(context);
            return wifiInfo.getMacAddress();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    /**
     * 获得SSID（wifiInfo.getSSID()）
     */
    public static String getSSID(Context context) {
        try {
            WifiInfo wifiInfo = getWifiInfo(context);
            return wifiInfo.getSSID();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    // ******************************** LocationManager ********************************

    /**
     * 获得Location对象
     */
    private static Location getLocation(Context context) throws Exception {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        return location;
    }

    /**
     * 获得经度（location.getLongitude()）
     */
    public static double getLongitude(Context context) {
        try {
            Location location = getLocation(context);
            return location.getLongitude();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return 0;
    }

    /**
     * 获得纬度（location.getLatitude()）
     */
    public static double getLatitude(Context context) {
        try {
            Location location = getLocation(context);
            return location.getLatitude();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return 0;
    }

    // ******************************** ConnectivityManager ********************************

    /**
     * 获得NetworkInfo对象
     */
    public static NetworkInfo getNetworkInfo(Context context) throws Exception {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isNetConnected(Context context) {
        try {
            NetworkInfo networkInfo = getNetworkInfo(context);
            if (networkInfo != null && networkInfo.isConnected() && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifiConnected(Context context) {
        try {
            NetworkInfo networkInfo = getNetworkInfo(context);
            if (isNetConnected(context) && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return false;
    }

    // ******************************** StatFs ********************************

    /**
     * 是否存在SD卡并可读写操作
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static boolean existExternalCard() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                !Environment.isExternalStorageRemovable()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 手机存储的总空间大小
     */
    public static float getInternalTotalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        float totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    /**
     * 手机存储的可用空间大小
     */
    public static float getInternalAvailableMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        float availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * SD卡存储的总空间大小
     */
    public static float getExternalTotalMemorySize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        float totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    /**
     * SD卡存储的可用空间大小
     */
    public static float getExternalAvailableMemorySize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        float availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    // ******************************** other ********************************

    /**
     * 获得手机唯一标识
     */
    public static String getUDID(Context context) {
        StringBuilder builder = new StringBuilder();

        builder.append(getIMEI(context));
        builder.append(getIMSI(context));
        if (!TextUtils.isEmpty(getMacAddress(context))) {
            builder.append(getMacAddress(context));
        }

        return MD5Util.md5(builder.toString());
    }

    /**
     * 获取标准的UUID字符串
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 手机震动
     */
    public static void vibrate(final Context context, long milliseconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }

    /**
     * 获取ActionBar的高度，单位为px
     */
    public static int getActionBarHeight(Context context) {
        int actionBarHeight = 0;
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    /**
     * 获取手机状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight;
        int x;
        Class<?> c;
        Object obj;
        Field field;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return 0;
    }

    /**
     * 截屏
     */
    public static Bitmap takeScreenShot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        int statusBarHeight = getStatusBarHeight(activity);
        int width = activity.getResources().getDisplayMetrics().widthPixels;
        int height = activity.getResources().getDisplayMetrics().heightPixels;
        try {
            bitmap = Bitmap.createBitmap(bitmap, 0, statusBarHeight, width, height - statusBarHeight);
            return bitmap;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static void printMemoryInfo(Context context) {
        // Runtime.getRuntime().maxMemory()
        long maxMemory = Runtime.getRuntime().maxMemory();
        LogUtil.i(TAG, "maxMemory -> " + maxMemory + "B");
        LogUtil.i(TAG, "maxMemory -> " + maxMemory / 1024 / 1024 + "MB");
        LogUtil.i(TAG, "maxMemory -> " + (maxMemory >> 10 >> 10) + "MB");
        LogUtil.i(TAG, "maxMemory -> " + (maxMemory >> 20) + "MB");

        // android.os.Process
        int myPid = android.os.Process.myPid();
        int myUid = android.os.Process.myUid();
        int myTid = android.os.Process.myTid();
        LogUtil.i(TAG, "myPid -> " + myPid);
        LogUtil.i(TAG, "myUid -> " + myUid);
        LogUtil.i(TAG, "myTid -> " + myTid);

        // ActivityManager
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int memoryClass = activityManager.getMemoryClass();
        int largeMemoryClass = activityManager.getLargeMemoryClass();
        LogUtil.i(TAG, "memoryClass -> " + memoryClass + "MB");
        LogUtil.i(TAG, "largeMemoryClass -> " + largeMemoryClass + "MB");

        // activityManager.getMemoryInfo
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        long totalMem = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            totalMem = memoryInfo.totalMem;
        }
        long availMem = memoryInfo.availMem;
        long threshold = memoryInfo.threshold;
        boolean lowMemory = memoryInfo.lowMemory;
        LogUtil.i(TAG, "totalMem-系统总共内存 -> " + (totalMem >> 20) + "MB");
        LogUtil.i(TAG, "availMem-系统剩余内存 -> " + (availMem >> 20) + "MB");
        LogUtil.i(TAG, "threshold-当系统剩余内存低于 -> " + (threshold >> 20) + "MB时就看成低内存运行");
        LogUtil.i(TAG, "lowMemory-系统是否处于低内存运行 -> " + lowMemory);

        // activityManager.getRunningAppProcesses()
        List<ActivityManager.RunningAppProcessInfo> list = activityManager.getRunningAppProcesses();
        for (int i = 0; i < list.size(); i++) {
            LogUtil.i(TAG, "pid -> " + list.get(i).pid);
            LogUtil.i(TAG, "uid -> " + list.get(i).uid);
            LogUtil.i(TAG, "processName -> " + list.get(i).processName);
            LogUtil.i(TAG, "pkgList -> " + ConvertUtil.parseListToString(ConvertUtil.parseArrayToList(list.get(i).pkgList)));
        }

        // Debug
        long nativeHeapSize = Debug.getNativeHeapSize();
        long nativeHeapAllocatedSize = Debug.getNativeHeapAllocatedSize();
        long nativeHeapFreeSize = Debug.getNativeHeapFreeSize();
        LogUtil.i(TAG, "nativeHeapSize-当前进程navtive堆中已使用的内存大小 -> " + (nativeHeapSize >> 10) + "KB");
        LogUtil.i(TAG, "nativeHeapAllocatedSize-当前进程navtive堆中已经剩余的内存大小 -> " + (nativeHeapAllocatedSize >> 10) + "KB");
        LogUtil.i(TAG, "nativeHeapFreeSize-当前进程navtive堆本身总的内存大小 -> " + (nativeHeapFreeSize >> 10) + "KB");

        // Debug.getMemoryInfo
        Debug.MemoryInfo memoryInfoDebug = new Debug.MemoryInfo();
        Debug.getMemoryInfo(memoryInfoDebug);
        int dalvikPrivateDirty = memoryInfoDebug.dalvikPrivateDirty;
        int dalvikPss = memoryInfoDebug.dalvikPss;
        int dalvikSharedDirty = memoryInfoDebug.dalvikSharedDirty;
        int nativePrivateDirty = memoryInfoDebug.nativePrivateDirty;
        int nativePss = memoryInfoDebug.nativePss;
        int nativeSharedDirty = memoryInfoDebug.nativeSharedDirty;
        int otherPrivateDirty = memoryInfoDebug.otherPrivateDirty;
        int otherPss = memoryInfoDebug.otherPss;
        int otherSharedDirty = memoryInfoDebug.otherSharedDirty;
        LogUtil.i(TAG, "dalvikPrivateDirty -> " + dalvikPrivateDirty + "KB");
        LogUtil.i(TAG, "dalvikPss -> " + dalvikPss + "KB");
        LogUtil.i(TAG, "dalvikSharedDirty -> " + dalvikSharedDirty + "KB");
        LogUtil.i(TAG, "nativePrivateDirty -> " + nativePrivateDirty + "KB");
        LogUtil.i(TAG, "nativePss -> " + nativePss + "KB");
        LogUtil.i(TAG, "nativeSharedDirty -> " + nativeSharedDirty + "KB");
        LogUtil.i(TAG, "otherPrivateDirty -> " + otherPrivateDirty + "KB");
        LogUtil.i(TAG, "otherPss -> " + otherPss + "KB");
        LogUtil.i(TAG, "otherSharedDirty -> " + otherSharedDirty + "KB");
    }
}
