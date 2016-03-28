package com.xjh1994.bmob.base;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.xjh1994.bmob.adapter.BaseUltimateAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XJH on 16/3/9.
 */
public abstract class BaseListActivity extends BaseActivity {

    protected static final int ONREFRESH_COMPLETE = 0;
    protected static final int ONLOADMORE_COMPLETE = 1;

    protected MyHandler myHandler = new MyHandler(this);

    protected UltimateRecyclerView ultimateRecyclerView;
    protected LinearLayoutManager linearLayoutManager;
    protected List dataList = new ArrayList<>();
    protected BaseUltimateAdapter adapter;

    protected static class MyHandler extends Handler {
        private WeakReference<Context> reference;

        public MyHandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseListActivity activity = (BaseListActivity) reference.get();
            if (activity != null) {
                switch (msg.what) {
                    case ONREFRESH_COMPLETE:
                        activity.ultimateRecyclerView.setRefreshing(false);
                        break;
                    case ONLOADMORE_COMPLETE:
                        activity.ultimateRecyclerView.setRefreshing(false);
                        break;
                }
            }
        }
    }

    @Override
    public void setContentView() {
        setContentView(com.xjh1994.bmob.R.layout.activity_list);
    }

    @Override
    public void initViews() {
        setBackTitle();

        ultimateRecyclerView = (UltimateRecyclerView) findViewById(com.xjh1994.bmob.R.id.ultimate_recycler_view);
        ultimateRecyclerView.setHasFixedSize(false);
        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);

        ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onLoadNew();
            }
        });
        ultimateRecyclerView.enableLoadmore();
        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                ultimateRecyclerView.setRefreshing(true);
                onLoadMore();
            }
        });

        fetchData();

    }

    protected abstract void onLoadNew();

    protected abstract void onLoadMore();

    protected abstract void fetchData();

    protected void onRefreshComplete() {
        Message message = Message.obtain();
        message.what = ONREFRESH_COMPLETE;
        myHandler.sendMessage(message);
    }

    protected void onLoadMoreComplete() {
        Message message = Message.obtain();
        message.what = ONLOADMORE_COMPLETE;
        myHandler.sendMessage(message);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacksAndMessages(null);
    }
}
