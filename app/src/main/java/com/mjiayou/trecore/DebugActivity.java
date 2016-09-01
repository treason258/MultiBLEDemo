package com.mjiayou.trecore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mjiayou.trecore.bean.entity.TCMenu;
import com.mjiayou.trecore.ui.TestActivity;
import com.mjiayou.trecore.util.AppUtil;
import com.mjiayou.trecore.util.LogUtil;
import com.mjiayou.trecore.util.MenuUtil;
import com.mjiayou.trecore.util.SharedUtil;
import com.mjiayou.trecore.util.ToastUtil;
import com.mjiayou.trecore.util.UserUtil;
import com.mjiayou.trecore.widget.TCRouter;
import com.mjiayou.trecore.widget.dialog.DialogHelper;
import com.mjiayou.trecore.widget.dialog.TCAlertDialog;
import com.mjiayou.trecoredemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DebugActivity extends TCActivity {

    @InjectView(R.id.layout_menu_container)
    LinearLayout mLayoutMenuContainer;
    @InjectView(R.id.tv_info)
    TextView mTvInfo;

    /**
     * startActivity
     */
    public static void open(Context context) {
        Intent intent = new Intent(context, DebugActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tc_activity_debug);
        ButterKnife.inject(this);

        setTitle("DebugActivity");

        initView();
    }

    @Override
    public void initView() {
        super.initView();

        MenuUtil.setMenus(mContext, mLayoutMenuContainer, getMenus());
        mTvInfo.setText(AppUtil.getAppInfoDetail(mContext));
    }

    /**
     * getMenus
     */
    public List<TCMenu> getMenus() {
        List<TCMenu> tcMenus = new ArrayList<>();
        tcMenus.add(new TCMenu("TEST", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestActivity.open(mContext);
            }
        }));
        tcMenus.add(new TCMenu("LOG AND TOAST", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.show(mContext, "LOG AND TOAST TEST");
                LogUtil.i("LOG AND TOAST TEST");
            }
        }));
        tcMenus.add(new TCMenu("设置第一次启动", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper.createTCAlertDialog(mContext,
                        "提示", "确认设置第一次启动？",
                        "确定", "取消", true,
                        new TCAlertDialog.OnTCActionListener() {
                            @Override
                            public void onOkAction() {
                                SharedUtil.get(mContext).setConfigIsFirst(true);
                                ToastUtil.show(mContext, "设置第一次启动成功");
                            }

                            @Override
                            public void onCancelAction() {

                            }
                        }).show();
            }
        }));
        tcMenus.add(new TCMenu("模拟登录", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper.createTCAlertDialog(mContext,
                        "提示", "确认模拟登陆？",
                        "确定", "取消", true,
                        new TCAlertDialog.OnTCActionListener() {
                            @Override
                            public void onOkAction() {
                                UserUtil.doLogin("12345678901234567890123456789012");
                                ToastUtil.show(mContext, "模拟登录成功");
                            }

                            @Override
                            public void onCancelAction() {

                            }
                        }).show();
            }
        }));
        tcMenus.add(new TCMenu("退出登录", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper.createTCAlertDialog(mContext,
                        "提示", "确认退出登录？",
                        "确定", "取消", true,
                        new TCAlertDialog.OnTCActionListener() {
                            @Override
                            public void onOkAction() {
                                UserUtil.doLogout();
                                ToastUtil.show(mContext, "退出登录成功");

                                // 打开登录页面
                                TCRouter.openTestUserLoginActivity(mContext);
                                finish();
                            }

                            @Override
                            public void onCancelAction() {

                            }
                        }).show();
            }
        }));
        return tcMenus;
    }
}
