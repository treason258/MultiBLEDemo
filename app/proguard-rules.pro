# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/treason/Archives/dev/android-sdk-macosx/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

## ******************************** custom ********************************
-keep class com.mjiayou.trecoredemo.bean.** { *; }
-keep class com.mjiayou.trecoredemo.bean.entity.** { *; }
-keep public class com.mjiayou.trecoredemo.R$*{
    public static final int *;
}

## ******************************** trecore ********************************
# 针对 com.mjiayou.trecore
-keep class com.mjiayou.trecore.bean.** { *; }
-keep class com.mjiayou.trecore.bean.entity.** { *; }
-keep public class com.mjiayou.trecore.R$*{
    public static final int *;
}

# 针对 org.apache.http.legacy.jar
-dontwarn org.apache.http.**
-dontwarn android.net.http.**

# 针对JS交互
-keepclassmembers class com.mjiayou.trecore.ui.TestActivity$JsImageGetter {
    public *;
}
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*

## ******************************** volley ********************************
-keep class com.android.volley.**  {* ;}
-keep class org.apache.http.**  {* ;}

## ******************************** gson-official ********************************
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.examples.android.model.** { *; }

## ******************************** butterknife-official ********************************
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

## ******************************** dart-official ********************************
-dontwarn com.f2prateek.dart.internal.**
-keep class **$$ExtraInjector { *; }
-keepnames class * { @com.f2prateek.dart.InjectExtra *;}

## ******************************** eventbus ********************************
-keepclassmembers class ** {
    public void onEvent*(**);
}

## ******************************** picasso-official ********************************
-dontwarn com.squareup.okhttp.**

## ******************************** umeng_analytics-official ********************************
-keepclassmembers class * {
    public <init>(org.json.JSONObject);
}
-keep public class com.aijifu.mmbj.R$*{
    public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

## ******************************** umeng_params-official ********************************
-keepclassmembers class * {
    public <init>(org.json.JSONObject);
}
-keep class com.umeng.onlineconfig.OnlineConfigAgent {
        public <fields>;
        public <methods>;
}
-keep class com.umeng.onlineconfig.OnlineConfigLog {
        public <fields>;
        public <methods>;
}
-keep interface com.umeng.onlineconfig.UmengOnlineConfigureListener {
        public <methods>;
}

## ******************************** umeng_update-official ********************************
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
-keep public class com.aijifu.mmbj.R$*{
    public static final int *;
}
-keep public class com.umeng.fb.ui.ThreadView {
}
#-libraryjars libs/umeng-sdk.jar
-keep public class * extends com.umeng.**
-keep class com.umeng.** { *; }

## ******************************** umeng_feedback-official ********************************
-keepclassmembers class * {
    public <init>(org.json.JSONObject);
}
-keep public class com.aijifu.mmbj.R$*{
    public static final int *;
}

## ******************************** umeng_onlineconfig ********************************
-keepclassmembers class * {
    public <init>(org.json.JSONObject);
}
-keep class com.umeng.onlineconfig.OnlineConfigAgent {
    public <fields>;
    public <methods>;
}
-keep class com.umeng.onlineconfig.OnlineConfigLog {
    public <fields>;
    public <methods>;
}
-keep interface com.umeng.onlineconfig.UmengOnlineConfigureListener {
    public <methods>;
}

## ******************************** umeng_social-official ********************************
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**
#-libraryjars libs/SocialSDK_QQZone_2.jar
-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**
-keep public class com.umeng.socialize.* {*;}
-keep public class javax.**
-keep public class android.webkit.**
-keep class com.facebook.**
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
-keep public class com.aijifu.mmbj.R$*{
    public static final int *;
}

## ******************************** xgpush-official ********************************
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep class com.tencent.android.tpush.**  {*;}
-keep class com.tencent.mid.**  {*;}

## ******************************** twowayview-official ********************************
-keep class org.lucasr.twowayview.** { *; }

## ******************************** okhttp ********************************
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *;}
-dontwarn okio.**

## ******************************** jpush ********************************

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontnote
-verbose

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.app.IntentService
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

-dontwarn com.google.gson.jpush.**
-keep class com.google.gson.jpush.** { *; }

## ******************************** qiniu-official ********************************

-keep class com.pili.pldroid.player.** { *; }
-keep class tv.danmaku.ijk.media.player.** {*;}

## ******************************** Common-Gson-official ********************************
##---------------Begin: proguard configuration common for all Android apps ----------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#-dump class_files.txt
#-printseeds seeds.txt
#-printusage unused.txt
#-printmapping mapping.txt

-allowaccessmodification
-keepattributes *Annotation*
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-repackageclasses ''

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-dontnote com.android.vending.licensing.ILicensingService

# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Preserve all native method names and the names of their classes.
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Preserve static fields of inner classes of R classes that might be accessed
# through introspection.
-keepclassmembers class **.R$* {
  public static <fields>;
}

# Preserve the special static methods that are required in all enumeration classes.
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep public class * {
    public protected *;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
##---------------End: proguard configuration common for all Android apps ----------
