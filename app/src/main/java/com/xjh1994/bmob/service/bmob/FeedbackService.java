package com.xjh1994.bmob.service.bmob;

import android.content.Context;

import com.xjh1994.bmob.bean.Feedback;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by XJH on 16/3/9.
 */
public class FeedbackService {

    public static void addFeedback(Context context, String advice, String contacts, SaveListener saveListener) {
        Feedback feedback = new Feedback();
        feedback.setContent(advice);
        feedback.setContacts(contacts);
        feedback.save(context, saveListener);
    }
}
