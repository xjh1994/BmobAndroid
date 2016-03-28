package com.xjh1994.bmob.ui;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import com.xjh1994.bmob.R;
import com.xjh1994.bmob.adapter.BaseUltimateAdapter;
import com.xjh1994.bmob.adapter.UserItem;
import com.xjh1994.bmob.base.BaseListActivity;
import com.xjh1994.bmob.base.IAdapterItem;
import com.xjh1994.bmob.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xjh1994 on 2016/3/9.
 * 列表页
 */
public class ListActivity extends BaseListActivity {

    @Override
    protected void fetchData() {
        adapter = new BaseUltimateAdapter(this, dataList) {
            @NonNull
            @Override
            public IAdapterItem createItem(Object type) {
                return new UserItem();
            }
        };
        adapter.setCustomLoadMoreView(LayoutInflater.from(this)
                .inflate(R.layout.custom_bottom_progressbar, null));

        User user = new User();
        user.setObjectId("firststart");
        dataList.add(user);
        dataList.add(user);
        dataList.add(user);
        dataList.add(user);
        dataList.add(user);
        adapter.notifyDataSetChanged();
        ultimateRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onLoadNew() {
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.setObjectId("loadnew");
                List list = new ArrayList();
                list.add(user);
                list.add(user);
                list.add(user);
                list.add(user);
                dataList.clear();
                dataList.add(user);
                dataList.add(user);
                dataList.add(user);
                adapter.setData(dataList);
                adapter.notifyDataSetChanged();

                onRefreshComplete();
            }
        }, 3000);
    }

    @Override
    protected void onLoadMore() {
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.setObjectId("loadmore");
                List list = new ArrayList();
                list.add(user);
                list.add(user);
                list.add(user);
                list.add(user);
                list.add(user);
                adapter.insertInternal(list, dataList);

                onLoadMoreComplete();
            }
        }, 3000);
    }

}
