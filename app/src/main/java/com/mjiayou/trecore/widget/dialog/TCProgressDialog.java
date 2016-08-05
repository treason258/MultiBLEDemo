package com.mjiayou.trecore.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
public class TCProgressDialog extends TCDialog {

    private static final String TAG = "TCProgressDialog";
    private static TCProgressDialog mTCProgressDialog;

    // root
    @Optional
    @InjectView(R.id.view_root)
    ViewGroup mViewRoot;
    // dialog
    @InjectView(R.id.layout_dialog)
    LinearLayout mLayoutDialog;
    // progressbar
    @InjectView(R.id.progressbar)
    ProgressBar mProgressBar;
    // message
    @InjectView(R.id.tv_message)
    TextView mTvMessage;

    private boolean mMessageVisible = true;
    private CharSequence mMessageStr = "";

    public TCProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        mResourceId = R.layout.tc_layout_progress_dialog;
    }

    public TCProgressDialog(Context context) {
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

    // ******************************** TCProgressDialog ********************************

    /**
     * 创建 TCProgressDialog
     */
    public static TCProgressDialog createDialog(Context context) {
        if (mTCProgressDialog == null) {
            mTCProgressDialog = new TCProgressDialog(context);
            mTCProgressDialog.setCancelable(true);
            mTCProgressDialog.setCanceledOnTouchOutside(false);
            mTCProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        } else if (context != mTCProgressDialog.getContext()) {
            LogUtil.w(TAG, "此处引发窗体泄露 | orign context -> " + mTCProgressDialog.getContext() + " | now context -> " + context);
            dismissDialog();

            mTCProgressDialog = new TCProgressDialog(context);
            mTCProgressDialog.setCancelable(true);
            mTCProgressDialog.setCanceledOnTouchOutside(false);
            mTCProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        }
        return mTCProgressDialog;
    }

    /**
     * 释放 TCProgressDialog
     */
    public static void dismissDialog() {
        if (mTCProgressDialog == null) {
            return;
        }

        if (mTCProgressDialog.isShowing()) {
            try {
                mTCProgressDialog.dismiss();
                mTCProgressDialog = null;
            } catch (Exception e) {
                LogUtil.printStackTrace(e);
            }
        }
    }

    /**
     * 更新 TCProgressDialog
     */
    public static void updateDialog(String message) {
        if (mTCProgressDialog != null && mTCProgressDialog.isShowing() && !TextUtils.isEmpty(message)) {
            mTCProgressDialog.setMessage(message);
        }
    }
}