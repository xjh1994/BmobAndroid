package com.xjh1994.bmob.service.bmob;

import android.content.Context;

import com.xjh1994.bmob.bean.User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by XJH on 16/3/10.
 */
public class BmobUserService {

    public static void getUserList(Context context, int page, int limit, FindListener<User> findListener) {
        BmobQuery<User> query = new BmobQuery<>();
        query.setLimit(limit);
        query.setSkip(page * limit);
        query.order("order,-createdAt");
        query.findObjects(context, findListener);

    }

}
