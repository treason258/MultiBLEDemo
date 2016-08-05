package com.mjiayou.trecore.util;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import com.mjiayou.trecore.helper.UmengHelper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mjiayou.trecoredemo.BuildConfig;

/**
 * Created by treason on 15/7/18.
 */
public class AppUtil {

    private static final String TAG = "AppUtil";

    /**
     * getAppInfo
     */
    public static String getAppInfo(Context context, String packageName) {
        StringBuilder builder = new StringBuilder();

        // getVersionInfo
        builder.append("\n");
        builder.append("**** getVersionInfo ****").append("\n");
        builder.append(getVersionInfo(context, packageName));

        // getSignInfo
        builder.append("\n");
        builder.append("**** getSignInfo ****").append("\n");
        builder.append(getSignInfo(context, packageName));

        // getApplicationInfoStr
        builder.append("\n");
        builder.append("**** getApplicationInfoStr ****").append("\n");
        builder.append(getApplicationInfoStr(context, packageName));

        return builder.toString();
    }

    public static String getAppInfo(Context context) {
        return getAppInfo(context, context.getPackageName());
    }

    public static String getAppInfo(Context context, ApplicationInfo applicationInfo) {
        return getAppInfo(context, applicationInfo.packageName);
    }

    /**
     * getPackageInfo()
     */
    public static String getVersionInfo(Context context, String packageName) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("PackageName = ").append(getPackageName(context, packageName)).append("\n");
        buffer.append("VersionName = ").append(getVersionName(context, packageName)).append("\n");
        buffer.append("VersionCode = ").append(getVersionCode(context, packageName)).append("\n");
        return buffer.toString();
    }

    public static String getVersionInfo(Context context) {
        return getVersionInfo(context, context.getPackageName());
    }

    /**
     * getSignInfo()
     */
    public static String getSignInfo(Context context, String packageName) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("PackageName = ").append(packageName).append("\n");
        buffer.append("SignMD5 = ").append(getSignMD5(context, packageName)).append("\n");
        buffer.append("UmengChannel = ").append(getUmengChannel(context, packageName)).append("\n");
        buffer.append("BuildConfig.DEBUG = " + BuildConfig.DEBUG).append("\n");
        buffer.append("** SignInfoMore **").append("\n");
        try {
            PackageInfo packageInfo = getPackageInfo(context, packageName);
            Signature[] signatures = packageInfo.signatures;

            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(signatures[0].toByteArray()));

            buffer.append("SigAlgName = ").append(certificate.getSigAlgName()).append("\n");
            buffer.append("SerialNumber = ").append(certificate.getSerialNumber().toString()).append("\n");
            buffer.append("SubjectDN = ").append(certificate.getSubjectDN().toString()).append("\n");
            buffer.append("PublicKey = ").append(certificate.getPublicKey().toString()).append("\n");
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return buffer.toString();
    }

    public static String getSignInfo(Context context) {
        return getSignInfo(context, context.getPackageName());
    }

    /**
     * 获得 getApplicationInfoStr
     */
    public static String getApplicationInfoStr(Context context, String packageName) {
        try {
            ApplicationInfo applicationInfo = getApplicationInfo(context, packageName);

            StringBuilder builder = new StringBuilder();
            builder.append("Label = ").append(getLabel(context, packageName)).append("\n");
            builder.append("DataDir = ").append(getDataDir(context, packageName)).append("\n");
            builder.append("PublicSourceDir = ").append(getPublicSourceDir(context, packageName)).append("\n");
            builder.append("** ApplicationInfoMore **").append("\n");
            builder.append("ClassName = ").append(applicationInfo.className).append("\n");
            builder.append("Permission = ").append(applicationInfo.permission).append("\n");
            builder.append("TaskAffinity = ").append(applicationInfo.taskAffinity).append("\n");
            return builder.toString();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static String getApplicationInfoStr(Context context) {
        return getApplicationInfoStr(context, context.getPackageName());
    }

    // ******************************** PackageManager ********************************

    /**
     * 获取PackageManager
     */
    public static PackageManager getPackageManager(Context context) {
        return context.getPackageManager();
    }

    /**
     * 获取已安装应用包名列表
     */
    public static List<PackageInfo> getInstalledPackages(Context context) {
        return getPackageManager(context).getInstalledPackages(PackageManager.GET_SIGNATURES);
    }

    /**
     * 获取已安装应用列表，按照字母顺序排序
     */
    public static List<ApplicationInfo> getInstalledApplications(Context context) {
        List<ApplicationInfo> applicationInfos = getPackageManager(context).getInstalledApplications(0);
        Collections.sort(applicationInfos, new ApplicationInfo.DisplayNameComparator(context.getPackageManager()));
        return applicationInfos;
    }

    /**
     * 获取第三方应用信息
     */
    public static List<ApplicationInfo> getThirdAppInfo(Context context) {
        List<ApplicationInfo> listAll = getInstalledApplications(context);
        List<ApplicationInfo> listThirdApp = new ArrayList<>();
        for (ApplicationInfo app : listAll) {
            // 非系统程序
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                listThirdApp.add(app);
            }
            // 本来是系统程序，被用户手动更新后，该系统程序也成为第三方应用程序了
            else if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                listThirdApp.add(app);
            }
        }
        return listThirdApp;
    }

    /**
     * 获取系统应用信息
     */
    public static List<ApplicationInfo> getSystemAppInfo(Context context) {
        List<ApplicationInfo> listAll = getInstalledApplications(context);
        List<ApplicationInfo> listSystemApp = new ArrayList<>();
        for (ApplicationInfo app : listAll) {
            // 系统程序
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                listSystemApp.add(app);
            }
        }
        return listSystemApp;
    }

    // ******************************** PackageInfo ********************************

    /**
     * 获得PackageInfo对象
     */
    public static PackageInfo getPackageInfo(Context context, String packageName) throws PackageManager.NameNotFoundException {
        return getPackageManager(context).getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
//        return getPackageManager(context).getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
    }

    public static PackageInfo getPackageInfo(Context context) throws PackageManager.NameNotFoundException {
        return getPackageInfo(context, context.getPackageName());
    }

    /**
     * 获取ApplicationInfo对象
     */
    public static ApplicationInfo getApplicationInfo(Context context, String packageName) throws PackageManager.NameNotFoundException {
        return getPackageManager(context).getApplicationInfo(packageName, PackageManager.GET_META_DATA);
    }

    public static ApplicationInfo getApplicationInfo(Context context) throws PackageManager.NameNotFoundException {
        return getApplicationInfo(context, context.getPackageName());
    }

    /**
     * 获得包名（packageInfo.packageName）
     */
    public static String getPackageName(Context context, String packageName) {
        try {
            PackageInfo packageInfo = getPackageInfo(context, packageName);
            return packageInfo.packageName;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static String getPackageName(Context context) {
        return getPackageName(context, context.getPackageName());
    }

    /**
     * 获得版本名（packageInfo.versionName）
     */
    public static String getVersionName(Context context, String packageName) {
        try {
            PackageInfo packageInfo = getPackageInfo(context, packageName);
            return packageInfo.versionName;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static String getVersionName(Context context) {
        return getVersionName(context, context.getPackageName());
    }

    /**
     * 获得版本号（packageInfo.versionCode）
     */
    public static int getVersionCode(Context context, String packageName) {
        try {
            PackageInfo packageInfo = getPackageInfo(context, packageName);
            return packageInfo.versionCode;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return 0;
    }

    public static int getVersionCode(Context context) {
        return getVersionCode(context, context.getPackageName());
    }

    // ******************************** ApplicationInfo ********************************

    /**
     * 获得应用名称
     */
    public static String getLabel(Context context, String packageName) {
        try {
            ApplicationInfo applicationInfo = getApplicationInfo(context, packageName);
            return String.valueOf(applicationInfo.loadLabel(context.getPackageManager()));
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static String getLabel(Context context) {
        return getLabel(context, context.getPackageName());
    }

    /**
     * 获得应用图标
     */
    public static Drawable getIcon(Context context, String packageName) {
        try {
            ApplicationInfo applicationInfo = getApplicationInfo(context, packageName);
            return applicationInfo.loadIcon(context.getPackageManager());
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static Drawable getIcon(Context context) {
        return getIcon(context, context.getPackageName());
    }

    /**
     * 获得PublicSourceDir
     */
    public static String getPublicSourceDir(Context context, String packageName) {
        try {
            ApplicationInfo applicationInfo = getApplicationInfo(context, packageName);
            return applicationInfo.publicSourceDir;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static String getPublicSourceDir(Context context) {
        return getPublicSourceDir(context, context.getPackageName());
    }

    /**
     * 获得 DataDir
     */
    public static String getDataDir(Context context, String packageName) {
        try {
            ApplicationInfo applicationInfo = getApplicationInfo(context, packageName);
            return applicationInfo.dataDir;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static String getDataDir(Context context) {
        return getDataDir(context, context.getPackageName());
    }

    // ******************************** MetaValue管理 ********************************

    /**
     * 获取Manifest内的meta-data的value
     */
    public static String getMetaValue(Context context, String packageName, String metaName) {
        try {
            ApplicationInfo applicationInfo = getApplicationInfo(context, packageName);

            Bundle bundle = applicationInfo.metaData;
            return bundle.get(metaName).toString();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static String getMetaValue(Context context, String metaName) {
        return getMetaValue(context, context.getPackageName(), metaName);
    }

    public static String getUmengChannel(Context context, String packageName) {
        return AppUtil.getMetaValue(context, packageName, UmengHelper.META_UMENG_CHANNEL);
    }

    public static String getUmengChannel(Context context) {
        return AppUtil.getMetaValue(context, context.getPackageName(), UmengHelper.META_UMENG_CHANNEL);
    }

    // ******************************** 签名MD5 ********************************

    /**
     * 获取指定包名APP的签名指纹的MD5
     */
    public static String getSignMD5(Context context, String packageName) {
        try {
            PackageInfo packageInfo = getPackageInfo(context, packageName);
            Signature[] signatures = packageInfo.signatures;

            StringBuilder builder = new StringBuilder();
            builder.append(MD5Util.md5(signatures[0].toByteArray()));
            return builder.toString();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static String getSignMD5(Context context) {
        return getSignMD5(context, context.getPackageName());
    }

    // ******************************** APP管理 ********************************

    /**
     * 从asset目录下安装APK文件
     */
    public static void installAPKFromAsset(Context context, String apkDir, String apkName) throws IOException {
        try {

        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        // asset目录结构
        String apkPathForAsset = apkDir + "/" + apkName;
        // SDCard目录结构
        String apkDirForSDCard = DirectoryUtil.get().APP_CACHE + apkDir + "/";
        String apkPathForSDCard = DirectoryUtil.get().APP_CACHE + apkDir + "/" + apkName;

        // 读取文件数据流
        InputStream inputStream = AssetUtil.getAssetInputStream(context, apkPathForAsset);
        if (inputStream != null) {
            // 递归创建 APP_CACHE + NAME_LIBS 文件夹
            FileUtil.createFolder(apkDirForSDCard);
            // 将资源中的文件重写到sdcard中
            FileUtil.writeToFile(apkPathForSDCard, inputStream);
            // 安装APK
            installAPKFromSDCard(context, apkPathForSDCard);
        } else {
            LogUtil.i(TAG, "asset目录下找不到文件：" + apkPathForAsset);
        }
    }

    /**
     * 从SD卡目录安装APK文件
     */
    public static void installAPKFromSDCard(Context context, String apkPath) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

//    /**
//     * 安装应用
//     */
//    public static void installAPP(Context context, String path) {
//        Intent intent = new Intent(Intent.ACTION_PACKAGE_ADDED, Uri.fromParts("package", path, null));
//        context.startActivity(intent);
//    }

    /**
     * 卸载应用
     */
    public static void uninstallAPP(Context context, String packageName) {
        try {
            if (!isAPPInstalled(context, packageName)) {
                ToastUtil.show(context, "程序未安装");
                return;
            }
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.fromParts("package", packageName, null));
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 删除应用广播监听
     */
    public static BroadcastReceiver getDeleteReceiver(Context context) {
        try {
            IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
            filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
            filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
            filter.addDataScheme("package");

            // 自定义的广播接收类，接收到结果后的操作
            BroadcastReceiver deleteReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    ToastUtil.show(context, "deleteReceiver-onReceive");
                }
            };
            context.registerReceiver(deleteReceiver, filter); //注册广播监听应用
            return deleteReceiver;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    /**
     * 打开应用
     */
    public static void openAPP(Context context, String packageName) {
        try {
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(packageName);
            List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(resolveIntent, 0);
            if (resolveInfoList != null && resolveInfoList.size() > 0) {
                ResolveInfo resolveInfo = resolveInfoList.get(0);
                String activityPackageName = resolveInfo.activityInfo.packageName;
                String className = resolveInfo.activityInfo.name;

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName componentName = new ComponentName(activityPackageName, className);

                intent.setComponent(componentName);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 判断APP是否被安装
     */
    public static boolean isAPPInstalled(Context context, String packageName) {
        try {
            getPackageManager(context).getPackageInfo(packageName, 0);
            return true;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return false;
    }

    // ******************************** 系统操作 ********************************

    /**
     * 打开系统设置
     */
    public static void openSetting(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 打开浏览器
     */
    public static void openWebView(Context context, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 打开地图
     */
    public static void openMap(Context context, String lon, String lat) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:38.899533,-77.036476"));
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 播放多媒体
     */
    public static void openMedia(Context context, String path) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("file://" + path));
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 打开拨号键盘
     */
    public static void openCallDialog(Context context, String number) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 直接拨号
     */
    public static void openCallDirectly(Context context, String number) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 打开发短信
     */
    public static void openSendMessage(Context context, String number, String body) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + number));
            intent.putExtra("sms_body", body);
            intent.setType("vnd.android-dir/mms-sms");
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 直接发短信
     */
    public static void openSendMessageDirectly(Context context, String number, String body) {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto://" + number));
            intent.putExtra("sms_body", body);
            intent.setType("vnd.android-dir/mms-sms");
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 直接发彩信
     */
    public static void openSendMessageMedia(Context context, String number, String body) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra("sms_body", body);
            intent.putExtra("sms_body", "shenrenkui");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://media/external/images/media/23"));
            intent.setType("image/png");
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 打开发邮件
     */
    public static void openSendEmail(Context context, String emailTo) {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailTo));
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 打开发邮件
     */
    public static void openSendEmail(Context context, String[] emailTo, String[] emailCC, String subject, String body) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, emailTo);
            intent.putExtra(Intent.EXTRA_CC, emailCC);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, body);
            intent.setType("message/rfc882");
            context.startActivity(Intent.createChooser(intent, "选择邮件客户端"));
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }

//        Uri uri = Uri.parse("mailto:xxx@abc.com");
//        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
//        startActivity(it);

//        it.setType("text/plain");

//        it.putExtra(Intent.EXTRA_STREAM, "file:///sdcard/mysong.mp3");
//        sendIntent.setType("audio/mp3");

    }

    /**
     * getAppDetailInfo
     */
    public static String getAppInfoDetail(Context context) {
        StringBuilder builder = new StringBuilder();

        builder.append("******** APP INFO DETAIL ********").append("\n");

        builder.append("\n");
        builder.append("******** getAppInfo ********").append("\n");
        builder.append(AppUtil.getAppInfo(context));

        builder.append("\n");
        builder.append("******** getDeviceInfo ********").append("\n");
        builder.append(DeviceUtil.getDeviceInfo(context));

        builder.append("\n");
        builder.append("******** getDirectoryInfo ********").append("\n");
        builder.append(DirectoryUtil.get().getDirectoryInfo());

        builder.append("\n");
        builder.append("******** getDipInfo ********").append("\n");
        builder.append(DipUtil.getDipInfo(context));

        builder.append("\n");
        builder.append("******** other ********").append("\n");

        return builder.toString();
    }
}
