package com.mjiayou.trecore.test.surfaceview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;

import com.mjiayou.trecore.TCActivity;
import com.mjiayou.trecore.util.DeviceUtil;
import com.mjiayou.trecore.util.LogUtil;
import com.mjiayou.trecoredemo.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class TestSurfaceViewActivity extends TCActivity {

    @InjectView(R.id.surfaceview)
    SurfaceView mSurfaceView;
    @InjectView(R.id.layout_container)
    RelativeLayout mLayoutContainer;

    private SurfaceHolder mSurfaceHolder;
    private Timer mTimer;
    private MyTimerTask mTimerTask;

    private Canvas mCanvas;
    private Paint mPaint;

    private int mScreenWidth, mScreenHeight; // 屏幕宽高

    int mCenterY; // 中心线
    int mAxisY[]; // 保存正弦波的Y轴上的点
    int mOldX, mOldY; // 上一个XY点
    int mCurrentX; // 当前绘制到的X轴上的点

    @OnClick({R.id.btn_a, R.id.btn_b, R.id.btn_c})
    void onClicks(View view) {
        switch (view.getId()) {
            case R.id.btn_a:
                mSurfaceView.setVisibility(View.VISIBLE);
                mLayoutContainer.setVisibility(View.GONE);

                simpleDraw(mAxisY.length); // 直接绘制正弦波
                break;
            case R.id.btn_b:
                mSurfaceView.setVisibility(View.VISIBLE);
                mLayoutContainer.setVisibility(View.GONE);

                mOldY = mCenterY;
                mTimer.schedule(mTimerTask, 0, 5);//动态绘制正弦波
                break;
            case R.id.btn_c:
//                mSurfaceView.setVisibility(View.GONE);
//                mLayoutContainer.setVisibility(View.VISIBLE);

                clearDraw();
                mTimerTask.cancel();
                break;
        }
    }

    /**
     * startActivity
     */
    public static void open(Context context) {
        Intent intent = new Intent(context, TestSurfaceViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_surface_view);
        ButterKnife.inject(this);

        initView();
    }

    @Override
    public void initView() {
        super.initView();
        mSurfaceHolder = mSurfaceView.getHolder();

        // 动态绘制正弦波的定时器
        mTimer = new Timer();
        mTimerTask = new MyTimerTask();

        // 获取屏幕宽高
        mScreenWidth = DeviceUtil.getScreenWidth(mContext);
        mScreenHeight = DeviceUtil.getScreenHeight(mContext);

        mPaint = new Paint();
        mPaint.setColor(Color.GREEN); // 画笔为绿色
        mPaint.setStrokeWidth(2); // 设置画笔粗细

        // 初始化y轴数据
        mCenterY = mScreenHeight / 2;
        mAxisY = new int[mScreenWidth];
        for (int i = 0; i < mAxisY.length; i++) { // 计算正弦波
            mAxisY[i] = mCenterY - (int) (100 * Math.sin(i * 2 * Math.PI / 180));
        }

//        mCanvas = mSurfaceHolder.lockCanvas(new Rect(100, 100, mScreenWidth, mScreenHeight)); // 关键:获取画布
//        mCanvas.drawLine(0, 0, 0, mScreenHeight, mPaint); // 画Y轴
//        mCanvas.drawLine(0, mCenterY, mScreenWidth, mCenterY, mPaint); // 画X轴
//        mSurfaceHolder.unlockCanvasAndPost(mCanvas); // 解锁画布，提交画好的图像
    }

    /**
     * MyTimerTask
     */
    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            simpleDraw(mCurrentX);
            mCurrentX++; // 往前进
            if (mCurrentX == mAxisY.length) { // 如果到了终点，则清屏重来
                clearDraw();
                mCurrentX = 0;
                mOldY = mCenterY;
            }
        }
    }

    /**
     * 绘制指定区域
     */
    void simpleDraw(int length) {
        if (length == 0) {
            mOldX = 0;
        }
        mCanvas = mSurfaceHolder.lockCanvas(new Rect(mOldX, 0, mOldX + length, mScreenHeight)); // 关键:获取画布
        LogUtil.i("Canvas:", String.valueOf(mOldX) + "," + String.valueOf(mOldX + length));

        int y;
        for (int i = mOldX + 1; i < length; i++) { // 绘画正弦波
            y = mAxisY[i - 1];
            mCanvas.drawLine(mOldX, mOldY, i, y, mPaint);
            mOldX = i;
            mOldY = y;
        }
        mSurfaceHolder.unlockCanvasAndPost(mCanvas); // 解锁画布，提交画好的图像
    }

    void clearDraw() {
        mCanvas = mSurfaceHolder.lockCanvas(null);
        mCanvas.drawColor(Color.BLACK);// 清除画布
        mSurfaceHolder.unlockCanvasAndPost(mCanvas);
    }
}
