package com.mjiayou.trecore.test.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.mjiayou.trecore.TCService;
import com.mjiayou.trecore.util.LogUtil;

/**
 * Created by treason on 16/8/14.
 */
public class MessengerService extends TCService {

    public static final int MESSAGE_FROM_CLIENT = 0;
    public static final int MESSAGE_FROM_SERVER = 1;
    public static final String BUNDLE_MESSAGE_FROM_CLIENT = "bundle_msg_from_client";
    public static final String BUNDLE_MESSAGE_FROM_SERVER = "bundle_msg_from_server";

    private final Messenger mServerMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_FROM_CLIENT: {
                    LogUtil.i(TAG, "receive msg from Client -> " + msg.getData().get(BUNDLE_MESSAGE_FROM_CLIENT));
                    Messenger clientMessenger = msg.replyTo;
                    Message message = Message.obtain(null, MESSAGE_FROM_SERVER);
                    Bundle bundle = new Bundle();
                    bundle.putString(BUNDLE_MESSAGE_FROM_SERVER, "hello, this is server! Get it!");
                    message.setData(bundle);
                    try {
                        clientMessenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                default:
                    super.handleMessage(msg);
            }
        }
    });

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mServerMessenger.getBinder();
    }
}
