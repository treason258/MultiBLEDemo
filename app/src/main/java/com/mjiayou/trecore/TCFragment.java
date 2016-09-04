package com.mjiayou.trecore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mjiayou.trecore.bean.TCResponse;
import com.mjiayou.trecore.net.RequestAdapter;
import com.mjiayou.trecore.util.AssetUtil;
import com.mjiayou.trecore.util.ClickUtil;
import com.mjiayou.trecore.util.LogUtil;
import com.mjiayou.trecore.util.ThemeUtil;
import com.mjiayou.trecore.widget.Configs;
import com.mjiayou.trecoredemo.R;
import com.umeng.analytics.MobclickAgent;


/**
 * Created by treason on 16/5/14.
 */
public class TCFragment extends Fragment implements RequestAdapter.DataRequest, RequestAdapter.DataResponse {

    protected final String TAG = this.getClass().getSimpleName();

    // 显示生命周期
    protected final String TAG_LIFE_CYCLE = "fragment_life_cycle";
    protected boolean SHOW_LIFE_CYCLE = true;

    // toolbar
    protected LinearLayout mLayoutBar;
    // content
    protected RelativeLayout mLayoutBarContent;
    // container
    protected LinearLayout mLayoutLeftContainer;
    protected LinearLayout mLayoutRightContainer;
    protected LinearLayout mLayoutTitleContainer;
    // title
    protected TextView mTvTitle;
    // line
    protected LinearLayout mLayoutBarLine;
    protected ImageView mIvBarLine;

    // var
    protected Activity mActivity;
    protected Context mContext;
    protected Intent mIntent;
    private RequestAdapter mRequestAdapter;

