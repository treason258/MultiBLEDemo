package com.mjiayou.trecore.ui.test;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mjiayou.trecore.ui.TCActivity;
import com.mjiayou.trecore.util.AssetUtil;
import com.mjiayou.trecore.util.ToastUtil;
import com.mjiayou.trecore.widget.BaseVideoView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.mjiayou.trecoredemo.R;

public class TestVideoActivity extends TCActivity {

    @InjectView(R.id.layout_videoview)
    RelativeLayout mLayoutVideoView;
    @InjectView(R.id.videoview)
    BaseVideoView mVideoView;
    @InjectView(R.id.tv_skip_video)
    TextView mTvSkipVideo;

    @Override
    public boolean getShowToolBar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tc_activity_test_video);
        ButterKnife.inject(this);

        final Uri uri = Uri.parse(AssetUtil.getRawUriString(mContext, R.raw.tc_test_soccer));

        mVideoView.setVideoURI(uri);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                ToastUtil.show(mContext, "视频开始");
                mp.start();
                mp.setLooping(true);
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideoView.setVideoURI(uri);
                mVideoView.start();
                ToastUtil.show(mContext, "视频结束，重新开始");
            }
        });
        mVideoView.start();

        mTvSkipVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
