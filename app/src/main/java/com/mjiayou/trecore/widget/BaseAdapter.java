package com.mjiayou.trecore.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by treason on 15/3/25.
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

    protected List<T> mList;
    protected Context mContext;

    public BaseAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
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
    public T getItem(int position) {
        try {
            if (mList != null && position < mList.size()) {
                return mList.get(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
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
    public class BaseViewHolder {

        public void findViews(View view) {
            ButterKnife.inject(this, view);
        }

        public void init(T item) {
        }
    }

    /**
     * ComViewHolder
     */
    public class ComViewHolder extends BaseViewHolder {
        @InjectView(android.R.id.icon)
        public ImageView mIcon;
        @InjectView(android.R.id.text1)
        public TextView mText1;
        @InjectView(android.R.id.text2)
        public TextView mText2;
    }
}
