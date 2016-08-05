package com.mjiayou.trecore.widget.dialog;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mjiayou.trecore.util.DeviceUtil;
import com.mjiayou.trecore.util.LogUtil;
import com.mjiayou.trecore.util.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import com.mjiayou.trecoredemo.R;

/**
 * Created by treason on 16/5/27.
 */
public class TCLoadingDialog extends TCDialog {

    protected static final String TAG = "TCLoadingDialog";
    private static TCLoadingDialog mTCLoadingDialog;

    // root
    @Optional
    @InjectView(R.id.view_root)
    ViewGroup mViewRoot;
    @InjectView(R.id.iv_bg)
    ImageView mIvBg;
    @InjectView(R.id.iv_loading)
    ImageView mIvLoading;
    @InjectView(R.id.tv_message)
    TextView mTvMessage;

    private boolean mMessageVisible = true;
    private CharSequence mMessageStr = "";

    public TCLoadingDialog(Context context, int theme) {
        super(context, theme);
        mResourceId = R.layout.tc_layout_loading_dialog;
    }

    public TCLoadingDialog(Context context) {
        this(context, R.style.tc_dialog_theme_default);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mResourceId);
        ButterKnife.inject(this);

        // 窗口宽度
        if (mViewRoot != null) {
            int width = (int) (DeviceUtil.getScreenWidth(mContext) * WIDTH_RATIO_DEFAULT);
            int height = WindowManager.LayoutParams.WRAP_CONTENT;
            ViewUtil.setWidthAndHeight(mViewRoot, width, height);
        }

        try {
            // message
            if (TextUtils.isEmpty(mMessageStr)) {
                mMessageStr = "请稍后...";
            }
            setMessage(mMessageStr);

        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        AnimationDrawable animationDrawable = (AnimationDrawable) mIvLoading.getBackground();
        animationDrawable.start();
    }

    // message
    @Override
    public void setMessage(CharSequence message) {
        super.setMessage(message);

        mMessageVisible = !TextUtils.isEmpty(message);
        if (mTvMessage != null) {
            mTvMessage.setVisibility(mMessageVisible ? View.VISIBLE : View.GONE);
        }

        if (mMessageVisible) {
            mMessageStr = message;
            if (mTvMessage != null) {
                mTvMessage.setText(mMessageStr);
            }
        }
    }

    // ******************************** TCLoadingDialog ********************************

    /**
     * 创建 TCLoadingDialog
     */
    public static TCLoadingDialog createDialog(Context context) {
        if (mTCLoadingDialog == null) {
            mTCLoadingDialog = new TCLoadingDialog(context);
            mTCLoadingDialog.setCancelable(true);
            mTCLoadingDialog.setCanceledOnTouchOutside(false);
            mTCLoadingDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        } else if (context != mTCLoadingDialog.getContext()) {
            LogUtil.w(TAG, "此处引发窗体泄露 | orign context -> " + mTCLoadingDialog.getContext() + " | now context -> " + context);
            dismissDialog();

            mTCLoadingDialog = new TCLoadingDialog(context);
            mTCLoadingDialog.setCancelable(true);
            mTCLoadingDialog.setCanceledOnTouchOutside(false);
            mTCLoadingDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        }
        return mTCLoadingDialog;
    }

    /**
     * 释放 TCLoadingDialog
     */
    public static void dismissDialog() {
        if (mTCLoadingDialog == null) {
            return;
        }

        if (mTCLoadingDialog.isShowing()) {
            try {
                mTCLoadingDialog.dismiss();
                mTCLoadingDialog = null;
            } catch (Exception e) {
                LogUtil.printStackTrace(e);
            }
        }
    }

    /**
     * 更新 TCLoadingDialog
     */
    public static void updateDialog(String message) {
        if (mTCLoadingDialog != null && mTCLoadingDialog.isShowing() && !TextUtils.isEmpty(message)) {
            mTCLoadingDialog.setMessage(message);
        }
    }
}