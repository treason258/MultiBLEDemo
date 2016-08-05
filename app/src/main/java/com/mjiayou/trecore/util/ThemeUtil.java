package com.mjiayou.trecore.util;

import com.mjiayou.trecore.TCApp;
import com.mjiayou.trecoredemo.R;

/**
 * Created by treason on 16/5/14.
 */
public class ThemeUtil {

    public static final int THEME_ERROR = -1; // 错误主题
    public static final int THEME_DEFAULT = 0; // 默认
    public static final int THEME_OTHER = 1; // 其他

    /**
     * getBackIcon
     */
    public static int getBackIcon() {
        int themeId = SharedUtil.get(TCApp.get()).getConfigThemeId();
        switch (themeId) {
            case THEME_DEFAULT:
                return R.drawable.tc_back;
            case THEME_OTHER:
                return R.drawable.tc_back;
            default:
                return R.drawable.tc_back;
        }
    }

    /**
     * getTitleImage
     */
    public static int getTitleImage() {
        int themeId = SharedUtil.get(TCApp.get()).getConfigThemeId();
        switch (themeId) {
            case THEME_DEFAULT:
                return R.drawable.tc_launcher;
            case THEME_OTHER:
                return R.drawable.tc_launcher;
            default:
                return R.drawable.tc_launcher;
        }
    }
}
