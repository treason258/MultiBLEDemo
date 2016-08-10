package com.mjiayou.trecore.test.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.mjiayou.trecore.util.ViewUtil;

/**
 * Created by treason on 16/8/10.
 */
public class TestLinearLayout extends LinearLayout {

    private final String TAG = this.getClass().getSimpleName();

    public TestLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TestLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestLinearLayout(Context context) {
        this(context, null);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        ViewUtil.printMotionEvent(event, TAG, "onInterceptTouchEvent");

        return super.onInterceptTouchEvent(event);
//        super.onInterceptTouchEvent(event);
//        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        ViewUtil.printMotionEvent(event, TAG, "dispatchTouchEvent");

        return super.dispatchTouchEvent(event);
//        super.dispatchTouchEvent(event);
//        return true;
//        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ViewUtil.printMotionEvent(event, TAG, "onTouchEvent");

        return super.onTouchEvent(event);
//        super.onTouchEvent(event);
//        return true;
//        return false;
    }
}
