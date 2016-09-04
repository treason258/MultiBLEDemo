package com.mjiayou.trecore.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.mjiayou.trecore.util.DirectoryUtil;
import com.mjiayou.trecore.util.FileUtil;
import com.mjiayou.trecore.util.LogUtil;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.io.File;
import java.io.IOException;

/**
 * Created by treason on 16/5/14.
 */
public class ImageLoaderHelper {

    private static final String TAG = "ImageLoaderHelper";

    /**
     * 初始化
     */
    public static void init(Context context) {
        LogUtil.i("初始化数据 -> " + TAG);

        ImageLoader.getInstance().init(getImageLoaderConfiguration(context));
    }

    /**
     * 展示图片
     */
    public static void display(final Context context, final String url, final ImageView imageView, final int imageResId) {
        try {
            if (!TextUtils.isEmpty(url)) ImageLoader.getInstance().displayImage(url,
                    imageView,
                    getDisplayImageOptions(imageResId),
                    getImageLoadingListener(context, imageView, imageResId),
                    getImageLoadingProgressListener());
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            LogUtil.i(TAG, "Throwable");
        }
    }

    /**
     * 默认全局配置
     */
    public static ImageLoaderConfiguration getImageLoaderConfiguration(Context context) {
//        return ImageLoaderConfiguration.createDefault(context);

        // 本地缓存路径
        File cacheDir = FileUtil.createFolder(DirectoryUtil.get().APP_CACHE_IMAGE);

        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context);
        // 设置调试状态
        if (Configs.DEBUG_IMAGE_LOADER) {
            builder.writeDebugLogs();
        }
        // 内存缓存配置
        builder.memoryCache(new WeakMemoryCache());
        builder.memoryCacheExtraOptions(Configs.IMAGELOADER_MAX_WIDTH, Configs.IMAGELOADER_MAX_HEIGHT);
        // 硬盘缓存配置
        try {
            builder.diskCache(new LruDiskCache(cacheDir, null, getFileNameGenerator(), Configs.IMAGELOADER_CACHE_MAX_SIZE, Integer.MAX_VALUE)); // Integer.MAX_VALUE表示不限文件数量，因为暂时不理解数值意义
        } catch (IOException e) {
            e.printStackTrace();
            builder.diskCache(new LimitedAgeDiskCache(cacheDir, null, getFileNameGenerator(), Integer.MAX_VALUE)); // Integer.MAX_VALUE
        }
        builder.diskCacheExtraOptions(Configs.IMAGELOADER_MAX_WIDTH, Configs.IMAGELOADER_MAX_HEIGHT, null);
        // 线程配置
        builder.threadPoolSize(3); // 线程池内加载数量
        builder.threadPriority(Thread.NORM_PRIORITY - 2); // 线程优先级
        // 其他配置
        builder.tasksProcessingOrder(QueueProcessingType.LIFO);
        builder.denyCacheImageMultipleSizesInMemory();
        return builder.build();

