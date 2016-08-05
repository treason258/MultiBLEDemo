package com.mjiayou.trecore.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by treason on 15/3/17.
 */
public abstract class BaseSimpleAdapter<T> extends BaseAdapter<T> {

    public BaseSimpleAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder baseViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutId(), parent, false);
            baseViewHolder = getViewHolder();
            baseViewHolder.findViews(convertView);
            convertView.setTag(baseViewHolder);
        } else {
            baseViewHolder = (BaseViewHolder) convertView.getTag();
        }

        T item = getItem(position);
        initView(baseViewHolder, item, position);

        return convertView;
    }

    /**
     * Item布局文件
     */
    protected abstract int getItemLayoutId();

    /**
     * ViewHolder
     */
    protected abstract BaseViewHolder getViewHolder();

    /**
     * 展示数据
     */
    protected abstract void initView(BaseViewHolder baseViewHolder, T item, int position);
}