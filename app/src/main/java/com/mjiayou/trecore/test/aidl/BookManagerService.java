package com.mjiayou.trecore.test.aidl;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mjiayou.trecore.TCService;
import com.mjiayou.trecore.util.LogUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by treason on 16/8/15.
 */
public class BookManagerService extends TCService {

    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();

    private Binder mBinder = new IBookManager.Stub() {

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            // 验证包名
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            if (TextUtils.isEmpty(packageName) || !packageName.startsWith("com.mjiayou")) {
                LogUtil.i(TAG, "!packageName.startsWith(\"com.mjiayou\")");
                return false;
            }

            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            LogUtil.i(TAG, "getBookList");

            SystemClock.sleep(2000);
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            LogUtil.i(TAG, "addBook | " + book);

            SystemClock.sleep(1000);
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.register(listener);

            int count = mListenerList.beginBroadcast();
            mListenerList.finishBroadcast();
            LogUtil.i(TAG, "registerListener | count -> " + count);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.unregister(listener);

            int count = mListenerList.beginBroadcast();
            mListenerList.finishBroadcast();
            LogUtil.i(TAG, "unregisterListener | count -> " + count);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Android DEV"));
        mBookList.add(new Book(2, "IOS DEV"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!mIsServiceDestroyed.get()) {
                    SystemClock.sleep(5000);

                    int bookId = mBookList.size() + 1;
                    Book book = new Book(bookId, "book#" + bookId);

                    try {
                        mBookList.add(book);
                        int count = mListenerList.beginBroadcast();
                        for (int i = 0; i < count; i++) {
                            IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
                            if (listener != null) {
                                listener.onNewBookArrived(book);
                            }
                        }
                        mListenerList.finishBroadcast();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // 验证权限
        int check = checkCallingOrSelfPermission("com.mjiayou.trecoredemo.permission.ACCESS_BOOK_SERVICE");
        if (check == PackageManager.PERMISSION_DENIED) {
            LogUtil.i(TAG, "check == PackageManager.PERMISSION_DENIED");
            return null;
        }

        return mBinder;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed.set(true);
        super.onDestroy();
    }
}
