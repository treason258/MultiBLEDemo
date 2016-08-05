package com.mjiayou.trecore.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.mjiayou.trecore.helper.ImageLoaderHelper;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import com.mjiayou.trecoredemo.R;

/**
 * Created by treason on 15/8/4.
 */
public class ImageViewUtil {

    private static final String TAG = "ImageViewUtil";

    private static final int MODE_IMAGELOADER = 1;

    private static int mMode = MODE_IMAGELOADER;

    /**
     * 展示图片-网络图片
     */
    public static void display(final Context context, final String url, final ImageView imageView, int imageResId) {
        switch (mMode) {
            default:
            case MODE_IMAGELOADER:
                ImageLoaderHelper.display(context, url, imageView, imageResId);
                break;
        }
    }

    public static void display(final Context context, final String url, final ImageView imageView) {
        display(context, url, imageView, R.drawable.tc_launcher);
    }

    /**
     * 下载图片
     */
    public static void downloadImage(String url, ImageLoadingListener listener) {
        switch (mMode) {
            default:
            case MODE_IMAGELOADER:
                ImageLoaderHelper.loadImage(url, listener);
                break;
        }
    }

    /**
     * 清除内存缓存
     */
    public static void clearMemoryCache() {
        switch (mMode) {
            default:
            case MODE_IMAGELOADER:
                ImageLoaderHelper.clearMemoryCache();
                break;
        }
    }

    /**
     * 清除硬盘缓存
     */
    public static void clearDiskCache() {
        switch (mMode) {
            default:
            case MODE_IMAGELOADER:
                ImageLoaderHelper.clearDiskCache();
                break;
        }
    }

    /**
     * 清除全部缓存
     */
    public static void clearAllCache() {
        switch (mMode) {
            default:
            case MODE_IMAGELOADER:
                ImageLoaderHelper.clearAllCache();
                break;
        }
    }

    /**
     * 展示图片-SD卡图片
     */
    public static void displayLocal(String imagePath, ImageView imageView) {
        imageView.setImageBitmap(BitmapUtil.getBitmap(imagePath));
    }

    /**
     * 展示图片-SD卡图片
     */
    public static void displayLocalCompress(String imagePath, ImageView imageView, int maxNumOfPixels) {
        imageView.setImageBitmap(BitmapUtil.getBitmapCompress(imagePath, maxNumOfPixels));
    }

    /**
     * 展示图片-Bitmap图片
     */
    public static void displayBitmap(Bitmap bitmap, ImageView imageView) {
        imageView.setImageBitmap(bitmap);
    }

    /**
     * 展示图片-Drawable图片
     */
    public static void displayDrawable(Context context, int resId, ImageView imageView) {
        imageView.setImageResource(resId);
    }

    /**
     * 展示图片-Drawable图片-压缩后展示
     */
    public static void displayDrawableCompress(Context context, int resId, ImageView imageView) {
        imageView.setImageDrawable(BitmapUtil.getBitmapDrawable(context, resId));
    }
}
