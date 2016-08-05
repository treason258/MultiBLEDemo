package com.mjiayou.trecore.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.mjiayou.trecore.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import com.mjiayou.trecoredemo.R;

/**
 * Created by treason on 16/3/22.
 */
public class BannerPager extends FrameLayout {

    // mViewBanner
    private View mViewBanner;
    private FrameLayout mLayoutBanner;
    private RecyclerViewPager mRecyclerViewPager;
    private LinearLayout mLayoutInfo;
    private TextView mTvTitle;
    private List<ImageView> mIvDots = new ArrayList<>();
    private int[] mDots = new int[]{
            R.id.iv_dot_1,
            R.id.iv_dot_2,
            R.id.iv_dot_3,
            R.id.iv_dot_4,
            R.id.iv_dot_5,
            R.id.iv_dot_6,
            R.id.iv_dot_7,
            R.id.iv_dot_8,
            R.id.iv_dot_9,
            R.id.iv_dot_10
    };

    public BannerPager(Context context) {
        super(context);
    }

    public BannerPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        mViewBanner = LayoutInflater.from(context).inflate(R.layout.tc_layout_banner_pager, this, true);
        mViewBanner.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // findView
        mLayoutBanner = (FrameLayout) mViewBanner.findViewById(R.id.layout_banner);
        mRecyclerViewPager = (RecyclerViewPager) mViewBanner.findViewById(R.id.recyclerviewpager);
        mLayoutInfo = (LinearLayout) mViewBanner.findViewById(R.id.layout_info);
        mTvTitle = (TextView) mViewBanner.findViewById(R.id.tv_title);
        for (int i = 0; i < mDots.length; i++) {
            mIvDots.add(i, (ImageView) mViewBanner.findViewById(mDots[i]));
        }

        // mLayoutBanner
        ViewUtil.resizeBanner(context, mLayoutBanner);

        // mRecyclerViewPager
        mRecyclerViewPager.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewPager.setClipToPadding(false);
        mRecyclerViewPager.setLoopEnable(true);
        mRecyclerViewPager.setAutoLoopEnable(true);
        mRecyclerViewPager.addOnPageChangedListener(new BannerPageChangedListener(mIvDots));
        mRecyclerViewPager.addOnLayoutChangeListener(new BannerLayoutChangeListener(mRecyclerViewPager));
        mRecyclerViewPager.addOnScrollListener(new BannerScrollListener(mRecyclerViewPager));
    }

    /**
     * 设置数据
     */
    public void setData(Context context, List<String> mListCarousel) {

        mLayoutBanner.setVisibility(View.GONE);
        // mIvDots
        for (int i = 0; i < mDots.length; i++) {
            mIvDots.get(i).setVisibility(View.GONE);
        }

        if (mListCarousel != null) {
            // 根据数量显示圆点个数
            for (int i = 0; i < mIvDots.size(); i++) {
                if (i < mListCarousel.size()) {
                    mIvDots.get(i).setVisibility(View.VISIBLE);
                    mIvDots.get(i).setSelected(false);
                } else {
                    mIvDots.get(i).setVisibility(View.GONE);
                }
            }

            mLayoutBanner.setVisibility(View.VISIBLE);
            mRecyclerViewPager.setAdapter(new BannerAdapter(context, mListCarousel));
        }
    }

    public void onDestroy() {
        if (mRecyclerViewPager != null) {
            mRecyclerViewPager.stopImageTimerTask();
            mRecyclerViewPager.removeAllViews();
        }
    }
}
