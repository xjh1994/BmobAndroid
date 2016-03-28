package com.xjh1994.bmob.bean;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by XJH on 16/3/28.
 * Blog: http://www.xjh1994.com
 */
public class BmobAppInfo {
    private List<Info> results;

    public List<Info> getResults() {
        return results;
    }

    public void setResults(List<Info> infoList) {
        this.results = infoList;
    }

    public class Info {
        private String className;
        private JSONObject fields;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public JSONObject getFields() {
            return fields;
        }

        public void setFields(JSONObject fields) {
            this.fields = fields;
        }
    }
}
