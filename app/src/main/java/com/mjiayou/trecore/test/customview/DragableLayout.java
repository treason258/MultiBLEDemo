package com.mjiayou.trecore.test.customview;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by treason on 16/8/7.
 */
public class DragableLayout extends LinearLayout {

    private ViewDragHelper mDragger;

    private View mDragView;
    private View mAutoBackView;
    private View mEdgeTrackerView;

    private Point mAutoBackOriginPos = new Point();

    public DragableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

//            tryCaptureView 已用
//            clampViewPositionHorizontal 已用
//            clampViewPositionVertical 已用
//            getViewHorizontalDragRange 已用
//            getViewVerticalDragRange 已用
//            getOrderedChildIndex 改变同一个坐标（x,y）去寻找captureView位置的方法。（具体在：findTopChildUnder方法中）
//            onViewDragStateChanged 当ViewDragHelper状态发生变化时回调（IDLE,DRAGGING,SETTING[自动滚动时]）
//            onViewPositionChanged 当captureview的位置发生改变时回调
//            onViewCaptured 当captureview被捕获时回调
//            onViewReleased 手指释放的时候回调
//            onEdgeTouched 当触摸到边界时回调
//            onEdgeLock true的时候会锁住当前的边界，false则unLock。
//            onEdgeDragStarted 在边界拖动时回调

//            回调顺序:
//            shouldInterceptTouchEvent：
//                DOWN:
//                    -> getOrderedChildIndex(findTopChildUnder)
//                    -> onEdgeTouched
//                MOVE:
//                    -> getOrderedChildIndex(findTopChildUnder)
//                    -> getViewHorizontalDragRange & getViewVerticalDragRange(checkTouchSlop)(MOVE中可能不止一次)
//                    -> clampViewPositionHorizontal& clampViewPositionVertical
//                    -> onEdgeDragStarted
//                    -> tryCaptureView
//                    -> onViewCaptured
//                    -> onViewDragStateChanged
//
//            processTouchEvent:
//                DOWN:
//                    -> getOrderedChildIndex(findTopChildUnder)
//                    -> tryCaptureView
//                    -> onViewCaptured
//                    -> onViewDragStateChanged
//                    -> onEdgeTouched
//                MOVE:
//                    -> STATE==DRAGGING:dragTo
//                    -> STATE!=DRAGGING:
//                        -> onEdgeDragStarted
//                        -> getOrderedChildIndex(findTopChildUnder)
//                        -> getViewHorizontalDragRange&getViewVerticalDragRange(checkTouchSlop)
//                        -> tryCaptureView
//                        -> onViewCaptured
//                        -> onViewDragStateChanged

            @Override

            public boolean tryCaptureView(View child, int pointerId) {
                //mEdgeTrackerView禁止直接移动
                return child == mDragView || child == mAutoBackView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            // 手指释放的时候回调
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                // mAutoBackView手指释放时可以自动回去
                if (releasedChild == mAutoBackView) {
                    mDragger.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                    invalidate();
                }
            }

            // 在边界拖动时回调
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                mDragger.captureChildView(mEdgeTrackerView, pointerId);
            }
        });
        mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mDragger.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mDragger.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        mAutoBackOriginPos.x = mAutoBackView.getLeft();
        mAutoBackOriginPos.y = mAutoBackView.getTop();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }
}
