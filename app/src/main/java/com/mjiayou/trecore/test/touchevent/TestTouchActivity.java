package com.mjiayou.trecore.test.touchevent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mjiayou.trecore.ui.TCActivity;
import com.mjiayou.trecore.util.LogUtil;
import com.mjiayou.trecoredemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TestTouchActivity extends TCActivity implements View.OnTouchListener, View.OnClickListener {

    @InjectView(R.id.btn_test)
    Button mBtnTest;
    @InjectView(R.id.iv_test)
    ImageView mIvTest;
    @InjectView(R.id.tbtn_test)
    TestButton mTbtnTest;

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

        // mBtnTest
        mBtnTest.setOnClickListener(this);
        mBtnTest.setOnTouchListener(this);
        // mIvTest
        mIvTest.setOnClickListener(this);
        mIvTest.setOnTouchListener(this);
        // mTbtnTest
        mTbtnTest.setOnClickListener(this);
        mTbtnTest.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        LogUtil.i(TAG, "onClick execute | " + v.getClass().getSimpleName());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.i(TAG, "onTouch execute | action ACTION_DOWN | " + v.getClass().getSimpleName());
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.i(TAG, "onTouch execute | action ACTION_UP | " + v.getClass().getSimpleName());
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.i(TAG, "onTouch execute | action ACTION_MOVE | " + v.getClass().getSimpleName());
                break;
            case MotionEvent.ACTION_CANCEL:
                LogUtil.i(TAG, "onTouch execute | action ACTION_CANCEL | " + v.getClass().getSimpleName());
                break;
            default:
                LogUtil.i(TAG, "onTouch execute | action " + event.getAction() + "|" + v.getClass().getSimpleName());
                break;
        }
        return false;
    }
}
