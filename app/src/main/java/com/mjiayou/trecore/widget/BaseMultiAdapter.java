package com.mjiayou.trecore.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by treason on 15/3/17.
 */
public abstract class BaseMultiAdapter<T> extends BaseAdapter<T> {

    public BaseMultiAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);

        BaseViewHolder baseViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutId(type), parent, false);
            baseViewHolder = getViewHolder(type);
            baseViewHolder.findViews(convertView);
            convertView.setTag(baseViewHolder);
        } else {
            baseViewHolder = (BaseViewHolder) convertView.getTag();
        }

        T item = getItem(position);
        initView(baseViewHolder, item, position, type);

        return convertView;
    }

    /**
     * Item布局文件
     */
    protected abstract int getItemLayoutId(int type);

    /**
     * ViewHolder
     */
    protected abstract BaseViewHolder getViewHolder(int type);

    /**
     * 展示数据
     */
    protected abstract void initView(BaseViewHolder baseViewHolder, T item, int position, int type);
}
