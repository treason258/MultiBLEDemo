package com.mjiayou.trecore.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class FitListView extends ListView {

    public FitListView(Context context) {
        super(context);
    }

    public FitListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 10, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
