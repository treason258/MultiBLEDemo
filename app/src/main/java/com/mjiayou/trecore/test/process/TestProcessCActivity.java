package com.mjiayou.trecore.test.process;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mjiayou.trecore.TCActivity;
import com.mjiayou.trecoredemo.R;

public class TestProcessCActivity extends TCActivity {

    /**
     * startActivity
     */
    public static void open(Context context) {
        Intent intent = new Intent(context, TestProcessCActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_process_c);

        setTitle(TAG);
    }
}
