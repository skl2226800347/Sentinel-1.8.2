package com.alibaba.csp.sentinel.dashboard.biz.vo.param;

import java.io.Serializable;

public class AppSaveParamVO implements Serializable {
    private Long id;
    private String app;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}
