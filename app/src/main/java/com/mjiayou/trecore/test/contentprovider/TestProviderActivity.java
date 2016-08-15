package com.mjiayou.trecore.test.contentprovider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.mjiayou.trecore.TCActivity;
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

        Uri uri = Uri.parse("content://com.mjiayou.trecore.test.contentprovider.BookProvider");
        getContentResolver().query(uri, null, null, null, null);
        getContentResolver().query(uri, null, null, null, null);
        getContentResolver().query(uri, null, null, null, null);
    }
}
