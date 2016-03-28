package com.xjh1994.bmob.bean;

public class Results {

    private String appName;
    private String applicationId;
    private String restKey;
    private String masterKey;
    private String accessKey;
    private String secretKey;
    private int status;
    private int notAllowedCreateTable;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getRestKey() {
        return restKey;
    }

    public void setRestKey(String restKey) {
        this.restKey = restKey;
    }

    public String getMasterKey() {
        return masterKey;
    }

    public void setMasterKey(String masterKey) {
        this.masterKey = masterKey;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNotAllowedCreateTable() {
        return notAllowedCreateTable;
    }

    public void setNotAllowedCreateTable(int notAllowedCreateTable) {
        this.notAllowedCreateTable = notAllowedCreateTable;
    }
}