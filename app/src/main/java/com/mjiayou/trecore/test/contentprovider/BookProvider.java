package com.mjiayou.trecore.test.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.mjiayou.trecore.TCContentProvider;

/**
 * Created by treason on 16/8/15.
 */
public class BookProvider extends TCContentProvider {

    @Override
    public boolean onCreate() {
        super.onCreate();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super.query(uri, projection, selection, selectionArgs, sortOrder);
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        super.getType(uri);
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        super.insert(uri, values);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        super.delete(uri, selection, selectionArgs);
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        super.update(uri, values, selection, selectionArgs);
        return 0;
    }
}
