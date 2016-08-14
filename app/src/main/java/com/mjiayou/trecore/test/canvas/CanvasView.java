package com.mjiayou.trecore.test.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.mjiayou.trecore.util.DeviceUtil;
import com.mjiayou.trecoredemo.R;

/**
 * Created by treason on 16/8/8.
 */
public class CanvasView extends View {

    private Paint mPaint; // 画笔
    private Rect mRect;
    private RectF mRectF;
    private Path mPath;
    private Shader mShader;
    private Bitmap mBitmap;
    private int mScreenWidth, mScreenHeight; // 屏幕宽高

    public CanvasView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasView(Context context) {
        this(context, null);
    }

    /**
     * 初始化
     */
    private void init(Context context, AttributeSet attrs, int defStyle) {
        // 初始化画笔
        mPaint = new Paint();
        mPaint.setTextSize(30);
        // 初始化其他
        mRect = new Rect();
        mRectF = new RectF();
        mPath = new Path();
        mShader = new LinearGradient(0, 0, 200, 200,
                new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.LTGRAY}, null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tc_launcher);

        // 获取屏幕宽高
        mScreenWidth = DeviceUtil.getScreenWidth(context);
        mScreenHeight = DeviceUtil.getScreenHeight(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 方法说明
         * drawPoint 绘制点
         * drawPoints 绘制多个点
         * drawLine 绘制直线
         * drawPath 绘制多边形
         * drawRect 绘制矩形
         * drawCircle 绘制圆形
         * drawOval 绘制椭圆
         * drawArc 绘制弧线
         * drawText 绘制文本
         * drawBitmap 绘制图片
         */

        /**
         * 画网格
         */
        mPaint.setColor(Color.LTGRAY);
        // 计算文本区域并绘制
        String text = mScreenWidth + "*" + mScreenHeight;
        mPaint.getTextBounds(text, 0, text.length(), mRect);
        canvas.drawText(text, mScreenWidth - mRect.width(), mRect.height(), mPaint);
        // 绘制竖线
        for (int i = 0; i < mScreenWidth / 100 + 1; i++) {
            canvas.drawLine(i * 100, 0, i * 100, mScreenHeight, mPaint);
        }
        // 绘制横线
        for (int i = 0; i < mScreenHeight / 100 + 1; i++) {
            canvas.drawLine(0, i * 100, mScreenWidth, i * 100, mPaint);
        }

        // 画圆 Y=100
        mPaint.setColor(Color.RED);
        canvas.drawText("画圆：", 100, 100, mPaint);// 画文本
        canvas.drawCircle(300, 100, 30, mPaint);// 小圆
        mPaint.setAntiAlias(true); // 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
        canvas.drawCircle(500, 100, 50, mPaint);// 大圆

        // 画线 Y=300
        mPaint.setColor(Color.GREEN);
        canvas.drawText("画线：", 100, 300, mPaint);
        canvas.drawLine(300, 200, 800, 200, mPaint);// 画线
        canvas.drawLine(300, 250, 800, 300, mPaint);// 斜线

        // 画弧线 Y=400
        mPaint.setColor(Color.RED);
        canvas.drawText("画弧线：", 100, 400, mPaint);
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        mRectF.set(310, 350, 390, 450);
        canvas.drawArc(mRectF, 180, 180, false, mPaint); // 小弧形
        mRectF.set(410, 350, 490, 450);
        canvas.drawArc(mRectF, 180, 180, false, mPaint); // 小弧形
        mRectF.set(350, 400, 450, 450);
        canvas.drawArc(mRectF, 0, 180, false, mPaint); // 大弧形

        // 画矩形 Y=600
        mPaint.setColor(Color.GREEN);
        canvas.drawText("画矩形：", 100, 600, mPaint);
        mPaint.setStyle(Paint.Style.FILL);//设置填满
        canvas.drawRect(300, 500, 600, 600, mPaint);// 正方形
        canvas.drawRect(700, 500, 800, 600, mPaint);// 长方形

        // 画扇形和椭圆 Y=700
        mPaint.setColor(Color.RED);
        canvas.drawText("画扇形和椭圆:", 100, 700, mPaint);
        mPaint.setShader(mShader); // 设置渐变色 这个正方形的颜色是改变的
        mRectF.set(300, 650, 600, 850); // 设置个新的长方形，扫描测量
        canvas.drawArc(mRectF, 200, 130, true, mPaint); // 画弧，第一个参数是RectF：该类是第二个参数是角度的开始，第三个参数是多少度，第四个参数是真的时候画扇形，是假的时候画弧线
        mRectF.set(700, 650, 900, 750); // 设置个新的长方形，扫描测量
        canvas.drawOval(mRectF, mPaint); // 画椭圆，把oval改一下

        // 画多边形 Y=900
        canvas.drawText("画多边形：", 100, 900, mPaint);
        mPath.moveTo(300, 800);// 此点为多边形的起点
        mPath.lineTo(300, 900);
        mPath.lineTo(400, 900);
        mPath.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(mPath, mPaint);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        mPath.moveTo(600, 800);
        mPath.lineTo(700, 800);
        mPath.lineTo(750, 850);
        mPath.lineTo(700, 900);
        mPath.lineTo(600, 900);
        mPath.lineTo(550, 850);
        mPath.close(); // 封闭
        canvas.drawPath(mPath, mPaint);
        /*
         * Path类封装复合(多轮廓几何图形的路径
         * 由直线段*、二次曲线,和三次方曲线，也可画以油画。drawPath(路径、油漆),要么已填充的或抚摸
         * (基于油漆的风格),或者可以用于剪断或画画的文本在路径。
         */

        // 画圆角矩形 Y=1000
        mPaint.setShader(null);
        mPaint.setColor(Color.GREEN);
        canvas.drawText("画圆角矩形：", 100, 1000, mPaint);
        mPaint.setStyle(Paint.Style.FILL); // 充满
        mPaint.setAntiAlias(true); // 设置画笔的锯齿效果
        mRectF.set(300, 950, 600, 1050);
        canvas.drawRoundRect(mRectF, 20, 15, mPaint);//第二个参数是x半径，第三个参数是y半径

        // 画贝塞尔曲线 Y=1200
        mPaint.setColor(Color.RED);
        canvas.drawText("画贝塞尔曲线：", 100, 1200, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath.moveTo(300, 1100); // 设置Path的起点
        mPath.quadTo(500, 1100, 600, 1300); // 设置贝塞尔曲线的控制点坐标和终点坐标
        canvas.drawPath(mPath, mPaint); // 画出贝塞尔曲线

        // 画点 Y=1400
        mPaint.setColor(Color.BLACK);
        canvas.drawText("画点：", 100, 1400, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPoint(300, 1400, mPaint); // 画一个点
        canvas.drawPoints(new float[]{310, 1410, 320, 1420, 330, 1430}, mPaint); // 画多个点

        // 画图片 Y=1600
        mPaint.setColor(Color.GREEN);
        canvas.drawText("画图片：", 100, 1600, mPaint);
        canvas.drawBitmap(mBitmap, 300, 1500, mPaint);
    }
}
