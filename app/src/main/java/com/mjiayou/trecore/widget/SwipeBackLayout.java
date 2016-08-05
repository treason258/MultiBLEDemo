package com.mjiayou.trecore.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.Scroller;

import com.mjiayou.trecore.util.LogUtil;
import com.mjiayou.trecoredemo.R;

import java.util.LinkedList;
import java.util.List;

/**
 * @author tim
 * @date 2014-12-19
 * @email tim_ding@qq.com
 */
public class SwipeBackLayout extends FrameLayout {

    private final static String TAG = "SwipeBackLayout";

    // var
    private Activity mActivity;
    // 有效touch距离，触发移动事件的最短距离，如果小于这个距离就不触发移动控件
    private int mTouchSlop;
    // 滚动类
    private Scroller mScroller;
    // 阴影图片
    private Drawable mShadowDrawable;
    // 宽度
    private int mViewWidth;
    // 父容器
    private View mContentView;

    // 按下的X坐标
    private int mDownX;
    // 按下的Y坐标
    private int mDownY;
    // 临时的X坐标
    private int mTempX;
    // 是否正在滑动
    private boolean isSilding;
    // 是否停止
    private boolean isFinish;
    // mViewPagers
    private List<ViewPager> mViewPagers = new LinkedList<ViewPager>();
    // mWebViews
    private List<WebView> mWebViews = new LinkedList<WebView>();
    // mHorScrollViews
    private List<HorizontalScrollView> mHorizontalScrollViews = new LinkedList<HorizontalScrollView>();

