package com.xjh1994.bmob.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by XJH on 16/3/9.
 * 用户反馈
 */
public class Feedback extends BmobObject {

    //反馈内容
    private String content;
    //联系方式
    private String contacts;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
}
