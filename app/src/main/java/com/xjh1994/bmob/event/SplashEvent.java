package com.xjh1994.bmob.event;

/**
 * Created by XJH on 16/3/8.
 * 欢迎界面跳转事件
 */
public class SplashEvent {

    public static final int GO_HOME = 0;   //首页
    public static final int GO_GUIDE = 1;  //引导界面
    public static final int GO_LOGIN = 3;  //登录界面

    private int msg;

    public SplashEvent(int msg) {
        this.msg = msg;
    }

    public int getMsg() {
        return msg;
    }

}
