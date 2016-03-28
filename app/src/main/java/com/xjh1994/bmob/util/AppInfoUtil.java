package com.xjh1994.bmob.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.xjh1994.bmob.BuildConfig;
import com.xjh1994.bmob.CustomApplication;

/**
 * Created by XJH on 16/3/8.
 * 获取程序的一些信息如版本号、渠道号等
 */
public class AppInfoUtil {

    private static int versionCode;
    private static String versionName;

    private static String channelName;

    private static final String DEFAULT_CHANNEL_NAME = "unknown";
    private static final String KEY_CHANNEL = "UMENG_CHANNEL";

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            if (versionCode == 0) {
                PackageManager manager = context.getPackageManager();
                PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
                versionCode = info.versionCode;
            }
        } catch (Exception e) {
        }
        return versionCode;
    }

    /**
     * 获取版本名
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            if (TextUtils.isEmpty(versionName)) {
                PackageManager manager = context.getPackageManager();
                PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
                versionName = info.versionName;
            }
        } catch (Exception e) {
        }
        return versionName;
    }

    /**
     * 获取渠道
     *
     * @return
     */
    public static String getChannel() {
        if (TextUtils.isEmpty(channelName) || channelName.equals(DEFAULT_CHANNEL_NAME)) {
            Context context = CustomApplication.getInstance();
            if (context == null) {
                channelName = DEFAULT_CHANNEL_NAME;
            } else {
                try {
                    ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                    channelName = appInfo.metaData.getString(KEY_CHANNEL);
                    if (channelName == null) {
                        channelName = DEFAULT_CHANNEL_NAME;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if (TextUtils.isEmpty(channelName)) {
            channelName = DEFAULT_CHANNEL_NAME;
        }
        return channelName;
    }

    /**
     * 是否可以调试
     *
     * @param context
     * @return
     */
    public static boolean isDebuggable(Context context) {
        int flags = context.getApplicationInfo().flags;
        flags &= ApplicationInfo.FLAG_DEBUGGABLE;
        return (flags != 0);
    }

    /**
     * 是否处于调试模式
     * @return
     */
    public static boolean isDebugMode() {
        // release版本，且非开发者模式，禁止输出调试
        if (BuildConfig.ISRELEASE) {
            return false;
        }
        return true;
    }

    /**
     * 判断程序运行在前台还是后台
     *
     * @param context
     * @return 正在前台运行 返回true，否则返回false
     */
    public static boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (!TextUtils.isEmpty(currentPackageName)
                && currentPackageName.equals(context.getPackageName())) {
            return true;
        }
        return false;
    }
}
