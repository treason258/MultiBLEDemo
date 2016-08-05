
/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
            佛祖保佑       永无BUG
*/


// ******************************** test ********************************
// **************** test ****************
//
<!-- ******************************* test ******************************* -->
<!-- **************** test **************** -->
<!-- custom -->

// -------------------------------- test --------------------------------
// ---------------- test ----------------
//
// //////////////////////////////// test ////////////////////////////////
// //////////////// test ////////////////
//
## ******************************** test ********************************
## **************** test ****************


// ******************************** 打包注意事项 ********************************

1.build.gradle
applicationId
versionCode
versionName
minifyEnabled

2.App.java
mMode = MODE_RELEASE;
DEBUG_SERVER = false;
DEBUG_TEST = false

// ******************************** 更新日志 ********************************

// ******************************** 项目结构 ********************************

// ******************************** TreCore集成步骤 ********************************

// **************** A-新建项目 ****************

0-1.
新建项目，修改.ignore文件，添加readme.txt文件

0-2.
编译调试通过，提交SVN/GIT备份

// **************** B-导入依赖库 ****************

1.
分别拷贝 lib_TreCore lib_TreView lib_Umeng 文件夹到项目根目录下

2.
文件 settings.gradle 修改为：
include ':app', ':lib_TreCore', ':lib_TreView', ':lib_Umeng'

3.
文件 app - build.gradle 在 dependencies 节点下添加：
compile project(':lib_TreCore')
compile project(':lib_TreView')
compile project(':lib_Umeng')

4.
文件 app - build.gradle 在 android 节点下添加：
packagingOptions {
    exclude 'META-INF/services/javax.annotation.processing.Processor'
}

5.
编译调试通过，提交SVN/GIT备份

// **************** C-导入TreCore相关资源和代码 ****************

6.
拷贝 res-trecore 文件夹到 项目/app/src/main 目录下

7.
文件 app - build.gradle 在 android 节点下添加：
sourceSets {
    main.res.srcDirs += 'src/main/res-trecore'
}

8.
拷贝 com/mjiayou/trecore 文件夹到 项目/app/src/main/java 目录下

9.
执行gradle sync，根据提示解决错误（替换import错误和删除脏数据）（可以考虑全局替换）
需要操作的大概有：
com.mjiayou.trecore.net.RequestAdapter
com.mjiayou.trecore.net.RequestBuilder
com.mjiayou.trecore.ui.*
com.mjiayou.trecore.util.ThemeUtil
com.mjiayou.trecore.util.DialogUtil
com.mjiayou.trecore.util.AppUtil
com.mjiayou.trecore.widget.ToolbarHelper
com.mjiayou.trecore.widget.SwipeBackLayout
com.mjiayou.trecore.widget.CircleImageView
com.mjiayou.trecore.widget.Configs
com.mjiayou.trecore.widget.Router

10.
编译调试通过，提交SVN/GIT备份

// **************** D-接入TreCore功能 ****************

11.
文件 app - AndroidManifest 在 application 节点下修改：
android:theme="@style/tc_theme_default"

12.
文件 app - AndroidManifest 在 application 节点下添加：
android:name="com.mjiayou.trecore.TCApp"
android:largeHeap="true"

13.
文件 app - AndroidManifest 在 manifest 节点下添加：
从 TreCore-Start 到 TreCore-End 的 uses-permission

14.
文件 app - AndroidManifest 在 application 节点下添加：
从 TreCore-Start 到 TreCore-End 的内容（包括友盟系列和DebugActivity等）

15.
文件 MainActivity 修改继承 BaseActivity ，在 onCreate 下添加：
addRightTextView("DEBUG", MENU_DEBUG);

16.
文件 app - build.gradle 在 android 节点下添加：
productFlavors {
    // -1.调试包 - alpha
    alpha {}
    // 0.未分渠道包 - beta
    beta {}
}
productFlavors.all { flavor -> flavor.manifestPlaceholders = [UMENG_CHANNEL_VALUE: name] }

17.
编译调试通过，提交SVN/GIT备份

// **************** E-预开发阶段 ****************

18.
文件 TCApp 修改：
APP_NAME = "proname"; // 全小写

19.
拷贝 proguard-rules.pro 全文，修改 custom 部分为项目包名

20.
新建 proname-release.jks 签名文件

21.
拷贝 proname-release.jks 和 trecore-debug.jks 文件到 app 目录

22.
文件 app - build.gradle 在 根节点下添加：
def buildTime(String yyMMddHHmm) {
    def simpleDateFormat = new SimpleDateFormat(yyMMddHHmm)
    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"))
    return simpleDateFormat.format(new Date())
}

23.
文件 app - build.gradle 在 android 节点下添加并按需修改：
signingConfigs

24.
文件 app - build.gradle 在 android 节点下按需修改：
buildTypes

25.
文件 app - build.gradle 在 android 节点下按需修改：
versionName "1.0.0"

26.
编译调试通过，提交SVN/GIT备份

27.
分别打debug和release包，获取签名MD5值

// **************** F-开发 ****************



// **************** Z-其他 ****************

assets
jniLibs

multiDexEnabled true // Dex突破65535的限制

lintOptions {
    abortOnError false
}

渠道：
// 1.官方网站 - official
official {}
// 2.友盟 - umeng
umeng {}
// 3.FIR.IM - firim
firim {}
// 4.云测 - ctestin
ctestin {}
// 5.爱加密 - ijiami
ijiami {}
// 6.阿里云测试 - aliyuntest
aliyuntest {}

// 1.应用宝 - yingyongbao
yingyongbao {}
// 2.豌豆夹 - wandoujia
wandoujia {}
// 3.百度 - baidu
baidu {}
// 4.91助手 - c91
c91 {}
// 5.安卓市场 - anzhuo
anzhuo {}
// 6.360 - c360
c360 {}
// 7.安智市场 - anzhi
anzhi {}
// 8.应用汇 - yingyonghui
yingyonghui {}
// 9.小米 - xiaomi
xiaomi {}
// 10.魅族 - meizu
meizu {}

// 11.华为 - huawei
huawei {}
// 12.联想 - lianxiang
lianxiang {}
// 13.OPPO - oppo
oppo {}
// 14.三星 - sanxing
sanxing {}

// ******************************** QUESTION ********************************

// ******************************** TREASON ********************************
