package com.mjiayou.trecore.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mjiayou.trecore.bean.entity.TCUser;
import com.mjiayou.trecore.util.DeviceUtil;
import com.mjiayou.trecore.util.LogUtil;
import com.mjiayou.trecore.util.ViewUtil;
import com.mjiayou.trecoredemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by treason on 16/7/12.
 */
public class ManagerListDialog extends TCDialog {

    protected static final String TAG = "ManagerListDialog";
    private static ManagerListDialog mManagerListDialog;

    // root
    @Optional
    @InjectView(R.id.view_root)
    ViewGroup mViewRoot;
    // dialog
    @InjectView(R.id.layout_dialog)
    LinearLayout mLayoutDialog;
    // title
    @InjectView(R.id.iv_menu)
    ImageView mIvMenu;
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    // list
    @InjectView(R.id.lv_user)
    ListView mLvUser;

    private static String mUUID = "";

    private List<TCUser> mListUser = new ArrayList<>();

    public ManagerListDialog(Context context, int themeResId) {
        super(context, themeResId);
        mResourceId = R.layout.layout_manager_list;
    }

    public ManagerListDialog(Context context) {
        this(context, R.style.tc_dialog_theme_default);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mResourceId);
        ButterKnife.inject(this);

        // 窗口宽度高度
        if (mViewRoot != null) {
            int width = (int) (DeviceUtil.getScreenWidth(mContext) * WIDTH_RATIO_DEFAULT);
            int height = (int) (DeviceUtil.getScreenHeight(mContext) * WIDTH_RATIO_DEFAULT);
            ViewUtil.setWidthAndHeight(mViewRoot, width, height);
        }

        try {
            // mIvMenu
            mIvMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissDialog();
                }
            });

        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 设置账号
     */
    public static void setUUID(String uuid) {
        mUUID = uuid;
    }

    // ******************************** ManagerListDialog ********************************

    /**
     * 创建 ManagerListDialog
     */

    public static ManagerListDialog createDialog(Context context, String uuid) {
        setUUID(uuid);

        boolean needCreate = false;

        // 当 mManagerListDialog 为 null 时，需要重新创建
        if (mManagerListDialog == null) {
            needCreate = true;
        }

        // 当 mManagerListDialog 不为 null，但是 getContext 不是 context 时
        if (mManagerListDialog != null && context != mManagerListDialog.getContext()) {
            LogUtil.w(TAG, "此处引发窗体泄露 | orign context -> " + mManagerListDialog.getContext() + " | now context -> " + context);
            dismissDialog();
            needCreate = true;
        }

        // 根据需要重新创建
        if (needCreate) {
            mManagerListDialog = new ManagerListDialog(context);
        }

        return mManagerListDialog;
    }

    /**
     * 释放 ManagerListDialog
     */
    public static void dismissDialog() {
        if (mManagerListDialog == null) {
            return;
        }

        if (mManagerListDialog.isShowing()) {
            try {
                mManagerListDialog.dismiss();
                mManagerListDialog = null;
            } catch (Exception e) {
                LogUtil.printStackTrace(e);
            }
        }
    }
}
