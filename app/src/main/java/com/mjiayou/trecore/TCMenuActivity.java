package com.mjiayou.trecore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.mjiayou.trecore.bean.entity.TCMenu;
import com.mjiayou.trecore.util.MenuUtil;
import com.mjiayou.trecoredemo.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TCMenuActivity extends TCActivity {

    @InjectView(R.id.layout_menu_container)
    LinearLayout mLayoutMenuContainer;

    private static String mTitle;
    private static List<TCMenu> mTCMenus;

    /**
     * startActivity
     */
    public static void open(Context context, String title, List<TCMenu> tcMenus) {
        mTitle = title;
        mTCMenus = tcMenus;
        Intent intent = new Intent(context, TCMenuActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tc_activity_menu);
        ButterKnife.inject(this);

        if (!TextUtils.isEmpty(mTitle)) {
            setTitle(mTitle);
        }

        initView();
    }

    @Override
    public void initView() {
        super.initView();

        MenuUtil.setMenus(mContext, mLayoutMenuContainer, mTCMenus);
    }
}
