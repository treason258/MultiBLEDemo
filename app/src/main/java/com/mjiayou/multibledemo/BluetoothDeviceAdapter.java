package com.mjiayou.multibledemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mjiayou.multibledemo.bean.BluetoothDeviceBean;

import java.util.List;

/**
 * Created by treason on 2017/1/2.
 */

public class BluetoothDeviceAdapter extends BaseAdapter {

    private Context mContext;
    private List<BluetoothDeviceBean> mList;

    public BluetoothDeviceAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public void setList(List<BluetoothDeviceBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }
}
