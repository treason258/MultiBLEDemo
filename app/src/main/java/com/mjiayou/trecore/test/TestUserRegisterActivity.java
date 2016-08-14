package com.mjiayou.trecore.test;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mjiayou.trecore.bean.TCResponseBody;
import com.mjiayou.trecore.TCActivity;
import com.mjiayou.trecore.util.SharedUtil;
import com.mjiayou.trecore.util.ToastUtil;
import com.mjiayou.trecore.util.UserUtil;
import com.mjiayou.trecore.widget.TCConfigs;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.mjiayou.trecoredemo.R;

public class TestUserRegisterActivity extends TCActivity {

    @InjectView(R.id.et_phone)
    EditText mEtPhone;
    @InjectView(R.id.et_verify_code)
    EditText mEtVerifyCode;
    @InjectView(R.id.tv_get_verify_code)
    TextView mTvGetVerifyCode;
    @InjectView(R.id.et_password)
    EditText mEtPassword;
    @InjectView(R.id.et_invite_code)
    EditText mEtInviteCode;
    @InjectView(R.id.btn_register)
    Button mBtnRegister;

    @OnClick({R.id.tv_get_verify_code, R.id.btn_register})
    void onClicks(View view) {
        switch (view.getId()) {
            case R.id.tv_get_verify_code: {
                getVerifyCode();
                break;
            }
            case R.id.btn_register: {
                submitData();
                break;
            }
        }
    }

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tc_activity_test_user_register);
        ButterKnife.inject(this);

        setTitle("注册");

        initView();
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void submitData() {
        super.submitData();
        String phone = mEtPhone.getText().toString();
        String verifyCode = mEtVerifyCode.getText().toString();
        String password = mEtPassword.getText().toString();
        String inviteCode = mEtInviteCode.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            ToastUtil.show(mContext, "请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(verifyCode)) {
            ToastUtil.show(mContext, "请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.show(mContext, "请输入密码");
            return;
        }

        SharedUtil.get(mContext).setAccountUsername(phone);

        // 调用注册接口
        ToastUtil.show(mContext, "调用注册接口");
    }

    @Override
    public void callback(Message msg) {
        super.callback(msg);
    }

    public void refreshView(TCResponseBody responseBody) {
        super.refreshView(responseBody);
        ToastUtil.show(mContext, "注册成功");

        String tokenId = "123456";
        UserUtil.doLogin(tokenId);

        finish();
    }

    /**
     * 获取验证码
     */
    private void getVerifyCode() {
        String phone = mEtPhone.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            ToastUtil.show(mContext, "请输入手机号");
            return;
        }

        // 设置60s后重新获取
        countDownTimer = new CountDownTimer(TCConfigs.TIME_GET_VERIFY_CODE, TCConfigs.TIME_GET_VERIFY_CODE_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvGetVerifyCode.setClickable(false);
                mTvGetVerifyCode.setEnabled(false);
                mTvGetVerifyCode.setText(millisUntilFinished / TCConfigs.TIME_GET_VERIFY_CODE_INTERVAL + "秒后获取");
            }

            @Override
            public void onFinish() {
                mTvGetVerifyCode.setClickable(true);
                mTvGetVerifyCode.setEnabled(true);
                mTvGetVerifyCode.setText("获取验证码");
            }
        };
        countDownTimer.start();

        // 调用发送验证码接口
        ToastUtil.show(mContext, "调用发送验证码接口");
    }
}
