package com.xjh1994.bmob;

import android.app.Application;

import com.xjh1994.bmob.util.AppInfoUtil;
import com.xjh1994.bmob.util.SharePreferenceUtil;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.newim.BmobIM;
import cn.bmob.v3.BmobUser;

/**
 * Created by xjh1994 on 2016/2/26.
 * 程序入口
 */
public class CustomApplication extends Application {

    public static CustomApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        init();
    }

    private void init() {
        initLog();
        initBugly();
        initBmob();
    }

    private void initBmob() {
        BmobIM.init(this);
    }

    private void initBugly() {
        CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(this);
        userStrategy.setAppChannel(AppInfoUtil.getChannel());
        CrashReport.initCrashReport(getApplicationContext(), BuildConfig.BUGLY_APPID, AppInfoUtil.isDebugMode(), userStrategy);
    }

    private void initLog() {
        Logger.init(getString(R.string.app_name)).setLogLevel(BuildConfig.ISRELEASE ? LogLevel.NONE : LogLevel.FULL);
    }


    public synchronized static CustomApplication getInstance() {
        if (null == mInstance) {
            mInstance = new CustomApplication();
        }
        return mInstance;
    }

    // 单例模式，才能及时返回数据
    SharePreferenceUtil mSpUtil;
    public static final String PREFERENCE_NAME = "_sharedinfo";

    public synchronized SharePreferenceUtil getSpUtil() {
        if (mSpUtil == null) {
            String currentId = "";
            BmobUser user = BmobUser.getCurrentUser(getApplicationContext());
            if (user != null)
                currentId = user.getObjectId();
            String sharedName = currentId + PREFERENCE_NAME;
            mSpUtil = new SharePreferenceUtil(this, sharedName);
        }
        return mSpUtil;
    }
}
