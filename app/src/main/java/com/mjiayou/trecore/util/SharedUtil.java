package com.mjiayou.trecore.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mjiayou.trecore.bean.entity.TCUser;
import com.mjiayou.trecore.helper.GsonHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by treason on 16/5/14.
 */
public class SharedUtil {

    private static final String TAG = "SharedUtil";

    // var
    private static SharedUtil mSharedUtil;
    private Context mContext;
    private Gson mGson;

    // SharedPreferences
    // APP配置
    private SharedPreferences mSharedConfig;
    private String PREFERENCES_CONFIG = "preferences_config";
    private String KEY_CONFIG_IS_FIRST = "key_config_is_first"; // 是否第一次使用APP
    private String KEY_CONFIG_VERSION_CODE = "key_config_version_code"; // 本地记录版本号
    private String KEY_CONFIG_THEME_ID = "key_config_theme_id"; // 当前主题ID
    // 个人账户信息
    private SharedPreferences mSharedAccount;
    private String PREFERENCES_ACCOUNT = "preferences_account";
    private String KEY_ACCOUNT_USERNAME = "key_account_username"; // 用户名
    private String KEY_ACCOUNT_PASSWORD = "key_account_password"; // 密码
    private String KEY_ACCOUNT_TOKEN = "key_account_token"; // Token
    private String KEY_ACCOUNT_USER_ID = "key_account_user_id"; // UserID
    private String KEY_ACCOUNT_USER_INFO = "key_account_user_info"; // 用户个人信息
    private String KEY_ACCOUNT_UUID = "key_account_uuid"; // UUID
    // 本地缓存数据
    private SharedPreferences mSharedCache;
    private String PREFERENCE_CACHE = "preference_cache";
    private String KEY_CACHE_SEARCH_HISTORY = "key_cache_search_history";
    private String KEY_CACHE_USER_LIST = "key_cache_user_list";
    // 通用
    private SharedPreferences mSharedCommon;
    private String PREFERENCE_COMMON = "preference_common";
    private String KEY_COMMON_TEST = "key_common_test";

    /**
     * 构造函数
     */
    private SharedUtil(Context context) {
        this.mContext = context;
        this.mGson = GsonHelper.get();
        this.mSharedConfig = context.getSharedPreferences(PREFERENCES_CONFIG, Context.MODE_PRIVATE);
        this.mSharedAccount = context.getSharedPreferences(PREFERENCES_ACCOUNT, Context.MODE_PRIVATE);
        this.mSharedCache = context.getSharedPreferences(PREFERENCE_CACHE, Context.MODE_PRIVATE);
        this.mSharedCommon = context.getSharedPreferences(PREFERENCE_COMMON, Context.MODE_PRIVATE);
    }

    /**
     * 单例模式，获取实例
     */
    public static SharedUtil get(Context context) {
        if (mSharedUtil == null) {
            mSharedUtil = new SharedUtil(context);
        }
        return mSharedUtil;
    }

    // ******************************** 封装 ********************************

    /**
     * 日志规范
     */
    private void logSet(String key, Object value) {
        LogUtil.i(TAG, "SET " + key + " -> " + value);
    }

    private void logSetFailed() {
        LogUtil.i(TAG, "SET failed");
    }

    private void logGet(String key, Object value) {
        LogUtil.i(TAG, "GET " + key + " -> " + value);
    }

    private void logGetFailed() {
        LogUtil.i(TAG, "GET failed");
    }

    private void logRemovePrepare(String key) {
        LogUtil.i(TAG, "REMOVE prepare " + key);
    }

    private void logRemoveSucceed() {
        LogUtil.i(TAG, "REMOVE succeed");
    }

    private void logRemoveFailed() {
        LogUtil.i(TAG, "REMOVE failed");
    }

