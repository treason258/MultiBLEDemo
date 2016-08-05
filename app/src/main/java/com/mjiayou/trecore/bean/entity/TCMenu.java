package com.mjiayou.trecore.bean.entity;

import android.view.View;

/**
 * Created by treason on 16/6/28.
 */
public class TCMenu {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_CANCEL = 1;

    private int type;
    private String text;
    private View.OnClickListener mOnClickListener;

    /**
     * 构造函数
     */
    public TCMenu(int type, String text, View.OnClickListener mOnClickListener) {
        this.type = type;
        this.text = text;
        this.mOnClickListener = mOnClickListener;
    }

    public TCMenu(String text, View.OnClickListener mOnClickListener) {
        this(TYPE_NORMAL, text, mOnClickListener);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public View.OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
