package com.mjiayou.trecore.widget;

import android.view.View;

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

/**
 * Created by treason on 15/11/15.
 */
public class BannerLayoutChangeListener implements View.OnLayoutChangeListener {

    private RecyclerViewPager mRecyclerViewPager;

    public BannerLayoutChangeListener(RecyclerViewPager recyclerViewPager) {
        this.mRecyclerViewPager = recyclerViewPager;
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (mRecyclerViewPager.getChildCount() < 3) {
            if (mRecyclerViewPager.getChildAt(1) != null) {
                if (mRecyclerViewPager.getCurrentPosition() == 0) {
                    View v1 = mRecyclerViewPager.getChildAt(1);
                    v1.setScaleY(TCConfigs.BANNER_SCALE);
                    v1.setScaleX(TCConfigs.BANNER_SCALE);
                } else {
                    View v1 = mRecyclerViewPager.getChildAt(0);
                    v1.setScaleY(TCConfigs.BANNER_SCALE);
                    v1.setScaleX(TCConfigs.BANNER_SCALE);
                }
            }
        } else {
            if (mRecyclerViewPager.getChildAt(0) != null) {
                View v0 = mRecyclerViewPager.getChildAt(0);
                v0.setScaleY(TCConfigs.BANNER_SCALE);
                v0.setScaleX(TCConfigs.BANNER_SCALE);
            }
            if (mRecyclerViewPager.getChildAt(2) != null) {
                View v2 = mRecyclerViewPager.getChildAt(2);
                v2.setScaleY(TCConfigs.BANNER_SCALE);
                v2.setScaleX(TCConfigs.BANNER_SCALE);
            }
        }
    }
}
