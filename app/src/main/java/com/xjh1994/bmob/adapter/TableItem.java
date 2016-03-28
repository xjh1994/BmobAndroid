package com.xjh1994.bmob.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xjh1994.bmob.R;
import com.xjh1994.bmob.base.IAdapterItem;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by XJH on 16/3/28.
 * Blog: http://www.xjh1994.com
 */
public class TableItem implements IAdapterItem<String> {

    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.container)
    LinearLayout container;

    @Override
    public int getLayoutResId() {
        return R.layout.item_app;
    }

    @Override
    public void bindViews(View root) {
        ButterKnife.bind(this, root);
    }

    @Override
    public void setViews() {

    }

    @Override
    public void handleData(Context context, String data, int type) {
        name.setText(data);
    }
}
