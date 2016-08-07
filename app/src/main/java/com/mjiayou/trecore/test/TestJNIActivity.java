package com.mjiayou.trecore.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mjiayou.trecoredemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TestJNIActivity extends AppCompatActivity {

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

        mTvJni.setText("来自libtest_trejni.so的问候 -> " + new TestTreJNIUtil().getHello());
    }
}
