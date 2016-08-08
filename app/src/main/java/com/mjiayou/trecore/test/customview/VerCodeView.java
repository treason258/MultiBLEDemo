package com.mjiayou.trecore.test.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.mjiayou.trecoredemo.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by treason on 16/8/7.
 */
public class VerCodeView extends View {

    /**
     * 背景颜色
     */
    private int mBackgroud;
    /**
     * 文本
     */
    private String mText;
    /**
     * 文本颜色
     */
    private int mTextColor;
    /**
     * 文本大小
     */
    private int mTextSize;

    private Paint mPaint; // 画笔
    private Rect mRect; // 矩形块

    public VerCodeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    public VerCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerCodeView(Context context) {
        this(context, null);
    }

    /**
     * 初始化
     */
    private void init(Context context, AttributeSet attrs, int defStyle) {
        // 获得定义的自定义样式属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.VerCodeView, defStyle, 0);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.VerCodeView_backgroud:
                    // 默认颜色设置为灰色
                    mBackgroud = typedArray.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.VerCodeView_text:
                    mText = typedArray.getString(attr);
                    break;
                case R.styleable.VerCodeView_textColor:
                    // 默认颜色设置为黑色
                    mTextColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.VerCodeView_textSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    mTextSize = typedArray.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }

        }
        typedArray.recycle();

        // 初始化画笔
        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        // 初始化其他
        mRect = new Rect();

        // 点击事件
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mText = randomText();
                postInvalidate();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//        系统帮我们测量的高度和宽度都是MATCH_PARNET，当我们设置明确的宽度和高度时，系统帮我们测量的结果就是我们设置的结果。
//        当我们设置为WRAP_CONTENT,或者MATCH_PARENT系统帮我们测量的结果就是MATCH_PARENT的长度。
//        所以，当设置了WRAP_CONTENT时，我们需要自己进行测量，即重写onMesure方法:
//        重写之前先了解MeasureSpec的specMode,一共三种类型：
//        EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
//        AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
//        UNSPECIFIED：表示子布局想要多大就多大，很少使用

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mPaint.getTextBounds(mText, 0, mText.length(), mRect);
            width = getPaddingLeft() + mRect.width() + getPaddingRight();
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            mPaint.getTextBounds(mText, 0, mText.length(), mRect);
            height = getPaddingTop() + mRect.height() + getPaddingBottom();
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制背景
        mPaint.setColor(mBackgroud);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        // 绘制数字
        mPaint.setColor(mTextColor);
        mPaint.getTextBounds(mText, 0, mText.length(), mRect);
        canvas.drawText(mText, getWidth() / 2 - mRect.width() / 2, getHeight() / 2 + mRect.height() / 2, mPaint);
    }

    /**
     * 获得四位随机数
     */
    private String randomText() {
        Random random = new Random();
        Set<Integer> set = new HashSet<>();
        while (set.size() < 4) {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set) {
            sb.append(String.valueOf(i));
        }
        return sb.toString();
    }
}
