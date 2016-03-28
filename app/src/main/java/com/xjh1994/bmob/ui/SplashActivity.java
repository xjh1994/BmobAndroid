package com.xjh1994.bmob.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xjh1994.bmob.CustomApplication;
import com.xjh1994.bmob.event.SplashEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by xjh1994 on 2016/2/26.
 * 欢迎界面
 */
public class SplashActivity extends AppCompatActivity {

    private static final int SHOW_TIME_MIN = 800;   //欢迎界面最小等待时间ms
    private long startTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        forward();
    }

    //初始化工作
    private void init() {
        EventBus.getDefault().register(this);
        startTime = System.currentTimeMillis();
        //只执行一次，否则会生成多行记录
//        BmobUpdateAgent.initAppVersion(this);
    }

    //跳转
    private void forward() {
        long loadingTime = System.currentTimeMillis() - startTime;
        if (loadingTime < SHOW_TIME_MIN) {    //如果加载时间还没到最小等待时间
            try {
                Thread.sleep(SHOW_TIME_MIN - loadingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        boolean isFirstUse = CustomApplication.getInstance().getSpUtil().isFirstUse();
        if (isFirstUse)
            EventBus.getDefault().post(new SplashEvent(SplashEvent.GO_GUIDE));
//TODO 未登录状态        else if (BmobUser.getCurrentUser(getApplicationContext()) == null)
//            EventBus.getDefault().post(new SplashEvent(SplashEvent.GO_LOGIN));
        else
            EventBus.getDefault().post(new SplashEvent(SplashEvent.GO_HOME));
    }

    public void onEventMainThread(SplashEvent splashEvent) {
        switch (splashEvent.getMsg()) {
            case SplashEvent.GO_HOME:
                startAnimActivity(MainActivity.class);
                break;
            case SplashEvent.GO_GUIDE:
                CustomApplication.getInstance().getSpUtil().setIsFirstUse(false);
                startAnimActivity(GuideActivity.class);
                break;
            case SplashEvent.GO_LOGIN:
                startAnimActivity(LoginActivity.class);
                break;
        }
    }

    public void startAnimActivity(Class<?> cla) {
        this.finish();
        this.startActivity(new Intent(this, cla));
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
