package com.mjiayou.trecore.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by treason on 15-12-21.
 */
public class DipUtil {

    /**
     * 获得Dip信息
     * <p/>
     * <br>当屏幕density=120时，使用ldpi标签的资源，对应240*400，图标36*36，基准0.75，（淘汰）
     * <br>当屏幕density=160时，使用mdpi标签的资源，对应320*480，图标48*48，基准1.0，（baseline）
     * <br>当屏幕density=240时，使用hdpi标签的资源，对应480*800，图标72*72，基准1.5
     * <br>当屏幕density=320时，使用xhdpi标签的资源，对应720*1280，图标96*96，基准2.0
     * <br>当屏幕density=480时，使用xxhdpi标签的资源，对应1080*1920，图标144*144，基准3.0
     * <br>当屏幕density=640时，使用xxxhdpi标签的资源，对应，基准4.0
     * <p/>
     * <br> PPI = √（长度像素数² + 宽度像素数²） / 屏幕对角线英寸数
     * <br> px = dp*ppi/160
     * <br> dp = px/(ppi/160)
     * <br> px = sp*ppi/160
     * <br> sp = px/(ppi/160)
     */
    public static String getDipInfo(Context context) {
        StringBuilder builder = new StringBuilder();

        float value = 100f;
        builder.append("\n");
        builder.append("基数 = " + value).append("\n");
        builder.append("dp2px = " + dp2px(context, value)).append("\n");
        builder.append("dp2px2 = " + dp2px2(context, value)).append("\n");
        builder.append("px2dp = " + px2dp(context, value)).append("\n");
        builder.append("sp2px = " + sp2px(context, value)).append("\n");
        builder.append("px2sp = " + px2sp(context, value)).append("\n");

        return builder.toString();
    }

    // ******************************** dip2px ********************************

    /**
     * 转换dip为px
     */
    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f * (dpValue >= 0 ? 1 : -1));
    }

    /**
     * 转换dip为px-另一种实现
     */
    @Deprecated
    public static int dp2px2(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    // ******************************** px2dip ********************************

    /**
     * 转换px为dip
     */
    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f * (pxValue >= 0 ? 1 : -1));
    }

    // ******************************** sp2px ********************************

    /**
     * 转换sp为px
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    // ******************************** px2sp ********************************

    /**
     * 转换px为sp
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
