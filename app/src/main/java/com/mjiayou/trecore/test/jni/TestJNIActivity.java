package com.mjiayou.trecore.test.jni;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mjiayou.trecore.TCActivity;
import com.mjiayou.trecoredemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TestJNIActivity extends TCActivity {

    @InjectView(R.id.tv_jni)
    TextView mTvJni;

    /**
     * startActivity
     */
    public static void open(Context context) {
        Intent intent = new Intent(context, TestJNIActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_jni);
        ButterKnife.inject(this);

        String msg = "来自 testjni.so的问候 -> " + TestJNIUtil.get().getHello();
        mTvJni.setText(msg);
    }
}
