package com.mjiayou.trecore.util;

import android.content.Context;
import android.text.TextUtils;

import com.mjiayou.trecore.TCApp;
import com.mjiayou.trecore.bean.entity.TCUser;
import com.mjiayou.trecore.event.UserLoginStatusEvent;
import com.mjiayou.trecore.helper.Router;

import de.greenrobot.event.EventBus;

/**
 * Created by treason on 16/5/14.
 */
public class UserUtil {

    public static final String TAG = "UserUtil";

    /**
     * 用户登陆之后的操作
     */
    public static void doLogin(String token) {
        UserUtil.setToken(token);

        EventBus.getDefault().post(new UserLoginStatusEvent(true));
    }

    /**
     * 获取用户详细信息之后的操作
     */
    public static void doGetUserInfo(TCUser user) {
        UserUtil.setUserID(user.getId());
        UserUtil.setUserInfo(user);
    }

    /**
     * 用户注销登陆之后的操作
     */
    public static void doLogout() {
        UserUtil.setToken("");

        EventBus.getDefault().post(new UserLoginStatusEvent(false));
    }

    // ******************************** 判断用户登录状态 ********************************

    /**
     * 判断当前用户是否登录（约定以SharedUtil中getAccountToken值是否有效为准）
     * <p/>
     * true：已登录状态；
     * <br/>
     * false：未登录状态；
     */
    public static boolean checkLoginStatus(Context context) {
        boolean isLogin = false;

        String token = UserUtil.getToken();
        if (!TextUtils.isEmpty(token)) {
            isLogin = true;
        }

        return isLogin;
    }

    /**
     * 判断当前用户是否登录，如果未登录则跳转到登录页面
     * <p/>
     * true：需要登录，则自动跳转到登陆页面，当前页面可以return；
     * <br/>
     * false：不需要登录，继续执行；
     */
    public static boolean checkNeedLogin(Context context) {
        boolean isNeedLogin = false;

        boolean isLogin = checkLoginStatus(context);
        if (!isLogin) {
            isNeedLogin = true;
            Router.openTestUserLoginActivity(context);
        }

        return isNeedLogin;
    }

    // ******************************** 缓存用户信息 ********************************

    /**
     * Token
     */
    public static void setToken(String token) {
        SharedUtil.get(TCApp.get()).setAccountToken(token);
    }

    public static String getToken() {
        return SharedUtil.get(TCApp.get()).getAccountToken();
    }

    /**
     * UserID
     */
    public static void setUserID(String userID) {
        SharedUtil.get(TCApp.get()).setAccountUserID(userID);
    }

    public static String getUserID() {
        return SharedUtil.get(TCApp.get()).getAccountUserID();
    }

    /**
     * 用户信息
     */
    public static void setUserInfo(TCUser user) {
        SharedUtil.get(TCApp.get()).setAccountUserInfo(user);
    }

    public static TCUser getUserInfo() {
        return SharedUtil.get(TCApp.get()).getAccountUserInfo();
    }

    /**
     * UUID
     */
    public static void setUUID(String uuid) {
        SharedUtil.get(TCApp.get()).setAccountUUID(uuid);
    }

    public static String getUUID() {
        return SharedUtil.get(TCApp.get()).getAccountUUID();
    }
}
