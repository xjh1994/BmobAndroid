package com.xjh1994.bmob.ui;

import com.xjh1994.bmob.R;
import com.xjh1994.bmob.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by XJH on 16/3/9.
 * 关于
 */
public class AboutActivity extends BaseActivity {

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        setBackTitle();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }
}
