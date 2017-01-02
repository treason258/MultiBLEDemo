package com.mjiayou.multibledemo.ble;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;

import com.mjiayou.multibledemo.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by treason on 2017/1/1.
 */

public class BleAdapter {

    public static final String TAG = BleAdapter.class.getSimpleName();

    private Activity mActivity = null;

    private BluetoothManager mBluetoothManager = null;
    private BluetoothAdapter mBluetoothAdapter = null;
    private List<BluetoothGatt> mBluetoothGattList = new ArrayList<>();
    private List<String> mDeviceAddressList = new ArrayList<>();

    private BluetoothAdapter.LeScanCallback mLeScanCallback;
    private BluetoothGattCallback mBluetoothGattCallback;

    /**
     * 优化API>21系统，使用更新的扫描方法
     */
    private static boolean mSupportAPI21 = false;
    private ScanCallback mScanCallback;

    /**
     * callback object through which we are returning results to the caller
     */
    private BleAdapterCallbacks mCallback = null;

    /**
     * creates BleWrapper object, set its parent activity and callback object
     *
     * @param activity
     * @param callback
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BleAdapter(Activity activity, BleAdapterCallbacks callback) {
        LogUtil.i(TAG, "public BleAdapter");

        mActivity = activity;
        mCallback = callback;
        if (mCallback == null) {
            mCallback = new BleAdapterCallbacks.Empty();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSupportAPI21 = true;
        } else {
            mSupportAPI21 = false;
        }

        LogUtil.i(TAG, "public BleAdapter | mSupportAPI21 -> " + mSupportAPI21);
        if (mSupportAPI21) {
            mScanCallback = new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    mCallback.onDeviceFound(result.getDevice(), result.getRssi(), result.getScanRecord().getBytes());
                }

                @Override
                public void onBatchScanResults(List<ScanResult> results) {
                }

                @Override
                public void onScanFailed(int errorCode) {
                }
            };
        } else {
            mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                    mCallback.onDeviceFound(device, rssi, scanRecord);
                }
            };
        }

        mBluetoothGattCallback = new BluetoothGattCallback() {
            @Override
            public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int status, int newState) {
                super.onConnectionStateChange(bluetoothGatt, status, newState);
                LogUtil.i(TAG, "onConnectionStateChange | status -> " + status + " | newState -> " + newState);

                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    // 如果当前 deviceAddress 不存在于 mDeviceAddressList 则 add；否则不处理
                    String deviceAddress = bluetoothGatt.getDevice().getAddress();
                    if (!checkExistInDeviceAddressList(deviceAddress)) {
                        mDeviceAddressList.add(deviceAddress);
                    }
                    // 如果当前 bluetoothGatt 不存在于 mBluetoothGattList 则 add；否则不处理
                    if (!checkExistInBluetoothGattList(bluetoothGatt)) {
                        mBluetoothGattList.add(bluetoothGatt);
                    }

                    // 连接成功
                    mCallback.onDeviceConnected(bluetoothGatt, bluetoothGatt.getDevice());

                    // 搜索服务
                    bluetoothGatt.discoverServices();

                    // 监控RSSI
                    startMonitoringRssiValue(bluetoothGatt);
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
//                    // 如果当前 deviceAddress 存在于 mDeviceAddressList 则 remove；否则不处理
//                    String deviceAddress = bluetoothGatt.getDevice().getAddress();
//                    if (checkExistInDeviceAddressList(deviceAddress)) {
//                        mDeviceAddressList.remove(deviceAddress);
//                    }
//                    // 如果当前 bluetoothGatt 存在于 mBluetoothGattList 则 remove；否则不处理
//                    BluetoothGatt bluetoothGattTemp = getBluetoothGattFromList(deviceAddress);
//                    if (checkExistInBluetoothGattList(bluetoothGattTemp)) {
//                        mBluetoothGattList.remove(bluetoothGattTemp);
//                    }

                    // 断开连接成功
                    mCallback.onDeviceDisconnected(bluetoothGatt, bluetoothGatt.getDevice());

                    try {
                        close(bluetoothGatt.getDevice().getAddress());
                    } catch (Exception e) {
                        LogUtil.i(TAG, "onConnectionStateChange | close Exception");
                        LogUtil.printStackTrace(e);
                    }
                }
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                super.onServicesDiscovered(gatt, status);
                LogUtil.i(TAG, "onServicesDiscovered | status -> " + status);

                if (status == BluetoothGatt.GATT_SUCCESS) {
                    if (gatt == null) {
                        LogUtil.i(TAG, "gatt == null");
                        return;
                    }
                    // 更新 mBluetoothGattList 中对应的 BluetoothGatt 对象
                    String deviceAddress = gatt.getDevice().getAddress();
                    int index = mBluetoothGattList.indexOf(getBluetoothGattFromList(deviceAddress));
                    mBluetoothGattList.set(index, gatt);

                    mCallback.onAvailableServices(gatt, gatt.getDevice(), gatt.getServices());
                }
            }

            @Override
            public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onCharacteristicRead(gatt, characteristic, status);
                LogUtil.i(TAG, "onCharacteristicRead | status -> " + status);
            }

            @Override
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onCharacteristicWrite(gatt, characteristic, status);
                LogUtil.i(TAG, "onCharacteristicWrite | status -> " + status);
            }

            @Override
            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                super.onCharacteristicChanged(gatt, characteristic);
                LogUtil.i(TAG, "onCharacteristicChanged");

                mCallback.onGotNotification(gatt, gatt.getDevice(), characteristic.getService(), characteristic);
            }

            @Override
            public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                super.onDescriptorRead(gatt, descriptor, status);
                LogUtil.i(TAG, "onDescriptorRead | status -> " + status);
            }

            @Override
            public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                super.onDescriptorWrite(gatt, descriptor, status);
                LogUtil.i(TAG, "onDescriptorWrite | status -> " + status);
            }

            @Override
            public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
                super.onReliableWriteCompleted(gatt, status);
                LogUtil.i(TAG, "onReliableWriteCompleted | status -> " + status);
            }

            @Override
            public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
                super.onReadRemoteRssi(gatt, rssi, status);
                LogUtil.i(TAG, "onReadRemoteRssi | status -> " + status + " | rssi -> " + rssi);

                if (status == BluetoothGatt.GATT_SUCCESS) {
                    // we got new value of RSSI of the connection, pass it to the UI
                    mCallback.onNewRssiAvailable(gatt, gatt.getDevice(), rssi);
                }
            }

            @Override
            public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
                super.onMtuChanged(gatt, mtu, status);
                LogUtil.i(TAG, "onMtuChanged | status -> " + status + " | mtu -> " + mtu);
            }
        };
    }

    /**
     * run test and check if this device has BT and BLE hardware available
     *
     * @return
     */
    public boolean checkBleHardwareAvailable() {
        LogUtil.i(TAG, "checkBleHardwareAvailable");

        // First check general Bluetooth Hardware:
        // get BluetoothManager ...
        final BluetoothManager manager = (BluetoothManager) mActivity.getSystemService(Context.BLUETOOTH_SERVICE);
        if (manager == null) {
            return false;
        }

        // ... and then get adapter from manager
        final BluetoothAdapter adapter = manager.getAdapter();
        if (adapter == null) {
            return false;
        }

        // ... and then check if BT LE is also available
        boolean hasBle = mActivity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
        return hasBle;
    }

