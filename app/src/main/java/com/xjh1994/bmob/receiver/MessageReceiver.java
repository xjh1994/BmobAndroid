package com.xjh1994.bmob.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.bmob.newim.event.MessageEvent;

/**
 * Created by XJH on 16/3/8.
 * 接收Bmob服务器消息
 */
public class MessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            final MessageEvent event = (MessageEvent) intent.getSerializableExtra("event");
            //开发者可以在这里发应用通知
        }
    }
}
