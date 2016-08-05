package com.mjiayou.trecore.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;

import com.mjiayou.trecore.bean.entity.TCMenu;
import com.mjiayou.trecore.widget.dialog.DialogHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by treason on 15-11-11.
 */
public class ImageChooseUtil {

    // final
    private final static String TAG = "ImageChooseUtil";
    public final static int MODE_TAKE_PHOTO = 1;
    public final static int MODE_PICK_PICTURE = 2;
    public final static int MODE_CROP_IMAGE = 3;

    public final static String APP_CACHE_IMAGE = DirectoryUtil.get().APP_CACHE_IMAGE;
    public final static String PATH_TEMP_IMAGE_COMPRESSED = APP_CACHE_IMAGE + "temp_image_compressed.jpg"; // 压缩之后的图片
    public final static String PATH_TEMP_IMAGE_CROPPED = APP_CACHE_IMAGE + "temp_image_cropped.jpg"; // 裁剪之后的图片
    public final static String PATH_TEMP_TAKE_PHOTO = APP_CACHE_IMAGE + "temp_take_photo.jpg"; // 相机拍照原始图片

    /**
     * 显示图片选择对话框
     */
    public static void showDialog(final Activity activity) {
        List<TCMenu> bottomMenuListeners = new ArrayList<>();

        bottomMenuListeners.add(new TCMenu("拍照", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto(activity);
            }
        }));
        bottomMenuListeners.add(new TCMenu("从相册选择", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickPicture(activity);
            }
        }));
        bottomMenuListeners.add(new TCMenu(TCMenu.TYPE_CANCEL, "取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        }));

        DialogHelper.createTCBottomMenuDialog(activity, null, null, bottomMenuListeners).show();
    }

    /**
     * 拍照
     */
    public static void takePhoto(Activity activity) {
        LogUtil.i(TAG, "拍照");

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(PATH_TEMP_TAKE_PHOTO)));

        // 创建文件夹，如果 PATH_TEMP_TAKE_PHOTO 路径不存在，则创建
        try {
            FileUtil.createFile(new File(PATH_TEMP_TAKE_PHOTO));
        } catch (IOException e) {
            LogUtil.printStackTrace(e);
        }

        try {
            activity.startActivityForResult(intent, MODE_TAKE_PHOTO);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            ToastUtil.show(activity, "相机初始化失败");
        }
    }

    /**
     * 从相册选择
     */
    public static void pickPicture(Activity activity) {
        LogUtil.i(TAG, "从相册选择");

        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_GET_CONTENT); // ACTION_GET_CONTENT,ACTION_OPEN_DOCUMENT
        }
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            activity.startActivityForResult(intent, MODE_PICK_PICTURE);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            ToastUtil.show(activity, "请先安装一个图片浏览器");
        }
    }

    /**
     * 裁剪图片
     */
    public static void cropImage(Activity activity, String imagePathInput, String imagePathOutput) {
        LogUtil.i(TAG, "裁剪图片");
        LogUtil.i(TAG, "imagePathInput ->" + imagePathInput);
        LogUtil.i(TAG, "imagePathOutput ->" + imagePathOutput);

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(new File(imagePathInput)), "image/*");
        intent.putExtra("crop", "true"); // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("aspectX", 1); // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300); // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true); // 输出地址
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("output", Uri.fromFile(new File(imagePathOutput)));
        intent.putExtra("outputFormat", "JPEG"); // 返回格式
        try {
            activity.startActivityForResult(intent, MODE_CROP_IMAGE);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 返回原始图片路径 - 在onActivityResult内执行
     */
    public static String getOriginImagePath(Context context, int requestCode, Intent data) {
        String originImagePath = "";
        switch (requestCode) {
            case MODE_TAKE_PHOTO:
                originImagePath = PATH_TEMP_TAKE_PHOTO;
                break;
            case MODE_PICK_PICTURE:
                if (data != null && data.getData() != null) {
                    originImagePath = UriUtil.getPath(context, data.getData());
                }
                break;
        }

        LogUtil.i(TAG, "返回原始图片路径 | originImagePath ->" + originImagePath);
        return originImagePath;
    }
}
