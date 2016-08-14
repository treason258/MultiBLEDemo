package com.mjiayou.trecore.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.mjiayou.trecore.TCActivity;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.mjiayou.trecoredemo.R;

public class TestHacksActivity extends TCActivity {

    @InjectView(R.id.tv_test_a)
    TextView mTvTestA;

    @InjectView(R.id.tv_test_b)
    TextView mTvTestB;

    @InjectView(R.id.textswitcher_a)
    TextSwitcher mTextSwitcherA;
    @InjectView(R.id.textswitcher_b)
    TextSwitcher mTextSwitcherB;
    @InjectView(R.id.imageswitcher_a)
    ImageSwitcher mImageSwitcherA;
    @InjectView(R.id.imageswitcher_b)
    ImageSwitcher mImageSwitcherB;
    @InjectView(R.id.btn_textswitcher)
    Button mBtnTextSwitcher;

    /**
     * startActivity
     */
    public static void open(Context context) {
        Intent intent = new Intent(context, TestHacksActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tc_activity_test_hacks);
        ButterKnife.inject(this);

        setTitle("TestHacksActivity");

        initView();
    }

    @Override
    public void initView() {
        super.initView();
        // 富文本-HTML
        String html = "<font color='red'>曾梦想仗剑走天涯</font><br>"
                + "<font color='#00ff00'><big><i>看一看世界的繁华</i></big></font><p>"
                + "<big><a href='http://www.baidu.com'>百度一下</a></big>";
        CharSequence charSequence = Html.fromHtml(html);
        mTvTestA.setText(charSequence);
        mTvTestA.setMovementMethod(LinkMovementMethod.getInstance()); // 点击的时候产生超链接

        // 富文本-autoLink
        String str = "我的URL：http://www.mjiayou.com\n"
                + "我的Email：18600574121@163.com\n"
                + "我的电话：+86 010-12345678";
        mTvTestB.setText(str);
        mTvTestB.setMovementMethod(LinkMovementMethod.getInstance());

        // mTextSwitcher
        mTextSwitcherA.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.tc_translate_in_from_left));
        mTextSwitcherA.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.tc_translate_out_to_right));
        mTextSwitcherA.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return new TextView(mContext);
            }
        });

        mTextSwitcherB.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.tc_translate_in_from_bottom));
        mTextSwitcherB.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.tc_translate_out_to_top));
        mTextSwitcherB.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(mContext);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(20);
                return textView;
            }
        });

        mImageSwitcherA.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.tc_translate_in_from_left));
        mImageSwitcherA.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.tc_translate_out_to_right));
        mImageSwitcherA.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return new ImageView(mContext);
            }
        });

        mImageSwitcherB.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.tc_translate_in_from_bottom));
        mImageSwitcherB.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.tc_translate_out_to_top));
        mImageSwitcherB.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(mContext);
                return imageView;
            }
        });

        mBtnTextSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextSwitcherA.setText(String.valueOf(new Random().nextInt()));
                mTextSwitcherB.setText(String.valueOf(new Random().nextInt()));
//                mImageSwitcherA.setImageDrawable(getResources().getDrawable(R.drawable.tc_shape_rect_solid_theme));
            }
        });
        mBtnTextSwitcher.performClick();
    }
}
