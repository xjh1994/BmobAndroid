package com.xjh1994.bmob.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xjh1994.bmob.CustomApplication;
import com.bmob.utils.BmobLog;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by xjh1994 on 2016/3/6.
 */
public abstract class BaseFragment extends Fragment {
    public CustomApplication mApplication;

    protected View contentView;

    public LayoutInflater mInflater;

    private Handler handler = new Handler();

    public void runOnWorkThread(Runnable action) {
        new Thread(action).start();
    }

    public void runOnUiThread(Runnable action) {
        handler.post(action);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mApplication = CustomApplication.getInstance();
        mInflater = LayoutInflater.from(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflat(inflater, container);
    }

    protected abstract View inflat(LayoutInflater inflater, ViewGroup container);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }


    public BaseFragment() {

    }

    public abstract void initView();

    protected abstract void initData();

    public View findViewById(int paramInt) {
        return getView().findViewById(paramInt);
    }

    public void ShowLog(String msg) {
        BmobLog.i(msg);
    }

    Toast mToast;

    public void toast(String text) {
        if (null != getActivity()) {
            if (!TextUtils.isEmpty(text)) {
                if (mToast == null) {
                    mToast = Toast.makeText(getActivity(), text,
                            Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(text);
                }
                mToast.show();
            }
        } else {
            Logger.e("getActivity is null");
        }
    }

    public void toastLong(String text) {
        if (null != getActivity()) {
            if (!TextUtils.isEmpty(text)) {
                if (mToast == null) {
                    mToast = Toast.makeText(getActivity(), text,
                            Toast.LENGTH_LONG);
                } else {
                    mToast.setText(text);
                }
                mToast.show();
            }
        } else {
            Logger.e("getActivity is null");
        }
    }

    /**
     * 动画启动页面 startAnimActivity
     *
     * @throws
     */
    public void startAnimActivity(Intent intent) {
        this.startActivity(intent);
    }

    public void startAnimActivity(Class<?> cla) {
        getActivity().startActivity(new Intent(getActivity(), cla));
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().toString());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
