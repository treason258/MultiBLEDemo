package com.mjiayou.trecore.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mjiayou.trecore.bean.entity.TCRect;

import com.mjiayou.trecoredemo.R;

/**
 * Created by treason on 15/11/15.
 */
public class ViewUtil {

    public static final String TAG = "ViewUtil";

    public static final int NONE_SIZE = Integer.MIN_VALUE;

    /**
     * OnGetViewWidthAndHeightListener
     */
    public interface OnGetViewWidthAndHeightListener {
        void onGet(int width, int height);
    }

    /**
     * 重新设置view的宽高大小
     */
    public static void resize(View view, int width, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
//         LogUtil.i(TAG, "width -> " + width + ";height -> " + height);
    }

    /**
     * 重新设置Banner的宽高 HomeFragment、VideoRecommendFragment、EquipRecommendFragment
     */
    public static void resizeBanner(Context context, View view) {
        // 设置图片展示宽高-585*287/640
//        float edge = context.getResources().getDimension(R.dimen.item_home_card_margin_right) +
//                context.getResources().getDimension(R.dimen.item_home_card_margin_left) +
//                context.getResources().getDimension(R.dimen.item_home_image_padding) * 2;
        float edge = 0;
        int width = DeviceUtil.getScreenWidth(context);
        int height = (int) ((width - edge) * 287.0f / 585.0f);
        resize(view, width, height);
    }

    public static void resizeHomeNew(Context context, View view) {
        float edge = context.getResources().getDimension(R.dimen.tc_margin_mini_2) * 2 +
                context.getResources().getDimension(R.dimen.tc_margin_mini_2) * 3 * 2;
        int width = (int) ((DeviceUtil.getScreenWidth(context) - edge) / 3);
        int height = width;
        resize(view, width, height);
    }

    public static void resizeHomeHot(Context context, View view) {
        float edge = 0;
        int width = (int) (DeviceUtil.getScreenWidth(context) - edge);
        int height = width;
        resize(view, width, height);
    }

    /**
     * 获取无数据时的页面
     */
    public static View getEmptyView(Context context, String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.tc_layout_data_empty, null);
        ImageView ivImg = (ImageView) view.findViewById(R.id.iv_img);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
        tvMessage.setText(text);
        return view;
    }

    public static View getEmptyView(Context context) {
        return getEmptyView(context, "暂无数据");
    }


    /**
     * 设置空间的高度和宽度
     */
    public static void setWidthAndHeight(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (width != NONE_SIZE) {
            layoutParams.width = width;
        }
        if (height != NONE_SIZE) {
            layoutParams.height = height;
        }
        view.setLayoutParams(layoutParams);
    }

    public static void setWidthAndHeightOld(View view, int width, int height) {
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginLayoutParams);
        if (width != NONE_SIZE) {
            layoutParams.width = width;
        }
        if (height != NONE_SIZE) {
            layoutParams.height = height;
        }
        view.setLayoutParams(layoutParams);
    }

    /**
     * 获取控件的高度和宽度，自己测量
     */
    public static TCRect getWidthAndHeight(final View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);

        TCRect tcRect = new TCRect();
        tcRect.setWidth(view.getMeasuredWidth());
        tcRect.setHeight(view.getMeasuredHeight());
        return tcRect;
    }

    public static void getWidthAndHeight(final View view, final OnGetViewWidthAndHeightListener onGetViewWidthAndHeightListener) {
        final ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                onGetViewWidthAndHeightListener.onGet(view.getMeasuredWidth(), view.getMeasuredHeight());

            }
        });
    }

    public static void getWidthAndHeightOld(final View view, final OnGetViewWidthAndHeightListener onGetViewWidthAndHeightListener) {
        final ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                onGetViewWidthAndHeightListener.onGet(view.getMeasuredWidth(), view.getMeasuredHeight());
                return true;
            }
        });
    }
}
