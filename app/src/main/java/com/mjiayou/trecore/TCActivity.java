package com.mjiayou.trecore;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mjiayou.trecore.bean.TCResponse;
import com.mjiayou.trecore.net.RequestAdapter;
import com.mjiayou.trecore.util.AssetUtil;
import com.mjiayou.trecore.util.ClickUtil;
import com.mjiayou.trecore.util.DeviceUtil;
import com.mjiayou.trecore.util.ExitUtil;
import com.mjiayou.trecore.util.LogUtil;
import com.mjiayou.trecore.util.SharedUtil;
import com.mjiayou.trecore.util.ThemeUtil;
import com.mjiayou.trecore.helper.Configs;
import com.mjiayou.trecore.helper.Params;
import com.mjiayou.trecore.widget.SwipeBackLayout;
import com.mjiayou.trecore.widget.ToolbarHelper;
import com.mjiayou.trecore.widget.dialog.DefaultProgressDialog;
import com.mjiayou.trecore.widget.dialog.TCDialog;
import com.mjiayou.trecore.widget.dialog.TCLoadingDialog;
import com.mjiayou.trecore.widget.dialog.TCProgressDialog;
import com.mjiayou.trecoredemo.R;
import com.umeng.analytics.MobclickAgent;


/**
 * Created by treason on 16/5/14.
 */
public class TCActivity extends AppCompatActivity implements RequestAdapter.DataRequest, RequestAdapter.DataResponse {

    protected final String TAG = this.getClass().getSimpleName();

    public static final int MENU_NULL = -1;
    public static final int MENU_DEBUG = 258;
    public static final int MENU_BACK = 259;

    public static final int MENU_REGISTER = 260;
    public static final int MENU_SAVE = 261;
    public static final int MENU_CANCEL = 262;

    //TRANSACTION 动作
    public static final int TRANSACTION_ADD = 0;
    public static final int TRANSACTION_REMOVE = 1;
    public static final int TRANSACTION_REPLACE = 2;

    // 显示生命周期
    protected final String TAG_LIFE_CYCLE = "activity_life_cycle";
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
    private Dialog mLoadingDialog;
    private RequestAdapter mRequestAdapter;

    // Toolbar
    protected Toolbar mToolbar;
    protected ToolbarHelper mToolbarHelper;

    // 侧滑返回
    protected SwipeBackLayout mSwipeBackLayout;

    /**
     * 开关显示ToolBar，默认显示
     */
    public boolean getShowToolBar() {
        return true;
    }

