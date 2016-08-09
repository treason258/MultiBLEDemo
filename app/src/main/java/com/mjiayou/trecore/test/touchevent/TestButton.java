package com.mjiayou.trecore.test.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.mjiayou.trecore.util.LogUtil;

/**
 * Created by treason on 16/8/9.
 */
public class TestButton extends Button {

    private final String TAG = this.getClass().getSimpleName();

    public TestButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TestButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestButton(Context context) {
        this(context, null);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View v = this;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.i(TAG, "dispatchTouchEvent execute | action ACTION_DOWN | " + v.getClass().getSimpleName());
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.i(TAG, "dispatchTouchEvent execute | action ACTION_UP | " + v.getClass().getSimpleName());
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.i(TAG, "dispatchTouchEvent execute | action ACTION_MOVE | " + v.getClass().getSimpleName());
                break;
            case MotionEvent.ACTION_CANCEL:
                LogUtil.i(TAG, "dispatchTouchEvent execute | action ACTION_CANCEL | " + v.getClass().getSimpleName());
                break;
            default:
                LogUtil.i(TAG, "dispatchTouchEvent execute | action " + event.getAction() + "|" + v.getClass().getSimpleName());
                break;
        }
//        super.dispatchTouchEvent(event);
//        return true;
//        return false;
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        View v = this;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.i(TAG, "onTouchEvent execute | action ACTION_DOWN | " + v.getClass().getSimpleName());
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.i(TAG, "onTouchEvent execute | action ACTION_UP | " + v.getClass().getSimpleName());
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.i(TAG, "onTouchEvent execute | action ACTION_MOVE | " + v.getClass().getSimpleName());
                break;
            case MotionEvent.ACTION_CANCEL:
                LogUtil.i(TAG, "onTouchEvent execute | action ACTION_CANCEL | " + v.getClass().getSimpleName());
                break;
            default:
                LogUtil.i(TAG, "onTouchEvent execute | action " + event.getAction() + "|" + v.getClass().getSimpleName());
                break;
        }
//        super.onTouchEvent(event);
//        return true;
        return false;
//        return super.onTouchEvent(event);
    }
}
