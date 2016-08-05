package com.mjiayou.trecore.event;

/**
 * Created by treason on 15-10-28.
 */
public class UserLoginStatusEvent {

    boolean mIsLogin;

    public UserLoginStatusEvent(boolean isLogin) {
        this.mIsLogin = isLogin;
    }

    public boolean isLogin() {
        return mIsLogin;
    }

    public void setIsLogin(boolean mIsLogin) {
        this.mIsLogin = mIsLogin;
    }
}
