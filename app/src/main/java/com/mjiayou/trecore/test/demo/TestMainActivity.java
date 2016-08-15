package com.mjiayou.trecore.test.demo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.f2prateek.dart.Dart;
import com.mjiayou.trecore.TCActivity;
import com.mjiayou.trecore.test.demo.TestHomeFragment;
import com.mjiayou.trecore.test.demo.TestUserFragment;
import com.mjiayou.trecore.test.demo.TestWeiboFragment;
import com.mjiayou.trecore.util.ToastUtil;

import com.mjiayou.trecoredemo.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

public class TestMainActivity extends TCActivity {

    private final int FRAGMENT_ALL_COUNT = 3;
    private final int FRAGMENT_HOME = 0;
    private final int FRAGMENT_WEIBO = 1;
    private final int FRAGMENT_USER = 2;

    @InjectView(R.id.viewpager)
    ViewPager mViewPager;
    @InjectViews({R.id.tv_navi_home, R.id.tv_navi_weibo, R.id.tv_navi_user})
    List<TextView> mTvNavis;

    @OnClick({R.id.tv_navi_home, R.id.tv_navi_weibo, R.id.tv_navi_user})
    public void onClickNavis(View view) {
        switch (view.getId()) {
            case R.id.tv_navi_home:
            case R.id.tv_navi_weibo:
            case R.id.tv_navi_user:
                if (view instanceof TextView) {
                    // 中间Fragment视图变化
                    int index = mTvNavis.indexOf(view);
                    mViewPager.setCurrentItem(index, false);
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tc_activity_test_main);
        ButterKnife.inject(this);
        Dart.inject(this);

        setTitle("首页");
        clearLeftContainer();
        clearRightContainer();

        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mViewPager != null) {
            mViewPager.removeAllViews();
        }
    }

    @Override
    public void initView() {
        // mViewPager
        mViewPager.setAdapter(new NaviAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new NaviPageChangeListener(FRAGMENT_HOME));
        mViewPager.setOffscreenPageLimit(FRAGMENT_ALL_COUNT);
        mViewPager.setCurrentItem(FRAGMENT_HOME, false);
    }

    /**
     * NaviAdapter
     */
    public class NaviAdapter extends FragmentPagerAdapter {

        public NaviAdapter(FragmentManager fm) {
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
                case FRAGMENT_HOME:
                    return new TestHomeFragment();
                case FRAGMENT_WEIBO:
                    return new TestWeiboFragment();
                case FRAGMENT_USER:
                    return new TestUserFragment();
            }
        }

        @Override
        public int getCount() {
            return FRAGMENT_ALL_COUNT;
        }
    }

    /**
     * NaviPageChangeListener
     */
    private class NaviPageChangeListener implements ViewPager.OnPageChangeListener {

        public NaviPageChangeListener(int index) {
            onPageSelected(index);
        }

        @Override
        public void onPageScrolled(int i, float v, int i2) {
        }

        @Override
        public void onPageSelected(int index) {
            // 导航按钮选中状态变化
            for (int i = 0; i < mTvNavis.size(); i++) {
                mTvNavis.get(i).setSelected(i == index);
            }
            switch (index) {
                case FRAGMENT_HOME:
                    ToastUtil.show(mContext, "首页");

                    // title
                    setTitle("首页");
                    // left
                    clearLeftContainer();
                    // right
                    clearRightContainer();
                    break;
                case FRAGMENT_WEIBO:
                    ToastUtil.show(mContext, "微博");

                    // title
                    setTitle("微博");
                    // left
                    clearLeftContainer();
                    // right
                    clearRightContainer();
                    break;
                case FRAGMENT_USER:
                    ToastUtil.show(mContext, "我的");

                    // title
                    setTitle("我的");
                    // left
                    clearLeftContainer();
                    // right
                    clearRightContainer();
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    }
}
