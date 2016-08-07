package com.mjiayou.trecore.test.customview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mjiayou.trecore.ui.TCActivity;
import com.mjiayou.trecoredemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TestCustomViewActivity extends TCActivity {

    @InjectView(R.id.vercodeview)
    VerCodeView mVerCodeView;
    @InjectView(R.id.customprogressbar)
    CustomProgressBar mCodeView;

    /**
     * startActivity
     */
    public static void open(Context context) {
        Intent intent = new Intent(context, TestCustomViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_custom_view);
        ButterKnife.inject(this);
    }
}
