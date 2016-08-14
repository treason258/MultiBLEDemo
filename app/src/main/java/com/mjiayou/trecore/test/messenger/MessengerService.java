package com.mjiayou.trecore.test.messenger;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;

import com.mjiayou.trecore.TCService;
import com.mjiayou.trecore.util.LogUtil;

/**
 * Created by treason on 16/8/14.
 */
public class MessengerService extends TCService {

    private final String TAG = "MessengerService";

    public static final int MSG_FROM_CLIENT = 0;
    public static final int MSG_FROM_SERVER = 1;
    public static final String BUNDLE_MSG = "msg";

    private final Messenger mMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FROM_CLIENT: {
                    LogUtil.i(TAG, "receive msg from Client -> " + msg.getData().get(BUNDLE_MSG));
                    break;
                }
                default: {
                    super.handleMessage(msg);
                }
            }
        }
    });

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
