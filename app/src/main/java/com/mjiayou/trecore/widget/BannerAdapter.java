package com.mjiayou.trecore.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mjiayou.trecore.util.ImageViewUtil;
import com.mjiayou.trecore.util.ToastUtil;
import com.mjiayou.trecoredemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by treason on 15/11/15.
 */
public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {

    private final Context mContext;

    private List<String> mList = new ArrayList<>();

    public BannerAdapter(Context context, List<String> list) {
        mContext = context;
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.tc_item_banner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ImageViewUtil.display(mContext, mList.get(position), holder.mIvImg);

        final View itemView = holder.itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = mList.get(position);
                ToastUtil.show(mContext, item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mIvImg;

        public ViewHolder(View view) {
            super(view);
            mIvImg = (ImageView) view.findViewById(R.id.iv_img);
        }
    }
}
