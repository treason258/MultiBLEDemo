package com.mjiayou.trecore.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.mjiayou.trecore.util.DeviceUtil;
import com.mjiayou.trecore.util.LogUtil;
import com.mjiayou.trecore.util.ViewUtil;

import butterknife.InjectView;
import butterknife.Optional;
import com.mjiayou.trecoredemo.R;

/**
 * Created by treason on 16/5/27.
 */
public class TCDialog extends Dialog {

    protected final String TAG = this.getClass().getSimpleName();
    protected final static double WIDTH_RATIO_DEFAULT = 0.85;
    protected final static double WIDTH_RATIO_BIG = 0.95;
    protected final static double WIDTH_RATIO_FULL = 1.0;
    protected final static double WIDTH_RATIO_HALF = 0.6;

    protected Context mContext;
    protected int mResourceId;

    public TCDialog(Context context, int themeResId) {
        super(context, themeResId);
        LogUtil.i(TAG, "onConstruct");
        mContext = context;

        // 窗口全屏 - 然后并没有什么卵用
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        getWindow().setAttributes(params);

        // 窗口显示位置 - 默认位置居中
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
    }

    public TCDialog(Context context) {
        this(context, R.style.tc_dialog_theme_default);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.i(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.i(TAG, "onStop");
    }

    public void setMessage(CharSequence message) {
        LogUtil.i(TAG, "setMessage -> " + message);
    }
}