    // treason@20151222
//    private VelocityTracker mVelocityTracker = null;
//    private long timeDown = 0l;
//    private long timeUp = 0l;

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);
        mShadowDrawable = getResources().getDrawable(R.drawable.tc_swipe_back_shadow_left);
    }

    /**
     * 事件拦截操作
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // 处理 ViewPager 冲突问题
        ViewPager viewPager = getTouchViewPager(mViewPagers, event);
        if (viewPager != null) {
            if (viewPager.getCurrentItem() != 0) {
                LogUtil.i(TAG, "viewPager -> " + viewPager);
                LogUtil.i(TAG, "viewPager.getCurrentItem() != 0");
                return super.onInterceptTouchEvent(event);
            }
        }

        // 处理 Webview 冲突问题
        WebView webView = getTouchWebView(mWebViews, event);
        if (webView != null) {
            if (webView.getScrollX() != 0) {
                LogUtil.i(TAG, "webView -> " + webView);
                LogUtil.i(TAG, "webView.getScrollX() != 0");
                return super.onInterceptTouchEvent(event);
            }
        }

        // 处理 HorizontalScrollView 冲突问题
        HorizontalScrollView horizontalScrollView = getTouchHorScrollerView(mHorizontalScrollViews, event);
        if (horizontalScrollView != null) {
            if (horizontalScrollView.getScrollX() != 0) {
                LogUtil.i(TAG, "horizontalScrollView -> " + webView);
                LogUtil.i(TAG, "horizontalScrollView.getScrollX() != 0");
                return super.onInterceptTouchEvent(event);
            }
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                LogUtil.i(TAG, "ACTION_DOWN - onInterceptTouchEvent");
                mDownX = mTempX = (int) event.getRawX();
                mDownY = (int) event.getRawY();

                // treason@20151222
//                if (mVelocityTracker == null) {
//                    mVelocityTracker = VelocityTracker.obtain();
//                } else {
//                    mVelocityTracker.clear();
//                }
//                mVelocityTracker.addMovement(event);
//                timeDown = System.currentTimeMillis();
//                LogUtil.i(TAG, "ACTION_DOWN-onInterceptTouchEvent -> " + "timeDown -> " + timeDown);
                break;
            case MotionEvent.ACTION_MOVE:
//                LogUtil.i(TAG, "ACTION_MOVE - onInterceptTouchEvent");
                int moveX = (int) event.getRawX();
                int moveY = (int) event.getRawY();
                if ((moveX - mDownX > mTouchSlop) && (Math.abs(moveY - mDownY) < mTouchSlop)) {
                    // 满足此条件屏蔽 SildingBackLayout 里面子类的touch事件
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                LogUtil.i(TAG, "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
//                LogUtil.i(TAG, "ACTION_POINTER_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
//                LogUtil.i(TAG, "ACTION_MOVE");
                // treason@20160105
//                mActivity.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_rect_solid_transparent));
                // treason@20151222
                int moveX = (int) event.getRawX();
                int moveY = (int) event.getRawY();
                int deltaX = mTempX - moveX;
                mTempX = moveX;
                if ((moveX - mDownX > mTouchSlop) && (Math.abs(moveY - mDownY) < mTouchSlop)) {
                    isSilding = true;
                }
                if ((moveX - mDownX >= 0) && isSilding) {
                    mContentView.scrollBy(deltaX, 0);
                }

                // treason@20151222
//                mVelocityTracker.addMovement(event);
//                mVelocityTracker.computeCurrentVelocity(1000); // 设置units的值为1000，意思为一秒时间内运动了多少个像素
//                LogUtil.i(TAG, "getXVelocity -> " + mVelocityTracker.getXVelocity());
//                if (mVelocityTracker.getXVelocity() > 3000) {
//                    isFinish = true;
//                    scrollRight();
//                    break;
//                }
                break;
            case MotionEvent.ACTION_UP:
//                LogUtil.i(TAG, "ACTION_UP");
                isSilding = false;
                if (Math.abs(mContentView.getScrollX()) > mViewWidth / 3) { // 滑动距离超过三分之一，则表示执行退出操作，自动完成滑动操作
                    isFinish = true;
                    scrollRight();
                } else { // 滑动距离不超过三分之一，则表示误操作，回滚
                    isFinish = false;
                    scrollOrigin();

                    // treason@20151222
                    // 检测手指抬起时间和手指落下时间间隔小于100ms时，表示意图执行完滑动
//                    timeUp = System.currentTimeMillis();
//                    if (timeUp - timeDown < 100) {
//                        isFinish = true;
//                        scrollRight();
//                    } else {
//                        scrollOrigin();
//                        isFinish = false;
//                    }
                }
//                LogUtil.i(TAG, "ACTION_UP -> " + "Math.abs(mContentView.getScrollX()) -> " + Math.abs(mContentView.getScrollX()) + ";viewWidth -> " + viewWidth);
//                LogUtil.i(TAG, "ACTION_UP -> " + "timeUp -> " + timeUp + ";timeDown -> " + timeDown + ";timeUp-timeDown -> " + (timeUp - timeDown));
                break;
            case MotionEvent.ACTION_CANCEL:
//                LogUtil.i(TAG, "ACTION_CANCEL");
//                mVelocityTracker.recycle();
                break;

        }

        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mViewWidth = this.getWidth();

            getAlLViewPager(this);
            getAlLWebViews(this);
            getAllHorizontalScrollViews(this);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mContentView != null && mShadowDrawable != null) {
            int left = mContentView.getLeft() - mShadowDrawable.getIntrinsicWidth();
            int right = left + mShadowDrawable.getIntrinsicWidth();
            int top = mContentView.getTop();
            int bottom = mContentView.getBottom();

            mShadowDrawable.setBounds(left, top, right, bottom);
            mShadowDrawable.draw(canvas);
        }
    }

    @Override
    public void computeScroll() {
        // 调用 startScroll 的时候 scroller.computeScrollOffset() 返回 true
        if (mScroller.computeScrollOffset()) {
            mContentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
            // treason@20160105
//            mActivity.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_rect_solid_white));

            if (mScroller.isFinished() && isFinish) {
                mActivity.finish();
            }
        }
    }

    /**
     * attachToActivity
     */
    public void attachToActivity(Activity activity) {
        mActivity = activity;

        TypedArray typedArray = activity.getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowBackground});
        int background = typedArray.getResourceId(0, 0);
        typedArray.recycle();

        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decorView.getChildAt(0);
        decorChild.setBackgroundResource(background);
        decorView.removeView(decorChild);
        addView(decorChild);

        mContentView = (View) decorChild.getParent();
        decorView.addView(this);
    }

    /**
     * 滚动出界面
     */
    private void scrollRight() {
        final int delta = (mViewWidth + mContentView.getScrollX());
        // 调用 startScroll 方法来设置一些滚动的参数，我们在 computeScroll() 方法中调用 scrollTo 来滚动 item
        mScroller.startScroll(mContentView.getScrollX(), 0, -delta + 1, 0, Math.abs(delta));
        postInvalidate();
    }

    /**
     * 滚动到起始位置
     */
    private void scrollOrigin() {
        int delta = mContentView.getScrollX();
        // 调用 startScroll 方法来设置一些滚动的参数，我们在 computeScroll() 方法中调用 scrollTo 来滚动 item
        mScroller.startScroll(mContentView.getScrollX(), 0, -delta, 0, Math.abs(delta));
        postInvalidate();
    }

    // ******************************** getAlLViewPager ********************************

    /**
     * 获取 SwipeBackLayout 里面的 ViewPager 的集合
     */
    private void getAlLViewPager(ViewGroup parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewPager) {
                mViewPagers.add((ViewPager) child);
            } else if (child instanceof ViewGroup) {
                getAlLViewPager((ViewGroup) child);
            }
        }
    }

    /**
     * 获取 SwipeBackLayout 里面的 WebView 的集合
     */
    private void getAlLWebViews(ViewGroup parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof WebView) {
                mWebViews.add((WebView) child);
            } else if (child instanceof ViewGroup) {
                getAlLWebViews((ViewGroup) child);
            }
        }
    }

    /**
     * 获取 SwipeBackLayout 里面的 HorizontalScrollView 的集合
     */
    private void getAllHorizontalScrollViews(ViewGroup parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof HorizontalScrollView) {
                mHorizontalScrollViews.add((HorizontalScrollView) child);
            } else if (child instanceof ViewGroup) {
                getAllHorizontalScrollViews((ViewGroup) child);
            }
        }
    }

    // ******************************** getTouchViewPager ********************************

    /**
     * 返回我们 touch 的 ViewPager
     */
    private ViewPager getTouchViewPager(List<ViewPager> viewPagers, MotionEvent event) {
        if (viewPagers == null || viewPagers.size() == 0) {
            return null;
        }
        Rect rect = new Rect();
        for (ViewPager viewPager : viewPagers) {
            viewPager.getHitRect(rect);
            if (rect.contains((int) event.getX(), (int) event.getY())) {
                return viewPager;
            }
        }
        return null;
    }

    /**
     * 返回我们 touch 的 WebView
     */
    private WebView getTouchWebView(List<WebView> webViews, MotionEvent event) {
        if (webViews == null || webViews.size() == 0) {
            return null;
        }
        Rect rect = new Rect();
        for (WebView webView : webViews) {
            webView.getGlobalVisibleRect(rect);
            if (rect.contains((int) event.getX(), (int) event.getY())) {
                return webView;
            }
        }
        return null;
    }

    /**
     * 返回我们 touch 的 HorizontalScrollView
     */
    private HorizontalScrollView getTouchHorScrollerView(List<HorizontalScrollView> horizontalScrollViews, MotionEvent event) {
        if (horizontalScrollViews == null || horizontalScrollViews.size() == 0) {
            return null;
        }
        Rect rect = new Rect();
        for (HorizontalScrollView horizontalScrollView : horizontalScrollViews) {
            horizontalScrollView.getGlobalVisibleRect(rect);
            if (rect.contains((int) event.getX(), (int) event.getY())) {
                return horizontalScrollView;
            }
        }
        return null;
    }
}