package com.mjiayou.trecore.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by treason on 15-10-29.
 */
public class BitmapUtil {

    private final static String TAG = "BitmapUtil";

    /**
     * 获取Drawable宽度
     */
    public static int getDrawableWidth(Drawable drawable) {
        return drawable.getIntrinsicWidth();
    }

    /**
     * 获取Drawable高度
     */
    public static int getDrawableHeight(Drawable drawable) {
        return drawable.getIntrinsicHeight();
    }

    /**
     * 获取本地图片-压缩
     */
    public static Bitmap getBitmapCompress(String imagePath, int maxNumOfPixels) {
        return compressBySize(imagePath, maxNumOfPixels);
    }

    /**
     * 获取本地图片-不压缩
     */
    public static Bitmap getBitmap(String imagePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(imagePath);
            return BitmapFactory.decodeStream(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取项目资源图片
     */
    public static Bitmap getBitmap(Context context, int resId) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            InputStream inputStream = context.getResources().openRawResource(resId);
            return BitmapFactory.decodeStream(inputStream, null, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取项目资源图片
     */
    public static BitmapDrawable getBitmapDrawable(Context context, int resId) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            InputStream inputStream = context.getResources().openRawResource(resId);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
            return bitmapDrawable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存图片到本地
     */
    public static void saveImage(Bitmap bitmap, String imagePath) {
        LogUtil.i(TAG, "保存图片到本地");

        FileOutputStream fileOutputStream = null;
        try {
            if (bitmap.isRecycled()) {
                return;
            }

            // 创建文件
            File imageFile = new File(imagePath);
            File imageDirFile = imageFile.getParentFile();
            LogUtil.i(TAG, "待保存文件名称 ->" + imageFile.getName()); // 123456789012.jpeg
            LogUtil.i(TAG, "待保存文件路径 ->" + imageDirFile.getPath()); // /storage/emulated/0/hoho/images/

            // 创建文件夹，如果imagePath路径不存在，则创建
            FileUtil.createFolder(imageDirFile);

            // 写文件
            fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.flush();

            LogUtil.i(TAG, "图片保存成功 ->" + imagePath); // /storage/emulated/0/hoho/images/123456789012.jpeg
        } catch (Throwable e) {
            e.printStackTrace();
            LogUtil.i(TAG, "图片保存失败 -> " + imagePath);
        } finally {
            try {
                if (bitmap != null) {
                    bitmap.recycle();
                    bitmap = null;
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    /**
//     * saveToSDCard
//     */
//    public void saveToSDCard(String filename, Bitmap bmp) throws IOException {
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            File file = new File(DirectoryUtil.get().APP_IMAGE, filename);
//            BufferedOutputStream bos = null;
//            try {
//                bos = new BufferedOutputStream(new FileOutputStream(file));
//                bmp.compress(Bitmap.CompressFormat.PNG, 90, bos);
//                bmp.recycle();
//                bmp = null;
//            } finally {
//                if (bos != null) bos.close();
//            }
//
//            LogUtil.i(TAG, "图片保存成功 -> " + file.getPath());
//        }
//    }

    /**
     * Bitmap转成String
     */
    public static String parseString(Bitmap bitmap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
            byte[] bitmapArray = baos.toByteArray();
            String strBitmap = Base64.encodeToString(bitmapArray, Base64.DEFAULT);
            return strBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * String转成Bitmap
     */
    public static Bitmap parseBitmap(String strBitmap) {
        try {
            byte[] bitmapArray = Base64.decode(strBitmap, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对图片尺寸压缩
     */
    public static Bitmap compressBySize(String imagePath, int minSideLength, int maxNumOfPixels) {
        LogUtil.i(TAG, "对图片尺寸压缩");

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            // Decode bitmap without inSampleSize set
            // 设置inJustDecodeBounds为true后，decodeFile并不分配空间，但可计算出原始图片的长度和宽度，即opts.width和opts.height。
            // 有了这两个参数，再通过一定的算法，即可得到一个恰当的inSampleSize。
            options.inJustDecodeBounds = true;
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, minSideLength, maxNumOfPixels);
            // options.inSampleSize = getInSampleSize(options, targetWidth, targetHeight);
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(imagePath, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对图片尺寸压缩（默认minSideLength=-1）
     */
    public static Bitmap compressBySize(String imagePath, int maxNumOfPixels) {
        return compressBySize(imagePath, -1, maxNumOfPixels);
    }

    /**
     * calculateInSampleSize
     * <p/>
     * eg：opts.inSampleSize = computeSampleSize(opts, -1, 128*128);
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        LogUtil.i(TAG, "calculateInSampleSize -> " + roundedSize);
        return roundedSize;
    }

    /**
     * computeInitialSampleSize
     */
    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

//    /**
//     * 对图片尺寸压缩（OOM）
//     */
//    public static Bitmap compressBySize(Bitmap bitmap, int minSideLength, int maxNumOfPixels) {
//        Bitmap bitmapResult = null;
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            if (baos.toByteArray().length > 1024 * 1024) { // 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
//                baos.reset(); // 重置baos即清空baos
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos); // 这里压缩50%，把压缩后的数据存放到baos中
//            }
//
//            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
//            options.inJustDecodeBounds = true;
//            bitmapResult = BitmapFactory.decodeStream(bais, null, options); // 此时返回值为空
//            // 设置缩放比例
//            options.inSampleSize = calculateInSampleSize(options, minSideLength, maxNumOfPixels);
////            options.inSampleSize = getInSampleSize(options, targetWidth, targetHeight);
//            // Decode bitmap with inSampleSize set
//            options.inJustDecodeBounds = false;
//            // 重新读入图片，注意此时已经把options.inJustDecodeBounds设回false了
//            bais = new ByteArrayInputStream(baos.toByteArray());
//            bitmapResult = BitmapFactory.decodeStream(bais, null, options);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return bitmapResult;
//    }

//    /**
//     * 对图片质量压缩（OOM）
//     * <p/>
//     * size为图片大小，以KB为单位
//     *
//     * @param bitmap 原图
//     * @param size   eg：size=100，即表示压缩到100KB
//     * @return 压缩后的图片
//     */
//    public static Bitmap compressByQuality(Bitmap bitmap, int size) {
//        Bitmap bitmapResult = null;
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//            int options = 100;
//            while (baos.toByteArray().length > size * 1024) { // 循环判断如果压缩后图片是否大于sizeKB，大于继续压缩
//                LogUtil.i(TAG, "质量等于" + baos.toByteArray().length / 1024 + "KB，大于" + size + "KB，进行压缩，options=" + options);
//                baos.reset(); // 重置baos即清空baos
//                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos); // 这里压缩options，把压缩后的数据存放到baos中
//                options -= 10; // 每次都减少10bi
//                if (options <= 10) {
//                    bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()), null, null); // 把ByteArrayInputStream数据生成图片
//                    options = 100;
//                }
//            }
//            LogUtil.i(TAG, "压缩完毕，最终质量等于" + baos.toByteArray().length / 1024 + "KB");
//            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray()); // 把压缩后的数据baos存放到ByteArrayInputStream中
//            bitmapResult = BitmapFactory.decodeStream(bais, null, null); // 把ByteArrayInputStream数据生成图片
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return bitmapResult;
//    }

//    /**
//     * 计算图片尺寸缩放值
//     *
//     * @param options      BitmapFactory.Options
//     * @param targetWidth  目标宽度，即最佳缩放宽度，实际结果宽度会上下浮动
//     * @param targetHeight 目标高度，即最佳缩放高度，实际结果宽度会上下浮动
//     * @return inSampleSize
//     */
//    private static int getInSampleSize(BitmapFactory.Options options, int targetWidth, int targetHeight) {
//        // Raw height and width of image
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//
//        if (width > targetWidth || height > targetHeight) {
//            // Calculate ratios of height and width to requested height and width
//            final int widthRatio = Math.round((float) width / (float) targetWidth);
//            final int heightRatio = Math.round((float) height / (float) targetHeight);
//            // Choose the smallest ratio as inSampleSize value, this will
//            // guarantee a final image with both dimensions larger than or equal to the requested height and width.
//            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
//        }
//        LogUtil.i(TAG, "getInSampleSize -> " + inSampleSize);
//
//        return inSampleSize;
//    }

//    /**
//     * 图片压缩
//     */
//    public static Bitmap compress(String srcPath) {
//        BitmapFactory.Options newOpts = new BitmapFactory.Options();
//        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
//        newOpts.inJustDecodeBounds = true;
//        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空
//
//        newOpts.inJustDecodeBounds = false;
//        int w = newOpts.outWidth;
//        int h = newOpts.outHeight;
//        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
//        float hh = 800f;//这里设置高度为800f
//        float ww = 480f;//这里设置宽度为480f
//        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
//        int be = 1;//be=1表示不缩放
//        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
//            be = (int) (newOpts.outWidth / ww);
//        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
//            be = (int) (newOpts.outHeight / hh);
//        }
//        if (be <= 0)
//            be = 1;
//        newOpts.inSampleSize = be;//设置缩放比例
//        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
//        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
//        return bitmap;
//    }
//
//    public boolean compressImage(File file) {
//        String path = file.getPath();
//        Bitmap bitmap = BitmapFactory.decodeFile(path);
//        BitmapFactory.Options newOpts = new BitmapFactory.Options();
//// 开始读入图片，此时把options.inJustDecodeBounds设为true
//        newOpts.inJustDecodeBounds = true;
//        bitmap = BitmapFactory.decodeFile(path, newOpts);
//        newOpts.inJustDecodeBounds = false;
//        int w = newOpts.outWidth;
//        int h = newOpts.outHeight;
//// 设置分辨率
//        float hh = 800f;
//        float ww = 480f;
//// 缩放比。由于是固定的比例缩放，只用高或者宽其中一个数据进行计算即可
//        int be = 1;
//        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
//            be = (int) (newOpts.outWidth / ww);
//        } else if (w < h && h > hh) {// 如果宽度大的话根据宽度固定大小缩放
//            be = (int) (newOpts.outHeight / hh);
//        }
//        if (be <= 0) {
//            be = 1;
//        }
//        newOpts.inSampleSize = be;// 设置缩放比例
//// 重新读入图片，注意此时已经把newOpts.inJustDecodeBounds = false
//        bitmap = BitmapFactory.decodeFile(path, newOpts);
//        try {
//            return compressImage2(bitmap);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean compressImage2(Bitmap bitmap) throws Exception {
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            int options = 100;
//            int size = baos.toByteArray().length / 1024;
//            while (size > 40 && options > 0) {
//                baos.reset();// 重置baos即清空baos
//                options -= 10;// 每次都减少10
//// 这里压缩options%，把压缩后的数据存放到baos中
//                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
//                size = baos.toByteArray().length / 1024;
//            }
//// 把压缩后的数据baos存放到ByteArrayInputStream中
//            ByteArrayInputStream isBm = new ByteArrayInputStream(
//                    baos.toByteArray());
//// 把ByteArrayInputStream数据生成图片
//            bitmap = BitmapFactory.decodeStream(isBm, null, null);
//            if (size > 40) {
//                return true;
//            } else {
//                return false;
//            }
//
//        } catch (Exception e) {
//            throw e;
//        }
//
//    }

}