    /**
     * 开关侧滑返回功能，默认关闭
     */
    public boolean getEnableSwipeBack() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onCreate");
        }
        if (!getEnableSwipeBack()) {
            switch (SharedUtil.get(this).getConfigThemeId()) {
                case ThemeUtil.THEME_DEFAULT:
                    setTheme(R.style.tc_theme_default);
                    break;
                case ThemeUtil.THEME_OTHER:
                    setTheme(R.style.tc_theme_other);
                    break;
            }
        } else {
            switch (SharedUtil.get(this).getConfigThemeId()) {
                case ThemeUtil.THEME_DEFAULT:
                    setTheme(R.style.tc_theme_default_for_swipe_back);
                    break;
                case ThemeUtil.THEME_OTHER:
                    setTheme(R.style.tc_theme_other_for_swipe_back);
                    break;
            }
        }
        super.onCreate(savedInstanceState);
        if (getEnableSwipeBack()) {
            mSwipeBackLayout = (SwipeBackLayout) LayoutInflater.from(this).inflate(R.layout.tc_layout_swipe_back, null);
            mSwipeBackLayout.attachToActivity(this);
        }

        // var
        mActivity = this;
        mContext = this;

        // 管理Activity
        ExitUtil.addActivity(this);

        // 通过程序改变屏幕显示的方向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onRestart() {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onRestart");
        }
        super.onRestart();
    }

    @Override
    protected void onStart() {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onStart");
        }
        super.onStart();
    }

    @Override
    protected void onResume() {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onResume");
        }
        super.onResume();
        if (Configs.SWITCH_UMENG_ANALYTICS_ON) {
            MobclickAgent.onResume(this);
        }
    }

    @Override
    protected void onPause() {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onPause");
        }
        super.onPause();
        if (Configs.SWITCH_UMENG_ANALYTICS_ON) {
            MobclickAgent.onPause(this);
        }
    }

    @Override
    protected void onStop() {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onStop");
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onDestroy");
        }
        super.onDestroy();
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
    protected void onNewIntent(Intent intent) {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onNewIntent");
        }
        super.onNewIntent(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onSaveInstanceState");
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onRestoreInstanceState");
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onConfigurationChanged");
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTrimMemory(int level) {
        if (SHOW_LIFE_CYCLE) {
            // 因为onTrimMemory() 是在API 14 里添加的，你可以在老版本里使用onLowMemory() 回调，大致跟TRIM_MEMORY_COMPLETE事件相同。
            switch (level) {
                case TRIM_MEMORY_RUNNING_MODERATE:
                    LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onTrimMemory -> 你的应用正在运行，并且不会被杀死，但设备已经处于低内存状态，并且开始杀死LRU缓存里的内存。");
                    break;
                case TRIM_MEMORY_RUNNING_LOW:
                    LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onTrimMemory -> 你的应用正在运行，并且不会被杀死，但设备处于内存更低的状态，所以你应该释放无用资源以提高系统性能（直接影响app性能）");
                    break;
                case TRIM_MEMORY_RUNNING_CRITICAL:
                    LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onTrimMemory -> 你的应用还在运行，但系统已经杀死了LRU缓存里的大多数进程，所以你应该在此时释放所有非关键的资源。如果系统无法回收足够的内存，它会清理掉所有LRU缓存，并且开始杀死之前优先保持的进程，像那些运行着service的。同时，当你的app进程当前被缓存，你可能会从onTrimMemory() 收到下面的几种level。");
                    break;
                case TRIM_MEMORY_BACKGROUND:
                    LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onTrimMemory -> 系统运行在低内存状态，并且你的进程已经接近LRU列表的顶端（即将被清理）。虽然你的app进程还没有很高的被杀死风险，系统可能已经清理LRU里的进程，你应该释放那些容易被恢复的资源，如此可以让你的进程留在缓存里，并且当用户回到app时快速恢复。");
                    break;
                case TRIM_MEMORY_MODERATE:
                    LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onTrimMemory -> 系统运行在低内存状态，你的进程在LRU列表中间附近。如果系统变得内存紧张，可能会导致你的进程被杀死。");
                    break;
                case TRIM_MEMORY_COMPLETE:
                    LogUtil.i(TAG, TAG_LIFE_CYCLE + " | onTrimMemory -> 系统运行在低内存状态，如果系统没有恢复内存，你的进程是首先被杀死的进程之一。你应该释放所有不重要的资源来恢复你的app状态。");
                    break;
            }
        }
        super.onTrimMemory(level);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (SHOW_LIFE_CYCLE) {
            LogUtil.i(TAG, TAG_LIFE_CYCLE + " | setContentView");
        }
        mToolbarHelper = new ToolbarHelper(this, layoutResID);
        mToolbar = mToolbarHelper.getToolbar();
        if (getShowToolBar()) {
            super.setContentView(mToolbarHelper.getContentLayout());
        } else {
            super.setContentView(layoutResID);
        }
        // 把mToolbar设置到Activity中
        setSupportActionBar(mToolbar);
        mToolbar.setContentInsetsRelative(0, 0);
        mToolbar.showOverflowMenu();
        // 自定义的一些操作
        onCreateCustomToolBar(mToolbar);
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
     * 自定义的一些操作
     */
    public void onCreateCustomToolBar(Toolbar toolbar) {
        getLayoutInflater().inflate(R.layout.tc_layout_toolbar, toolbar);

        // findView
        mLayoutBar = (LinearLayout) toolbar.findViewById(R.id.layout_bar);
        mLayoutBarContent = (RelativeLayout) toolbar.findViewById(R.id.layout_bar_content);
        mLayoutLeftContainer = (LinearLayout) toolbar.findViewById(R.id.layout_left_container);
        mLayoutRightContainer = (LinearLayout) toolbar.findViewById(R.id.layout_right_container);
        mLayoutTitleContainer = (LinearLayout) toolbar.findViewById(R.id.layout_title_container);
        mTvTitle = (TextView) toolbar.findViewById(R.id.tv_title);
        mLayoutBarLine = (LinearLayout) toolbar.findViewById(R.id.layout_bar_line);
        mIvBarLine = (ImageView) toolbar.findViewById(R.id.iv_bar_line);

        mLayoutBar = (LinearLayout) findViewById(R.id.layout_bar);
        mLayoutBarContent = (RelativeLayout) findViewById(R.id.layout_bar_content);
        mLayoutLeftContainer = (LinearLayout) findViewById(R.id.layout_left_container);
        mLayoutRightContainer = (LinearLayout) findViewById(R.id.layout_right_container);
        mLayoutTitleContainer = (LinearLayout) findViewById(R.id.layout_title_container);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mLayoutBarLine = (LinearLayout) findViewById(R.id.layout_bar_line);
        mIvBarLine = (ImageView) findViewById(R.id.iv_bar_line);
    }

    // ******************************** showLoading ********************************

    /**
     * 显示、隐藏正在加载对话框
     */
    public void showLoading(boolean show) {
        try {
            // 需要显示时，如果页面已经finish，则return
            if (show && isFinishing()) {
                return;
            }

            // 需要显示时，如果正在显示，则return
            if (show && (mLoadingDialog != null && mLoadingDialog.isShowing())) {
                return;
            }

            // 需要隐藏时，如果mLoadingDialog不存在或没有在显示，则return
            if (!show && (mLoadingDialog == null || !mLoadingDialog.isShowing())) {
                return;
            }

            // 如果mLoadingDialog不存在，则创建
            if (mLoadingDialog == null) {
                switch (Configs.LOADING_STYLE) {
                    default:
                    case Params.LOADING_STYLE_DEFAULT_PROGRESS: {
                        mLoadingDialog = DefaultProgressDialog.createDialog(mContext);
                        break;
                    }
                    case Params.LOADING_STYLE_TC_PROGRESS: {
                        mLoadingDialog = TCProgressDialog.createDialog(mContext);
                        break;
                    }
                    case Params.LOADING_STYLE_TC_LOADING: {
                        mLoadingDialog = TCLoadingDialog.createDialog(mContext);
                        break;
                    }
                }
                mLoadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (mRequestAdapter != null) {
                            mRequestAdapter.cancelAll();
                        }
                    }
                });
            }

            // 显示/隐藏
            if (show) {
                mLoadingDialog.show();
            } else {
                mLoadingDialog.dismiss();
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 更新信息
     */
    public void updateLoading(String message) {
        if (null != mLoadingDialog && mLoadingDialog.isShowing() && !TextUtils.isEmpty(message)) {
            if (mLoadingDialog instanceof ProgressDialog) {
                ((ProgressDialog) mLoadingDialog).setMessage(message);
            } else if (mLoadingDialog instanceof TCDialog) {
                ((TCDialog) mLoadingDialog).setMessage(message);
            }
        }
        LogUtil.i(TAG, "updateLoading -> " + message);
    }

    // ******************************** getRequestAdapter ********************************

    /**
     * 返回DataAdapter对象
     */
    public RequestAdapter getRequestAdapter() {
        if (mRequestAdapter == null) {
            mRequestAdapter = new RequestAdapter(mContext, this);
        }
        return mRequestAdapter;
    }

    // **************************************************************** 适配ToolBar ****************************************************************

    // ******************************** findView ********************************

    protected void findViewForToolbar() {
        mLayoutBar = (LinearLayout) findViewById(R.id.layout_bar);
        mLayoutBarContent = (RelativeLayout) findViewById(R.id.layout_bar_content);
        mLayoutLeftContainer = (LinearLayout) findViewById(R.id.layout_left_container);
        mLayoutRightContainer = (LinearLayout) findViewById(R.id.layout_right_container);
        mLayoutTitleContainer = (LinearLayout) findViewById(R.id.layout_title_container);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mLayoutBarLine = (LinearLayout) findViewById(R.id.layout_bar_line);
        mIvBarLine = (ImageView) findViewById(R.id.iv_bar_line);
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
        super.setTitle(title);
        setTitle(String.valueOf(title), null);
    }

    public void setTitle(int titleId) {
        super.setTitle(titleId);
        setTitle(TCApp.get().getResources().getString(titleId), null);
    }

    private void resizeTitleWidth() {
        // 计算宽度，防止覆盖
        int leftWidth = mLayoutLeftContainer.getWidth();
        int rightWidth = mLayoutRightContainer.getWidth();
        mTvTitle.setMaxWidth(DeviceUtil.getScreenWidth(mContext) - leftWidth - rightWidth);
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
        resizeTitleWidth();

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
        if (AssetUtil.getTypeface(mContext) != null) {
            textView.setTypeface(AssetUtil.getTypeface(mContext));
        }
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
        if (AssetUtil.getTypeface(mContext) != null) {
            textView.setTypeface(AssetUtil.getTypeface(mContext));
        }
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
        if (mLayoutTitleContainer != null) {
            addViewToLayout(mLayoutTitleContainer, view, menuId);

            // 默认显示左边返回按钮
            clearLeftContainer();
            addLeftImageView(ThemeUtil.getBackIcon(), TCActivity.MENU_BACK);
        }
    }

    protected void addTitleTextView(String text) {
        if (mLayoutTitleContainer != null) {
            addTitleTextView(mLayoutTitleContainer, text, TCActivity.MENU_NULL);

            // 默认显示左边返回按钮
            clearLeftContainer();
            addLeftImageView(ThemeUtil.getBackIcon(), TCActivity.MENU_BACK);
        }
    }


    protected void addTitleImageView(int resId, int menuId) {
        if (mLayoutTitleContainer != null) {
            addTitleImageView(mLayoutTitleContainer, resId, menuId);

            // 默认显示左边返回按钮
            clearLeftContainer();
            addLeftImageView(ThemeUtil.getBackIcon(), TCActivity.MENU_BACK);
        }
    }

    protected void addTitleImageView(int resId) {
        addTitleImageView(resId, TCActivity.MENU_NULL);
    }

    protected void clearTitleContainer() {
        if (mLayoutTitleContainer != null) {
            clearLayout(mLayoutTitleContainer);
        }
    }

    /**
     * 左容器操作
     */
    protected void addLeftView(View view, int menuId) {
        if (mLayoutLeftContainer != null) {
            addViewToLayout(mLayoutLeftContainer, view, menuId);
        }
    }

    protected void addLeftTextView(String text, int menuId) {
        if (mLayoutLeftContainer != null) {
            addTextView(mLayoutLeftContainer, text, menuId);
        }
    }

    protected void addLeftImageView(int resId, int menuId) {
        if (mLayoutLeftContainer != null) {
            addImageView(mLayoutLeftContainer, resId, menuId);
        }
    }

    protected void clearLeftContainer() {
        if (mLayoutLeftContainer != null) {
            clearLayout(mLayoutLeftContainer);
        }
    }

    /**
     * 右容器操作
     */
    protected void addRightView(View view, int menuId) {
        if (mLayoutRightContainer != null) {
            addViewToLayout(mLayoutRightContainer, view, menuId);
        }
    }

    protected void addRightTextView(String text, int menuId) {
        if (mLayoutRightContainer != null) {
            addTextView(mLayoutRightContainer, text, menuId);
        }
    }

    protected void addRightImageView(int resId, int menuId) {
        if (mLayoutRightContainer != null) {
            addImageView(mLayoutRightContainer, resId, menuId);
        }
    }

    protected void clearRightContainer() {
        if (mLayoutRightContainer != null) {
            clearLayout(mLayoutRightContainer);
        }
    }
}
