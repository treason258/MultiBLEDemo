package com.mjiayou.trecore.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by treason on 16/5/14.
 */
public class AssetUtil {

    private static final String TAG = "AssetUtil";

    public static final String DIR_FONTS = "fonts";
    public static final String DIR_LIBS = "libs";

    private static AssetManager mAssetManager = null;

    /**
     * 获取AssetManager对象
     */
    public static AssetManager getAssetManager(Context context) {
        if (mAssetManager == null) {
            mAssetManager = context.getAssets();
        }
        return mAssetManager;
    }

    /**
     * 获取assets文件夹path目录内文件名列表
     */
    public static String[] getAssetArray(Context context, String dir) throws IOException {
        return getAssetManager(context).list(dir);
    }

    /**
     * 获取assets文件夹path目录内文件名列表
     */
    public static List<String> getAssetList(Context context, String dir) throws IOException {
        return ConvertUtil.parseArrayToList(getAssetArray(context, dir));
    }

    /**
     * 读取assets文件夹中文件数据流
     */
    public static InputStream getAssetInputStream(Context context, String path) throws IOException {
        return getAssetManager(context).open(path);
    }

    /**
     * 获取字体对象-font_bgqc.ttf
     */
    public static Typeface getTypeface(Context context) {
        Typeface typeface = null;
//        try {
//            typeface = Typeface.createFromAsset(getAssetManager(context), "fonts/font_bgqc.ttf");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return typeface;
    }

    /**
     * 获取assets文件夹下文件路径，如getFilePath("js/testjs.html") -> file:///android_asset/js/testjs.html
     */
    public static String getFilePath(String path) {
        return "file:///android_asset/" + path;
    }

    /**
     * 获取Raw下的UriString
     */
    public static String getRawUriString(Context context, int rawResId) {
        return "android.resource://" + context.getPackageName() + "/" + rawResId;
    }
}
