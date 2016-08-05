package com.mjiayou.trecore.widget.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;

import com.mjiayou.trecore.util.LogUtil;

/**
 * Created by treason on 16/5/28.
 */
public class DefaultProgressDialog {

    protected static final String TAG = "TCLoadingDialog";
    private static ProgressDialog mProgressDialog;

    /**
     * 创建 DefaultProgressDialog
     */
    public static ProgressDialog createDialog(Context context) {
        if (null == mProgressDialog) {
            mProgressDialog = DialogHelper.createProgressDialog(context, null, "请稍候...");
            mProgressDialog.setCancelable(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        } else if (context != mProgressDialog.getContext()) {
            LogUtil.w(TAG, "此处引发窗体泄露 | orign context -> " + mProgressDialog.getContext() + " | now context -> " + context);
            dismissDialog();

            mProgressDialog = DialogHelper.createProgressDialog(context, null, "请稍候...");
            mProgressDialog.setCancelable(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        }
        return mProgressDialog;
    }

    /**
     * 释放 TCProgressDefaultProgressDialogDialog
     */
    public static void dismissDialog() {
        if (null == mProgressDialog) {
            return;
        }

        if (mProgressDialog.isShowing()) {
            try {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            } catch (Exception e) {
                LogUtil.printStackTrace(e);
            }
        }
    }

    /**
     * 更新 DefaultProgressDialog
     */
    public static void updateDialog(String message) {
        if (null != mProgressDialog && mProgressDialog.isShowing() && !TextUtils.isEmpty(message)) {
            mProgressDialog.setMessage(message);
        }
    }
}
