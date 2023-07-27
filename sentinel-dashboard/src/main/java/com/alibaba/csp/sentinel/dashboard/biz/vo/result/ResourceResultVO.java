package com.alibaba.csp.sentinel.dashboard.biz.vo.result;

import java.io.Serializable;

public class ResourceResultVO implements Serializable {
    private String app;
    private String resourceName;
    private String resourceUrl;
    private Integer appType;
    private Boolean shown;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public Boolean getShown() {
        return shown;
    }

    public void setShown(Boolean shown) {
        this.shown = shown;
    }
}
