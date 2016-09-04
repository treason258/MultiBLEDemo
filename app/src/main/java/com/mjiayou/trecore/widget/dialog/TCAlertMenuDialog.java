package com.mjiayou.trecore.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mjiayou.trecore.bean.entity.TCMenu;
import com.mjiayou.trecore.bean.entity.TCRect;
import com.mjiayou.trecore.util.DeviceUtil;
import com.mjiayou.trecore.util.LogUtil;
import com.mjiayou.trecore.util.ViewUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

import com.mjiayou.trecore.widget.Configs;
import com.mjiayou.trecoredemo.R;

/**
 * Created by treason on 16/6/27.
 */
public class TCAlertMenuDialog extends TCDialog {

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
    @InjectView(R.id.layout_option_container)
    LinearLayout mLayoutOptionContainer;

    private CharSequence mTitleStr = "", mMessageStr = "";
    private List<TCMenu> mTCMenus;

    /**
     * 构造函数
     */
    public TCAlertMenuDialog(Context context, int themeResId) {
        super(context, themeResId);
        mResourceId = R.layout.tc_layout_alert_menu_dialog;
    }

    public TCAlertMenuDialog(Context context) {
        this(context, R.style.tc_dialog_theme_default);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mResourceId);
        ButterKnife.inject(this);

        // 设置窗口宽高
        if (mViewRoot != null) {
            ViewUtil.setWidthAndHeight(mViewRoot, Configs.getScreenWidth(WIDTH_RATIO_DEFAULT), WindowManager.LayoutParams.WRAP_CONTENT);
        }

        try {
            // title
            setTitle(mTitleStr);
            // message
            setMessage(mMessageStr);
            // menu
            setMenu(mTCMenus);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }

        // 设置窗口最大高度
        if (mViewRoot != null) {
            TCRect rootRect = ViewUtil.getWidthAndHeight(mViewRoot);
            int maxHeight = (int) (DeviceUtil.getScreenHeight(mContext) * WIDTH_RATIO_DEFAULT);
            if (rootRect.getHeight() > maxHeight) {
                ViewUtil.setWidthAndHeight(mViewRoot, ViewUtil.NONE_SIZE, maxHeight);
            }
        }
    }

    // ******************************** 自定义操作 ********************************

    // title

    public void setTitle(CharSequence title) {
        LogUtil.i(TAG, "setTitle -> " + title);

        boolean titleVisible = !TextUtils.isEmpty(title);
        if (mLayoutTitle != null) {
            mLayoutTitle.setVisibility(titleVisible ? View.VISIBLE : View.GONE);
        }

        if (titleVisible) {
            mTitleStr = title;
            if (mTvTitle != null) {
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

    public void setMenu(List<TCMenu> tcMenus) {
        this.mTCMenus = tcMenus;
        if (mTCMenus != null) {
            // 移除
            clearOptionContainer();
            // 添加
            for (TCMenu tcMenu : mTCMenus) {
                // option & cancel
                addOptionTextView(tcMenu.getText(), tcMenu.getOnClickListener());
            }
        }
    }

    // ******************************** 封装 ********************************

    /**
     * View添加到Layout中
     */
    protected void addViewToLayout(LinearLayout parentLayout, View childView, final View.OnClickListener onClickListener) {
        parentLayout.setVisibility(View.VISIBLE);
        childView.setVisibility(View.VISIBLE);
        parentLayout.addView(childView);

        childView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onClickListener != null) {
                    onClickListener.onClick(v);
                }
            }
        });
    }

    /**
     * 清除Layout中子View
     */
    protected void clearLayout(LinearLayout parentLayout) {
        parentLayout.removeAllViews();
    }

    /**
     * 添加TextView
     */
    protected void addTextView(LinearLayout parentLayout, String text, View.OnClickListener onClickListener) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tc_layout_menu_item_left, parentLayout, false);
        TextView tvMenu = (TextView) view.findViewById(R.id.tv_menu);
        tvMenu.setText(text);
        addViewToLayout(parentLayout, view, onClickListener);
    }

    // ******************************** 快捷方式 ********************************

    /**
     * Option 容器操作
     */
    protected void addOptionTextView(String text, View.OnClickListener onClickListener) {
        if (mLayoutOptionContainer != null) {
            addTextView(mLayoutOptionContainer, text, onClickListener);
        }
    }

    protected void clearOptionContainer() {
        if (mLayoutOptionContainer != null) {
            clearLayout(mLayoutOptionContainer);
        }
    }
}
