package com.xjh1994.bmob.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xjh1994.bmob.R;
import com.xjh1994.bmob.base.IAdapterItem;
import com.xjh1994.bmob.bean.Results;
import com.xjh1994.bmob.ui.TableListActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by XJH on 16/3/28.
 * Blog: http://www.xjh1994.com
 */
public class AppItem implements IAdapterItem<Results> {

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
    public void handleData(final Context context, final Results results, int type) {
        name.setText(results.getAppName());
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, TableListActivity.class);
                intent.putExtra(TableListActivity.EXTRA_APPID, results.getApplicationId());
                intent.putExtra(TableListActivity.EXTRA_MASTERKEY, results.getMasterKey());
                context.startActivity(intent);
            }
        });
    }
}
