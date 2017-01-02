package com.mjiayou.multibledemo.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;

import java.util.List;

public interface BleAdapterCallbacks {

    public void onDeviceFound(final BluetoothDevice device, int rssi, byte[] record);

    public void onDeviceConnected(final BluetoothGatt gatt, final BluetoothDevice device);

    public void onDeviceReconnected(final BluetoothGatt gatt, final BluetoothDevice device);

    public void onDeviceDisconnected(final BluetoothGatt gatt, final BluetoothDevice device);

    public void onNewRssiAvailable(final BluetoothGatt gatt, final BluetoothDevice device, final int rssi);

    public void onAvailableServices(final BluetoothGatt gatt, final BluetoothDevice device, final List<BluetoothGattService> services);

    public void onGotNotification(final BluetoothGatt gatt, final BluetoothDevice device, final BluetoothGattService service, final BluetoothGattCharacteristic characteristic);

    // TEMP

    public void uiCharacteristicForService(final BluetoothGatt gatt, final BluetoothDevice device, final BluetoothGattService service, final List<BluetoothGattCharacteristic> chars);

    public void uiCharacteristicsDetails(final BluetoothGatt gatt, final BluetoothDevice device, final BluetoothGattService service, final BluetoothGattCharacteristic characteristic);

    public void uiNewValueForCharacteristic(final BluetoothGatt gatt,
                                            final BluetoothDevice device,
                                            final BluetoothGattService service,
                                            final BluetoothGattCharacteristic ch,
                                            final String strValue,
                                            final int intValue,
                                            final byte[] rawValue,
                                            final String timestamp);

    public void uiSuccessfulWrite(final BluetoothGatt gatt,
                                  final BluetoothDevice device,
                                  final BluetoothGattService service,
                                  final BluetoothGattCharacteristic ch,
                                  final String description);

    public void uiFailedWrite(final BluetoothGatt gatt,
                              final BluetoothDevice device,
                              final BluetoothGattService service,
                              final BluetoothGattCharacteristic ch,
                              final String description);

    /**
     * define Empty Adapter class for that interface
     */
    public static class Empty implements BleAdapterCallbacks {

        @Override
        public void onDeviceFound(BluetoothDevice device, int rssi, byte[] record) {
        }

        @Override
        public void onDeviceConnected(BluetoothGatt gatt, BluetoothDevice device) {
        }

        @Override
        public void onDeviceReconnected(BluetoothGatt gatt, BluetoothDevice device) {
        }

        @Override
        public void onDeviceDisconnected(BluetoothGatt gatt, BluetoothDevice device) {
        }

        @Override
        public void onNewRssiAvailable(BluetoothGatt gatt, BluetoothDevice device, int rssi) {
        }

        @Override
        public void onAvailableServices(BluetoothGatt gatt, BluetoothDevice device, List<BluetoothGattService> services) {
        }

        @Override
        public void onGotNotification(BluetoothGatt gatt, BluetoothDevice device, BluetoothGattService service, BluetoothGattCharacteristic characteristic) {
        }

        // TEMP

        @Override
        public void uiCharacteristicForService(BluetoothGatt gatt,
                                               BluetoothDevice device, BluetoothGattService service,
                                               List<BluetoothGattCharacteristic> chars) {
        }

        @Override
        public void uiCharacteristicsDetails(BluetoothGatt gatt,
                                             BluetoothDevice device, BluetoothGattService service,
                                             BluetoothGattCharacteristic characteristic) {
        }

        @Override
        public void uiNewValueForCharacteristic(BluetoothGatt gatt,
                                                BluetoothDevice device, BluetoothGattService service,
                                                BluetoothGattCharacteristic ch, String strValue, int intValue,
                                                byte[] rawValue, String timestamp) {
        }

        @Override
        public void uiSuccessfulWrite(BluetoothGatt gatt, BluetoothDevice device,
                                      BluetoothGattService service, BluetoothGattCharacteristic ch,
                                      String description) {
        }

        @Override
        public void uiFailedWrite(BluetoothGatt gatt, BluetoothDevice device,
                                  BluetoothGattService service, BluetoothGattCharacteristic ch,
                                  String description) {
        }

    }
}