        // ALL
//        // 设置调试状态
//        builder.writeDebugLogs(); // 打印LOG
//        // 内存缓存配置
//        builder.memoryCache(new WeakMemoryCache());
//        builder.memoryCache(new LruMemoryCache(2 * 1024 * 1024)); // 你可以通过自己的内存缓存实现 2M
//        builder.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)); // 你可以通过自己的内存缓存实现 2M
//        builder.memoryCacheSize((1 * 1024 * 1024)); // 2M
//        builder.memoryCacheSizePercentage(13);
//        builder.memoryCacheExtraOptions(480, 800);  // 即保存的每个缓存文件的最大长宽 // default = device screen dimensions
//        // 硬盘缓存配置
//        builder.diskCache(new UnlimitedDiskCache(cacheDir)); //
//        builder.diskCache(new LruDiskCache(cacheDir, null, new Md5FileNameGenerator(), 5, 100));
//        builder.diskCache(new LimitedAgeDiskCache(cacheDir, cacheDir, new Md5FileNameGenerator(), 1 * 1024 * 1024)); //自定义缓存路径 1M
//        builder.diskCacheSize(80 * 1024 * 1024); // 硬盘缓存80MB
//        builder.diskCacheFileCount(100); //缓存的文件数量
//        builder.diskCacheFileNameGenerator(new Md5FileNameGenerator()); // 将保存的时候的URI名称用MD5
//        builder.diskCacheFileNameGenerator(new HashCodeFileNameGenerator()); // 将保存的时候的URI名称用HASHCODE加密
//        builder.diskCacheExtraOptions(480, 800, null);
//        // 线程配置
//        builder.threadPoolSize(3); // 线程池内加载的数量
//        builder.threadPriority(Thread.NORM_PRIORITY - 2); // 线程优先级
//        // 其他配置
//        builder.tasksProcessingOrder(QueueProcessingType.LIFO);
//        builder.denyCacheImageMultipleSizesInMemory(); // so when some image will be cached in memory then previous cached size of this image (if it exists) will be removed from memory cache before.
//        builder.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)); // connectTimeout (5s), readTimeout (30s)超时时间
//        builder.defaultDisplayImageOptions(DisplayImageOptions.createSimple());
    }

    /**
     * 默认显示配置
     */
    public static DisplayImageOptions getDisplayImageOptions(int imageResId) {
//        return DisplayImageOptions.createSimple();

        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.bitmapConfig(Bitmap.Config.RGB_565);  // 设置图片的解码类型
        builder.showImageOnLoading(imageResId); // 设置图片在下载期间显示的图片
        builder.showImageForEmptyUri(imageResId); // 设置图片Uri为空或是错误的时候显示的图片
        builder.showImageOnFail(imageResId); // 设置图片加载/解码过程中错误时候显示的图片
        builder.cacheInMemory(true); // 设置下载的图片是否缓存在内存中
        builder.cacheOnDisk(true); // 设置下载的图片是否缓存在SD卡中
        return builder.build();

        // ALL
//        builder.bitmapConfig(Bitmap.TCConfigs.RGB_565);  // 设置图片的解码类型
//        builder.showImageOnLoading(imageResId); // 设置图片在下载期间显示的图片
//        builder.showImageForEmptyUri(imageResId); // 设置图片Uri为空或是错误的时候显示的图片
//        builder.showImageOnFail(imageResId); // 设置图片加载/解码过程中错误时候显示的图片
//        builder.cacheInMemory(false); // 设置下载的图片是否缓存在内存中
//        builder.cacheOnDisk(true); // 设置下载的图片是否缓存在SD卡中
//        builder.imageScaleType(ImageScaleType.IN_SAMPLE_INT); // 设置图片以如何的编码方式显示
//        builder.imageScaleType(ImageScaleType.EXACTLY_STRETCHED); // 设置图片以如何的编码方式显示
//        builder.considerExifParams(true); // 是否考虑JPEG图像EXIF参数（旋转，翻转）
//        builder.decodingOptions(new BitmapFactory.Options()); // 设置图片的解码配置
//        builder.delayBeforeLoading(100); // 设置图片下载前的延迟
//        builder.preProcessor(null); // 设置图片加入缓存前，对bitmap进行设置
//        builder.resetViewBeforeLoading(false); // 设置图片在下载前是否重置，复位
//        builder.displayer(new RoundedBitmapDisplayer(20)); // 是否设置为圆角，弧度为多少
//        builder.displayer(new FadeInBitmapDisplayer(100)); // 是否图片加载好后渐入的动画时间
    }

    /**
     * FileNameGenerator
     */
    public static FileNameGenerator getFileNameGenerator() {
        return new Md5FileNameGenerator();

//        return new FileNameGenerator() {
//            @Override
//            public String generate(String imageUri) {
////                http://img.soccerapp.cn/userfiles/b4283019b3c94e14b5cd7971f590c6f3/images/cms/imageinfo/2015/12/u%3D3410960248%2C474436663%26fm%3D11%26gp%3D0%281%29.jpg
////                http://beibanqiu.oss-cn-beijing.aliyuncs.com/avatar/c28edc3f-c398-4d31-a4ea-10d8aa24c019
//                imageUri = imageUri.replace("http://img.soccerapp.cn/", "");
//                imageUri = imageUri.replace("userfiles/", "");
//                imageUri = imageUri.replace("images/", "");
//                imageUri = imageUri.replace("cms/", "");
//                imageUri = imageUri.replace("imageinfo/", "");
//                imageUri = imageUri.replace("http://beibanqiu.oss-cn-beijing.aliyuncs.com/", "");
//                imageUri = imageUri.replace("avatar/", "");
//                imageUri = imageUri.replace(":", "-").replace("/", "-").replace(".", "-").replace("(", "-").replace(")", "-").replace("%", "-");
//                if (imageUri.length() >= 64) {
//                    imageUri.substring(imageUri.length() - 64, imageUri.length());
//                }
//                return imageUri;
//            }
//        };
    }

    /**
     * ImageLoadingListener
     */
    public static ImageLoadingListener getImageLoadingListener(final Context context, final ImageView imageView, final int imageResId) {
        return null;

//        return new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//                ImageViewUtil.displayDrawable(context, imageResId, imageView);
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                ImageViewUtil.displayDrawable(context, imageResId, imageView);
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                ImageViewUtil.displayBitmap(loadedImage, imageView);
//            }
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//                ImageViewUtil.displayDrawable(context, imageResId, imageView);
//            }
//        };
    }

    /**
     * ImageLoadingProgressListener
     */
    public static ImageLoadingProgressListener getImageLoadingProgressListener() {
        return null;

//        return new ImageLoadingProgressListener() {
//            @Override
//            public void onProgressUpdate(String imageUri, View view, int current, int total) {
//                LogUtil.i("imageUri -> " + imageUri + " ;current -> " + current + "/" + total);
//            }
//        };
    }

    /**
     * 下载图片
     */
    public static void loadImage(String url, ImageLoadingListener listener) {
        ImageLoader.getInstance().loadImage(url, listener);
    }


    /**
     * 清除内存缓存
     */
    public static void clearMemoryCache() {
        ImageLoader.getInstance().clearMemoryCache();
    }

    /**
     * 清除硬盘缓存
     */
    public static void clearDiskCache() {
        ImageLoader.getInstance().clearDiskCache();
    }

    /**
     * 清除全部缓存
     */
    public static void clearAllCache() {
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();
    }
}
