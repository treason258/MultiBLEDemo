package com.mjiayou.trecore.test.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mjiayou.trecore.ui.TCFragment;
import com.mjiayou.trecore.util.ToastUtil;

import com.mjiayou.trecoredemo.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

/**
 * Created by treason on 16/5/16.
 */
public class TestWeiboFragment extends TCFragment {

    private final int FRAGMENT_ALL_COUNT = 2;
    private final int FRAGMENT_TAB_A = 0;
    private final int FRAGMENT_TAB_B = 1;

    @InjectView(R.id.viewpager)
    ViewPager mViewPager;
    @InjectViews({R.id.layout_tab_a, R.id.layout_tab_b})
    List<LinearLayout> mLayoutTabs;
    @InjectViews({R.id.tv_tab_a, R.id.tv_tab_b})
    List<TextView> mTvTabs;
    @InjectViews({R.id.iv_tab_a, R.id.iv_tab_b})
    List<ImageView> mIvTabs;

    @OnClick({R.id.layout_tab_a, R.id.layout_tab_b,
            R.id.tv_tab_a, R.id.tv_tab_b,
            R.id.iv_tab_a, R.id.iv_tab_b})
    public void onClickTabs(View view) {
        switch (view.getId()) {
            case R.id.layout_tab_a:
            case R.id.layout_tab_b:
                if (view instanceof LinearLayout) {
                    // 中间Fragment视图变化
                    int index = mLayoutTabs.indexOf(view);
                    mViewPager.setCurrentItem(index, true);
                }
                break;

            case R.id.tv_tab_a:
            case R.id.iv_tab_a:
                onClickTabs(mLayoutTabs.get(FRAGMENT_TAB_A));
                break;
            case R.id.tv_tab_b:
            case R.id.iv_tab_b:
                onClickTabs(mLayoutTabs.get(FRAGMENT_TAB_B));
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.tc_fragment_test_weibo, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mViewPager != null) {
            mViewPager.removeAllViews();
        }
    }

    @Override
    public void initView() {
        super.initView();
        // mViewPager
        mViewPager.setAdapter(new TabAdapter(getChildFragmentManager()));
        mViewPager.addOnPageChangeListener(new TabPageChangeListener(FRAGMENT_TAB_A));
        mViewPager.setOffscreenPageLimit(FRAGMENT_ALL_COUNT);
        mViewPager.setCurrentItem(FRAGMENT_TAB_A, true);
    }

    /**
     * TabAdapter
     */
    public class TabAdapter extends FragmentPagerAdapter {

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int i) {
            switch (i) {
                default:
                case FRAGMENT_TAB_A:
                    return new TestWeiboAFragment();
                case FRAGMENT_TAB_B:
                    return new TestWeiboBFragment();
            }
        }

        @Override
        public int getCount() {
            return FRAGMENT_ALL_COUNT;
        }
    }

    /**
     * TabPageChangeListener
     */
    private class TabPageChangeListener implements ViewPager.OnPageChangeListener {

        public TabPageChangeListener(int index) {
            onPageSelected(index);
        }

        @Override
        public void onPageScrolled(int i, float v, int i2) {
        }

        @Override
        public void onPageSelected(int index) {
            // 导航按钮选中状态变化
            for (int i = 0; i < mTvTabs.size(); i++) {
                mTvTabs.get(i).setSelected(i == index);
                mIvTabs.get(i).setSelected(i == index);
            }
            switch (index) {
                case FRAGMENT_TAB_A:
                    ToastUtil.show(mContext, "微博—A");
                    break;
                case FRAGMENT_TAB_B:
                    ToastUtil.show(mContext, "微博—B");
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    }
}
