package com.mjiayou.trecore.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
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
import com.mjiayou.trecore.helper.Configs;
import com.mjiayou.trecoredemo.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by treason on 16/6/27.
 */
public class TCBottomMenuDialog extends TCDialog {

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
    @InjectView(R.id.layout_cancel_container)
    LinearLayout mLayoutCancelContainer;

    private CharSequence mTitleStr = "", mMessageStr = "";
    private List<TCMenu> mTCMenus;

    private LayoutType mLayoutType;

    /**
     * LayoutType
     */
    public enum LayoutType {
        DEFAULT(0, "default"),
        HOHO(1, "hoho");

        private int id;
        private String value;

        LayoutType(int id, String value) {
            this.id = id;
            this.value = value;
        }

        static LayoutType getLayoutType(int id) {
            for (LayoutType layoutType : values()) {
                if (layoutType.getId() == id) {
                    return layoutType;
                }
            }
            return DEFAULT;
        }

        public int getId() {
            return id;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 构造函数
     */
    public TCBottomMenuDialog(Context context, LayoutType layoutType, int themeResId) {
        super(context, themeResId);
        mLayoutType = layoutType;

        switch (mLayoutType) {
            default:
            case DEFAULT:
                mResourceId = R.layout.tc_layout_bottom_menu_dialog;
                break;
            case HOHO:
                mResourceId = R.layout.tc_layout_bottom_menu_dialog_hoho;
                break;
        }

        // 窗口显示位置
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        // 窗口显示动画
        getWindow().setWindowAnimations(R.style.tc_bottom_menu_animation);
    }

    public TCBottomMenuDialog(Context context, LayoutType layoutType) {
        this(context, layoutType, R.style.tc_dialog_theme_default);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mResourceId);
        ButterKnife.inject(this);

        // 窗口宽高
        if (mViewRoot != null) {
            switch (mLayoutType) {
                default:
                case DEFAULT:
                    ViewUtil.setWidthAndHeight(mViewRoot, Configs.get().getScreenWidth(WIDTH_RATIO_BIG), WindowManager.LayoutParams.WRAP_CONTENT);
                    break;
                case HOHO:
                    ViewUtil.setWidthAndHeight(mViewRoot, Configs.get().getScreenWidth(WIDTH_RATIO_FULL), WindowManager.LayoutParams.WRAP_CONTENT);
                    break;
            }
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

        // 设置最大高度
        if (mViewRoot != null) {
            TCRect rootRect = ViewUtil.getWidthAndHeight(mViewRoot);
            int maxHeight = (int) (DeviceUtil.getScreenHeight(mContext) * WIDTH_RATIO_HALF);
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
            clearCancelContainer();
            // 添加
            for (TCMenu tcMenu : mTCMenus) {
                // option
                if (tcMenu.getType() == TCMenu.TYPE_NORMAL) {
                    addOptionTextView(tcMenu.getText(), tcMenu.getOnClickListener());
                }
                // cancel
                if (tcMenu.getType() == TCMenu.TYPE_CANCEL) {
                    addCancelTextView(tcMenu.getText(), tcMenu.getOnClickListener());
                }
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
        View view;
        switch (mLayoutType) {
            default:
            case DEFAULT:
                view = LayoutInflater.from(mContext).inflate(R.layout.tc_layout_menu_item_center, parentLayout, false);
                break;
            case HOHO:
                view = LayoutInflater.from(mContext).inflate(R.layout.tc_layout_bottom_menu_dialog_hoho_item, parentLayout, false);
                break;
        }
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

    /**
     * Cancel 容器操作
     */
    protected void addCancelTextView(String text, View.OnClickListener onClickListener) {
        if (mLayoutCancelContainer != null) {
            addTextView(mLayoutCancelContainer, text, onClickListener);
        }
    }

    protected void clearCancelContainer() {
        if (mLayoutCancelContainer != null) {
            clearLayout(mLayoutCancelContainer);
        }
    }
}
