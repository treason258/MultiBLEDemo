package com.mjiayou.trecore.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import com.mjiayou.trecore.TCActivity;
import com.mjiayou.trecore.TCApp;
import com.mjiayou.trecore.TCMenuActivity;
import com.mjiayou.trecore.bean.TCResponse;
import com.mjiayou.trecore.bean.TCSinaStatusesResponse;
import com.mjiayou.trecore.bean.entity.TCMenu;
import com.mjiayou.trecore.bean.entity.TCUMShare;
import com.mjiayou.trecore.helper.GsonHelper;
import com.mjiayou.trecore.helper.UmengHelper;
import com.mjiayou.trecore.net.RequestAdapter;
import com.mjiayou.trecore.test.TestCameraActivity;
import com.mjiayou.trecore.test.TestHacksActivity;
import com.mjiayou.trecore.test.aidl.TestAIDLActivity;
import com.mjiayou.trecore.test.canvas.TestCanvasActivity;
import com.mjiayou.trecore.test.contentprovider.TestProviderActivity;
import com.mjiayou.trecore.test.customview.TestCustomViewActivity;
import com.mjiayou.trecore.test.jni.TestJNIActivity;
import com.mjiayou.trecore.test.messenger.TestMessengerActivity;
import com.mjiayou.trecore.test.process.TestProcessBActivity;
import com.mjiayou.trecore.test.surfaceview.TestSurfaceViewActivity;
import com.mjiayou.trecore.test.touchevent.TestTouchActivity;
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

        // test
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

        tcMenus.add(new TCMenu("TestProviderActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestProviderActivity.open(mContext);
            }
        }));
        tcMenus.add(new TCMenu("TestAIDLActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestAIDLActivity.open(mContext);
            }
        }));
        tcMenus.add(new TCMenu("TestMessengerActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestMessengerActivity.open(mContext);
            }
        }));
        tcMenus.add(new TCMenu("TestProcessBActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestProcessBActivity.open(mContext);
            }
        }));
        tcMenus.add(new TCMenu("TestTouchActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestTouchActivity.open(mContext);
            }
        }));
        tcMenus.add(new TCMenu("TestCameraActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestCameraActivity.open(mContext);
            }
        }));
        tcMenus.add(new TCMenu("TestSurfaceViewActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestSurfaceViewActivity.open(mContext);
            }
        }));
        tcMenus.add(new TCMenu("TestCanvasActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestCanvasActivity.open(mContext);
            }
        }));
        tcMenus.add(new TCMenu("TestCustomViewActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestCustomViewActivity.open(mContext);
            }
        }));
        tcMenus.add(new TCMenu("TestJNIActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestJNIActivity.open(mContext);
            }
        }));
        tcMenus.add(new TCMenu("TestHacksActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestHacksActivity.open(mContext);
            }
        }));
        tcMenus.add(new TCMenu("TCMenuActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TCMenuActivity.open(mContext, "TCMenu", MenuUtil.getTCMenus(mContext));
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
        tcMenus.add(new TCMenu("Dialog测试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper.showDialogDemo(mContext);
            }
        }));
        tcMenus.add(new TCMenu("网页视频测试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        }));
        tcMenus.add(new TCMenu("视频测试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TCRouter.openTestVideoActivity(mContext);
            }
        }));
        tcMenus.add(new TCMenu("Test Main", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TCRouter.openTestSplashActivity(mContext);
            }
        }));
        tcMenus.add(new TCMenu("Test Weibo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TCRouter.openTestWeiboActivity(mContext);
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
                                TCSinaStatusesResponse response = (TCSinaStatusesResponse) msg.obj;
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
                    public void refreshView(TCResponse response) {

                    }
                });
                requestAdapter.sinaStatuses();
            }
        }));
        return tcMenus;
    }
}
