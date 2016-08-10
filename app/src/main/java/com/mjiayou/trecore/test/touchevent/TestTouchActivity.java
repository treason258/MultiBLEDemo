package com.mjiayou.trecore.test.touchevent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.mjiayou.trecore.ui.TCActivity;
import com.mjiayou.trecore.util.ViewUtil;
import com.mjiayou.trecoredemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TestTouchActivity extends TCActivity implements View.OnTouchListener, View.OnClickListener {

    @InjectView(R.id.layout_root)
    TestLinearLayout mLayoutRoot;
    @InjectView(R.id.tbtn_test)
    TestButton mTbtnTest;
    @InjectView(R.id.iv_test)
    ImageView mIvTest;

    /**
     * startActivity
     */
    public static void open(Context context) {
        Intent intent = new Intent(context, TestTouchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_touch);
        ButterKnife.inject(this);

        initView();
    }

    @Override
    public void initView() {
        super.initView();

        // mLayoutRoot
        mLayoutRoot.setOnClickListener(this);
        mLayoutRoot.setOnTouchListener(this);
        // mTbtnTest
        mTbtnTest.setOnClickListener(this);
        mTbtnTest.setOnTouchListener(this);
        // mIvTest
        mIvTest.setOnClickListener(this);
        mIvTest.setOnTouchListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        ViewUtil.printMotionEvent(event, TAG, "dispatchTouchEvent");

        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onUserInteraction() {
        ViewUtil.printEvent(TAG, "onUserInteraction");
        super.onUserInteraction();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ViewUtil.printMotionEvent(event, TAG, "onTouchEvent");

        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        ViewUtil.printEvent(v.getClass().getSimpleName(), "onClick");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ViewUtil.printMotionEvent(event, v.getClass().getSimpleName(), "onTouch");

        return false;
    }
}
