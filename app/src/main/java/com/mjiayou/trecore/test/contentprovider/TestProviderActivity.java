package com.mjiayou.trecore.test.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.mjiayou.trecore.TCActivity;
import com.mjiayou.trecore.test.aidl.Book;
import com.mjiayou.trecore.util.LogUtil;
import com.mjiayou.trecoredemo.R;

public class TestProviderActivity extends TCActivity {

    /**
     * startActivity
     */
    public static void open(Context context) {
        Intent intent = new Intent(context, TestProviderActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_provider);

        setTitle(TAG);

        {
            Uri bookUri = Uri.parse("content://" + BookProvider.AUTHORITY + "/book");

            ContentValues contentValues = new ContentValues();
            contentValues.put("_id", 6);
            contentValues.put("name", "程序设计的艺术");
            getContentResolver().insert(bookUri, contentValues);

            Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);
            while (bookCursor.moveToNext()) {
                Book book = new Book(bookCursor.getInt(0), bookCursor.getString(1));
                LogUtil.i(TAG, "query book -> " + book.toString());
            }
            bookCursor.close();
        }

        {
            Uri userUri = Uri.parse("content://" + BookProvider.AUTHORITY + "/user");

            Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
            while (userCursor.moveToNext()) {
                User user = new User(userCursor.getInt(0), userCursor.getString(1), userCursor.getInt(2) == 1);
                LogUtil.i(TAG, "query user -> " + user.toString());
            }
            userCursor.close();
        }
    }
}
