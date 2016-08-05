package com.mjiayou.trecore.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mjiayou.trecoredemo.R;

/**
 * Created by treason on 16/5/14.
 */
public class ToolbarHelper {

    // 上下文，创建view的时候需要用到
    private Context mContext;
    // 内容布局容器
    private FrameLayout mLayoutContent;
    // 用户定义的view
    private View mViewCustom;
    // mToolbar
    private Toolbar mToolbar;
    // 视图构造器
    private LayoutInflater mLayoutInflater;
    /*
    * 两个属性
    * 1、toolbar是否悬浮在窗口之上
    * 2、toolbar的高度获取
    * */
    private static int[] ATTRS = {
            R.attr.windowActionBarOverlay,
            R.attr.actionBarSize
    };

    public ToolbarHelper(Context context, int layoutId) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        // 初始化整个内容
        initContentLayout();
        // 初始化用户定义的布局
        initCustomView(layoutId);
        // 初始化Toolbar
        initToolbar();
    }

    /**
     * 初始化整个内容
     */
    private void initContentLayout() {
        // 直接创建一个帧布局，作为视图容器的父容器
        mLayoutContent = new FrameLayout(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLayoutContent.setLayoutParams(params);
    }

    /**
     * 初始化用户定义的布局
     */
    @SuppressWarnings("ResourceType")
    private void initCustomView(int layoutId) {
        mViewCustom = mLayoutInflater.inflate(layoutId, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(ATTRS);
        // 获取主题中定义的悬浮标志
        boolean overly = typedArray.getBoolean(0, false);
        // 获取主题中定义的toolbar的高度
        int toolBarSize = (int) typedArray.getDimension(1, (int) mContext.getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        typedArray.recycle();
        // 如果是悬浮状态，则不需要设置间距
        params.topMargin = overly ? 0 : toolBarSize;
        mLayoutContent.addView(mViewCustom, params);
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        // 通过inflater获取toolbar的布局文件
        View toolbar = mLayoutInflater.inflate(R.layout.tc_layout_toolbar_widget, mLayoutContent);
        mToolbar = (Toolbar) toolbar.findViewById(R.id.toolbar);
    }

    /**
     * 返回 mLayoutContent
     */
    public FrameLayout getContentLayout() {
        return mLayoutContent;
    }

    /**
     * 返回 mToolbar
     */
    public Toolbar getToolbar() {
        return mToolbar;
    }
}
