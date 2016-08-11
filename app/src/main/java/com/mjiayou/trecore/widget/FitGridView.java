package com.mjiayou.trecore.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

import com.mjiayou.trecore.util.LogUtil;

public class FitGridView extends GridView {

    public FitGridView(Context context) {
        super(context);
    }

    public FitGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean handle = false;
        try {
            handle = super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return handle;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 10, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
