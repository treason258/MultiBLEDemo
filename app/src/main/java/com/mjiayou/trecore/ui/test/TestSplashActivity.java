package com.mjiayou.trecore.ui.test;

import android.os.Bundle;
import android.widget.ImageView;

import com.mjiayou.trecore.ui.TCActivity;
import com.mjiayou.trecore.util.HandlerUtil;
import com.mjiayou.trecore.widget.TCRouter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.mjiayou.trecoredemo.R;

public class TestSplashActivity extends TCActivity {

    @InjectView(R.id.iv_splash)
    ImageView mIvSplash;

    @Override
    public boolean getShowToolBar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tc_activity_test_splash);
        ButterKnife.inject(this);

        mIvSplash.setImageDrawable(getResources().getDrawable(R.drawable.tc_launcher));

        // 延迟显示 mIvSplashContent
        HandlerUtil.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TCRouter.openTestMainActivity(mContext);
                finish();
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HandlerUtil.destory();
    }
}
