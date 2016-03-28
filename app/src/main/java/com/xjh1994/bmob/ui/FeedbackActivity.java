package com.xjh1994.bmob.ui;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.xjh1994.bmob.R;
import com.xjh1994.bmob.base.BaseActivity;
import com.xjh1994.bmob.service.bmob.FeedbackService;
import com.xjh1994.bmob.util.StringUtil;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by XJH on 16/3/9.
 * 用户反馈
 */
public class FeedbackActivity extends BaseActivity {

    @Bind(R.id.advice)
    MaterialEditText advice;
    @Bind(R.id.contact)
    MaterialEditText contact;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_feedback);
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

    /**
     * 提交反馈
     */
    private void submit() {
        if (StringUtil.tooShort(advice.getText().toString().trim(), 2)) {
            toast(getString(R.string.too_short));
            return;
        }
        if (StringUtil.tooLong(advice.getText().toString().trim(), 140)) {
            toast(getString(R.string.too_long));
            return;
        }
        if (!TextUtils.isEmpty(contact.getText().toString().trim())) {
            if (StringUtil.tooShort(contact.getText().toString().trim(), 6)) {
                toast(getString(R.string.too_short));
                return;
            }
            if (StringUtil.tooLong(contact.getText().toString().trim(), 30)) {
                toast(getString(R.string.too_long));
                return;
            }
        }
        FeedbackService.addFeedback(this, advice.getText().toString().trim(), contact.getText().toString().trim(), new SaveListener() {
            @Override
            public void onSuccess() {
                toast(getString(R.string.submit_success_thanks_to_advice));
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                toast(new StringBuilder().append(getString(R.string.submit_failed)).append(s).toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.submit:
                submit();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_activity_feedback, menu);
        return true;
    }
}
