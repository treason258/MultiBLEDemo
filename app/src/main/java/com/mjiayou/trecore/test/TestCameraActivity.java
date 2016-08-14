package com.mjiayou.trecore.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mjiayou.trecore.TCActivity;
import com.mjiayou.trecore.util.BitmapUtil;
import com.mjiayou.trecore.util.CameraUtil;
import com.mjiayou.trecore.util.DeviceUtil;
import com.mjiayou.trecoredemo.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class TestCameraActivity extends TCActivity {

    @InjectView(R.id.surfaceview)
    SurfaceView mSurfaceView;
    @InjectView(R.id.tv_capture)
    TextView mTvCapture;
    @InjectView(R.id.iv_img)
    ImageView mIvImg;
    @InjectView(R.id.tv_path)
    TextView mTvPath;

    @OnClick({R.id.tv_capture})
    void onClicks(View view) {
        switch (view.getId()) {
            case R.id.tv_capture:
                mCamera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        Bitmap bitmap = BitmapUtil.byte2bitmap(data);
                        mIvImg.setImageBitmap(bitmap);
                    }
                });
                break;
        }
    }

    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private Camera.Parameters mParams;
    private Camera.Size mPreviewSize;
    private Camera.Size mPictureSize;
    private int mPreviewWidth;
    private int mPreviewHeight;

    /**
     * startActivity
     */
    public static void open(Context context) {
        Intent intent = new Intent(context, TestCameraActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_camera);
        ButterKnife.inject(this);

        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraStopAndRelease();
    }

    @Override
    public void initView() {
        super.initView();

        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new MySurfaceHolderCallback());
    }

    /**
     * MySurfaceHolderCallback
     */
    class MySurfaceHolderCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                mCamera = Camera.open(mCameraId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            try {

                mParams = mCamera.getParameters();
                mPreviewSize = CameraUtil.getFitPreviewSize(mParams.getSupportedPreviewSizes(), 720);
                mPictureSize = CameraUtil.getFitPictureSize(mParams.getSupportedPictureSizes(), 1280);
                mPreviewWidth = mPreviewSize.width;
                mPreviewHeight = mPreviewSize.height;

                // 设置 mSurfaceView
                float previewRate = mPreviewWidth * 1.0f / mPreviewHeight;
                int screenWidth = DeviceUtil.getScreenWidth(mContext);
                int screenHeight = (int) (screenWidth * previewRate);
                ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(mSurfaceView.getLayoutParams());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
                layoutParams.height = screenHeight;
                mSurfaceView.setLayoutParams(layoutParams);

                // 设置 mParams
                mParams.setPictureFormat(PixelFormat.JPEG);
                mParams.setPictureSize(mPictureSize.width, mPictureSize.height);
                mParams.setPreviewSize(mPreviewSize.width, mPreviewSize.height);

                // 设置 自动对焦
                List<String> focusModes = mParams.getSupportedFocusModes();
                if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                    mParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
                }

                // 设置 mCamera
                mCamera.setDisplayOrientation(90);
                mCamera.setParameters(mParams);

                // 开始预览
                mCamera.setPreviewDisplay(holder);
                mCamera.setPreviewCallback(new MyCameraPreviewCallback());
                mCamera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            cameraStopAndRelease();
        }
    }

    /**
     * MyCameraPreviewCallback
     */
    class MyCameraPreviewCallback implements Camera.PreviewCallback {

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {

        }
    }

    /**
     * 释放相机资源
     */
    private void cameraStopAndRelease() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }
}
