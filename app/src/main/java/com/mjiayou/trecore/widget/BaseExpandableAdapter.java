package com.mjiayou.trecore.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by treason on 15-11-4.
 */
public abstract class BaseExpandableAdapter<T> extends BaseExpandableListAdapter {

    protected List<T> mList;
    protected static Context mContext;

    public BaseExpandableAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getGroupCount() {
        try {
            if (mList != null) {
                return mList.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public T getGroup(int groupPosition) {
        try {
            if (mList != null && groupPosition < mList.size()) {
                return mList.get(groupPosition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

//    @Override
//    public int getChildrenCount(int groupPosition) {
//        return super.getChildrenCount(groupPosition);
//    }
//
//    @Override
//    public Article getChild(int groupPosition, int childPosition) {
//        return super.getChild(groupPosition, childPosition);
//    }
//
//    @Override
//    public long getChildId(int groupPosition, int childPosition) {
//        return super.getChildId(groupPosition, childPosition);
//    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        BaseViewHolder baseViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(getGroupItemLayoutId(), parent, false);
            baseViewHolder = getGroupViewHolder();
            baseViewHolder.findViews(convertView);
            convertView.setTag(baseViewHolder);
        } else {
            baseViewHolder = (BaseViewHolder) convertView.getTag();
        }

        T item = getGroup(groupPosition);
        initGroupView(baseViewHolder, item, groupPosition);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        BaseViewHolder baseViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(getChildItemLayoutId(), parent, false);
            baseViewHolder = getChildViewHolder();
            baseViewHolder.findViews(convertView);
            convertView.setTag(baseViewHolder);
        } else {
            baseViewHolder = (BaseViewHolder) convertView.getTag();
        }

        T item = getGroup(groupPosition);
        initChildView(baseViewHolder, item, groupPosition, childPosition);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * setList
     */
    public void setList(List<T> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    /**
     * getList
     */
    public List<T> getList() {
        return this.mList;
    }

    /**
     * BaseViewHolder
     */
    public static class BaseViewHolder {
        public void findViews(View view) {
            ButterKnife.inject(this, view);
        }
    }

    /**
     * GroupItem布局文件
     */
    protected abstract int getGroupItemLayoutId();

    /**
     * GroupViewHolder
     */
    protected abstract BaseViewHolder getGroupViewHolder();

    /**
     * Group展示数据
     */
    protected abstract void initGroupView(BaseViewHolder baseViewHolder, T item, int groupPosition);

    /**
     * ChildItem布局文件
     */
    protected abstract int getChildItemLayoutId();

    /**
     * ChildViewHolder
     */
    protected abstract BaseViewHolder getChildViewHolder();

    /**
     * Child展示数据
     */
    protected abstract void initChildView(BaseViewHolder baseViewHolder, T item, int groupPosition, int childPosition);
}
