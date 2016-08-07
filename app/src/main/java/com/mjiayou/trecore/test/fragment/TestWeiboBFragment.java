package com.mjiayou.trecore.test.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mjiayou.trecore.ui.TCFragment;
import com.mjiayou.trecoredemo.R;

import butterknife.ButterKnife;

/**
 * Created by treason on 16/5/16.
 */
public class TestWeiboBFragment extends TCFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.tc_fragment_test_weibo_b, null);
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

        //步骤一：添加一个FragmentTransaction的实例
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //步骤二：用add()方法加上Fragment的对象rightFragment
        fragmentTransaction.replace(R.id.layout_container, new TestWeiboAFragment());
        fragmentTransaction.addToBackStack(null);
        //步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
        fragmentTransaction.commit();
    }
}
