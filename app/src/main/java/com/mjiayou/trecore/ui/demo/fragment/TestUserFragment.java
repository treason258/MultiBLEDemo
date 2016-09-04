package com.mjiayou.trecore.ui.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mjiayou.trecore.TCFragment;
import com.mjiayou.trecore.util.ToastUtil;
import com.mjiayou.trecore.widget.Router;
import com.mjiayou.trecoredemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by treason on 16/5/16.
 */
public class TestUserFragment extends TCFragment {

    @InjectView(R.id.tv_user_info)
    TextView mTvUserInfo;

    @OnClick({R.id.btn_login, R.id.btn_logout})
    void onClicks(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Router.openTestUserLoginActivity(mContext);
                break;
            case R.id.btn_logout:
                ToastUtil.show(mContext, "注销");
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.tc_fragment_test_user, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    @Override
    public void initView() {
        super.initView();
    }
}
