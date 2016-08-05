package com.mjiayou.trecore.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

/**
 * Created by treason on 15/11/15.
 */
public class BannerScrollListener extends RecyclerView.OnScrollListener {

    private RecyclerViewPager mRecyclerViewPager;

    public BannerScrollListener(RecyclerViewPager recyclerViewPager) {
        this.mRecyclerViewPager = recyclerViewPager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int childCount = mRecyclerViewPager.getChildCount();
        int width = mRecyclerViewPager.getChildAt(0).getWidth();
        int padding = (mRecyclerViewPager.getWidth() - width) / 2;

        for (int j = 0; j < childCount; j++) {
            View v = recyclerView.getChildAt(j);
            //往左 从 padding 到 -(v.getWidth()-padding) 的过程中，由大到小
            float rate = 0;
            if (v.getLeft() <= padding) {
                if (v.getLeft() >= padding - v.getWidth()) {
                    rate = (padding - v.getLeft()) * 1f / v.getWidth();
                } else {
                    rate = 1;
                }
                v.setScaleY(1 - rate * (1 - TCConfigs.BANNER_SCALE));
                v.setScaleX(1 - rate * (1 - TCConfigs.BANNER_SCALE));

            } else {
                //往右 从 padding 到 recyclerView.getWidth()-padding 的过程中，由大到小
                if (v.getLeft() <= recyclerView.getWidth() - padding) {
                    rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                }
                v.setScaleY(TCConfigs.BANNER_SCALE + rate * (1 - TCConfigs.BANNER_SCALE));
                v.setScaleX(TCConfigs.BANNER_SCALE + rate * (1 - TCConfigs.BANNER_SCALE));
            }
        }
    }
}
