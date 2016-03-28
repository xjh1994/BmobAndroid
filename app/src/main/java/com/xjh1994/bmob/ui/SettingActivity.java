package com.xjh1994.bmob.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.text.TextUtils;
import android.widget.Toast;

import com.xjh1994.bmob.base.BaseActivity;
import com.xjh1994.bmob.bean.User;
import com.xjh1994.bmob.util.AppManager;
import com.orhanobut.logger.Logger;

import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

/**
 * Created by XJH on 16/3/9.
 * 设置
 */
public class SettingActivity extends BaseActivity {

    private SettingsFragment mSettingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mSettingsFragment = new SettingsFragment(this);
            replaceFragment(com.xjh1994.bmob.R.id.settings_container, mSettingsFragment);
        }
    }

    @Override
    public void setContentView() {
        setContentView(com.xjh1994.bmob.R.layout.activity_setting);
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void replaceFragment(int viewId, android.app.Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(viewId, fragment).commit();
    }

    public static class SettingsFragment extends PreferenceFragment {
        private Context context;

        public SettingsFragment() {

        }

        @SuppressLint("ValidFragment")
        public SettingsFragment(Context context) {
            this.context = context;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(com.xjh1994.bmob.R.xml.preferences);
        }

        Toast mToast;

        public void toast(String text) {
            if (!TextUtils.isEmpty(text)) {
                if (mToast == null) {
                    mToast = Toast.makeText(getActivity(), text,
                            Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(text);
                }
                mToast.show();
            }
        }

        @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, final Preference preference) {
            if ("notification".equals(preference.getKey())) {

            } else if ("share".equals(preference.getKey())) {
                share();
            } else if ("update".equals(preference.getKey())) {
                BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
                    @Override
                    public void onUpdateReturned(int updateStatus, UpdateResponse updateResponse) {
                        if (updateStatus == UpdateStatus.Yes) {//版本有更新

                        } else if (updateStatus == UpdateStatus.No) {
                            toast(getString(com.xjh1994.bmob.R.string.update_latest_version));
                        } else if (updateStatus == UpdateStatus.EmptyField) {//此提示只是提醒开发者关注那些必填项，测试成功后，无需对用户提示
                            Logger.d("请检查你AppVersion表的必填项，1、target_size（文件大小）是否填写；2、path或者android_url两者必填其中一项。");
                        } else if (updateStatus == UpdateStatus.IGNORED) {
                            toast(getString(com.xjh1994.bmob.R.string.update_ignored_version));
                        } else if (updateStatus == UpdateStatus.ErrorSizeFormat) {
                            Logger.d("请检查target_size填写的格式，请使用file.length()方法获取apk大小。");
                        } else if (updateStatus == UpdateStatus.TimeOut) {
                            toast(getString(com.xjh1994.bmob.R.string.update_search_error));
                        }
                    }
                });
                BmobUpdateAgent.update(getActivity());
            } else if ("about".equals(preference.getKey())) {
                startActivity(new Intent(getActivity(), AboutActivity.class));
            } else if ("logout".equals(preference.getKey())) {
                if (null != User.getCurrentUser(getActivity())) {
                    User.logOut(getActivity());
                    AppManager.getAppManager().finishAllActivity();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }

        /**
         * 系统分享
         */
        public void share() {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, getString(com.xjh1994.bmob.R.string.share_app_text));   //附带的说明信息
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, new StringBuilder().append(getString(com.xjh1994.bmob.R.string.share_title)).append(getString(com.xjh1994.bmob.R.string.app_name)).toString()));
        }
    }
}
