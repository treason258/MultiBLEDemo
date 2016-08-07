package com.mjiayou.trecore.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import com.mjiayou.trecore.TCApp;
import com.mjiayou.trecore.bean.TCResponseBody;
import com.mjiayou.trecore.bean.TCSinaStatusesResponseBody;
import com.mjiayou.trecore.bean.entity.TCMenu;
import com.mjiayou.trecore.bean.entity.TCUMShare;
import com.mjiayou.trecore.helper.GsonHelper;
import com.mjiayou.trecore.helper.UmengHelper;
import com.mjiayou.trecore.net.RequestAdapter;
import com.mjiayou.trecore.test.customview.TestCustomViewActivity;
import com.mjiayou.trecore.test.jni.TestJNIActivity;
import com.mjiayou.trecore.ui.TCActivity;
import com.mjiayou.trecore.ui.TCMenuActivity;
import com.mjiayou.trecore.util.MenuUtil;
import com.mjiayou.trecore.util.ToastUtil;
import com.mjiayou.trecore.widget.TCConfigs;
import com.mjiayou.trecore.widget.TCRouter;
import com.mjiayou.trecore.widget.dialog.DialogHelper;
import com.mjiayou.trecoredemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TestActivity extends TCActivity {

    @InjectView(R.id.layout_menu_container)
    LinearLayout mLayoutMenuContainer;

    /**
     * startActivity
     */
    public static void open(Context context) {
        Intent intent = new Intent(context, TestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tc_activity_menu);
        ButterKnife.inject(this);

        setTitle("TestActivity");

        initView();
    }

    @Override
    public void initView() {
        super.initView();

        MenuUtil.setMenus(mContext, mLayoutMenuContainer, getMenus());
    }

    /**
     * getMenus
     */
    public List<TCMenu> getMenus() {
        List<TCMenu> tcMenus = new ArrayList<>();
        tcMenus.add(new TCMenu("TEMP", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.show(mContext, "temp");
            }
        }));
        tcMenus.add(new TCMenu("Test API", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestAdapter requestAdapter = new RequestAdapter(mContext, mContext, new RequestAdapter.DataResponse() {
                    @Override
                    public void callback(Message msg) {
                        switch (msg.what) {
                            case RequestAdapter.SINA_STATUSES: {
                                TCSinaStatusesResponseBody response = (TCSinaStatusesResponseBody) msg.obj;
                                if (response != null) {
                                    ToastUtil.show(mContext, GsonHelper.get().toJson(response));
                                } else {
                                    ToastUtil.show(mContext, TCApp.get().getResources().getString(R.string.tc_error_response_null));
                                }
                                break;
                            }
                        }
                    }

                    @Override
                    public void refreshView(TCResponseBody responseBody) {

                    }
                });
                requestAdapter.sinaStatuses();
            }
        }));
        tcMenus.add(new TCMenu("Test Weibo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TCRouter.openTestWeiboActivity(mContext);
            }
        }));
        tcMenus.add(new TCMenu("Test Main", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TCRouter.openTestSplashActivity(mContext);
            }
        }));
        tcMenus.add(new TCMenu("视频测试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TCRouter.openTestVideoActivity(mContext);
            }
        }));
        tcMenus.add(new TCMenu("网页视频测试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        }));
        tcMenus.add(new TCMenu("Dialog测试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper.showDialogDemo(mContext);
            }
        }));
        tcMenus.add(new TCMenu("友盟分享测试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TCUMShare tcumShare = new TCUMShare();
                tcumShare.setTitle(TCConfigs.DEFAULT_SHARE_TARGET_TITLE);
                tcumShare.setDesc(TCConfigs.DEFAULT_SHARE_TARGET_CONTENT);
                tcumShare.setImgUrl(TCConfigs.DEFAULT_SHARE_IMAGE_URL);
                tcumShare.setTargetUrl(TCConfigs.DEFAULT_SHARE_TARGET_URL);
                UmengHelper.openShare(mActivity, null, UmengHelper.SHARE_FOR_NORMAL, tcumShare);
            }
        }));
        tcMenus.add(new TCMenu("TCMenuActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TCMenuActivity.open(mContext, "TCMenu", MenuUtil.getTCMenus(mContext));
            }
        }));
        tcMenus.add(new TCMenu("TestHacksActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestHacksActivity.open(mContext);
            }
        }));
        tcMenus.add(new TCMenu("TestJNIActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestJNIActivity.open(mContext);
            }
        }));
        tcMenus.add(new TCMenu("TestCustomViewActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestCustomViewActivity.open(mContext);
            }
        }));
        return tcMenus;
    }
}
