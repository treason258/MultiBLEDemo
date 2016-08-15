package com.mjiayou.trecore.test.contentprovider;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.mjiayou.trecore.TCContentProvider;

/**
 * Created by treason on 16/8/15.
 */
public class BookProvider extends TCContentProvider {

    public static final String AUTHORITY = "com.mjiayou.trecore.test.contentprovider.BookProvider";

    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;

    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        mUriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    private SQLiteDatabase mSQLiteDatabase;

    @Override
    public boolean onCreate() {
        super.onCreate();

        mSQLiteDatabase = new BookDBHelper(mContext).getWritableDatabase();
        mSQLiteDatabase.execSQL("delete from " + BookDBHelper.BOOK_TABLE_NAME);
        mSQLiteDatabase.execSQL("delete from " + BookDBHelper.USER_TABLE_NAME);
        mSQLiteDatabase.execSQL("insert into book values(3,'Android');");
        mSQLiteDatabase.execSQL("insert into book values(4,'IOS');");
        mSQLiteDatabase.execSQL("insert into book values(5,'Html5');");
        mSQLiteDatabase.execSQL("insert into user values(1,'Jack',1);");
        mSQLiteDatabase.execSQL("insert into user values(2,'Lucy',0);");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super.query(uri, projection, selection, selectionArgs, sortOrder);

        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        return mSQLiteDatabase.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        super.insert(uri, values);

        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        mSQLiteDatabase.insert(tableName, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        super.delete(uri, selection, selectionArgs);

        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        int row = mSQLiteDatabase.delete(tableName, selection, selectionArgs);
        if (row > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return row;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        super.update(uri, values, selection, selectionArgs);

        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        int row = mSQLiteDatabase.update(tableName, values, selection, selectionArgs);
        if (row > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return row;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        super.getType(uri);
        return null;
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (mUriMatcher.match(uri)) {
            case BOOK_URI_CODE:
                tableName = BookDBHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = BookDBHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;
    }
}
