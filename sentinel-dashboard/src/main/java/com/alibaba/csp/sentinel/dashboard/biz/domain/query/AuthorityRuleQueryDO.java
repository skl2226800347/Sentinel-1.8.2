package com.alibaba.csp.sentinel.dashboard.biz.domain.query;

import java.io.Serializable;

public class AuthorityRuleQueryDO implements Serializable {
    private String app;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}
