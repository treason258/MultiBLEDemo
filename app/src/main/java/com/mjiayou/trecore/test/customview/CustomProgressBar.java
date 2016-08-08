package com.mjiayou.trecore.test.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.mjiayou.trecoredemo.R;

/**
 * Created by treason on 16/8/7.
 */
public class CustomProgressBar extends View {

    public final static int DEFAULT_CIRCLE_WIDTH = 100; // 单位为dp

    /**
     * 圆圈的厚度
     */
    private int mCircleBorder;
    /**
     * 第一圈的颜色
     */
    private int mFirstColor;
    /**
     * 第二圈的颜色
     */
    private int mSecondColor;
    /**
     * 速度
     */
    private int mSpeed;

    private Paint mPaint; // 画笔
    private RectF mRectF; // 矩形块
    private int mCircleWidth; // 默认圆圈的宽度
    private int mProgress; // 当前进度
    private boolean isNext = false; //是否应该开始下一个

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context) {
        this(context, null);
    }

    /**
     * 初始化
     */
    private void init(Context context, AttributeSet attrs, int defStyle) {
        // 获得定义的自定义样式属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, defStyle, 0);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.CustomProgressBar_circleBorder:
                    // 默认设置为10dp
                    mCircleBorder = typedArray.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomProgressBar_firstColor:
                    // 默认颜色设置为红色
                    mFirstColor = typedArray.getColor(attr, Color.RED);
                    break;
                case R.styleable.CustomProgressBar_secondColor:
                    // 默认颜色设置为绿色
                    mSecondColor = typedArray.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CustomProgressBar_speed:
                    // 默认设置为10
                    mSpeed = typedArray.getInt(attr, 10);
                    break;
            }
        }
        typedArray.recycle();

        // 默认圆圈的宽度
        mCircleWidth = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_CIRCLE_WIDTH, getResources().getDisplayMetrics()));

        // 初始化画笔
        mPaint = new Paint();
        // 初始化其他
        mRectF = new RectF();
        // 绘图线程
        new Thread() {
            public void run() {
                while (true) {
                    mProgress++;
                    if (mProgress == 360) {
                        mProgress = 0;
                        if (!isNext)
                            isNext = true;
                        else
                            isNext = false;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = mCircleWidth;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = mCircleWidth;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centre = getWidth() / 2; // 获取圆心的x坐标
        int radius = centre - mCircleBorder / 2;// 半径
        mPaint.setStrokeWidth(mCircleBorder); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        mRectF.set(centre - radius, centre - radius, centre + radius, centre + radius); // 用于定义的圆弧的形状和大小的界限
        // 第一颜色的圈完整，第二颜色跑
        if (!isNext) {
            // 画圆环
            mPaint.setColor(mFirstColor);
            canvas.drawCircle(centre, centre, radius, mPaint);
            // 根据进度画圆弧
            mPaint.setColor(mSecondColor);
            canvas.drawArc(mRectF, -90, mProgress, false, mPaint);
        } else {
            // 画圆环
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(centre, centre, radius, mPaint);
            // 根据进度画圆弧
            mPaint.setColor(mFirstColor);
            canvas.drawArc(mRectF, -90, mProgress, false, mPaint);
        }
    }
}
