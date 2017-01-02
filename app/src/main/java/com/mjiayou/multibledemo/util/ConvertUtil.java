package com.mjiayou.multibledemo.util;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.List;
import java.util.Locale;

/**
 * Created by treason on 16/5/14.
 */
public class ConvertUtil {

    public static final String TAG = ConvertUtil.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static BluetoothGattCharacteristic getCharacteristic(List<BluetoothGattService> services, String sericeUUID, String characteristicUUID) {
        BluetoothGattService bluetoothGattService = null;
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).getUuid().toString().toLowerCase(Locale.getDefault()).contains(sericeUUID)) {
                bluetoothGattService = services.get(i);
                break;
            }
        }

        if (bluetoothGattService == null) {
            return null;
        }

        List<BluetoothGattCharacteristic> bluetoothGattCharacteristicList = bluetoothGattService.getCharacteristics();
        if (bluetoothGattCharacteristicList == null) {
            return null;
        }

        BluetoothGattCharacteristic bluetoothGattCharacteristic = null;
        for (int i = 0; i < bluetoothGattCharacteristicList.size(); i++) {
            if (bluetoothGattCharacteristicList.get(i).getUuid().toString().toLowerCase(Locale.getDefault()).contains(characteristicUUID)) {
                bluetoothGattCharacteristic = bluetoothGattCharacteristicList.get(i);
                break;
            }
        }

        if (bluetoothGattCharacteristic == null) {
            return null;
        }

        return bluetoothGattCharacteristic;
    }

    public static byte[] parseHexStringToBytes(final String hex) {
        String tmp = hex.substring(2).replaceAll("[^[0-9][a-f]]", "");
        byte[] bytes = new byte[tmp.length() / 2]; // every two letters in the string are one byte finally

        String part = "";

        for (int i = 0; i < bytes.length; ++i) {
            part = "0x" + tmp.substring(i * 2, i * 2 + 2);
            bytes[i] = Long.decode(part).byteValue();
        }

        return bytes;
    }
}
