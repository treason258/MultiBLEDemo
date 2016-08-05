package com.mjiayou.trecore.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

/**
 * Created by treason on 15/3/13.
 */
public class AnimUtil {

    // ******************************** 出现 ********************************

    /**
     * View从上向下出现动画
     */
    public static Animation showViewFromUp(long durationMillis) {
        Animation animation = new TranslateAnimation(0, 0, -1000, 0);
        animation.setDuration(durationMillis);
        animation.setInterpolator(new DecelerateInterpolator());
        return animation;
    }

    /**
     * View从左向右出现动画
     */
    public static Animation showViewFromLeft(long durationMillis) {
        Animation animation = new TranslateAnimation(-1000, 0, 0, 0);
        animation.setDuration(durationMillis);
        return animation;
    }

    /**
     * View从右向左出现动画
     */
    public static Animation showViewFromRight(long durationMillis) {
        Animation animation = new TranslateAnimation(1000, 0, 0, 0);
        animation.setDuration(durationMillis);
        return animation;
    }


    // ******************************** 隐藏 ********************************

    /**
     * View从下向上隐藏动画
     */
    public static Animation hideViewFromBottom(long durationMillis) {
        Animation animation = new TranslateAnimation(0, 0, 0, 1000);
        animation.setDuration(durationMillis);
        animation.setInterpolator(new DecelerateInterpolator());
        return animation;
    }

    /**
     * View从右向左隐藏动画
     */
    public static Animation hideViewFromRight(long durationMillis) {
        Animation animation = new TranslateAnimation(0, -1000, 0, 0);
        animation.setDuration(durationMillis);
        return animation;
    }

    // ******************************** 渐隐渐现效果 ********************************

    /**
     * View渐隐动画效果
     */
    public static void setAlphaAnimationHide(final View view, int duration) {
        if (null == view || duration < 0) {
            return;
        }

//        Animation mHideAnimation = new AlphaAnimation(1.0f, 0.01f);
//        mHideAnimation.setDuration(duration);
//        mHideAnimation.setFillAfter(true);
//        mHideAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                view.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        view.startAnimation(mHideAnimation);

        view.animate()
                .alpha(0.01f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.GONE);
                    }
                });

    }

    /**
     * View渐现动画效果
     */
    public static void setAlphaAnimationShow(final View view, long duration) {
        if (null == view || duration < 0) {
            return;
        }

//        AlphaAnimation mShowAnimation = new AlphaAnimation(0.1f, 1.0f);
//        mShowAnimation.setDuration(duration);
//        mShowAnimation.setFillAfter(true);
//        mShowAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                view.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        view.startAnimation(mShowAnimation);

        view.animate()
                .alpha(1.0f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        view.setVisibility(View.VISIBLE);
                    }
                });
    }

    // ******************************** RotateAnimation ********************************

    public static void setRotateAnimation(View view, int duration, float fromDegrees, float toDegrees, float pivotX, float pivotY) {
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
        rotateAnimation.setDuration(duration);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

    public static void setRotateAnimation(View view, Context context, int resId) {
        Animation anim = AnimationUtils.loadAnimation(context, resId);
        anim.setInterpolator(new LinearInterpolator());
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }

    // ******************************** other ********************************

    /**
     * 分类更多显示动画
     */
    public static Animation show(final LinearLayout mLinearLayout) {
        mLinearLayout.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targtetHeight = mLinearLayout.getMeasuredHeight();
        mLinearLayout.getLayoutParams().height = 0;
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                mLinearLayout.getLayoutParams().height = interpolatedTime == 1 ? mLinearLayout.getLayoutParams().WRAP_CONTENT : (int) (targtetHeight * interpolatedTime);
                mLinearLayout.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(200);
        animation.setInterpolator(new DecelerateInterpolator());
        return animation;
    }

    /**
     * 分类更多隐藏动画
     */
    public static Animation hide(final LinearLayout mLinearLayout) {

        final int initialHeight = mLinearLayout.getMeasuredHeight();
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    mLinearLayout.setVisibility(View.GONE);
                } else {
                    mLinearLayout.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    mLinearLayout.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(200);
        animation.setInterpolator(new DecelerateInterpolator());
        return animation;
    }
}
