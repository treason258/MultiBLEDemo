package com.mjiayou.trecore.ui.test;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mjiayou.trecore.bean.TCResponseBody;
import com.mjiayou.trecore.bean.entity.TCUMLogin;
import com.mjiayou.trecore.event.UserLoginStatusEvent;
import com.mjiayou.trecore.ui.TCActivity;
import com.mjiayou.trecore.util.SharedUtil;
import com.mjiayou.trecore.util.ToastUtil;
import com.mjiayou.trecore.util.UserUtil;
import com.mjiayou.trecore.widget.TCRouter;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.mjiayou.trecoredemo.R;
import de.greenrobot.event.EventBus;

public class TestUserLoginActivity extends TCActivity {

    @InjectView(R.id.et_phone)
    EditText mEtPhone;
    @InjectView(R.id.et_password)
    EditText mEtPassword;
    @InjectView(R.id.tv_forget_pwd)
    TextView mTvForgetPwd;
    @InjectView(R.id.tv_register)
    TextView mTvRegister;
    @InjectView(R.id.tv_login)
    TextView mTvLogin;
    @InjectView(R.id.iv_login_weibo)
    ImageView mIvLoginWeibo;
    @InjectView(R.id.iv_login_wechat)
    ImageView mIvLoginWechat;
    @InjectView(R.id.iv_login_qq)
    ImageView mIvLoginQQ;

    @OnClick({R.id.tv_forget_pwd, R.id.tv_register, R.id.tv_login})
    void onClicks(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_pwd: {
                ToastUtil.show(mContext, "忘记密码");
                break;
            }
            case R.id.tv_register: {
                TCRouter.openTestUserRegisterActivity(mContext);
                break;
            }
            case R.id.tv_login: {
                submitData();
                break;
            }
        }
    }

    @OnClick({R.id.iv_login_weibo, R.id.iv_login_wechat, R.id.iv_login_qq})
    void onClicksThirdLogin(View view) {
        switch (view.getId()) {
            case R.id.iv_login_weibo:
                mTCUMLogin.setPlatform(SHARE_MEDIA.SINA);
                break;
            case R.id.iv_login_wechat:
                mTCUMLogin.setPlatform(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.iv_login_qq:
                mTCUMLogin.setPlatform(SHARE_MEDIA.QQ);
                break;
        }
        loginByThird();
    }


    TCUMLogin mTCUMLogin = new TCUMLogin();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tc_activity_test_user_login);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);

        setTitle("登录");

        initView();
    }

    /**
     * EventBus处理登陆后更新用户信息
     */
    public void onEvent(UserLoginStatusEvent event) {
        // 针对注册成功直接跳转finish该页面的情况
        if (event.isLogin() && mActivity != null && !mActivity.isFinishing()) {
            try {
                mActivity.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initView() {
        super.initView();
        String phone = SharedUtil.get(mContext).getAccountUsername();
        if (!TextUtils.isEmpty(phone)) {
            mEtPhone.setText(phone);
        }
    }

    @Override
    public void submitData() {
        super.submitData();
        String phone = mEtPhone.getText().toString();
        String password = mEtPassword.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            ToastUtil.show(mContext, "请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.show(mContext, "请输入密码");
            return;
        }

        SharedUtil.get(mContext).setAccountUsername(phone);

        // 调用登录接口
        ToastUtil.show(mContext, "调用登录接口");
    }

    @Override
    public void callback(Message msg) {
        super.callback(msg);
    }

    public void refreshView(TCResponseBody response) {
        super.refreshView(response);
        ToastUtil.show(mContext, "登录成功");

        String tokenId = "123456";
        UserUtil.doLogin(tokenId);

        finish();
    }


    /**
     * 第三方登陆
     */
    private void loginByThird() {
        ToastUtil.show(mContext, "调用第三方登录");
    }
}
