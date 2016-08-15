package com.mjiayou.trecore.test.aidl;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

import com.mjiayou.trecore.TCActivity;
import com.mjiayou.trecore.util.LogUtil;
import com.mjiayou.trecore.util.ThreadUtil;
import com.mjiayou.trecoredemo.R;

import java.util.List;

public class TestAIDLActivity extends TCActivity {

    private final int MESSAGE_NEW_BOOK_ARRIVED = 1;

    private IBookManager mIBookManager = null;

    @SuppressLint("HandlerLeak")
    private android.os.Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    LogUtil.i(TAG, "New Book Arrived | " + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            LogUtil.i(TAG, "binderDied. thread name -> " + ThreadUtil.getThreadName());
            if (mIBookManager == null) {
                return;
            }
            mIBookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mIBookManager = null;
        }
    };

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.i(TAG, "onServiceConnected");

            if (service == null) {
                LogUtil.i(TAG, "service == null");
                return;
            }

            mIBookManager = IBookManager.Stub.asInterface(service);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (mIBookManager == null) {
                            LogUtil.i(TAG, "mIBookManager == null");
                            return;
                        }

                        mIBookManager.asBinder().linkToDeath(mDeathRecipient, 0);

                        // 获取
                        getBookList();

                        // 新增
                        addBook(new Book(3, "Android开发艺术探索"));

                        // 获取
                        getBookList();

                        // 监听
                        mIBookManager.registerListener(mOnNewBookArrivedListener);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.i(TAG, "onServiceConnected");
            LogUtil.i(TAG, "onServiceConnected. thread name -> " + ThreadUtil.getThreadName());
        }
    };

    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {

        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            mIBookManager = null;
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, book).sendToTarget();
        }
    };

    /**
     * startActivity
     */
    public static void open(Context context) {
        Intent intent = new Intent(context, TestAIDLActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_aidl);

        setTitle(TAG);

        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIBookManager == null) {
            LogUtil.i(TAG, "mIBookManager == null");
            return;
        }

        if (mIBookManager.asBinder().isBinderAlive()) {
            try {
                mIBookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mServiceConnection);
    }

    /**
     * 获取所有图书信息
     */
    private void getBookList() throws RemoteException {
        if (mIBookManager == null) {
            LogUtil.i(TAG, "mIBookManager == null");
            return;
        }

        List<Book> list = mIBookManager.getBookList();
        LogUtil.i(TAG, "getBookList | list type -> " + list.getClass().getCanonicalName());
        for (int i = 0; i < list.size(); i++) {
            LogUtil.i(TAG, list.get(i).toString());
        }
    }

    /**
     * 新增图书
     */
    private void addBook(Book book) throws RemoteException {
        if (mIBookManager == null) {
            LogUtil.i(TAG, "mIBookManager == null");
            return;
        }

        mIBookManager.addBook(book);
        LogUtil.i(TAG, "addBook | " + book.toString());
    }
}
