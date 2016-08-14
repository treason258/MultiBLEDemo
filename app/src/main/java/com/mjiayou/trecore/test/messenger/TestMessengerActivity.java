package com.mjiayou.trecore.test.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.mjiayou.trecore.TCActivity;
import com.mjiayou.trecoredemo.R;

public class TestMessengerActivity extends TCActivity {

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Messenger messenger = new Messenger(service);
            Message message = Message.obtain(null, MessengerService.MSG_FROM_CLIENT);
            Bundle bundle = new Bundle();
            bundle.putString(MessengerService.BUNDLE_MSG, "hello, this is client!");
            message.setData(bundle);
            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

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
