package com.mjiayou.trecore.test.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.mjiayou.trecore.TCActivity;
import com.mjiayou.trecore.util.LogUtil;
import com.mjiayou.trecoredemo.R;

public class TestMessengerActivity extends TCActivity {

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.i(TAG, "onServiceConnected");

            Messenger serverMessenger = new Messenger(service);
            Message message = Message.obtain(null, MessengerService.MESSAGE_FROM_CLIENT);
            Bundle bundle = new Bundle();
            bundle.putString(MessengerService.BUNDLE_MESSAGE_FROM_CLIENT, "hello, this is client!");
            message.setData(bundle);
            message.replyTo = new Messenger(new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case MessengerService.MESSAGE_FROM_SERVER: {
                            LogUtil.i(TAG, "receive msg from server -> " + msg.getData().get(MessengerService.BUNDLE_MESSAGE_FROM_SERVER));
                            break;
                        }
                        default:
                            super.handleMessage(msg);
                    }
                }
            });
            try {
                serverMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.i(TAG, "onServiceDisconnected");
        }
    };

    /**
     * startActivity
     */
    public static void open(Context context) {
        Intent intent = new Intent(context, TestMessengerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_messenger);

        setTitle(TAG);

        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }
}
