package com.mjiayou.multibledemo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mjiayou.multibledemo.bean.BluetoothDeviceBean;
import com.mjiayou.multibledemo.ble.BleAdapter;
import com.mjiayou.multibledemo.ble.BleAdapterCallbacks;
import com.mjiayou.multibledemo.util.ConvertUtil;
import com.mjiayou.multibledemo.widget.FitListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private static final String DEVICE_FILTER_NAME = "E3-A";
    private static final long SCANNING_TIMEOUT = 12 * 1000; /* 5 seconds */
    private static final int REQUEST_ENABLE_BLUETOOTH = 1;

    private Button mBtnScanDevice;
    private FitListView mLvDevice;
    private Button mBtnCallDevice;
    private TextView mTvInfo;

    private Activity mActivity;
    private Context mContext;

    private ArrayAdapter<String> mArrayAdapter;
    private List<String> mDeviceList = new ArrayList<>();

    private BleAdapter mBleAdapter = null;

    private boolean mBleInitialized = false;
    private boolean mScanning = false;

    private StringBuilder mInfoBuilder = new StringBuilder();

    private List<String> mDeviceAddressList = new ArrayList<>();
    private String mCurrentDeviceAddress = "";

    private boolean mRing; // 呼叫设备

    private BluetoothGattCharacteristic mRingDeviceCharacteristic = null;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;
        mContext = this;

        // findViewById
        mBtnScanDevice = (Button) findViewById(R.id.btn_scan_device);
        mLvDevice = (FitListView) findViewById(R.id.lv_device);
        mBtnCallDevice = (Button) findViewById(R.id.btn_call_device);
        mTvInfo = (TextView) findViewById(R.id.tv_info);

        // mLvDevice
        mArrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, mDeviceList);
        mLvDevice.setAdapter(mArrayAdapter);
        mLvDevice.setOnItemClickListener(mOnItemClickListener);

        // mBtnScanDevice
        mBtnScanDevice.setOnClickListener(mOnClickListener);

        // mBtnCallDevice
        mBtnCallDevice.setOnClickListener(mOnClickListener);

        // mTvInfo
        mTvInfo.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvInfo.setText("onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateInfo("onResume");

        initBle();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // user didn't want to turn on BT
        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    updateInfo("用户同意了开启蓝牙功能的请求");
                    break;
                case Activity.RESULT_CANCELED:
                    updateInfo("用户拒绝了开启蓝牙功能的请求");
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initBle() {
        updateInfo("initBle");

        // create BleWrapper with empty callback object except uiDeficeFound function (we need only that here)
        if (mBleAdapter == null) {
            mBleAdapter = new BleAdapter(mActivity, mBleAdapterCallbacks);
        }

        // check if we have BT and BLE on board
        if (!mBleAdapter.checkBleHardwareAvailable()) {
            updateInfo("设备不支持蓝牙功能！");
            return;
        } else {
            updateInfo("设备支持蓝牙功能");
        }

        // on every Resume check if BT is enabled (user could turn it off while app was in background etc.)
        if (!mBleAdapter.checkBluetoothEnabled()) {
            updateInfo("蓝牙未开启，请求开启蓝牙");
            // BT is not turned on - ask user to make it enabled
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BLUETOOTH);
            // see onActivityResult to check what is the status of our request
            return;
        } else {
            updateInfo("蓝牙已开启");
        }

        // initialize BleWrapper object
        mBleInitialized = mBleAdapter.initialize();
        updateInfo("mBleInitialized -> " + mBleInitialized);
    }

    /**
     * mOnClickListener
     */
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_scan_device: {
                    updateInfo("btn_scan_device");
                    updateInfo("mBleInitialized -> " + mBleInitialized);
                    if (mBleInitialized) {
                        mBleAdapter.startScanning();
                        mScanning = true;
                    }
                    break;
                }
                case R.id.btn_call_device: {
                    updateInfo("btn_call_device");
                    updateInfo("mBleInitialized -> " + mBleInitialized);
                    if (mBleInitialized) {
                        if (mBleAdapter == null) {
                            updateInfo("mBleAdapter == null");
                            return;
                        }

                        BluetoothGatt bluetoothGatt = mBleAdapter.getBluetoothGattFromList(mCurrentDeviceAddress);
                        if (bluetoothGatt == null) {
                            updateInfo("bluetoothGatt == null");
                            return;
                        }

                        BluetoothGattCharacteristic ringDeviceCharacteristic = ConvertUtil.getCharacteristic(bluetoothGatt.getServices(), "00001802", "00002a06");
                        if (ringDeviceCharacteristic == null) {
                            updateInfo("ringDeviceCharacteristic == null");
                            return;
                        }

                        String newValue;
                        if (mRing) { // 正在响铃，需要停止
                            newValue = "0x00";
                            mRing = false;
                            mBtnCallDevice.setText("CALL DEVICE");
                        } else { // 没有响铃，启动响铃
                            newValue = "0x02";
                            mRing = true;
                            mBtnCallDevice.setText("STOP CALLING");
                        }

                        byte[] dataToWrite = ConvertUtil.parseHexStringToBytes(newValue);
                        mBleAdapter.writeDataToCharacteristic(bluetoothGatt, ringDeviceCharacteristic, dataToWrite);
                    }
                    break;
                }
            }
        }
    };

    /**
     * mBleAdapterCallbacks
     */
    private BleAdapterCallbacks mBleAdapterCallbacks = new BleAdapterCallbacks() {
        @Override
        public void onDeviceFound(final BluetoothDevice device, final int rssi, final byte[] record) {
//            updateInfo("onDeviceFound | device.getAddress() -> " + device.getAddress() + " | rssi -> " + rssi);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (device != null && device.getName() != null && device.getName().contains(DEVICE_FILTER_NAME)) {
                        if (!mDeviceAddressList.contains(device.getAddress())) {
                            mDeviceAddressList.add(device.getAddress());

                            BluetoothDeviceBean deviceBean = new BluetoothDeviceBean(device.getName(), device.getAddress(), rssi, record, device);
                            updateInfo("发现新设备 | " + deviceBean.toString());
                            mDeviceList.add(deviceBean.getAddress());
                            mArrayAdapter.notifyDataSetChanged();

                            mBleAdapter.stopScanning();
                        }
                    }
                }
            });
        }

        @Override
        public void onDeviceConnected(BluetoothGatt gatt, BluetoothDevice device) {
            updateInfo("onDeviceConnected | device.getAddress() -> " + device.getAddress());
            mCurrentDeviceAddress = device.getAddress();
        }

        @Override
        public void onDeviceReconnected(BluetoothGatt gatt, BluetoothDevice device) {
            updateInfo("onDeviceReconnected | device.getAddress() -> " + device.getAddress());
            mCurrentDeviceAddress = device.getAddress();
        }

        @Override
        public void onDeviceDisconnected(BluetoothGatt gatt, BluetoothDevice device) {
            updateInfo("onDeviceDisconnected | device.getAddress() -> " + device.getAddress());
        }

        @Override
        public void onNewRssiAvailable(BluetoothGatt gatt, BluetoothDevice device, int rssi) {
            updateInfo("onNewRssiAvailable | device.getAddress() -> " + device.getAddress() + " | rssi -> " + rssi);
        }

        @Override
        public void onAvailableServices(BluetoothGatt gatt, BluetoothDevice device, List<BluetoothGattService> services) {
            if (device == null) {
                updateInfo("onAvailableServices | device == null");
                return;
            }
            if (services == null) {
                updateInfo("onAvailableServices | services == null");
                return;
            }
            updateInfo("onAvailableServices | device.getAddress() -> " + device.getAddress());

            // ringPhone
            BluetoothGattCharacteristic ringPhoneCharacteristic = ConvertUtil.getCharacteristic(services, "0000ffe0", "0000ffe1");
            if (ringPhoneCharacteristic == null) {
                updateInfo("ringPhoneCharacteristic == null");
                return;
            }
            mBleAdapter.setNotificationForCharacteristic(gatt, ringPhoneCharacteristic, true);
        }

        @Override
        public void onGotNotification(BluetoothGatt gatt, BluetoothDevice device, BluetoothGattService service, BluetoothGattCharacteristic characteristic) {
            updateInfo("onGotNotification | device.getAddress() -> " + device.getAddress());
        }

        // TEMP

        @Override
        public void uiCharacteristicForService(BluetoothGatt gatt, BluetoothDevice device, BluetoothGattService service, List<BluetoothGattCharacteristic> chars) {

        }

        @Override
        public void uiCharacteristicsDetails(BluetoothGatt gatt, BluetoothDevice device, BluetoothGattService service, BluetoothGattCharacteristic characteristic) {

        }

        @Override
        public void uiNewValueForCharacteristic(BluetoothGatt gatt, BluetoothDevice device, BluetoothGattService service, BluetoothGattCharacteristic ch, String strValue, int intValue, byte[] rawValue, String timestamp) {

        }

        @Override
        public void uiSuccessfulWrite(BluetoothGatt gatt, BluetoothDevice device, BluetoothGattService service, BluetoothGattCharacteristic ch, String description) {

        }

        @Override
        public void uiFailedWrite(BluetoothGatt gatt, BluetoothDevice device, BluetoothGattService service, BluetoothGattCharacteristic ch, String description) {

        }
    };

    /**
     * mOnItemClickListener
     */
    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            updateInfo("onItemClick -> " + mDeviceList.get(position));
            mCurrentDeviceAddress = mDeviceAddressList.get(position);
            updateInfo("mCurrentDeviceAddress -> " + mCurrentDeviceAddress);
            mBleAdapter.connect(mCurrentDeviceAddress);
        }
    };

    /**
     * updateInfo
     *
     * @param info
     */
    private void updateInfo(final String info) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                ToastUtil.show(mContext, info);
                mTvInfo.setText(mInfoBuilder.append("\n\n").append(info).toString());

                int offset = mTvInfo.getLineCount() * mTvInfo.getLineHeight();
                if (offset > mTvInfo.getHeight()) {
                    mTvInfo.scrollTo(0, offset - mTvInfo.getHeight());
                }
            }
        });
    }
}
