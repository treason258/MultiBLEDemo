package com.mjiayou.trecore.util;

import android.hardware.Camera;
import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by treason on 16/6/30.
 */
public class CameraUtil {

    private static final String TAG = "CameraUtil";

    private static CameraSizeComparator mCameraSizeComparator = new CameraSizeComparator();

    /**
     * printSupportedSizes
     */
    public static void printSupportedSizes(List<Camera.Size> sizes) {
        for (Camera.Size size : sizes) {
            Log.i(TAG, "width -> " + size.width + " | height -> " + size.height);
        }
    }

    /**
     * printSupportedPreviewSizes
     */
    public static void printSupportedPreviewSizes(Camera camera) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            Log.i(TAG, "printSupportedPreviewSizes");
            printSupportedSizes(parameters.getSupportedPreviewSizes());
        }
    }

    /**
     * printSupportedPictureSizes
     */
    public static void printSupportedPictureSizes(Camera camera) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            Log.i(TAG, "printSupportedPictureSizes");
            printSupportedSizes(parameters.getSupportedPictureSizes());
        }
    }


    /**
     * 获取合适的预览尺寸
     */
    public static Camera.Size getFitPreviewSize(List<Camera.Size> supportedPreviewSizes, int minWidth) {
        Collections.sort(supportedPreviewSizes, mCameraSizeComparator);

        Log.i(TAG, "printSupportedPreviewSizes");
        printSupportedSizes(supportedPreviewSizes);

        Camera.Size fitPreviewSize = supportedPreviewSizes.get(0);
        for (int i = 0; i < supportedPreviewSizes.size(); i++) {
            fitPreviewSize = supportedPreviewSizes.get(i);
            if ((fitPreviewSize.width > fitPreviewSize.height) && (fitPreviewSize.height >= minWidth)) { // height即竖屏模式下的相片宽度
                break;
            }
        }

        Log.i(TAG, "getFitPreviewSize | width -> " + fitPreviewSize.width + " | height-> " + fitPreviewSize.height);
        return fitPreviewSize;
    }

    /**
     * 获取合适的拍照尺寸
     */
    public static Camera.Size getFitPictureSize(List<Camera.Size> supportedPictureSizes, int minWidth) {
        Collections.sort(supportedPictureSizes, mCameraSizeComparator);

        Log.i(TAG, "printSupportedPictureSizes");
        printSupportedSizes(supportedPictureSizes);

        Camera.Size fitPictureSizes = supportedPictureSizes.get(0);
        for (int i = 0; i < supportedPictureSizes.size(); i++) {
            fitPictureSizes = supportedPictureSizes.get(i);
            if ((fitPictureSizes.width > fitPictureSizes.height) && (fitPictureSizes.height >= minWidth)) { // height即竖屏模式下的相片宽度
                break;
            }
        }

        Log.i(TAG, "getFitPictureSize | width -> " + fitPictureSizes.width + " | height-> " + fitPictureSizes.height);
        return fitPictureSizes;
    }

    /**
     * CameraSizeComparator
     */
    public static class CameraSizeComparator implements Comparator<Camera.Size> {

        @Override
        public int compare(Camera.Size lhs, Camera.Size rhs) {
            if (lhs.height == rhs.height) { // height即竖屏模式下的相片宽度
                return 0;
            } else if (lhs.height > rhs.height) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}