    /**
     * 保存数据
     */
    public void setShared(SharedPreferences sharedPreferences, String key, Object value) {
        if (sharedPreferences != null && !TextUtils.isEmpty(key) && value != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);

            } else if (value instanceof String) {
                editor.putString(key, (String) value);

            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);

            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);

            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            }
            editor.commit();
            logSet(key, value);
        } else {
            logSetFailed();
        }
    }

    public void setShared(SharedPreferences sharedPreferences, String key, boolean value) {
        if (sharedPreferences != null && !TextUtils.isEmpty(key)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, value);
            editor.commit();
            logSet(key, value);
        } else {
            logSetFailed();
        }
    }

    public void setShared(SharedPreferences sharedPreferences, String key, String value) {
        if (sharedPreferences != null && !TextUtils.isEmpty(key) && value != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.commit();
            logSet(key, value);
        } else {
            logSetFailed();
        }
    }

    public void setShared(SharedPreferences sharedPreferences, String key, int value) {
        if (sharedPreferences != null && !TextUtils.isEmpty(key)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key, value);
            editor.commit();
            logSet(key, value);
        } else {
            logSetFailed();
        }
    }

    public void setShared(SharedPreferences sharedPreferences, String key, float value) {
        if (sharedPreferences != null && !TextUtils.isEmpty(key)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat(key, value);
            editor.commit();
            logSet(key, value);
        } else {
            logSetFailed();
        }
    }

    public void setShared(SharedPreferences sharedPreferences, String key, long value) {
        if (sharedPreferences != null && !TextUtils.isEmpty(key)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(key, value);
            editor.commit();
            logSet(key, value);
        } else {
            logSetFailed();
        }
    }

    /**
     * 读取数据
     */
    public Object getShared(SharedPreferences sharedPreferences, String key, Object defValue) {
        try {
            Object value = new Object();
            if (defValue instanceof Boolean) {
                value = sharedPreferences.getBoolean(key, (Boolean) defValue);

            } else if (defValue instanceof String) {
                value = sharedPreferences.getString(key, (String) defValue);

            } else if (defValue instanceof Integer) {
                value = sharedPreferences.getInt(key, (Integer) defValue);

            } else if (defValue instanceof Float) {
                value = sharedPreferences.getFloat(key, (Float) defValue);

            } else if (defValue instanceof Long) {
                value = sharedPreferences.getLong(key, (Long) defValue);
            }
            logGet(key, value);
            return value;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            logGetFailed();
        }
        return defValue;
    }

    public boolean getShared(SharedPreferences sharedPreferences, String key, boolean defValue) {
        try {
            boolean value = sharedPreferences.getBoolean(key, defValue);
            logGet(key, value);
            return value;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            logGetFailed();
        }
        return defValue;
    }

    public String getShared(SharedPreferences sharedPreferences, String key, String defValue) {
        try {
            String value = sharedPreferences.getString(key, defValue);
            logGet(key, value);
            return value;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            logGetFailed();
        }
        return defValue;
    }

    public int getShared(SharedPreferences sharedPreferences, String key, int defValue) {
        try {
            int value = sharedPreferences.getInt(key, defValue);
            logGet(key, value);
            return value;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            logGetFailed();
        }
        return defValue;
    }

    public float getShared(SharedPreferences sharedPreferences, String key, float defValue) {
        try {
            float value = sharedPreferences.getFloat(key, defValue);
            logGet(key, value);
            return value;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            logGetFailed();
        }
        return defValue;
    }

    public long getShared(SharedPreferences sharedPreferences, String key, long defValue) {
        try {
            long value = sharedPreferences.getLong(key, defValue);
            logGet(key, value);
            return value;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            logGetFailed();
        }
        return defValue;
    }

    /**
     * 清除全部数据
     */
    public void removeShared(SharedPreferences sharedPreferences) {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Set<String> keySet = sharedPreferences.getAll().keySet();
            for (String key : keySet) {
                editor.remove(key);
                logRemovePrepare(key);
            }
            editor.commit();
            logRemoveSucceed();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            logRemoveFailed();
        }
    }

    /**
     * 清除全部
     */
    public void clearAll() {
        clearConfig();
        clearAccount();
        clearCache();
        clearCommon();
    }

    // ******************************** shortcut ********************************

    // **************** mSharedConfig start ****************

    /**
     * 是否第一次使用APP
     */
    public void setConfigIsFirst(boolean isFirst) {
        setShared(mSharedConfig, KEY_CONFIG_IS_FIRST, isFirst);
    }

    public boolean getConfigIsFirst() {
        return getShared(mSharedConfig, KEY_CONFIG_IS_FIRST, true);
    }

    /**
     * 记录版本号
     */
    public void setConfigVersionCode(int versionCode) {
        setShared(mSharedConfig, KEY_CONFIG_VERSION_CODE, versionCode);
    }

    public int getConfigVersionCode() {
        return getShared(mSharedConfig, KEY_CONFIG_VERSION_CODE, -1); // 默认返回-1，表示没有旧版本
    }

    /**
     * 主题ID
     */
    public void setConfigThemeId(int themeId) {
        setShared(mSharedConfig, KEY_CONFIG_THEME_ID, themeId);
    }

    public int getConfigThemeId() {
        return getShared(mSharedConfig, KEY_CONFIG_THEME_ID, ThemeUtil.THEME_DEFAULT);
    }

    /**
     * 清除配置信息
     */
    public void clearConfig() {
        removeShared(mSharedConfig);
    }

    // **************** mSharedAccount ****************

    /**
     * 用户名
     */
    public void setAccountUsername(String username) {
        setShared(mSharedAccount, KEY_ACCOUNT_USERNAME, username);
    }

    public String getAccountUsername() {
        return getShared(mSharedAccount, KEY_ACCOUNT_USERNAME, "");
    }

    /**
     * 密码
     */
    public void setAccountPassword(String password) {
        setShared(mSharedAccount, KEY_ACCOUNT_PASSWORD, password);
    }

    public String getAccountPassword() {
        return getShared(mSharedAccount, KEY_ACCOUNT_PASSWORD, "");
    }

    /**
     * Token
     */
    public void setAccountToken(String token) {
        setShared(mSharedAccount, KEY_ACCOUNT_TOKEN, token);
    }

    public String getAccountToken() {
        return getShared(mSharedAccount, KEY_ACCOUNT_TOKEN, "");
    }

    /**
     * UserID
     */
    public void setAccountUserID(String userID) {
        setShared(mSharedAccount, KEY_ACCOUNT_USER_ID, userID);
    }

    public String getAccountUserID() {
        return getShared(mSharedAccount, KEY_ACCOUNT_USER_ID, "");
    }

    /**
     * 用户信息
     */
    public void setAccountUserInfo(TCUser user) {
        String data = mGson.toJson(user);
        setShared(mSharedAccount, KEY_ACCOUNT_USER_INFO, data);
    }

    public TCUser getAccountUserInfo() {
        String data = getShared(mSharedAccount, KEY_ACCOUNT_USER_INFO, "");
        if (!TextUtils.isEmpty(data)) {
            return mGson.fromJson(data, TCUser.class);
        }
        return null;
    }

    /**
     * UUID
     */
    public void setAccountUUID(String uuid) {
        setShared(mSharedAccount, KEY_ACCOUNT_UUID, uuid);
    }

    public String getAccountUUID() {
        return getShared(mSharedAccount, KEY_ACCOUNT_UUID, "");
    }

    /**
     * 清除账户信息
     */
    public void clearAccount() {
        removeShared(mSharedAccount);
    }

    // **************** mSharedCache ****************

    /**
     * 搜索关键字历史记录
     */
    public void setCacheSearchHistory(List<String> list) {
        String data = mGson.toJson(list);
        setShared(mSharedCache, KEY_CACHE_SEARCH_HISTORY, data);
    }

    public List<String> getCacheSearchHistory() {
        String data = getShared(mSharedCache, KEY_CACHE_SEARCH_HISTORY, "");
        if (!TextUtils.isEmpty(data)) {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            return mGson.fromJson(data, type);
        }
        return null;
    }

    /**
     * 缓存用户列表
     */
    public void setCacheUserList(List<TCUser> list) {
        String data = mGson.toJson(list);
        setShared(mSharedCache, KEY_CACHE_USER_LIST, data);
    }

    public List<TCUser> getCacheUserList() {
        String data = getShared(mSharedCache, KEY_CACHE_USER_LIST, "");
        if (!TextUtils.isEmpty(data)) {
            Type type = new TypeToken<ArrayList<TCUser>>() {
            }.getType();
            return mGson.fromJson(data, type);
        }
        return null;
    }

    /**
     * 清除缓存信息
     */
    public void clearCache() {
        removeShared(mSharedCache);
    }

    // **************** mSharedCommon ****************

    /**
     * CommonTest
     */
    public void setCommonTest(String value) {
        setShared(mSharedCommon, KEY_COMMON_TEST, value);
    }

    public String getCommonTest() {
        return getShared(mSharedCommon, KEY_COMMON_TEST, "");
    }

    /**
     * Common
     */
    public void setCommon(String key, String value) {
        setShared(mSharedCommon, key, value);
    }

    public String getCommon(String key) {
        return getShared(mSharedCommon, key, "");
    }

    /**
     * 清除信息
     */
    public void clearCommon() {
        removeShared(mSharedCommon);
    }
}
