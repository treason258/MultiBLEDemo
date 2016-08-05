package com.mjiayou.trecore.widget;

import android.widget.ImageView;

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.List;

/**
 * Created by treason on 15/11/15.
 */
public class BannerPageChangedListener implements RecyclerViewPager.OnPageChangedListener {

    private List<ImageView> mIvDots;

    public BannerPageChangedListener(List<ImageView> ivDots) {
        this.mIvDots = ivDots;
    }

    @Override
    public void OnPageChanged(int oldPosition, int newPosition) {
    }

    @Override
    public void OnActualPageChanged(int oldPosition, int newPosition) {
//        LogUtil.i("oldPosition -> " + oldPosition + " | newPosition -> " + newPosition);

        if (mIvDots != null) {
            for (int i = 0; i < mIvDots.size(); i++) {
                mIvDots.get(i).setSelected(newPosition == i);
            }
        }
    }
}
