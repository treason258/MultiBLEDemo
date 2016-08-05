package com.mjiayou.trecore.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mjiayou.trecore.bean.entity.TCUser;
import com.mjiayou.trecore.util.DeviceUtil;
import com.mjiayou.trecore.util.LogUtil;
import com.mjiayou.trecore.util.ToastUtil;
import com.mjiayou.trecore.util.ViewUtil;
import com.mjiayou.trecore.widget.CircleImageView;
import com.mjiayou.trecoredemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by treason on 16/6/2.
 */
public class UserCenterDialog extends TCDialog {

    protected static final String TAG = "UserCenterDialog";
    private static UserCenterDialog mUserCenterDialog;

    // root
    @Optional
    @InjectView(R.id.view_root)
    ViewGroup mViewRoot;
    // dialog
    @InjectView(R.id.layout_dialog)
    LinearLayout mLayoutDialog;
    // header
    @InjectView(R.id.layout_header)
    RelativeLayout mLayoutHeader;
    @InjectView(R.id.tv_manager)
    TextView mTvManager;
    @InjectView(R.id.tv_close)
    TextView mTvClose;
    @InjectView(R.id.iv_avatar)
    CircleImageView mIvAvatar;
    @InjectView(R.id.iv_avatar_related)
    CircleImageView mIvAcatarRelated;
    @InjectView(R.id.tv_nickname)
    TextView mTvNickname;
    @InjectView(R.id.iv_sex)
    ImageView mIvSex;
    @InjectView(R.id.tv_grade)
    TextView mTvGrade;
    @InjectView(R.id.tv_account)
    TextView mTvAccount;
    @InjectView(R.id.tv_signature)
    TextView mTvSignature;
    // content
    @InjectView(R.id.layout_attention)
    LinearLayout mLayoutAttention;
    @InjectView(R.id.tv_attention_number)
    TextView mTvAttentionNumber;
    @InjectView(R.id.layout_fans)
    LinearLayout mLayoutFans;
    @InjectView(R.id.tv_fans_number)
    TextView mTvFansNumber;
    @InjectView(R.id.layout_money)
    LinearLayout mLayoutMoney;
    @InjectView(R.id.tv_money_number)
    TextView mTvMoneyNumber;
    @InjectView(R.id.tv_money_out)
    TextView mIvMoneyOut;
    // menu
    @InjectView(R.id.layout_menu)
    LinearLayout mLayoutMenu;
    @InjectView(R.id.tv_attention)
    TextView mTvAttention;
    @InjectView(R.id.tv_message)
    TextView mTvMessage;
    @InjectView(R.id.tv_reply)
    TextView mTvReply;
    // user
    @InjectView(R.id.layout_user)
    LinearLayout mLayoutUser;
    @InjectView(R.id.lv_user)
    ListView mLvUser;

    @OnClick({R.id.tv_manager, R.id.tv_close,
            R.id.layout_attention, R.id.layout_fans, R.id.layout_money,
            R.id.tv_attention, R.id.tv_message, R.id.tv_reply})
    void onClicks(View view) {
        switch (view.getId()) {
            case R.id.tv_manager: {
                break;
            }
            case R.id.tv_close: {
                break;
            }
            case R.id.layout_attention: { // 关注列表
                break;
            }
            case R.id.layout_fans: { // 粉丝列表
                break;
            }
            case R.id.layout_money: { // 金币列表
                break;
            }

            case R.id.tv_attention: { // 关注操作
                break;
            }
            case R.id.tv_message: {
                break;
            }
            case R.id.tv_reply: {
                ToastUtil.show(mContext, "回复操作");
                break;
            }
        }
    }

    private static String mUUID = "";
    private static String mRoomId = "";
    private static String mAnchorUUID = "";
    private TCUser mUser = null;

    private boolean mAttentioned = false; // 是否关注当前用户，默认没有关注

    private boolean mShowUserList = false;

    public UserCenterDialog(Context context, int themeResId) {
        super(context, themeResId);
        mResourceId = R.layout.layout_user_info_dialog;

        // 可返回按钮取消
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    public UserCenterDialog(Context context) {
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
            if (!TextUtils.isEmpty(mUUID)) {
                mTvAccount.setText("账号：" + mUUID);
            }

            // 显示状态
            mLayoutMenu.setVisibility(View.VISIBLE);
            mLayoutUser.setVisibility(View.GONE);
            mTvClose.setText("关闭");
            mShowUserList = false;

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

    /**
     * 设置房间ID
     */
    public static void setRoomId(String roomId) {
        mRoomId = roomId;
    }

    /**
     * 设置房间ID
     */
    public static void setAnchorUUID(String anchorUUID) {
        mAnchorUUID = anchorUUID;
    }

    // ******************************** UserCenterDialog ********************************

    /**
     * 创建 UserCenterDialog
     */
    public static UserCenterDialog createDialog(Context context, String uuid, String roomId, String anchorUUID) {
        setUUID(uuid);
        setRoomId(roomId);
        setAnchorUUID(anchorUUID);

        boolean needCreate = false;

        // 当 mUserCenterDialog 为 null 时，需要重新创建
        if (mUserCenterDialog == null) {
            needCreate = true;
        }

        // 当 mUserCenterDialog 不为 null，但是 getContext 不是 context 时
        if (mUserCenterDialog != null && context != mUserCenterDialog.getContext()) {
            LogUtil.w(TAG, "此处引发窗体泄露 | orign context -> " + mUserCenterDialog.getContext() + " | now context -> " + context);
            dismissDialog();
            needCreate = true;
        }

        // 根据需要重新创建
        if (needCreate) {
            mUserCenterDialog = new UserCenterDialog(context);
        }

        return mUserCenterDialog;
    }

    /**
     * 释放 UserCenterDialog
     */
    public static void dismissDialog() {
        if (mUserCenterDialog == null) {
            return;
        }

        if (mUserCenterDialog.isShowing()) {
            try {
                mUserCenterDialog.dismiss();
                mUserCenterDialog = null;
            } catch (Exception e) {
                LogUtil.printStackTrace(e);
            }
        }
    }
}
