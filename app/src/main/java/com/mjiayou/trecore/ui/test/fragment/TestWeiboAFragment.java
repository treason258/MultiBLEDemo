package com.mjiayou.trecore.ui.test.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mjiayou.trecore.TCApp;
import com.mjiayou.trecore.bean.TCSinaStatusesResponseBody;
import com.mjiayou.trecore.net.RequestAdapter;
import com.mjiayou.trecore.ui.TCFragment;
import com.mjiayou.trecore.util.ConvertUtil;
import com.mjiayou.trecore.util.PageUtil;
import com.mjiayou.trecore.util.ToastUtil;
import com.mjiayou.trecoredemo.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by treason on 16/5/16.
 */
public class TestWeiboAFragment extends TCFragment {

    @InjectView(R.id.listview)
    PullToRefreshListView mListView;

    private ArrayList<String> mList = new ArrayList<>();
    private ArrayAdapter mAdapter;

    private boolean isAutoLoadMore = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.tc_fragment_test_weibo_a, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        mListView.autoPullDownToRefresh();
    }

    @Override
    public void initView() {
        super.initView();

        // 设置下拉刷新模式
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        // 监听下拉上拉事件
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMoreData();
            }
        });

        mAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(mAdapter);
        // 监听Item点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int positionReal = position - 1;
                ToastUtil.show(mContext, mList.get(positionReal));
            }
        });
        // 监听正在滚动事件
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // 判断滚动到底部
                if (!isAutoLoadMore && view.getLastVisiblePosition() == (view.getCount() - 3)) {
                    isAutoLoadMore = true;
                    mListView.autoPullUpToLoadMore();
                }
            }
        });
    }

    @Override
    public void getData(int pageNumber) {
        super.getData(pageNumber);

        getRequestAdapter().sinaStatuses();
    }

    @Override
    public void refreshData() {
        super.refreshData();

        getData(PageUtil.pageReset());
    }

    @Override
    public void loadMoreData() {
        super.loadMoreData();

        getData(PageUtil.pageAdd());
    }

    @Override
    public void callback(Message msg) {
        super.callback(msg);
        switch (msg.what) {
            case RequestAdapter.SINA_STATUSES:
                TCSinaStatusesResponseBody response = (TCSinaStatusesResponseBody) msg.obj;
                if (response != null) {
                    if (PageUtil.isPullRefresh()) {
                        mList.clear();
                    }
                    refreshView(response);
                } else {
                    ToastUtil.show(mContext, TCApp.get().getResources().getString(R.string.tc_error_response_null));
                }
                mListView.onRefreshComplete();
                isAutoLoadMore = false;
                break;
        }
    }

    public void refreshView(TCSinaStatusesResponseBody response) {
        mList.addAll(ConvertUtil.parseSinaStatusesToString(response.getStatuses()));
        mAdapter.notifyDataSetChanged();
    }
}