    /**
     * before any action check if BT is turned ON and enabled for us
     * call this in onResume to be always sure that BT is ON when Your
     * application is put into the foreground
     *
     * @return
     */
    public boolean checkBluetoothEnabled() {
        LogUtil.i(TAG, "checkBluetoothEnabled");

        final BluetoothManager manager = (BluetoothManager) mActivity.getSystemService(Context.BLUETOOTH_SERVICE);
        if (manager == null) {
            return false;
        }

        final BluetoothAdapter adapter = manager.getAdapter();
        if (adapter == null) {
            return false;
        }

        return adapter.isEnabled();
    }

    /**
     * initialize BLE and get BT Manager & Adapter
     *
     * @return
     */
    public boolean initialize() {
        LogUtil.i(TAG, "initialize");

        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) mActivity.getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                return false;
            }
        }

        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = mBluetoothManager.getAdapter();
            if (mBluetoothAdapter == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * start scanning for BT LE devices around
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void startScanning() {
        LogUtil.i(TAG, "startScanning | mSupportAPI21 -> " + mSupportAPI21);

        if (mSupportAPI21) {
            mBluetoothAdapter.getBluetoothLeScanner().startScan(null, new ScanSettings.Builder().build(), mScanCallback);
        } else {
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        }
    }

    /**
     * stops current scanning
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void stopScanning() {
        LogUtil.i(TAG, "stopScanning | mSupportAPI21 -> " + mSupportAPI21);

        if (mSupportAPI21) {
            mBluetoothAdapter.getBluetoothLeScanner().stopScan(mScanCallback);
        } else {
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    /**
     * connect to the device with specified address
     *
     * @param deviceAddress
     * @return
     */
    public boolean connect(final String deviceAddress) {
        LogUtil.i(TAG, "connect | deviceAddress -> " + deviceAddress);

        if (mBluetoothAdapter == null || TextUtils.isEmpty(deviceAddress)) {
            return false;
        }

        // 根据 deviceAddress 获取 mBluetoothGattList 中对应的 BluetoothGatt 对象
        BluetoothGatt bluetoothGatt = getBluetoothGattFromList(deviceAddress);

        // 如果 bluetoothGatt 为空，表示 mBluetoothGattList 中不存在地址为 deviceAddress 的 BluetoothGatt 对象，则全新建立连接；否则只需要重连即可
        if (bluetoothGatt == null) {
            BluetoothDevice bluetoothDevice = mBluetoothAdapter.getRemoteDevice(deviceAddress);
            if (bluetoothDevice == null) {
                return false;
            }
            bluetoothGatt = bluetoothDevice.connectGatt(mActivity, true, mBluetoothGattCallback);
            if (bluetoothGatt != null) {
                return true;
            } else {
                return false;
            }
        } else {
            boolean connect = bluetoothGatt.connect();
            if (connect) {
                // 重连成功
                mCallback.onDeviceReconnected(bluetoothGatt, bluetoothGatt.getDevice());
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * disconnect the device. It is still possible to reconnect to it later with this Gatt client
     *
     * @param deviceAddress
     */
    public boolean disconnect(String deviceAddress) {
        LogUtil.i(TAG, "disconnect | deviceAddress -> " + deviceAddress);

        // 根据 deviceAddress 获取 mBluetoothGattList 中对应的 BluetoothGatt 对象
        BluetoothGatt bluetoothGatt = getBluetoothGattFromList(deviceAddress);

        // 如果 bluetoothGatt 为空，表示 mBluetoothGattList 中不存在地址为 deviceAddress 的 BluetoothGatt 对象，则断开失败；否则执行断开操作
        if (bluetoothGatt == null) {
            return false;
        } else {
            bluetoothGatt.disconnect();
            return true;
        }
    }

    /**
     * close GATT client completely
     */
    private boolean close(String deviceAddress) {
        LogUtil.i(TAG, "close | deviceAddress -> " + deviceAddress);

        // 根据 deviceAddress 获取 mBluetoothGattList 中对应的 BluetoothGatt 对象
        BluetoothGatt bluetoothGatt = getBluetoothGattFromList(deviceAddress);

        // 如果 bluetoothGatt 为空，表示 mBluetoothGattList 中不存在地址为 deviceAddress 的 BluetoothGatt 对象，则关闭失败；否则执行关闭操作
        if (bluetoothGatt == null) {
            return false;
        } else {
            bluetoothGatt.close();
            return true;
        }
    }

    /**
     * enables/disables notification for characteristic
     */
    public void setNotificationForCharacteristic(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic characteristic, boolean enabled) {
        if (mBluetoothAdapter == null) {
            LogUtil.i(TAG, "setNotificationForCharacteristic | mBluetoothAdapter == null");
            return;
        }
        if (bluetoothGatt == null) {
            LogUtil.i(TAG, "setNotificationForCharacteristic | bluetoothGatt == null");
            return;
        }

        boolean success = bluetoothGatt.setCharacteristicNotification(characteristic, enabled);
        LogUtil.i(TAG, "setNotificationForCharacteristic | success -> " + success);

        // This is also sometimes required (e.g. for heart rate monitors) to enable notifications/indications
        // see: https://developer.bluetooth.org/gatt/descriptors/Pages/DescriptorViewer.aspx?u=org.bluetooth.descriptor.gatt.client_characteristic_configuration.xml
        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
        if (descriptor != null) {
            byte[] val = enabled ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
            descriptor.setValue(val);
            bluetoothGatt.writeDescriptor(descriptor);
        }
    }

    /**
     * set new value for particular characteristic
     */
    public void writeDataToCharacteristic(BluetoothGatt bluetoothGatt, final BluetoothGattCharacteristic characteristic, final byte[] dataToWrite) {
        if (mBluetoothAdapter == null || bluetoothGatt == null || characteristic == null) return;

        // first set it locally....
        characteristic.setValue(dataToWrite);
        // ... and then "commit" changes to the peripheral
        bluetoothGatt.writeCharacteristic(characteristic);
    }

    // ******************************** check & get ********************************

    /**
     * 检测 deviceAddress 是否存在于 mDeviceAddressList
     *
     * @param deviceAddress
     * @return
     */
    private boolean checkExistInDeviceAddressList(String deviceAddress) {
        LogUtil.i(TAG, "checkExistInDeviceAddressList | deviceAddress -> " + deviceAddress);

//        if (mDeviceAddressList != null && !mDeviceAddressList.isEmpty()) {
//            for (String deviceAddressItem : mDeviceAddressList) {
//                if (deviceAddressItem != null && deviceAddressItem.equals(deviceAddress)) {
//                    return true;
//                }
//            }
//        }
//        return false;
        return mDeviceAddressList.contains(deviceAddress);
    }

    /**
     * 检测 bluetoothGatt 是否已存在于 mBluetoothGattList
     *
     * @param bluetoothGatt
     * @return
     */
    private boolean checkExistInBluetoothGattList(BluetoothGatt bluetoothGatt) {
        LogUtil.i(TAG, "checkExistInBluetoothGattList | bluetoothGatt.getDevice().getAddress() -> " + bluetoothGatt.getDevice().getAddress());

        if (mBluetoothGattList != null && !mBluetoothGattList.isEmpty()) {
            for (BluetoothGatt bluetoothGattItem : mBluetoothGattList) {
                if (bluetoothGattItem != null && bluetoothGattItem.equals(bluetoothGatt)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 返回 mBluetoothGattList
     *
     * @return
     */
    public List<BluetoothGatt> getBluetoothGattList() {
        return mBluetoothGattList;
    }

    /**
     * 根据 deviceAddress 获取 mBluetoothGattList 中对应的 BluetoothGatt 对象，如果 mBluetoothGattList 中不存在则返回 null
     *
     * @param deviceAddress
     * @return
     */
    public BluetoothGatt getBluetoothGattFromList(String deviceAddress) {
        LogUtil.i(TAG, "getBluetoothGattFromList | deviceAddress -> " + deviceAddress);

        if (mBluetoothGattList != null && !mBluetoothGattList.isEmpty()) {
            for (BluetoothGatt bluetoothGattItem : mBluetoothGattList) {
                if (bluetoothGattItem != null && bluetoothGattItem.getDevice().getAddress().equals(deviceAddress)) {
                    return bluetoothGattItem;
                }
            }
        }
        return null;
    }

    public BluetoothManager getBluetoothManager() {
        return mBluetoothManager;
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return mBluetoothAdapter;
    }

    // ******************************** startMonitoringRssiValue ********************************

    private static final int RSSI_UPDATE_TIME_INTERVAL = 1500; // 1.5 seconds
    private Handler mTimerHandler = new Handler();
    private Runnable mRunnable = null;
    private boolean mTimerEnabled = false;

    /**
     * starts monitoring RSSI value
     */
    private void startMonitoringRssiValue(BluetoothGatt bluetoothGatt) {
        LogUtil.i(TAG, "startMonitoringRssiValue | bluetoothGatt.getDevice().getAddress() -> " + bluetoothGatt.getDevice().getAddress());

        if (mTimerHandler != null && mRunnable != null) {
            mTimerHandler.removeCallbacks(mRunnable);
            mRunnable = null;
        }
        bluetoothGatt.readRemoteRssi();
        readRssiValuePeriodically(bluetoothGatt, true);
    }

    /**
     * stops monitoring of RSSI value
     */
    public void stopMonitoringRssiValue(BluetoothGatt bluetoothGatt) {
        LogUtil.i(TAG, "stopMonitoringRssiValue | bluetoothGatt.getDevice().getAddress() -> " + bluetoothGatt.getDevice().getAddress());

        readRssiValuePeriodically(bluetoothGatt, false);
    }

    /**
     * request new RSSi value for the connection
     */
    private void readRssiValuePeriodically(final BluetoothGatt bluetoothGatt, final boolean repeat) {
        LogUtil.i(TAG, "readRssiValuePeriodically | bluetoothGatt.getDevice().getAddress() -> " + bluetoothGatt.getDevice().getAddress());

        mTimerEnabled = repeat;
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mBluetoothAdapter == null || bluetoothGatt == null || mTimerEnabled == false) {
                    LogUtil.i(TAG, "mBluetoothAdapter == null || bluetoothGatt == null || mTimerEnabled == false");
                    mTimerEnabled = false;
                    return;
                }

                // request RSSI value
                bluetoothGatt.readRemoteRssi();
                // add call it once more in the future
                readRssiValuePeriodically(bluetoothGatt, mTimerEnabled);
            }
        };

        mTimerHandler.postDelayed(mRunnable, RSSI_UPDATE_TIME_INTERVAL);
    }
}
