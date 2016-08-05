package com.mjiayou.trecore.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
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
public class TCAlertDialog extends TCDialog {

    // root
    @Optional
    @InjectView(R.id.view_root)
    ViewGroup mViewRoot;
    // dialog
    @InjectView(R.id.layout_dialog)
    LinearLayout mLayoutDialog;
    // title
    @InjectView(R.id.layout_title)
    LinearLayout mLayoutTitle;
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    // message
    @InjectView(R.id.layout_message)
    LinearLayout mLayoutMessage;
    @InjectView(R.id.tv_message)
    TextView mTvMessage;
    // menu
    @InjectView(R.id.layout_menu)
    LinearLayout mLayoutMenu;
    @InjectView(R.id.layout_cancel)
    LinearLayout mLayoutCancel;
    @InjectView(R.id.tv_cancel)
    TextView mTvCancel;
    @InjectView(R.id.layout_ok)
    LinearLayout mLayoutOk;
    @InjectView(R.id.tv_ok)
    TextView mTvOk;

    private CharSequence mTitleStr = "", mMessageStr = "", mOkStr = "", mCancelStr = "";
    private TCAlertDialog.OnTCActionListener mOnTCActionListener;

    /**
     * 构造函数
     */
    public TCAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
        mResourceId = R.layout.tc_layout_alert_dialog;
    }

    public TCAlertDialog(Context context) {
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
            // title
            setTitle(mTitleStr);
            // message
            setMessage(mMessageStr);
            // menu
            setOkMenu(mOkStr);
            setCancelMenu(mCancelStr);
            setTCActionListener(mOnTCActionListener);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    // title

    public void setTitle(CharSequence title) {
        LogUtil.i(TAG, "setTitle -> " + title);

        boolean titleVisible = !TextUtils.isEmpty(title);
        if (mLayoutTitle != null) {
            mLayoutTitle.setVisibility(titleVisible ? View.VISIBLE : View.GONE);
        }

        if (titleVisible) {
            mTitleStr = title;
            if (null != mTvTitle) {
                mTvTitle.setText(mTitleStr);
            }
        }
    }

    // message

    public void setMessage(CharSequence message) {
        LogUtil.i(TAG, "setMessage -> " + message);

        boolean messageVisible = !TextUtils.isEmpty(message);
        if (mLayoutMessage != null) {
            mLayoutMessage.setVisibility(messageVisible ? View.VISIBLE : View.GONE);
        }

        if (messageVisible) {
            mMessageStr = message;
            if (mTvMessage != null) {
                mTvMessage.setText(mMessageStr);
            }
        }
    }

    // menu

    public void setOkMenu(CharSequence okStr) {
        LogUtil.i(TAG, "setOkMenu -> " + okStr);

        boolean okVisible = !TextUtils.isEmpty(okStr);
        if (mLayoutOk != null) {
            mLayoutOk.setVisibility(okVisible ? View.VISIBLE : View.GONE);
        }

        if (okVisible) {
            mOkStr = okStr;

            if (mTvOk != null) {
                mTvOk.setText(mOkStr);
                mTvOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                        if (mOnTCActionListener != null) {
                            mOnTCActionListener.onOkAction();
                        }
                    }
                });
            }
        }
    }

    public void setCancelMenu(CharSequence cancelStr) {
        LogUtil.i(TAG, "setCancleMenu -> " + cancelStr);

        boolean cancelVisible = !TextUtils.isEmpty(cancelStr);
        if (mLayoutCancel != null) {
            mLayoutCancel.setVisibility(cancelVisible ? View.VISIBLE : View.GONE);
        }

        if (cancelVisible) {
            mCancelStr = cancelStr;

            if (mTvCancel != null) {
                mTvCancel.setText(mCancelStr);
                mTvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                        if (mOnTCActionListener != null) {
                            mOnTCActionListener.onCancelAction();
                        }
                    }
                });
            }
        }
    }

    public void setTCActionListener(final TCAlertDialog.OnTCActionListener mOnTCActionListener) {
        this.mOnTCActionListener = mOnTCActionListener;
    }

    /**
     * OnDialogActionListener
     */
    public interface OnTCActionListener {
        void onOkAction();

        void onCancelAction();
    }
}
