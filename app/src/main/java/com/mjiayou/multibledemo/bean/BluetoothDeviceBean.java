package com.mjiayou.multibledemo.bean;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;

import java.util.Arrays;

/**
 * Created by treason on 2017/1/1.
 */

public class BluetoothDeviceBean {

    private BluetoothGatt bluetoothGatt;
    private BluetoothDevice bluetoothDevice;

    private String name;
    private String address;
    private int rssi;
    private byte[] record;

    public BluetoothDeviceBean(String name, String address, int rssi, byte[] record, BluetoothDevice bluetoothDevice) {
        this.name = name;
        this.address = address;
        this.rssi = rssi;
        this.record = record;
        this.bluetoothDevice = bluetoothDevice;
    }

    @Override
    public String toString() {
        return name + " -> " + address + " -> " + rssi;
    }

    public String toString(boolean showRecord) {
        return name + " -> " + address + " -> " + rssi + " -> " + Arrays.toString(record) + " -> " + bluetoothDevice.toString();
    }

    public BluetoothGatt getBluetoothGatt() {
        return bluetoothGatt;
    }

    public void setBluetoothGatt(BluetoothGatt bluetoothGatt) {
        this.bluetoothGatt = bluetoothGatt;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public byte[] getRecord() {
        return record;
    }

    public void setRecord(byte[] record) {
        this.record = record;
    }
}