    @Override
    public void onAttach(Activity activity) {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onAttach");
        }
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onCreate");
        }
        super.onCreate(savedInstanceState);

        // var
        mActivity = getActivity();
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onCreateView");
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onViewCreated");
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onStart");
        }
        super.onStart();
    }

    @Override
    public void onResume() {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onResume");
        }
        super.onResume();
        if (Configs.SWITCH_UMENG_ANALYTICS_ON) {
            MobclickAgent.onPageStart("MainScreen");
        }
    }

    @Override
    public void onPause() {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onPause");
        }
        super.onPause();
        if (Configs.SWITCH_UMENG_ANALYTICS_ON) {
            MobclickAgent.onPageEnd("MainScreen");
        }
    }

    @Override
    public void onStop() {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onStop");
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onDestroy");
        }
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onDestroyView");
        }
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onDetach");
        }
        super.onDetach();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (SHOW_LIFE_CYCLE) {
            if (isVisibleToUser) {
                LogUtil.i(TAG, TAG_LIFE_CYCLE + " | setUserVisibleHint -> " + isVisibleToUser);
            } else {
                LogUtil.i(TAG, TAG_LIFE_CYCLE + " | setUserVisibleHint -> " + isVisibleToUser);
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void startActivity(Intent intent) {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | startActivity");
        }
        super.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | startActivityForResult");
        }
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void initView() {

    }

    @Override
    public void getData(int pageNumber) {

    }

    @Override
    public void refreshData() {

    }

    @Override
    public void loadMoreData() {

    }

    @Override
    public void submitData() {

    }

    @Override
    public void callback(Message msg) {

    }

    @Override
    public void refreshView(TCResponse response) {

    }

    /**
     * 返回DataAdapter对象
     */
    public RequestAdapter getRequestAdapter() {
        if (mRequestAdapter == null) {
            mRequestAdapter = new RequestAdapter(mContext, this);
        }
        return mRequestAdapter;
    }

    /**
     * 显示、隐藏正在加载对话框
     */
    public void showLoading(boolean show) {
        if (mActivity != null && mActivity instanceof TCActivity) {
            ((TCActivity) mActivity).showLoading(show);
        }
    }

    // **************************************************************** 适配ToolBar ****************************************************************

    // ******************************** findView ********************************

    protected void findViewForToolbar(View view) {
        mLayoutBar = (LinearLayout) view.findViewById(R.id.layout_bar);
        mLayoutBarContent = (RelativeLayout) view.findViewById(R.id.layout_bar_content);
        mLayoutLeftContainer = (LinearLayout) view.findViewById(R.id.layout_left_container);
        mLayoutRightContainer = (LinearLayout) view.findViewById(R.id.layout_right_container);
        mLayoutTitleContainer = (LinearLayout) view.findViewById(R.id.layout_title_container);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mLayoutBarLine = (LinearLayout) view.findViewById(R.id.layout_bar_line);
        mIvBarLine = (ImageView) view.findViewById(R.id.iv_bar_line);
    }

    // ******************************** title ********************************

    /**
     * 设置标题
     */
    protected void setTitle(String title, Typeface typeface) {
        if (mTvTitle != null) {
            if (!TextUtils.isEmpty(title)) {
                if (typeface == null) {
                    typeface = AssetUtil.getTypeface(mContext);
                }
                if (typeface != null) {
                    mTvTitle.setTypeface(typeface);
                }
                mTvTitle.setText(title);
            } else {
                mTvTitle.setText("");
            }
        }

        // 默认显示左边返回按钮
        clearLeftContainer();
        addLeftImageView(ThemeUtil.getBackIcon(), TCActivity.MENU_BACK);
    }

    public void setTitle(CharSequence title) {
        setTitle(String.valueOf(title), null);
    }

    public void setTitle(int titleId) {
        setTitle(TCApp.get().getResources().getString(titleId), null);
    }

    // ******************************** 封装 ********************************

    /**
     * 显示、隐藏标题
     */
    protected void showTitleContainer(boolean isShow) {
        mLayoutTitleContainer.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    /**
     * View添加到Layout中
     */
    protected void addViewToLayout(LinearLayout parentLayout, View childView, final int menuId) {
        parentLayout.setVisibility(View.VISIBLE);
        childView.setVisibility(View.VISIBLE);
        parentLayout.addView(childView);

        childView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuSelected(menuId);
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
     * 添加文本标题
     */
    protected void addTitleTextView(LinearLayout parentLayout, String text, int menuId) {
        TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tc_layout_toolbar_title_text, parentLayout, false);
        textView.setText(text);
        textView.setTypeface(AssetUtil.getTypeface(mContext));
        addViewToLayout(parentLayout, textView, menuId);
    }

    /**
     * 添加图片标题
     */
    protected void addTitleImageView(LinearLayout parentLayout, int resId, int menuId) {
        ImageView imageView = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.tc_layout_toolbar_title_image, parentLayout, false);
        imageView.setImageResource(resId);
        addViewToLayout(parentLayout, imageView, menuId);
    }

    /**
     * 添加文本MENU
     */
    protected void addTextView(LinearLayout parentLayout, String text, int menuId) {
        TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tc_layout_toolbar_menu_text, parentLayout, false);
        textView.setText(text);
        textView.setTypeface(AssetUtil.getTypeface(mContext));
        addViewToLayout(parentLayout, textView, menuId);
    }

    /**
     * 添加图片MENU
     */
    protected void addImageView(LinearLayout parentLayout, int resId, int menuId) {
        ImageView imageView = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.tc_layout_toolbar_menu_image, parentLayout, false);
        imageView.setImageResource(resId);
        addViewToLayout(parentLayout, imageView, menuId);
    }

    /**
     * 监听执行
     */
    protected void onMenuSelected(int menuId) {
        switch (menuId) {
            case TCActivity.MENU_BACK:
                if (ClickUtil.isFastClick()) {
                    return;
                }
                mActivity.finish();
                break;
            case TCActivity.MENU_DEBUG:
//                TCRouter.openDebugActivity(mContext);
                DebugActivity.open(mContext);
                break;
        }
    }

    // ******************************** 快捷方式 ********************************

    /**
     * 中间容器操作
     */
    protected void addTitleView(View view, int menuId) {
        addViewToLayout(mLayoutTitleContainer, view, menuId);

        // 默认显示左边返回按钮
        clearLeftContainer();
        addLeftImageView(ThemeUtil.getBackIcon(), TCActivity.MENU_BACK);
    }

    protected void addTitleTextView(String text) {
        addTitleTextView(mLayoutTitleContainer, text, TCActivity.MENU_NULL);

        // 默认显示左边返回按钮
        clearLeftContainer();
        addLeftImageView(ThemeUtil.getBackIcon(), TCActivity.MENU_BACK);
    }


    protected void addTitleImageView(int resId, int menuId) {
        addTitleImageView(mLayoutTitleContainer, resId, menuId);

        // 默认显示左边返回按钮
        clearLeftContainer();
        addLeftImageView(ThemeUtil.getBackIcon(), TCActivity.MENU_BACK);
    }

    protected void addTitleImageView(int resId) {
        addTitleImageView(resId, TCActivity.MENU_NULL);
    }

    protected void clearTitleContainer() {
        clearLayout(mLayoutTitleContainer);
    }

    /**
     * 左容器操作
     */
    protected void addLeftView(View view, int menuId) {
        addViewToLayout(mLayoutLeftContainer, view, menuId);
    }

    protected void addLeftTextView(String text, int menuId) {
        addTextView(mLayoutLeftContainer, text, menuId);
    }

    protected void addLeftImageView(int resId, int menuId) {
        addImageView(mLayoutLeftContainer, resId, menuId);
    }

    protected void clearLeftContainer() {
        clearLayout(mLayoutLeftContainer);
    }

    /**
     * 右容器操作
     */
    protected void addRightView(View view, int menuId) {
        addViewToLayout(mLayoutRightContainer, view, menuId);
    }

    protected void addRightTextView(String text, int menuId) {
        addTextView(mLayoutRightContainer, text, menuId);
    }

    protected void addRightImageView(int resId, int menuId) {
        addImageView(mLayoutRightContainer, resId, menuId);
    }

    protected void clearRightContainer() {
        clearLayout(mLayoutRightContainer);
    }
}
