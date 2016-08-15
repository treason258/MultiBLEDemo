package com.mjiayou.trecore;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.mjiayou.trecore.util.LogUtil;
import com.mjiayou.trecore.util.ThreadUtil;
import com.umeng.socialize.utils.Log;

/**
 * Created by treason on 16/7/19.
 */
public class TCContentProvider extends ContentProvider {

    protected final String TAG = this.getClass().getSimpleName();

    protected Context mContext;

    @Override
    public boolean onCreate() {
//        LogUtil.i(TAG, "onCreate | current thread -> " + ThreadUtil.getThreadName());
        Log.i(TAG, "onCreate | current thread -> " + ThreadUtil.getThreadName());

        mContext = getContext();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        LogUtil.i(TAG, "query | current thread -> " + ThreadUtil.getThreadName());
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        LogUtil.i(TAG, "getType | current thread -> " + ThreadUtil.getThreadName());
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        LogUtil.i(TAG, "insert | current thread -> " + ThreadUtil.getThreadName());
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        LogUtil.i(TAG, "delete | current thread -> " + ThreadUtil.getThreadName());
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        LogUtil.i(TAG, "update | current thread -> " + ThreadUtil.getThreadName());
        return 0;
    }
}
