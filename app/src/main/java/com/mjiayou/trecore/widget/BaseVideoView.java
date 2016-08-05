package com.mjiayou.trecore.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by treason on 15-12-24.
 */
public class BaseVideoView extends VideoView {

    public BaseVideoView(Context context) {
        super(context);
    }

    public BaseVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        // 默认显示
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 全屏显示
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
